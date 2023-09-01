package uo.ri.ui.manager.contract.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListContractByIdAction implements Action  {

	@Override
	public void execute() throws BusinessException {
		displayAllContracts();

		String id = Console.readString("Type id");

		Optional<ContractDto> opContract = Factory.service.forContractService().findContractById(id);

		if (opContract.isPresent()) {
			Printer.printContract(opContract.get());
		}else {
			Console.println("There is no contract with this id");
		}
	}
	
	private void displayAllContracts() throws BusinessException {
		Printer.printContractSummary(Factory.service.forContractService().findAllContracts());
	}
}
