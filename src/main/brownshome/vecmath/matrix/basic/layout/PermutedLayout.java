package brownshome.vecmath.matrix.basic.layout;

import java.util.Arrays;

import brownshome.vecmath.matrix.basic.PermutationUtil;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.VecNLayout;

public record PermutedLayout(MatrixLayout delegate, int[] rowPermutation, int[] columnPermutation) implements MatrixLayout {
	public static MatrixLayout of(MatrixLayout delegate, int[] rows, int[] columns) {
		if (rows == PermutationUtil.IDENTITY_PERMUTATION && columns == PermutationUtil.IDENTITY_PERMUTATION) {
			return delegate;
		}

		return new PermutedLayout(delegate, rows, columns);
	}

	public static MatrixLayout rows(MatrixLayout delegate, int[] rows) {
		return of(delegate, rows, PermutationUtil.IDENTITY_PERMUTATION);
	}

	public static MatrixLayout columns(MatrixLayout delegate, int[] columns) {
		return of(delegate, PermutationUtil.IDENTITY_PERMUTATION, columns);
	}

	public PermutedLayout {
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
	}

	@Override
	public int start() {
		return delegate.start();
	}

	@Override
	public int end() {
		return delegate.end();
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isContinuous() {
		return delegate.isContinuous();
	}

	@Override
	public boolean isPacked() {
		return delegate.isPacked();
	}

	@Override
	public int arrayIndex(int row, int column) {
		if (rowPermutation != PermutationUtil.IDENTITY_PERMUTATION) {
			row = rowPermutation[row];
		}

		if (columnPermutation != PermutationUtil.IDENTITY_PERMUTATION) {
			column = columnPermutation[column];
		}

		return delegate.arrayIndex(row, column);
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
	public boolean isRowPacked() {
		return delegate.isRowPacked();
	}

	@Override
	public boolean isColumnPacked() {
		return delegate.isColumnPacked();
	}

	@Override
	public MatrixLayout transpose() {
		// If we are the best permutation for this delegate then we will also be the best permutation of the transposed
		return new PermutedLayout(delegate.transpose(), columnPermutation, rowPermutation);
	}

	@Override
	public MatrixLayout permuteByRow(int... rows) {
		return of(delegate, PermutationUtil.combinePermutations(rowPermutation, rows), columnPermutation);
	}

	@Override
	public MatrixLayout permuteByColumn(int... columns) {
		return of(delegate, columnPermutation, PermutationUtil.combinePermutations(columnPermutation, columns));
	}

	@Override
	public VecNLayout row(int r) {
		if (columnPermutation == PermutationUtil.IDENTITY_PERMUTATION) {
			r = rowPermutation[r];
			return delegate.row(r);
		}

		return MatrixLayout.super.row(r);
	}

	@Override
	public VecNLayout column(int c) {
		if (rowPermutation == PermutationUtil.IDENTITY_PERMUTATION) {
			c = columnPermutation[c];
			return delegate.column(c);
		}

		return MatrixLayout.super.column(c);
	}
}
