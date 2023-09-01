package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class FindAllMechanic implements Command<List<MechanicBLDto>> {
	
	public List<MechanicBLDto> execute() throws BusinessException {
		return MechanicAssembler.toMechanicDtoList(PersistenceFactory.forMechanic().findAll());
	}


}
