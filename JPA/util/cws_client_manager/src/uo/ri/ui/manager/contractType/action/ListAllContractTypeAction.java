package uo.ri.ui.manager.contractType.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListAllContractTypeAction implements Action  {

	@Override
	public void execute() throws BusinessException {
		ContractTypeService as = Factory.service.forContractTypeService();
		
		List<ContractTypeDto> types = as.findAllContractTypes();

		Console.println("\nList of contract types\n");
		types.forEach( m ->
			Printer.printContractType( m )
		);
	}

}
