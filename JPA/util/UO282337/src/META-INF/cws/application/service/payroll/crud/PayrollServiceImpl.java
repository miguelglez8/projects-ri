package uo.ri.cws.application.service.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService;

public class PayrollServiceImpl implements PayrollService {

	@Override
	public void generatePayrolls() throws BusinessException {
	}

	@Override
	public void generatePayrolls(LocalDate present) throws BusinessException {		
	}

	@Override
	public void deleteLastPayrollFor(String mechanicId) throws BusinessException {		
	}

	@Override
	public void deleteLastPayrolls() throws BusinessException {		
	}

	@Override
	public Optional<PayrollBLDto> getPayrollDetails(String id) throws BusinessException {
		return Optional.empty();
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrolls() throws BusinessException {
		return null;
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrollsForMechanic(String id) throws BusinessException {
		return null;
	}

	@Override
	public List<PayrollSummaryBLDto> getAllPayrollsForProfessionalGroup(String name) throws BusinessException {
		return null;
	}

}
