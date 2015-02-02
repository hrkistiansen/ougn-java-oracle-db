package no.bouvet.jdbc.dao;

import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.domain.Airplane;

public interface AirplaneDao {

	void save(Airplane airplane);
	
	void update(Airplane airplane);
	
	void delete(Airplane airplane);
	
	Airplane getById(Long airplaneId);
	
	List<Airplane> getAllAirplanes();
}
