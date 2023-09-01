package uo.ri.cws.application.service.mechanic;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.service.mechanic.MechanicService.MechanicDto;
import uo.ri.cws.domain.Mechanic;

public class DtoAssembler {

	public static List<MechanicDto> toDtoList(List<Mechanic> l) {
		List<MechanicDto> lista = new ArrayList<>();
		for (int i=0; i < l.size(); i++) {
			lista.add(toDto(l.get(i)));
		}
		return lista;
	}

	private static MechanicDto toDto(Mechanic mechanic) {
		MechanicDto m = new MechanicDto();
		m.name=mechanic.getName();
		m.surname=mechanic.getSurname();
		m.id=mechanic.getId();
		m.dni=mechanic.getDni();
		return m;
	}

}
