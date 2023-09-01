package uo.ri.ui.manager.contractType;

import uo.ri.ui.manager.contractType.action.AddContractTypeAction;
import uo.ri.ui.manager.contractType.action.DeleteContractTypeAction;
import uo.ri.ui.manager.contractType.action.ListAllContractTypeAction;
import uo.ri.ui.manager.contractType.action.ListContractTypeByNameAction;
import uo.ri.ui.manager.contractType.action.UpdateContractTypeAction;
import uo.ri.util.menu.BaseMenu;

public class ContractTypeMenu extends BaseMenu {

    public ContractTypeMenu() {
	menuOptions = new Object[][] {
		{ "Manager > contract type management", null },
		
		{ "Add contract type ", AddContractTypeAction.class },
		{ "Update contract type ", UpdateContractTypeAction.class },
		{ "Delete contract type ", DeleteContractTypeAction.class },
		{ "List all contract types", ListAllContractTypeAction.class },
		{ "List contract type by name", ListContractTypeByNameAction.class },};
    }

}