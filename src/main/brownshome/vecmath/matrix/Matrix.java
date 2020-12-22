package brownshome.vecmath.matrix;

import java.util.Arrays;

/**
 * An arbitrary size matrix of double precision
 */
public final class Matrix implements MatrixView {
	private final int rows, columns;

	private final int offset, rowStride, columnStride;
	private final double[] m;

	Matrix(double[] m, int rows, int columns, int offset, int rowStride, int columnStride) {
		assert layoutIsValid(m, rows, columns, offset, rowStride, columnStride);

		this.m = m;
		this.rows = rows;
		this.columns = columns;
		this.offset = offset;
		this.rowStride = rowStride;
		this.columnStride = columnStride;
	}

	private static boolean layoutIsValid(double[] m, int rows, int columns, int offset, int rowStride, int columnStride) {
		if (rows < 0 || columns < 0) {
			return false;
		}

		if (rows == 0 || columns == 0) {
			return true;
		}

		var predicate = new Object() {
			boolean check(int row, int column) {
				int index = offset + row * rowStride + column * columnStride;
				return index >= 0 && index < m.length;
			}
		};

		return m != null
				&& predicate.check(0, 0)
				&& predicate.check(rows - 1, 0)
				&& predicate.check(0, columns - 1)
				&& predicate.check(rows - 1, columns - 1);
	}

	/**
	 * Creates a matrix from an input array, note that the input array is not copied and will be the backing array for the
	 * created matrix. The input array is expected to be in row-major order.
	 * @param matrix the backing array
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a new matrix backed by the given array
	 */
	public static Matrix of(double[] matrix, int rows, int columns) {
		return of(matrix, 0, columns, 1, rows, columns);
	}

	/**
	 * Creates a matrix from an input array, note that the input array is not copied and will be the backing array for the
	 * created matrix
	 * @param matrix the backing array
	 * @param offset the index of the first item in the array
	 * @param columnStride the index difference between columns
	 * @param rowStride the index difference between rows
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a new matrix backed by the given array
	 */
	public static Matrix of(double[] matrix, int offset, int rowStride, int columnStride, int rows, int columns) {
		return new Matrix(matrix, rows, columns, offset, rowStride, columnStride);
	}

	public static Matrix identity(int size) {
		return MatrixView.identity(size).asMatrix();
	}

	public static Matrix zeros(int rows, int columns) {
		return of(new double[rows * columns], rows, columns);
	}

	public static Matrix constant(double value, int rows, int columns) {
		double[] array = new double[rows * columns];
		Arrays.fill(array, value);
		return of(array, rows, columns);
	}

	public static Matrix diagonal(double value, int size) {
		return MatrixView.diagonal(value, size).asMatrix();
	}

	private int index(int r, int c) {
		assert r < rows && c < columns;

		return offset + c * columnStride + r * rowStride;
	}

	@Override
	public double get(int row, int column) {
		return m[index(row, column)];
	}

	@Override
	public int rows() {
		return rows;
	}

	@Override
	public int columns() {
		return columns;
	}

	/**
	 * The index of the (0, 0) in the array
	 * @return the index
	 */
	public int offset() {
		return offset;
	}

	/**
	 * The difference between any two positions that differ by one row
	 * @return index(1, 0) - index(0, 0)
	 */
	public int rowStride() {
		return rowStride;
	}

	/**
	 * The difference between any two positions that differ by one column
	 * @return index(0, 1) - index(0, 0)
	 */
	public int columnStride() {
		return columnStride;
	}

	/**
	 * Gets the backing array of this matrix. Writes to this array will impact the matrix
	 *
	 * @return a backing array for this matrix
	 */
	public double[] backingArray() {
		return m;
	}

	private boolean layoutIsContinuous() {
		return rowStride == columns && columnStride == 1
				|| rowStride == 1 && columnStride == rows
				|| rows == 0
				|| columns == 0;
	}

	public Matrix subMatrix(int r, int c, int rows, int columns) {
		assert rows >= 0 && r >= 0 && r + rows <= rows();
		assert columns >= 0 && c >= 0 && c + columns <= columns();

		return of(m, index(r, c), rowStride, columnStride, rows, columns);
	}

	/**
	 * Adds a matrix to this object, mutating this matrix
	 * @param other the matrix to add
	 */
	public void add(MatrixView other) {
		assert other.columns() == columns && other.rows() == rows;

		if (other instanceof Matrix) {
			add((Matrix) other);
		} else {
			slowAddPath(other);
		}
	}

	/**
	 * Adds a matrix to this object, mutating this matrix
	 * @param other the matrix to add
	 */
	public void add(Matrix other) {
		if (other.rowStride == rowStride && other.columnStride == columnStride && layoutIsContinuous()) {
			for (int i = 0; i < rows * columns; i++) {
				m[offset + i] += other.m[other.offset + i];
			}
		} else {
			slowAddPath(other);
		}
	}

	private void slowAddPath(MatrixView other) {
		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			m[index(r, c)] += other.get(r, c);
		}
	}

	/**
	 * Adds scale * matrix to this object, mutating this matrix
	 * @param scale the scale to use
	 * @param other the matrix to add
	 */
	public void scaleAdd(double scale, MatrixView other) {
		assert other.columns() == columns && other.rows() == rows;

		if (other instanceof Matrix) {
			scaleAdd(scale, (Matrix) other);
		} else {
			slowScaleAddPath(scale, other);
		}
	}

	/**
	 * Adds a matrix to this object, mutating this matrix
	 * @param scale the scale to use
	 * @param other the matrix to add
	 */
	public void scaleAdd(double scale, Matrix other) {
		if (other.rowStride == rowStride && other.columnStride == columnStride && layoutIsContinuous()) {
			for (int i = 0; i < rows * columns; i++) {
				m[offset + i] += scale * other.m[other.offset + i];
			}
		} else {
			slowScaleAddPath(scale, other);
		}
	}

	private void slowScaleAddPath(double scale, MatrixView other) {
		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			m[index(r, c)] += scale * other.get(r, c);
		}
	}

	public void set(double value, int row, int column) {
		m[index(row, column)] = value;
	}

	/**
	 * Sets this matrix to equal another one
	 * @param other the matrix to read
	 */
	public void set(MatrixView other) {
		assert other.columns() == columns && other.rows() == rows;

		if (other instanceof Matrix) {
			set((Matrix) other);
		} else {
			slowPathSet(other);
		}
	}

	/**
	 * Sets this matrix to equal another one
	 * @param other the matrix to read
	 */
	public void set(Matrix other) {
		if (other.rowStride == rowStride && other.columnStride == columnStride && layoutIsContinuous()) {
			System.arraycopy(other.m, other.offset, m, offset, rows * columns);
		} else {
			slowPathSet(other);
		}
	}

	private void slowPathSet(MatrixView other) {
		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			m[index(r, c)] = other.get(r, c);
		}
	}

	@Override
	public Matrix transpose() {
		return of(m, offset, columnStride, rowStride, columns, rows);
	}

	@Override
	public Matrix asMatrix() {
		return this;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder("Matrix [\n");

		for (int r = 0; r < rows(); r++) {
			for (int c = 0; c < columns(); c++) {
				output.append(String.format("\t%+.2f", get(r, c)));
			}

			output.append('\n');
		}

		output.append(']');

		return output.toString();
	}
}
