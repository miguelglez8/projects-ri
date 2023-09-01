package uo.ri.cws.application.business.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService;
import uo.ri.cws.application.business.contract.crud.commands.AddContract;
import uo.ri.cws.application.business.contract.crud.commands.DeleteContract;
import uo.ri.cws.application.business.contract.crud.commands.FinishContract;
import uo.ri.cws.application.business.contract.crud.commands.ListAllContract;
import uo.ri.cws.application.business.contract.crud.commands.ListContractById;
import uo.ri.cws.application.business.contract.crud.commands.ListContractsByMechanicDni;
import uo.ri.cws.application.business.contract.crud.commands.UpdateContract;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class ContractServiceImpl implements ContractService {

	@Override
	public ContractBLDto add(ContractBLDto c) throws BusinessException {
		return new CommandExecutor().execute(new AddContract(c));
	}

	@Override
	public void updateContract(ContractBLDto dto) throws BusinessException {
		new CommandExecutor().execute(new UpdateContract(dto));
	}

	@Override
	public void deleteContract(String id) throws BusinessException {
		new CommandExecutor().execute(new DeleteContract(id));
	}

	@Override
	public void terminateContract(String contractId) throws BusinessException {
		new CommandExecutor().execute(new FinishContract(contractId));
	}

	@Override
	public Optional<ContractBLDto> findContractById(String id) throws BusinessException {
		return new CommandExecutor().execute(new ListContractById(id));
	}

	@Override
	public List<ContractSummaryBLDto> findContractsByMechanic(String mechanicDni) throws BusinessException {
		return new CommandExecutor().execute(new ListContractsByMechanicDni(mechanicDni));
	}

	@Override
	public List<ContractSummaryBLDto> findAllContracts() throws BusinessException {
		return new CommandExecutor().execute(new ListAllContract());
	}

}
