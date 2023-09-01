package uo.ri.cws.application.service.invoice.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.DtoAssembler;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoicingWorkOrderDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class FindWorkOrdersByClientDni implements Command<List<InvoicingWorkOrderDto>> {

	private String dni;
	
	public FindWorkOrdersByClientDni(String dni) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotBlank(dni);
		
		this.dni = dni;
	}

	@Override
	public List<InvoicingWorkOrderDto> execute() throws BusinessException {
		Optional<Mechanic> mecanico = Factory.repository.forMechanic().findByDni(dni);
		BusinessChecks.isTrue(mecanico.isPresent(), "El mecánico no existe");
		return DtoAssembler.toWorkOrderDtoList(new ArrayList<>(mecanico.get().getAssigned()));
	}

}
