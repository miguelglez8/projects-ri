package uo.ri.cws.application.business.workorder.assembler;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.workorder.WorkOrderService.WorkOrderBLDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

public class WorkOrderAssembler {
	
	public static WorkOrderBLDto toWorkOrderDto(WorkOrderDALDto vr) {
		WorkOrderBLDto wo = new WorkOrderBLDto();
		
		wo.date = vr.date;
		wo.description = vr.description;
		wo.id = vr.id;
		wo.invoiceId = vr.invoice_id;
		wo.mechanicId = vr.mechanic_id;
		wo.state = vr.state;
		wo.total = vr.amount;
		wo.vehicleId = vr.vehicle_id;
		wo.version = vr.version;
		
		return wo;	
	}

	public static List<WorkOrderBLDto> toWorkOrderDtoList(
			List<WorkOrderDALDto> findByMechanic) {
		List<WorkOrderBLDto> l = new ArrayList<>();
		for (int i=0; i < findByMechanic.size(); i++) {
			l.add(toWorkOrderDto(findByMechanic.get(i)));
		}
		return l;
	}

}
