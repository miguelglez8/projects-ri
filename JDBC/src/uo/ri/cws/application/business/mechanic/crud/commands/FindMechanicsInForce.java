package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class FindMechanicsInForce implements Command<List<MechanicBLDto>> {

	@Override
	public List<MechanicBLDto> execute() throws BusinessException {
		List<ContractDALDto> contratos = PersistenceFactory.forContract().findContractsInForce();
		if (contratos.isEmpty()) {
			List<MechanicBLDto> list = new ArrayList<>();
			return list;
		}
		List<String> mechanicIds = new ArrayList<>();
		for (int i=0; i < contratos.size(); i++) {
			mechanicIds.add(contratos.get(i).mechanic_id);
		}
		return MechanicAssembler.toDtoList(PersistenceFactory.forMechanic().findMechanicsByIds(mechanicIds));
	}

}
