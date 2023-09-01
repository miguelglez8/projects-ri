package uo.ri.cws.application.business.contracttype.crud.commands;

import java.util.UUID;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contracttype.ContractTypeService.ContractTypeBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contracttype.assembler.ContractTypeAssembler;

public class AddContractType implements Command<ContractTypeBLDto> {

	private ContractTypeBLDto dto;

	public AddContractType(ContractTypeBLDto dto) {
		Argument.isNotNull(dto);
		Argument.isNotEmpty(dto.name);
		Argument.isTrue(dto.compensationDays >= 0);
		
		this.dto=dto;
	}

	@Override
	public ContractTypeBLDto execute() throws BusinessException {
		dto.id=UUID.randomUUID().toString();
		dto.version=1L;
		
		if (! PersistenceFactory.forContractType().findContractTypeByName(dto.name).isEmpty()) {
			throw new BusinessException("You can't add a contract with an existing name");
		}
		PersistenceFactory.forContractType().add(ContractTypeAssembler.toContractTypeDALDto(dto));
		return dto;
	}

}
