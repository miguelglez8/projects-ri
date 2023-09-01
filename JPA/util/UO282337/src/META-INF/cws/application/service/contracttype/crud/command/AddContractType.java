package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;


public class AddContractType implements Command<ContractTypeDto> {

	private ContractTypeDto dto;

	public AddContractType(ContractTypeDto dto) {
		ArgumentChecks.isNotNull(dto);
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotBlank(dto.name);
		ArgumentChecks.isTrue(dto.compensationDays >= 0);
		
		this.dto=dto;
	}

	@Override
	public ContractTypeDto execute() throws BusinessException {
		Optional<ContractType> om = Factory.repository.forContractType().findByName(dto.name);
		BusinessChecks.isFalse(om.isPresent(), "El tipo de contrato no puede de existir");
				
		ContractType m = new ContractType(dto.name, dto.compensationDays);
		
		BusinessChecks.hasVersion(m, m.getVersion());

		Factory.repository.forContractType().add(m);
		
		dto.id = m.getId();
		return dto;
	}

}
