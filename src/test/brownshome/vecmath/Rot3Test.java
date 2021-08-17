package brownshome.vecmath;

import brownshome.vecmath.matrix.Matrix;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Rot3Test {
	private Rot3 A, B;

	private static void assertRotEquals(Rot3 expected, Rot3 result) {
		assertEquals(1.0, Math.abs(expected.dot(result)), ACCURACY, "expected: %s but was: %s".formatted(expected, result));
	}

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
		double expected = Math.toRadians(120);

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void angleToSameAxis() {
		var axis = Vec3.of(1, 2, 3);
		axis.normalize();

		double result = Rot3.fromAxisAngle(axis, 1).angleTo(Rot3.fromAxisAngle(axis, 2));
		double expected = 1.0;

		assertEquals(expected, result, ACCURACY);
	}

	@Test
	void createMatrix() {
		Matrix matrix = A.createMatrix();
		Matrix expected = Matrix.of(new double[] {
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

		assertRotEquals(expected, result);
	}
}