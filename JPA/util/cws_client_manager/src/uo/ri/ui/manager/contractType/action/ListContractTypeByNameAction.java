package uo.ri.ui.manager.contractType.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListContractTypeByNameAction implements Action  {

	@Override
	public void execute() throws BusinessException {
		String name = Console.readString("Contract type name ");
		ContractTypeService as = Factory.service.forContractTypeService();
		
		Optional<ContractTypeDto> ct = as.findContractTypeByName(name);

		if (ct.isPresent()) {
			Printer.printContractType( ct.get() );
		} else {
			Console.println("Dont exist contract type with name " + name);
		}
	}

}
