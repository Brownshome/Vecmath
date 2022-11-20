package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.MatrixWithFastMultiply;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.DiagonalFactorisation;
import brownshome.vecmath.matrix.factorisation.basic.SingularFactorisation;

public class DiagonalMatrix implements SymmetricMatrix, MatrixWithFastMultiply {
	private final double value;
	private final int size;

	public DiagonalMatrix(double value, int size) {
		assert size >= 0;

		this.value = value;
		this.size = size;
	}

	@Override
	public boolean isRowOptimal() {
		return true;
	}

	@Override
	public boolean isColumnOptimal() {
		return true;
	}

	@Override
	public double get(int row, int column) {
		assert row < size;
		assert row >= 0;
		assert column < size;
		assert column >= 0;

		return row == column ? value : 0.0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		assert r >= 0;
		assert r + rows <= rows();
		assert c >= 0;
		assert c + columns <= columns();

		if (r == c && rows == columns) {
			return new DiagonalMatrix(value, rows);
		}

		return SymmetricMatrix.super.subMatrix(r, c, rows, columns);
	}

	@Override
	public Matrix add(Matrix e) {
		assert e.rows() == size;
		assert e.columns() == size;

		if (e instanceof ZeroMatrix) {
			return this;
		}

		if (e instanceof DiagonalMatrix other) {
			return new DiagonalMatrix(value + other.value, size);
		}

		return SymmetricMatrix.super.add(e);
	}

	@Override
	public Matrix subtract(Matrix e) {
		assert e.rows() == size;
		assert e.columns() == size;

		if (e instanceof ZeroMatrix) {
			return this;
		}

		if (e instanceof DiagonalMatrix other) {
			return new DiagonalMatrix(value - other.value, size);
		}

		return SymmetricMatrix.super.subtract(e);
	}

	@Override
	public Matrix scale(double scale) {
		return new DiagonalMatrix(value * scale, size);
	}

	@Override
	public Matrix scale(Matrix scale) {
		assert scale.rows() == size;
		assert scale.columns() == size;

		if (scale instanceof ZeroMatrix zero) {
			return zero;
		}

		if (scale instanceof DiagonalMatrix other) {
			return new DiagonalMatrix(value * other.value, size);
		}

		return SymmetricMatrix.super.scale(scale);
	}

	@Override
	public Matrix scaleAdd(Matrix e, double scale) {
		assert e.rows() == size;
		assert e.columns() == size;

		if (e instanceof ZeroMatrix) {
			return this;
		}

		if (e instanceof DiagonalMatrix other) {
			return new DiagonalMatrix(value + scale * other.value, size);
		}

		return SymmetricMatrix.super.scaleAdd(e, scale);
	}

	@Override
	public Matrix negated() {
		return new DiagonalMatrix(-value, size);
	}

	@Override
	public Matrix interpolated(Matrix other, double t) {
		assert other.rows() == size;
		assert other.columns() == size;

		if (other instanceof ZeroMatrix) {
			return scale(1.0 - t);
		}

		if (other instanceof DiagonalMatrix d) {
			return new DiagonalMatrix(value * (1.0 - t) + d.value * t, size);
		}

		return SymmetricMatrix.super.interpolated(other, t);
	}

	@Override
	public Factorisation factorisation(double tolerance) {
		return value == 0.0 ? new SingularFactorisation(size) : new DiagonalFactorisation(value, size);
	}

	@Override
	public Matrix multiply(Matrix other) {
		return other.scale(value);
	}

	@Override
	public Matrix multiplyLeft(Matrix matrix) {
		return matrix.scale(value);
	}

	@Override
	public boolean exactEquals(Matrix other) {
		if (other instanceof ZeroMatrix) {
			return value == 0.0;
		}

		if (other instanceof DiagonalMatrix diag) {
			return value == diag.value;
		}

		return SymmetricMatrix.super.exactEquals(other);
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
