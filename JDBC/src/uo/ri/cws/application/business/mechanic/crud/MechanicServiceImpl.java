package uo.ri.cws.application.business.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.commands.AddMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.DeleteMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.FindAllMechanic;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicByDni;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicById;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicsInForce;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicsInProfessionalGroups;
import uo.ri.cws.application.business.mechanic.crud.commands.FindMechanicsWithContractInForceInContractType;
import uo.ri.cws.application.business.mechanic.crud.commands.UpdateMechanic;
import uo.ri.cws.application.business.util.command.CommandExecutor;

public class MechanicServiceImpl implements MechanicService {

	@Override
	public MechanicBLDto addMechanic(MechanicBLDto mechanic) throws BusinessException {
		return new CommandExecutor().execute(new AddMechanic(mechanic));
	}

	@Override
	public void deleteMechanic(String idMechanic) throws BusinessException {
		new CommandExecutor().execute(new DeleteMechanic(idMechanic));
		
	}

	@Override
	public void updateMechanic(MechanicBLDto mechanic) throws BusinessException {
		new CommandExecutor().execute(new UpdateMechanic(mechanic));
		
	}

	@Override
	public Optional<MechanicBLDto> findMechanicById(String idMechanic) throws BusinessException {
		return new CommandExecutor().execute(new FindMechanicById(idMechanic));
	}

	@Override
	public List<MechanicBLDto> findAllMechanics() throws BusinessException {
		return new CommandExecutor().execute(new FindAllMechanic());
	}

	@Override
	public Optional<MechanicBLDto> findMechanicByDni(String dniMechanic) throws BusinessException {
		return new CommandExecutor().execute(new FindMechanicByDni(dniMechanic));
	}

	@Override
	public List<MechanicBLDto> findMechanicsInForce() throws BusinessException {
		return new CommandExecutor().execute(new FindMechanicsInForce());
	}

	@Override
	public List<MechanicBLDto> findMechanicsWithContractInForceInContractType(String name) throws BusinessException {
		return new CommandExecutor().execute(new FindMechanicsWithContractInForceInContractType(name));
	}

	@Override
	public List<MechanicBLDto> findMechanicsInProfessionalGroups(String name) throws BusinessException {
		return new CommandExecutor().execute(new FindMechanicsInProfessionalGroups(name));
	}

	

}
