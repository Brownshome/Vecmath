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
	private final MatrixView decomposition;
	private final MatrixView permutation;
	private final int determinantPolarity;

	public LUFactorisation(Matrix matrix, double tolerance) {
		assert matrix.columns() == matrix.rows();

		int[] permutation = new int[matrix.columns()];
		for (int i = 0; i < matrix.rows(); i++) {
			permutation[i] = i;
		}

		determinantPolarity = performFactorisation(matrix, permutation, tolerance);
		decomposition = matrix.permuteRows(permutation);
		this.permutation = new PermutationMatrix(permutation);
	}

	private static int performFactorisation(Matrix decomposition, int[] permutations, double tolerance) {
		int size = permutations.length;
		int determinantScalar = 1;

		// Eliminate the values below the diagonal
		for (int c = 0; c < size; c++) {
			// Find the max element
			double max = decomposition.get(permutations[c], c);
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

			// Pivot the rows
			if (maxRow != c) {
				int swap = permutations[c];
				permutations[c] = permutations[maxRow];
				permutations[maxRow] = swap;
				determinantScalar = -determinantScalar;
			}

			// Subtract the greatest row for each row under it
			for (int r = c + 1; r < size; r++) {
				double valueAtColumn = decomposition.get(permutations[r], c) / max;
				decomposition.set(valueAtColumn, permutations[r], c);

				for (int k = c + 1; k < size; k++) {
					double value = decomposition.get(permutations[r], k);
					double maxRowValue = decomposition.get(permutations[c], k);
					decomposition.set(value - valueAtColumn * maxRowValue, permutations[r], k);
				}
			}
		}

		return determinantScalar;
	}

	@Override
	public int size() {
		return decomposition.rows();
	}

	@Override
	public Matrix leftSolve(MatrixView other) {
		assert size() == other.rows();

		Matrix answer = permutation.multiply(other);

		// Columns of B
		for (int c = 0; c < answer.columns(); c++) {

			// Solve Ly = PB
			for (int r = 0; r < size(); r++) {
				double value = answer.get(r, c);

				// Columns of L
				for (int k = 0; k < r; k++) {
					value -= decomposition.get(r, k) * answer.get(k, c);
				}

				answer.set(value, r, c);
			}

			// Solve Ux = y
			for (int r = size() - 1; r >= 0; r--) {
				double value = answer.get(r, c);

				// Column of U
				for (int k = r + 1; k < size(); k++) {
					value -= decomposition.get(r, k) * answer.get(k, c);
				}

				answer.set(value / decomposition.get(r, r), r, c);
			}
		}

		return answer;
	}

	@Override
	public MatrixView rightSolve(MatrixView other) {
		assert size() == other.columns();

		Matrix answer = other.copy();

		// Rows of B
		for (int r = 0; r < answer.rows(); r++) {

			// Solve yU = PB
			for (int c = 0; c < size(); c++) {
				double value = answer.get(r, c);

				// Rows of U
				for (int k = 0; k < c; k++) {
					value -= decomposition.get(k, c) * answer.get(r, k);
				}

				answer.set(value / decomposition.get(c, c), r, c);
			}

			// Solve xL = y
			for (int c = size() - 1; c >= 0; c--) {
				double value = answer.get(r, c);

				// Row of L
				for (int k = c + 1; k < size(); k++) {
					value -= decomposition.get(k, c) * answer.get(r, k);
				}

				answer.set(value, r, c);
			}
		}

		return answer.multiply(permutation);
	}

	@Override
	public double determinant() {
		// Product of diagonal elements of the U matrix
		double determinant = decomposition.get(0, 0) * determinantPolarity;

		for (int rc = 1; rc < size(); rc++) {
			determinant *= decomposition.get(rc, rc);
		}

		return determinant;
	}
}
