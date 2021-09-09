package brownshome.vecmath.matrix;

import jdk.incubator.vector.DoubleVector;

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

		Matrix m = other.permuteRows(permutation).copy(MatrixLayout.optimal(other.rows(), other.columns()));
		double[] array = m.backingArray();

		var species = DoubleVector.SPECIES_PREFERRED;

		// Solve Ly = PB
		for (int r = 0; r < size(); r++) {
			for (int c = 0; c < m.columns(); c += species.length()) {
				var acc = DoubleVector.fromArray(species, array, m.index(r, c));

				for (int k = 0; k < r; k++) {
					var kRow = DoubleVector.fromArray(species, array, m.index(k, c));
					var lValue = DoubleVector.broadcast(species, -decomposition.get(r, k));
					acc = kRow.fma(lValue, acc);
				}

				acc.intoArray(array, m.index(r, c));
			}
		}

		// Solve Ux = y
		for (int r = size() - 1; r >= 0; r--) {
			for (int c = 0; c < m.columns(); c += species.length()) {
				var acc = DoubleVector.fromArray(species, array, m.index(r, c));

				for (int k = r + 1; k < size(); k++) {
					var kRow = DoubleVector.fromArray(species, array, m.index(k, c));
					var lValue = DoubleVector.broadcast(species, -decomposition.get(r, k));
					acc = kRow.fma(lValue, acc);
				}

				acc.div(decomposition.get(r, r)).intoArray(array, m.index(r, c));
			}
		}

		return m;
	}

	@Override
	public MatrixView rightSolve(MatrixView other) {
		assert size() == other.columns();

		Matrix m = other.copy(MatrixLayout.optimal(other.columns(), other.rows()).transpose());
		double[] array = m.backingArray();

		var species = DoubleVector.SPECIES_PREFERRED;

		// Solve zU = B
		for (int c = 0; c < size(); c++) {
			for (int r = 0; r < m.rows(); r += species.length()) {
				var acc = DoubleVector.fromArray(species, array, m.index(r, c));

				for (int k = 0; k < c; k++) {
					var kRow = DoubleVector.fromArray(species, array, m.index(r, k));
					var lValue = DoubleVector.broadcast(species, -decomposition.get(k, c));
					acc = kRow.fma(lValue, acc);
				}

				acc.div(decomposition.get(c, c)).intoArray(array, m.index(r, c));
			}
		}

		// Solve yL = z
		for (int c = size() - 1; c >= 0; c--) {
			for (int r = 0; r < m.rows(); r += species.length()) {
				var acc = DoubleVector.fromArray(species, array, m.index(r, c));

				for (int k = c + 1; k < size(); k++) {
					var kRow = DoubleVector.fromArray(species, array, m.index(r, k));
					var lValue = DoubleVector.broadcast(species, -decomposition.get(k, c));
					acc = kRow.fma(lValue, acc);
				}

				acc.intoArray(array, m.index(r, c));
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
