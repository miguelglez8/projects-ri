package uo.ri.cws.application.business.contracttype.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto;

public class ContractTypeAssembler {

	public static Optional<ContractTypeBLDto> resultSetToContractTypeDto(ResultSet rs) throws SQLException {
		if (rs.next()) {
			ContractTypeBLDto value = new ContractTypeBLDto();
			value.id = rs.getString("id");
			value.version = rs.getLong("version");
			
			value.name = rs.getString("name");
			value.compensationDays = rs.getDouble("compensationDays");
			
			return Optional.of(value);
		} return Optional.ofNullable(null);
		
	}

	public static List<ContractTypeBLDto> toContractTypeBLDtoList(List<ContractTypeDALDto> findAll) {
		List<ContractTypeBLDto> list = new ArrayList<>();
		for (int i=0; i < findAll.size(); i++) {
			list.add(toContractTypeBLDto(Optional.of(findAll.get(i))).get());
		}
		return list;
	}

	public static Optional<ContractTypeBLDto> toContractTypeBLDto(
			Optional<ContractTypeDALDto> dto) {
		if (dto.isPresent()) {
			ContractTypeBLDto d = new ContractTypeBLDto();
			ContractTypeDALDto rs = dto.get();
			d.compensationDays=rs.compensationDays;
			d.id=rs.id;
			d.name=rs.name;
			d.version=rs.version;
			return Optional.of(d);
		} 	return Optional.ofNullable(null);
	}
	
	
	public static Optional<ContractTypeBLDto> toContractTypeBLDtoOptional(Optional<ContractTypeDALDto> arg) {
		Optional<ContractTypeBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: toContractTypeBLDto(arg.get());
		return result;
	}

	private static Optional<ContractTypeBLDto> toContractTypeBLDto(ContractTypeDALDto arg) {

		ContractTypeBLDto result = new ContractTypeBLDto();
		result.id = arg.id;
		result.version = arg.version;
		result.name = arg.name;
		result.compensationDays = arg.compensationDays;
		return Optional.of(result);
	}

	
	
}
