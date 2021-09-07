package brownshome.vecmath.matrix;

import brownshome.vecmath.CompareConstant;
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

		var a = SymmetricMatrix.of(Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				0.5, 1.5, 0.0
		}, 3, 3));

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, 2, 3).transpose();

		var factorisation = new CholeskyFactorisation(a, 0.0);
		var result = factorisation.leftSolve(b).asMatrix().backingArray();

		assertArrayEquals(expected, result, CompareConstant.ACCURACY);
	}

	@Test
	void rightSolve() {
		double[] expected = new double[] {
				2.2, -.4, 1.2,
				-2.2, .9, -.2
		};

		var a = SymmetricMatrix.of(Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				0.5, 1.5, 0.0
		}, 3, 3));

		Matrix b = Matrix.of(new double[] {
				1, 0, .5,
				-1, 1, .25
		}, 2, 3);

		var factorisation = new CholeskyFactorisation(a, 0.0);
		var result = factorisation.rightSolve(b).asMatrix().backingArray();

		assertArrayEquals(expected, result, CompareConstant.ACCURACY);
	}

	@Test
	void inverse() {
		double[] expected = new double[] {
				1.8, -.6, .8,
				-.6, .2, .4,
				.8, .4, .8
		};

		var a = SymmetricMatrix.of(Matrix.of(new double[] {
				0.0, -1.0, 0.5,
				-1.0, -1.0, 1.5,
				0.5, 1.5, 0.0
		}, 3, 3));

		var factorisation = new CholeskyFactorisation(a, 0.0);
		var result = factorisation.inverse().asMatrix().backingArray();

		assertArrayEquals(expected, result, CompareConstant.ACCURACY);
	}

	@Test
	void determinant() {
		var m = SymmetricMatrix.of(Matrix.of(new double[] {
				0.0, -1.0, 2.0,
				-1.0, -1.0, 1.5,
				2.0, 1.5, 0.0
		}, 3, 3));

		var factorisation = new CholeskyFactorisation(m, 0.0);

		assertEquals(-2.0, factorisation.determinant(), CompareConstant.ACCURACY);
	}

	@Test
	void failsForSingularMatrix() {
		var m = SymmetricMatrix.of(Matrix.of(new double[] {
				-1 / 9.0, -1 / 3.0, .5,
				-1 / 3.0, -1,       1.5,
				.5,        1.5,     0
		}, 3, 3));

		assertThrows(SingularMatrixException.class, () -> new CholeskyFactorisation(m, CompareConstant.ACCURACY));
	}
}