package uo.ri.cws.application.service.contract.crud.commands;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.util.assertion.ArgumentChecks;


public class FinishContract implements Command<Void>{
	
private String contractId;
	
	public FinishContract(String contractId) {
		ArgumentChecks.isNotEmpty(contractId);
		ArgumentChecks.isNotBlank(contractId);

		this.contractId = contractId;
	}

	@Override
	public Void execute() throws BusinessException {
		Optional<Contract> c = Factory.repository.forContract().findById(contractId);
		BusinessChecks.isTrue(c.isPresent(), "El contrato no existe");
		
		BusinessChecks.isTrue(c.get().getState().equals(ContractState.IN_FORCE), "El contrato debe de estar en vigor");
		
		c.get().terminate();
		
		return null;
	}
}
