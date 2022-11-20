package brownshome.vecmath.vector.generic;

import brownshome.vecmath.vector.Vec3;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.ACCURACY;
import static brownshome.vecmath.VecmathTesting.assertVecEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class GenericVecTest {
	@Test
	void normalised() {
		assertVecEquals(Vec3.of(0.6, 0.8, 0.0), Vec3.of(3.0, 4.0, 0.0).normalised());
	}

	@Test
	void distanceSquared() {
		assertEquals(2.0, Vec3.X_AXIS.distanceSquared(Vec3.Y_AXIS));
	}

	@Test
	void distance() {
		assertEquals(5.0, Vec3.of(3.0, 0.0, 0.0).distance(Vec3.of(0.0, 4.0, 0.0)));
	}

	@Test
	void lengthSquared() {
		assertEquals(2.0, Vec3.of(1.0, 1.0, 0.0).lengthSquared());
	}

	@Test
	void length() {
		assertEquals(5.0, Vec3.of(3.0, 4.0, 0.0).length());
	}

	@Test
	void angle() {
		assertEquals(Math.PI * 0.5, Vec3.X_AXIS.angle(Vec3.Y_AXIS), ACCURACY);
	}
}
