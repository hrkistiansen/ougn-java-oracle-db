package no.bouvet.orm.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.dao.EmployeeDao;
import no.bouvet.jdbc.domain.Employee;
import oracle.jdbc.pool.OracleDataSource;

import org.junit.Before;
import org.junit.Test;

public class EmployeeDaoTest {
	private static final int NO_EMPS = 107;
    private static final Long EMP_ID = 999L;
    private EmployeeDao subject;

	@Before
	public void setUp() {
		DataSource dataSource = getOracleDataSource();

		// Status quo
		deleteEmployee(dataSource);

		subject = new EmployeeDao();
		subject.setDataSource(dataSource);
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

		Employee fetchedEmployee = subject.getById(EMP_ID);
		fetchedEmployee.setFirstName("Oppdatert navn");

		// When
		subject.update(fetchedEmployee);

		// Then
		Employee fetchedEmployeeAfterUpdate = subject.getById(EMP_ID);
		assertEquals(fetchedEmployee.getFirstName(), fetchedEmployeeAfterUpdate.getFirstName());
	}

	@Test
	public void deleteEmployee() {
		// Given
		Employee employee = createTestEmployee();
		subject.create(employee);

				// When
		subject.delete(employee);

		// Then
		assertNull(subject.getById(EMP_ID));
	}

	public static DataSource getOracleDataSource() {
		OracleDataSource oracleDS = null;
		try {
			oracleDS = new OracleDataSource();
			oracleDS.setURL("jdbc:oracle:thin:@localhost:1521:XE");
			oracleDS.setUser("hr");
			oracleDS.setPassword("hr");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oracleDS;
	}

	public void deleteEmployee(DataSource dataSource) {
		Connection connection = null;
		PreparedStatement deleteEmployeeStatement = null;
        PreparedStatement deleteJobHistoryStatement = null;
		String deleteEmployeeSql = "delete from employees where employee_id = " + EMP_ID;
        String deleteJobHistorySql = "delete from job_history where employee_id = " + EMP_ID;
		try {
			connection = dataSource.getConnection();
            deleteJobHistoryStatement = connection.prepareStatement(deleteJobHistorySql);
            deleteJobHistoryStatement.executeUpdate();
			deleteEmployeeStatement = connection.prepareStatement(deleteEmployeeSql);
			deleteEmployeeStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				deleteEmployeeStatement.close();
				deleteJobHistoryStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
    private Employee createTestEmployee() {
        Employee employee = new Employee();
        employee.setEmployeeId(EMP_ID);
        employee.setFirstName("Test");
        employee.setLastName("Testesen");
        employee.setEmail("EMAIL");
        employee.setPhoneNumber("555");
        employee.setHireDate(new Date());
        employee.setJobId("ST_CLERK");
        employee.setSalary(BigDecimal.valueOf(1000L));
        employee.setCommissionPct(Double.valueOf("0.1"));
        employee.setManagerId(100L);
        employee.setDepartmentId(80L);
        return employee;
    }

}
