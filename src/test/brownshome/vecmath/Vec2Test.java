package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Vec2Test {
	private IRot2 A, B;

	@BeforeEach
	void setUp() {
		A = new IRot2(0.5, 0.5);
		B = new IRot2(0.5, -0.5);
	}

	@Test
	void distanceSq() {
		double result = A.distanceSq(B);
		double expected = 1.0;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void dot() {
		double result = A.dot(B);
		double expected = 0.0;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void length() {
		double result = A.length();
		double expected = Math.sqrt(0.5);

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void lengthSq() {
		double result = A.lengthSq();
		double expected = 0.5;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void distance() {
		double result = A.distanceSq(B);
		double expected = 1.0;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void angle() {
		double result = A.angle(B);
		double expected = Math.PI / 2.0;

		assertEquals(expected, result, ACCURACY);
	}
}