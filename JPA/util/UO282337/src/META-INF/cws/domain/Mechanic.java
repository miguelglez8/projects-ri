package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TMechanics")
public class Mechanic extends BaseEntity {
	// natural attributes
	@Column(unique=true)
	private String dni;
	
	@Basic(optional=false)
	private String name;

	@Basic(optional=false)
	private String surname;

	// accidental attributes
	@OneToMany(mappedBy="mechanic")
	private Set<WorkOrder> assigned = new HashSet<>();
	
	@OneToMany(mappedBy="mechanic")
	private Set<Intervention> interventions = new HashSet<>();
	
	@OneToMany(mappedBy="firedMechanic")
	private Set<Contract> contratosTerminados = new HashSet<>();
	
	@OneToOne(mappedBy="mechanic")
	private Contract contratoVigor;

	Mechanic() {}

	public Mechanic(String dni, String surname, String name) {
		ArgumentChecks.isNotBlank(dni);
		ArgumentChecks.isNotBlank(surname);
		ArgumentChecks.isNotBlank(name);
		
		this.dni = dni;
		this.surname = surname;
		this.name = name;
	}

	
	public Optional<Contract> getContrato() {
		return Optional.ofNullable(contratoVigor);
	}
	
	void _setContrato(Optional<Contract> contrato) {
		this.contratoVigor = contrato.get();
	}

	Set<Contract> _getContratos() {
		return contratosTerminados;
	}
	
	public Set<Contract> getContratos() {
		return new HashSet<>(contratosTerminados);
	}

	public void setContratos(Set<Contract> contratos) {
		this.contratosTerminados = contratos;
	}

	public Mechanic(String dni) {
		this(dni, "no-name", "no-surname");
	}

	public String getDni() {
		return dni;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(dni);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mechanic other = (Mechanic) obj;
		return Objects.equals(dni, other.dni);
	}

	public Set<WorkOrder> getAssigned() {
		return new HashSet<>( assigned );
	}

	Set<WorkOrder> _getAssigned() {
		return assigned;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	public Optional<Contract> getContractInForce() {
		return Optional.ofNullable(contratoVigor);
	}

	public Set<Contract> getTerminatedContracts() {
		return new HashSet<>(contratosTerminados);
	}
	
	public Set<Contract> _getTerminatedContracts() {
		return contratosTerminados;
	}

	public boolean isInForce() {
		if (contratoVigor==null) {
			return false;
		}
		return contratoVigor.getState().equals(ContractState.IN_FORCE);
	}

	void _setContractInForce(Contract object) {
		this.contratoVigor=object;
	}
	
}
