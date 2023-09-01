package uo.ri.cws.application.service.invoice.crud;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.crud.command.CreateInvoiceFor;
import uo.ri.cws.application.service.invoice.crud.command.FindInvoice;
import uo.ri.cws.application.service.invoice.crud.command.FindPayMeansByClientDni;
import uo.ri.cws.application.service.invoice.crud.command.FindWorkOrdersByClientDni;
import uo.ri.cws.application.service.invoice.crud.command.SettleInvoice;
import uo.ri.cws.application.util.command.CommandExecutor;

public class InvoicingServiceImpl implements InvoicingService {
	
	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public InvoiceDto createInvoiceFor(List<String> workOrderIds) throws BusinessException {
		return executor.execute(new CreateInvoiceFor( workOrderIds ));
	}

	@Override
	public List<InvoicingWorkOrderDto> findWorkOrdersByClientDni(String dni) throws BusinessException {
		return executor.execute(new FindWorkOrdersByClientDni( dni) );
	}

	@Override
	public Optional<InvoiceDto> findInvoice(Long number) throws BusinessException {
		return executor.execute(new FindInvoice( number) );
	}

	@Override
	public List<PaymentMeanDto> findPayMeansByClientDni(String dni) throws BusinessException {
		return executor.execute(new FindPayMeansByClientDni( dni) );
	}

	@Override
	public void settleInvoice(String invoiceId, Map<Long, Double> charges) throws BusinessException {		
		executor.execute(new SettleInvoice(invoiceId, charges));
	}

}
