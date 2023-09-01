package uo.ri.ui.manager.contractType.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;


public class AddContractTypeAction implements Action {
	
	@Override
	public void execute() throws BusinessException {
		
		// Get info
		String name = Console.readString("Name "); 
		Double compensationDays = Console.readDouble("Compensation days ");
		
		// Process
		ContractTypeDto ct = new ContractTypeDto();
		ct.compensationDays = compensationDays;
		ct.name = name;
		
		// Invoke the service
		ContractTypeService as = Factory.service.forContractTypeService();
		ct = as.addContractType(ct);
		
		// Show result
		Console.println("New contract type added: " + ct.id);
	}
}
