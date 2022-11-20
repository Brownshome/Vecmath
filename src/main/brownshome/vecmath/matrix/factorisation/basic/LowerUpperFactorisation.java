package brownshome.vecmath.matrix.factorisation.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.factorisation.SingularMatrixException;

/**
 * An LU factorisation of a square matrix.
 * <p>
 * Algorithms lifted from https://en.wikipedia.org/wiki/LU_decomposition
 */
public final class LowerUpperFactorisation implements Factorisation {
	/**
	 * Stores the decomposition of A as L + U - I
	 */
	private final ArrayMatrix decomposition;
	private final int[] permutation;
	private final double determinant;

	public LowerUpperFactorisation(ArrayMatrix matrix, double tolerance) {
		assert matrix.columns() == matrix.rows();

		this.decomposition = matrix;

		permutation = new int[decomposition.columns()];
		for (int i = 0; i < decomposition.rows(); i++) {
			permutation[i] = i;
		}

		determinant = performFactorisation(decomposition, permutation, tolerance);
		decomposition.permuteSelfByRow(permutation);
	}

	private static double performFactorisation(MMatrix decomposition, int[] permutations, double tolerance) {
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
	public Matrix leftSolve(Matrix other) {
		assert size() == other.rows();

		var m = other.permuteByRow(permutation).arrayBackedCopy();
		double[] array = m.backingArray();

			// Solve Ly = PB
			for (int r = 0; r < size(); r++) {
				for (int k = 0; k < r; k++) {
					for (int c = 0; c < m.columns(); c++) {
						array[m.layout().arrayIndex(r, c)] -= decomposition.get(r, k) * m.get(k, c);
					}
				}
			}

			// Solve Ux = y
			for (int r = size() - 1; r >= 0; r--) {
				for (int k = r + 1; k < size(); k++) {
					for (int c = 0; c < m.columns(); c++) {
						array[m.layout().arrayIndex(r, c)] -= decomposition.get(r, k) * m.get(k, c);
					}
				}

				for (int c = 0; c < m.columns(); c++) {
					array[m.layout().arrayIndex(r, c)] /= decomposition.get(r, r);
				}
			}

		return m;
	}

	@Override
	public Matrix rightSolve(Matrix other) {
		assert size() == other.columns();

		var m = other.transpose().arrayBackedCopy().transpose();
		double[] array = m.backingArray();

		// Solve zU = B
		for (int c = 0; c < size(); c++) {
			for (int k = 0; k < c; k++) {
				for (int r = 0; r < m.rows(); r++) {
					array[m.layout().arrayIndex(r, c)] -= decomposition.get(k, c) * m.get(r, k);
				}
			}

			for (int r = 0; r < m.rows(); r++) {
				array[m.layout().arrayIndex(r, c)] /= decomposition.get(c, c);
			}
		}

		// Solve yL = z
		for (int c = size() - 1; c >= 0; c--) {
			for (int k = c + 1; k < size(); k++) {
				for (int r = 0; r < m.rows(); r++) {
					array[m.layout().arrayIndex(r, c)] -= decomposition.get(k, c) * m.get(r, k);
				}
			}
		}

		// Solve y / P = x
		return m.permuteByColumn(permutation);
	}

	@Override
	public double determinant() {
		return determinant;
	}
}
