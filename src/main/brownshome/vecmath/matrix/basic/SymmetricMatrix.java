package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.MatrixWithFastMultiply;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.CholeskyFactorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;

/**
 * A marker interface noting that this matrix is symmetric
 */
public interface SymmetricMatrix extends Matrix {
	/**
	 * Returns the number of rows and columns in this matrix
	 * @return the size of this matrix
	 */
	int size();

	@Override
	default int rows() {
		return size();
	}

	@Override
	default int columns() {
		return size();
	}

	@Override
	default Matrix subMatrix(int r, int c, int rows, int columns) {
		var result = Matrix.super.subMatrix(r, c, rows, columns);

		if (r == c && rows == columns) {
			result = result.asSymmetric();
		}

		return result;
	}

	@Override
	default Matrix add(Matrix e) {
		var result = Matrix.super.add(e);

		return e instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	default Matrix subtract(Matrix e) {
		var result = Matrix.super.subtract(e);

		return e instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	default Matrix scale(double scale) {
		return Matrix.super.scale(scale).asSymmetric();
	}

	@Override
	default Matrix scale(Matrix scale) {
		var result = Matrix.super.scale(scale);

		return scale instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	default Matrix scaleAdd(Matrix e, double scale) {
		var result = Matrix.super.scaleAdd(e, scale);

		return e instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	default Matrix negated() {
		return Matrix.super.negated().asSymmetric();
	}

	@Override
	default Matrix interpolated(Matrix other, double t) {
		var result = Matrix.super.interpolated(other, t);

		return other instanceof SymmetricMatrix ? result.asSymmetric() : result;
	}

	@Override
	default Matrix transpose() {
		return this;
	}

	@Override
	default Factorisation factorisation(double tolerance) {
		return new CholeskyFactorisation((BasicSymmetricMatrix) asSymmetricCopy(), tolerance);
	}

	@Override
	default boolean exactEquals(Matrix other) {
		if (other instanceof SymmetricMatrix) {
			for (int r = 0; r < rows(); r++) for (int c = 0; c <= r; c++) {
				if (get(r, c) != other.get(r, c)) {
					return false;
				}
			}

			return true;
		}

		return Matrix.super.exactEquals(other);
	}

	@Override
	default Matrix asSymmetric() {
		return this;
	}

	@Override
	default ArrayMatrix asArrayBacked() {
		return Matrix.super.asArrayBacked();
	}

	@Override
	default ArrayMatrix arrayBackedCopy() {
		return Matrix.super.arrayBackedCopy();
	}

	@Override
	default ArrayMatrix arrayBackedCopy(MatrixLayout layout) {
		return Matrix.super.arrayBackedCopy(layout);
	}

	@Override
	default MMatrix copy() {
		return Matrix.super.copy();
	}

	@Override
	default MMatrix move() {
		return asSymmetricCopy();
	}
}
