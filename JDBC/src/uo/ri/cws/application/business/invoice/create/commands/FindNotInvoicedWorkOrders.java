package uo.ri.cws.application.business.invoice.create.commands;

import java.util.ArrayList;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.client.ClientService.ClientBLDto;
import uo.ri.cws.application.business.client.assembler.ClientAssembler;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.invoice.assembler.InvoicingAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.business.vehicle.VehicleService.VehicleBLDto;
import uo.ri.cws.application.business.vehicle.assembler.VehicleAssembler;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class FindNotInvoicedWorkOrders implements Command<List<WorkOrderForInvoicingBLDto>> {

    /**
     * Process:
     * 
     * - Ask customer dni
     * 
     * - Display all uncharged workorder (state <> 'INVOICED'). For each
     * workorder, display id, vehicle id, date, state, amount and description
     */

    private String dni;

    public FindNotInvoicedWorkOrders(String dni) {
    	Argument.isNotEmpty(dni);
    	
    	this.dni = dni;
    }

    public List<WorkOrderForInvoicingBLDto> execute() throws BusinessException {
    	if (PersistenceFactory.forClient().findByDni(dni).isEmpty()) {
    		throw new BusinessException("Client dont exist");
    	}
    	
    	ClientBLDto c = ClientAssembler.toClientDto(PersistenceFactory.forClient().findByDni(dni)).get();
    	    	
    	List<VehicleBLDto> vehiculos = VehicleAssembler.toVehicleDtoList(PersistenceFactory.forVehicle().findByClient(c.id));
    	
    	List<String> idsVehicles = new ArrayList<>();
    	for (int i=0; i < vehiculos.size(); i++) {
    		idsVehicles.add(vehiculos.get(i).id);
    	}
    	
    	List<WorkOrderForInvoicingBLDto> result = InvoicingAssembler.toWorkOrderForInvoicingDtoList(PersistenceFactory.forWorkOrder().findNotInvoicedForVehicles(idsVehicles));
    	
    	return result;
    }
    
}
