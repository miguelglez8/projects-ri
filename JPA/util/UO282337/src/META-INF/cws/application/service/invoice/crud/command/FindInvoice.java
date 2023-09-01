package uo.ri.cws.application.service.invoice.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.util.assertion.ArgumentChecks;

public class FindInvoice implements Command<Optional<InvoiceDto>> {

	private Long number;
	
	public FindInvoice(Long number) {
		ArgumentChecks.isNotNull(number);
		
		this.number = number;
	}

	@Override
	public Optional<InvoiceDto> execute() throws BusinessException {
		Optional<Invoice> invoice = Factory.repository.forInvoice().findByNumber(number);
		return invoice.isPresent() ?  Optional.of(DtoAssembler.toDto(invoice.get())) : Optional.empty();
	}

}
