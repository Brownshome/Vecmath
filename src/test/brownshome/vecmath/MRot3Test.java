package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MRot3Test {
	private MRot3 A, B, C;

	@BeforeEach
	void setUp() {
		A = Rot3.fromAxisAngle(Vec3.X_AXIS, Math.toRadians(90));
		B = Rot3.fromAxisAngle(Vec3.Y_AXIS, Math.toRadians(90));
		C = Rot3.fromAxisAngle(Vec3.X_AXIS, Math.toRadians(-90));
	}

	private static void assertRotEquals(Rot3 expected, Rot3 result) {
		assertEquals(1.0, Math.abs(expected.dot(result)), ACCURACY, "expected: %s but was: %s".formatted(expected, result));
	}

	@Test
	void multiplyLeft() {
		Rot3 result = A;
		A.multiplyLeft(B);
		A.multiplyLeft(C);
		Rot3 expected = Rot3.of(0, 0, -Math.sqrt(0.5), Math.sqrt(0.5));

		assertRotEquals(expected, result);
	}

	@Test
	void multiplyRight() {
		Rot3 result = C;
		C.multiplyRight(B);
		C.multiplyRight(A);
		Rot3 expected = Rot3.of(0, 0, -Math.sqrt(0.5), Math.sqrt(0.5));

		assertRotEquals(expected, result);
	}

	@Test
	void slerp() {
		Rot3 result = A;
		A.slerp(C, 0.25);
		Rot3 expected = Rot3.fromAxisAngle(Vec3.X_AXIS, Math.toRadians(45));

		assertRotEquals(expected, result);
	}

	@Test
	void nLerp() {
		Rot3 result = A;
		A.nLerp(C, 0.5);
		Rot3 expected = Rot3.IDENTITY;

		assertRotEquals(expected, result);
	}

	@Test
	void nLerpThroughZero() {
		Rot3 result = A;
		Rot3 expected = A.copy();

		A.nLerp(Rot3.of(-Math.sqrt(0.5), 0, 0, -Math.sqrt(0.5)), 0.5);

		assertRotEquals(expected, result);
	}

	@Test
	void invert() {
		Rot3 result = A;
		A.invert();
		Rot3 expected = C;

		assertRotEquals(expected, result);
	}
}