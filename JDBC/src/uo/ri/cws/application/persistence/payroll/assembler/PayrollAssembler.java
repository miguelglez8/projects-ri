package uo.ri.cws.application.persistence.payroll.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

public class PayrollAssembler {

	public static List<PayrollDALDto> resultSetToPayrollDALDtoList(ResultSet rs) throws SQLException {
		List<PayrollDALDto> l = new ArrayList<>();
		
		while (rs.next()) {
			l.add(resultSetToPayrollDALDto(rs));
		}

		return l;
	}

	private static PayrollDALDto resultSetToPayrollDALDto(ResultSet rs) throws SQLException {
		PayrollDALDto p = new PayrollDALDto();
		p.bonus=rs.getDouble("bonus");
		p.id=rs.getString("id");
		p.monthlyWage=rs.getDouble("monthlyWage");
		p.date=rs.getDate("date").toLocalDate();
		p.trienniumPayment=rs.getDouble("trienniumPayment");
		p.productivityBonus=rs.getDouble("productivityBonus"); 
		return p;
	}

}
