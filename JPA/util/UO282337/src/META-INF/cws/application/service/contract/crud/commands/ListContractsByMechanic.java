package uo.ri.cws.application.service.contract.crud.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;


public class ListContractsByMechanic implements Command<List<ContractSummaryDto>> {
	private String id;
	
	public ListContractsByMechanic(String id) {
		ArgumentChecks.isNotEmpty(id);
		
		this.id = id;
	}

	public List<ContractSummaryDto> execute() throws BusinessException {
		Optional<Mechanic> m = Factory.repository.forMechanic().findById(id);
		if (m.isEmpty()) {
			return new ArrayList<>();
		}
		return DtoAssembler.toSummaryDtoList(Factory.repository.forContract().findByMechanicId(id));
	}	

}
