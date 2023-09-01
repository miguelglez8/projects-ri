package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TWorkOrders", uniqueConstraints = {
		@UniqueConstraint(columnNames= {"DATE", "VEHICLE_ID"})
})
public class WorkOrder extends BaseEntity {
	public enum WorkOrderState {
		OPEN,
		ASSIGNED,
		FINISHED,
		INVOICED
	}

	// natural attributes
	@Basic(optional=false)
	private LocalDateTime date;
	
	@Basic(optional=false)
	private String description;
	
	private double amount = 0.0;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	@ManyToOne(optional=false)
	private Vehicle vehicle;
	@ManyToOne
	private Mechanic mechanic;
	@ManyToOne
	private Invoice invoice;
	@OneToMany(mappedBy="workOrder")
	private Set<Intervention> interventions = new HashSet<>();
	
	WorkOrder() {}
	
	public WorkOrder(Vehicle vehicle, LocalDateTime date, String description) {
		ArgumentChecks.isNotNull(vehicle);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotEmpty(description);
		
		this.date = date.truncatedTo(ChronoUnit.MILLIS);
		this.description = description;

		Associations.Fix.link(vehicle, this);
	}
	
	public WorkOrder(Vehicle vehicle) {
		this(vehicle, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), "no-description");
	}
	
	public WorkOrder(Vehicle vehicle, String description) {
		this(vehicle, LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS), description);
	}
	
	public WorkOrder(Vehicle vehicle, LocalDateTime date) {
		this(vehicle, date, "no-description");
	}
	
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	public Mechanic getMechanic() {
		return mechanic;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}


	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", description=" + description + ", amount=" + amount + ", state=" + state
				+ "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(date, vehicle);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(date, other.date) && Objects.equals(vehicle, other.vehicle);
	}


	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public WorkOrderState getStatus() {
		return state;
	}


	public void setState(WorkOrderState state) {
		this.state = state;
	}


	/**
	 * Changes it to INVOICED state given the right conditions
	 * This method is called from Invoice.addWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not FINISHED, or
	 *  - The work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		if (state != WorkOrderState.FINISHED) {
			throw new IllegalStateException(
					"The workorder finished");
		}
		if (invoice == null) {
			throw new IllegalStateException(
					"The workorder is not linked");
		}
		state = WorkOrderState.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and
	 * computes the amount
	 * Unlink de mecnico y workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state, or
	 *  - The work order is not linked with a mechanic
	 */
	public void markAsFinished() {
		if (state != WorkOrderState.ASSIGNED) {
			throw new IllegalStateException(
					"The workorder assigned");
		}
		if (mechanic == null) {
			throw new IllegalStateException(
					"The workorder is not linked");
		}
		state = WorkOrderState.FINISHED;
		double amount = 0.0;
		for (Intervention in: interventions) {
			amount += in.getAmount();
		}
		this.amount = amount;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions
	 * This method is called from Invoice.removeWorkOrder(...)
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not INVOICED, or
	 *  - The work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		if (state != WorkOrderState.INVOICED) {
			throw new IllegalStateException("Work order is not INVOICED");
		}
		if (invoice!=null) {
			throw new IllegalStateException("Workorder is not linked");
		}
		state = WorkOrderState.ASSIGNED;
		markAsFinished();
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state
	 * to ASSIGNED
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in OPEN state, or
	 *  - The work order is already linked with another mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		if (state != WorkOrderState.OPEN) {
			throw new IllegalStateException("Work order is not OPEN");
		}
		if (this.getMechanic()!=null) {
			throw new IllegalStateException("Workorder is not linked");
		}
		Associations.Assign.link(mechanic, this);
		state = WorkOrderState.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes
	 * its state back to OPEN
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in ASSIGNED state
	 */
	public void desassign() {
		if (state != WorkOrderState.ASSIGNED) {
			throw new IllegalStateException("Work order is not ASSIGNED");
		}
		state = WorkOrderState.OPEN;
		Associations.Assign.unlink(mechanic, this);
	}

	/**
	 * In order to assign a work order to another mechanic is first have to
	 * be moved back to OPEN state and unlinked from the previous mechanic.
	 * No es necesario hacer un unlink del mecánico en absoluto
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if
	 * 	- The work order is not in FINISHED state
	 */
	public void reopen() {
		if (state != WorkOrderState.FINISHED) {
			throw new IllegalStateException(
					"The workorder finished");
		}
		this.state = WorkOrderState.OPEN;
		Associations.Assign.unlink(mechanic, this);
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>( interventions );
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public boolean isInvoiced() {
		return state == WorkOrderState.INVOICED;
	}

	public boolean isFinished() {
		return state == WorkOrderState.FINISHED;
	}
	public void setStatusForTesting(WorkOrderState invoiced) {
		this.state = invoiced;
	}
	public void setAmountForTesting(double money) {
		this.amount=money;
	}

}
