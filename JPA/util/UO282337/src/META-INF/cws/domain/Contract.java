package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TContracts", uniqueConstraints = {
		@UniqueConstraint(columnNames= {"MECHANIC_ID", "STARTDATE"})
})
public class Contract extends BaseEntity {
	
	public enum ContractState { IN_FORCE, TERMINATED};
	
	@OneToOne
	private Mechanic mechanic;
	
	@ManyToOne(optional=false)
	private ProfessionalGroup professionalGroup;
	
	@ManyToOne(optional=false)
	private Mechanic firedMechanic;
	
	@ManyToOne(optional=false)
	private ContractType contractType;
	
	private LocalDate startDate;
	
	@Column(name="ANNUALBASEWAGE")
	private double annualWage;
	
	@Basic(optional=false)
	private LocalDate endDate;
	
	private double settlement;
	
	@Enumerated(EnumType.STRING)
	private ContractState state;
	
	@OneToMany(mappedBy="contract")
	private Set<Payroll> payrolls = new HashSet<>();

	Contract() {}
	
	public Contract(Mechanic mechanic, ContractType type, ProfessionalGroup group, LocalDate endDate, double wage) {
		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotNull(group);
		ArgumentChecks.isTrue(wage > 0);
		ArgumentChecks.isNotNull(endDate);

		this.endDate=endDate;
		this.annualWage=wage;
		this.state=ContractState.IN_FORCE;
		this.settlement = 0;
		this.startDate = LocalDate.now().plus(1, ChronoUnit.MONTHS).with(TemporalAdjusters.firstDayOfMonth());
		
		Associations.Hire.link(mechanic, this);
		Associations.Type.link(this, type);
		Associations.Group.link(this, group);
	}
	
	public Contract(Mechanic mechanicFired, ContractType type, ProfessionalGroup group, double wage) {
		this(mechanicFired, type, group, LocalDate.now(), wage);
	}

	public ProfessionalGroup getProfessionalGroup() {
		return professionalGroup;
	}
	void _setProfessionalGroup(ProfessionalGroup professionalGroup) {
		this.professionalGroup = professionalGroup;
	}
	
	Set<Payroll> _getPayrolls() {
		return payrolls;
	}
	public Set<Payroll> getPayrolls() {
		return new HashSet<>(payrolls);
	}
	public void setPayrolls(Set<Payroll> payrolls) {
		this.payrolls = payrolls;
	}
	
	public ContractType getContractType() {
		return contractType;
	}
	void _setContractType(ContractType type) {
		this.contractType = type;
	}
	
	void _setMechanicFired(Mechanic mechanicFired) {
		this.firedMechanic = mechanicFired;
	}
	
	public LocalDate getStartDate() {
		return startDate;
	}
	
	public double getAnnualBaseWage() {
		return annualWage;
	}
	public void setAnnualWage(double annualWage) {
		this.annualWage = annualWage;
	}
	
	public double getSettlement() {
		return settlement;
	}
	public void setSettlement(double settlement) {
		this.settlement = settlement;
	}
	public ContractState getState() {
		return state;
	}
	public void setState(ContractState state) {
		this.state = state;
	}
	public void setEndDate(LocalDate date) {
		this.endDate = date;
	}
	@Override
	public int hashCode() {
		return Objects.hash(mechanic, firedMechanic, startDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contract other = (Contract) obj;
		return Objects.equals(mechanic, other.mechanic) && Objects.equals(firedMechanic, other.firedMechanic)
				&& Objects.equals(startDate, other.startDate);
	}
	@Override
	public String toString() {
		return "Contract [mechanic=" + mechanic + ", mechanicFired=" + firedMechanic + ", startDate=" + startDate
				+ ", annualWage=" + annualWage + ", endDate=" + endDate + ", settlement=" + settlement + ", state="
				+ state + "]";
	}

	public void terminate() {
		this.state=ContractState.TERMINATED;
		int years = Period.between(startDate, LocalDate.now()).getYears();
		if (years > 0) {
			double salary12Months = payrolls.stream()
					.sorted((p1,p2) -> -p1.getDate().compareTo(p2.getDate()))
					.limit(12)
					.mapToDouble(payroll -> payroll.getMonthlyWage() + payroll.getBonus() + payroll.getProductivityBonus() + payroll.getTrienniumPayment())
					.sum();
			double daySalary = salary12Months / 365;
			this.settlement =Math.floor( daySalary * contractType.getCompensationDays() * years * 100) / 100;
		}
		this.endDate = getEndOfMonth();
		Associations.Fire.link(this);
	}
	
	public static LocalDate getEndOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

	public Optional<Mechanic> getFiredMechanic() {
		return Optional.ofNullable(firedMechanic);
	}

	public Optional<LocalDate> getEndDate() {
		return Optional.ofNullable(endDate);
	}

	void _setMechanic(Mechanic optional) {
		this.mechanic = optional;
	}

	public Optional<Mechanic> getMechanic() {
		return Optional.ofNullable(mechanic);
	}

	public void setFiredMechanic(Mechanic mechanic) {
		this.firedMechanic = mechanic;
	}

	public void setStartDate(LocalDate startDate2) {
		this.startDate = startDate2;
	}
	
	

	
	
	

}
