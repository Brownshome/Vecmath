package brownshome.vecmath.matrix;

/**
 * The layout of a matrix
 * @param rows the number of rows in this matrix
 * @param columns the number of columns in this matrix
 * @param rowStride the difference in indices that results from increasing the row index.
 *                  This may be negative (or zero if there are no rows)
 * @param columnStride the difference in indices that results from increasing the column index.
 *                     This may be negative (or zero if there are no columns)
 * @param offset the offset from the start of the array that this matrix shape will use
 */
public record MatrixLayout(int rows, int columns, int rowStride, int columnStride, int offset) {
	public MatrixLayout {
		assert rows >= 0;
		assert columns >= 0;
		assert noIndexesOverlap(rows, columns, rowStride, columnStride, offset);
	}

	private static boolean noIndexesOverlap(int rows, int columns, int rowStride, int columnStride, int offset) {
		if (rows == 0 || columns == 0) {
			return true;
		}

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

	/**
	 * Create a layout with a given row and column stride. The offset shall be zero and the length will be the minimum valid
	 * length
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @param rowStride the row stride
	 * @param columnStride the column stride
	 */
	public MatrixLayout(int rows, int columns, int rowStride, int columnStride) {
		this(rows, columns, rowStride, columnStride, 0);
	}

	/**
	 * Create a row-packed layout. This is the same as <code>new MatrixLayout(rows, columns, columns, 1)</code>.
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a row-major matrix layout
	 * @see MatrixLayout#isRowPacked()
	 */
	public static MatrixLayout rowMajor(int rows, int columns) {
		return new MatrixLayout(rows, columns, columns, 1, 0);
	}

	/**
	 * Create a column-packed layout. This is the same as <code>new MatrixLayout(rows, columns, 1, rows)</code>
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a column-major matrix layout
	 * @see MatrixLayout#isColumnPacked()
	 */
	public static MatrixLayout columnMajor(int rows, int columns) {
		return new MatrixLayout(rows, columns, 1, rows, 0);
	}

	/**
	 * Creates a matrix layout that is optimal for this architecture
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return an optimal layout
	 * @see MatrixLayout#isOptimal()
	 */
	public static MatrixLayout optimal(int rows, int columns) {
		return rowMajor(rows, columns);
	}

	/**
	 * Computes the index into the backing array
	 * @param row the row
	 * @param column the column
	 * @return the index at which that element can be found
	 */
	public int index(int row, int column) {
		assert row < rows;
		assert column < columns;
		assert row >= 0;
		assert column >= 0;

		return offset + row * rowStride + column * columnStride;
	}

	/**
	 * Transposes this layout. <em>This may not preserve optimality</em>
	 * @return a layout that views the same array as its transpose
	 */
	public MatrixLayout transpose() {
		return new MatrixLayout(columns, rows, columnStride, rowStride, offset);
	}

	/**
	 * Converts this layout to a row-major one
	 * @return a row-packed layout with the same number of rows and columns as this one
	 */
	public MatrixLayout rowMajor() {
		return isRowPacked() ? this : rowMajor(rows, columns);
	}

	/**
	 * Converts this layout to a column-major one
	 * @return a column-packed layout with the same number of rows and columns as this one
	 */
	public MatrixLayout columnMajor() {
		return isColumnPacked() ? this : columnMajor(rows, columns);
	}

	/**
	 * Converts this layout to an optimal one
	 * @return an optimal layout with the same number of rows and columns
	 */
	public MatrixLayout optimal() {
		return isOptimal() ? this : optimal(rows, columns);
	}

	/**
	 * Creates a layout with a set offset from this one
	 * @param offset the index offset
	 * @return the same layout as this but with a given offset
	 */
	public MatrixLayout offset(int offset) {
		return new MatrixLayout(rows, columns, rowStride, columnStride, offset);
	}

	/**
	 * Returns true if this layout has a {@link #columnStride()} of 1
	 * @return is this layout row-packed
	 */
	public boolean isRowPacked() {
		return columnStride == 1;
	}

	/**
	 * Returns true if this layout has a {@link #rowStride()} of 1
	 * @return is this layout column-packed
	 */
	public boolean isColumnPacked() {
		return rowStride == 1;
	}

	/**
	 * Returns true if all items in this matrix are organised into one continuous block.
	 * @return is this layout packed
	 * @see #isColumnPacked()
	 * @see #isRowPacked()
	 */
	public boolean isPacked() {
		return rowStride == 1 && columnStride == rows || columnStride == 1 && rowStride == columns;
	}

	/**
	 * Returns true if this layout is optimal
	 * @return is this layout optimal
	 */
	public boolean isOptimal() {
		return isRowPacked();
	}

	/**
	 * Creates the layout of a sub-matrix
	 * @param r the row of this layout to start the new layout at
	 * @param c the column of this layout to start the new layout at
	 * @param rows the number of rows in the sub-matrix
	 * @param columns the number of columns in the sub-matrix
	 * @return a sub-layout
	 */
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		assert rows >= 0 && r >= 0 && r + rows <= rows();
		assert columns >= 0 && c >= 0 && c + columns <= columns();

		return new MatrixLayout(rows, columns, rowStride, columnStride, index(r, c));
	}

	/**
	 * Returns the length of array required to hold this matrix layout
	 * @return the minimum array length
	 */
	public int requiredArrayLength() {
		if (rows == 0 || columns == 0) {
			return 0;
		}

		int r = rowStride < 0 ? 0 : (rows - 1);
		int c = columnStride < 0 ? 0 : (columns - 1);

		return index(r, c) + 1;
	}
}
