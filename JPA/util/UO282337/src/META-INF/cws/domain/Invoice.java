package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TInvoices")
public class Invoice extends BaseEntity {
	public enum InvoiceState { NOT_YET_PAID, PAID }

	// natural attributes
	@Column(unique=true)
	private Long number;
	
	@Basic(optional=false)
	private LocalDate date;
	
	private double amount;
	
	private double vat;
	
	@Enumerated(EnumType.STRING)
	@Column(name="STATUS")
	private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	@OneToMany(mappedBy="invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	
	@OneToMany(mappedBy="invoice")
	private Set<Charge> charges = new HashSet<>();

	Invoice() {}
	
	public Invoice(Long number) {
		this(number, LocalDate.now(), List.of());
	}

	public Invoice(Long number, LocalDate date) {
		this(number, date, List.of());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotNull(date);
		ArgumentChecks.isNotNull(workOrders);
		this.number=number;
		this.date=LocalDate.from(date);
		this.vat=1.21;
		if (date.isBefore(LocalDate.of(2012, 7, 1))) {
			this.vat = 1.18;
		}
		for (int i=0; i < workOrders.size(); i++) {
			if (workOrders.get(i).getStatus() != WorkOrderState.FINISHED) {
				throw new IllegalStateException(
						"Workorder is not finished");
			}
			addWorkOrder(workOrders.get(i));
		}
		
	}
	
	public Long getNumber() {
		return number;
	}

	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getVat() {
		return vat;
	}

	public void setVat(double vat) {
		this.vat = vat;
	}

	public InvoiceState getState() {
		return state;
	}

	public void setState(InvoiceState state) {
		this.state = state;
	}
	
	

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount=" + amount + ", vat=" + vat + ", state="
				+ state + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(number);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invoice other = (Invoice) obj;
		return Objects.equals(number, other.number);
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double amount = 0;
		for (WorkOrder w: workOrders) {
			amount += w.getAmount();
		}
		amount = amount * vat;
		this.amount = Math.floor(amount*100)/100;
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
		if (this.getState() != InvoiceState.NOT_YET_PAID) {
			throw new IllegalStateException(
					"Invoice is not paid");
		}
		Associations.ToInvoice.link(this, workOrder);
		workOrder.markAsInvoiced();
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
		if (this.getState() != InvoiceState.NOT_YET_PAID) {
			throw new IllegalStateException(
					"Invoice is not paid");
		}
		Associations.ToInvoice.unlink(this, workOrder);
		workOrder.markBackToFinished();
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * @throws IllegalStateException if
	 * 	- Is already settled
	 *  - Or the amounts paid with charges to payment means do not cover
	 *  	the total of the invoice
	 */
	public void settle() {
		if (this.getState() != InvoiceState.PAID) {
			throw new IllegalStateException(
					"Invoice is paid");
		} 
		this.state = InvoiceState.PAID;
		double cantidad = 0.0;
		for (Charge charge: charges) {
	        cantidad+=charge.getAmount();
		}
		if (Math.abs(cantidad-amount) > 0.01) {
			throw new IllegalStateException(
					"Invoice do not cover");
		}
	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>( workOrders );
	}

	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>( charges );
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public boolean isNotSettled() {
		return state == InvoiceState.NOT_YET_PAID;
	}
	
	public boolean isSettled() {
		return state == InvoiceState.PAID;
	}

}
