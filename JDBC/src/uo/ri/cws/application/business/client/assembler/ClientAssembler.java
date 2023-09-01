package uo.ri.cws.application.business.client.assembler;

import java.util.Optional;

import uo.ri.cws.application.business.client.ClientService.ClientBLDto;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientDALDto;

public class ClientAssembler {
	
	public static Optional<ClientBLDto> toClientDto(Optional<ClientDALDto> arg) {
		Optional<ClientBLDto> result = arg.isEmpty()?Optional.ofNullable(null)
				:Optional.ofNullable(toClientDto(arg.get()));
		return result;
	}
	
	private static ClientBLDto toClientDto(ClientDALDto cli) {
		ClientBLDto c = new ClientBLDto();
		
		c.id = cli.id;
		c.dni = cli.dni;
		c.name = cli.name;
		c.surname = cli.surname;
		c.email = cli.email;
		c.phone = cli.phone;
		c.addressCity = cli.city;
		c.addressStreet = cli.street;
		c.addressZipcode = cli.zipcode;
		c.version = cli.version;
		
		return c;		
	}
	

}
