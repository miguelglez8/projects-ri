package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;

public class PayrollJpaRepository 
	extends BaseJpaRepository<Payroll> 
	implements PayrollRepository {

	@Override
	public List<Payroll> findByContract(String contractId) {
		return null;
	}

	@Override
	public List<Payroll> findCurrentMonthPayrolls() {
		return null;
	}

	@Override
	public Optional<Payroll> findCurrentMonthByContractId(String contractId) {
		return Optional.empty();
	}

}
