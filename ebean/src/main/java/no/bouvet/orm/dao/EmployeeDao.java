package no.bouvet.orm.dao;



public class EmployeeDao {

//	public void create(Employee employee) {
//
//	}
//
//	public void update(Employee employee) {
//		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
//		session.update(employee);
//		tx.commit();
//	}
//
//	public void delete(Employee employee) {
//		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
//		session.delete(employee);
//		tx.commit();
//	}
//
//	public Employee getById(Long employeeId) {
//		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
//		Employee employee = (Employee) session.get(Employee.class, employeeId);
//		tx.commit();
//		return employee;
//	}
//
//	public List<Employee> getAll() {
//		Session session = sessionFactory.getCurrentSession();
//		Transaction tx = session.beginTransaction();
//		List<Employee> employees = session.createQuery("FROM Employee").list();
//		tx.commit();
//		return employees;
//	}
//
//	public void setSessionFactory(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//	}
}
