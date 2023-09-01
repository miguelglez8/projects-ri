package uo.ri.ui.manager.contractType.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class UpdateContractTypeAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Get info
		String name = Console.readString("Name "); 
		
		// Process
		ContractTypeService as = Factory.service.forContractTypeService();
		Optional<ContractTypeDto> res = as.findContractTypeByName(name);
		if (! res.isPresent()) {
			throw new BusinessException("There is no contract type with that name");
		}
		ContractTypeDto c = res.get();
		Printer.printContractType(c);
		
		// Ask for new data
		Double compensationDays = Console.readDouble("Compensation days ");

		c.compensationDays = compensationDays;
		
		// Update
		as.updateContractType(c);
		
		// Show the result
		Console.println("Contract type succesfully updated");
	}

}
