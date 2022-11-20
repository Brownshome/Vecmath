package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;

public final class WrappedSymmetricMMatrix extends WrappedSymmetricMatrix implements SymmetricMMatrix {
	public WrappedSymmetricMMatrix(Matrix delegate) {
		super(delegate);
	}

	@Override
	protected MMatrix delegate() {
		return (MMatrix) super.delegate();
	}

	@Override
	public void subtractFromSelf(Matrix e) {
		assert e != null;
		assert e.exactEquals(e.transpose());

		delegate().subtractFromSelf(e);
	}

	@Override
	public void scaleAddToSelf(Matrix e, double scale) {
		assert e != null;
		assert e.exactEquals(e.transpose());

		delegate().scaleAddToSelf(e, scale);
	}

	@Override
	public void setToInterpolated(Matrix other, double t) {
		assert other != null;
		assert other.exactEquals(other.transpose());

		delegate().setToInterpolated(other, t);
	}

	@Override
	public void set(double value, int row, int column) {
		delegate().set(value, row, column);
		delegate().set(value, column, row);
	}

	@Override
	public void set(Matrix matrix) {
		assert matrix.exactEquals(matrix.transpose());
		assert size() == matrix.rows();

		delegate().set(matrix);
	}

	@Override
	public void addToSelf(Matrix matrix) {
		assert matrix.exactEquals(matrix.transpose());
		assert size() == matrix.rows();

		delegate().addToSelf(matrix);
	}

	@Override
	public void scaleSelf(double scale) {
		delegate().scaleSelf(scale);
	}

	@Override
	public void scaleSelf(Matrix matrix) {
		assert matrix.exactEquals(matrix.transpose());
		assert size() == matrix.rows();

		delegate().scaleSelf(matrix);
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
	public MVecN asRowVec() {
		return SymmetricMMatrix.super.asRowVec();
	}

	@Override
	public MVecN asColumnVec() {
		return SymmetricMMatrix.super.asColumnVec();
	}

	@Override
	public MMatrix permuteByRow(int... rows) {
		return super.permuteByRow(rows).move();
	}

	@Override
	public MMatrix permuteByColumn(int... columns) {
		return super.permuteByColumn(columns).move();
	}

	@Override
	public MMatrix joinRows(MMatrix other) {
		return super.joinRows(other).move();
	}

	@Override
	public MMatrix joinColumns(MMatrix other) {
		return super.joinColumns(other).move();
	}

	@Override
	public MMatrix subMatrix(int r, int c, int rows, int columns) {
		return super.subMatrix(r, c, rows, columns).move();
	}

	@Override
	public MMatrix transpose() {
		return super.transpose().move();
	}

	@Override
	public MMatrix asSymmetric() {
		return SymmetricMMatrix.super.asSymmetric();
	}

	@Override
	public MMatrix move() {
		return SymmetricMMatrix.super.move();
	}
}
