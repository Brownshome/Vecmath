package brownshome.vecmath.matrix;

/**
 * An LU factorisation of a square matrix.
 *
 * Algorithms lifted from https://en.wikipedia.org/wiki/LU_decomposition
 */
final class LUFactorisation implements Factorisation {
	/**
	 * Stores the decomposition of A as L + U - I
	 */
	private final Matrix decomposition;
	private final int[] permutation;
	private final double determinant;

	LUFactorisation(Matrix matrix, double tolerance) {
		assert matrix.columns() == matrix.rows();

		permutation = new int[matrix.columns()];
		for (int i = 0; i < matrix.rows(); i++) {
			permutation[i] = i;
		}

		determinant = performFactorisation(matrix, permutation, tolerance);
		decomposition = matrix.permuteRows(permutation).copy();
	}

	private static double performFactorisation(Matrix decomposition, int[] permutations, double tolerance) {
		int size = permutations.length;
		double determinant = 1.0;

		// Eliminate the values below the diagonal
		for (int c = 0; c < size; c++) {
			int columnIndex = permutations[c];

			// Find the max element
			double max = decomposition.get(columnIndex, c);
			int maxRow = c;
			for (int r = c + 1; r < size; r++) {
				double value = decomposition.get(permutations[r], c);

				if (Math.abs(value) > Math.abs(max)) {
					max = value;
					maxRow = r;
				}
			}

			if (Math.abs(max) < tolerance) {
				throw new SingularMatrixException();
			}

			determinant *= max;

			// Pivot the rows
			if (maxRow != c) {
				permutations[c] = permutations[maxRow];
				permutations[maxRow] = columnIndex;
				determinant = -determinant;

				columnIndex = permutations[c];
			}

			// Subtract the greatest row for each row under it
			for (int r = c + 1; r < size; r++) {
				int rowIndex = permutations[r];

				double valueAtColumn = decomposition.get(rowIndex, c) / max;
				decomposition.set(valueAtColumn, rowIndex, c);

				for (int k = c + 1; k < size; k++) {
					double value = decomposition.get(rowIndex, k);
					double maxRowValue = decomposition.get(columnIndex, k);
					decomposition.set(value - valueAtColumn * maxRowValue, rowIndex, k);
				}
			}
		}

		return determinant;
	}

	@Override
	public int size() {
		return decomposition.rows();
	}

	@Override
	public Matrix leftSolve(MatrixView other) {
		assert size() == other.rows();

		Matrix m = other.permuteRows(permutation).copy();
		double[] array = m.backingArray();

			// Solve Ly = PB
			for (int r = 0; r < size(); r++) {
				for (int k = 0; k < r; k++) {
					for (int c = 0; c < m.columns(); c++) {
						array[m.index(r, c)] -= decomposition.get(r, k) * m.get(k, c);
					}
				}
			}

			// Solve Ux = y
			for (int r = size() - 1; r >= 0; r--) {
				for (int k = r + 1; k < size(); k++) {
					for (int c = 0; c < m.columns(); c++) {
						array[m.index(r, c)] -= decomposition.get(r, k) * m.get(k, c);
					}
				}

				for (int c = 0; c < m.columns(); c++) {
					array[m.index(r, c)] /= decomposition.get(r, r);
				}
			}

		return m;
	}

	@Override
	public MatrixView rightSolve(MatrixView other) {
		assert size() == other.columns();

		Matrix m = other.transpose().copy().transpose();
		double[] array = m.backingArray();

		// Solve zU = B
		for (int c = 0; c < size(); c++) {
			for (int k = 0; k < c; k++) {
				for (int r = 0; r < m.rows(); r++) {
					array[m.index(r, c)] -= decomposition.get(k, c) * m.get(r, k);
				}
			}

			for (int r = 0; r < m.rows(); r++) {
				array[m.index(r, c)] /= decomposition.get(c, c);
			}
		}

		// Solve yL = z
		for (int c = size() - 1; c >= 0; c--) {
			for (int k = c + 1; k < size(); k++) {
				for (int r = 0; r < m.rows(); r++) {
					array[m.index(r, c)] -= decomposition.get(k, c) * m.get(r, k);
				}
			}
		}

		// Solve y / P = x
		return m.permuteColumns(permutation);
	}

	@Override
	public double determinant() {
		return determinant;
	}
}
