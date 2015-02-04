package no.bouvet.mybatis.service;

import java.util.List;

import no.bouvet.mybatis.domain.Employee;
import no.bouvet.mybatis.mapper.EmployeeDao;
import no.bouvet.mybatis.util.MyBatisUtil;

import org.apache.ibatis.session.SqlSession;

public class EmployeeService {

	public void create(Employee employee) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			EmployeeDao employeeMapper = sqlSession.getMapper(EmployeeDao.class);
			employeeMapper.create(employee);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	public Employee getById(Long employeeId) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			EmployeeDao employeeMapper = sqlSession.getMapper(EmployeeDao.class);
			return employeeMapper.getById(employeeId);
		} finally {
			sqlSession.close();
		}
	}

	public List<Employee> getAll() {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			EmployeeDao employeeMapper = sqlSession.getMapper(EmployeeDao.class);
			return employeeMapper.getAll();
		} finally {
			sqlSession.close();
		}
	}

	public void update(Employee employee) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			EmployeeDao employeeMapper = sqlSession.getMapper(EmployeeDao.class);
			employeeMapper.update(employee);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}

	public void delete(Employee employee) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			EmployeeDao employeeMapper = sqlSession.getMapper(EmployeeDao.class);
			employeeMapper.delete(employee);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}

}