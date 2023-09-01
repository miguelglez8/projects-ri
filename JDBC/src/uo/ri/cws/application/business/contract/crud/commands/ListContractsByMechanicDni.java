package uo.ri.cws.application.business.contract.crud.commands;

import java.util.ArrayList;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class ListContractsByMechanicDni implements Command<List<ContractSummaryBLDto>> {
	private String dni;
	
	public ListContractsByMechanicDni(String dni) {
		Argument.isNotEmpty(dni);
		
		this.dni = dni;
	}

	public List<ContractSummaryBLDto> execute() throws BusinessException {	
		if (PersistenceFactory.forMechanic().findByDni(dni).isEmpty()) {
			return new ArrayList<>();
		}
		MechanicBLDto m = MechanicAssembler.toMechanicBLDto(PersistenceFactory.forMechanic().findByDni(dni)).get();
		return summaryBLDtoList(PersistenceFactory.forContract().findByMechanicId(m.id));
		
	}

	private List<ContractSummaryBLDto> summaryBLDtoList(List<ContractDALDto> findByMechanicId) {
		List<ContractSummaryBLDto> list = new ArrayList<>();
		for (int i=0; i < findByMechanicId.size(); i++) {
			ContractDALDto co = findByMechanicId.get(i);
			ContractBLDto c = ContractAssembler.toContractDto(co);	
			c.dni=PersistenceFactory.forMechanic().findById(co.mechanic_id).get().dni;
		    list.add(ContractAssembler.toContractSummaryBLDto(c));
		}
		return list;
	}
	

}
