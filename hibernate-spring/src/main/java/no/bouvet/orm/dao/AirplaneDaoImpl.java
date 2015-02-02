package no.bouvet.orm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import no.bouvet.orm.domain.Airplane;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

public class AirplaneDaoImpl implements AirplaneDao {

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this. entityManager = entityManager;
    }

	@Override
	public void save(Airplane airplane) {
		entityManager.persist(airplane);
	}

	@Override
	public void update(Airplane airplane) {
		entityManager.merge(airplane);
	}

	@Override
	public void delete(Airplane airplane) {
		// Merge only necessary for simplistic test case
		entityManager.remove(entityManager.contains(airplane) ? airplane : entityManager.merge(airplane));
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

}
