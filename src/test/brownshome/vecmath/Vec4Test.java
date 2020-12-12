package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Vec4Test {
	private Vec4 A, B;

	@BeforeEach
	void setUp() {
		A = Vec4.of(0.5, 0.5, 0.5, 1.0);
		B = Vec4.of(0.5, -0.5, -0.5, -1.0);
	}

	@Test
	void distanceSq() {
		double result = A.distanceSq(B);
		double expected = 6.0;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void dot() {
		double result = A.dot(B);
		double expected = -1.25;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void length() {
		double result = A.length();
		double expected = Math.sqrt(1.75);

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void lengthSq() {
		double result = A.lengthSq();
		double expected = 1.75;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void distance() {
		double result = A.distance(B);
		double expected = Math.sqrt(6.0);

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void angle() {
		double result = A.angle(B);
		double expected = 2.0 * Math.asin(Math.sqrt(6.0 / 7.0));

		assertEquals(expected, result, ACCURACY);
	}
}