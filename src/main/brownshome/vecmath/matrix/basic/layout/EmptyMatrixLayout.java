package brownshome.vecmath.matrix.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;

/**
 * A matrix layout where either the rows or columns are zero
 * @param rows rows
 * @param columns columns
 */
public record EmptyMatrixLayout(int rows, int columns) implements MatrixLayout {
	public EmptyMatrixLayout {
		assert rows == 0 || columns == 0;
	}

	@Override
	public int start() {
		throw new UnsupportedOperationException("An empty layout has no start");
	}

	@Override
	public int end() {
		throw new UnsupportedOperationException("An empty layout has no end");
	}

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isContinuous() {
		return true;
	}

	@Override
	public boolean isPacked() {
		return true;
	}

	@Override
	public int arrayIndex(int row, int column) {
		throw new UnsupportedOperationException("An empty layout has no valid indexes");
	}

	@Override
	public boolean isRowPacked() {
		return true;
	}

	@Override
	public boolean isColumnPacked() {
		return true;
	}

	@Override
	public MatrixLayout transpose() {
		return this;
	}
}
