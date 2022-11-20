package brownshome.vecmath.matrix.basic;

import java.util.Objects;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.VecN;

public class TransposedMatrix implements Matrix {
	private final Matrix delegate;

	public TransposedMatrix(Matrix delegate) {
		assert delegate != null;

		this.delegate = delegate;
	}

	protected Matrix delegate() {
		return delegate;
	}

	@Override
	public int rows() {
		return delegate.columns();
	}

	@Override
	public int columns() {
		return delegate.rows();
	}

	@Override
	public boolean isRowOptimal() {
		return delegate.isColumnOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return delegate.isRowOptimal();
	}

	@Override
	public double get(int row, int column) {
		return delegate.get(column, row);
	}

	@Override
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		return delegate.subMatrix(c, r, columns, rows);
	}

	@Override
	public VecN row(int r) {
		return delegate.column(r);
	}

	@Override
	public VecN column(int c) {
		return delegate.row(c);
	}

	@Override
	public Matrix transpose() {
		return delegate;
	}

	@Override
	public Matrix permuteByRow(int... rows) {
		return delegate.permuteByColumn(rows).transpose();
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		return delegate.permuteByRow(columns).transpose();
	}

	@Override
	public MMatrix move() {
		return new TransposedMMatrix(delegate.move());
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}

}
