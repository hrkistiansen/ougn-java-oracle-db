package no.bouvet.orm.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.security.auth.Subject;

import no.bouvet.orm.domain.Employee;

import org.avaje.agentloader.AgentLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.avaje.ebean.Ebean;

public class EmployeeDaoTest {
    private static final int NO_EMPS = 107;
    private static final Long EMP_ID = 999L;

	private static EmployeeDao subject;

	@BeforeClass
	public static void setUp() {
	    subject = new EmployeeDao();
	}

	@Before
	public void initTest() {
	    Ebean.createSqlUpdate("delete from employees where employee_id = " + EMP_ID).execute();
	}

	@Test
	public void createEmployee() {
		// Given
		Employee employee = createTestEmployee();

		// When
		subject.create(employee);

		// Then
        List<Employee> employees = Ebean.find(Employee.class).findList();
		assertEquals(NO_EMPS + 1, employees.size());
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
		employee.save();

		// When
		Employee result = subject.getById(EMP_ID);

		// Then
		assertEquals(employee.getFirstName(), result.getFirstName());
	}

	@Test
	public void updateEmployee() {
		// Given
        Employee employee = createTestEmployee();
        employee.save();

		employee.setFirstName("Oppdatert navn");

		// When
		subject.update(employee);

		// Then
		Employee fetchedEmployeeAfterUpdate = Ebean.find(Employee.class, EMP_ID);
		assertEquals(employee.getFirstName(), fetchedEmployeeAfterUpdate.getFirstName());
	}

	@Test
	public void deleteEmployee() {
		// Given
		Employee employee = createTestEmployee();
		employee.save();

		// When
		subject.delete(employee);

		// Then
		assertEquals(NO_EMPS, Ebean.find(Employee.class).findList().size());
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
