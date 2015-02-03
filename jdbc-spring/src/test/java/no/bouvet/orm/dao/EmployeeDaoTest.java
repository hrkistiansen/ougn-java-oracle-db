package no.bouvet.orm.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.dao.EmployeeDao;
import no.bouvet.jdbc.domain.Employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Repository
public class EmployeeDaoTest {
    private static final int NO_EMPS = 107;
    private static final Long EMP_ID = 999L;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private EmployeeDao subject;

	@Before
	public void setUp() {
		// Status quo
		deleteTestEmployee();
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

	public void deleteTestEmployee() {
        String deleteJobHistorySql = "delete from job_history where employee_id = " + EMP_ID;
        String deleteEmployeeSql = "delete from employees where employee_id = " + EMP_ID;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update(deleteJobHistorySql);
		jdbcTemplate.update(deleteEmployeeSql);
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
