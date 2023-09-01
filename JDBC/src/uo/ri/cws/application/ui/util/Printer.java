package uo.ri.cws.application.ui.util;

import java.util.List;
import java.util.Optional;

import console.Console;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;

public class Printer {

    public static void printMechanic(MechanicBLDto m) {
	Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s %-10.2s\n", m.id,
		m.dni, m.name, m.surname, m.version);
    }

    public static void printMechanics(List<MechanicBLDto> list) {

	Console.printf("\t%-36s %-9s %-10s %-25s %-10s\n",
		"Mechanic identifier", "DNI", "Name", "Surname", "Version");
	for (MechanicBLDto m : list)
	    printMechanic(m);
    }

    public static void printInvoice(InvoiceBLDto invoice) {

	double importeConIVa = invoice.total;
	double iva = invoice.vat;
	double importeSinIva = importeConIVa / (1 + iva / 100);

	Console.printf("Invoice number: %d%n", invoice.number);
	Console.printf("\tDate: %1$td/%1$tm/%1$tY%n", invoice.date);
	Console.printf("\tAmount: %.2f %n", importeSinIva);
	Console.printf("\tVat: %.1f %% %n", invoice.vat);
	Console.printf("\tTotal (vat included): %.2f %n", invoice.total);
    }

    public static void printInvoicingWorkOrder(WorkOrderForInvoicingBLDto arg) {

	Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n", arg.id,
		arg.description, arg.date, arg.state, arg.total);
    }

    public static void printInvoicingWorkOrders(
	    List<WorkOrderForInvoicingBLDto> arg) {
	Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n", "Identifier",
		"description", "state", "total");
	for (WorkOrderForInvoicingBLDto inv : arg)
	    printInvoicingWorkOrder(inv);
    }

	public static void printContractType(Optional<ContractTypeBLDto> c) {
		Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s\n", "Identifier",
				"compensationDays", "name", "version");
		Console.printf("\t%-36.36s %-9s \t      %-10.10s %-25.25s\n", c.get().id,
				c.get().compensationDays, c.get().name, c.get().version);
	}
	
	public static void printContractTypeList(List<ContractTypeBLDto> findAllContractTypes) {
		Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s\n", "Identifier",
				"compensationDays", "name", "version");
		for (int i=0; i < findAllContractTypes.size(); i++) {
			ContractTypeBLDto c = findAllContractTypes.get(i);
			Console.printf("\t%-36.36s %-9s \t      %-10.10s %-25.25s\n", c.id,
					c.compensationDays, c.name, c.version);
		}
		
	}

	public static void displayAllContracts(List<ContractBLDto> lista) {
		Console.printf("\t%s     \t\t\t%s \t%s \t%s \t%s \t%s \t%s \t%s\n", "Identifier",
				"annualBaseWage", "contractTypeName", "dni", "proffesionalGroupName", "startDate", "endDate", "version");
		for (int i=0; i < lista.size(); i++) {
			ContractBLDto contractBLDto = lista.get(i);
			printContract(contractBLDto);
		}
	}
	
	public static void displayThisContractDetails() {
		Console.printf("\t%s \t\t\t\t%s \t\t%s \t%s \t%s \t%s \t\t%s   \t%s\n", "Identifier",
				"annualBaseWage", "contractTypeName", "dni", "professionalGroupName", "startDate", "endDate", "version");
	}

	public static void printContract(ContractBLDto contractBLDto) {
		Console.printf("\t%s \t%f \t\t%s \t\t%s \t%s \t\t%s   \t\t%s \t%d\n", contractBLDto.id,
				contractBLDto.annualBaseWage, contractBLDto.contractTypeName, contractBLDto.dni, contractBLDto.professionalGroupName,
				contractBLDto.startDate, contractBLDto.endDate, contractBLDto.version);
	}

	public static void displayAllContractsDetailsWithPayrolls(List<ContractSummaryBLDto> result) {
		Console.printf("\t%s \t\t\t\t%s \t\t%s \t%s \t%s \t%s\n", "Identifier",
				"dni", "numPayRolls", "settlement", "state", "version");
		for (int i=0; i < result.size(); i++) {
			ContractSummaryBLDto c = result.get(i);
			if (result.get(i).state.equals(ContractState.TERMINATED)) {
				Console.printf("\t%s \t%s \t0 \t\t\t%s \t%s\n", c.id,
						c.dni, c.state, c.version);
			} else {
				Console.printf("\t%s \t%s \t%s \t\t\t%s \t%s\n", c.id,
						c.dni, c.numPayrolls, c.settlement, c.state, c.version);
			}
		}
	}

	public static void printProfessionalGroup(Optional<ProfessionalGroupBLDto> result) {
		// TODO Auto-generated method stub
		
	}

	public static void printPayrollsSummary(Optional<PayrollSummaryBLDto> result) {
		// TODO Auto-generated method stub
		
	}

	public static void printPayrolls(Optional<PayrollSummaryBLDto> result) {
		// TODO Auto-generated method stub
		
	}

	public static void printPayrollsSummary(List<PayrollSummaryBLDto> result) {
		// TODO Auto-generated method stub
		
	}

	
	


}