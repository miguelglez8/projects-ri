package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class AddContract implements Command<ContractDto> {	
		private ContractDto contrato; // se lo pasamos para a�adirlo en el m�todo
		
		public AddContract(ContractDto contrato) {
			ArgumentChecks.isNotNull(contrato);
			
			ArgumentChecks.isNotEmpty(contrato.dni);
			ArgumentChecks.isNotBlank(contrato.dni);
			
			ArgumentChecks.isNotEmpty(contrato.contractTypeName);
			ArgumentChecks.isNotBlank(contrato.contractTypeName);

			ArgumentChecks.isNotEmpty(contrato.professionalGroupName);
			ArgumentChecks.isNotBlank(contrato.contractTypeName);

			ArgumentChecks.isTrue(contrato.annualBaseWage>0);
			ArgumentChecks.isNotNull(contrato.startDate);
			if(contrato.professionalGroupName.equals("FIXED_TERM")) {
				ArgumentChecks.isNotNull(contrato.endDate);
			}
			
			this.contrato = contrato;
		}

		public ContractDto execute() throws BusinessException {
						
			compruebaExisteMecanico();
			
			compruebaExisteContractType(); 
			
			compruebaExisteGrupoProfesional();
			
			compruebaFecha();
			
			Optional<Mechanic> m = Factory.repository.forMechanic().findByDni(contrato.dni);
			Optional<Contract> c = m.get().getContractInForce();
			terminarContrato(c);
	
			añadirContrato(m);
						
			return contrato;		
		}

		private void añadirContrato(Optional<Mechanic> m) {
			Optional<ContractType> t = Factory.repository.forContractType().findByName(contrato.contractTypeName);
			Optional<ProfessionalGroup> p = Factory.repository.forProfessionalGroup().findByName(contrato.professionalGroupName);
			
			Contract contract = new Contract(m.get(), t.get(), p.get(), contrato.annualBaseWage);
			
			Factory.repository.forContract().add(contract);
			
			contrato.id = contract.getId();
		}

		private void terminarContrato(Optional<Contract> c) {
			if (! c.isEmpty()) {
				c.get().terminate();
			}
		}

		private void compruebaFecha() throws BusinessException {
			if (contrato.endDate != null) {
				if (contrato.endDate.isBefore(contrato.startDate) || contrato.endDate.isEqual(contrato.startDate)) {
					throw new BusinessException("EndDate cant be before startDate");
				} 
			}
		}

		private void compruebaExisteGrupoProfesional() throws BusinessException {
			BusinessChecks.isTrue(Factory.repository.forProfessionalGroup().findByName(contrato.professionalGroupName).isPresent(), 
					"El tipo de grupo profesional debe de existir");
		}

		private void compruebaExisteContractType() throws BusinessException {
			BusinessChecks.isTrue(Factory.repository.forContractType().findByName(contrato.contractTypeName).isPresent(), 
					"El tipo de contrato debe de existir");
		}

		private void compruebaExisteMecanico() throws BusinessException {
			BusinessChecks.isTrue(Factory.repository.forMechanic().findByDni(contrato.dni).isPresent(), "El mecánico no existe");
		}


}
