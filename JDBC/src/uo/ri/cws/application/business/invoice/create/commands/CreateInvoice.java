package uo.ri.cws.application.business.invoice.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import assertion.Argument;
import math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto.InvoiceState;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.business.workorder.WorkOrderService.WorkOrderBLDto;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.assembler.InvoiceAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;
import uo.ri.cws.application.persistence.workorder.assembler.WorkOrderAssembler;


public class CreateInvoice implements Command<InvoiceBLDto> {
    
    private List<String> workOrderIds;

    public CreateInvoice(List<String> workOrderIds) {
    	Argument.isNotNull(workOrderIds);
		Argument.isTrue(!workOrderIds.isEmpty());
		for (String id : workOrderIds) {
			Argument.isNotEmpty(id);
		}
		
    	this.workOrderIds = workOrderIds;
    }

    
    public InvoiceBLDto execute() throws BusinessException {
    	InvoiceBLDto i = new InvoiceBLDto();
    	
    	if (!checkWorkOrdersExist(workOrderIds))
			throw new BusinessException("Workorder does not exist");
		if (!checkWorkOrdersFinished(workOrderIds))
			throw new BusinessException("Workorder is not finished yet");
		
	    long numberInvoice = generateInvoiceNumber();
	    LocalDate dateInvoice = LocalDate.now();
	    double amount = calculateTotalInvoice(workOrderIds); // vat not
								 // included
	    double vat = vatPercentage(amount, dateInvoice);
	    double total = amount * (1 + vat / 100); // vat included
	    total = Round.twoCents(total);
	    
	    i.id=UUID.randomUUID().toString();
	    i.version=1L;
	    i.total=total;
	    i.vat = vat;
	    i.number=numberInvoice;
	    i.date=dateInvoice;
	    i.state=InvoiceState.NOT_YET_PAID;
	    
	    String idInvoice = createInvoice(i);
	    linkWorkordersToInvoice(idInvoice, workOrderIds);
	    markWorkOrderAsInvoiced(workOrderIds);
	    updateVersion(workOrderIds);
	    
	    return i;
    }

    private void updateVersion(List<String> workOrderIds) {
    	for (int i=0; i < workOrderIds.size(); i++) {
    		WorkOrderBLDto w = uo.ri.cws.application.business.workorder.assembler.WorkOrderAssembler.toWorkOrderDto(PersistenceFactory.forWorkOrder().findById(workOrderIds.get(i)).get());
    		w.version = w.version + 1;
    		PersistenceFactory.forWorkOrder().update(WorkOrderAssembler.toWorkOrderDALDto(w));
    	}
    }

    /*
     * checks whether every work order exist
     */
    private boolean checkWorkOrdersExist(List<String> workOrderIDS){
    	for (String e: workOrderIDS) {
    		if (PersistenceFactory.forWorkOrder().findById(e).isEmpty()) {
    			return false;
    		}
    	}
    	return true;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private boolean checkWorkOrdersFinished(List<String> workOrderIDS) {
    	List<WorkOrderDALDto> w = new ArrayList<>();
    	
    	for (int i=0; i < workOrderIDS.size(); i++) {
    		w.add(PersistenceFactory.forWorkOrder().findById(workOrderIDS.get(i)).get());
    	}
    	
	    for (WorkOrderDALDto workOrderID : w) {
	    	if (!"FINISHED".equalsIgnoreCase(workOrderID.state)) {
	    		return false;
	    	}

	    }
	    return true;
    }

    /*
     * Generates next invoice number (not to be confused with the inner id)
     */
    private Long generateInvoiceNumber() {
    	return PersistenceFactory.forInvoice().getNextInvoiceNumber();
    }

    /*
     * Compute total amount of the invoice (as the total of individual work
     * orders' amount
     */
    private double calculateTotalInvoice(List<String> workOrderIDS) throws BusinessException {

		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
		    totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
    }

    /*
     * checks whether every work order id is FINISHED
     */
    private Double getWorkOrderTotal(String workOrderID) throws BusinessException {
		Double money = 0.0;
		
		Optional<WorkOrderDALDto> op = PersistenceFactory.forWorkOrder().findById(workOrderID);
		if (op.isEmpty()) {
			throw new BusinessException(
					"Workorder " + workOrderID + " doesn't exist");
	    } else {
	    	money = op.get().amount;
	    }
		
		return money;
    }

    /*
     * returns vat percentage
     */
    private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
	return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0
		: 18.0;

    }

    /*
     * Creates the invoice in the database; returns the id
     */
    private String createInvoice(InvoiceBLDto i) {
    	PersistenceFactory.forInvoice().add(InvoiceAssembler.toInvoiceDALDto(i));
    	return i.id;
	}

    /*
     * Set the invoice number field in work order table to the invoice number
     * generated
     */
    private void linkWorkordersToInvoice(String invoiceId, List<String> workOrderIDS) { 
    	for (int i=0; i < workOrderIDS.size(); i++) {
    		WorkOrderBLDto w = uo.ri.cws.application.business.workorder.assembler.WorkOrderAssembler.toWorkOrderDto(PersistenceFactory.forWorkOrder().findById(workOrderIDS.get(i)).get());
    		w.invoiceId=invoiceId;
    		PersistenceFactory.forWorkOrder().update(WorkOrderAssembler.toWorkOrderDALDto(w));
    	}
    }

    /*
     * Sets state to INVOICED for every workorder
     */
    private void markWorkOrderAsInvoiced(List<String> ids) {
    	for (int i=0; i < ids.size(); i++) {
    		WorkOrderBLDto w = uo.ri.cws.application.business.workorder.assembler.WorkOrderAssembler.toWorkOrderDto(PersistenceFactory.forWorkOrder().findById(ids.get(i)).get());
    		w.state="INVOICED";
    	    PersistenceFactory.forWorkOrder().update(WorkOrderAssembler.toWorkOrderDALDto(w));
    	}
    }

}
