package uo.ri.cws.application.ui.manager.action.payrollManagement;

import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class ListPayrollDetailAction implements Action {

	@Override
	public void execute() throws BusinessException {
		// Get info
		String id = Console.readString("Payroll id  ");

		// Process




		// Print result
		Console.println(String.format("Details Payroll %s", id));
		
		Optional<PayrollSummaryBLDto> result = null;
		Printer.printPayrolls(result);
	}

}
