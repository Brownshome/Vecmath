package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MRot2Test {
	private MRot2 A;

	private static void assertRotEquals(Rot2 expected, Rot2 result) {
		assertEquals(1.0, Math.abs(expected.dot(result)), ACCURACY, "expected: %s but was: %s".formatted(expected, result));
	}

	@BeforeEach
	void setUp() {
		A = Rot2.fromAngle(Math.toRadians(20));
	}

	@Test
	void invert() {
		A.invert();
		Rot2 result = A;
		Rot2 expected = Rot2.fromAngle(Math.toRadians(-20));

		assertRotEquals(expected, result);
	}
}