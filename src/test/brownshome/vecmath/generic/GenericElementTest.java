package brownshome.vecmath.generic;

import brownshome.vecmath.vector.Vec3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

final class GenericElementTest {
	@Test
	void subtract() {
		assertTrue(Vec3.Y_AXIS.subtract(Vec3.X_AXIS).exactEquals(Vec3.of(-1.0, 1.0, 0.0)));
	}

	@Test
	void scaleAdd() {
		assertTrue(Vec3.Y_AXIS.scaleAdd(Vec3.X_AXIS, 2.0).exactEquals(Vec3.of(2.0, 1.0, 0.0)));
	}

	@Test
	void negated() {
		assertTrue(Vec3.X_AXIS.negated().exactEquals(Vec3.of(-1.0, 0.0, 0.0)));
	}

	@Test
	void interpolated() {
		assertTrue(Vec3.X_AXIS.interpolated(Vec3.Y_AXIS, 0.25).exactEquals(Vec3.of(0.75, 0.25, 0.0)));
	}
}
