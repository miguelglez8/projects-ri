package uo.ri.cws.application.service.invoice.crud.command;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private List<String> workOrderIds;
	private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
	private InvoiceRepository invsRepo = Factory.repository.forInvoice();

	public CreateInvoiceFor(List<String> workOrderIds) {
		ArgumentChecks.isNotNull(workOrderIds);
		ArgumentChecks.isTrue(!workOrderIds.isEmpty());
		for (String w : workOrderIds) {
			ArgumentChecks.isNotEmpty(w);
			ArgumentChecks.isNotBlank(w);
		}
		this.workOrderIds = workOrderIds;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		List<WorkOrder> wo = wrkrsRepo.findByIds(workOrderIds);
		BusinessChecks.isTrue(wo.size() == workOrderIds.size(), "Tienen que tener el mismo tamaño");
		BusinessChecks.isTrue(allFinished(wo), "Todas tienen que estar acabadas");
		
		Long number = invsRepo.getNextInvoiceNumber();
		number = number == null ? 1 : number;
		
		Invoice i = new Invoice(number, wo);
		invsRepo.add(i);
		
		return DtoAssembler.toDto(i);
	}
	
	private boolean allFinished(List<WorkOrder> wo) {
		for (int i=0; i < wo.size(); i++) {
			if (! wo.get(i).isFinished()) {
				return false;
			}
		}
		return true;
	}

}
