package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.persistence.util.Conf;

public class PayrollGatewayImpl implements PayrollGateway {

	@Override
	public void add(PayrollDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PayrollDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<PayrollDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<PayrollDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PayrollDALDto> findByContractId(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
		    c = Jdbc.getCurrentConnection();
		    
    	    pst = c.prepareStatement(Conf.getInstance().getProperty("TPAYROLLS_FIND_BY_CONTRACT_ID"));
    	    pst.setString(1, id);
    	    
    	    rs = pst.executeQuery();
    	    
    	    return PayrollAssembler.resultSetToPayrollDALDtoList(rs);
    	     
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
		    Jdbc.close(pst);
		}
	}

}
