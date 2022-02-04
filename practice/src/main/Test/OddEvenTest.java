package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.ca.jrvs.practice.codingChallenge.OddEven;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Class for {@link OddEven}
 * <p>
 * Copyright & Copy; 2021 Jarvis.
 * </p>
 * 
 * @author Ashwin Rishi.
 */
class OddEvenTest {

	private OddEven oddEven;

	@Before
	void setUp() throws Exception {
		oddEven = new OddEven();
	}

	@Test
	void testOddEvenMod() {
		assertAll("positive numbers Validation", () -> assertEquals("odd", oddEven.validateOddEven(3)),
				() -> assertEquals("even", oddEven.validateOddEven(4)));
		assertAll("negative numbers Validation", () -> assertEquals("odd", oddEven.validateOddEven(-3)),
				() -> assertEquals("even", oddEven.validateOddEven(-4)));
		assertEquals("even", oddEven.validateOddEven(0));
	}
}