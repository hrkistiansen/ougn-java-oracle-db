package no.bouvet.mybatis.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import no.bouvet.mybatis.domain.Airplane;
import no.bouvet.mybatis.mapper.AirplaneMapper;
import no.bouvet.mybatis.util.MyBatisUtil;

public class AirplaneService {

	public void insertAirplane(Airplane airplane) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			AirplaneMapper AirplaneMapper = sqlSession.getMapper(AirplaneMapper.class);
			AirplaneMapper.create(airplane);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}
	}

	public Airplane getAirplaneById(Long airplaneId) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			AirplaneMapper AirplaneMapper = sqlSession.getMapper(AirplaneMapper.class);
			return AirplaneMapper.getAirplaneById(airplaneId);
		} finally {
			sqlSession.close();
		}
	}

	public List<Airplane> getAllAirplanes() {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			AirplaneMapper AirplaneMapper = sqlSession.getMapper(AirplaneMapper.class);
			return AirplaneMapper.getAllAirplanes();
		} finally {
			sqlSession.close();
		}
	}

	public void updateAirplane(Airplane airplane) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			AirplaneMapper AirplaneMapper = sqlSession.getMapper(AirplaneMapper.class);
			AirplaneMapper.update(airplane);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}

	public void deleteAirplane(Airplane airplane) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			AirplaneMapper AirplaneMapper = sqlSession.getMapper(AirplaneMapper.class);
			AirplaneMapper.delete(airplane);
			sqlSession.commit();
		} finally {
			sqlSession.close();
		}

	}

}