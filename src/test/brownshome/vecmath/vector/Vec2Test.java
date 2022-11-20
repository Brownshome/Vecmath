package brownshome.vecmath.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class Vec2Test {
	@Test
	void tangent() {
		assertTrue(Vec2.X_AXIS.tangent().exactEquals(Vec2.Y_AXIS));
	}

	@Test
	void dot() {
		assertEquals(2.0, Vec2.of(0.0, 2.0).dot(Vec2.of(-1.0, 1.0)));
	}

	@Test
	void exactEquals() {
		assertTrue(Vec2.X_AXIS.exactEquals(Vec2.X_AXIS.copy()));
	}

	@Test
	void asUnknownSizeImmutable() {
		var unknownSize = new Vec2() {
			@Override
			public double x() {
				return 1.0;
			}

			@Override
			public double y() {
				return 0.0;
			}
		}.asUnknownSize();

		assertEquals(2, unknownSize.size());
		assertTrue(unknownSize.exactEquals(VecN.of(1.0, 0.0)));
	}

	@Test
	void asUnknownSizeMutable() {
		var unknownSize = Vec2.X_AXIS.asUnknownSize();

		assertEquals(2, unknownSize.size());
		assertTrue(unknownSize.exactEquals(VecN.of(1.0, 0.0)));
	}

	@Test
	void asVecNImmutable() {
		var unknownSize = new VecN() {
			@Override
			public int size() {
				return 2;
			}

			@Override
			public double get(int i) {
				return i;
			}
		};

		assertTrue(Vec2.of(0.0, 1.0).exactEquals(unknownSize.asVec2()));
	}

	@Test
	void asVecNMutable() {
		var unknownSize = VecN.of(0.0, 1.0);

		assertTrue(Vec2.of(0.0, 1.0).exactEquals(unknownSize.asVec2()));
	}

	@Test
	void copy() {
		assertTrue(Vec2.X_AXIS.exactEquals(Vec2.X_AXIS.copy()));
	}
}
