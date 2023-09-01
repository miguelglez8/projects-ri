package uo.ri.ui.manager.mechanic;

import uo.ri.ui.manager.mechanic.action.AddMechanicAction;
import uo.ri.ui.manager.mechanic.action.DeleteMechanicAction;
import uo.ri.ui.manager.mechanic.action.ListMechanicsAction;
import uo.ri.ui.manager.mechanic.action.ListMechanicsByIdAction;
import uo.ri.ui.manager.mechanic.action.ListMechanicsInForceAction;
import uo.ri.ui.manager.mechanic.action.ListMechanicsInProfessionalGroupsAction;
import uo.ri.ui.manager.mechanic.action.ListMechanicsWithContractInForceInContractTypeAction;
import uo.ri.ui.manager.mechanic.action.UpdateMechanicAction;
import uo.ri.util.menu.BaseMenu;

public class MechanicsMenu extends BaseMenu {

	public MechanicsMenu() {
		menuOptions = new Object[][] { 
			{"Manager > Mechanics management", null},
			
			{ "Add mechanic", 		AddMechanicAction.class }, 
			{ "Update mechanic", 	UpdateMechanicAction.class }, 
			{ "Disable mechanic", 	DeleteMechanicAction.class }, 
			{ "List mechanics", 	ListMechanicsAction.class },
			{ "List mechanic by id",ListMechanicsByIdAction.class },
			{ "List mechanics with contract in force",
									ListMechanicsInForceAction.class },
			{ "List mechanics with contract in force in contract type",
									ListMechanicsWithContractInForceInContractTypeAction.class },
			{ "List mechanics in professional group",
									ListMechanicsInProfessionalGroupsAction.class },
		};
	}

}
