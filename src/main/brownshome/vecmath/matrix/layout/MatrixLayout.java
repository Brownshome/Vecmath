package brownshome.vecmath.matrix.layout;

import brownshome.vecmath.generic.ElementLayout;
import brownshome.vecmath.matrix.basic.layout.*;
import brownshome.vecmath.vector.basic.layout.MatrixVecNLayout;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * The layout of an array-backed matrix
 */
public interface MatrixLayout extends ElementLayout {
	/**
	 * Gets an optimal layout
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a layout
	 */
	static MatrixLayout ofOptimal(int rows, int columns) {
		return ofRowMajor(rows, columns);
	}

	/**
	 * Gets a packed layout in row-major layout
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a layout
	 */
	static MatrixLayout ofRowMajor(int rows, int columns) {
		return ofRowMajor(rows, columns, 0);
	}

	/**
	 * Gets a packed layout in column-major layout
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a layout
	 */
	static MatrixLayout ofColumnMajor(int rows, int columns) {
		return ofColumnMajor(rows, columns, 0);
	}

	/**
	 * Gets a packed layout in row-major layout
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @param offset the offset
	 * @return a layout
	 */
	static MatrixLayout ofRowMajor(int rows, int columns, int offset) {
		return of(rows, columns, offset, columns, 1);
	}

	/**
	 * Gets a packed layout in column-major layout
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @param offset the offset
	 * @return a layout
	 */
	static MatrixLayout ofColumnMajor(int rows, int columns, int offset) {
		return of(rows, columns, offset, 1, rows);
	}

	/**
	 * A packed layout of a symmetric matrix
	 * @param size the size of the matrix
	 * @return a symmetric layout
	 */
	static MatrixLayout ofSymmetricRowMajor(int size) {
		return ofSymmetricRowMajor(size, 0);
	}

	/**
	 * A packed layout of a symmetric matrix
	 * @param size the size of the matrix
	 * @param offset the offset of the data in the
	 * @return a symmetric layout
	 */
	static MatrixLayout ofSymmetricRowMajor(int size, int offset) {
		return ofSymmetricRowMajor(size, offset, 0);
	}

	/**
	 * A layout of a symmetric matrix
	 * @param size the size of the matrix
	 * @param offset the offset of the data
	 * @return a symmetric layout
	 */
	static MatrixLayout ofSymmetricRowMajor(int size, int offset, int rowPadding) {
		if (size == 0) {
			return new EmptyMatrixLayout(size, size);
		}

		return new SymmetricMatrixLayout(size, offset, rowPadding);
	}

	/**
	 * Gets a layout
	 * @param rows the number of rows
	 * @param columns the nbubmer of columns
	 * @param offset the offset of (0, 0) in the backing array
	 * @param rowStride the index difference between two rows
	 * @param columnStride the index difference between two columns
	 * @return a layout
	 */
	static MatrixLayout of(int rows, int columns, int offset, int rowStride, int columnStride) {
		if (rows == 0 || columns == 0) {
			return new EmptyMatrixLayout(rows, columns);
		}

		return new BasicMatrixLayout(rows, columns, offset, rowStride, columnStride);
	}

	/**
	 * The array index of a particular item of this matrix in the backing array
	 * @param row the row
	 * @param column the column
	 * @return the array index
	 */
	int arrayIndex(int row, int column);

	/**
	 * The number of rows
	 * @return the number of rows
	 */
	int rows();

	/**
	 * The number of columns
	 * @return the number of columns
	 */
	int columns();

	/**
	 * Whether traversing items a row at a time is optimal
	 * @return a boolean
	 */
	default boolean isRowOptimal() {
		return isRowPacked();
	}

	/**
	 * Whether traversing items a columns at a time is optimal
	 * @return a boolean
	 */
	default boolean isColumnOptimal() {
		return isColumnPacked();
	}

	/**
	 * Returns true if all items in a row are adjacent
	 * @return if this layout is row-packed
	 */
	boolean isRowPacked();

	/**
	 * Returns true if all items in a column are adjacent
	 * @return if this layout is column-packed
	 */
	boolean isColumnPacked();

	/**
	 * Gets a layout representing a sub-matrix
	 * @param r the row to start the view at
	 * @param c the column to start the view at
	 * @param rows the number of rows in the view
	 * @param columns the number of columns in the view
	 * @return a layout
	 */
	default MatrixLayout subLayout(int r, int c, int rows, int columns) {
		assert r >= 0;
		assert r + rows <= rows();
		assert c >= 0;
		assert c + columns <= columns();

		if (rows == 0 || columns == 0) {
			return new EmptyMatrixLayout(rows, columns);
		}

		return new SubMatrixLayout(this, r, c, rows, columns);
	}

	/**
	 * A layout viewing the same backing array as a transposed matrix
	 * @return a layout
	 */
	default MatrixLayout transpose() {
		return new TransposedMatrixLayout(this);
	}

	/**
	 * A layout viewing the same backing array with the rows permuted
	 * @param rows the permutation to apply. Each index is the location in the new layout of the ith row
	 * @return a layout
	 */
	default MatrixLayout permuteByRow(int... rows) {
		return PermutedLayout.rows(this, rows);
	}

	/**
	 * A layout viewing the same backing array with the columns permuted
	 * @param columns the permutation to apply. Each index is the location in the new layout of the ith column
	 * @return a layout
	 */
	default MatrixLayout permuteByColumn(int... columns) {
		return PermutedLayout.columns(this, columns);
	}

	/**
	 * Returns a layout that is optimal for row-first traversal
	 * @return a layout with the same rows and columns and this one
	 */
	default MatrixLayout rowOptimal() {
		return isRowOptimal() ? this : ofRowMajor(rows(), columns());
	}

	/**
	 * Returns a layout that is optimal for column-first traversal
	 * @return a layout with the same rows and columns and this one
	 */
	default MatrixLayout columnOptimal() {
		return isColumnOptimal() ? this : ofColumnMajor(rows(), columns());
	}

	/**
	 * The layout of a row vector in the same backing array
	 * @param r the index of the row
	 * @return a vector layout
	 */
	default VecNLayout row(int r) {
		return new MatrixVecNLayout(subLayout(r, 0, 1, columns()).transpose());
	}

	/**
	 * The layout of a column vector in the same backing array
	 * @param c the index of the column
	 * @return a vector layout
	 */
	default VecNLayout column(int c) {
		return new MatrixVecNLayout(subLayout(0, c, rows(), 1));
	}
}
