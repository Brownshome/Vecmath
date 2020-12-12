package brownshome.vecmath;

import org.junit.jupiter.api.*;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class Rot2Test {
	private Rot2 A, B;

	@BeforeEach
	void setUp() {
		A = Rot2.fromAngle(Math.toRadians(90));
		B = Rot2.fromAngle(Math.toRadians(45));
	}

	@Test
	void rotate() {
		Vec2 expected = Vec2.of(0, 1);
		MVec2 toBeRotated = Vec2.of(1, 0);
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