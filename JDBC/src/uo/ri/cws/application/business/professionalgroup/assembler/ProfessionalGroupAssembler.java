package uo.ri.cws.application.business.professionalgroup.assembler;

import java.util.Optional;

import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

public class ProfessionalGroupAssembler {

	public static Optional<ProfessionalGroupBLDto> toProfessionalGroupBLDto(
			Optional<ProfessionalGroupDALDto> a) {
		if (a.isPresent()) {
			ProfessionalGroupBLDto p = new ProfessionalGroupBLDto();
			ProfessionalGroupDALDto rs = a.get();
			p.id = rs.id;
			p.version = rs.version;
			p.name = rs.name;
			p.trieniumSalary = rs.trieniumSalary;
			p.productivityRate = rs.productivityRate;
			return Optional.of(p);
		} 	return Optional.ofNullable(null);
	}

}
