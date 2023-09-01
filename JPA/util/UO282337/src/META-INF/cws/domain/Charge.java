package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.Invoice.InvoiceState;
import uo.ri.cws.domain.base.BaseEntity;

@Entity
@Table(name="TCharges", uniqueConstraints = {
		@UniqueConstraint(columnNames= {"INVOICE_ID", "PAYMENTMEAN_ID"})
})
public class Charge extends BaseEntity {
	// natural attributes
	private double amount = 0.0;

	// accidental attributes
	@ManyToOne
	private Invoice invoice;
	
	@ManyToOne
	private PaymentMean paymentMean;

	Charge() {}
	
	public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
		this.amount = amount;
		paymentMean.pay(amount);
		Associations.ToCharge.link(paymentMean, this, invoice);
		// store the amount
		// increment the paymentMean accumulated -> paymentMean.pay( amount )
		// link invoice, this and paymentMean
	}

	/**
	 * Unlinks this charge and restores the accumulated to the payment mean
	 * @throws IllegalStateException if the invoice is already settled
	 */
	public void rewind() {
		// asserts the invoice is not in PAID status
		// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
		// unlinks invoice, this and paymentMean
		assert(invoice.getState() != InvoiceState.PAID);
		paymentMean.pay(-amount);
		Associations.ToCharge.unlink(this);
	}

	public double getAmount() {
		return amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	
	public PaymentMean getPaymentMean() {
		return paymentMean;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	void _setPaymentMean(PaymentMean p) {
		this.paymentMean = p; 
	}

	

}
