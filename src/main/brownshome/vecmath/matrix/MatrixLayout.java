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
 * @param length the length of the region in the underlying backing array that must be valid. This is used to specify the
 *               minimum length of array that will be accepted by constructors and dictates the size of the created matrix
 *               in {@link MatrixView#copy(MatrixLayout)}
 */
public record MatrixLayout(int rows, int columns, int rowStride, int columnStride, int offset, int length) {
	public MatrixLayout {
		assert rows >= 0;
		assert columns >= 0;
		assert length >= 0;
		assert noIndexesOverlap();
	}

	private boolean noIndexesOverlap() {
		boolean[] v = new boolean[length];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				int i = index(r, c);
				if (i >= length || v[i]) {
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
		this(rows, columns, rowStride, columnStride, 0, (rows - 1) * rowStride + (columns - 1) * columnStride + 1);
	}

	/**
	 * Create a layout with a given row and column stride and offset. The length will be the minimum valid length
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @param rowStride the row stride
	 * @param columnStride the column stride
	 * @param offset the offset
	 */
	public MatrixLayout(int rows, int columns, int rowStride, int columnStride, int offset) {
		this(rows, columns, rowStride, columnStride, 0, (rows - 1) * rowStride + (columns - 1) * columnStride + 1);
	}

	/**
	 * Create a row-packed layout. This is the same as <code>new MatrixLayout(rows, columns, columns, 1)</code>.
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a row-major matrix layout
	 * @see MatrixLayout#isRowPacked()
	 */
	public static MatrixLayout rowMajor(int rows, int columns) {
		return new MatrixLayout(rows, columns, columns, 1, 0, rows * columns);
	}

	/**
	 * Create a column-packed layout. This is the same as <code>new MatrixLayout(rows, columns, 1, rows)</code>
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a column-major matrix layout
	 * @see MatrixLayout#isColumnPacked()
	 */
	public static MatrixLayout columnMajor(int rows, int columns) {
		return new MatrixLayout(rows, columns, 1, rows, 0, rows * columns);
	}

	/**
	 * Creates a matrix layout that is optimal for this architecture
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return an optimal layout
	 * @see MatrixLayout#isOptimal()
	 */
	public static MatrixLayout optimal(int rows, int columns) {
		int rowStride = VectorisationUtil.roundUpToVectorLength(columns);
		return new MatrixLayout(rows, columns, VectorisationUtil.roundUpToVectorLength(columns), 1, 0, rowStride * rows);
	}

	/**
	 * Computes the index into the backing array
	 * @param row the row
	 * @param column the column
	 * @return the index at which that element can be found
	 */
	public int index(int row, int column) {
		return offset + row * rowStride + column * columnStride;
	}

	/**
	 * Transposes this layout. <em>This may not preserve optimality</em>
	 * @return a layout that views the same array as its transpose
	 */
	public MatrixLayout transpose() {
		return new MatrixLayout(columns, rows, columnStride, rowStride, offset, length);
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
		return new MatrixLayout(rows, columns, rowStride, columnStride, offset, length);
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
		return isRowPacked()
				&& rowStride >= VectorisationUtil.roundUpToVectorLength(columns)
				&& length >= rowStride * (rows - 1) + VectorisationUtil.roundUpToVectorLength(columns);
	}
}
