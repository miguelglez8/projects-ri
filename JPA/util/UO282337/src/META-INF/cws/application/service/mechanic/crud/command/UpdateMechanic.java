package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic implements Command<Void> {

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto);
		
		ArgumentChecks.isNotEmpty(dto.dni);
		ArgumentChecks.isNotBlank(dto.dni);
		
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotBlank(dto.name);
		
		ArgumentChecks.isNotEmpty(dto.surname);
		ArgumentChecks.isNotBlank(dto.surname);
		
		this.dto = dto;
	}

	public Void execute() throws BusinessException {
		Optional<Mechanic> om = Factory.repository.forMechanic().findById(dto.id);
		BusinessChecks.exists(om);
		
		Mechanic m = om.get();
				
		m.setName(dto.name);
		m.setSurname(dto.surname);
		
		return null;
	}

}
