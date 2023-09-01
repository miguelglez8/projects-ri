package uo.ri.cws.application.business.invoice.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto.InvoiceState;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceDALDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

public class InvoicingAssembler {

	public static InvoiceBLDto toDto(InvoiceDALDto arg) {
		InvoiceBLDto result = new InvoiceBLDto();
		result.id = arg.id;
		result.number = arg.number;
		result.state = InvoiceState.valueOf(arg.state);
		result.date = arg.date;
		result.total = arg.amount;
		result.vat = arg.vat;
		return result;
	}

	
	public static WorkOrderForInvoicingBLDto toWorkOrderForInvoicingDto(ResultSet rs) throws SQLException {
		WorkOrderForInvoicingBLDto dto = new WorkOrderForInvoicingBLDto();

		dto.id = rs.getString("id");
		dto.description = rs.getString("Description");
		Timestamp sqlDate = rs.getTimestamp( "date");
		dto.date =  sqlDate.toLocalDateTime(); 
		dto.total = rs.getDouble("amount");
		dto.state = rs.getString("status");

		return dto;
	}
	
	public static List<WorkOrderForInvoicingBLDto> toWorkOrderForInvoicingDtoList(
		    ResultSet rs) throws SQLException {
		List<WorkOrderForInvoicingBLDto> res = new ArrayList<>();
		while (rs.next()) {
		    res.add(toWorkOrderForInvoicingDto(rs));
		}

		return res;
	    }

	public static List<WorkOrderForInvoicingBLDto> toWorkOrderForInvoicingDtoList(
			List<WorkOrderDALDto> findNotInvoicedForVehicles) {
		List<WorkOrderForInvoicingBLDto> result = new ArrayList<WorkOrderForInvoicingBLDto>();
		for (WorkOrderDALDto mr : findNotInvoicedForVehicles)
			result.add(toWorkOrderForInvoicingDto(mr));
		return result;
	}
	
	
	private static WorkOrderForInvoicingBLDto toWorkOrderForInvoicingDto(WorkOrderDALDto mr) {
		WorkOrderForInvoicingBLDto w = new WorkOrderForInvoicingBLDto();
		w.id=mr.id;
		w.description=mr.description;
		w.date=mr.date;
		w.state=mr.state;
		w.total=mr.amount;
		return w;
	}
	
}
