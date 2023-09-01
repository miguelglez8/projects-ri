package uo.ri.cws.application.business.contract.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;


public class ContractAssembler {
	
	public static List<ContractBLDto> toContractDtoList(List<ContractDALDto> arg) {
		List<ContractBLDto> result = new ArrayList<ContractBLDto>();
		for (ContractDALDto mr : arg)
			result.add(toContractDto(mr));
		return result;
	}


	public static ContractBLDto toContractDto(ContractDALDto cli) {
		ContractBLDto v = new ContractBLDto();

		v.id=cli.id;
		v.annualBaseWage=cli.annualBaseWage;
		v.startDate=cli.startDate;
		v.version=cli.version;
		v.version=cli.version;
		v.settlement=cli.settlement;
		if (cli.state.equals("TERMINATED")) {
			v.state=ContractState.TERMINATED;
		} else {
			v.state=ContractState.IN_FORCE;
		}
		v.startDate=cli.startDate;
		v.endDate=cli.endDate;
		
		return v;
	}
	
	public static Optional<ContractBLDto> resultSetToContractDtoOptional(ResultSet rs) throws SQLException {
		if (rs.next()) {
			ContractBLDto value = new ContractBLDto();
			value.id = rs.getString("id");
			value.version = rs.getLong("version");
						
			if ( rs.getDate("endDate")!=null) {
				value.endDate = rs.getDate("endDate").toLocalDate();
			}
			value.startDate = rs.getDate("startDate").toLocalDate();
			value.annualBaseWage = rs.getDouble("annualBaseWage");
			
			if (rs.getString("state").equals("TERMINATED")) {
				value.state=ContractState.TERMINATED;
			} else {
				value.state=ContractState.IN_FORCE;
			}
			value.settlement=rs.getDouble("settlement");
			return Optional.of(value);
		} else {
			return Optional.ofNullable(null);
		}
	}


	public static Optional<ContractBLDto> toContractBLDto(Optional<ContractDALDto> arg) {
		Optional<ContractBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toContractDto(arg.get()));
		return result;
	}
	
	public static ContractBLDto toContractDto(Optional<ContractDALDto> arg) {
		Optional<ContractBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toContractDto(arg.get()));
		return result.get();
	}



	public static ContractBLDto toContractBLDto(ContractSummaryBLDto contractSummaryBLDto) {
		ContractBLDto c = new ContractBLDto();
		c.dni=contractSummaryBLDto.dni;
		c.id=contractSummaryBLDto.id;
		c.settlement=contractSummaryBLDto.settlement;
		c.state=contractSummaryBLDto.state;
		c.version=contractSummaryBLDto.version;
		return c;
	}


	public static List<ContractSummaryBLDto> toContractSummaryBLDtoList(List<ContractDALDto> list) {
		List<ContractSummaryBLDto> l = new ArrayList<>();
		for (int i=0; i < list.size(); i++) {
			l.add(toContractSummaryBLDto(list.get(i)));
		}
		return l;
	}


	public static List<ContractSummaryBLDto> toContractSummaryDtoList(List<ContractDALDto> findAll) {
		List<ContractSummaryBLDto> l = new ArrayList<>();
		for (int i=0; i < findAll.size(); i++) {
			l.add(toContractSummaryBLDto(findAll.get(i)));
		}
		return l;
	}


	public static ContractSummaryBLDto toContractSummaryBLDto(ContractDALDto a) {
		ContractSummaryBLDto c = new ContractSummaryBLDto();
		c.id=a.id;
		c.settlement=a.settlement;
		if (a.state.equals("TERMINATED")) {
			c.state=ContractState.TERMINATED;
		} else {
			c.state=ContractState.IN_FORCE;
		}
		c.version=a.version;
		return c;
	}


	public static ContractSummaryBLDto toContractSummaryBLDto(ContractBLDto c) {
		ContractSummaryBLDto co = new ContractSummaryBLDto();
		co.id=c.id;
		co.settlement=c.settlement;
		co.state=c.state;
		co.version=c.version;
		co.dni=c.dni;
		return co;
	}





	


	

}
