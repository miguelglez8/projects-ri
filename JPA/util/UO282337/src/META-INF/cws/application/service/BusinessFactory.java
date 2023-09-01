package uo.ri.cws.application.service;

import uo.ri.cws.application.ServiceFactory;
import uo.ri.cws.application.service.client.ClientCrudService;
import uo.ri.cws.application.service.client.ClientHistoryService;
import uo.ri.cws.application.service.contract.ContractService;
import uo.ri.cws.application.service.contract.crud.ContractServiceImpl;
import uo.ri.cws.application.service.contracttype.ContractTypeService;
import uo.ri.cws.application.service.contracttype.crud.ContractTypeServiceImpl;
import uo.ri.cws.application.service.invoice.InvoicingService;
import uo.ri.cws.application.service.invoice.crud.InvoicingServiceImpl;
import uo.ri.cws.application.service.mechanic.MechanicService;
import uo.ri.cws.application.service.mechanic.crud.MechanicServiceImpl;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.service.payroll.crud.PayrollServiceImpl;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.service.sparepart.SparePartCrudService;
import uo.ri.cws.application.service.vehicle.VehicleCrudService;
import uo.ri.cws.application.service.vehicle.crud.VehicleServiceImpl;
import uo.ri.cws.application.service.vehicleType.VehicleTypeCrudService;
import uo.ri.cws.application.service.workorder.CloseWorkOrderService;
import uo.ri.cws.application.service.workorder.ViewAssignedWorkOrdersService;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService;

public class BusinessFactory implements ServiceFactory {

    @Override
    public MechanicService forMechanicService() {
    	return new MechanicServiceImpl();
    }

    @Override
    public InvoicingService forCreateInvoiceService() {
    	return new InvoicingServiceImpl();
    }

    @Override
    public VehicleCrudService forVehicleCrudService() {
    	return new VehicleServiceImpl();
    }

    @Override
    public CloseWorkOrderService forClosingBreakdown() {
    	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public VehicleTypeCrudService forVehicleTypeCrudService() {
    	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public SparePartCrudService forSparePartCrudService() {
    	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ClientCrudService forClienteCrudService() {
    	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ClientHistoryService forClientHistoryService() {
    	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public WorkOrderCrudService forWorkOrderCrudService() {
    	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ViewAssignedWorkOrdersService forViewAssignedWorkOrdersService() {
    	throw new RuntimeException("Not yet implemented");
    }

    @Override
    public ContractService forContractService() {
    	return new ContractServiceImpl();
    }

    @Override
    public ContractTypeService forContractTypeService() {
    	return new ContractTypeServiceImpl();
    }

    @Override
    public PayrollService forPayrollService() {
    	return new PayrollServiceImpl();
    }

    @Override
    public ProfessionalGroupService forProfessionalGroupService() {
    	throw new RuntimeException("Not yet implemented");
    }
}

