package no.bouvet.orm.repository;

import no.bouvet.orm.domain.Employee;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long>{

}
