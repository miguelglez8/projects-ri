package uo.ri.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicService;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListMechanicsWithContractInForceInContractTypeAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		String tipo = Console.readString("contract type name");
		MechanicService as = Factory.service.forMechanicService();
		
		List<MechanicDto> mechanics = as.findMechanicsWithContractInForceInContractType(tipo);
		
		Console.println("\nList of mechanics in force in contract type\n");
		mechanics.forEach( m ->
			Printer.printMechanic( m )
		);
	}

}
