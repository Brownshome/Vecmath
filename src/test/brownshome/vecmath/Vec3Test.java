package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Vec3Test {
	private Vec3 A, B;

	@BeforeEach
	void setUp() {
		A = Vec3.of(0.5, 0.5, 0.5);
		B = Vec3.of(0.5, -0.5, -0.5);
	}

	@Test
	void distanceSq() {
		double result = A.distanceSq(B);
		double expected = 2.0;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void dot() {
		double result = A.dot(B);
		double expected = -0.25;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void length() {
		double result = A.length();
		double expected = Math.sqrt(0.75);

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void lengthSq() {
		double result = A.lengthSq();
		double expected = 0.75;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void distance() {
		double result = A.distance(B);
		double expected = Math.sqrt(2.0);

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void angle() {
		double result = A.angle(B);
		double expected = 2.0 * Math.asin(Math.sqrt(2.0 / 3.0));

		assertEquals(expected, result, ACCURACY);
	}
}