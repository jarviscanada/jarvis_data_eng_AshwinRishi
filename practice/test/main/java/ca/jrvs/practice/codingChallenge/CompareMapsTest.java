package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

/**
 * Test Class for {@link CompareMaps}
 * <p>
 * Copyright & Copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
public class CompareMapsTest {

	private CompareMaps<K, V> compareMaps;
	private HashMap<Integer, String> map1 = new HashMap();
	private HashMap<Integer, String> map2 = new HashMap();

	@Before
	public void setUp() throws Exception {
		compareMaps = new CompareMapsTest();
		map1.put(1, "ashwin");
		map2.put(1, "ashwin");
	}

	@Test
	public void testValidateMaps() {
		assertTrue(compareMaps.validateMaps(map1, map2), true);
		map2.put(2, "ashwin");
		assertFalse(compareMaps.validateMaps(map1, map2), false);
	}

}
