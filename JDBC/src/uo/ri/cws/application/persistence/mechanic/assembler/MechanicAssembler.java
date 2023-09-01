package uo.ri.cws.application.persistence.mechanic.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto;

public class MechanicAssembler {
	public static Optional<MechanicDALDto> toMechanicDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToMechanicDALDto(m));
		}
		else 	
			return Optional.ofNullable(null);
	}

	public static List<MechanicDALDto> toMechanicDALDtoList(ResultSet rs) throws SQLException {
		List<MechanicDALDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToMechanicDALDto(rs));
		}

		return res;
	}

	private static MechanicDALDto resultSetToMechanicDALDto(ResultSet rs) throws SQLException {
		MechanicDALDto value = new MechanicDALDto();
		value.id = rs.getString("id");
		value.version = rs.getLong("version");
		
		value.dni = rs.getString("dni");
		value.name = rs.getString("name");
		value.surname = rs.getString("surname");
		return value;
	}
	
	 public static MechanicDALDto toMechanicDALDto(MechanicBLDto arg) {
			MechanicDALDto result = new MechanicDALDto();
			result.id = arg.id;
			result.version = arg.version;
			result.dni = arg.dni;
			result.name = arg.name;
			result.surname = arg.surname;
			return result;
	}
	 
	 

}
