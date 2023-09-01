package uo.ri.cws.application.business.mechanic.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.assembler.MechanicAssembler;

public class UpdateMechanic implements Command<Void> {
	private MechanicBLDto mechanic; // actualizamos
		
	public UpdateMechanic(MechanicBLDto mechanic) {
		Argument.isNotNull(mechanic);
		Argument.isNotEmpty(mechanic.id);
		Argument.isNotEmpty(mechanic.dni);
		Argument.isNotEmpty(mechanic.name);
		Argument.isNotEmpty(mechanic.surname);
		
		this.mechanic = mechanic;
	}

	public Void execute() throws BusinessException {
		if (PersistenceFactory.forMechanic().findById(mechanic.id).isEmpty()) {
			throw new BusinessException("Mechanic not founde with id " + mechanic.id);
		}
		
		PersistenceFactory.forMechanic().update(MechanicAssembler.toMechanicDALDto(mechanic));
		return null;
	}

}
