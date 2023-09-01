package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.DtoAssembler;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicsWithContractInForceInContractType implements Command<List<MechanicDto>>{
	
	private String name;
	
	public FindMechanicsWithContractInForceInContractType(String name) {
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotBlank(name);

		this.name=name;
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		Optional<ContractType> p = Factory.repository.forContractType().findByName(name);
		if (p.isEmpty()) {
			return new ArrayList<>();
		}
		return DtoAssembler.toDtoList(Factory.repository.forMechanic().findInForceInContractType(p.get()));
	}

}
