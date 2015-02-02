package no.bouvet.orm.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import no.bouvet.orm.domain.Airplane;
import no.bouvet.orm.util.HibernateUtil;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

public class AirplaneDaoTest {
	private SessionFactory sessionFactory;
	private AirplaneDaoImpl subject;

	@Before
	public void setUp() {
		// Create sessionFactory and assign to subject
        sessionFactory = HibernateUtil.getSessionFactory();
        subject = new AirplaneDaoImpl();
        subject.setSessionFactory(sessionFactory);
        
        // Status quo
        deleteAllAirplanes();
	}
	
	@Test
	public void createAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");

		// When
		subject.save(airplane);

		// Then
		assertEquals(1, subject.getAllAirplanes().size());
	}

	@Test
	public void selectAllAirplanes() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.save(airplane);

		Airplane airplane2 = new Airplane("Harald Hardråde");
		subject.save(airplane2);

		// When
		List<Airplane> result = subject.getAllAirplanes();

		// Then
		assertEquals(2, result.size());
	}

	@Test
	public void selectAirplaneById() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.save(airplane);

		Airplane createdAirplane = subject.getAllAirplanes().get(0);
		System.out.println(createdAirplane.getId());

		// When
		Airplane result = subject.getAirplaneById(createdAirplane.getId());

		// Then
		assertEquals(createdAirplane.getName(), result.getName());
	}

	@Test
	public void updateAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.save(airplane);

		Airplane fetchedAirplane = subject.getAllAirplanes().get(0);
		assertEquals(airplane.getName(), fetchedAirplane.getName());
		fetchedAirplane.setName("Oppdatert navn");

		// When
		subject.update(fetchedAirplane);

		// Then
		Airplane fetchedAirplaneAfterUpdate = subject.getAllAirplanes().get(0);
		assertEquals(fetchedAirplane.getName(), fetchedAirplaneAfterUpdate.getName());
	}

	@Test
	public void deleteAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.save(airplane);

		Airplane fetchedAirplane = subject.getAllAirplanes().get(0);

		// When
		subject.delete(fetchedAirplane);

		// Then
		assertEquals(0, subject.getAllAirplanes().size());
		;
	}

	public void deleteAllAirplanes() {
		for (Airplane airplane : subject.getAllAirplanes()) {
			subject.delete(airplane);
		}
	}

}
