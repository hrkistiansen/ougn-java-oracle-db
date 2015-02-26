package no.bouvet.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import no.bouvet.orm.domain.Employee;

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
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Employee.class));
        return entityManager.createQuery(cq).getResultList();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
