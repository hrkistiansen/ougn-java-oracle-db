package no.bouvet.orm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import no.bouvet.orm.domain.Airplane;

public class AirplaneDaoImpl implements AirplaneDao {
	private SessionFactory sessionFactory;

	@Override
	public void save(Airplane airplane) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		airplane.setCreated(new Date());
		airplane.setCreatedBy(System.getProperty("user.name"));
		session.save(airplane);
		tx.commit();
	}

	@Override
	public void update(Airplane airplane) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		airplane.setLastUpdated(new Date());
		airplane.setLastUpdatedBy(System.getProperty("user.name"));
		session.update(airplane);
		tx.commit();
	}

	@Override
	public void delete(Airplane airplane) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.delete(airplane);
		tx.commit();
	}

	@Override
	public Airplane getAirplaneById(Long airplaneId) {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("FROM Airplane WHERE id = :airplaneId");
		Airplane airplane = (Airplane) query.setLong("airplaneId", airplaneId).uniqueResult();
		tx.commit();
		return airplane;
	}

	@Override
	public List<Airplane> getAllAirplanes() {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<Airplane> airplanes = session.createQuery("FROM Airplane").list();
		tx.commit();
		return airplanes;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
