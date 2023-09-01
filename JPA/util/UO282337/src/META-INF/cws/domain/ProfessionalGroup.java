package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TProfessionalGroups")
public class ProfessionalGroup extends BaseEntity {
	
	@Column(unique=true)
	private String name;
	@Column(name="PRODUCTIVITYBONUSPERCENTAGE")
	private double productivityRate;
	@Column(name="TRIENNIUMPAYMENT")
	private double trienniumSalary;
	
	@OneToMany(mappedBy="professionalGroup")
	private Set<Contract> contratos = new HashSet<>();
	
	ProfessionalGroup() {}
	
	public ProfessionalGroup(String name, double trienniumSalary, double productivityRate) {
		ArgumentChecks.isNotBlank(name);
		ArgumentChecks.isTrue(productivityRate>0 && trienniumSalary>0);
		
		this.name=name;
		this.productivityRate=productivityRate;
		this.trienniumSalary=trienniumSalary;
	}
	
	public Set<Contract> getContracts() {
		return new HashSet<>(contratos);
	}
	
	Set<Contract> _getContracts() {
		return contratos;
	}

	public void setContratos(Set<Contract> contratos) {
		this.contratos = contratos;
	}


	public String getName() {
		return name;
	}

	public double getProductivityBonusPercentage() {
		return productivityRate;
	}

	public void setProductivityRate(double productivityRate) {
		this.productivityRate = productivityRate;
	}

	public double getTrienniumPayment() {
		return trienniumSalary;
	}

	public void setTrienniumSalary(double trienniumSalary) {
		this.trienniumSalary = trienniumSalary;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessionalGroup other = (ProfessionalGroup) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "ProfessionalGroup [name=" + name + ", productivityRate=" + productivityRate + ", trienniumSalary="
				+ trienniumSalary + "]";
	}

	
	
	

}
