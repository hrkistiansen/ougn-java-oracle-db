package no.bouvet.orm.repository;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import no.bouvet.orm.Application;
import no.bouvet.orm.domain.Airplane;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class AirplaneRepositoryTest {

	@Autowired
	private AirplaneRepository subject;

	@Before
	public void setUp() {
		deleteAllAirplanes();
	}

	@Test
	public void createAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.save(airplane);

		// When
		Airplane result = subject.findOne(airplane.getId());

		// Then
		assertEquals(airplane.getId(), result.getId());
	}

	@Test
	public void selectAllAirplanes() {
		// Given
		createPersistantAirplane("Harald Hårfagre");
		createPersistantAirplane("Harald Hardråde");

		// When
		List<Airplane> result = constructList(subject.findAll());

		// Then
		assertEquals(2, result.size());
	}

	@Test
	public void selectAirplaneById() {
		// Given
		Airplane airplane = createPersistantAirplane("Harald Hårfagre");

		// When
		Airplane result = subject.findOne(airplane.getId());

		// Then
		assertEquals(airplane.getName(), result.getName());
	}

	@Test
	public void updateAirplane() {
		// Given
		Airplane airplane = createPersistantAirplane("Harald Hårfagre");
		airplane.setName("Harald Hardråde");

		// When
		subject.save(airplane);

		// Then
		Airplane fetchedAirplaneAfterUpdate = subject.findOne(airplane.getId());
		assertEquals(airplane.getName(), fetchedAirplaneAfterUpdate.getName());
	}

	@Test
	public void deleteAirplane() {
		// Given
		Airplane airplane = createPersistantAirplane("Harald Hårfagre");

		// When
		subject.delete(airplane);

		// Then
		assertEquals(0, subject.count());
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Airplane createPersistantAirplane(String name) {
		Airplane airplane = new Airplane(name);
		subject.save(airplane);
		return airplane;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteAllAirplanes() {
		for (Airplane airplane : subject.findAll()) {
			subject.delete(airplane);
		}
	}

	private List<Airplane> constructList(Iterable<Airplane> airplanes) {
		List<Airplane> list = new ArrayList<Airplane>();
		for (Airplane airplane : airplanes) {
			list.add(airplane);
		}
		return list;
	}
}
