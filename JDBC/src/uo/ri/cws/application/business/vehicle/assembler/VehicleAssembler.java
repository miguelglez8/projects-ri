package uo.ri.cws.application.business.vehicle.assembler;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.vehicle.VehicleService.VehicleBLDto;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleDALDto;

public class VehicleAssembler {
	
	private static VehicleBLDto toVehicleDto(VehicleDALDto cli) {
		VehicleBLDto v = new VehicleBLDto();

		v.id=cli.id;
		v.clientId=cli.client_id;
		v.vehicletypeId=cli.vehicletype_id;
		v.plateNumber=cli.platenumber;
		v.make=cli.make;
		v.model=cli.model;
		v.version=cli.version;
		
		return v;		
	}


	public static List<VehicleBLDto> toVehicleDtoList(List<VehicleDALDto> arg) {
		List<VehicleBLDto> result = new ArrayList<VehicleBLDto>();
		for (VehicleDALDto mr : arg)
			result.add(toVehicleDto(mr));
		return result;
	}

}
