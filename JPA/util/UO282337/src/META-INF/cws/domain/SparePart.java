package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="TSpareParts")
public class SparePart extends BaseEntity {
	// natural attributes
	@Column(unique=true)
	private String code;
	
	@Basic(optional=false)
	private String description;
	
	private double price;

	// accidental attributes
	@OneToMany(mappedBy="sparePart")
	private Set<Substitution> substitutions = new HashSet<>();

	SparePart() {}
	
	public SparePart(String code, String description, double price) {
		ArgumentChecks.isNotBlank(code);
		ArgumentChecks.isNotBlank(description);
		
		this.code = code;
		this.description = description;
		this.price = price;
	}

	public SparePart(String code) {
		this(code, "no-description", 0);
	}

	
	public String getCode() {
		return code;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setSubstitutions(Set<Substitution> substitutions) {
		this.substitutions = substitutions;
	}

	public Set<Substitution> getSubstitutions() {
		return new HashSet<>( substitutions );
	}

	Set<Substitution> _getSubstitutions() {
		return substitutions;
	}

	@Override
	public int hashCode() {
		return Objects.hash(code);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SparePart other = (SparePart) obj;
		return Objects.equals(code, other.code);
	}

	@Override
	public String toString() {
		return "SparePart [code=" + code + ", description=" + description + ", price=" + price + "]";
	}
	

}
