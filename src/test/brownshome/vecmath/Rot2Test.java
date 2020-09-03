package brownshome.vecmath;

import org.junit.jupiter.api.*;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Rot2Test {
	private IRot2 A, B;

	@BeforeEach
	void setUp() {
		A = new IRot2(Math.toRadians(90));
		B = new IRot2(Math.toRadians(45));
	}

	@Test
	void rotate() {
		IVec2 expected = new IVec2(0, 1);
		MVec2 toBeRotated = new MVec2(1, 0);
		A.rotate(toBeRotated);

		assertEquals(expected.x(), toBeRotated.x(), ACCURACY);
		assertEquals(expected.y(), toBeRotated.y(), ACCURACY);
	}

	@Test
	void angleTo() {
		double result = A.angleTo(B);
		double expected = Math.toRadians(-45);

		assertEquals(expected, result, ACCURACY);
	}
}