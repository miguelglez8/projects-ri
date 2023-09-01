package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.Conf;

public class InvoiceGatewayImpl implements InvoiceGateway {

	@Override
	public void add(InvoiceDALDto t) {
		Connection c = null;
		PreparedStatement pst = null;
	
		try {
			c = Jdbc.getCurrentConnection();
		    pst = c.prepareStatement(Conf.getInstance().getProperty("TINVOICES_ADD"));
		    
		    pst.setString(1, t.id);
		    pst.setLong(2, t.number);
		    pst.setDate(3, java.sql.Date.valueOf(t.date));
		    pst.setDouble(4, t.vat);
		    pst.setDouble(5, t.amount);
		    pst.setString(6, t.state);
		    pst.setLong(7, t.version);
		    		    
		    pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException();
		} finally {
		    Jdbc.close(pst);
		}
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(InvoiceDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<InvoiceDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<InvoiceDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceDALDto> findByNumber(Long number) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Long getNextInvoiceNumber() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
		    pst = Jdbc.getCurrentConnection().prepareStatement(Conf.getInstance().getProperty("TINVOICES_GET_NEXT_INVOICE_NUMBER"));
		    rs = pst.executeQuery();

		    if (rs.next()) {
		    	return rs.getLong(1) + 1; // +1, next
		    } else { // there is none yet
		    	return 1L;
		    }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
		    Jdbc.close(rs,pst);
		}
	}

	
   

}
