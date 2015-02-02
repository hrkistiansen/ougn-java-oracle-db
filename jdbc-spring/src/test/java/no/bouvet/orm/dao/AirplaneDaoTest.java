package no.bouvet.orm.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.sql.DataSource;

import no.bouvet.jdbc.dao.AirplaneDaoImpl;
import no.bouvet.jdbc.domain.Airplane;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@Repository
public class AirplaneDaoTest {
	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private DataSource dataSource;

	private AirplaneDaoImpl subject;

	@Before
	public void setUp() {
		// Status quo
		deleteAllAirplanes();

		subject = (AirplaneDaoImpl) applicationContext.getBean("airplaneDao");
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
		Airplane result = subject.getById(createdAirplane.getId());

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
		String query = "delete from airplane";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query);
	}

}
