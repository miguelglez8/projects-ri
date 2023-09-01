package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TVouchers")
public class Voucher extends PaymentMean {
	@Column(unique=true)
	private String code;
	private double available = 0.0;
	@Basic(optional=false)
	private String description;
	
	Voucher() {}
	
	public String getCode() {
		return code;
	}



	public double getAvailable() {
		return available;
	}


	public String getDescription() {
		return description;
	}



	public Voucher(String code, String description,  double available) {
		ArgumentChecks.isNotNull(code);
		ArgumentChecks.isNotEmpty(code);
		ArgumentChecks.isNotNull(description);
		ArgumentChecks.isNotEmpty(description);
		this.code = code;
		this.available = available;
		this.description = description;
	}

	
	public Voucher(String code, double available) {
		this(code, "no-description", available);
	}


	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
		super.pay(amount);
		if ((this.available-amount) < 0) {
			throw new IllegalStateException("Not enough available");
		}
		this.available-=amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(code);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Voucher other = (Voucher) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available + ", description=" + description + "]";
	}

	@Override
	public boolean canPay(double amount) {
		return available >= amount;
	}
	
	

}
