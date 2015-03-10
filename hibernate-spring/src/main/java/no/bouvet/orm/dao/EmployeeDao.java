package no.bouvet.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import no.bouvet.orm.domain.Employee;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDao {

    @PersistenceContext
    private EntityManager entityManager;

	public void create(Employee employee) {
		entityManager.persist(employee);
	}

	public void update(Employee employee) {
		entityManager.merge(employee);
	}

	public void delete(Employee employee) {
		entityManager.remove(employee);
	}

	public Employee getById(Long employeeId) {
		return entityManager.find(Employee.class, employeeId);
	}

	@SuppressWarnings("unchecked")
    public List<Employee> getAll() {
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Employee.class).list();
	}

}
