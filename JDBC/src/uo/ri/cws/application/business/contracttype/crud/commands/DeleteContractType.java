package uo.ri.cws.application.business.contracttype.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class DeleteContractType implements Command<Void> {

	private String name;

	public DeleteContractType(String name) {
		Argument.isNotEmpty(name);
		
		this.name=name;
	}

	@Override
	public Void execute() throws BusinessException {
		if (PersistenceFactory.forContractType().findContractTypeByName(name).isEmpty()) {
			throw new BusinessException("You cant delete because the name does not exist");
		}
		ContractTypeBLDto c = ContractTypeAssembler.toContractTypeBLDto(PersistenceFactory.forContractType().findContractTypeByName(name)).get();
		if (! PersistenceFactory.forContract().findById(c.id).isEmpty()) {
			throw new BusinessException("You cant delete because there are contracts registered with the contract type");
		}			
		PersistenceFactory.forContractType().remove(name);
		return null;
	}
	
	 

}
