package uo.ri.ui.manager.contract.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteContractAction implements Action {

	@Override
	public void execute() throws BusinessException {
		displayAllContracts();

		String idContract = Console.readString("Contract identifier ");

		Factory.service.forContractService().deleteContract(idContract);

		Console.println("Contract no longer exists");
	}

	private void displayAllContracts() throws BusinessException {
		Printer.printContractSummary(Factory.service.forContractService().findAllContracts());
	}
}
