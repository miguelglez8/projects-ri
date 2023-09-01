package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TPayrolls", uniqueConstraints = {
		@UniqueConstraint(columnNames= {"CONTRACT_ID", "DATE"})
})
public class Payroll extends BaseEntity {
	@ManyToOne(optional=false)
	private Contract contract;
	@Basic(optional=false)
	private LocalDate date;
	private double bonus;
	private double incomeTax;
	private double monthlyWage;
	private double nic;
	private double productivityBonus;
	private double trienniumPayment;

	Payroll() {}
	
	public Payroll(Contract contract, LocalDate d, double monthlyWage, double extra, double productivity,
			double trienniums, double tax, double nic) {
		ArgumentChecks.isNotNull(contract);
		ArgumentChecks.isNotNull(d);

		this.date = d;
		this.monthlyWage = monthlyWage;
		this.bonus = extra;
		this.productivityBonus = productivity;
		this.trienniumPayment = trienniums;
		this.incomeTax = tax;
		this.nic = nic;
		
		Associations.Run.link(this, contract);
	}

	public Payroll(Contract contract) {
		this(contract, LocalDate.now(), contract!=null ? contract.getAnnualBaseWage()/14 : 0,  
				contract!=null ? calcularBonus(contract) : 0, contract!=null ? calcularProductivityBonus(contract) : 0, 
						contract != null ? calcularTriennium(contract) : 0, 
								contract!=null ? calcularTax(contract) : 0,  
										contract!=null ? (contract.getAnnualBaseWage() / 12) * 0.05 : 0);
	}

	public Payroll(Contract contract, LocalDate d) {
		this(contract, d, contract.getAnnualBaseWage()/14, calcularBonus(contract, d), 
				calcularProductivityBonus(contract, d), 
					calcularTriennium(contract, d), 
						calcularTax(contract, d), 
							(contract.getAnnualBaseWage() / 12) * 0.05);
	}
	
	private static double calcularTriennium(Contract contract, LocalDate d) {
		if (d!=null) {
			
			int años = Period.between(contract.getStartDate(),d).getYears();
			int trienios = años / 3;
					
			return Math.floor((trienios * contract.getProfessionalGroup().getTrienniumPayment())*100)/100;
		}
		return 0;
	}

	private static double calcularProductivityBonus(Contract contract, LocalDate d) {
		double amount = 0;
		for (WorkOrder element: contract.getMechanic().get().getAssigned()) {
			if ("INVOICED".equals(element.getStatus().toString()) 
					&& element.getDate().getMonth() == d.getMonth() 
					&& element.getDate().getYear() == d.getYear()) {
				amount += element.getAmount();
			}
		}		
		return Math.floor((amount * contract.getProfessionalGroup().getProductivityBonusPercentage() / 100)*100)/100;
	}

	private static double calcularTax(Contract contract, LocalDate d) {
		double per = 0;
		if (contract.getAnnualBaseWage() < 12450) {
			per = 0.19;
		} else if (contract.getAnnualBaseWage() < 20200 ) {
			per = 0.24;
		} else if (contract.getAnnualBaseWage() < 35200 ) {
			per = 0.30;
		} else if (contract.getAnnualBaseWage() < 60000 ) {
			per = 0.37;
		} else if (contract.getAnnualBaseWage() < 300000 ) {
			per = 0.45;
		} else {
			per = 0.47;
		}
		return Math.floor(((contract.getAnnualBaseWage()/14 + calcularBonus(contract, d) + calcularProductivityBonus(contract, d) + calcularTriennium(contract, d)) * per)*100)/100;
		
	}

	private static double calcularProductivityBonus(Contract contract) {
		double amount = 0;
		for (WorkOrder element: contract.getMechanic().get().getAssigned()) {
			if ("INVOICED".equals(element.getStatus().toString()) 
					&& element.getDate().getMonth() == LocalDate.now().getMonth() 
					&& element.getDate().getYear() == LocalDate.now().getYear()) {
				amount += element.getAmount();
			}
		}		
		return Math.floor((amount * contract.getProfessionalGroup().getProductivityBonusPercentage() / 100)*100)/100;
	}

	private static double calcularBonus(Contract contract) {
		if (LocalDate.now().getMonthValue()==6 || LocalDate.now().getMonthValue()==12) {
			return contract.getAnnualBaseWage()/14;
		} else {
			return 0;
		}
	}

	private static double calcularBonus(Contract contract, LocalDate d) {
		if (d!=null) {
			if (d.getMonth()==Month.JUNE || d.getMonth()==Month.DECEMBER) {
				return contract.getAnnualBaseWage()/14;
			} else {
				return 0;
			}
		}
		return 0;
	}

	private static double calcularTriennium(Contract contract) {
		int años = Period.between(contract.getStartDate(), LocalDate.now()).getYears();
		int trienios = años / 3;
				
		return Math.floor((trienios * contract.getProfessionalGroup().getTrienniumPayment())*100)/100;
	}

	private static double calcularTax(Contract contract) {
		double per = 0;
		if (contract.getAnnualBaseWage() < 12450) {
			per = 0.19;
		} else if (contract.getAnnualBaseWage() < 20200 ) {
			per = 0.24;
		} else if (contract.getAnnualBaseWage() < 35200 ) {
			per = 0.30;
		} else if (contract.getAnnualBaseWage() < 60000 ) {
			per = 0.37;
		} else if (contract.getAnnualBaseWage() < 300000 ) {
			per = 0.45;
		} else {
			per = 0.47;
		}
		return Math.floor(((contract.getAnnualBaseWage()/14 + calcularBonus(contract) + calcularProductivityBonus(contract) + calcularTriennium(contract)) * per)*100)/100;
	}

	public Contract getContract() {
		return contract;
	}

	void _setContract(Contract contract) {
		this.contract = contract;
	}

	public LocalDate getDate() {
		return date;
	}

	void _setDate(LocalDate date) {
		this.date = date;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(double incomeTax) {
		this.incomeTax = incomeTax;
	}

	public double getMonthlyWage() {
		return monthlyWage;
	}

	public void setMonthlyWage(double monthlyWage) {
		this.monthlyWage = monthlyWage;
	}

	public double getNIC() {
		return nic;
	}

	public void setNic(double nic) {
		this.nic = nic;
	}

	public double getProductivityBonus() {
		return productivityBonus;
	}

	public void setProductivityBonus(double productivityBonus) {
		this.productivityBonus = productivityBonus;
	}

	public double getTrienniumPayment() {
		return trienniumPayment;
	}

	public void setTrienniumPayment(double trienniumPayment) {
		this.trienniumPayment = trienniumPayment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(contract, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payroll other = (Payroll) obj;
		return Objects.equals(contract, other.contract) && Objects.equals(date, other.date);
	}

	@Override
	public String toString() {
		return "Payroll [contract=" + contract + ", date=" + date + ", bonus=" + bonus + ", incomeTax=" + incomeTax
				+ ", monthlyWage=" + monthlyWage + ", nic=" + nic + ", productivityBonus=" + productivityBonus
				+ ", trienniumPayment=" + trienniumPayment + "]";
	}

	
	
	
	

}
