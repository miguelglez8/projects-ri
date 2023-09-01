package uo.ri.cws.application.business.contracttype.crud.commands;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class ListContractTypeByName implements Command<Optional<ContractTypeBLDto>> {
	
	private String name;

	public ListContractTypeByName(String name) {
		Argument.isNotEmpty(name);
		
		this.name=name;
	}

	@Override
	public Optional<ContractTypeBLDto> execute() throws BusinessException {
		return ContractTypeAssembler.toContractTypeBLDtoOptional(PersistenceFactory.forContractType().findContractTypeByName(name));
	}
	

}
