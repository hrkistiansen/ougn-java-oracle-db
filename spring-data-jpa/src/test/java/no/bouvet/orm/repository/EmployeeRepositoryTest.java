package no.bouvet.orm.repository;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import no.bouvet.orm.Application;
import no.bouvet.orm.domain.Employee;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class EmployeeRepositoryTest {

    private static final int NO_EMPS = 107;
    private static final Long EMP_ID = 999L;

    @Autowired
	private EmployeeRepository subject;
    
    @Autowired
    private DataSource dataSource;

	@Before
	public void setUp() {
		deleteEmployeeBeforeTest();
	}

	@Test
	public void createEmployee() {
		// Given
		Employee employee = createPersistantEmployee();

		// When
		Employee result = subject.findOne(employee.getEmployeeId());

		// Then
		assertEquals(employee.getEmployeeId(), result.getEmployeeId());
	}

	@Test
	public void selectAllEmployees() {
		// Given
		Employee employee = createPersistantEmployee();

		// When
		List<Employee> result = constructList(subject.findAll());

		// Then
		assertEquals(NO_EMPS + 1, result.size());
	}

	@Test
	public void selectEmployeeById() {
		// Given
		Employee employee = createPersistantEmployee();

		// When
		Employee result = subject.findOne(employee.getEmployeeId());

		// Then
		assertEquals(employee.getFirstName(), result.getFirstName());
	}

	@Test
	public void updateEmployee() {
		// Given
		Employee employee = createPersistantEmployee();

		// When
		subject.save(employee);

		// Then
		Employee fetchedEmployeeAfterUpdate = subject.findOne(employee.getEmployeeId());
		assertEquals(employee.getFirstName(), fetchedEmployeeAfterUpdate.getFirstName());
	}

	@Test
	public void deleteEmployee() {
		// Given
		Employee employee = createPersistantEmployee();

		// When
		subject.delete(employee);

		// Then
		assertEquals(NO_EMPS, subject.count());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Employee createPersistantEmployee() {
		Employee employee = createTestEmployee();
		subject.save(employee);
		return employee;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteEmployeeBeforeTest() {
		Employee employee = subject.findOne(EMP_ID);
		if (employee != null) {
			JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
			String query = "delete from job_history where employee_id = " + EMP_ID;
			jdbcTemplate.execute(query);
			String query2 = "delete from employees where employee_id = " + EMP_ID;
			jdbcTemplate.execute(query2);
		}
	}

	private List<Employee> constructList(Iterable<Employee> employees) {
		List<Employee> list = new ArrayList<Employee>();
		for (Employee employee : employees) {
			list.add(employee);
		}
		return list;
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
