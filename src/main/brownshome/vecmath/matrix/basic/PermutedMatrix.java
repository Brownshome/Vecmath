package brownshome.vecmath.matrix.basic;

import java.util.Arrays;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.MatrixWithFastMultiply;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.PermutedFactorisation;
import brownshome.vecmath.vector.VecN;

public class PermutedMatrix implements MatrixWithFastMultiply {
	private final Matrix delegate;
	private final int[] rowPermutation, columnPermutation;

	public static Matrix of(Matrix matrix, int[] rows, int[] columns) {
		if (rows == PermutationUtil.IDENTITY_PERMUTATION && columns == PermutationUtil.IDENTITY_PERMUTATION) {
			return matrix;
		}

		return new PermutedMatrix(matrix, rows, columns);
	}

	public static Matrix rows(Matrix matrix, int[] rows) {
		return of(matrix, rows, null);
	}

	public static Matrix columns(Matrix matrix, int[] columns) {
		return of(matrix, null, columns);
	}

	public PermutedMatrix(Matrix delegate, int[] rowPermutation, int[] columnPermutation) {
		assert delegate != null;
		assert rowPermutation != PermutationUtil.IDENTITY_PERMUTATION || columnPermutation != PermutationUtil.IDENTITY_PERMUTATION;

		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || rowPermutation.length >= 2;
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || rowPermutation.length == delegate.rows();
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(rowPermutation).distinct().count() == delegate.rows();
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(rowPermutation).allMatch(i -> i < rowPermutation.length && i >= 0);
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || PermutationUtil.invertPermutation(rowPermutation) != PermutationUtil.IDENTITY_PERMUTATION;

		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || columnPermutation.length >= 2;
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || columnPermutation.length == delegate.columns();
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(columnPermutation).distinct().count() == delegate.columns();
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(columnPermutation).allMatch(i -> i < columnPermutation.length && i >= 0);
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || PermutationUtil.invertPermutation(columnPermutation) != PermutationUtil.IDENTITY_PERMUTATION;

		this.delegate = delegate;
		this.rowPermutation = rowPermutation;
		this.columnPermutation = columnPermutation;
	}

	protected Matrix delegate() {
		return delegate;
	}

	protected final int[] rowPermutation() {
		return rowPermutation;
	}

	protected final int[] columnPermutation() {
		return columnPermutation;
	}

	@Override
	public int rows() {
		return delegate.rows();
	}

	@Override
	public int columns() {
		return delegate.columns();
	}

	@Override
	public boolean isRowOptimal() {
		return columnPermutation == PermutationUtil.IDENTITY_PERMUTATION && delegate.isRowOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return rowPermutation == PermutationUtil.IDENTITY_PERMUTATION && delegate.isColumnOptimal();
	}

	@Override
	public double get(int row, int column) {
		if (rowPermutation != PermutationUtil.IDENTITY_PERMUTATION) {
			row = rowPermutation[row];
		}

		if (columnPermutation != PermutationUtil.IDENTITY_PERMUTATION) {
			column = columnPermutation[column];
		}

		return delegate.get(row, column);
	}

	@Override
	public VecN row(int r) {
		if (columnPermutation == PermutationUtil.IDENTITY_PERMUTATION) {
			r = rowPermutation[r];
			return delegate.row(r);
		}

		return MatrixWithFastMultiply.super.row(r);
	}

	@Override
	public VecN column(int c) {
		if (rowPermutation == PermutationUtil.IDENTITY_PERMUTATION) {
			c = columnPermutation[c];
			return delegate.column(c);
		}

		return MatrixWithFastMultiply.super.column(c);
	}

	@Override
	public Matrix transpose() {
		// If we are the best permutation for this delegate then we will also be the best permutation of the transposed
		return new PermutedMatrix(delegate.transpose(), columnPermutation, rowPermutation);
	}

	@Override
	public Matrix permuteByRow(int... rows) {
		return of(delegate, PermutationUtil.combinePermutations(rowPermutation, rows), columnPermutation);
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		return of(delegate, rowPermutation, PermutationUtil.combinePermutations(columnPermutation, columns));
	}

	@Override
	public Factorisation factorisation(double tolerance) {
		return new PermutedFactorisation(delegate.factorisation(tolerance), rowPermutation, columnPermutation);
	}

	@Override
	public Matrix multiply(Matrix other) {
		if (other instanceof ConstantMatrix constant) {
			return rows(delegate.multiply(constant), rowPermutation);
		}

		if (other instanceof DiagonalMatrix) {
			return delegate.multiply(other).permuteByRow(rowPermutation).permuteByColumn(columnPermutation);
		}

		if (delegate instanceof MatrixWithFastMultiply) {
			return rows(delegate.multiply(other.permuteByRow(PermutationUtil.invertPermutation(columnPermutation))), rowPermutation);
		}

		if (other instanceof PermutedMatrix otherPermuted) {
			var inner = delegate
					.permuteByColumn(columnPermutation)
					.permuteByColumn(PermutationUtil.invertPermutation(otherPermuted.rowPermutation))
					.multiply(otherPermuted.delegate);

			return of(inner, rowPermutation, otherPermuted.columnPermutation);
		}

		return MatrixWithFastMultiply.super.multiply(other);
	}

	@Override
	public MMatrix move() {
		return new PermutedMMatrix(delegate.move(), rowPermutation, columnPermutation);
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
