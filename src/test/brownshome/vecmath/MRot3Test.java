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

	@Test
	void multiplyLeft() {
		Rot3 result = A;
		A.multiplyLeft(B);
		A.multiplyLeft(C);
		Rot3 expected = Rot3.of(0, 0, -Math.sqrt(0.5), Math.sqrt(0.5));

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void multiplyRight() {
		Rot3 result = C;
		C.multiplyRight(B);
		C.multiplyRight(A);
		Rot3 expected = Rot3.of(0, 0, -Math.sqrt(0.5), Math.sqrt(0.5));

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void slerp() {
		Rot3 result = A;
		A.slerp(C, 0.25);
		Rot3 expected = Rot3.fromAxisAngle(Vec3.X_AXIS, Math.toRadians(45));

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void nLerp() {
		Rot3 result = A;
		A.slerp(C, 0.5);
		Rot3 expected = Rot3.IDENTITY;

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void invert() {
		Rot3 result = A;
		A.invert();
		Rot3 expected = C;

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}
}