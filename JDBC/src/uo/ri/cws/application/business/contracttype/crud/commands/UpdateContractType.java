package uo.ri.cws.application.business.contracttype.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contracttype.assembler.ContractTypeAssembler;

public class UpdateContractType implements Command<Void> {

	private ContractTypeBLDto dto;

	public UpdateContractType(ContractTypeBLDto dto) {
		Argument.isNotNull(dto);
		Argument.isNotEmpty(dto.name);
		Argument.isTrue(dto.compensationDays >= 0);
		
		this.dto=dto;
	}
	
	/**
	 
	 * @throws BusinessException if:
	 * 		- the contract type does not exist, or
	 */
	
	@Override
	public Void execute() throws BusinessException {
		if (PersistenceFactory.forContractType().findContractTypeByName(dto.name).isEmpty()) {
			throw new BusinessException("You cant update de type contract because does not exist");
		}
		PersistenceFactory.forContractType().update(ContractTypeAssembler.toContractTypeDALDto(dto));
		return null;
	}
	

}
