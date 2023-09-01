package uo.ri.cws.application.business.payroll.assembler;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

public class PayrollAssembler {

	public static List<PayrollBLDto> toPayrollBLDtoList(List<PayrollDALDto> findByContractId) {
		List<PayrollBLDto> list = new ArrayList<>();
		for (int i=0; i < findByContractId.size(); i++) {
			list.add(toPayrollBLDto(findByContractId.get(i)));
		}
		return list;
	}

	private static PayrollBLDto toPayrollBLDto(PayrollDALDto payrollDALDto) {
		PayrollBLDto p = new PayrollBLDto();
		p.id=payrollDALDto.id;
		p.monthlyWage=payrollDALDto.monthlyWage;
		p.bonus=payrollDALDto.bonus;
		p.date=payrollDALDto.date;
		return p;
	}

}
