package uo.ri.ui.manager.contractType.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class DeleteContractTypeAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Get info
		String name = Console.readString("Name type contract"); 
		
		ContractTypeService as = Factory.service.forContractTypeService();
		as.deleteContractType(name);
				
		// Print result
		Console.println("Contract type succesfully deleted");
	}
}
