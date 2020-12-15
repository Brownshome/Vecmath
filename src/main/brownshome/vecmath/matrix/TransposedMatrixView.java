package brownshome.vecmath.matrix;

class TransposedMatrixView implements MatrixView {
	private final MatrixView delegate;

	public TransposedMatrixView(MatrixView matrix) {
		this.delegate = matrix;
	}

	@Override
	public int rows() {
		return delegate.columns();
	}

	@Override
	public int columns() {
		return delegate.rows();
	}

	@Override
	public double get(int row, int column) {
		return delegate.get(column, row);
	}

	@Override
	public MatrixView transpose() {
		return delegate;
	}

	@Override
	public double determinant() {
		return delegate.determinant();
	}

	@Override
	public MatrixView invert() {
		return delegate.invert().transpose();
	}
}
