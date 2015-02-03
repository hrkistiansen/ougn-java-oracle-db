package no.bouvet.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.domain.Employee;

public class EmployeeDao {
    private DataSource dataSource;

    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet resultSet = null;

    public void create(Employee employee) {
        // String insertStatement =
        // "insert into employees ( employee_id, first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id ) values ( :employeeId, :firstName, :lastName, :email, :phoneNumber, :hireDate, :jobId, :salary, :commissionPct, :managerId, :departmentId);";
        String insertStatement = "insert into employees ( employee_id, first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(insertStatement);
            statement.setLong(1, employee.getEmployeeId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getEmail());
            statement.setString(5, employee.getPhoneNumber());
            statement.setDate(6, new java.sql.Date(employee.getHireDate().getTime()));
            statement.setString(7, employee.getJobId());
            statement.setBigDecimal(8, employee.getSalary());
            statement.setDouble(9, employee.getCommissionPct());
            statement.setLong(10, employee.getManagerId());
            statement.setLong(11, employee.getDepartmentId());
            int state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Employee employee) {
        String updateStatement = "update employees set first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, job_id = ?, salary = ?, commission_pct = ?, manager_id = ?, department_id = ?where employee_id = ?";;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(updateStatement);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getEmail());
            statement.setString(4, employee.getPhoneNumber());
            statement.setDate(5, new java.sql.Date(employee.getHireDate().getTime()));
            statement.setString(6, employee.getJobId());
            statement.setBigDecimal(7, employee.getSalary());
            statement.setDouble(8, employee.getCommissionPct());
            statement.setLong(9, employee.getManagerId());
            statement.setLong(10, employee.getDepartmentId());
            statement.setLong(11, employee.getEmployeeId());
            int state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(Employee employee) {
        String deleteStatement = "delete employees where employee_id = ?";
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(deleteStatement);
            statement.setLong(1, employee.getEmployeeId());
            int state = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Employee getById(Long employeeId) {
        String query = "select * from employees where employee_id = ?";
        Employee employee = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);
            statement.setLong(1, employeeId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employee = resultSetToEmployeeMapper(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return employee;
    }

    public List<Employee> getAll() {
        String query = "select * from employees";
        List<Employee> employees = new ArrayList<Employee>();
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Employee employee = resultSetToEmployeeMapper(resultSet);
                employees.add(employee);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
        return employees;
    }

    private Employee resultSetToEmployeeMapper(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setEmployeeId(rs.getLong("EMPLOYEE_ID"));
        employee.setFirstName(rs.getString("FIRST_NAME"));
        employee.setLastName(rs.getString("LAST_NAME"));
        employee.setEmail(rs.getString("EMAIL"));
        employee.setPhoneNumber(rs.getString("PHONE_NUMBER"));
        employee.setHireDate(rs.getDate("HIRE_DATE"));
        employee.setJobId(rs.getString("JOB_ID"));
        employee.setSalary(rs.getBigDecimal("SALARY"));
        employee.setCommissionPct(rs.getDouble("COMMISSION_PCT"));
        employee.setManagerId(rs.getLong("MANAGER_ID"));
        employee.setDepartmentId(rs.getLong("DEPARTMENT_ID"));
        return employee;
    }

    private void closeAll() {
        try {
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
