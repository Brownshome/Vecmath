package brownshome.vecmath.matrix;

import java.util.Arrays;

final class SymmetricMatrix implements SymmetricMatrixView {
	private final int size;

	// Stored in row-major format, ignoring elements above the main diagonal
	private final double[] matrix;

	private SymmetricMatrix(double[] m, int size) {
		assert m.length == size * size;

		this.matrix = m;
		this.size = size;
	}

	private static boolean isSymmetric(MatrixView m) {
		for (int r = 0; r < m.rows(); r++) {
			for (int c = 0; c < r; c++) {
				if (m.get(r, c) != m.get(c, r)) {
					return false;
				}
			}
		}

		return true;
	}

	private static int backingArrayLength(int size) {
		return size * size;
	}

	int index(int r, int c) {
		assert c <= r;

		return r * size + c;
	}

	static SymmetricMatrix of(MatrixView m) {
		assert m.rows() == m.columns() && isSymmetric(m);

		double[] array = new double[backingArrayLength(m.rows())];

		var result = new SymmetricMatrix(array, m.rows());

		for (int r = 0; r < m.rows(); r++) {
			for (int c = 0; c <= r; c++) {
				array[result.index(r, c)] = m.get(r, c);
			}
		}

		return new SymmetricMatrix(array, m.rows());
	}

	static SymmetricMatrix of(Matrix m) {
		assert m.rows() == m.columns() && isSymmetric(m);

		int stride;

		if (m.columnStride() == 1) {
			stride = m.rowStride();
		} else if (m.rowStride() == 1) {
			stride = m.columnStride();
		} else {
			return of((MatrixView) m);
		}

		double[] array;
		if (stride == m.rows()) {
			array = Arrays.copyOfRange(m.backingArray(), m.offset(), m.offset() + backingArrayLength(m.rows()));
		} else {
			array = new double[backingArrayLength(m.rows())];

			for (int r = 0; r < m.rows(); r++) {
				System.arraycopy(m.backingArray(), m.offset() + stride * r, array, r * m.rows(), r + 1);
			}
		}

		return new SymmetricMatrix(array, m.rows());
	}

	static SymmetricMatrix of(double[] matrix, int rows) {
		return new SymmetricMatrix(matrix, rows);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public double get(int row, int column) {
		assert row < size && column < size;

		return matrix[column <= row
				? index(row, column)
				: index(column, row)];
	}

	@Override
	public Matrix copy() {
		double[] array = matrix.clone();

		for (int r = 0; r < size; r++) {
			for (int c = r + 1; c < size; c++) {
				array[r * size + c] = matrix[index(c, r)];
			}
		}

		return Matrix.of(array, size, size);
	}

	@Override
	public SymmetricMatrix transpose() {
		return this;
	}

	double[] backingArray() {
		return matrix;
	}

	@Override
	public String toString() {
		return MatrixView.toString(this);
	}

	@Override
	public Factorisation factorise() {
		return new CholeskyFactorisation(new SymmetricMatrix(matrix, size), 1e-10);
	}
}
