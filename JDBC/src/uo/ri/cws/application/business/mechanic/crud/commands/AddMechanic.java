package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.UUID;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.assembler.MechanicAssembler;

public class AddMechanic implements Command<MechanicBLDto> {
	// operaciones crud -> insert, delete, update, select
	
	private MechanicBLDto mechanicDto; // se lo pasamos para añadirlo en el método
	
	public AddMechanic(MechanicBLDto mechanicDto) {
		Argument.isNotNull(mechanicDto);
		Argument.isNotEmpty(mechanicDto.dni);
		
		this.mechanicDto = mechanicDto;
	}

	public MechanicBLDto execute() throws BusinessException {
		mechanicDto.id=UUID.randomUUID().toString();
		mechanicDto.version=1L;
		
		if (! PersistenceFactory.forMechanic().findByDni(mechanicDto.dni).isEmpty()) {
			throw new BusinessException("Mechanic exists");
		}
				
		PersistenceFactory.forMechanic().add(MechanicAssembler.toMechanicDALDto(mechanicDto));
				
		return mechanicDto;		
	}

}
