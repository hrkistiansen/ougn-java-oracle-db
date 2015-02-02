package no.bouvet.orm.domain;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Statistics;
import no.bouvet.orm.dao.AirplaneDaoImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class AirplaneDaoTest {
	private AirplaneDaoImpl subject;

	private CacheManager cacheManager;

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
	@Before
	public void setUp() {
        cacheManager = (CacheManager) applicationContext.getBean("cacheManager");
		subject = (AirplaneDaoImpl) applicationContext.getBean("airplaneDao");
		deleteAllAirplanes();
	}

	@Test
	@Transactional
	public void selectAllAirplanesWithCache() {
		// Given
		Cache cache = prepareCache("getAirplaneById"); 

		Airplane airplane = new Airplane("Harald Hårfagre");
		subject.save(airplane);
		subject.flushClear();

		// When
		Airplane result = subject.getAirplaneById(airplane.getId());
		subject.flushClear();

		Airplane result2 = subject.getAirplaneById(airplane.getId());

		// Then
        assertEquals(1, cache.getStatistics().getCacheMisses());
        assertEquals(1, cache.getStatistics().getCacheHits());
	}

	public void deleteAllAirplanes() {
		for (Airplane airplane : subject.getAllAirplanes()) {
			subject.delete(airplane);
		}
	}

	private Cache prepareCache(String cacheName) {
		net.sf.ehcache.Cache cache = (net.sf.ehcache.Cache)cacheManager.getCache(cacheName).getNativeCache();
        cache.setStatisticsEnabled(true);
        cache.setStatisticsAccuracy(Statistics.STATISTICS_ACCURACY_GUARANTEED);
        cache.clearStatistics();
		return cache;
	}
}
