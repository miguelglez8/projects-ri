package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.persistence.util.Conf;

public class MechanicGatewayImpl implements MechanicGateway {
		
	@Override
	public void add(MechanicDALDto mechanicDto) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_ADD"));
			
			pst.setString(1, mechanicDto.id);
			pst.setString(2, mechanicDto.dni);
			pst.setString(3, mechanicDto.name);
			pst.setString(4, mechanicDto.surname);
			pst.setLong(5, mechanicDto.version);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(pst);
		}							
	}

	@Override
	public void remove(String id) {
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_REMOVE"));
			pst.setString(1, id);
			
			pst.executeUpdate();
			
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(pst);
		}

	}

	@Override
	public void update(MechanicDALDto mechanic) {
		Connection c = null;
		PreparedStatement pst = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_UPDATE"));
			pst.setString(1, mechanic.name);
			pst.setString(2, mechanic.surname);
			pst.setString(3, mechanic.id);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(pst);
		}	
		
	}

	@Override
	public Optional<MechanicDALDto> findById(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_FIND_BY_ID"));
			pst.setString(1, id);
			
			rs = pst.executeQuery();
			return MechanicAssembler.toMechanicDALDto(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<MechanicDALDto> findAll() {		
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_FIND_ALL"));
			
			rs = pst.executeQuery();
			return MechanicAssembler.toMechanicDALDtoList(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public Optional<MechanicDALDto> findByDni(String dni) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			
			pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_FIND_BY_DNI"));
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			return MechanicAssembler.toMechanicDALDto(rs);
			
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

	@Override
	public List<MechanicDALDto> findMechanicsByIds(List<String> mechanic) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<MechanicDALDto> list = new ArrayList<>();
		try {
			c = Jdbc.getCurrentConnection();
			
			for (int i=0; i < mechanic.size(); i++) {
				pst = c.prepareStatement(Conf.getInstance().getProperty("TMECHANICS_FIND_BY_ID"));
				pst.setString(1, mechanic.get(i));
				rs = pst.executeQuery();
				
				Optional<MechanicDALDto> m = MechanicAssembler.toMechanicDALDto(rs);
				if (!m.isEmpty()) list.add(m.get());
			}
			
			return list;
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
		finally {
			Jdbc.close(rs,pst);
		}
	}

}
