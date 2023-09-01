package uo.ri.cws.application.service.contract.crud.commands;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.cws.application.service.contract.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;

public class ListAllContract implements Command<List<ContractSummaryDto>> {
	
	public List<ContractSummaryDto> execute() throws BusinessException {
		List<Contract> list = Factory.repository.forContract().findAll();
		return DtoAssembler.toSummaryDtoList(list);
	}
	
}
