package no.bouvet.mybatis.mapper;

import java.util.List;

import no.bouvet.mybatis.domain.Employee;

public interface EmployeeMapper {

    void create(Employee employee);

    void update(Employee employee);

    void delete(Employee employee);

    Employee getById(Long employeeId);

    List<Employee> getAll();
}
