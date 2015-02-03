package no.bouvet.orm.domain;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import no.bouvet.orm.dao.EmployeeDao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class EmployeeDaoTest {
    private static final int NO_EMPS = 107;
    private static final Long EMP_ID = 999L;

    @Autowired
    private EmployeeDao subject;

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Before
	public void setUp() {
		deleteEmployeeBeforeTest();
	}

    @Test
    @Transactional
    public void createEmployee() {
        // Given
        Employee employee = createTestEmployee();

        // When
        subject.create(employee);

        // Then
        assertEquals(NO_EMPS + 1, subject.getAll().size());
    }

    @Test
    @Transactional
    public void selectAllEmployees() {
        // Given

        // When
        List<Employee> result = subject.getAll();

        // Then
        assertEquals(NO_EMPS, result.size());
    }

    @Test
    @Transactional
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
    @Transactional
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
    @Transactional
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
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Session session = (Session) entityManager.getDelegate();
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
