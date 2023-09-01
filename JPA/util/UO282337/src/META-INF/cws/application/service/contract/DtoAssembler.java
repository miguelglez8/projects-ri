package uo.ri.cws.application.service.contract;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.cws.domain.Contract;

public class DtoAssembler {

	public static List<ContractSummaryDto> toSummaryDtoList(List<Contract> list) {
		List<ContractSummaryDto> l = new ArrayList<>();
		for (int i=0; i < list.size(); i++) {
			l.add(toSummaryDto(list.get(i)));
		}
		return l;
	}

	private static ContractSummaryDto toSummaryDto(Contract contract) {
		ContractSummaryDto c = new ContractSummaryDto();
		if (contract.getMechanic().isPresent()) {
			c.dni = contract.getMechanic().get().getDni();
		} else {
			c.dni = contract.getFiredMechanic().get().getDni();
		}
		c.settlement = contract.getSettlement();
		c.state = contract.getState();
		c.numPayrolls = contract.getPayrolls().size();
		c.version = contract.getVersion();
		c.id = contract.getId();
		c.version = contract.getVersion();
		return c;
	}

	public static Optional<ContractDto> toDto(Contract contract) {
		ContractDto c = new ContractDto();
		c.id = contract.getId();
		c.version = contract.getVersion();
		if (contract.getMechanic().isPresent()) {
			c.dni = contract.getMechanic().get().getDni();
		} else {
			c.dni = contract.getFiredMechanic().get().getDni();
		}
		c.contractTypeName = contract.getContractType().getName();
		c.professionalGroupName = contract.getProfessionalGroup().getName();
		c.startDate = contract.getStartDate();
		if (contract.getEndDate().isPresent())
			c.endDate = contract.getEndDate().get();
		c.annualBaseWage = contract.getAnnualBaseWage();
		c.settlement = contract.getSettlement();
		c.state = contract.getState();
		return Optional.of(c);
	}


}
