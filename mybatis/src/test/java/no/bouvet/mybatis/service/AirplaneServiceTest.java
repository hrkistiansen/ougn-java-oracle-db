package no.bouvet.mybatis.service;

import java.util.List;

import no.bouvet.mybatis.domain.Airplane;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AirplaneServiceTest {
	private static AirplaneService airplaneService;

	@BeforeClass
	public static void setup() {
		airplaneService = new AirplaneService();
	}

	@AfterClass
	public static void teardown() {
		airplaneService = null;
	}

	@Test
	public void testGetAirplaneById() {
		Airplane airplane = airplaneService.getAirplaneById(15l);
		Assert.assertNotNull(airplane);
		System.out.println(airplane);
	}

	@Test
	public void testGetAllAirplanes() {
		List<Airplane> airplanes = airplaneService.getAllAirplanes();
		Assert.assertEquals(1, airplanes.size());
		System.out.println(airplanes);
	}
}
