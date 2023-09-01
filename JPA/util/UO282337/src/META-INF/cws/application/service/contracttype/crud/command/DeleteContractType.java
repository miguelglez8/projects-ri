package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteContractType implements Command<Void> {

	private String name;

	public DeleteContractType(String name) {
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotBlank(name);
		
		this.name=name;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<ContractType> om = Factory.repository.forContractType().findByName(name);
		BusinessChecks.isFalse(om.isEmpty(), "El tipo de contrato debe de existir");
		
		BusinessChecks.isTrue(om.get().getContracts().isEmpty(), "No puedes eliminarlo porque hay contratos asociados a este tipo");

		Factory.repository.forContractType().remove(om.get());
				
		return null;
	}
	
	 

}
