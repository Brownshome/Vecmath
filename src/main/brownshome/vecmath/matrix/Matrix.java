package brownshome.vecmath.matrix;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.basic.*;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.LowerUpperFactorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.basic.MatrixVecN;

/**
 * A matrix
 */
public interface Matrix extends GenericElement<Matrix> {
	/**
	 * A matrix with a single value
	 * @param value the value
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a matrix
	 */
	static Matrix constant(double value, int rows, int columns) {
		return new ConstantMatrix(value, rows, columns);
	}

	/**
	 * A zero matrix
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a matrix
	 */
	static Matrix zero(int rows, int columns) {
		return new ZeroMatrix(rows, columns);
	}

	/**
	 * A matrix with a single known value on the leading diagonal
	 * @param value the value
	 * @param size the number of rows and columns
	 * @return a matrix
	 */
	static Matrix diagonal(double value, int size) {
		return new DiagonalMatrix(value, size);
	}

	/**
	 * The identity matrix
	 * @param size the number of rows and columns
	 * @return a matrix
	 */
	static Matrix identity(int size) {
		return new IdentityMatrix(size);
	}

	/**
	 * A mutable matrix with a given number of rows and columns
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a mutable matrix
	 */
	static MMatrix of(int rows, int columns) {
		return of(MatrixLayout.ofOptimal(rows, columns));
	}

	/**
	 * A mutable column-matrix with the given number of rows
	 * @param rows the number of rows
	 * @return a mutable matrix
	 */
	static MMatrix ofColumn(int rows) {
		return of(rows, 1);
	}

	/**
	 * A mutable column-matrix with the given rows
	 * @param rows the rows of the matrix
	 * @return a mutable matrix
	 */
	static MMatrix ofColumn(double... rows) {
		return new BasicMatrix(rows, MatrixLayout.ofOptimal(rows.length, 1));
	}

	/**
	 * A mutable matrix with the given columns
	 * @param columns the columns of the matrix
	 * @return a mutable matrix
	 */
	static MMatrix ofColumns(double[]... columns) {
		return new NestedArrayMatrix(columns).transpose();
	}


	/**
	 * A mutable row-matrix with the given number of columns
	 * @param columns the number of columns
	 * @return a mutable matrix
	 */
	static MMatrix ofRow(int columns) {
		return of(1, columns);
	}

	/**
	 * A mutable row-matrix with the given columns
	 * @param columns the columns of the matrix
	 * @return a mutable matrix
	 */
	static MMatrix ofRow(double... columns) {
		return new BasicMatrix(columns, MatrixLayout.ofOptimal(1, columns.length));
	}

	/**
	 * A mutable matrix with the given rows
	 * @param rows the rows of the matrix
	 * @return a mutable matrix
	 */
	static MMatrix ofRows(double[]... rows) {
		return new NestedArrayMatrix(rows);
	}

	/**
	 * An array-backed matrix with the given layout
	 * @param layout the layout
	 * @return an array-backed matrix
	 */
	static ArrayMatrix of(MatrixLayout layout) {
		return new BasicMatrix(new double[layout.end()], layout);
	}

	/**
	 * An array-backed matrix with the given layout
	 * @param matrix the backing array for this matrix
	 * @param layout the layout of the matrix
	 * @return an array-backed matrix
	 */
	static ArrayMatrix of(double[] matrix, MatrixLayout layout) {
		return new BasicMatrix(matrix, layout);
	}

	/**
	 * Creates a symmetric matrix with the given rows and columns. Setting an item in this matrix will also set the symmetric item
	 * @param size the size of this matrix
	 * @return a symmetric modifiable matrix
	 */
	static MMatrix ofSymmetric(int size) {
		return ofSymmetric(MatrixLayout.ofSymmetricRowMajor(size));
	}

	/**
	 * Creates a symmetric matrix with the given matrix layout
	 * @param layout the layout of this matrix
	 * @return a symmetric array-backed matrix
	 */
	static ArrayMatrix ofSymmetric(MatrixLayout layout) {
		assert layout.rows() == layout.columns();
		return ofSymmetric(new double[layout.end()], layout);
	}

	/**
	 * Creates a symmetric matrix with the given matrix layout
	 * @param matrix the backing array for this matrix
	 * @param layout the layout of this matrix
	 * @return a symmetric array-backed matrix
	 */
	static ArrayMatrix ofSymmetric(double[] matrix, MatrixLayout layout) {
		return new BasicSymmetricMatrix(matrix, layout);
	}

	/**
	 * The number of rows in this matrix
	 * @return the number of rows
	 */
	int rows();

	/**
	 * The number of columns in this matrix
	 * @return the number of columns
	 */
	int columns();

	/**
	 * If iterating along a row of this matrix is cache-friendly
	 * @return a boolean
	 */
	default boolean isRowOptimal() {
		return columns() < 2;
	}

	/**
	 * If iterating along a column of this matrix is cache-friendly
	 * @return a boolean
	 */
	default boolean isColumnOptimal() {
		return rows() < 2;
	}

	/**
	 * Gets a value from this matrix
	 * @param row the row
	 * @param column the column
	 * @return the value
	 */
	double get(int row, int column);

	/**
	 * Creates a sub-matrix view of this matrix
	 * @param r the starting row
	 * @param c the starting column
	 * @param rows the number of rows
	 * @param columns the number of columns
	 * @return a matrix
	 */
	default Matrix subMatrix(int r, int c, int rows, int columns) {
		return new SubMatrix(this, r, c, rows, columns);
	}

	/**
	 * Gets a row of this matrix as an unknown-size vector
	 * @param r the row index
	 * @return a vector of unknown size
	 */
	default VecN row(int r) {
		return transpose().column(r);
	}

	/**
	 * Gets a column of this matrix as an unknown-size vector
	 * @param c the column index
	 * @return a vector of unknown size
	 */
	default VecN column(int c) {
		return new MatrixVecN(this, c);
	}

	/**
	 * Gets the transposed view of this matrix
	 * @return a matrix
	 */
	default Matrix transpose() {
		return new TransposedMatrix(this);
	}

	/**
	 * Gets the invert matrix
	 * @return a matrix
	 * @throws brownshome.vecmath.matrix.factorisation.SingularMatrixException if this matrix is not invertible
	 */
	default Matrix invert() {
		return factorisation().inverse();
	}

	/**
	 * Gets a permuted view of this matrix
	 * @param rows the permutation to apply. Each index is the location in the new matrix of the ith row
	 * @return a matrix
	 */
	default Matrix permuteByRow(int... rows) {
		return PermutedMatrix.rows(this, rows);
	}

	/**
	 * Gets a permuted view of this matrix
	 * @param columns the permutation to apply. Each index is the location in the new matrix of the ith column
	 * @return a matrix
	 */
	default Matrix permuteByColumn(int... columns) {
		return PermutedMatrix.columns(this, columns);
	}

	/**
	 * The factorisation of this matrix
	 * @return a factorisation
	 */
	default Factorisation factorisation() {
		return factorisation(1e-10);
	}

	/**
	 * The factorisation of this matrix
	 * @param tolerance the tolerance to use
	 * @return a factorisation
	 */
	default Factorisation factorisation(double tolerance) {
		return new LowerUpperFactorisation(arrayBackedCopy(), tolerance);
	}

	/**
	 * Gets the result of multiplying this matrix by the other one
	 * @param other the other matrix
	 * @return a matrix
	 */
	default Matrix multiply(Matrix other) {
		assert columns() == other.rows();

		if (other instanceof MatrixWithFastMultiply fast) {
			return fast.multiplyLeft(this);
		}

		// Attempt to find the most cache-friendly result
		MMatrix result;
		if (other.isRowOptimal()) {
			result = Matrix.of(MatrixLayout.ofRowMajor(rows(), other.columns()));
			for (int r = 0; r < rows(); r++) for (int k = 0; k < columns(); k++) for (int c = 0; c < other.columns(); c++) {
				var value = result.get(r, c);
				value += get(r, k) * other.get(k, c);
				result.set(value, r, c);
			}
		} else if (isColumnOptimal()) {
			result = Matrix.of(MatrixLayout.ofColumnMajor(rows(), other.columns()));
			for (int c = 0; c < other.columns(); c++) for (int k = 0; k < columns(); k++) for (int r = 0; r < rows(); r++) {
				var value = result.get(r, c);
				value += get(r, k) * other.get(k, c);
				result.set(value, r, c);
			}
		} else if (other.isColumnOptimal() && isRowOptimal()) {
			result = Matrix.of(MatrixLayout.ofOptimal(rows(), other.columns()));
			for (int r = 0; r < rows(); r++) for (int c = 0; c < other.columns(); c++) {
				double value = 0.0;

				for (int k = 0; k < columns(); k++) {
					value += get(r, k) * other.get(k, c);
				}

				result.set(value, r, c);
			}
		} else {
			result = Matrix.of(MatrixLayout.ofRowMajor(rows(), other.columns()));
			for (int k = 0; k < columns(); k++) {
				var otherRow = other.subMatrix(k, 0, 1, other.columns()).copy();

				for (int r = 0; r < rows(); r++) for (int c = 0; c < otherRow.columns(); c++) {
					var value = result.get(r, c);
					value += get(r, k) * otherRow.get(0, c);
					result.set(value, r, c);
				}
			}
		}

		return result;
	}

	/**
	 * Returns a matrix such that the divider multiplied by that matrix equals this matrix
	 * @param divider the matrix to divide by
	 * @return the result of the division
	 * @throws brownshome.vecmath.matrix.factorisation.SingularMatrixException if the divider matrix is singular, or close to singular
	 */
	default Matrix divideLeft(Matrix divider) {
		return divider.factorisation().leftSolve(this);
	}

	/**
	 * Returns a matrix such that that matrix multiplied by the divider equals this matrix
	 * @param divider the matrix to divide by
	 * @return the result of the division
	 * @throws brownshome.vecmath.matrix.factorisation.SingularMatrixException if the divider matrix is singular, or close to singular
	 */
	default Matrix divideRight(Matrix divider) {
		return divider.factorisation().rightSolve(this);
	}

	/**
	 * The determinant of this matrix
	 * @return the determinant
	 */
	default double determinant() {
		return factorisation().determinant();
	}

	/**
	 * Joins the rows of this matrix and the given matrix
	 * @param other the other matrix
	 * @return a combined matrix
	 */
	default Matrix joinRows(Matrix other) {
		assert rows() == other.rows();
		return transpose().joinColumns(other.transpose()).transpose();
	}

	/**
	 * Joins the columns of this matrix and the given matrix
	 * @param other the other matrix
	 * @return a combined matrix
	 */
	default Matrix joinColumns(Matrix other) {
		assert columns() == other.columns();
		return new JoinedMatrix(this, other);
	}

	/**
	 * Returns true if this matrix exactly equals the other matrix
	 * @param other the other element
	 * @return a boolean
	 */
	@Override
	default boolean exactEquals(Matrix other) {
		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			if (get(r, c) != other.get(r, c)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Returns this matrix as a single value, if it is one
	 * @return the single value in this matrix
	 */
	default double asValue() {
		assert rows() == 1;
		assert columns() == 1;

		return get(0, 0);
	}

	/**
	 * Returns this matrix as a row vector, if it is one
	 * @return the row of this matrix as an unknown-size vector
	 */
	default VecN asRowVec() {
		assert rows() == 1;

		return row(0);
	}

	/**
	 * Returns this matrix as a column vector, if it is one
	 * @return the column of this matrix as an unknown-size vector
	 */
	default VecN asColumnVec() {
		return column(0);
	}

	/**
	 * Returns this matrix as a symmetric matrix
	 * @return a symmetric matrix
	 */
	default Matrix asSymmetric() {
		assert exactEquals(transpose());

		return new WrappedSymmetricMatrix(this);
	}

	default MMatrix asSymmetricCopy() {
		assert exactEquals(transpose());

		var result = Matrix.ofSymmetric(rows());
		result.set(this);
		return result;
	}

	@Override
	default ArrayMatrix asArrayBacked() {
		return (ArrayMatrix) GenericElement.super.asArrayBacked();
	}

	@Override
	default ArrayMatrix arrayBackedCopy() {
		return arrayBackedCopy(MatrixLayout.ofOptimal(rows(), columns()));
	}

	/**
	 * An array-backed copy of this matrix with the given layout
	 * @return a copy
	 */
	default ArrayMatrix arrayBackedCopy(MatrixLayout layout) {
		var result = Matrix.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MMatrix copy() {
		var result = Matrix.of(rows(), columns());
		result.set(this);
		return result;
	}

	@Override
	default MMatrix move() {
		return (MMatrix) GenericElement.super.move();
	}

	/**
	 * Formats a matrix as a string
	 * @param m the matrix to format
	 * @return the string
	 */
	static String toString(Matrix m) {
		StringBuilder output = new StringBuilder("[\n");

		for (int r = 0; r < m.rows(); r++) {
			for (int c = 0; c < m.columns(); c++) {
				output.append(String.format("\t%+.2f", m.get(r, c)));
			}

			output.append('\n');
		}

		output.append(']');

		return output.toString();
	}
}
