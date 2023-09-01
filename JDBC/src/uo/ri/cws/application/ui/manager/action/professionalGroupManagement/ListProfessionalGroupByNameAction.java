package uo.ri.cws.application.ui.manager.action.professionalGroupManagement;

import java.util.Optional;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class ListProfessionalGroupByNameAction implements Action {

	@Override
	public void execute() throws BusinessException {

		
		@SuppressWarnings("unused")
		String name = Console.readString("Professional group name ");
		

		Optional<ProfessionalGroupBLDto> result = null;
		Printer.printProfessionalGroup(result);
		
	}

}
