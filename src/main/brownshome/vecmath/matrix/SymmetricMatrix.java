package brownshome.vecmath.matrix;

final class SymmetricMatrix implements SymmetricMatrixView {
	private final int size;

	// Stored in row-major format, only storing the first N values of each row
	private final double[] matrix;

	private SymmetricMatrix(double[] m, int size) {
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
		return index(size, 0);
	}

	static int index(int r, int c) {
		assert c <= r;

		return r * (r + 1) / 2 + c;
	}

	static SymmetricMatrix of(SymmetricMatrix o) {
		return new SymmetricMatrix(o.matrix.clone(), o.size);
	}

	static SymmetricMatrix of(MatrixView m) {
		assert m.rows() == m.columns() && isSymmetric(m);

		double[] array = new double[backingArrayLength(m.rows())];

		for (int r = 0; r < m.rows(); r++) {
			for (int c = 0; c <= r; c++) {
				array[index(r, c)] = m.get(r, c);
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

		double[] array = new double[backingArrayLength(m.rows())];

		for (int r = 0; r < m.rows(); r++) {
			System.arraycopy(m.backingArray(), m.offset() + stride * r, array, index(r, 0), r + 1);
		}

		return new SymmetricMatrix(array, m.rows());
	}

	static MatrixView of(double[] matrix, int rows) {
		return new SymmetricMatrix(matrix, rows);
	}

	@Override
	public int rows() {
		return size;
	}

	@Override
	public int columns() {
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
		double[] array = new double[size * size];

		for (int r = 0; r < size; r++) {
			System.arraycopy(matrix, index(r, 0), array, r * size, r + 1);
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

	@Override
	public Factorisation factorise() {
		return SymmetricMatrixView.super.factorise();
	}

	double[] backingArray() {
		return matrix;
	}

	@Override
	public String toString() {
		return MatrixView.toString(this);
	}
}
