package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.util.Conf;

public class ContractGatewayImpl implements ContractGateway {

	@Override
	public void add(ContractDALDto t) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_ADD"));
			pst.setString(1, t.id);
			pst.setDate(2, Date.valueOf(t.startDate));
			if(t.endDate == null) {
                pst.setDate(3, null);
            }
            else {
                pst.setDate(3,Date.valueOf(t.endDate));
            }
			pst.setString(4, t.contractType_id);
			pst.setString(5, t.professionalGroup_id);
			pst.setDouble(6, t.annualBaseWage);
			pst.setString(7, t.state.toString());
			pst.setString(8, t.mechanic_id);
			pst.setLong(9, t.version);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}						
	}

	@Override
	public void remove(String id) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
					
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_REMOVE"));
					
			pst.setString(1, id);
					
			pst.executeUpdate();
					
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}			
	}

	@Override
	public void update(ContractDALDto t) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
					
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_UPDATE"));
					
			pst.setDouble(1, t.annualBaseWage);
			if(t.endDate == null) {
                pst.setDate(2, null);
            }
            else {
                pst.setDate(2,Date.valueOf(t.endDate));
            }
			pst.setString(3, t.state.toString());
			pst.setDouble(4, t.settlement);
			pst.setString(5, t.id);

					
			pst.executeUpdate();
					
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}				
	}

	@Override
	public Optional<ContractDALDto> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FIND_BY_ID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			return uo.ri.cws.application.persistence.contract.assembler.ContractAssembler.resultSetToContractDALDtoOptional(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<ContractDALDto> findAll() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FIND_ALL"));
			
			rs = pst.executeQuery();
			
			return uo.ri.cws.application.persistence.contract.assembler.ContractAssembler.resultSetToContractDALDtoList(rs);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<ContractDALDto> findByMechanicId(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FIND_BY_MECHANIC_ID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return uo.ri.cws.application.persistence.contract.assembler.ContractAssembler.resultSetToContractDALDtoList(rs);
			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<ContractDALDto> findContractsInForce() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FIND_CONTRACTS_IN_FORCE"));
			
			rs = pst.executeQuery();
			
			return uo.ri.cws.application.persistence.contract.assembler.ContractAssembler.resultSetToContractDALDtoList(rs);
			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<ContractDALDto> findByContractTypeId(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FIND_BY_CONTRACT_TYPE_ID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			
			return uo.ri.cws.application.persistence.contract.assembler.ContractAssembler.resultSetToContractDALDtoList(rs);
			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<ContractDALDto> findByProfessionalGroupId(String name) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FIND_BY_PROFESSIONAL_GROUP_ID"));
			pst.setString(1, name);
			
			rs = pst.executeQuery();
			
			return uo.ri.cws.application.persistence.contract.assembler.ContractAssembler.resultSetToContractDALDtoList(rs);
			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public Optional<ContractDALDto> findInForceByIdMechanic(String idMecanico) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTS_FIND_IN_FORCE_BY_ID_MECHANIC"));
			pst.setString(1, idMecanico);
			
			rs = pst.executeQuery();
			return uo.ri.cws.application.persistence.contract.assembler.ContractAssembler.resultSetToContractDALDtoOptional(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	

}
