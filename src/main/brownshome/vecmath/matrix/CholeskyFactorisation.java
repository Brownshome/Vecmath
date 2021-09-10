package brownshome.vecmath.matrix;

import jdk.incubator.vector.DoubleVector;

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

	// Negative diagonal elements
	private final double[] diagonal;
	private final MatrixView permutation;
	private final double determinant;

	CholeskyFactorisation(SymmetricMatrixView matrix, double tolerance) {
		// Wider stride
		int stride = matrix.rows() + DoubleVector.SPECIES_PREFERRED.length() - 1;

		// Special layout to allow vectorisation, in column major order
		decomposition = Matrix.zeros(new MatrixLayout(
				matrix.rows(), matrix.columns(),
				1, stride,
				0, stride * matrix.columns()
		));
		diagonal = new double[matrix.size() + DoubleVector.SPECIES_PREFERRED.length() - 1];

		var array = decomposition.backingArray();
		for (int c = 0; c < matrix.size(); c++) {
			diagonal[c] = -matrix.get(c, c);

			for (int r = c + 1; r < matrix.size(); r++) {
				array[r + stride * c] = matrix.get(r, c);
			}
		}

		int[] permutation = new int[matrix.columns()];
		for (int i = 0; i < matrix.rows(); i++) {
			permutation[i] = i;
		}

		determinant = performFactorisation(decomposition, diagonal, permutation, tolerance);
		this.permutation = new PermutationMatrix(permutation);
	}

	/**
	 * Factorises the given matrix in-place.
	 *
	 * https://en.wikipedia.org/wiki/Cholesky_decomposition#LDL_decomposition_2
	 *
	 * @param m the matrix to factorise
	 * @param diagonal the diagonal entries
	 * @param permutation a permutation that is used to pivot the rows and columns of m.
	 * @param tolerance a value to detect singular matrices
	 * @return the determinant of the matrix
	 */
	private static double performFactorisation(Matrix m, double[] diagonal, int[] permutation, double tolerance) {
		var species = DoubleVector.SPECIES_PREFERRED;
		int size = permutation.length;
		double determinant = 1.0;
		double[] array = m.backingArray();

		for (int rc = 0; rc < size - 1; rc++) {
			// Find the largest diagonal element remaining to pivot
			int maxIndex = rc;
			double diag = -diagonal[rc];
			for (int rrcc = rc + 1; Math.abs(diag) <= Math.max(tolerance, PIVOT_THRESHOLD) && rrcc < size; rrcc++) {
				double value = -diagonal[rrcc];

				if (Math.abs(value) > Math.abs(diag)) {
					diag = value;
					maxIndex = rrcc;
				}
			}

			if (rc != maxIndex) {
				int swap = permutation[rc];
				permutation[rc] = permutation[maxIndex];
				permutation[maxIndex] = swap;

				diagonal[maxIndex] = diagonal[rc];
				diagonal[rc] = -diag;

				// Pivot
				pivot(m, rc, maxIndex);
			}

			determinant *= diag;

			for (int r = rc + 1; r < size; r += species.length()) {
				var raw = DoubleVector.fromArray(species, array, m.index(r, rc));
				var l = raw.div(diag);
				l.intoArray(array, m.index(r, rc));

				var diagonalVec = DoubleVector.fromArray(species, diagonal, r);
				raw.fma(l, diagonalVec).intoArray(diagonal, r);
			}

			for (int c = rc + 1; c < size - 1; c++) {
				var lCol = DoubleVector.broadcast(species, -diag * array[m.index(c, rc)]);

				for (int r = c + 1; r < size; r += species.length()) {
					// Lrc -= Lr rc * Lc rc * Drc
					var lRow = DoubleVector.fromArray(species, array, m.index(r, rc));
					var l = DoubleVector.fromArray(species, array, m.index(r, c));
					lRow.fma(lCol, l).intoArray(array, m.index(r, c));
				}
			}
		}

		determinant *= -diagonal[m.rows() - 1];

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
				double value = answer.get(r, c) / -diagonal[r];

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
				double value = answer.get(r, c) / -diagonal[c];

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
