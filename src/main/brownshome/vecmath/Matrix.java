package brownshome.vecmath;

/**
 * An arbitrary size matrix of double precision
 */
public class Matrix {
	private final int rows, columns;
	private final double[] m;

	public Matrix(int rows, int columns) {
		this(new double[rows * columns], rows, columns);
	}

	public Matrix(double[] m, int rows, int columns) {
		assert rows > 0 && columns > 0 && m.length == rows * columns;

		this.m = m;
		this.rows = rows;
		this.columns = columns;
	}

	private int index(int r, int c) {
		assert r < rows && c < columns;

		return c + r * columns;
	}

	/**
	 * Gets the backing array of this matrix. Writes to this array will impact the matrix
	 *
	 * @return a backing array for this matrix
	 */
	public double[] backingArray() {
		return m;
	}

	public Matrix add(Matrix other) {
		assert other.columns == columns && other.rows == rows;

		double[] n = new double[m.length];

		for (int i = 0; i < m.length; i++) {
			n[i] = m[i] + other.m[i];
		}

		return new Matrix(n, rows, columns);
	}

	public Matrix scaleAdd(double scale, Matrix other) {
		assert other.columns == columns && other.rows == rows;

		double[] n = new double[m.length];

		for (int i = 0; i < m.length; i++) {
			n[i] = m[i] + scale * other.m[i];
		}

		return new Matrix(n, rows, columns);
	}

	public Matrix multiply(Matrix other) {
		assert columns == other.rows;

		double[] n = new double[m.length];

		for (int r = 0; r < rows; r++) {
			for (int k = 0; k < columns; k++) {
				for (int c = 0; c < other.columns; c++) {
					n[index(r, c)] += m[index(r, k)] * other.m[index(k, c)];
				}
			}
		}

		return new Matrix(n, rows, columns);
	}

	public Matrix resize(int rows, int columns) {
		return new Matrix(m, rows, columns);
	}

	public Matrix transpose() {
		if (rows == 1 || columns == 1) {
			return resize(columns, rows);
		}

		double[] n = new double[m.length];

		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < columns; c++) {
				n[index(r, c)] = m[index(c, r)];
			}
		}

		return new Matrix(n, columns, rows);
	}

	public int rows() {
		return rows;
	}

	public int columns() {
		return columns;
	}
}
