package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TCreditCards")
public class CreditCard extends PaymentMean {
	@Column(unique=true)
	private String number;
	@Basic(optional=false)
	private String type;
	@Basic(optional=false)
	private LocalDate validThru;
	
	CreditCard() {}
	
	public CreditCard(String number) {
		super();
		ArgumentChecks.isNotNull(number);
		ArgumentChecks.isNotEmpty(number);
		
		this.number = number;
		this.validThru = LocalDate.now().plusDays(1);
		this.type = "UNKNOWN";
	}
	

	public CreditCard(String number, String type, LocalDate validThru) {
		this(number);
		ArgumentChecks.isNotNull(type);
		ArgumentChecks.isNotEmpty(type);
		
		this.validThru = validThru;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number);
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
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number);
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type + ", validThru=" + validThru + "]";
	}


	public LocalDate getValidThru() {
		return validThru;
	}


	public String getType() {
		return type;
	}


	public String getNumber() {
		return number;
	}


	public boolean isValidNow() {
		return validThru.compareTo(LocalDate.now()) > 0;
	}


	public void setValidThru(LocalDate minusDays) {
		this.validThru = minusDays;
	}
	
	public void pay(double importe) {
		if (validThru.compareTo(LocalDate.now()) < 0) {
			throw new IllegalStateException("Card expired");
		}
		super.pay(importe);
	}

	@Override
	public boolean canPay(double amount) {
		return isValidNow();
	}
	
	
	

}
