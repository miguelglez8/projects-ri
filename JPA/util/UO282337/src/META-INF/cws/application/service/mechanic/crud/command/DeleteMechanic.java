package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteMechanic implements Command<Void>{

	private String mechanicId;

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotEmpty(mechanicId);
		ArgumentChecks.isNotBlank(mechanicId);

		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {
		Optional<Mechanic> op = Factory.repository.forMechanic().findById(mechanicId);
		BusinessChecks.exists(op, "El mecánico no existe");
		
		checkDelete(op.get());
		
		Factory.repository.forMechanic().remove(op.get());
				
		return null;
	}

	private void checkDelete(Mechanic mechanic) throws BusinessException {
		BusinessChecks.isTrue(mechanic.getAssigned().isEmpty(),
				"El mecánico tiene workOrders");
		BusinessChecks.isTrue(mechanic.getTerminatedContracts().isEmpty(),
				"El mecánico tiene contratos terminados");
		BusinessChecks.isTrue(mechanic.getInterventions().isEmpty(),
				"El mecánico tiene intervenciones");
		if (mechanic.getContractInForce().isPresent())
			BusinessChecks.isTrue(! mechanic.getContractInForce().get().getState().equals(ContractState.IN_FORCE),
				"El mecánico tiene contrato en vigor");
	}

}
