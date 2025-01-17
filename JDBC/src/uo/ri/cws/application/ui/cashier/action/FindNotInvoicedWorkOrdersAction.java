package uo.ri.cws.application.ui.cashier.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.ui.util.Printer;

public class FindNotInvoicedWorkOrdersAction implements Action {

    @Override
    public void execute() throws BusinessException {
	String dni = Console.readString("Client DNI ");

	Console.println("\nClient's not invoiced work orders\n");

	Printer.printInvoicingWorkOrders(BusinessFactory.forInvoicingService()
		.findNotInvoicedWorkOrdersByClientDni(dni));
    }

}