package uo.ri.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.mechanic.MechanicService;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

public class ListMechanicsInProfessionalGroupsAction implements Action {

	@Override
	public void execute() throws Exception {

		String grupo = Console.readString("professional group name");
		MechanicService as = Factory.service.forMechanicService();
		
		List<MechanicDto> mechanics = as.findMechanicsInProfessionalGroups(grupo);
		
		Console.println("\nList of mechanics in professional group\n");
		mechanics.forEach( m ->
			Printer.printMechanic( m )
		);
	}

}
