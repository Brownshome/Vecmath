package brownshome.vecmath.matrix;

import brownshome.vecmath.generic.GenericMElement;
import brownshome.vecmath.vector.MVecN;

/**
 * A mutable matrix
 */
public interface MMatrix extends GenericMElement<Matrix>, Matrix {
	/**
	 * Sets a value in this matrix
	 * @param value the value to set
	 * @param row the row
	 * @param column the column
	 */
	void set(double value, int row, int column);

	@Override
	default void set(Matrix matrix) {
		assert rows() == matrix.rows();
		assert columns() == matrix.columns();

		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			set(matrix.get(r, c), r, c);
		}
	}

	@Override
	default MMatrix subMatrix(int r, int c, int rows, int columns) {
		return Matrix.super.subMatrix(r, c, rows, columns).move();
	}

	@Override
	default MVecN row(int r) {
		return (MVecN) Matrix.super.row(r);
	}

	@Override
	default MVecN column(int c) {
		return (MVecN) Matrix.super.column(c);
	}

	@Override
	default MMatrix transpose() {
		return Matrix.super.transpose().move();
	}

	@Override
	default MMatrix permuteByRow(int... rows) {
		return (MMatrix) Matrix.super.permuteByRow(rows);
	}

	@Override
	default MMatrix permuteByColumn(int... columns) {
		return (MMatrix) Matrix.super.permuteByColumn(columns);
	}

	/**
	 * Joins the rows of this matrix and the given matrix
	 * @param other the other matrix
	 * @return a combined mutable matrix
	 */
	default MMatrix joinRows(MMatrix other) {
		return Matrix.super.joinRows(other).move();
	}

	/**
	 * Joins the columns of this matrix and the given matrix
	 * @param other the other matrix
	 * @return a combined mutable matrix
	 */
	default MMatrix joinColumns(MMatrix other) {
		return Matrix.super.joinColumns(other).move();
	}

	@Override
	default MVecN asRowVec() {
		return (MVecN) Matrix.super.asRowVec();
	}

	@Override
	default MVecN asColumnVec() {
		return (MVecN) Matrix.super.asColumnVec();
	}

	@Override
	default MMatrix asSymmetric() {
		return (MMatrix) Matrix.super.asSymmetric();
	}

	@Override
	default void addToSelf(Matrix matrix) {
		assert rows() == matrix.rows();
		assert columns() == matrix.columns();

		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			set(get(r, c) + matrix.get(r, c), r, c);
		}
	}

	@Override
	default void scaleSelf(double scale) {
		scaleSelf(Matrix.constant(scale, rows(), columns()));
	}

	@Override
	default void scaleSelf(Matrix matrix) {
		assert rows() == matrix.rows();
		assert columns() == matrix.columns();

		for (int r = 0; r < rows(); r++) for (int c = 0; c < columns(); c++) {
			set(get(r, c) * matrix.get(r, c), r, c);
		}
	}

	/**
	 * Transposes this matrix in-place
	 */
	default void transposeSelf() {
		set(transpose().copy());
	}

	/**
	 * Inverts this matrix in-place
	 */
	default void invertSelf() {
		set(invert());
	}

	/**
	 * Permutes the rows of this matrix in-place
	 * @param rows the row permutation
	 */
	default void permuteSelfByRow(int... rows) {
		set(permuteByRow(rows).copy());
	}

	/**
	 * Permutes the columns of this matrix in-place
	 * @param columns the column permutation
	 */
	default void permuteSelfByColumns(int... columns) {
		set(permuteByColumn(columns).copy());
	}

	/**
	 * Sets this matrix to be right times this matrix
	 * @param right the right matrix
	 */
	default void multiplyRightSelf(Matrix right) {
		set(multiply(right));
	}

	/**
	 * Sets this matrix to be this matrix times the left one
	 * @param left the left matrix
	 */
	default void multiplyLeftSelf(Matrix left) {
		set(left.multiply(this));
	}

	/**
	 * Sets this matrix to be this matrix divided by to the right by the divider
	 * @param divider the matrix to divide by
	 */
	default void divideRightSelf(Matrix divider) {
		set(divideRight(divider));
	}

	/**
	 * Sets this matrix to be this matrix divided by to the left by the divider
	 * @param divider the matrix to divide by
	 */
	default void divideLeftSelf(Matrix divider) {
		set(divideLeft(divider));
	}

	@Override
	default MMatrix move() {
		return (MMatrix) GenericMElement.super.move();
	}
}
