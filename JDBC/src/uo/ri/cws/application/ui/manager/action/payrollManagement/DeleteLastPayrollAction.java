package uo.ri.cws.application.ui.manager.action.payrollManagement;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;

public class DeleteLastPayrollAction implements Action {

	@Override
	public void execute() throws BusinessException {
		

		
		// Print result
		Console.println( "Last payroll successfully deleted" );		
	}

}
