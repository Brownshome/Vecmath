package brownshome.vecmath.matrix.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.VecNLayout;

public record VecNMatrixLayout(VecNLayout delegate) implements MatrixLayout {
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
		assert column == 0;
		assert row < delegate.elements();
		assert row >= 0;

		return delegate.arrayIndex(row);
	}

	@Override
	public int rows() {
		return delegate.elements();
	}

	@Override
	public int columns() {
		return 1;
	}

	@Override
	public boolean isRowPacked() {
		return true;
	}

	@Override
	public boolean isColumnPacked() {
		return delegate.isPacked();
	}

	@Override
	public VecNLayout row(int r) {
		throw new UnsupportedOperationException();
	}

	@Override
	public VecNLayout column(int c) {
		return delegate;
	}
}
