package uo.ri.cws.application.persistence.workorder.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.workorder.WorkOrderService.WorkOrderBLDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

public class WorkOrderAssembler {
	
	public static Optional<WorkOrderDALDto> toWorkOrderDALDto ( ResultSet rs ) throws SQLException {
		WorkOrderDALDto record = null;
		
		if (rs.next()) {
			record = resultSetToWorkOrderDALDto(rs);
			}
		return Optional.ofNullable(record);
		
	}

	public static List<WorkOrderDALDto> toWorkOrderDALDtoList(ResultSet rs) throws SQLException {
		List<WorkOrderDALDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToWorkOrderDALDto(rs)	);
		}
		
		return res;
	}
	
	public static WorkOrderDALDto resultSetToWorkOrderDALDto ( ResultSet rs ) throws SQLException {
		WorkOrderDALDto record = new WorkOrderDALDto();
		
		record.id = rs.getString("id");
		record.version = rs.getLong("version");

		record.vehicle_id = rs.getString( "vehicle_Id");
		record.description = rs.getString( "description");
		record.date =  rs.getTimestamp("date").toLocalDateTime();
		record.amount = rs.getDouble("amount");
		record.state = rs.getString( "state");
		record.mechanic_id = rs.getString( "mechanic_Id");
		record.invoice_id = rs.getString( "invoice_Id");
		
		return record;		
	}
	
	public static WorkOrderDALDto toWorkOrderDALDto(WorkOrderBLDto w) {
		WorkOrderDALDto wor = new WorkOrderDALDto();
		
		wor.amount = w.total;
		wor.date = w.date;
		wor.description = w.description;
		wor.id = w.id;
		wor.invoice_id = w.invoiceId;
		wor.mechanic_id = w.mechanicId;
		wor.state = w.state;
		wor.vehicle_id = w.vehicleId;
		wor.version = w.version;
		
		return wor;
	}
	
	
	

}
