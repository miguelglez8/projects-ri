package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TVehicles")
public class Vehicle extends BaseEntity {
	
	@Column(unique=true)
	private String plateNumber;
	
	@Basic(optional=false)
	@Column(name="BRAND")
	private String make;
	
	@Basic(optional=false)
	private String model;
	
	@ManyToOne(optional=false)
	private VehicleType vehicleType;
	
	@ManyToOne(optional=false)
	private Client client;

	@OneToMany(mappedBy="vehicle")
	private Set<WorkOrder> workOrders = new HashSet<>();
	
	Vehicle() {}
	
	public Vehicle(String plateNumber, String make, String model) {
		ArgumentChecks.isNotBlank(plateNumber);
		ArgumentChecks.isNotBlank(make);
		ArgumentChecks.isNotBlank(model);

		this.plateNumber = plateNumber;
		this.make = make;
		this.model = model;
	}
	public Vehicle(String plateNumber) {
		this(plateNumber, "no-make", "no-model");
	}
	
	Set<WorkOrder> _getWorkOrders() {
		return workOrders;
	}
	
	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}
	
	void setWorkOrders(Set<WorkOrder> workOrders) {
		this.workOrders = workOrders;
	}
	public VehicleType getVehicleType() {
		return vehicleType;
	}
	void _setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	void _setClient(Client client) {
		this.client=client;
	}
	public Client getClient() {
		return client;
	}
	
	public String getPlateNumber() {
		return plateNumber;
	}
	
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Override
	public int hashCode() {
		return Objects.hash(plateNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vehicle other = (Vehicle) obj;
		return Objects.equals(plateNumber, other.plateNumber);
	}
	@Override
	public String toString() {
		return "Vehicle [plateNumber=" + plateNumber + ", make=" + make + ", model=" + model + "]";
	}


	
	

}
