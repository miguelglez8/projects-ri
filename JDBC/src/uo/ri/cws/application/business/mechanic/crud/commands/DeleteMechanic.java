package uo.ri.cws.application.business.mechanic.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class DeleteMechanic implements Command<Void> {
	
	private String id;
	
	public DeleteMechanic(String idMechanic) {
		Argument.isNotEmpty(idMechanic);
		
		this.id=idMechanic;
	}

	public Void execute() throws BusinessException {
		if (PersistenceFactory.forMechanic().findById(id).isEmpty()) {
			throw new BusinessException("Not mechanic found with id " + id);
		}
		
		if (! PersistenceFactory.forWorkOrder().findByMechanic(id).isEmpty()) {
			throw new BusinessException("Not work order found with id " + id);
		}
		
		if (! PersistenceFactory.forContract().findByMechanicId(id).isEmpty()) {
			throw new BusinessException("Mechanic has contracts");
		}
						
		PersistenceFactory.forMechanic().remove(id);
		return null;
	}

}
