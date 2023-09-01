package uo.ri.cws.application.business.contracttype.crud.commands;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class ListAllContractType implements Command<List<ContractTypeBLDto>> {

	@Override
	public List<ContractTypeBLDto> execute() throws BusinessException {
		return ContractTypeAssembler.toContractTypeBLDtoList(PersistenceFactory.forContractType().findAll());
	}
	
	
}
