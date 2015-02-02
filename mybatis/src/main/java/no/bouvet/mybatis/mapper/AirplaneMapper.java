package no.bouvet.mybatis.mapper;

import java.util.List;

import javax.sql.DataSource;

import no.bouvet.mybatis.domain.Airplane;

public interface AirplaneMapper {

	void create(Airplane airplane);
	
	void update(Airplane airplane);
	
	void delete(Airplane airplane);
	
	Airplane getAirplaneById(Long airplaneId);
	
	List<Airplane> getAllAirplanes();
}
