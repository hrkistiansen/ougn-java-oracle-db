package no.bouvet.orm.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

public class HibernateUtilTest {

    @Test
    public void testConfiguration() {
        //Get Session
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        //start transaction
        session.beginTransaction();

        //Commit transaction
        session.getTransaction().commit();
    }
}
