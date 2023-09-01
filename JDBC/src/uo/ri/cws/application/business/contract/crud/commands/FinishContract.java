package uo.ri.cws.application.business.contract.crud.commands;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.assembler.ContractAssembler;

public class FinishContract implements Command<Void>{
	
	private String contractId;
	
	public FinishContract(String contractId) {
		Argument.isNotEmpty(contractId);
		
		this.contractId = contractId;
	}

	@Override
	public Void execute() throws BusinessException {
		if (PersistenceFactory.forContract().findById(contractId).isEmpty()) {
			throw new BusinessException("You can't finish the contract because does not exist");
		}
		if (PersistenceFactory.forContract().findById(contractId).isPresent()) {
			if (PersistenceFactory.forContract().findById(contractId).get().state.equals("TERMINATED")) {
				throw new BusinessException("You can't finish the contract because is not in force");
			}	
		}
		
		finalizaContrato();
		
		return null;
	}

	private void finalizaContrato() {
		ContractBLDto c = uo.ri.cws.application.business.contract.assembler.ContractAssembler.toContractBLDto(PersistenceFactory.forContract().findById(contractId)).get();
		
		c.endDate = getEndOfMonth();
		c.settlement = obtenerValorIndemnizacion(c);
		c.state = ContractState.TERMINATED;
		
		PersistenceFactory.forContract().update(ContractAssembler.toContractDALDto(c));
	}
	
	private double obtenerValorIndemnizacion(ContractBLDto c) {
		return Math.floor(calcularIndemnizacion(c) * 100) / 100;
	}
	
	private double calcularIndemnizacion(ContractBLDto c) {
		if (Period.between(c.startDate, c.endDate).getYears() > 0) {
			double media =  obtenerSumaNominas(c)/365;
			double days =  PersistenceFactory.forContractType().findById(
						PersistenceFactory.forContract().findById(contractId).get().contractType_id).get().compensationDays;
			int years = Period.between(c.startDate, c.endDate).getYears();
			return media * days * years;
		} else {
			return 0;
		}
	}
	
	
	private double obtenerSumaNominas(ContractBLDto c) {
		List<PayrollBLDto> list = PayrollAssembler.toPayrollBLDtoList(PersistenceFactory.forPayroll().findByContractId(c.id));
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
