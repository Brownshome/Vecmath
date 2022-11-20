package brownshome.vecmath.matrix.basic;

import java.util.Arrays;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.MatrixWithFastMultiply;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.SingularFactorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;

public class ConstantMatrix implements MatrixWithFastMultiply {
	private final double value;
	private final int rows, columns;

	public ConstantMatrix(double value, int rows, int columns) {
		assert rows >= 0;
		assert columns >= 0;

		this.value = value;
		this.rows = rows;
		this.columns = columns;
	}

	@Override
	public int rows() {
		return rows;
	}

	@Override
	public int columns() {
		return columns;
	}

	@Override
	public double get(int row, int column) {
		assert row < rows;
		assert column < columns;

		return value;
	}

	@Override
	public Matrix add(Matrix e) {
		assert e.rows() == rows;
		assert e.columns() == columns;

		if (e instanceof ConstantMatrix c) {
			return new ConstantMatrix(value + c.value, rows, columns);
		}

		return MatrixWithFastMultiply.super.add(e);
	}

	@Override
	public Matrix subtract(Matrix e) {
		assert e.rows() == rows;
		assert e.columns() == columns;

		if (e instanceof ConstantMatrix c) {
			return new ConstantMatrix(value - c.value, rows, columns);
		}

		return MatrixWithFastMultiply.super.subtract(e);
	}

	@Override
	public Matrix scale(double scale) {
		return new ConstantMatrix(value * scale, rows, columns);
	}

	@Override
	public Matrix scale(Matrix scale) {
		assert scale.rows() == rows;
		assert scale.columns() == columns;

		if (scale instanceof ConstantMatrix c) {
			return scale(c.value);
		}

		return scale.scale(value);
	}

	@Override
	public Matrix scaleAdd(Matrix e, double scale) {
		assert e.rows() == rows;
		assert e.columns() == columns;

		if (e instanceof ConstantMatrix c) {
			return add(c.scale(scale));
		}

		return MatrixWithFastMultiply.super.scaleAdd(e, scale);
	}

	@Override
	public Matrix negated() {
		return new ConstantMatrix(-value, rows, columns);
	}

	@Override
	public Matrix interpolated(Matrix other, double t) {
		assert other.rows() == rows;
		assert other.columns() == columns;

		if (other instanceof ConstantMatrix c) {
			return new ConstantMatrix(value * (1.0 - t) + c.value * t, rows, columns);
		}

		return MatrixWithFastMultiply.super.interpolated(other, t);
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
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		assert r >= 0;
		assert r + rows <= rows();
		assert c >= 0;
		assert c + columns <= columns();

		return new ConstantMatrix(value, rows, columns);
	}

	@Override
	public Matrix transpose() {
		return new ConstantMatrix(value, columns, rows);
	}

	@Override
	public Matrix permuteByRow(int... rows) {
		return this;
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		return this;
	}

	@Override
	public Factorisation factorisation(double tolerance) {
		assert rows == columns;

		return new SingularFactorisation(rows());
	}

	@Override
	public Matrix multiply(Matrix other) {
		assert columns == other.rows();

		if (other instanceof ConstantMatrix c) {
			if (c instanceof ZeroMatrix z) {
				return z.multiplyLeft(this);
			}

			return new ConstantMatrix(value * c.value * columns, rows, c.columns);
		}

		if (other instanceof PermutationMatrix) {
			return this;
		}

		if (other instanceof DiagonalMatrix d) {
			return d.multiplyLeft(this);
		}

		var result = Matrix.of(MatrixLayout.ofColumnMajor(rows, other.columns()));
		for (int c = 0; c < other.columns(); c++) {
			double sum = 0.0;

			for (int r = 0; r < other.rows(); r++) {
				sum += other.get(r, c);
			}

			sum *= value;

			for (int r = 0; r < rows(); r++) {
				result.set(sum, r, c);
			}
		}

		return result;
	}

	@Override
	public boolean exactEquals(Matrix other) {
		assert other.rows() == rows;
		assert other.columns() == columns;

		if (other instanceof ConstantMatrix c) {
			return value == c.value;
		}

		return MatrixWithFastMultiply.super.exactEquals(other);
	}

	@Override
	public ArrayMatrix arrayBackedCopy() {
		var result = Matrix.of(MatrixLayout.ofOptimal(rows, columns));
		Arrays.fill(result.backingArray(), value);
		return result;
	}

	@Override
	public MMatrix asSymmetricCopy() {
		assert exactEquals(transpose());

		var result = Matrix.ofSymmetric(MatrixLayout.ofSymmetricRowMajor(rows()));
		Arrays.fill(result.backingArray(), value);
		return result;
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
