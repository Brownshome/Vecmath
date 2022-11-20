package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.MatrixWithFastMultiply;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.VecN;

public class WrappedSymmetricMatrix implements SymmetricMatrix, MatrixWithFastMultiply {
	private final Matrix delegate;

	public WrappedSymmetricMatrix(Matrix delegate) {
		assert delegate.rows() == delegate.columns();
		assert delegate.exactEquals(delegate.transpose());

		this.delegate = delegate;
	}

	protected Matrix delegate() {
		return delegate;
	}

	@Override
	public boolean isRowOptimal() {
		return delegate.isRowOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return delegate.isColumnOptimal();
	}

	@Override
	public double get(int row, int column) {
		return delegate.get(row, column);
	}

	@Override
	public VecN row(int r) {
		return delegate.row(r);
	}

	@Override
	public VecN column(int c) {
		return delegate.column(c);
	}

	@Override
	public Matrix invert() {
		return delegate.invert().asSymmetric();
	}

	@Override
	public Matrix permuteByRow(int... rows) {
		if (rows == PermutationUtil.IDENTITY_PERMUTATION) {
			return this;
		}

		return delegate.permuteByRow(rows);
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		if (columns == PermutationUtil.IDENTITY_PERMUTATION) {
			return this;
		}

		return delegate.permuteByColumn(columns);
	}

	@Override
	public Matrix divideLeft(Matrix divider) {
		return delegate.divideLeft(divider);
	}

	@Override
	public Matrix divideRight(Matrix divider) {
		return delegate.divideRight(divider);
	}

	@Override
	public double determinant() {
		return delegate.determinant();
	}

	@Override
	public Matrix joinRows(Matrix other) {
		return delegate.joinRows(other);
	}

	@Override
	public Matrix joinColumns(Matrix other) {
		return delegate.joinColumns(other);
	}

	@Override
	public int size() {
		return delegate.rows();
	}

	@Override
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		var result = delegate.subMatrix(r, c, rows, columns);

		if (r == c && rows == columns) {
			result = result.asSymmetric();
		}

		return result;
	}

	@Override
	public Matrix add(Matrix e) {
		var result = delegate.add(e);

		return e instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	public Matrix subtract(Matrix e) {
		var result = delegate.subtract(e);

		return e instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	public Matrix scale(double scale) {
		return delegate.scale(scale).asSymmetric();
	}

	@Override
	public Matrix scale(Matrix scale) {
		var result = delegate.scale(scale);

		return scale instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	public Matrix scaleAdd(Matrix e, double scale) {
		var result = delegate.scaleAdd(e, scale);

		return e instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	public Matrix negated() {
		return delegate.negated().asSymmetric();
	}

	@Override
	public Matrix interpolated(Matrix other, double t) {
		var result = delegate.interpolated(other, t);

		return other instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	public Matrix transpose() {
		return SymmetricMatrix.super.transpose();
	}

	@Override
	public Factorisation factorisation(double tolerance) {
		if (delegate instanceof MatrixWithFastMultiply) {
			// Probably more efficient than the Cholesky
			return delegate.factorisation(tolerance);
		}

		return SymmetricMatrix.super.factorisation(tolerance);
	}

	@Override
	public Matrix multiply(Matrix other) {
		return delegate.multiply(other);
	}

	@Override
	public Matrix multiplyLeft(Matrix matrix) {
		return matrix.multiply(delegate);
	}

	@Override
	public boolean exactEquals(Matrix other) {
		if (delegate instanceof MatrixWithFastMultiply) {
			// Probably more efficient
			return delegate.exactEquals(other);
		}

		return SymmetricMatrix.super.exactEquals(other);
	}

	@Override
	public Matrix asSymmetric() {
		return SymmetricMatrix.super.asSymmetric();
	}

	@Override
	public ArrayMatrix asArrayBacked() {
		return delegate.asArrayBacked().asSymmetric();
	}

	@Override
	public double asValue() {
		return delegate.asValue();
	}

	@Override
	public VecN asRowVec() {
		return delegate.asRowVec();
	}

	@Override
	public VecN asColumnVec() {
		return delegate.asColumnVec();
	}

	@Override
	public MMatrix asSymmetricCopy() {
		return delegate.asSymmetricCopy();
	}

	@Override
	public ArrayMatrix arrayBackedCopy() {
		return delegate.arrayBackedCopy();
	}

	@Override
	public ArrayMatrix arrayBackedCopy(MatrixLayout layout) {
		return delegate.arrayBackedCopy(layout);
	}

	@Override
	public MMatrix copy() {
		return delegate.copy();
	}

	@Override
	public MMatrix move() {
		return delegate.move().asSymmetric();
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
