package uo.ri.cws.application.service.contracttype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.domain.ContractType;

public class DtoAssembler {

	public static List<ContractTypeDto> toContractTypeDtoList(List<ContractType> om) {
		List<ContractTypeDto> l = new ArrayList<>();
		for (int i=0; i < om.size(); i++) {
			l.add(toContractTypeDto(Optional.of(om.get(i))).get());
		}
		return l;
	}

	public static Optional<ContractTypeDto> toContractTypeDto(Optional<ContractType> om) {
		if (om.isEmpty()) return Optional.empty();
		ContractTypeDto c = new ContractTypeDto();
		c.version = om.get().getVersion();
		c.name = om.get().getName();
		c.id = om.get().getId();
		c.compensationDays = om.get().getCompensationDays();
		return Optional.of(c);
	}
	
}
