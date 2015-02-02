package no.bouvet.orm.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import no.bouvet.orm.dao.AirplaneDaoImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AirplaneDaoTest {
	private AirplaneDaoImpl subject;

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Before
	public void setUp() {
		subject = (AirplaneDaoImpl) applicationContext.getBean("airplaneDao");
		deleteAllAirplanes();
	}

	@Test
	@Transactional
	public void createAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");

		// When
		subject.save(airplane);

		// Then
		assertEquals(1, subject.getAllAirplanes().size());
	}

	@Test
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public void deleteAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.save(airplane);

		Airplane fetchedAirplane = subject.getAirplaneById(airplane.getId());

		// When
		subject.delete(fetchedAirplane);

		// Then
		assertEquals(0, subject.getAllAirplanes().size());
	}

	public void deleteAllAirplanes() {
		for (Airplane airplane : subject.getAllAirplanes()) {
			subject.delete(airplane);
		}
	}
}
