package uo.ri.cws.application.business;


import exception.NotYetImplementedException;
import uo.ri.cws.application.business.client.ClientService;
import uo.ri.cws.application.business.contract.ContractService;
import uo.ri.cws.application.business.contract.crud.ContractServiceImpl;
import uo.ri.cws.application.business.contracttype.ContractTypeService;
import uo.ri.cws.application.business.contracttype.crud.ContractTypeServiceImpl;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.MechanicServiceImpl;
import uo.ri.cws.application.business.payroll.PayrollService;
import uo.ri.cws.application.business.payroll.crud.PayrollServiceImpl;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService;

public class BusinessFactory {


	public static MechanicService forMechanicService() {
		return new MechanicServiceImpl();
	}

	public static InvoicingService forInvoicingService() {
		return new InvoicingServiceImpl();
	}

	public static ContractService forContractService() {
		return new ContractServiceImpl();
	}
	
	public static ContractTypeService forContractTypeService() {
		return new ContractTypeServiceImpl();
	}

	public static PayrollService forPayrollService() {
		return new PayrollServiceImpl();
	}

	public static ClientService forClientService() {
		throw new NotYetImplementedException();
	}

	public static ProfessionalGroupService forProfessionalGroupService() {
		throw new NotYetImplementedException();
	}

}
