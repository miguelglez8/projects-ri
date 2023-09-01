package uo.ri.cws.application.ui.manager.action.contractManagement;

import java.util.List;
import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.ui.util.Printer;

/**
 * Clase que encuentra un contrato dado un id
 * 
 * @author Carlos
 *
 */
public class ListContractByIdAction implements Action {

	/**
	 * Encuentra un contrato dado un id de contrato pasado por consola
	 */
	@Override
	public void execute() throws BusinessException {
		displayAllContracts();

		String id = Console.readString("Type id");

		Optional<ContractBLDto> opContract =  BusinessFactory.forContractService().findContractById(id);

		if (opContract.isPresent()) {
			Printer.displayThisContractDetails();
			Printer.printContract(opContract.get());
		}else {
			Console.println("There is no contract with this id");
		}
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
