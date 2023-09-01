package uo.ri.cws.application.persistence.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface ContractGateway extends Gateway<uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto> {
	List<ContractDALDto> findByMechanicId(String id);
	
	List<ContractDALDto> findContractsInForce();
	
	List<ContractDALDto> findByContractTypeId(String id);

	List<ContractDALDto> findByProfessionalGroupId(String name);
	
	Optional<ContractDALDto> findInForceByIdMechanic(String idMecanico);
	
	public class ContractDALDto {
		public String id;
		public long version;
		
		public String mechanic_id;
		public String contractType_id;
		public String professionalGroup_id;
		public LocalDate startDate;
		public LocalDate endDate;
		public double annualBaseWage;

		// Filled in reading operations
		public double settlement;
		public String state;

	}




}
