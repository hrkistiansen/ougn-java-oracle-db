package no.bouvet.orm.domain;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import no.bouvet.jdbc.dao.AirplaneDaoImpl;
import no.bouvet.jdbc.domain.Airplane;
import oracle.jdbc.pool.OracleDataSource;

import org.junit.Before;
import org.junit.Test;

public class AirplaneDaoTest {
	private AirplaneDaoImpl subject;

	@Before
	public void setUp() {
		DataSource dataSource = getOracleDataSource();

		// Status quo
		deleteAllAirplanes(dataSource);

		subject = new AirplaneDaoImpl();
		subject.setDataSource(dataSource);
	}

	@Test
	public void createAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");

		// When
		subject.create(airplane);

		// Then
		assertEquals(1, subject.getAll().size());
	}

	@Test
	public void selectAllAirplanes() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.create(airplane);

		Airplane airplane2 = new Airplane("Harald Hardråde");
		subject.create(airplane2);

		// When
		List<Airplane> result = subject.getAll();

		// Then
		assertEquals(2, result.size());
	}

	@Test
	public void selectAirplaneById() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.create(airplane);

		Airplane createdAirplane = subject.getAll().get(0);

		// When
		Airplane result = subject.getById(createdAirplane.getId());

		// Then
		assertEquals(createdAirplane.getName(), result.getName());
	}

	@Test
	public void updateAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.create(airplane);

		Airplane fetchedAirplane = subject.getAll().get(0);
		assertEquals(airplane.getName(), fetchedAirplane.getName());
		fetchedAirplane.setName("Oppdatert navn");

		// When
		subject.update(fetchedAirplane);

		// Then
		Airplane fetchedAirplaneAfterUpdate = subject.getAll().get(0);
		assertEquals(fetchedAirplane.getName(), fetchedAirplaneAfterUpdate.getName());
	}

	@Test
	public void deleteAirplane() {
		// Given
		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.create(airplane);

		Airplane fetchedAirplane = subject.getAll().get(0);

		// When
		subject.delete(fetchedAirplane);

		// Then
		assertEquals(0, subject.getAll().size());
		;
	}

	public static DataSource getOracleDataSource() {
		OracleDataSource oracleDS = null;
		try {
			oracleDS = new OracleDataSource();
			oracleDS.setURL("jdbc:oracle:thin:@localhost:1521:XE");
			oracleDS.setUser("hibtest");
			oracleDS.setPassword("hibtest");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return oracleDS;
	}

	public void deleteAllAirplanes(DataSource dataSource) {
		Connection connection = null;
		PreparedStatement statement = null;
		String insertStatement = "delete from airplane";
		try {
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(insertStatement);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
