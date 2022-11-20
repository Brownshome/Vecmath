package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;

public final class ZeroMatrix extends ConstantMatrix {
	public ZeroMatrix(int rows, int columns) {
		super(0.0, rows, columns);
	}

	@Override
	public ArrayMatrix arrayBackedCopy(MatrixLayout layout) {
		return Matrix.of(layout);
	}

	@Override
	public Matrix multiplyLeft(Matrix other) {
		return new ZeroMatrix(other.rows(), columns());
	}

	@Override
	public Matrix add(Matrix e) {
		return e;
	}

	@Override
	public Matrix subtract(Matrix e) {
		return e.negated();
	}

	@Override
	public Matrix scale(double scale) {
		return this;
	}

	@Override
	public Matrix scale(Matrix scale) {
		return this;
	}

	@Override
	public Matrix scaleAdd(Matrix e, double scale) {
		return e.scale(scale);
	}

	@Override
	public Matrix negated() {
		return this;
	}

	@Override
	public Matrix interpolated(Matrix other, double t) {
		return other.scale(t);
	}

	@Override
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		return new ZeroMatrix(rows, columns);
	}

	@Override
	public Matrix multiply(Matrix other) {
		return new ZeroMatrix(rows(), other.columns());
	}

	@Override
	public ArrayMatrix arrayBackedCopy() {
		return arrayBackedCopy(MatrixLayout.ofOptimal(rows(), columns()));
	}
}
