package uo.ri.cws.application.service.contract.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.service.contract.crud.commands.AddContract;
import uo.ri.cws.application.service.contract.crud.commands.DeleteContract;
import uo.ri.cws.application.service.contract.crud.commands.FinishContract;
import uo.ri.cws.application.service.contract.crud.commands.ListAllContract;
import uo.ri.cws.application.service.contract.crud.commands.ListContractById;
import uo.ri.cws.application.service.contract.crud.commands.ListContractsByMechanic;
import uo.ri.cws.application.service.contract.crud.commands.UpdateContract;
import uo.ri.cws.application.util.command.CommandExecutor;

public class ContractServiceImpl implements ContractService {
	
	private CommandExecutor executor = Factory.executor.forExecutor();
	
	@Override
	public ContractDto addContract(ContractDto c) throws BusinessException {
		return executor.execute(new AddContract( c ));
	}

	@Override
	public void updateContract(ContractDto dto) throws BusinessException {
		executor.execute(new UpdateContract( dto ));
	}

	@Override
	public void deleteContract(String id) throws BusinessException {
		executor.execute(new DeleteContract( id ));
	}

	@Override
	public void terminateContract(String contractId) throws BusinessException {
		executor.execute(new FinishContract( contractId ));
	}

	@Override
	public Optional<ContractDto> findContractById(String id) throws BusinessException {
		return executor.execute(new ListContractById( id ));
	}

	@Override
	public List<ContractSummaryDto> findContractsByMechanic(String mechanicDni) throws BusinessException {
		return executor.execute(new ListContractsByMechanic( mechanicDni ));
	}

	@Override
	public List<ContractSummaryDto> findAllContracts() throws BusinessException {
		return executor.execute(new ListAllContract());
	}

}
