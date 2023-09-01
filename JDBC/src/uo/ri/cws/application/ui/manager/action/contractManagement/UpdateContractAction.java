package uo.ri.cws.application.ui.manager.action.contractManagement;

import java.time.LocalDate;
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
 * Clase que actualiza un contrato dado su id
 * 
 * @author Carlos
 *
 */
public class UpdateContractAction implements Action {


	/**
	 * Actualiza un contrato cuyo id es pasado por consola, 
	 * el contrato es actualizado con una fecha 
	 * de finalizacion y un salario pasado por consola
	 */
	@Override
	public void execute() throws BusinessException {

		displayAllContracts();
		String id = Console.readString("Type contract id ");
		LocalDate endD = Console.readDate("Type contract start date ");
		double salary = Console.readDouble("Type annual base wage ");

		ContractBLDto cdto = new ContractBLDto();
		cdto.id = id;
		cdto.endDate = endD;
		cdto.annualBaseWage = salary;


		BusinessFactory.forContractService().updateContract(cdto);

		Console.println("Contract updated");
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
