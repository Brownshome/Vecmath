package brownshome.vecmath.matrix;

import brownshome.vecmath.VecmathTesting;
import brownshome.vecmath.matrix.basic.BasicSymmetricMatrix;
import brownshome.vecmath.matrix.factorisation.SingularMatrixException;
import brownshome.vecmath.matrix.factorisation.basic.CholeskyFactorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CholeskyFactorisationTest {
	@Test
	void leftSolve() {
		double[] expected = new double[] {
				2.2, -2.2,
				-0.4, 0.9,
				1.2, -0.2
		};

		var a = Matrix.ofSymmetric(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				0.5, 1.5, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, MatrixLayout.ofRowMajor(2, 3)).transpose();

		var factorisation = new CholeskyFactorisation((BasicSymmetricMatrix) a, 0.0);
		var result = factorisation.leftSolve(b).arrayBackedCopy().backingArray();

		assertArrayEquals(expected, result, VecmathTesting.ACCURACY);
	}

	@Test
	void rightSolve() {
		double[] expected = new double[] {
				2.2, -.4, 1.2,
				-2.2, .9, -.2
		};

		var a = Matrix.ofSymmetric(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				0.5, 1.5, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, MatrixLayout.ofRowMajor(2, 3));

		var factorisation = new CholeskyFactorisation((BasicSymmetricMatrix) a, 0.0);
		var result = factorisation.rightSolve(b).arrayBackedCopy().backingArray();

		assertArrayEquals(expected, result, VecmathTesting.ACCURACY);
	}

	@Test
	void inverse() {
		double[] expected = new double[] {
				1.8, -.6, .8,
				-.6, .2, .4,
				.8, .4, .8
		};

		var a = Matrix.ofSymmetric(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				0.5, 1.5, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		var factorisation = new CholeskyFactorisation((BasicSymmetricMatrix) a, 0.0);
		var result = factorisation.inverse().arrayBackedCopy().backingArray();

		assertArrayEquals(expected, result, VecmathTesting.ACCURACY);
	}

	@Test
	void determinant() {
		var m = Matrix.ofSymmetric(new double[] {
				0.0, -1.0, 2.0,
				-1.0, -1.0, 1.5,
				2.0, 1.5, 0.0
		}, MatrixLayout.ofRowMajor(3, 3));

		var factorisation = new CholeskyFactorisation((BasicSymmetricMatrix) m, 0.0);

		assertEquals(-2.0, factorisation.determinant(), VecmathTesting.ACCURACY);
	}

	@Test
	void failsForSingularMatrix() {
		var m = Matrix.ofSymmetric(new double[] {
				-1 / 9.0, -1 / 3.0, .5,
				-1 / 3.0, -1,       1.5,
				.5,        1.5,     0
		}, MatrixLayout.ofRowMajor(3, 3));

		assertThrows(SingularMatrixException.class, () -> new CholeskyFactorisation((BasicSymmetricMatrix) m, VecmathTesting.ACCURACY));
	}
}