package uo.ri.cws.application.business.contract.crud.commands;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

public class ListAllContract implements Command<List<ContractSummaryBLDto>> {
	
	public List<ContractSummaryBLDto> execute() throws BusinessException {
		List<ContractDALDto> list = PersistenceFactory.forContract().findAll();
		return toContractSummaryDtoList(list);
	}
	
	private List<ContractSummaryBLDto> toContractSummaryDtoList(List<ContractDALDto> list) {
		List<ContractSummaryBLDto> l = new ArrayList<>();
		for (int i=0; i < list.size(); i++) {
			ContractDALDto c = list.get(i);
			ContractBLDto co = ContractAssembler.toContractDto(c);	
			co.dni=PersistenceFactory.forMechanic().findById(c.mechanic_id).get().dni;
		    l.add(ContractAssembler.toContractSummaryBLDto(co));
		}
		return l;
	}
	
}
