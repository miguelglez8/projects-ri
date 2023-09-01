package uo.ri.cws.application.business.contract.crud.commands;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.assembler.ContractAssembler;

public class AddContract implements Command<ContractBLDto> {	
		private ContractBLDto contrato; // se lo pasamos para añadirlo en el método
		
		public AddContract(ContractBLDto contrato) {
			Argument.isNotNull(contrato);
			Argument.isNotEmpty(contrato.dni);
			Argument.isNotEmpty(contrato.contractTypeName);
			Argument.isNotEmpty(contrato.professionalGroupName);
			Argument.isTrue(contrato.annualBaseWage>0);
			Argument.isNotNull(contrato.startDate);
			if(contrato.professionalGroupName.equals("FIXED_TERM")) {
				Argument.isNotNull(contrato.endDate);
			}
			
			this.contrato = contrato;
		}

		public ContractBLDto execute() throws BusinessException {
			
			compruebaExisteContrato();
			
			compruebaExisteMecanico();
			
			compruebaExisteContractType(); 
			
			compruebaExisteGrupoProfesional();
			
			compruebaFecha();

			Optional<MechanicBLDto> m = MechanicAssembler.toBLDto(PersistenceFactory.forMechanic().findByDni(contrato.dni));
			
			if (! PersistenceFactory.forContract().findInForceByIdMechanic(m.get().id).isEmpty()) {
				ContractBLDto c = uo.ri.cws.application.business.contract.assembler.ContractAssembler.toContractDto(
						PersistenceFactory.forContract().findInForceByIdMechanic(m.get().id).get());
				finalizarContrato(c);

			}
			
			List<String> list = añadirDatosContrato();
			PersistenceFactory.forContract().add(ContractAssembler.toContractDALDto(contrato, list));
			
			return contrato;		
		}

		private void finalizarContrato(ContractBLDto c) {
			c.endDate =  getEndOfMonth();
			c.settlement = obtenerValorIndemnizacion(c);
			c.state = ContractState.TERMINATED;
			PersistenceFactory.forContract().update(ContractAssembler.toContractDALDto(c));
		}

		private List<String> añadirDatosContrato() {
			contrato.id=UUID.randomUUID().toString();
			contrato.version=1L;
			String contractTypeId = PersistenceFactory.forContractType().findContractTypeByName(contrato.contractTypeName).get().id;
			String professionalGroupId = PersistenceFactory.forProfessionalGroup().findByName(contrato.professionalGroupName).get().id;
			String mechanicId = PersistenceFactory.forMechanic().findByDni(contrato.dni).get().id; 
			contrato.state = ContractState.IN_FORCE;
			List<String> list = new ArrayList<>();
			list.add(mechanicId); list.add(contractTypeId); list.add(professionalGroupId);
			return list;
		}

	

		private void compruebaFecha() throws BusinessException {
			if (contrato.endDate != null) {
				if (contrato.endDate.isBefore(contrato.startDate) || contrato.endDate.isEqual(contrato.startDate)) {
					throw new BusinessException("EndDate cant be before startDate");
				} 
			}
		}

		private void compruebaExisteGrupoProfesional() throws BusinessException {
			Optional<ProfessionalGroupBLDto> d = ProfessionalGroupAssembler.toProfessionalGroupBLDto(PersistenceFactory.forProfessionalGroup().findByName(contrato.professionalGroupName));

			if (d.isEmpty()) {
				throw new BusinessException("Professional group name does not exist");
			}
		}

		private void compruebaExisteContractType() throws BusinessException {
			Optional<ContractTypeBLDto> co = ContractTypeAssembler.toContractTypeBLDto(PersistenceFactory.forContractType().findContractTypeByName(contrato.contractTypeName));
			if (co.isEmpty()) {
				throw new BusinessException("Contract type does not exist");
			}
		}

		private void compruebaExisteMecanico() throws BusinessException {
			if (PersistenceFactory.forMechanic().findByDni(contrato.dni).isEmpty()) {
				throw new BusinessException("Mechanic does not exist");
			}
		}

		private void compruebaExisteContrato() throws BusinessException {
			if (PersistenceFactory.forContract().findById(contrato.id).isPresent()) {
				throw new BusinessException("You cant add because contract id dont exist");
			}
		}
		
		private double obtenerValorIndemnizacion(ContractBLDto c) {
			return Math.floor(calcularIndemnizacion(c) * 100) / 100;
		}
		
		
		private double calcularIndemnizacion(ContractBLDto c) {
			if (Period.between(c.startDate, c.endDate).getYears() > 0) {
				double media = obtenerSumaNominas(c) / 365 ;
				double days = PersistenceFactory.forContractType().findContractTypeByName(contrato.contractTypeName).get().compensationDays;
				int years = Period.between(c.startDate,c.endDate).getYears();
				return media * days * years;
			} else {
				return 0;
			}
		}

		private double obtenerSumaNominas(ContractBLDto c) {
			List<PayrollBLDto> list = PayrollAssembler.toPayrollBLDtoList(PersistenceFactory.forPayroll().findByContractId(c.id));
			list = list.stream().sorted((p1, p2) -> p1.date.compareTo(p2.date)).collect(Collectors.toList());
			
			double ca = 0;
			for (int i=0; i < list.size(); i++) {
				int years = Period.between(list.get(i).date, LocalDate.now()).getYears();
				if (years < 1)
					ca += list.get(i).monthlyWage + list.get(i).bonus + 
					list.get(i).productivityBonus + list.get(i).trienniumPayment;
			}
			return ca;
		}

		public static LocalDate getEndOfMonth() {
	        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	    }

}
