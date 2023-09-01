package uo.ri.cws.application.business.contracttype.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService;
import uo.ri.cws.application.business.contracttype.crud.commands.AddContractType;
import uo.ri.cws.application.business.contracttype.crud.commands.DeleteContractType;
import uo.ri.cws.application.business.contracttype.crud.commands.ListAllContractType;
import uo.ri.cws.application.business.contracttype.crud.commands.ListContractTypeByName;
import uo.ri.cws.application.business.contracttype.crud.commands.UpdateContractType;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class ContractTypeServiceImpl implements ContractTypeService {

	@Override
	public ContractTypeBLDto addContractType(ContractTypeBLDto dto) throws BusinessException {
		return new CommandExecutor().execute(new AddContractType(dto));
	}

	@Override
	public void deleteContractType(String name) throws BusinessException {
		new CommandExecutor().execute(new DeleteContractType(name));
	}

	@Override
	public void updateContractType(ContractTypeBLDto dto) throws BusinessException {
		new CommandExecutor().execute(new UpdateContractType(dto)); 
	}

	@Override
	public Optional<ContractTypeBLDto> findContractTypeByName(String name) throws BusinessException {
		return new CommandExecutor().execute(new ListContractTypeByName(name));
	}

	@Override
	public List<ContractTypeBLDto> findAllContractTypes() throws BusinessException {
		return new CommandExecutor().execute(new ListAllContractType());
	}

}
