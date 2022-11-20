package brownshome.vecmath.vector;

import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.assertVecEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class Vec3Test {
	@Test
	void dot() {
		assertEquals(2.0, Vec3.of(0.0, 2.0, 0.0).dot(Vec3.of(-1.0, 1.0, 0.0)));
	}

	@Test
	void cross() {
		assertVecEquals(Vec3.Z_AXIS, Vec3.X_AXIS.cross(Vec3.Y_AXIS));
	}

	@Test
	void exactEquals() {
		assertTrue(Vec3.X_AXIS.exactEquals(Vec3.X_AXIS.copy()));
	}

	@Test
	void asUnknownSizeImmutable() {
		var unknownSize = new Vec3() {
			@Override
			public double x() {
				return 1.0;
			}

			@Override
			public double y() {
				return 0.0;
			}

			@Override
			public double z() {
				return -1.0;
			}
		}.asUnknownSize();

		assertEquals(3, unknownSize.size());
		assertTrue(unknownSize.exactEquals(VecN.of(1.0, 0.0, -1.0)));
	}

	@Test
	void asUnknownSizeMutable() {
		var unknownSize = Vec3.X_AXIS.asUnknownSize();

		assertEquals(3, unknownSize.size());
		assertTrue(unknownSize.exactEquals(VecN.of(1.0, 0.0, 0.0)));
	}

	@Test
	void asVecNImmutable() {
		var unknownSize = new VecN() {
			@Override
			public int size() {
				return 3;
			}

			@Override
			public double get(int i) {
				return i;
			}
		};

		assertTrue(Vec3.of(0.0, 1.0, 2.0).exactEquals(unknownSize.asVec3()));
	}

	@Test
	void asVecNMutable() {
		var unknownSize = VecN.of(0.0, 1.0, 2.0);

		assertTrue(Vec3.of(0.0, 1.0, 2.0).exactEquals(unknownSize.asVec3()));
	}

	@Test
	void copy() {
		assertTrue(Vec3.X_AXIS.exactEquals(Vec3.X_AXIS.copy()));
	}
}
