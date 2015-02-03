package no.bouvet.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;

import no.bouvet.orm.domain.Employee;

import org.hibernate.Session;

public class EmployeeDao {
	private EntityManager entityManager;

	public void create(Employee employee) {
		entityManager.getTransaction().begin();
		entityManager.persist(employee);
		entityManager.getTransaction().commit();
	}

	public void update(Employee employee) {
		entityManager.getTransaction().begin();
		entityManager.merge(employee);
		entityManager.getTransaction().commit();
	}

	public void delete(Employee employee) {
		entityManager.getTransaction().begin();
		entityManager.remove(employee);
		entityManager.getTransaction().commit();
	}

	public Employee getById(Long employeeId) {
		return entityManager.find(Employee.class, employeeId);
	}

	@SuppressWarnings("unchecked")
	public List<Employee> getAll() {
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Employee.class).list();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
