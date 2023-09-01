package uo.ri.cws.application.persistence.mechanic;

import java.util.List;
import java.util.Optional;
import uo.ri.cws.application.persistence.Gateway;

public interface MechanicGateway extends Gateway<uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto> {

	/*
	 * Finds a row in the table
	 * @param record's field
	 * @return dto from that record, probably null
	 */
	Optional<MechanicDALDto> findByDni(String dni);
	
	
	public class MechanicDALDto {

		public String id;
		public Long version;
		
		public String dni;
		public String name;
		public String surname;

	}


	List<MechanicDALDto> findMechanicsByIds(List<String> mechanic);

}
