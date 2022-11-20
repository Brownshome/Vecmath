package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;

/**
 * A marker interface noting that this mutable matrix is symmetric
 */
public interface SymmetricMMatrix extends MMatrix, SymmetricMatrix {
	@Override
	default void set(Matrix matrix) {
		assert matrix.exactEquals(matrix.transpose());
		assert size() == matrix.rows();

		for (int r = 0; r < rows(); r++) for (int c = 0; c <= r; c++) {
			set(matrix.get(r, c), r, c);
		}
	}

	@Override
	default MMatrix subMatrix(int r, int c, int rows, int columns) {
		return (MMatrix) SymmetricMatrix.super.subMatrix(r, c, rows, columns);
	}

	@Override
	default MMatrix transpose() {
		return (MMatrix) SymmetricMatrix.super.transpose();
	}

	@Override
	default void addToSelf(Matrix matrix) {
		assert matrix.exactEquals(matrix.transpose());
		assert size() == matrix.rows();

		for (int r = 0; r < rows(); r++) for (int c = 0; c <= r; c++) {
			set(get(r, c) + matrix.get(r, c), r, c);
		}
	}

	@Override
	default void scaleSelf(double scale) {
		for (int r = 0; r < rows(); r++) for (int c = 0; c <= r; c++) {
			set(get(r, c) * scale, r, c);
		}
	}

	@Override
	default void scaleSelf(Matrix matrix) {
		assert matrix.exactEquals(matrix.transpose());
		assert size() == matrix.rows();

		for (int r = 0; r < rows(); r++) for (int c = 0; c <= r; c++) {
			set(get(r, c) * matrix.get(r, c), r, c);
		}
	}

	@Override
	default void transposeSelf() { }

	@Override
	default MMatrix asSymmetric() {
		return (MMatrix) SymmetricMatrix.super.asSymmetric();
	}

	@Override
	default MMatrix move() {
		return MMatrix.super.move();
	}
}
