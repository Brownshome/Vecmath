package brownshome.vecmath.rotation.generic;

import brownshome.vecmath.rotation.Rot3;
import brownshome.vecmath.vector.Vec3;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.ACCURACY;
import static brownshome.vecmath.VecmathTesting.assertVecEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class GenericRotTest {
	private final Rot3 A = Rot3.ofAxisAngle(Vec3.X_AXIS, Math.toRadians(90.0));
	private final Rot3 B = Rot3.ofAxisAngle(Vec3.X_AXIS, Math.toRadians(-90.0));

	@Test
	void sphericalInterpolationNegated() {
		assertEquals(0.0, A.sphericalInterpolation(B.negated(), 0.25).angularDistance(Rot3.ofAxisAngle(Vec3.X_AXIS, Math.toRadians(45.0))), ACCURACY);
	}

	@Test
	void sphericalInterpolationSame() {
		Rot3 result = A.sphericalInterpolation(A, 0.25);
		assertVecEquals(A, result);
	}

	@Test
	void normalisedInterpolation() {
		Rot3 result = A.normalisedInterpolation(B, 0.5);
		assertVecEquals(Rot3.IDENTITY, result);
	}

	@Test
	void normalisedInterpolationNegated() {
		Rot3 result = A.normalisedInterpolation(A.negated(), 0.5);
		assertVecEquals(A, result);
	}

	@Test
	void angularDistance() {
		double result = A.angularDistance(B);
		assertEquals(Math.toRadians(180.0), result, ACCURACY);
	}
}
