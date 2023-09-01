package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class ContractJpaRepository 
	extends BaseJpaRepository<Contract>
	implements ContractRepository {

	@Override
	public List<Contract> findAllInForce() {
		return null;
	}

	@Override
	public List<Contract> findAllInForceThisMonth(LocalDate present) {
		return null;
	}

	@Override
	public List<Contract> findByMechanicId(String id) {
		return Jpa.getManager().createNamedQuery("Contract.findByMechanicId", Contract.class)
				.setParameter(1, id)
				.getResultStream()
				.toList();
	}
	
	@Override
	public List<Contract> findByContractTypeId(String id2Del) {
		return null;
	}
	
	@Override
	public List<Contract> findByProfessionalGroupId(String id) {
		return null;
	}

}
