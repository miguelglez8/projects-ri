package uo.ri.cws.application.service.invoice.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Client;
import uo.ri.util.assertion.ArgumentChecks;

public class FindPayMeansByClientDni implements Command<List<PaymentMeanDto>>{
	
	private String dni;
	
	public FindPayMeansByClientDni(String dni) {
		ArgumentChecks.isNotEmpty(dni);
		ArgumentChecks.isNotBlank(dni);

		this.dni = dni;
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		Optional<Client> cliente = Factory.repository.forClient().findByDni(dni);
		BusinessChecks.isTrue(cliente.isPresent(), "No existe ningún cliente con ese dni");
		return DtoAssembler.toPaymentMeanDtoList(new ArrayList<>(cliente.get().getPaymentMeans()));
	}

}