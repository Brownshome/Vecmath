package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;

public final class SubMMatrix extends SubMatrix implements MMatrix {
	public SubMMatrix(Matrix delegate, int startRow, int startColumn, int rows, int columns) {
		super(delegate, startRow, startColumn, rows, columns);
	}

	@Override
	protected MMatrix delegate() {
		return (MMatrix) super.delegate();
	}

	@Override
	public void set(double value, int row, int column) {
		assert row < rows();
		assert column < columns();

		delegate().set(value, row + startRow(), column + startColumn());
	}

	@Override
	public MMatrix subMatrix(int r, int c, int rows, int columns) {
		return (MMatrix) super.subMatrix(r, c, rows, columns);
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
		return new SubMMatrix(delegate().transpose(), startColumn(), startRow(), columns(), rows());
	}

	@Override
	public MMatrix permuteByRow(int... rows) {
		return (MMatrix) super.permuteByRow(rows);
	}

	@Override
	public MMatrix permuteByColumn(int... columns) {
		return (MMatrix) super.permuteByColumn(columns);
	}

	@Override
	public MMatrix move() {
		return MMatrix.super.move();
	}
}
