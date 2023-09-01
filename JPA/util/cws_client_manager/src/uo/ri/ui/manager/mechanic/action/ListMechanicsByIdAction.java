package uo.ri.ui.manager.mechanic.action;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicService;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListMechanicsByIdAction implements Action {

	@Override
	public void execute() throws BusinessException {
		
		String id = Console.readString("mechanic id");
		MechanicService as = Factory.service.forMechanicService();
		
		Optional<MechanicDto> mechanic = as.findMechanicById(id);
		
		if (mechanic.isPresent()) {
			Printer.printMechanic( mechanic.get() );
		} else {
			Console.println("Dont exist mechanic with id " + id);
		}
	}

}
