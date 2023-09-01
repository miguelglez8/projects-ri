package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;


public class DeleteContract implements Command<Void> {
	private String id;
	
	public DeleteContract(String id) {
		ArgumentChecks.isNotEmpty(id);
		ArgumentChecks.isNotBlank(id);
		
		this.id=id;
	}

	public Void execute() throws BusinessException {
		Optional<Contract> c = Factory.repository.forContract().findById(id);
		BusinessChecks.exists(c, "El contrato no existe");
		
		if (c.get().getMechanic().isPresent())
			BusinessChecks.isFalse(c.get().getMechanic().get().getAssigned().size() > 0, "Tiene workorders asociados");

		BusinessChecks.isTrue(c.get().getPayrolls().isEmpty(), "Existen payrolls asociadas a este contrato");
							
		Factory.repository.forContract().remove(c.get());
		
		return null;
	}

}
