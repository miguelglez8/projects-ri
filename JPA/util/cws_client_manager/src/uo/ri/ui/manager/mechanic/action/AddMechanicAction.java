package uo.ri.ui.manager.mechanic.action;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicService;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class AddMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Ask the user for data
		MechanicDto m = new MechanicDto();
		m.dni = Console.readString("Dni");
		m.name = Console.readString("Name");
		m.surname = Console.readString("Surname");

		// Invoke the service
		MechanicService as = Factory.service.forMechanicService();
		m = as.addMechanic( m );

		// Show result
		Console.println("New mechanic added: " + m.id);
	}

}
