package uo.ri.cws.application.service.contracttype.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.contracttype.ContractTypeService.ContractTypeDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ContractType;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateContractType implements Command<Void> {

	private ContractTypeDto dto;

	public UpdateContractType(ContractTypeDto dto) {
		ArgumentChecks.isNotNull(dto);
		
		ArgumentChecks.isNotEmpty(dto.name);
		ArgumentChecks.isNotBlank(dto.name);
		
		ArgumentChecks.isTrue(dto.compensationDays >= 0);
		
		this.dto=dto;
	}
	
	/**
	 
	 * @throws BusinessException if:
	 * 		- the contract type does not exist, or
	 */
	
	@Override
	public Void execute() throws BusinessException {
		Optional<ContractType> om = Factory.repository.forContractType().findByName(dto.name);
		BusinessChecks.exists(om, "El tipo de contrato debe de existir");
		
		ContractType m = om.get();
				
		m.setCompensationDays(dto.compensationDays);
				
		return null;
	}
	

}
