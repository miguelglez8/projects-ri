package uo.ri.cws.application.business.contract.crud.commands;

import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.business.workorder.WorkOrderService.WorkOrderBLDto;
import uo.ri.cws.application.business.workorder.assembler.WorkOrderAssembler;
import uo.ri.cws.application.persistence.PersistenceFactory;

public class DeleteContract implements Command<Void> {
	private String id;
	
	public DeleteContract(String id) {
		Argument.isNotEmpty(id);
		
		this.id=id;
	}

	public Void execute() throws BusinessException {
		if (PersistenceFactory.forContract().findById(id).isEmpty()) {
			throw new BusinessException("Cant update because dont exist");
		}
		
		compruebaExisteWorkOrders();
		
		if (PersistenceFactory.forPayroll().findByContractId(id).size() > 0) {
			throw new BusinessException("Cant update because there are payrolls for this contract");
		}

						
		PersistenceFactory.forContract().remove(id);
		return null;
	}

	private void compruebaExisteWorkOrders() throws BusinessException {
		Optional<ContractBLDto> contratosConId = ContractAssembler.toContractBLDto(PersistenceFactory.forContract().findById(id));
		Optional<MechanicBLDto> mecanicosConDni = MechanicAssembler.toMechanicBLDto(PersistenceFactory.forMechanic().findByDni(contratosConId.get().dni));
		if (contratosConId.isPresent() && 
				mecanicosConDni.isPresent() ) {
			List<WorkOrderBLDto> list = WorkOrderAssembler.toWorkOrderDtoList(PersistenceFactory.forWorkOrder().findByMechanic(
					mecanicosConDni.get().dni));
			if (list.size() > 0) {
				throw new BusinessException("Cant update because mechanic has workorders");	
			}
		}
	}
	
	

}
