package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.vector.MVecN;

public final class PermutedMMatrix extends PermutedMatrix implements MMatrix {
	public static MMatrix of(MMatrix matrix, int[] rows, int[] columns) {
		if (rows == PermutationUtil.IDENTITY_PERMUTATION && columns == PermutationUtil.IDENTITY_PERMUTATION) {
			return matrix;
		}

		return new PermutedMMatrix(matrix, rows, columns);
	}

	public static MMatrix rows(MMatrix matrix, int[] rows) {
		return of(matrix, rows, null);
	}

	public static MMatrix columns(MMatrix matrix, int[] columns) {
		return of(matrix, null, columns);
	}

	public PermutedMMatrix(MMatrix matrix, int[] rowPermutation, int[] columnPermutation) {
		super(matrix, rowPermutation, columnPermutation);
	}

	@Override
	protected MMatrix delegate() {
		return (MMatrix) super.delegate();
	}

	@Override
	public void set(double value, int row, int column) {
		if (rowPermutation() != PermutationUtil.IDENTITY_PERMUTATION) {
			row = rowPermutation()[row];
		}

		if (columnPermutation() != PermutationUtil.IDENTITY_PERMUTATION) {
			column = columnPermutation()[column];
		}

		delegate().set(value, row, column);
	}

	@Override
	public MVecN row(int r) {
		return (MVecN) super.row(r);
	}

	@Override
	public MVecN column(int c) {
		return (MVecN) super.column(c);
	}

	@Override
	public MMatrix transpose() {
		return super.transpose().move();
	}

	@Override
	public MMatrix permuteByRow(int... rows) {
		return of(delegate(), PermutationUtil.combinePermutations(rowPermutation(), rows), columnPermutation());
	}

	@Override
	public MMatrix permuteByColumn(int... columns) {
		return of(delegate(), columnPermutation(), PermutationUtil.combinePermutations(columnPermutation(), columns));
	}

	@Override
	public MMatrix move() {
		return MMatrix.super.move();
	}
}
