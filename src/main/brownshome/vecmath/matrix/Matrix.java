package brownshome.vecmath.matrix;

import java.util.Arrays;

/**
 * An arbitrary size matrix of double precision
 */
public final class Matrix implements MatrixView {
	private final MatrixLayout layout;
	private final double[] m;

	private Matrix(double[] m, MatrixLayout layout) {
		assert m.length >= layout.requiredArrayLength();

		this.m = m;
		this.layout = layout;
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
		return new Matrix(matrix, new MatrixLayout(rows, columns, rowStride, columnStride, offset));
	}

	public static Matrix of(double[] matrix, MatrixLayout layout) {
		return new Matrix(matrix, layout);
	}

	public static Matrix identity(int size) {
		return MatrixView.identity(size).asMatrix();
	}

	/**
	 * Creates an identity matrix with the given layout
	 * @param layout the layout, this must be a square layout
	 * @return an identity matrix
	 */
	public static Matrix identity(MatrixLayout layout) {
		return MatrixView.identity(layout.rows()).asMatrix(layout);
	}

	public static Matrix zeros(int rows, int columns) {
		return of(new double[rows * columns], rows, columns);
	}

	public static Matrix zeros(MatrixLayout layout) {
		return of(new double[layout.requiredArrayLength()], layout);
	}

	public static Matrix constant(double value, int rows, int columns) {
		double[] array = new double[rows * columns];
		Arrays.fill(array, value);
		return of(array, rows, columns);
	}

	public static Matrix constant(double value, MatrixLayout layout) {
		double[] array = new double[layout.requiredArrayLength()];
		Arrays.fill(array, layout.offset(), array.length, value);
		return of(array, layout);
	}

	public static Matrix diagonal(double value, int size) {
		return MatrixView.diagonal(value, size).asMatrix();
	}

	/**
	 * Creates a diagonal matrix with the given layout
	 * @param layout the layout, this must be a square layout
	 * @param value the value on the diagonal
	 * @return an diagonal matrix
	 */
	public static Matrix diagonal(double value, MatrixLayout layout) {
		return MatrixView.diagonal(value, layout.rows()).asMatrix(layout);
	}

	/**
	 * Returns the index in the backing array of the given row / column
	 * @param r the row
	 * @param c the column
	 * @return an index into the array
	 */
	public int index(int r, int c) {
		return layout.index(r, c);
	}

	@Override
	public double get(int row, int column) {
		return m[index(row, column)];
	}

	public MatrixLayout layout() {
		return layout;
	}

	@Override
	public int rows() {
		return layout.rows();
	}

	@Override
	public int columns() {
		return layout.columns();
	}

	/**
	 * The index of the (0, 0) in the array
	 * @return the index
	 */
	public int offset() {
		return layout.offset();
	}

	/**
	 * The difference between any two positions that differ by one row
	 * @return index(1, 0) - index(0, 0)
	 */
	public int rowStride() {
		return layout.rowStride();
	}

	/**
	 * The difference between any two positions that differ by one column
	 * @return index(0, 1) - index(0, 0)
	 */
	public int columnStride() {
		return layout.columnStride();
	}

	/**
	 * Gets the backing array of this matrix. Writes to this array will impact the matrix
	 *
	 * @return a backing array for this matrix
	 */
	public double[] backingArray() {
		return m;
	}

	public Matrix subMatrix(int r, int c, int rows, int columns) {
		return of(m, layout.subLayout(r, c, rows, columns));
	}

	/**
	 * Adds a matrix to this object, mutating this matrix
	 * @param other the matrix to add
	 */
	public void add(MatrixView other) {
		assert other.columns() == columns() && other.rows() == rows();

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
		if (other.rowStride() == rowStride() && other.columnStride() == columnStride() && layout.isPacked()) {
			for (int i = 0; i < rows() * columns(); i++) {
				m[offset() + i] += other.m[other.offset() + i];
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
		assert other.columns() == columns() && other.rows() == rows();

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
		if (other.rowStride() == rowStride() && other.columnStride() == columnStride() && layout.isPacked()) {
			for (int i = 0; i < rows() * columns(); i++) {
				m[offset() + i] += scale * other.m[other.offset() + i];
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

	/**
	 * Sets this matrix to be <code>this * scale</code>, mutating this matrix
	 * @param scale the scale to use
	 */
	public void scale(double scale) {
		if (layout.isPacked()) {
			for (int i = 0; i < rows() * columns(); i++) {
				m[offset() + i] *= scale;
			}
		} else {
			for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
				m[index(r, c)] *= scale;
			}
		}
	}

	/**
	 * Sets this matrix to be <code>other .* scale</code>, mutating this matrix
	 * @param scale the scale to use
	 */
	public void scale(MatrixView scale) {
		assert scale.columns() == columns() && scale.rows() == rows();

		if (scale instanceof Matrix matrix) {
			scale(matrix);
		} else {
			slowScalePath(scale);
		}
	}

	/**
	 * Sets this matrix to be <code>other .* scale</code>, mutating this matrix
	 * @param scale the scale to use
	 */
	public void scale(Matrix scale) {
		if (scale.rowStride() == rowStride() && scale.columnStride() == columnStride() && layout.isPacked()) {
			for (int i = 0; i < rows() * columns(); i++) {
				m[offset() + i] *= scale.m[scale.offset() + i];
			}
		} else {
			slowScalePath(scale);
		}
	}

	private void slowScalePath(MatrixView scale) {
		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			m[index(r, c)] *= scale.get(r, c);
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
		assert other.columns() == columns() && other.rows() == rows();

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
		if (other.rowStride() == rowStride() && other.columnStride() == columnStride() && layout.isPacked()) {
			System.arraycopy(other.m, other.offset(), m, offset(), rows() * columns());
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
		return of(m, layout.transpose());
	}

	@Override
	public Matrix asMatrix() {
		return this;
	}

	@Override
	public SymmetricMatrixView asSymmetricMatrix() {
		return SymmetricMatrix.of(this);
	}

	@Override
	public String toString() {
		return MatrixView.toString(this);
	}
}
