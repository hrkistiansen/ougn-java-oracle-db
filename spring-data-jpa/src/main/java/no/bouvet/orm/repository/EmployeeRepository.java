package no.bouvet.orm.repository;

import org.springframework.data.repository.CrudRepository;

import no.bouvet.orm.domain.Airplane;
import no.bouvet.orm.domain.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
