package no.bouvet.orm.dao;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import no.bouvet.orm.domain.Employee;

import org.avaje.agentloader.AgentLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.avaje.ebean.Ebean;

public class EmployeeDaoTest {
    private static final int NO_EMPS = 107;
    private static final Long EMP_ID = 999L;

	private EmployeeDao subject;

	@BeforeClass
	public static void setUp() {
	    AgentLoader.loadAgentFromClasspath("avaje-ebeanorm-agent","debug=2;packages=no.bouvet.orm.domain.**");
	}

	@Before
	public void initTest() {
	    Ebean.createSqlUpdate("delete from employees where employee_id = " + EMP_ID).execute();
	}

//	@Test
//	public void testDatasource() {
//        String sql = "select count(*) as count from dual";
//        SqlRow row =
//            Ebean.createSqlQuery(sql)
//            .findUnique();
//
//        Integer i = row.getInteger("count");
//
//        System.out.println("Got "+i+"  - DataSource good.");
//	}
//
	@Test
	public void createEmployee() {
		// Given
		Employee employee = createTestEmployee();

		// When
        employee.save();

		// Then
        List<Employee> employees = Ebean.find(Employee.class).findList();
		assertEquals(NO_EMPS + 1, employees.size());
	}

	@Test
	public void selectAllEmployees() {
		// Given

		// When
	    List<Employee> result = Ebean.find(Employee.class).findList();

		// Then
		assertEquals(NO_EMPS, result.size());
	}

	@Test
	public void selectEmployeeById() {
		// Given
		Employee employee = createTestEmployee();
		employee.save();

		// When
		Employee result = Ebean.find(Employee.class, EMP_ID);

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
		employee.save();

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
		employee.delete();

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
