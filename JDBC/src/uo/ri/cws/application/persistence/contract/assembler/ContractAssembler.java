package uo.ri.cws.application.persistence.contract.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class ContractAssembler {
	
	public static ContractDALDto toContractDALDto(ContractBLDto contrato) {
		ContractDALDto c = new ContractDALDto();
		
		c.id=contrato.id;
		c.version=contrato.version;
		c.startDate=contrato.startDate;
		c.endDate=contrato.endDate;
		c.annualBaseWage=contrato.annualBaseWage;
		c.settlement=contrato.settlement;
		c.state=contrato.state.toString();
		
		return c;
	}
	
	public static Optional<ContractDALDto> toContractDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToContractDALDto(m));
		}
		else 	
			return Optional.ofNullable(null);
	}

	public static List<ContractDALDto> resultSetToContractDALDtoList(ResultSet rs) throws SQLException {
		List<ContractDALDto> res = new ArrayList<>();
		while(rs.next()) {
			res.add( resultSetToContractDALDto(rs));
		}

		return res;
	}
	
	public static ContractDALDto resultSetToContractDALDto(ResultSet rs) throws SQLException {
		ContractDALDto value = new ContractDALDto();
		value.id = rs.getString("id");
		value.version = rs.getLong("version");
		
		value.contractType_id = rs.getString("contractType_id");
		value.professionalGroup_id = rs.getString("professionalGroup_id");
		
		if ( rs.getDate("endDate")!=null) {
			value.endDate = rs.getDate("endDate").toLocalDate();
		}
		
		value.startDate = rs.getDate("startDate").toLocalDate();
		value.annualBaseWage = rs.getDouble("annualBaseWage");
		value.state=rs.getString("state");
		value.settlement=rs.getDouble("settlement");
		value.mechanic_id=rs.getString("mechanic_id");
		return value;
					
	}

	public static Optional<ContractDALDto> resultSetToContractDALDtoOptional(ResultSet rs) throws SQLException {
		if (rs.next()) {
			ContractDALDto value = new ContractDALDto();
			value.id = rs.getString("id");
			value.version = rs.getLong("version");
			
			value.contractType_id = rs.getString("contractType_id");
			value.professionalGroup_id = rs.getString("professionalGroup_id");
			
			if ( rs.getDate("endDate")!=null) {
				value.endDate = rs.getDate("endDate").toLocalDate();
			}
			
			value.startDate = rs.getDate("startDate").toLocalDate();
			value.annualBaseWage = rs.getDouble("annualBaseWage");
			value.state=rs.getString("state");
			value.settlement=rs.getDouble("settlement");
			value.mechanic_id=rs.getString("mechanic_id");
			return Optional.of(value);
		} return Optional.ofNullable(null);
	}

	public static ContractDALDto toContractDALDto(ContractBLDto contrato, List<String> list) {
		ContractDALDto value = new ContractDALDto();
		value.id = contrato.id;
		value.version = contrato.version;
		
		value.contractType_id = list.get(1);
		value.professionalGroup_id = list.get(2);
		
		if (contrato.endDate!=null)
			value.endDate = contrato.endDate;
		
		value.startDate = contrato.startDate;
		value.annualBaseWage = contrato.annualBaseWage;
		value.state=contrato.state.toString();
		value.settlement=contrato.settlement;
		value.mechanic_id=list.get(0);
		return value;
	}
	

}
