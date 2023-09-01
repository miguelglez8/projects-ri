package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.persistence.util.Conf;

public class ContractTypeGatewayImpl implements ContractTypeGateway {

	
	@Override
	public void add(ContractTypeDALDto t) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTTYPES_ADD"));
			
			pst.setString(1, t.name);
			pst.setDouble(2, t.compensationDays);
			pst.setString(3, t.id);
			pst.setLong(4, t.version);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}						
	}

	@Override
	public void remove(String name) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
					
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTTYPES_REMOVE"));
					
			pst.setString(1, name);
					
			pst.executeUpdate();
					
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}			
	}

	@Override
	public void update(ContractTypeDALDto t) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
					
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTTYPES_UPDATE"));
			
			pst.setDouble(1, t.compensationDays);
			pst.setString(2, t.name);
					
			pst.executeUpdate();
					
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(pst);
		}				
	}

	@Override
	public Optional<ContractTypeDALDto> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTTYPES_FIND_BY_ID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			return ContractTypeAssembler.resultSetToContractTypeDALDtoOptional(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<ContractTypeDALDto> findAll() {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTTYPES_FIND_ALL"));
			
			rs = pst.executeQuery();
			
			return ContractTypeAssembler.resultSetToListContractTypeListDALDto(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public Optional<ContractTypeDALDto> findContractTypeByName(String name) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTTYPES_FIND_CONTRACT_TYPE_BY_NAME"));
			pst.setString(1, name);
			
			rs = pst.executeQuery();
			
			return ContractTypeAssembler.resultSetToContractTypeDALDtoOptional(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}



	
}
