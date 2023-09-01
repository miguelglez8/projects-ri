package uo.ri.cws.application.persistence.contracttype;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface ContractTypeGateway extends Gateway<uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto> {
	
	Optional<ContractTypeDALDto> findContractTypeByName(String name);
	

	public class ContractTypeDALDto {
		public String id;
		public Long version;
		public String name;
		public double compensationDays;
	}

}
