package uo.ri.ui.manager.contract.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class FinishContractAction implements Action {

	@Override
	public void execute() throws Exception {
		displayAllContracts();
		String id = Console.readString("Type contract identifier");
		
		Factory.service.forContractService().terminateContract(id);
		
		Console.println("Contract is terminated");
	}
	
	private void displayAllContracts() throws BusinessException {
		Printer.printContractSummary(Factory.service.forContractService().findAllContracts());
	}

}
