package uo.ri.cws.application.business.contract.crud.commands;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.assembler.ContractAssembler;

public class UpdateContract implements Command<Void> {
	private ContractBLDto contract; // actualizamos
	
	public UpdateContract(ContractBLDto dto) {
		Argument.isNotNull(dto);
		Argument.isNotEmpty(dto.id);
		Argument.isTrue(dto.annualBaseWage>0);
		
		this.contract = dto;
	}



	public Void execute() throws BusinessException {
		if (PersistenceFactory.forContract().findById(contract.id).isEmpty()) {
			throw new BusinessException("Cant update because dont exist");
		}
		if (contract.contractTypeName.equals("FIXED_TERM") && contract.endDate.isBefore(contract.startDate)) {
			throw new BusinessException("Cant update because type is FIXED_TERM and end date is earlier than startDate");
		}
		if (contract.state.equals(ContractState.TERMINATED)) {
			throw new BusinessException("Cant update because the contract is no longer in force");
		}
		PersistenceFactory.forContract().update(ContractAssembler.toContractDALDto(contract));
		return null;
	}

}
