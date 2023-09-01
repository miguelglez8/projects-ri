package uo.ri.cws.application.persistence.contracttype.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto;

public class ContractTypeAssembler {

	public static ContractTypeDALDto toContractTypeDALDto(ContractTypeBLDto dto) {
		ContractTypeDALDto c = new ContractTypeDALDto();

		c.compensationDays=dto.compensationDays;
		c.id=dto.id;
		c.name=dto.name;
		c.version=dto.version;

		return c;
	}

	public static ContractTypeDALDto resultSetToContractTypeDALDto(ResultSet rs) throws SQLException {
			ContractTypeDALDto value = new ContractTypeDALDto();
			value.id = rs.getString("id");
			value.version = rs.getLong("version");
			
			value.name = rs.getString("name");
			value.compensationDays = rs.getDouble("compensationDays");
			return value;		
	}

	public static List<ContractTypeDALDto> resultSetToListContractTypeListDALDto(ResultSet rs) throws SQLException {
		List<ContractTypeDALDto> list = new ArrayList<>();
		while (rs.next()) {
			list.add(resultSetToContractTypeDALDto(rs));
		}
		return list;
	}
	
	public static Optional<ContractTypeDALDto> resultSetToContractTypeDALDtoOptional(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToContractTypeDALDto(m));
		}
		else 	
			return Optional.ofNullable(null);
	}
	

	


}
