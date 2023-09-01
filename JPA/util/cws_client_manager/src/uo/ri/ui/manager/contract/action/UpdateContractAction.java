package uo.ri.ui.manager.contract.action;

import java.time.LocalDate;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class UpdateContractAction implements Action {

	@Override
	public void execute() throws BusinessException {

		displayAllContracts();
		String id = Console.readString("Type contract id ");
		
		ContractService as = Factory.service.forContractService();
		Optional<ContractDto> res = as.findContractById(id);
		if (! res.isPresent()) {
			throw new BusinessException("There is no contract type with that name");
		}

		ContractDto cdto = res.get();
		Printer.printContract(cdto);
		
		LocalDate endD = Console.readDate("Type contract start date ");
		double salary = Console.readDouble("Type annual base wage ");
		
		cdto.endDate = endD;
		cdto.annualBaseWage = salary;

		as.updateContract(cdto);

		Console.println("Contract updated");
	}

	private void displayAllContracts() throws BusinessException {
		Printer.printContractSummary(Factory.service.forContractService().findAllContracts());
	}

}
