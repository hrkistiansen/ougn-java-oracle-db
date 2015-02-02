package no.bouvet.orm.dao;

import java.util.List;

import no.bouvet.orm.domain.Airplane;

public interface AirplaneDao {
	void save(Airplane airplane);

	void update(Airplane airplane);

	void delete(Airplane airplane);

	Airplane getAirplaneById(Long airplaneId);

	List<Airplane> getAllAirplanes();

	void clearCaches();

	void flushClear();
}
