package no.bouvet.orm.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import no.bouvet.orm.domain.Employee;
import no.bouvet.orm.util.HibernateUtil;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class EmployeeDaoTest {
    private static final int NO_EMPS = 107;
    private static final Long EMP_ID = 999L;

    private SessionFactory sessionFactory;
	private EmployeeDao subject;

	@Before
	public void setUp() {
		// Create sessionFactory and assign to subject
        sessionFactory = HibernateUtil.getSessionFactory();
        subject = new EmployeeDao();
        subject.setSessionFactory(sessionFactory);

        // Status quo
        deleteEmployeeBeforeTest();
	}

	@Test
	public void createEmployee() {
		// Given
		Employee employee = createTestEmployee();

		// When
		subject.create(employee);

		// Then
		assertEquals(NO_EMPS + 1, subject.getAll().size());
	}

	@Test
	public void selectAllEmployees() {
		// Given

		// When
		List<Employee> result = subject.getAll();

		// Then
		assertEquals(NO_EMPS, result.size());
	}

	@Test
	public void selectEmployeeById() {
		// Given
		Employee employee = createTestEmployee();
		subject.create(employee);

		// When
		Employee result = subject.getById(EMP_ID);

		// Then
		assertEquals(employee.getFirstName(), result.getFirstName());
	}

	@Test
	public void updateEmployee() {
		// Given
		Employee employee = createTestEmployee();
		subject.create(employee);

		employee.setFirstName("Oppdatert navn");

		// When
		subject.update(employee);

		// Then
		Employee fetchedEmployeeAfterUpdate = subject.getById(EMP_ID);
		assertEquals(employee.getFirstName(), fetchedEmployeeAfterUpdate.getFirstName());
	}

	@Test
	public void deleteEmployee() {
		// Given
		Employee employee = createTestEmployee();
		subject.create(employee);

		// When
		subject.delete(employee);

		// Then
		assertEquals(NO_EMPS, subject.getAll().size());
	}

	public void deleteEmployeeBeforeTest() {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        SQLQuery q = session.createSQLQuery("delete from job_history where employee_id = " + EMP_ID);
        q.executeUpdate();
        SQLQuery q2 = session.createSQLQuery("delete from employees where employee_id = " + EMP_ID);
        q2.executeUpdate();
        tx.commit();
	}

    private Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(EMP_ID);
        employee.setFirstName("Test");
        employee.setLastName("Testesen");
        employee.setEmail("EMAIL");
        employee.setPhoneNumber("555");
        employee.setHireDate(getYesterday());
        employee.setJobId("ST_CLERK");
        employee.setSalary(BigDecimal.valueOf(1000L));
        employee.setCommissionPct(Double.valueOf("0.1"));
        employee.setManagerId(100L);
        employee.setDepartmentId(80L);
        return employee;
    }

    private Date getYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        return new Date(yesterday.getTimeInMillis());
    }
}
