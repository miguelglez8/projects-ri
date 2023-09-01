package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class FindMechanicsInProfessionalGroups implements Command<List<MechanicBLDto>> {

	private String name;
	
	public FindMechanicsInProfessionalGroups(String name) {
		Argument.isNotEmpty(name);
		
		this.name=name;
	}

	@Override
	public List<MechanicBLDto> execute() throws BusinessException {
		Optional<ProfessionalGroupBLDto> p = ProfessionalGroupAssembler.toProfessionalGroupBLDto(PersistenceFactory.forProfessionalGroup().findByName(name));
		if (p.isEmpty()) {
			List<MechanicBLDto> list = new ArrayList<>();
			return list;
		}
		if (PersistenceFactory.forContract().findByProfessionalGroupId(p.get().id).isEmpty()) {
			List<MechanicBLDto> list = new ArrayList<>();
			return list;
		}
		List<ContractDALDto> contratos = PersistenceFactory.forContract().findByProfessionalGroupId(p.get().id);
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
