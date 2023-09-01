package uo.ri.ui.util;

import java.util.List;

import uo.ri.cws.application.service.contract.ContractService.ContractDto;
import uo.ri.cws.application.service.contract.ContractService.ContractSummaryDto;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.cws.application.service.vehicleType.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.util.console.Console;

public class Printer {

	public static void printInvoice(InvoiceDto invoice) {

		double importeConIVa = invoice.total;
		double iva =  invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Invoice #: %d\n", 				invoice.number );
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", 	invoice.date);
		Console.printf("\tTotal: %.2f €\n", 			importeSinIva);
		Console.printf("\tTax: %.1f %% \n", 			invoice.vat );
		Console.printf("\tTotal, tax inc.: %.2f €\n", 	invoice.total );
		Console.printf("\tStatus: %s\n", 				invoice.state );
	}

	public static void printPaymentMeans(List<PaymentMeanDto> medios) {
		Console.println();
		Console.println("Available payment means");

		Console.printf("\t%s \t%-8.8s \t%s \n", "Id", "Type", "Acummulated");
		for (PaymentMeanDto medio : medios) {
			printPaymentMean( medio );
		}
	}

	private static void printPaymentMean(PaymentMeanDto medio) {
		Console.printf("\t%s \t%-8.8s \t%s \n"
				, medio.id
				, medio.getClass().getName()  // not the best...
				, medio.accumulated
		);
	}

	public static void printWorkOrder(WorkOrderDto rep) {

		Console.printf("\t%s \t%-40.40s \t%td/%<tm/%<tY \t%-12.12s \t%.2f\n"
				, rep.id
				, rep.description
				, rep.date
				, rep.state
				, rep.total
		);
	}

	public static void printMechanic(MechanicDto m) {

		Console.printf("\t%s %-10.10s %-15.15s %-25.25s\n"
				, m.id
				, m.dni
				, m.name
				, m.surname
			);
	}

	public static void printVehicleType(VehicleTypeDto vt) {

		Console.printf("\t%s %-10.10s %5.2f %d\n"
				, vt.id
				, vt.name
				, vt.pricePerHour
				, vt.minTrainigHours
			);
	}
	
	public static void printContractType(ContractTypeDto c) {
		
		Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s\n"
				, c.id
				, c.compensationDays
				, c.name
				, c.version
			);
	}
	
	public static void printContract(ContractDto contractBLDto) {
		
		Console.printf("\t%s \t%f \t%s \t%s \t%s \t%s \t%s \t%d\n"
				, contractBLDto.id
				, contractBLDto.annualBaseWage
				, contractBLDto.contractTypeName
				, contractBLDto.dni
				, contractBLDto.professionalGroupName
				, contractBLDto.startDate
				, contractBLDto.endDate
				, contractBLDto.version);
	}

	public static void printContractSummary(List<ContractSummaryDto> result) {
		
		for (int i=0; i < result.size(); i++) {
			
			ContractSummaryDto c = result.get(i);
			
				Console.printf("\t%s \t%s \t%s \t%s \t%s\n"
						, c.id
						, c.dni
						, c.numPayrolls
						, c.settlement
						, c.state
						, c.version);
				
		}
	}


}
