package brownshome.vecmath.matrix;

final class SymmetricMatrix implements SymmetricMatrixView, MatrixViewWithFastMultiply {
	private final MatrixView delegate;

	private SymmetricMatrix(MatrixView delegate) {
		assert isSymmetric(delegate);

		this.delegate = delegate;
	}

	private static boolean isSymmetric(MatrixView m) {
		if (m.rows() != m.columns()) {
			return false;
		}

		for (int r = 0; r < m.rows(); r++) {
			for (int c = 0; c < r; c++) {
				if (m.get(r, c) != m.get(c, r)) {
					return false;
				}
			}
		}

		return true;
	}

	static SymmetricMatrix of(MatrixView other) {
		return new SymmetricMatrix(other);
	}

	@Override
	public int size() {
		return delegate.rows();
	}

	@Override
	public double get(int row, int column) {
		return column <= row
				? delegate.get(row, column)
				: delegate.get(column, row);
	}

	@Override
	public Matrix multiply(MatrixView other) {
		return delegate.multiply(other);
	}

	@Override
	public Matrix asMatrix(MatrixLayout layout) {
		return delegate.asMatrix(layout);
	}

	@Override
	public Matrix asMatrix() {
		return delegate.asMatrix();
	}

	@Override
	public Matrix copy() {
		return delegate.copy();
	}

	@Override
	public SymmetricMatrix transpose() {
		return this;
	}

	@Override
	public MatrixView permuteRows(int... rows) {
		return delegate.permuteRows(rows);
	}

	@Override
	public MatrixView permuteColumns(int... columns) {
		return delegate.permuteColumns(columns);
	}

	@Override
	public String toString() {
		return MatrixView.toString(this);
	}


	@Override
	public Matrix leftMultiply(MatrixView other) {
		return other.multiply(delegate);
	}
}
