package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.service.contracttype.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;


public class ListAllContractType implements Command<List<ContractTypeDto>> {

	@Override
	public List<ContractTypeDto> execute() throws BusinessException {
		List<ContractType> om = Factory.repository.forContractType().findAll();
		
		return DtoAssembler.toContractTypeDtoList(om);
	}
	
	
}
