package brownshome.vecmath.matrix.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;

public record SymmetricMatrixLayout(int matrixSize, int offset, int rowPadding) implements MatrixLayout {
	public SymmetricMatrixLayout {
		assert matrixSize > 0;
		assert offset >= 0;
		assert rowPadding >= 0;
	}

	@Override
	public int start() {
		return offset;
	}

	@Override
	public int size() {
		return arrayIndex(matrixSize - 1, matrixSize - 1) + 1;
	}

	@Override
	public boolean isContinuous() {
		return rowPadding == 0 || matrixSize == 1;
	}

	@Override
	public boolean isPacked() {
		return isContinuous() && offset == 0;
	}

	@Override
	public int arrayIndex(int row, int column) {
		if (column > row) {
			return arrayIndex(column, row);
		}

		return start() + row * (row + 1 + rowPadding * 2) / 2 + column;
	}

	@Override
	public int rows() {
		return matrixSize;
	}

	@Override
	public int columns() {
		return matrixSize;
	}

	@Override
	public boolean isRowPacked() {
		return true;
	}

	@Override
	public boolean isColumnPacked() {
		return matrixSize == 1 ||
				matrixSize == 2 && rowPadding == 0;
	}

	@Override
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		if (rows != 0 && r == c && rows == columns) {
			assert r >= 0;
			assert r + rows <= rows();

			return new SymmetricMatrixLayout(rows, arrayIndex(r, c), matrixSize - rows + rowPadding);
		}

		return MatrixLayout.super.subLayout(r, c, rows, columns);
	}

	@Override
	public MatrixLayout transpose() {
		return this;
	}
}
