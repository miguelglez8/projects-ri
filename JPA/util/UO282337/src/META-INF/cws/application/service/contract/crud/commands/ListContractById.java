package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.service.contract.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.util.assertion.ArgumentChecks;

public class ListContractById implements Command<Optional<ContractDto>>{
	private String id;
	
	public ListContractById(String id) {
		ArgumentChecks.isNotEmpty(id);
		
		this.id = id;
	}

	public Optional<ContractDto> execute() throws BusinessException {
		Optional<Contract> c = Factory.repository.forContract().findById(id);
		if (c.isEmpty()) {
			return Optional.empty();
		}
		return DtoAssembler.toDto(c.get());
	}

	
	
}
