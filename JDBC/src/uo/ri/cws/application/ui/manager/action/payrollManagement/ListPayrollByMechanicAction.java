package uo.ri.cws.application.ui.manager.action.payrollManagement;

import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class ListPayrollByMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		// Get info
		String id = Console.readString("Mechanic id  ");

		// Process



		// Print result
		Console.println(String.format("Payrolls for mechanic %s", id));
		
		Optional<PayrollSummaryBLDto> result = null;
		Printer.printPayrollsSummary(result);

	}

}
