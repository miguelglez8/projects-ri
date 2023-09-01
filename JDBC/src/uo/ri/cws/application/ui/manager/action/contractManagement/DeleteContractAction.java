package uo.ri.cws.application.ui.manager.action.contractManagement;

import java.util.List;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.ui.util.Printer;

/**
 * Clase qe borra un contrato
 * 
 * @author Carlos
 *
 */	
public class DeleteContractAction implements Action {

	/**
	 * Borra el contrato dado un id pasado por consola
	 */
	@Override
	public void execute() throws BusinessException {
		displayAllContracts();

		String idContract = Console.readString("Contract identifier ");

		BusinessFactory.forContractService().deleteContract(idContract);

		Console.println("Contract no longer exists");

	}

	private void displayAllContracts() throws BusinessException {
		Printer.displayThisContractDetails();
		List<ContractSummaryBLDto> lista = BusinessFactory.forContractService().findAllContracts();
		for (int i=0; i < lista.size(); i++) {
			ContractBLDto c = ContractAssembler.toContractBLDto(lista.get(i));
			Printer.printContract(c);
		}
	}
}
