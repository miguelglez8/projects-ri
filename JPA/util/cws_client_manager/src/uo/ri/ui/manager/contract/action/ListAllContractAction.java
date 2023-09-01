package uo.ri.ui.manager.contract.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListAllContractAction implements Action  {

	@Override
	public void execute() throws BusinessException {
		List<ContractSummaryDto> result = Factory.service.forContractService().findAllContracts();
		if (result.isEmpty()) {
			Console.println("No contracts");
		} else {
			Printer.printContractSummary(result);
		}
	}

}
