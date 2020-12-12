package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MRot2Test {
	private MRot2 A;

	@BeforeEach
	void setUp() {
		A = Rot2.fromAngle(Math.toRadians(20));
	}

	@Test
	void invert() {
		A.invert();
		Rot2 result = A;
		Rot2 expected = Rot2.fromAngle(Math.toRadians(-20));

		assertEquals(expected.sin(), result.sin(), ACCURACY);
		assertEquals(expected.cos(), result.cos(), ACCURACY);
	}
}