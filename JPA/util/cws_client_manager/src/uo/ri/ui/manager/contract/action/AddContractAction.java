package uo.ri.ui.manager.contract.action;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;


public class AddContractAction implements Action {
	
	@Override
	public void execute() throws BusinessException {
		
		String mechanicdNI = Console.readString("Mechanic DNI ");
		LocalDate startDate = getStartOfNextMonth();
		Console.println("Contract type");
		Console.println("PERMANENT \t SEASONAL \t FIXED_TERM");
		String typeName = Console.readString("Contract type name ");
		LocalDate endDate = null;
		if (typeName.toUpperCase().compareTo("PERMANENT") != 0) {
			endDate = Console.readDate("Type end date ");			
		}

		Console.println("Professional group");
		Console.println("I \t II \t III \t IV \t V \t VI \t VII");
		String categoryName = Console.readString("Professional group name ");
		double yearBaseSalary = Console.readDouble("Annual base salary");

		ContractDto c = new ContractDto();
		c.dni=mechanicdNI;
		c.startDate=startDate;
		c.contractTypeName=typeName;
		c.endDate=endDate;
		c.professionalGroupName=categoryName;
		c.annualBaseWage=yearBaseSalary;
		
		ContractService as = Factory.service.forContractService();
		c = as.addContract(c);
		
		Console.println("Contract registered");
		Printer.printContract(c);
	}


	public static LocalDate getStartOfNextMonth() {
	    return LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());
	}
	

}
