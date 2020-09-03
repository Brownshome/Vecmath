package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Rot3Test {
	private IRot3 A, B;

	@BeforeEach
	void setUp() {
		A = IRot3.fromAxisAngle(IVec3.X_AXIS, Math.toRadians(90));
		B = IRot3.fromAxisAngle(IVec3.Y_AXIS, Math.toRadians(90));
	}

	@Test
	void rotate() {
		MVec3 result = IVec3.Y_AXIS.mutable();
		A.rotate(result);
		IVec3 expected = IVec3.Z_AXIS;

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
		Rot3 expected = new IRot3(Math.sqrt(0.5), 0, 0, Math.sqrt(0.5));

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}
}