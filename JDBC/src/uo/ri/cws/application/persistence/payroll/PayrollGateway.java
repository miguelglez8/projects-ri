package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;

public interface PayrollGateway extends Gateway<uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto> {
	
	public class PayrollDALDto {

    	public String id;
    	public long version;
    	
    	public String contractId;
    	public LocalDate date;
    	
    	// Earnings
    	public double monthlyWage;
    	public double bonus;
    	public double productivityBonus;
    	public double trienniumPayment;
    	
    	// Deductions
    	public double incomeTax;
    	public double nic;
    	
    	// Net wage
    	public double netWage;
    }

	public List<PayrollDALDto> findByContractId(String id);

}
