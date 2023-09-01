package uo.ri.cws.application.persistence.client.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.util.Conf;

public class ClientGatewayImpl implements ClientGateway {

	
	@Override
	public void add(uo.ri.cws.application.persistence.client.ClientGateway.ClientDALDto t) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(uo.ri.cws.application.persistence.client.ClientGateway.ClientDALDto t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<uo.ri.cws.application.persistence.client.ClientGateway.ClientDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public List<uo.ri.cws.application.persistence.client.ClientGateway.ClientDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<uo.ri.cws.application.persistence.client.ClientGateway.ClientDALDto> findByDni(String clientdni) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = Jdbc.getCurrentConnection().prepareStatement(Conf.getInstance().getProperty("TCLIENTS_FIND_BY_DNI"));
			
			pst.setString(1, clientdni);
			
			rs = pst.executeQuery();
			
			return uo.ri.cws.application.persistence.client.assembler.ClientAssembler.toClientDALDto(rs);
			
		}catch(SQLException e) {
			throw new PersistenceException(e);
		} finally {
			Jdbc.close(rs, pst);
		}
	}
	
	public class ClientDALDto {

		public String id;
		public Long version;
		
		public String dni;
		public String email;
		public String name;
		public String surname;
		public String phone;
		public String city;
		public String street;
		public String zipcode;
		
	}

	

	

}
