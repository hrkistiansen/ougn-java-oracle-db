package no.bouvet.orm.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.Test;

public class EclipseUtilTest {
	private EntityManagerFactory entityManagerFactory;

    @Test
    public void testConfiguration() {
        //Get Session
    	entityManagerFactory = Persistence.createEntityManagerFactory( "no.bouvet.orm" );
    	EntityManager entityManager = entityManagerFactory.createEntityManager();

        //start transaction
		entityManager.getTransaction().begin();

        //Commit transaction
		entityManager.getTransaction().commit();
		
		// Close manager
		entityManager.close();
    }
}
