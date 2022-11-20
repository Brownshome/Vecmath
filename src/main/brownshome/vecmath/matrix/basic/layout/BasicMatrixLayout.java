package brownshome.vecmath.matrix.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * The layout of a matrix
 *
 * @param rows         the number of rows in this matrix
 * @param columns      the number of columns in this matrix
 * @param offset       the offset from the start of the array that this matrix shape will use
 * @param rowStride    the difference in indices that results from increasing the row index.
 *                     This may be negative (or zero if there are no rows)
 * @param columnStride the difference in indices that results from increasing the column index.
 *                     This may be negative (or zero if there are no columns)
 */
public record BasicMatrixLayout(int rows, int columns, int offset, int rowStride, int columnStride) implements MatrixLayout {
	public BasicMatrixLayout {
		assert rows > 0;
		assert columns > 0;
		assert noIndexesOverlap(rows, columns, rowStride, columnStride, offset);
	}

	private static boolean noIndexesOverlap(int rows, int columns, int rowStride, int columnStride, int offset) {
		int r = rowStride < 0 ? 0 : (rows - 1);
		int c = columnStride < 0 ? 0 : (columns - 1);

		boolean[] v = new boolean[offset + rowStride * r + columnStride * c + 1];

		for (r = 0; r < rows; r++) {
			for (c = 0; c < columns; c++) {
				int i = r * rowStride + c * columnStride + offset;
				if (i >= v.length || v[i]) {
					return false;
				}

				v[i] = true;
			}
		}

		return true;
	}

	@Override
	public int start() {
		return offset() + Math.min(0, rowStride()) * (rows() - 1) + Math.min(0, columnStride()) * (columns() - 1);
	}

	@Override
	public int end() {
		return offset() + Math.max(0, rowStride()) * (rows() - 1) + Math.max(0, columnStride()) * (columns() - 1) + 1;
	}

	@Override
	public int size() {
		return Math.abs(rowStride()) * (rows() - 1) + Math.abs(columnStride()) * (columns() - 1) + 1;
	}

	@Override
	public int arrayIndex(int row, int column) {
		assert row < rows;
		assert column < columns;
		assert row >= 0;
		assert column >= 0;

		return offset + row * rowStride + column * columnStride;
	}

	@Override
	public boolean isContinuous() {
		return size() == rows() * columns();
	}

	@Override
	public boolean isPacked() {
		return end() == rows() * columns();
	}

	@Override
	public boolean isRowPacked() {
		return columns() == 1 || Math.abs(columnStride()) == 1;
	}

	@Override
	public boolean isColumnPacked() {
		return rows() == 1 || Math.abs(rowStride()) == 1;
	}

	@Override
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		assert r < rows();
		assert c < columns();
		assert r >= 0;
		assert c >= 0;
		assert r + rows <= rows();
		assert c + columns <= columns();
		assert rows >= 0;
		assert columns >= 0;

		if (rows == 0 || columns == 0) {
			return MatrixLayout.super.subLayout(r, c, rows, columns);
		}

		return new BasicMatrixLayout(rows, columns, arrayIndex(r, c), rowStride(), columnStride());
	}

	@Override
	public MatrixLayout transpose() {
		return new BasicMatrixLayout(columns(), rows(), offset(), columnStride(), rowStride());
	}

	@Override
	public VecNLayout row(int r) {
		assert r < rows();
		assert r >= 0;

		return VecNLayout.of(columns(), arrayIndex(r, 0), columnStride());
	}

	@Override
	public VecNLayout column(int c) {
		assert c < columns();
		assert c >= 0;

		return VecNLayout.of(rows(), arrayIndex(0, c), rowStride());
	}
}
