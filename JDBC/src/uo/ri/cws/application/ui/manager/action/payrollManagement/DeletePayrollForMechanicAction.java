package uo.ri.cws.application.ui.manager.action.payrollManagement;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;

public class DeletePayrollForMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {

		// Get info
		String id = Console.readString("Mechanic id "); 


				
		// Print result
		Console.println(String.format("Last payroll for mechanic %s successfully deleted", id));
	}

}
