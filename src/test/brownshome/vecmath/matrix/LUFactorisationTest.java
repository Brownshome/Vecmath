package brownshome.vecmath.matrix;

import brownshome.vecmath.VecmathTesting;
import brownshome.vecmath.matrix.factorisation.SingularMatrixException;
import brownshome.vecmath.matrix.factorisation.basic.LowerUpperFactorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;
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

		var a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, MatrixLayout.ofRowMajor(2, 3)).transpose();

		var factorisation = new LowerUpperFactorisation(a, 0.0);
		var result = factorisation.leftSolve(b).asArrayBacked().backingArray();

		assertArrayEquals(expected, result, VecmathTesting.ACCURACY);
	}

	@Test
	void rightSolve() {
		double[] expected = new double[] {
				-.5, .5, .75,
				-1.75, .75, -.125
		};

		var a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, MatrixLayout.ofRowMajor(2, 3));

		var factorisation = new LowerUpperFactorisation(a, 0.0);
		var result = factorisation.rightSolve(b).arrayBackedCopy().backingArray();

		assertArrayEquals(expected, result, VecmathTesting.ACCURACY);
	}

	@Test
	void rightSolveNonSymmetricalPermutation() {
		double[] expected = new double[] {
				.5, -.5, .5,
				-2.75, 1.75, .75
		};

		var a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, .5,
				1.0, 0.0, 1.0
		}, MatrixLayout.ofRowMajor(3, 3));

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, MatrixLayout.ofRowMajor(2, 3));

		var factorisation = new LowerUpperFactorisation(a, 0.0);
		var result = factorisation.rightSolve(b).arrayBackedCopy().backingArray();

		assertArrayEquals(expected, result, VecmathTesting.ACCURACY);
	}

	@Test
	void inverse() {
		double[] expected = new double[] {
				0, 0, .5,
				-1.5, .5, .25,
				-1, 1, .5
		};

		var a = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		var factorisation = new LowerUpperFactorisation(a, 0.0);
		var result = factorisation.inverse().asArrayBacked().backingArray();

		assertArrayEquals(expected, result, VecmathTesting.ACCURACY);
	}

	@Test
	void determinant() {
		var m = Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		var factorisation = new LUFactorisation(m, 0.0);

		assertEquals(-2.0, factorisation.determinant(), VecmathTesting.ACCURACY);
	}

	@Test
	void failsForSingularMatrix() {
		var m = Matrix.of(new double[] {
				0.0, -1.0 / 3.0, 0.5,
				-1.0, -1.0, 1.5,
				2.0, 0.0, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		assertThrows(SingularMatrixException.class, () -> new LUFactorisation(m, VecmathTesting.ACCURACY));
	}
}