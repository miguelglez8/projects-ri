package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.DtoAssembler;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.cws.application.util.command.Command;

public class FindMechanicsInForce implements Command<List<MechanicDto>> {

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		return DtoAssembler.toDtoList(Factory.repository.forMechanic().findAllInForce());
	}

}
