package no.bouvet.jdbc.dao;

import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.domain.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class EmployeeDao {

    private SimpleJdbcInsert insertEmployee;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public void create(Employee employee) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(employee);
        insertEmployee.execute(parameters);
    }

    public void update(Employee employee) {
        String updateStatement = "update employees set first_name = :firstName, last_name = :lastName, email = :email, phone_number = :phoneNumber, hire_date = :hireDate, job_id = :jobId, salary = :salary, commission_pct = :commissionPct, manager_id = :managerId, department_id = :departmentId where employee_id = :employeeId";
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(employee);

        namedParameterJdbcTemplate.update(updateStatement, parameters);
    }

    public void delete(Employee employee) {
        String query = "delete from employees where employee_id = :employeeId";
        SqlParameterSource parameters = new MapSqlParameterSource("employeeId", employee.getEmployeeId());

        namedParameterJdbcTemplate.update(query, parameters);
    }

    public Employee getById(Long employeeId) {
        String query = "select * from employees where employee_id = :employeeId";
        SqlParameterSource parameters = new MapSqlParameterSource("employeeId", employeeId);

        return namedParameterJdbcTemplate.queryForObject(query, parameters, new BeanPropertyRowMapper<>(Employee.class));
    }

    public List<Employee> getAll() {
        String query = "select * from employees";

        return namedParameterJdbcTemplate.getJdbcOperations().query(query, new BeanPropertyRowMapper<>(Employee.class));
    }

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.insertEmployee = new SimpleJdbcInsert(dataSource).withTableName("employees");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
}
