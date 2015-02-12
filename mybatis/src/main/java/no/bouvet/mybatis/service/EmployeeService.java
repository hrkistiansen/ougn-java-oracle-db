package no.bouvet.mybatis.service;

import java.util.List;

import no.bouvet.mybatis.domain.Employee;
import no.bouvet.mybatis.mapper.EmployeeDao;
import no.bouvet.mybatis.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

public class EmployeeService {

	public void create(Employee employee) {
		try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			EmployeeDao employeeMapper = session.getMapper(EmployeeDao.class);
			employeeMapper.create(employee);
			session.commit();
		}
	}

	public Employee getById(Long employeeId) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            EmployeeDao employeeMapper = session.getMapper(EmployeeDao.class);
            return employeeMapper.getById(employeeId);
        }
	}

	public List<Employee> getAll() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			EmployeeDao employeeMapper = session.getMapper(EmployeeDao.class);
			return employeeMapper.getAll();
		}
	}

	public void update(Employee employee) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			EmployeeDao employeeMapper = session.getMapper(EmployeeDao.class);
			employeeMapper.update(employee);
			session.commit();
		}
	}

	public void delete(Employee employee) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
			EmployeeDao employeeMapper = session.getMapper(EmployeeDao.class);
			employeeMapper.delete(employee);
			session.commit();
		}
	}

}