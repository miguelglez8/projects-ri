package uo.ri.cws.application.persistence.professionalgroup;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;

public interface ProfessionalGroupGateway extends Gateway<uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto> {
	
	Optional<ProfessionalGroupDALDto> findByName(String name);

	public class ProfessionalGroupDALDto {
		public String id;
		public long version;
		
		public String name;
		public double trieniumSalary;
		public double productivityRate;
		
	}

}
