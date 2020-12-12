package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Rot3Test {
	private Rot3 A, B;

	@BeforeEach
	void setUp() {
		A = Rot3.fromAxisAngle(Vec3.X_AXIS, Math.toRadians(90));
		B = Rot3.fromAxisAngle(Vec3.Y_AXIS, Math.toRadians(90));
	}

	@Test
	void rotate() {
		MVec3 result = Vec3.Y_AXIS.copy();
		A.rotate(result);
		Vec3 expected = Vec3.Z_AXIS;

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void angleTo() {
		double result = A.angleTo(B);
		double expected = Math.toRadians(90);

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void createMatrix() {
		Matrix matrix = A.createMatrix();
		Matrix expected = new Matrix(new double[] {
				1, 0, 0,
				0, 0, -1,
				0, 1, 0
		}, 3, 3);

		assertArrayEquals(expected.backingArray(), matrix.backingArray(), ACCURACY);
	}

	@Test
	void fromAxisAngle() {
		Rot3 result = A;
		Rot3 expected = Rot3.of(Math.sqrt(0.5), 0, 0, Math.sqrt(0.5));

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}
}