package brownshome.vecmath.matrix;

/**
 * An Cholesky factorisation of a square symmetric matrix.
 *
 * Algorithms lifted from https://en.wikipedia.org/wiki/Cholesky_decomposition
 */
final class CholeskyFactorisation implements Factorisation {
	/**
	 * Don't bother pivoting if the leading value is larger than this
	 */
	private static final double PIVOT_THRESHOLD = 1e-6;

	/**
	 * Stores the decomposition of A as L
	 */
	private final Matrix decomposition;
	private final MatrixView permutation;
	private final double determinant;

	CholeskyFactorisation(SymmetricMatrixView matrix, double tolerance) {
		int[] permutation = new int[matrix.rows()];
		for (int i = 0; i < matrix.rows(); i++) {
			permutation[i] = i;
		}

		decomposition = matrix.copy();
		determinant = performFactorisation(decomposition, permutation, tolerance);
		this.permutation = new PermutationMatrix(permutation);
	}

	/**
	 * Factorises the given matrix in-place.
	 *
	 * https://en.wikipedia.org/wiki/Cholesky_decomposition#LDL_decomposition_2
	 *
	 * @param m the matrix to factorise
	 * @param permutation a permutation that is used to pivot the rows and columns of m.
	 * @param tolerance a value to detect singular matrices
	 * @return the determinant of the matrix
	 */
	private static double performFactorisation(Matrix m, int[] permutation, double tolerance) {
		int size = permutation.length;
		double determinant = 1.0;
		double[] array = m.backingArray();

		for (int rc = 0; rc < size - 1; rc++) {
			// Find the largest diagonal element remaining to pivot
			int maxIndex = rc;
			double diag = array[m.index(rc, rc)];
			for (int rrcc = rc + 1; Math.abs(diag) <= Math.max(tolerance, PIVOT_THRESHOLD) && rrcc < size; rrcc++) {
				double value = array[m.index(rrcc, rrcc)];

				if (Math.abs(value) > Math.abs(diag)) {
					diag = value;
					maxIndex = rrcc;
				}
			}

			if (rc != maxIndex) {
				int swap = permutation[rc];
				permutation[rc] = permutation[maxIndex];
				permutation[maxIndex] = swap;

				// Pivot
				pivot(m, rc, maxIndex);
			}

			determinant *= diag;

			// Compute all L values in this column
			for (int r = rc + 1; r < size; r++) {
				double l = array[m.index(r, rc)];

				for (int c = 0; c < rc; c++) {
					l -= array[m.index(rc, c)] * array[m.index(r, c)] * array[m.index(c, c)];
				}

				// Adjust other diagonal values using the new diagonal value
				array[m.index(r, r)] -= l * (l /= diag);
				array[m.index(r, rc)] = l;
			}
		}

		determinant *= array[m.index(size - 1, size - 1)];

		if (Math.abs(determinant) <= tolerance) {
			throw new SingularMatrixException();
		}

		return determinant;
	}

	/**
	 * Pivot a and b, a <= b
	 */
	private static void pivot(Matrix m, int a, int b) {
		assert a < b;

		double[] array = m.backingArray();

		// (a, a) <-> (b, b)
		{
			double tmp = array[m.index(a, a)];
			array[m.index(a, a)] = array[m.index(b, b)];
			array[m.index(b, b)] = tmp;
		}

		// (a, i) <-> (b, i) [i < a]
		for (int i = 0; i < a; i++) {
			double tmp = array[m.index(a, i)];
			array[m.index(a, i)] = array[m.index(b, i)];
			array[m.index(b, i)] = tmp;
		}

		// (i, a) <-> (b, i) [a < i < b]
		for (int i = a + 1; i < b; i++) {
			double tmp = array[m.index(i, a)];
			array[m.index(i, a)] = array[m.index(b, i)];
			array[m.index(b, i)] = tmp;
		}

		// (i, b) <-> (i, a) [b < i]
		for (int i = b + 1; i < m.rows(); i++) {
			double tmp = array[m.index(i, a)];
			array[m.index(i, a)] = array[m.index(i, b)];
			array[m.index(i, b)] = tmp;
		}
	}

	@Override
	public int size() {
		return decomposition.rows();
	}

	@Override
	public MatrixView leftSolve(MatrixView other) {
		assert size() == other.rows();

		var s = decomposition;
		double[] m = s.backingArray();
		var answer = permutation.multiply(other).asMatrix();

		// Columns of PB
		for (int c = 0; c < answer.columns(); c++) {

			// Solve Ly = PB
			for (int r = 0; r < size(); r++) {
				double value = answer.get(r, c);

				// Columns of L
				for (int k = 0; k < r; k++) {
					value -= m[s.index(r, k)] * answer.get(k, c);
				}

				answer.set(value, r, c);
			}

			// Solve L'x = D⁻¹y
			for (int r = size() - 1; r >= 0; r--) {
				double value = answer.get(r, c) / m[s.index(r, r)];

				// Column of L'
				for (int k = r + 1; k < size(); k++) {
					value -= m[s.index(k, r)] * answer.get(k, c);
				}

				answer.set(value, r, c);
			}
		}

		return permutation.transpose().multiply(answer);
	}

	@Override
	public MatrixView rightSolve(MatrixView other) {
		assert size() == other.columns();

		var s = decomposition;
		double[] m = s.backingArray();
		var answer = other.multiply(permutation.transpose()).asMatrix();

		// Rows of BP'
		for (int r = 0; r < answer.rows(); r++) {

			// Solve yL' = BP'
			for (int c = 0; c < size(); c++) {
				double value = answer.get(r, c);

				// Rows of L
				for (int k = 0; k < c; k++) {
					value -= m[s.index(c, k)] * answer.get(r, k);
				}

				answer.set(value, r, c);
			}

			// Solve xL = yD⁻¹
			for (int c = size() - 1; c >= 0; c--) {
				double value = answer.get(r, c) / m[s.index(c, c)];

				// Row of L
				for (int k = c + 1; k < size(); k++) {
					value -= m[s.index(k, c)] * answer.get(r, k);
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
