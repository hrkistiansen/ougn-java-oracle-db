package no.bouvet.orm.util;

import java.util.Properties;
 
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
public class HibernateUtil {
 
    private static SessionFactory sessionAnnotationFactory;
     
    private static SessionFactory buildSessionAnnotationFactory() {
        try {
            // Create configuration
            Configuration configuration = getConfiguration();
            System.out.println("Hibernate Annotation Configuration loaded");
             
            // Create service registry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate Annotation serviceRegistry created");
             
            // Create sessionFactory
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             
            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

	public static Configuration getConfiguration() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate-annotation.cfg.xml");
		return configuration;
	}
 
    public static SessionFactory getSessionFactory() {
    	// Build session factory one time
        if(sessionAnnotationFactory == null) sessionAnnotationFactory = buildSessionAnnotationFactory();
        return sessionAnnotationFactory;
    }
     
}