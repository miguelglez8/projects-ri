package uo.ri.ui.manager.contract.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListContractByMechanicAction implements Action  {

	@Override
	public void execute() throws BusinessException {
		displayAllMechanics();
		displayAllContracts();
		
		String idM = Console.readString("Type mechanic id ");
		
		List<ContractSummaryDto> result = Factory.service.forContractService().findContractsByMechanic(idM);
		if (!result.isEmpty())
			Printer.printContractSummary(result);
		else
			Console.println("There are no terminated contracts for a mechanic with this id");
	}
	
	private void displayAllMechanics() throws BusinessException {
		Console.println("Lista de mecánicos");
		Factory.service.forMechanicService().findAllMechanics().forEach( m ->
			Printer.printMechanic( m )
		);
	}

	private void displayAllContracts() throws BusinessException {
		Console.println("Lista de contratos");
		Printer.printContractSummary(Factory.service.forContractService().findAllContracts());
	}

}
