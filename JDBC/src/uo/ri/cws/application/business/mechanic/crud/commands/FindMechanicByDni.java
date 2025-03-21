package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class FindMechanicByDni implements Command<Optional<MechanicBLDto>>{
	private String dni;
	
	public FindMechanicByDni(String dni) {
		Argument.isNotEmpty(dni);
		this.dni = dni;
	}

	public Optional<MechanicBLDto> execute() throws BusinessException {
				
		return MechanicAssembler.toMechanicBLDto(PersistenceFactory.forMechanic().findByDni(dni));
	}
}
