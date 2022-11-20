package brownshome.vecmath.vector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class VecNTest {
	@Test
	void dot() {
		assertEquals(2.0, VecN.of(0.0, 2.0, 0.0).dot(VecN.of(-1.0, 1.0, 0.0)));
	}

	@Test
	void exactEquals() {
		assertTrue(VecN.axis(0, 6).exactEquals(VecN.axis(0, 6).copy()));
	}

	@Test
	void asUnknownSizeImmutable() {
		var result = VecN.zero(20);
		assertSame(result, result.asUnknownSize());
	}

	@Test
	void copy() {
		assertTrue(VecN.axis(0, 6).exactEquals(VecN.axis(0, 6).copy()));
	}
}
