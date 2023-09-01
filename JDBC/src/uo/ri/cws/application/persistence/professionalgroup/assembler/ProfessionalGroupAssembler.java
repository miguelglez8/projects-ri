package uo.ri.cws.application.persistence.professionalgroup.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

public class ProfessionalGroupAssembler {

	public static Optional<ProfessionalGroupDALDto> toProfessionalGroupDALDto(ResultSet rs) throws SQLException {
		if (rs.next()) {
			ProfessionalGroupDALDto value = new ProfessionalGroupDALDto();
			value.id = rs.getString("id");
			value.version = rs.getLong("version");
			value.name = rs.getString("name");
			value.trieniumSalary = rs.getDouble("trienniumpayment");
			value.productivityRate = rs.getDouble("productivitybonuspercentage");
			return Optional.of(value);
		} return Optional.ofNullable(null);
	}
	
	public static List<ProfessionalGroupDALDto> resultSetToListProfessionalGroupDALDto(ResultSet rs) throws SQLException {
		List<ProfessionalGroupDALDto> list = new ArrayList<>();
		while (rs.next()) {
			list.add(toProfessionalGroupDALDto(rs).get());
		}
		return list;
	}

	
	
	

	

}
