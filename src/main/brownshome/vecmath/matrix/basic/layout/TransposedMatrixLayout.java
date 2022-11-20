package brownshome.vecmath.matrix.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.VecNLayout;

public record TransposedMatrixLayout(MatrixLayout delegate) implements MatrixLayout {
	@Override
	public int start() {
		return delegate.start();
	}

	@Override
	public int end() {
		return delegate.end();
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isContinuous() {
		return delegate.isContinuous();
	}

	@Override
	public boolean isPacked() {
		return delegate.isPacked();
	}

	@Override
	public int arrayIndex(int row, int column) {
		return delegate.arrayIndex(column, row);
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
	public boolean isRowOptimal() {
		return delegate.isColumnOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return delegate.isRowOptimal();
	}

	@Override
	public boolean isRowPacked() {
		return delegate.isColumnPacked();
	}

	@Override
	public boolean isColumnPacked() {
		return delegate.isRowPacked();
	}

	@Override
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		return delegate.subLayout(c, r, columns, rows).transpose();
	}

	@Override
	public MatrixLayout transpose() {
		return delegate;
	}

	@Override
	public MatrixLayout permuteByRow(int... rows) {
		return delegate.permuteByColumn(rows).transpose();
	}

	@Override
	public MatrixLayout permuteByColumn(int... columns) {
		return delegate.permuteByRow(columns).transpose();
	}

	@Override
	public MatrixLayout rowOptimal() {
		return delegate.columnOptimal();
	}

	@Override
	public MatrixLayout columnOptimal() {
		return delegate.rowOptimal();
	}

	@Override
	public VecNLayout row(int r) {
		return delegate.column(r);
	}

	@Override
	public VecNLayout column(int c) {
		return delegate.row(c);
	}
}
