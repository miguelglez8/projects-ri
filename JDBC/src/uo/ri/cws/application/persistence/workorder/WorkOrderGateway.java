package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface WorkOrderGateway extends Gateway<uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto> {
	List<WorkOrderDALDto> findByMechanic(String id);
	List<WorkOrderDALDto> findNotInvoicedForVehicles(List<String> vehicleIds) ;
	List<WorkOrderDALDto> findByVehicleId(String vehicleId);
	List<WorkOrderDALDto> findByIds(List<String> arg);
	List<WorkOrderDALDto> findByInvoice(String id) ;
	List<WorkOrderDALDto> findInvoiced();
	List<WorkOrderDALDto> findNotInvoicedWorkOrders();

	public class WorkOrderDALDto {
		public String id;
		public Long version;
		
		public Double amount; 
		public LocalDateTime date;
		public String description;
		public String state;
		public String invoice_id;
		public String mechanic_id;
		public String vehicle_id;
		public Boolean usedforvoucher;
	}
	
}
