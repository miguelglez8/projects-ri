package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class ListContractTypeByName implements Command<Optional<ContractTypeDto>> {
	
	private String name;

	public ListContractTypeByName(String name) {
		ArgumentChecks.isNotEmpty(name);
		ArgumentChecks.isNotBlank(name);
		
		this.name=name;
	}

	@Override
	public Optional<ContractTypeDto> execute() throws BusinessException {
		Optional<ContractType> om = Factory.repository.forContractType().findByName(name);
		
		return DtoAssembler.toContractTypeDto(om);
	}
	

}
