package uo.ri.ui.manager.contract;

import uo.ri.ui.manager.contract.action.AddContractAction;
import uo.ri.ui.manager.contract.action.DeleteContractAction;
import uo.ri.ui.manager.contract.action.FinishContractAction;
import uo.ri.ui.manager.contract.action.ListAllContractAction;
import uo.ri.ui.manager.contract.action.ListContractByIdAction;
import uo.ri.ui.manager.contract.action.ListContractByMechanicAction;
import uo.ri.ui.manager.contract.action.UpdateContractAction;
import uo.ri.util.menu.BaseMenu;

public class ContractMenu extends BaseMenu {

    public ContractMenu() {
	menuOptions = new Object[][] {
		{ "Manager > contract management", null },
		
		{ "Add contract ", AddContractAction.class },
		{ "Update contract ", UpdateContractAction.class },
		{ "Finish contract ", FinishContractAction.class },
		{ "Delete contract ", DeleteContractAction.class },
		{ "List all contract ", ListAllContractAction.class },
		{ "List contract by id", ListContractByIdAction.class },
		{ "List terminated contract by mechanic id", ListContractByMechanicAction.class },};
    }

}