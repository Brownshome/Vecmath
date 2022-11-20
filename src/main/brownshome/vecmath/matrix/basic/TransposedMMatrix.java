package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;

public class TransposedMMatrix extends TransposedMatrix implements MMatrix {
	public TransposedMMatrix(MMatrix delegate) {
		super(delegate);
	}

	@Override
	protected MMatrix delegate() {
		return (MMatrix) super.delegate();
	}

	@Override
	public void setToNegated() {
		delegate().setToNegated();
	}

	@Override
	public void set(double value, int row, int column) {
		delegate().set(value, column, row);
	}

	@Override
	public void scaleSelf(double scale) {
		delegate().scaleSelf(scale);
	}

	@Override
	public void transposeSelf() {
		delegate().transposeSelf();
	}

	@Override
	public void permuteSelfByRow(int... rows) {
		delegate().permuteSelfByColumns(columns());
	}

	@Override
	public void permuteSelfByColumns(int... columns) {
		delegate().permuteSelfByRow(rows());
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
		return (MMatrix) super.transpose();
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
