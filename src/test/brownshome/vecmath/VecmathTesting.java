package brownshome.vecmath;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.generic.GenericVec;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VecmathTesting {
	public static final double ACCURACY = 1e-6;

	public static <VEC_TYPE extends GenericVec<VEC_TYPE>> void assertVecEquals(VEC_TYPE expected, VEC_TYPE result) {
		assertEquals(0.0, Math.abs(expected.distance(result)), ACCURACY, "expected: %s but was: %s".formatted(expected, result));
	}

	public static void assertMatrixEquals(Matrix expected, Matrix result) {
		double sum = 0.0;

		for (int r = 0; r < expected.rows(); r++) for (int c = 0; c < expected.columns(); c++) {
			var diff = expected.get(r, c) - result.get(r, c);
			sum += diff * diff;
		}

		assertEquals(0.0, sum, ACCURACY, "expected: %s but was: %s".formatted(expected, result));
	}
}
