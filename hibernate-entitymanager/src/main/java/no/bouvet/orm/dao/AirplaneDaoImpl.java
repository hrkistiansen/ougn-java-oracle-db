package no.bouvet.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;

import no.bouvet.orm.domain.Airplane;

import org.hibernate.Session;

public class AirplaneDaoImpl implements AirplaneDao {
	private EntityManager entityManager;

	@Override
	public void save(Airplane airplane) {
		entityManager.getTransaction().begin();
		entityManager.persist(airplane);
		entityManager.getTransaction().commit();
	}

	@Override
	public void update(Airplane airplane) {
		entityManager.getTransaction().begin();
		entityManager.merge(airplane);
		entityManager.getTransaction().commit();
	}

	@Override
	public void delete(Airplane airplane) {
		entityManager.getTransaction().begin();
		entityManager.remove(airplane);
		entityManager.getTransaction().commit();
	}

	@Override
	public Airplane getAirplaneById(Long airplaneId) {
		return entityManager.find(Airplane.class, airplaneId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Airplane> getAllAirplanes() {
		Session session = (Session) entityManager.getDelegate();
		return session.createCriteria(Airplane.class).list();
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
