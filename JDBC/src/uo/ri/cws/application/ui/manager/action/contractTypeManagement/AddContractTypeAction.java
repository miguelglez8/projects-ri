package uo.ri.cws.application.ui.manager.action.contractTypeManagement;

import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class AddContractTypeAction implements Action {
	
	@Override
	public void execute() throws BusinessException {
		
		// Get info
		String name = Console.readString("Name "); 
		Double compensationDays = Console.readDouble("Compensation days ");
		
		// Process
		
		ContractTypeBLDto ct = new ContractTypeBLDto();
		ct.compensationDays = compensationDays;
		ct.name = name;
		
				
		
		
		Optional<ContractTypeBLDto> result = Optional.of(BusinessFactory.forContractTypeService().addContractType(ct));
		Printer.printContractType(result);
		
		// Print result
		if (result.isEmpty()) {
			Console.println("New contract type succesfully added");
		}
	}
}
