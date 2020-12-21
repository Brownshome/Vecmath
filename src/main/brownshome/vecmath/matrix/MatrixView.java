package brownshome.vecmath.matrix;

import brownshome.vecmath.*;

public interface MatrixView {
	/**
	 * Creates a matrix from a 2D array. Changes to the array will write through the the returned view. Neither dimension of
	 * this new matrix may be zero.
	 * @param rows the rows of the new array
	 * @return a matrix representing the array
	 */
	static MatrixView of(double[]... rows) {
		return new ArrayMatrixView(rows);
	}

	/**
	 * Creates a matrix representing the given rows. Updates to the supplied vectors will write through to the matrix
	 * @param rows the rows of the new matrix
	 * @return a matrix holding the provided rows
	 */
	static MatrixView of(Vec2... rows) {
		assert rows != null;

		return new MatrixView() {
			@Override
			public int rows() {
				return rows.length;
			}

			@Override
			public int columns() {
				return 2;
			}

			@Override
			public double get(int row, int column) {
				return switch (column) {
					case 0 -> rows[row].x();
					case 1 -> rows[row].y();
					default -> throw new IllegalArgumentException("Column index out of range: " + column);
				};
			}
		};
	}

	/**
	 * Creates a matrix representing the given rows. Updates to the supplied vectors will write through to the matrix
	 * @param rows the rows of the new matrix
	 * @return a matrix holding the provided rows
	 */
	static MatrixView of(Vec3... rows) {
		assert rows != null;

		return new MatrixView() {
			@Override
			public int rows() {
				return rows.length;
			}

			@Override
			public int columns() {
				return 3;
			}

			@Override
			public double get(int row, int column) {
				return switch (column) {
					case 0 -> rows[row].x();
					case 1 -> rows[row].y();
					case 2 -> rows[row].z();
					default -> throw new IllegalArgumentException("Column index out of range: " + column);
				};
			}
		};
	}

	/**
	 * Creates a matrix representing the given rows. Updates to the supplied vectors will write through to the matrix
	 * @param rows the rows of the new matrix
	 * @return a matrix holding the provided rows
	 */
	static MatrixView of(Vec4... rows) {
		assert rows != null;

		return new MatrixView() {
			@Override
			public int rows() {
				return rows.length;
			}

			@Override
			public int columns() {
				return 4;
			}

			@Override
			public double get(int row, int column) {
				return switch (column) {
					case 0 -> rows[row].x();
					case 1 -> rows[row].y();
					case 2 -> rows[row].z();
					case 3 -> rows[row].w();
					default -> throw new IllegalArgumentException("Column index out of range: " + column);
				};
			}
		};
	}

	/**
	 * Joins a series of matrices such that the first row of the new matrix is made up of the combination of all the first
	 * rows of the arguments.
	 * @param matrices An array of matrices, all with the same row count R.
	 * @return an R x sum(C) matrix.
	 */
	static MatrixView concatByRows(MatrixView... matrices) {
		assert matrices.length != 0;

		int[] endColumn = new int[matrices.length];

		endColumn[0] = matrices[0].columns();

		for (int i = 1; i < matrices.length; i++) {
			endColumn[i] = endColumn[i - 1] + matrices[i].columns();
		}

		return new MatrixView() {
			@Override
			public int rows() {
				return matrices[0].rows();
			}

			@Override
			public int columns() {
				return endColumn[matrices.length - 1];
			}

			@Override
			public double get(int row, int column) {
				int m = 0;
				int startColumn = 0;

				while (endColumn[m] <= column) {
					startColumn = endColumn[m];
					m++;
				}

				return matrices[m].get(row, column - startColumn);
			}
		};
	}

	/**
	 * Joins a series of matrices such that the first column of the new matrix is made up of the combination of all the first
	 * columns of the arguments.
	 * @param matrices An array of matrices, all with the same column count C.
	 * @return a sum(R) x C matrix.
	 */
	static MatrixView concatByColumns(MatrixView... matrices) {
		assert matrices.length != 0;

		int[] endRow = new int[matrices.length];
		endRow[0] = matrices[0].rows();

		for (int i = 1; i < matrices.length; i++) {
			endRow[i] = endRow[i - 1] + matrices[i].rows();
		}

		return new MatrixView() {
			@Override
			public int rows() {
				return endRow[matrices.length - 1];
			}

			@Override
			public int columns() {
				return matrices[0].columns();
			}

			@Override
			public double get(int row, int column) {
				int m = 0;
				int startRow = 0;

				while (endRow[m] <= row) {
					startRow = endRow[m];
					m++;
				}

				return matrices[m].get(row - startRow, column);
			}
		};
	}

	/**
	 * Creates an identity matrix, this matrix uses O(size) space, so is efficient for large identity matrices
	 * @param size the number of rows and columns in this matrix
	 * @return a size x size matrix with ones on the diagonal
	 */
	static MatrixView identity(int size) {
		return new MatrixViewWithFastMultiply() {
			@Override
			public Matrix leftMultiply(MatrixView other) {
				return other.copy();
			}

			@Override
			public Matrix multiply(MatrixView other) {
				return other.copy();
			}

			@Override
			public int rows() {
				return size;
			}

			@Override
			public int columns() {
				return size;
			}

			@Override
			public double get(int row, int column) {
				return row == column ? 1.0 : 0.0;
			}

			@Override
			public MatrixView transpose() {
				return this;
			}

			@Override
			public MatrixView permuteRows(int... rows) {
				if (rows == null) {
					return this;
				}

				return new PermutationMatrix(rows);
			}

			@Override
			public MatrixView permuteColumns(int... columns) {
				if (columns == null) {
					return this;
				}

				return new PermutationMatrix(columns).transpose();
			}
		};
	}

	/**
	 * Creates a matrix with a given value on the diagonal
	 * @param diagonal the value on the diagonal
	 * @param size the number of rows and columns
	 * @return a size x size diagonal matrix
	 */
	static MatrixView diagonal(double diagonal, int size) {
		assert size >= 0;

		return new MatrixViewWithFastMultiply() {
			@Override
			public int rows() {
				return size;
			}

			@Override
			public int columns() {
				return size;
			}

			@Override
			public double get(int row, int column) {
				return row == column ? diagonal : 0.0;
			}

			@Override
			public MatrixView transpose() {
				return this;
			}

			@Override
			public Matrix multiply(MatrixView other) {
				assert size == other.rows();

				Matrix result = other.copy();

				for (int i = 0; i < result.backingArray().length; i++) {
					result.backingArray()[i] *= diagonal;
				}

				return result;
			}

			@Override
			public Matrix leftMultiply(MatrixView other) {
				return multiply(other);
			}
		};
	}

	static MatrixView zeros(int rows, int columns) {
		assert rows >= 0 && columns >= 0;

		return new MatrixViewWithFastMultiply() {
			@Override
			public Matrix leftMultiply(MatrixView other) {
				return zeros(other.rows(), other.columns()).asMatrix();
			}

			@Override
			public Matrix multiply(MatrixView other) {
				return zeros(other.rows(), other.columns()).asMatrix();
			}

			@Override
			public int rows() {
				return rows;
			}

			@Override
			public int columns() {
				return columns;
			}

			@Override
			public double get(int row, int column) {
				return 0.0;
			}

			@Override
			public MatrixView transpose() {
				return this;
			}

			@Override
			public MatrixView permuteRows(int... rows) {
				return this;
			}

			@Override
			public MatrixView permuteColumns(int... columns) {
				return this;
			}
		};
	}

	static MatrixView constant(double value, int rows, int columns) {
		assert rows >= 0 && columns >= 0;

		return new MatrixView() {
			@Override
			public int rows() {
				return rows;
			}

			@Override
			public int columns() {
				return columns;
			}

			@Override
			public double get(int row, int column) {
				return value;
			}

			@Override
			public MatrixView transpose() {
				return this;
			}

			@Override
			public MatrixView permuteRows(int... rows) {
				return this;
			}

			@Override
			public MatrixView permuteColumns(int... columns) {
				return this;
			}
		};
	}

	/**
	 * Creates a matrix that represents the cross product of vec.
	 *
	 * cross(vec) * a = vec x a
	 *
	 * @param vec the vector to cross using
	 * @return a matrix such that Va = v x a
	 */
	static MatrixView cross(Vec3 vec) {
		return new MatrixView() {
			@Override
			public int rows() {
				return 3;
			}

			@Override
			public int columns() {
				return 3;
			}

			@Override
			public double get(int row, int column) {
				if (row == column) {
					return 0.0;
				}

				double result = switch (row + column) {
					case 1 -> -vec.z();
					case 2 -> vec.y();
					case 3 -> -vec.x();
					default -> 0.0;
				};

				return column > row ? result : -result;
			}
		};
	}

	/**
	 * The number of rows in this matrix
	 *
	 * @return the number of rows
	 */
	int rows();

	/**
	 * The number of columns in this matrix
	 *
	 * @return the number of columns
	 */
	int columns();

	/**
	 * Gets an element of this matrix
	 * @param row the row to query
	 * @param column the column to query
	 * @return the element of this matrix
	 */
	double get(int row, int column);

	/**
	 * Computes this * other
	 *
	 * @param other the right hand side of the multiplication
	 * @return this * other
	 */
	default Matrix multiply(MatrixView other) {
		assert columns() == other.rows();

		if (other instanceof MatrixViewWithFastMultiply) {
			return ((MatrixViewWithFastMultiply) other).leftMultiply(this);
		}

		double[] n = new double[rows() * other.columns()];

		for (int r = 0; r < rows(); r++) {
			for (int k = 0; k < columns(); k++) {
				for (int c = 0; c < other.columns(); c++) {
					n[r * other.columns() + c] += get(r, k) * other.get(k, c);
				}
			}
		}

		return Matrix.of(n, rows(), other.columns());
	}

	/**
	 * Returns this matrix as a {@link Matrix} if it is one, or a copy if it is not
	 * @return A matrix object that may or may not be the same as this.
	 */
	default Matrix asMatrix() {
		return copy();
	}

	/**
	 * Copies this matrix, the new copy will be completely disjoint from this matrix, and is guaranteed to be in row-major
	 * format with no offset.
	 * @return A copy of this matrix
	 */
	default Matrix copy() {
		double[] matrix = new double[rows() * columns()];
		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			matrix[r * columns() + c] = get(r, c);
		}

		return Matrix.of(matrix, 0, columns(), 1, rows(), columns());
	}

	/**
	 * Transposes this matrix. The matrix is a view of the other one, and as such will reflect changes in the source
	 * matrix.
	 * @return A transposed view of this matrix
	 */
	default MatrixView transpose() {
		return new TransposedMatrixView(this);
	}

	default MatrixView permuteRows(int... rows) {
		return PermutedMatrixView.rows(this, rows);
	}

	default MatrixView permuteColumns(int... columns) {
		return PermutedMatrixView.columns(this, columns);
	}

	default Factorisation factorise() {
		return new LUFactorisation(copy(), 1e-10);
	}

	default MatrixView invert() {
		return factorise().inverse();
	}

	/**
	 * Computes other \ this
	 * @param other the matrix to divide by
	 * @return other \ this
	 */
	default MatrixView leftDivide(MatrixView other) {
		return other.factorise().leftSolve(this);
	}

	/**
	 * Computes this / other
	 * @param other the matrix to divide by
	 * @return this / other
	 */
	default MatrixView rightDivide(MatrixView other) {
		return other.factorise().rightSolve(this);
	}

	/**
	 * Computes the determinant of this matrix
	 * @return the determinant
	 */
	default double determinant() {
		return factorise().determinant();
	}
}
