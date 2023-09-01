package uo.ri.cws.application.business.contract.crud.commands;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class ListContractById implements Command<Optional<ContractBLDto>>{
	private String id;
	
	public ListContractById(String id) {
		Argument.isNotEmpty(id);
		
		this.id = id;
	}

	public Optional<ContractBLDto> execute() throws BusinessException {
		if (PersistenceFactory.forContract().findById(id).isEmpty()) {
			return Optional.empty();
		}
		Optional<ContractDALDto> co = PersistenceFactory.forContract().findById(id);
		ContractBLDto c = ContractAssembler.toContractDto(co.get());	
		añadirDatosContrato(co, c);
		return Optional.of(c);
	}

	private void añadirDatosContrato(Optional<ContractDALDto> co, ContractBLDto c) {
		c.dni=PersistenceFactory.forMechanic().findById(co.get().mechanic_id).get().dni;
		c.contractTypeName=PersistenceFactory.forContractType().findById(co.get().contractType_id).get().name;
	    c.professionalGroupName=PersistenceFactory.forProfessionalGroup().findById(co.get().professionalGroup_id).get().name;
	}
	
	
}
