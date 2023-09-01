package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateContract implements Command<Void> {
	private ContractDto contract; 
	
	public UpdateContract(ContractDto dto) {
		ArgumentChecks.isNotNull(dto);
		
		ArgumentChecks.isNotEmpty(dto.id);
		ArgumentChecks.isNotBlank(dto.id);
		
		ArgumentChecks.isTrue(dto.annualBaseWage>0);
		
		this.contract = dto;
	}
	
	/**
	 * It updates a contract that must be in force.
	 * Only three of the fields in the argument will be considered: id to identify the contract to update and
	 * endDate (if not null in the argument) and annualBaseWage, to update values stored. 
	 * 
	 * If endDate provided is not null, it must be a valid future date and will be updated. If it is null, 
	 * then contract endDate will be set to null.
	 * 
	 * @param dto, just id, endDate and annualBaseWage are mandatory. Other fields in the argument 
	 * will be ignored.
	 * 
	 */
	public Void execute() throws BusinessException {
		BusinessChecks.exists(Factory.repository.forContract().findById(contract.id), "El contrato no existe");
		BusinessChecks.isTrue(contract.state.equals(ContractState.IN_FORCE)
				, "El contrato está terminado");
		BusinessChecks.isTrue(! (contract.contractTypeName.equals("FIXED_TERM") && contract.endDate.isBefore(contract.startDate))
				, "El tipo es FIXED_TERM y la fecha de inicio es posterior a la fecha de fin de contrato");
		
		Optional<Contract> om = Factory.repository.forContract().findById(contract.id);
		
		Contract m = om.get();
				
		m.setAnnualWage(contract.annualBaseWage);
		if (contract.endDate!=null) {
			m.setEndDate(contract.endDate);
		}
		
		return null;
	}

}
