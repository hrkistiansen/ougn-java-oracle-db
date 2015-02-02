package no.bouvet.orm.repository;

import org.springframework.data.repository.CrudRepository;

import no.bouvet.orm.domain.Airplane;

public interface AirplaneRepository extends CrudRepository<Airplane, Long>{

}
