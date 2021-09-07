package brownshome.vecmath.matrix;

final class PermutedMatrixView implements MatrixView {
	private final MatrixView delegate;

	// The permutation to apply; null indicates the identity permutation
	private final int[] rows, columns;

	PermutedMatrixView(MatrixView matrix, int[] rows, int[] columns) {
		assert matrix != null;
		assert rows == null || rows.length == matrix.rows();
		assert columns == null || columns.length == matrix.columns();

		this.delegate = matrix;
		this.rows = rows;
		this.columns = columns;
	}

	static MatrixView of(MatrixView matrix, int[] rows, int[] columns) {
		if (rows == null && columns == null) {
			return matrix;
		}

		return new PermutedMatrixView(matrix, rows, columns);
	}

	static MatrixView rows(MatrixView matrix, int[] rows) {
		return of(matrix, rows, null);
	}

	static MatrixView columns(MatrixView matrix, int[] columns) {
		return of(matrix, null, columns);
	}

	@Override
	public int rows() {
		return delegate.rows();
	}

	@Override
	public int columns() {
		return delegate.columns();
	}

	@Override
	public double get(int row, int column) {
		if (rows != null) {
			row = rows[row];
		}

		if (columns != null) {
			column = columns[column];
		}

		return delegate.get(row, column);
	}

	@Override
	public MatrixView permuteRows(int... rows) {
		return of(delegate, PermutationUtil.combinePermutations(this.rows, rows), columns);
	}

	@Override
	public MatrixView permuteColumns(int... columns) {
		return of(delegate, rows, PermutationUtil.combinePermutations(this.columns, columns));
	}

	@Override
	public MatrixView transpose() {
		return of(delegate.transpose(), columns, rows);
	}

	@Override
	public String toString() {
		return MatrixView.toString(this);
	}
}
