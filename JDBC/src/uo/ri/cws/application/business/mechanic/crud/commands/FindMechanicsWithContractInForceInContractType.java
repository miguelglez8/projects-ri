package uo.ri.cws.application.business.mechanic.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class FindMechanicsWithContractInForceInContractType implements Command<List<MechanicBLDto>>{
	
	private String name;
	
	public FindMechanicsWithContractInForceInContractType(String name) {
		Argument.isNotEmpty(name);
		
		this.name=name;
	}

	@Override
	public List<MechanicBLDto> execute() throws BusinessException {
		Optional<ContractTypeBLDto> p = ContractTypeAssembler.toContractTypeBLDto(PersistenceFactory.forContractType().findContractTypeByName(name));
		if (p.isEmpty()) {
			List<MechanicBLDto> list = new ArrayList<>();
			return list;
		}
		if (PersistenceFactory.forContract().findByContractTypeId(p.get().id).isEmpty()) {
			List<MechanicBLDto> list = new ArrayList<>();
			return list;
		}
		List<ContractDALDto> contratos = PersistenceFactory.forContract().findByContractTypeId(p.get().id);
		if (contratos.isEmpty()) {
			List<MechanicBLDto> list = new ArrayList<>();
			return list;
		}
		List<String> dniMecanicos = new ArrayList<>();
		for (int i=0; i < contratos.size(); i++) {
			if (contratos.get(i).state.equals("IN_FORCE"))
				dniMecanicos.add(contratos.get(i).mechanic_id);
		}
		return MechanicAssembler.toMechanicDtoList(PersistenceFactory.forMechanic().findMechanicsByIds(dniMecanicos));
	}

}
