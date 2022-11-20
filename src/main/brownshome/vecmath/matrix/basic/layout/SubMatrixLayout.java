package brownshome.vecmath.matrix.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;

/**
 * A matrix layout representing a sub-layout of a delegate
 * @param delegate the delegate
 * @param r the starting row
 * @param c the starting column
 * @param rows the number of rows
 * @param columns the number of columns
 */
public record SubMatrixLayout(MatrixLayout delegate, int r, int c, int rows, int columns) implements MatrixLayout {
	public SubMatrixLayout {
		assert r >= 0;
		assert r + rows <= delegate.rows();
		assert rows > 0;
		assert c >= 0;
		assert c + columns <= delegate.columns();
		assert columns > 0;
	}

	@Override
	public int start() {
		return Math.min(delegate.arrayIndex(r, c), delegate.arrayIndex(r + rows - 1, c + columns - 1));
	}

	@Override
	public int end() {
		return Math.max(delegate.arrayIndex(r, c), delegate.arrayIndex(r + rows - 1, c + columns - 1)) + 1;
	}

	@Override
	public boolean isContinuous() {
		return size() == rows * columns;
	}

	@Override
	public boolean isPacked() {
		return isContinuous() && start() == 0;
	}

	@Override
	public int arrayIndex(int row, int column) {
		return delegate.arrayIndex(row + r, column + c);
	}

	@Override
	public boolean isRowOptimal() {
		return columns == 1 || delegate.isRowOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return rows == 1 || delegate.isColumnOptimal();
	}

	@Override
	public boolean isRowPacked() {
		return columns == 1 || delegate.isRowPacked();
	}

	@Override
	public boolean isColumnPacked() {
		return rows == 1 || delegate.isColumnPacked();
	}

	@Override
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		return delegate.subLayout(r + r(), c + c(), rows, columns);
	}

	@Override
	public MatrixLayout transpose() {
		// If we are the best sub-matrix for this delegate, then the same will
		// be true for the transpose
		return new SubMatrixLayout(delegate.transpose(), c, r, rows, columns);
	}

	@Override
	public MatrixLayout rowOptimal() {
		return isRowOptimal() ? this : delegate.rowOptimal().subLayout(r, c, rows, columns);
	}

	@Override
	public MatrixLayout columnOptimal() {
		return isColumnOptimal() ? this : delegate.columnOptimal().subLayout(r, c, rows, columns);
	}
}
