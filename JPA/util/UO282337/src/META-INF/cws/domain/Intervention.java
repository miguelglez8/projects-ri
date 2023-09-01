package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TInterventions", uniqueConstraints = {
		@UniqueConstraint(columnNames= {"MECHANIC_ID", "WORKORDER_ID","DATE"})
})
public class Intervention extends BaseEntity {
	// natural attributes
	@Basic(optional=false)
	private LocalDateTime date;
	
	private int minutes;

	// accidental attributes
	@ManyToOne
	private WorkOrder workOrder;
	
	@ManyToOne
	private Mechanic mechanic;
	
	@OneToMany(mappedBy="intervention")
	private Set<Substitution> substitutions = new HashSet<>();
	
	Intervention() {}
	
	public Intervention( Mechanic mechanic, WorkOrder workOrder, LocalDateTime date, int minutes) {

		ArgumentChecks.isNotNull(mechanic);
		ArgumentChecks.isNotNull(workOrder);
		ArgumentChecks.isNotNull(date);
		
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		this.minutes = minutes;
		Associations.Intervene.link(workOrder, this, mechanic);
	}
	
	public Intervention( Mechanic mechanic, WorkOrder workOrder, int minutes) {
		this(mechanic, workOrder, LocalDateTime.now(), minutes);
	}
	

	void _setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	@Override
	public String toString() {
		return "Intervention [date=" + date + ", minutes=" + minutes + ", workOrder=" + workOrder + ", mechanic="
				+ mechanic + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, mechanic, workOrder);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Intervention other = (Intervention) obj;
		return Objects.equals(date, other.date) && Objects.equals(mechanic, other.mechanic)
				&& Objects.equals(workOrder, other.workOrder);
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	public double getAmount() {
		double precio = 0.0;
		
		for (Substitution substitution : substitutions) {
			precio += substitution.getQuantity() * substitution.getSparePart().getPrice();
		}
		
		double precioHora = workOrder.getVehicle().getVehicleType().getPricePerHour();
		
		double horas = (double) minutes/60;
		double precioHoraTrabajo = precioHora * horas;
		
		return precioHoraTrabajo + precio;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public LocalDateTime getDate() {
		return date;
	}


	public int getMinutes() {
		return minutes;
	}

	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	
	

}
