package brownshome.vecmath.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class Vec4Test {
	@Test
	void dot() {
		assertEquals(1.5, Vec4.of(0.0, 2.0, 0.0, -1.0).dot(Vec4.of(-1.0, 1.0, 0.0, 0.5)));
	}

	@Test
	void exactEquals() {
		assertTrue(Vec4.ZERO.exactEquals(Vec4.ZERO.copy()));
	}

	@Test
	void asUnknownSizeImmutable() {
		var unknownSize = new Vec4() {
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

			@Override
			public double w() {
				return 3.0;
			}
		}.asUnknownSize();

		assertEquals(4, unknownSize.size());
		assertTrue(unknownSize.exactEquals(VecN.of(1.0, 0.0, -1.0, 3.0)));
	}

	@Test
	void asUnknownSizeMutable() {
		var unknownSize = Vec4.ZERO.asUnknownSize();

		assertEquals(4, unknownSize.size());
		assertTrue(unknownSize.exactEquals(VecN.zero(4)));
	}

	@Test
	void asVecNImmutable() {
		var unknownSize = new VecN() {
			@Override
			public int size() {
				return 4;
			}

			@Override
			public double get(int i) {
				return i;
			}
		};

		assertTrue(Vec4.of(0.0, 1.0, 2.0, 3.0).exactEquals(unknownSize.asVec4()));
	}

	@Test
	void asVecNMutable() {
		var unknownSize = VecN.of(0.0, 1.0, 2.0, 3.0);

		assertTrue(Vec4.of(0.0, 1.0, 2.0, 3.0).exactEquals(unknownSize.asVec4()));
	}

	@Test
	void copy() {
		assertTrue(Vec4.ZERO.exactEquals(Vec4.ZERO.copy()));
	}
}
