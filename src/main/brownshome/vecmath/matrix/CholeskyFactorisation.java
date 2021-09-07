package brownshome.vecmath.matrix;

/**
 * An Cholesky factorisation of a square symmetric matrix.
 *
 * Algorithms lifted from https://en.wikipedia.org/wiki/Cholesky_decomposition
 */
final class CholeskyFactorisation implements Factorisation {
	/**
	 * Stores the decomposition of A as L + L' + D - 2I
	 */
	private final SymmetricMatrix decomposition;
	private final MatrixView permutation;
	private final double determinant;

	CholeskyFactorisation(SymmetricMatrix matrix, double tolerance) {
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
	 *
	 * https://en.wikipedia.org/wiki/Cholesky_decomposition#LDL_decomposition_2
	 *
	 * @param s the matrix to factorise
	 * @param permutation a permutation that is used to pivot the rows and columns of s.
	 * @param tolerance a value to detect singular matrices
	 * @return the determinant of the matrix
	 */
	private static double performFactorisation(SymmetricMatrix s, int[] permutation, double tolerance) {
		double determinant = 1.0;
		double[] m = s.backingArray();

		for (int r = 0, rowStart = 0; r < s.rows();  r++, rowStart += r) {
			int indexD = rowStart + r;

			// Find the largest diagonal element remaining to pivot
			int maxIndex = r;
			double max = Math.abs(m[indexD]);
			for (int i = r + 1, d = indexD + 1; i < s.rows(); i++, d += i) {
				double x = Math.abs(m[d + i]);
				if (x > max) {
					max = x;
					maxIndex = i;
				}
			}

			// Pivot
			pivot(s, r, maxIndex);

			double diag = m[indexD];

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
			for (int k = r + 1, lRowStart = rowStart + k; k < s.rows(); k++, lRowStart += k) {
				int indexL = lRowStart + r;
				double l = m[indexL];

				for (int c = 0, d = 0; c < r; c++, d += c) {
					l -= m[rowStart + c] * m[lRowStart + c] * m[d + c];
				}

				m[indexL] = l / diag;
			}

			// Adjust other diagonal values using the new diagonal value
			for (int i = r + 1, dRowStart = rowStart + i; i < s.rows(); i++, dRowStart += i) {
				double lVal = m[dRowStart + r];
				m[dRowStart + i] -= diag * lVal * lVal;
			}
		}

		return determinant;
	}

	/**
	 * Pivot a and b, a <= b
	 */
	private static void pivot(SymmetricMatrix s, int a, int b) {
		assert a <= b;

		double[] m = s.backingArray();

		if (a == b) {
			return;
		}

		int indexA = SymmetricMatrix.index(a, 0), indexB = SymmetricMatrix.index(b, 0);

		// (a, a) <-> (b, b)
		{
			double tmp = m[indexA + a];
			m[indexA + a] = m[indexB + b];
			m[indexB + b] = tmp;
		}

		// (a, i) <-> (b, i) [i < a]
		for (int i = 0; i < a; i++) {
			double tmp = m[indexA + i];
			m[indexA + i] = m[indexB + i];
			m[indexB + i] = tmp;
		}

		// (i, a) <-> (b, i) [a < i < b]
		indexA = SymmetricMatrix.index(a + 1, a);
		for (int i = a + 1; i < b; i++, indexA += i) {
			double tmp = m[indexA];
			m[indexA] = m[indexB + i];
			m[indexB + i] = tmp;
		}

		// (i, b) <-> (i, a) [b < i]
		indexB = SymmetricMatrix.index(b + 1, b);
		indexA += b + 1;
		for (int i = b + 1; i < s.rows(); i++, indexA += i, indexB += i) {
			double tmp = m[indexA];
			m[indexA] = m[indexB];
			m[indexB] = tmp;
		}
	}

	@Override
	public int size() {
		return decomposition.rows();
	}

	@Override
	public MatrixView leftSolve(MatrixView other) {
		assert size() == other.rows();

		double[] m = decomposition.backingArray();
		var answer = permutation.multiply(other).asMatrix();

		// Columns of PB
		for (int c = 0; c < answer.columns(); c++) {

			// Solve Ly = PB
			int rowStart = 0;
			for (int r = 0; r < size(); r++, rowStart += r) {
				double value = answer.get(r, c);

				// Columns of L
				for (int k = 0; k < r; k++) {
					value -= m[rowStart + k] * answer.get(k, c);
				}

				answer.set(value, r, c);
			}

			// Solve L'x = D⁻¹y
			rowStart -= size();
			for (int r = size() - 1; r >= 0; rowStart -= r, r--) {
				double value = answer.get(r, c) / m[rowStart + r];

				// Column of L'
				for (int k = r + 1, columnStart = rowStart + k; k < size(); k++, columnStart += k) {
					value -= m[columnStart + r] * answer.get(k, c);
				}

				answer.set(value, r, c);
			}
		}

		return permutation.transpose().multiply(answer);
	}

	@Override
	public MatrixView rightSolve(MatrixView other) {
		assert size() == other.columns();

		double[] m = decomposition.backingArray();
		var answer = other.multiply(permutation.transpose()).asMatrix();

		// Rows of BP'
		for (int r = 0; r < answer.rows(); r++) {

			// Solve yL' = BP'
			int columnStart = 0;
			for (int c = 0; c < size(); c++, columnStart += c) {
				double value = answer.get(r, c);

				// Rows of L
				for (int k = 0; k < c; k++) {
					value -= m[columnStart + k] * answer.get(r, k);
				}

				answer.set(value, r, c);
			}

			// Solve xL = yD⁻¹
			columnStart -= size();
			for (int c = size() - 1; c >= 0; columnStart -= c, c--) {
				double value = answer.get(r, c) / m[columnStart + c];

				// Row of L
				for (int k = c + 1, rowStart = columnStart + k; k < size(); k++, rowStart += k) {
					value -= m[rowStart + c] * answer.get(r, k);
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
