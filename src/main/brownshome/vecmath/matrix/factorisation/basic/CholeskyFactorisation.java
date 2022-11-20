package brownshome.vecmath.matrix.factorisation.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.basic.BasicSymmetricMatrix;
import brownshome.vecmath.matrix.basic.PermutationMatrix;
import brownshome.vecmath.matrix.basic.SymmetricMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.SingularMatrixException;

/**
 * An Cholesky factorisation of a square symmetric matrix.
 * <p>
 * Algorithms lifted from https://en.wikipedia.org/wiki/Cholesky_decomposition
 */
public final class CholeskyFactorisation implements Factorisation {
	/**
	 * Stores the decomposition of A as L + L' + D - 2I
	 */
	private final BasicSymmetricMatrix decomposition;
	private final Matrix permutation;
	private final double determinant;

	public CholeskyFactorisation(BasicSymmetricMatrix matrix, double tolerance) {
		int[] permutation = new int[matrix.rows()];
		for (int i = 0; i < matrix.rows(); i++) {
			permutation[i] = i;
		}

		determinant = performFactorisation(matrix, permutation, tolerance);
		decomposition = matrix;
		this.permutation = new PermutationMatrix(permutation);
	}

	/**
	 * Factorises the given matrix in-place.
	 * <p>
	 * https://en.wikipedia.org/wiki/Cholesky_decomposition#LDL_decomposition_2
	 *
	 * @param s the matrix to factorise
	 * @param permutation a permutation that is used to pivot the rows and columns of s.
	 * @param tolerance a value to detect singular matrices
	 * @return the determinant of the matrix
	 */
	private static double performFactorisation(BasicSymmetricMatrix s, int[] permutation, double tolerance) {
		double determinant = 1.0;
		double[] m = s.backingArray();

		for (int r = 0; r < s.size(); r++) {
			// Find the largest diagonal element remaining to pivot
			int maxIndex = r;
			double max = Math.abs(m[s.layout().arrayIndex(r, r)]);
			for (int i = r + 1; i < s.size(); i++) {
				double x = Math.abs(m[s.layout().arrayIndex(i, i)]);
				if (x > max) {
					max = x;
					maxIndex = i;
				}
			}

			// Pivot
			pivot(s, r, maxIndex);

			double diag = m[s.layout().arrayIndex(r, r)];

			if (Math.abs(diag) < tolerance) {
				throw new SingularMatrixException();
			}

			determinant *= diag;
			{
				int swap = permutation[r];
				permutation[r] = permutation[maxIndex];
				permutation[maxIndex] = swap;
			}

			// Compute all L values in this column
			for (int k = r + 1; k < s.size(); k++) {
				double l = m[s.layout().arrayIndex(k, r)];

				for (int c = 0; c < r; c++) {
					l -= m[s.layout().arrayIndex(r, c)] * m[s.layout().arrayIndex(k, c)] * m[s.layout().arrayIndex(c, c)];
				}

				m[s.layout().arrayIndex(k, r)] = l / diag;
			}

			// Adjust other diagonal values using the new diagonal value
			for (int i = r + 1; i < s.rows(); i++) {
				double lVal = m[s.layout().arrayIndex(i, r)];
				m[s.layout().arrayIndex(i, i)] -= diag * lVal * lVal;
			}
		}

		return determinant;
	}

	/**
	 * Pivot a and b, a <= b
	 */
	private static void pivot(BasicSymmetricMatrix s, int a, int b) {
		assert a <= b;

		double[] m = s.backingArray();

		if (a == b) {
			return;
		}

		// (a, a) <-> (b, b)
		{
			double tmp = m[s.layout().arrayIndex(a, a)];
			m[s.layout().arrayIndex(a, a)] = m[s.layout().arrayIndex(b, b)];
			m[s.layout().arrayIndex(b, b)] = tmp;
		}

		// (a, i) <-> (b, i) [i < a]
		for (int i = 0; i < a; i++) {
			double tmp = m[s.layout().arrayIndex(a, i)];
			m[s.layout().arrayIndex(a, i)] = m[s.layout().arrayIndex(b, i)];
			m[s.layout().arrayIndex(b, i)] = tmp;
		}

		// (i, a) <-> (b, i) [a < i < b]
		for (int i = a + 1; i < b; i++) {
			double tmp = m[s.layout().arrayIndex(i, a)];
			m[s.layout().arrayIndex(i, a)] = m[s.layout().arrayIndex(b, i)];
			m[s.layout().arrayIndex(b, i)] = tmp;
		}

		// (i, b) <-> (i, a) [b < i]
		for (int i = b + 1; i < s.rows(); i++) {
			double tmp = m[s.layout().arrayIndex(i, a)];
			m[s.layout().arrayIndex(i, a)] = m[s.layout().arrayIndex(i, b)];
			m[s.layout().arrayIndex(i, b)] = tmp;
		}
	}

	@Override
	public int size() {
		return decomposition.rows();
	}

	@Override
	public Matrix leftSolve(Matrix other) {
		assert size() == other.rows();

		var s = decomposition;
		double[] m = s.backingArray();
		var answer = permutation.multiply(other).copy();

		// Columns of PB
		for (int c = 0; c < answer.columns(); c++) {

			// Solve Ly = PB
			for (int r = 0; r < size(); r++) {
				double value = answer.get(r, c);

				// Columns of L
				for (int k = 0; k < r; k++) {
					value -= m[s.layout().arrayIndex(r, k)] * answer.get(k, c);
				}

				answer.set(value, r, c);
			}

			// Solve L'x = D⁻¹y
			for (int r = size() - 1; r >= 0; r--) {
				double value = answer.get(r, c) / m[s.layout().arrayIndex(r, r)];

				// Column of L'
				for (int k = r + 1; k < size(); k++) {
					value -= m[s.layout().arrayIndex(k, r)] * answer.get(k, c);
				}

				answer.set(value, r, c);
			}
		}

		return permutation.transpose().multiply(answer);
	}

	@Override
	public Matrix rightSolve(Matrix other) {
		assert size() == other.columns();

		var s = decomposition;
		double[] m = s.backingArray();
		var answer = other.multiply(permutation.transpose()).copy();

		// Rows of BP'
		for (int r = 0; r < answer.rows(); r++) {

			// Solve yL' = BP'
			for (int c = 0; c < size(); c++) {
				double value = answer.get(r, c);

				// Rows of L
				for (int k = 0; k < c; k++) {
					value -= m[s.layout().arrayIndex(c, k)] * answer.get(r, k);
				}

				answer.set(value, r, c);
			}

			// Solve xL = yD⁻¹
			for (int c = size() - 1; c >= 0; c--) {
				double value = answer.get(r, c) / m[s.layout().arrayIndex(c, c)];

				// Row of L
				for (int k = c + 1; k < size(); k++) {
					value -= m[s.layout().arrayIndex(k, c)] * answer.get(r, k);
				}

				answer.set(value, r, c);
			}
		}

		return answer.multiply(permutation);
	}

	@Override
	public double determinant() {
		return determinant;
	}
}
