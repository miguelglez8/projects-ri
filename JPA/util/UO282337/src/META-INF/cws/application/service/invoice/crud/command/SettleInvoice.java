package uo.ri.cws.application.service.invoice.crud.command;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Charge;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

public class SettleInvoice implements Command<Void> {

	private String invoiceId;
	private Map<Long, Double> charges; // paymentMeanId, AmountPaymentMean

	public SettleInvoice(String invoiceId, Map<Long, Double> charges) {
		ArgumentChecks.isNotEmpty(invoiceId);
		ArgumentChecks.isNotBlank(invoiceId);
		
		ArgumentChecks.isNotNull(charges);
		
		this.invoiceId = invoiceId;
		this.charges = charges;
	}
	
	@Override
	public Void execute() throws BusinessException {
		Optional<Invoice> i = Factory.repository.forInvoice().findById(invoiceId);
		BusinessChecks.isTrue(i.isPresent(), "No existe ningun invoice con esa id");
		BusinessChecks.isTrue(i.get().isNotSettled(), "El invoice ya está pagado");
		List<PaymentMean> lista = Factory.repository.forPaymentMean().findPaymentMeansByInvoiceId(invoiceId);
		BusinessChecks.isTrue(! lista.isEmpty(), "No existe ningun paymentMean con ese id de invoice");
		
		double total = calculaTotal(i.get());
		Invoice in = i.get();
		
		BusinessChecks.isTrue(Round.twoCents(Math.abs(total - in.getAmount())) <= 0.01,
				"the addition of all amounts charged to each payment mean does not equals the amount of the invoice");
		
		in.settle();

		return null;
	}

	private double calculaTotal(Invoice invoice) throws BusinessException {
		double total = 0;
		
		for (Entry<Long, Double> elemento : charges.entrySet()) {
			Long payId = elemento.getKey();
			Double amount = elemento.getValue();
						
			Optional<PaymentMean> op = Factory.repository.forPaymentMean().findById(String.valueOf(payId));
			BusinessChecks.isTrue(op.isPresent(), "No existe ningun paymentMean con ese id");
			BusinessChecks.isTrue(op.get().canPay(amount), "No existe ningun paymentMean con ese id");
			
			op.get().pay(amount); // pagamos la cantidad
			total = total + amount;
			
			Charge charge = new Charge(invoice, op.get(), amount);
			Factory.repository.forCharge().add(charge);
		}
		
		
		return total;
	}

}
