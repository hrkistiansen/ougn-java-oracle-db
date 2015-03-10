package no.bouvet.orm.dao;

import java.util.List;

import com.avaje.ebean.Ebean;

import no.bouvet.orm.domain.Employee;

public class EmployeeDao {

	public void create(Employee employee) {
        employee.save();
	}

	public void update(Employee employee) {
		employee.save();
	}

	public void delete(Employee employee) {
		employee.delete();
	}

	public Employee getById(Long employeeId) {
		return Ebean.find(Employee.class, employeeId);
	}

	public List<Employee> getAll() {
		return Ebean.find(Employee.class).findList();
	}

//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
}
