package brownshome.vecmath.matrix;

import brownshome.vecmath.CompareConstant;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LUFactorisationTest {
	@Test
	void leftSolve() {
		double[] expected = new double[] {
				.25, .125,
				-1.375, 2.0625,
				-.75, 2.125
		};

		Matrix a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, 3, 3);

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, 2, 3).transpose();

		var factorisation = new LUFactorisation(a, 0.0);
		var result = factorisation.leftSolve(b).asMatrix().backingArray();

		assertArrayEquals(expected, result, CompareConstant.ACCURACY);
	}

	@Test
	void rightSolve() {
		double[] expected = new double[] {
				-.5, .5, .75,
				-1.75, .75, -.125
		};

		Matrix a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, 3, 3);

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, 2, 3);

		var factorisation = new LUFactorisation(a, 0.0);
		var result = factorisation.rightSolve(b).asMatrix().backingArray();

		assertArrayEquals(expected, result, CompareConstant.ACCURACY);
	}

	@Test
	void rightSolveNonSymmetricalPermutation() {
		double[] expected = new double[] {
				.5, -.5, .5,
				-2.75, 1.75, .75
		};

		Matrix a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, .5,
				1.0, 0.0, 1.0
		}, 3, 3);

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, 2, 3);

		var factorisation = new LUFactorisation(a, 0.0);
		var result = factorisation.rightSolve(b).asMatrix().backingArray();

		assertArrayEquals(expected, result, CompareConstant.ACCURACY);
	}

	@Test
	void inverse() {
		double[] expected = new double[] {
				0, 0, .5,
				-1.5, .5, .25,
				-1, 1, .5
		};

		Matrix a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, 3, 3);

		var factorisation = new LUFactorisation(a, 0.0);
		var result = factorisation.inverse().asMatrix().backingArray();

		assertArrayEquals(expected, result, CompareConstant.ACCURACY);
	}

	@Test
	void determinant() {
		Matrix m = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, 3, 3);

		var factorisation = new LUFactorisation(m, 0.0);

		assertEquals(-2.0, factorisation.determinant(), CompareConstant.ACCURACY);
	}

	@Test
	void failsForSingularMatrix() {
		Matrix m = Matrix.of(new double[] {
				0.0, -1.0 / 3.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, 3, 3);

		assertThrows(SingularMatrixException.class, () -> new LUFactorisation(m, CompareConstant.ACCURACY));
	}
}