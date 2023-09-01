package uo.ri.cws.application.ui.manager.action.professionalGroupManagement;

import console.Console;
import menu.Action;

public class DeleteProfessionalGroupAction implements Action {

	@Override
	public void execute() throws Exception {
		
		@SuppressWarnings("unused")
		String name = Console.readString("Professional group name ");
		


		Console.print("Professional group successfully deleted");		
	}

}
