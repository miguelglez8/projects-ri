package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.assembler.WorkOrderAssembler;

public class WorkOrderGatewayImpl implements WorkOrderGateway {
	

	@Override
	public void add(WorkOrderDALDto t) {
		
	}

	@Override
	public void remove(String id) {
		
	}

	@Override
	public void update(WorkOrderDALDto t) {
		PreparedStatement pst = null;
		try {
			pst = Jdbc.getCurrentConnection().prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_UPDATE"));
	
			pst.setString(1, t.state);
			pst.setString(2, t.invoice_id);
			pst.setString(3, t.id);
	
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException();
		}
		finally {
		    Jdbc.close(pst);
		}
	}
		 
	

	@Override
	public Optional<WorkOrderDALDto> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FIND_BY_ID"));
			pst.setString(1, id);
			rs = pst.executeQuery();
			
			return WorkOrderAssembler.toWorkOrderDALDto(rs);
			
		}catch (SQLException e) {
			throw new PersistenceException(e);
		}finally {
			Jdbc.close(rs, pst);
		}
	}

	@Override
	public List<WorkOrderDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<WorkOrderDALDto> findNotInvoicedForVehicles(List<String> vehicleIds) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<WorkOrderDALDto> list = new ArrayList<>();
		try {
		    pst = Jdbc.getCurrentConnection().prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FIND_NOT_INVOICED_FOR_VEHICLES"));

		    for (String workOrderID : vehicleIds) {
		    	pst.setString(1, workOrderID);

		    	rs = pst.executeQuery();
		    	
                while(rs.next()) {
                    list.add(WorkOrderAssembler.resultSetToWorkOrderDALDto(rs));
                }
		    }
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
		    Jdbc.close(pst);
		}
		return list;
	}

	@Override
	public List<WorkOrderDALDto> findByVehicleId(String vehicleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findByInvoice(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findInvoiced() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findNotInvoicedWorkOrders() {
    	Connection c = null;
    	PreparedStatement pst = null;
    	ResultSet rs = null;

    	try {
    	    c = Jdbc.getCurrentConnection();

    	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FIND_NOT_INVOICED_WORKORDERS"));

    	    rs = pst.executeQuery();
    	    return WorkOrderAssembler.toWorkOrderDALDtoList(rs);
    	} catch (SQLException e) {
    	    throw new PersistenceException(e);
    	} finally {
    	   Jdbc.close(rs,pst);
    	}
	}

	@Override
	public List<WorkOrderDALDto> findByMechanic(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
		    c = Jdbc.getCurrentConnection();
		    
    	    pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_FIND_BY_MECHANIC"));
    	    pst.setString(1, id);
    	    
    	    rs = pst.executeQuery();
    	    
    	    return WorkOrderAssembler.toWorkOrderDALDtoList(rs);
    	     
		} catch (SQLException e) {
			throw new PersistenceException(e);
		} finally {
		    Jdbc.close(pst);
		}
	}

	@Override
	public List<WorkOrderDALDto> findByIds(List<String> arg) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
