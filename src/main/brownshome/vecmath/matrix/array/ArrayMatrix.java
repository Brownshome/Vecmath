package brownshome.vecmath.matrix.array;

import java.util.Arrays;

import brownshome.vecmath.generic.GenericArrayElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.array.ArrayVecN;

record CacheStrategy() {
	static final int CACHE_SIZE = 16 * 1024;
}

/**
 * An array-backed matrix
 */
public interface ArrayMatrix extends GenericArrayElement<MatrixLayout, Matrix>, MMatrix {
	@Override
	default int rows() {
		return layout().rows();
	}

	@Override
	default int columns() {
		return layout().columns();
	}

	@Override
	default boolean isRowOptimal() {
		return rows() * columns() <= CacheStrategy.CACHE_SIZE || layout().isRowOptimal();
	}

	@Override
	default boolean isColumnOptimal() {
		return rows() * columns() <= CacheStrategy.CACHE_SIZE || layout().isColumnOptimal();
	}

	@Override
	default double get(int row, int column) {
		return backingArray()[layout().arrayIndex(row, column)];
	}

	@Override
	default void set(double value, int row, int column) {
		backingArray()[layout().arrayIndex(row, column)] = value;
	}

	@Override
	default ArrayMatrix subMatrix(int r, int c, int rows, int columns) {
		return Matrix.of(backingArray(), layout().subLayout(r, c, rows, columns));
	}

	@Override
	default ArrayVecN row(int r) {
		return VecN.of(backingArray(), layout().row(r));
	}

	@Override
	default ArrayVecN column(int c) {
		return VecN.of(backingArray(), layout().column(c));
	}

	@Override
	default ArrayMatrix transpose() {
		return Matrix.of(backingArray(), layout().transpose());
	}

	@Override
	default ArrayMatrix permuteByRow(int... rows) {
		return Matrix.of(backingArray(), layout().permuteByRow(rows));
	}

	@Override
	default ArrayMatrix permuteByColumn(int... columns) {
		return Matrix.of(backingArray(), layout().permuteByColumn(columns));
	}

	@Override
	default void set(Matrix e) {
		assert e != null;

		if (layout().isContinuous() && e instanceof ArrayMatrix a && a.layout().equals(layout())) {
			GenericArrayElement.super.set(a);
			return;
		}

		MMatrix.super.set(e);
	}

	@Override
	default void addToSelf(Matrix e) {
		assert e != null;

		if (layout().isContinuous() && e instanceof ArrayMatrix a && a.layout().equals(layout())) {
			GenericArrayElement.super.addToSelf(a);
			return;
		}

		MMatrix.super.addToSelf(e);
	}

	@Override
	default void scaleSelf(double scale) {
		if (layout().isContinuous()) {
			GenericArrayElement.super.scaleSelf(scale);
			return;
		}

		MMatrix.super.scaleSelf(scale);
	}

	@Override
	default void scaleSelf(Matrix scale) {
		assert scale != null;

		if (layout().isContinuous() && scale instanceof ArrayMatrix a && a.layout().equals(layout())) {
			GenericArrayElement.super.scaleSelf(a);
			return;
		}

		MMatrix.super.scaleSelf(scale);
	}

	@Override
	default boolean exactEquals(Matrix other) {
		assert other != null;

		if (layout().isContinuous() && other instanceof ArrayMatrix a && a.layout().equals(layout())) {
			return GenericArrayElement.super.exactEquals(a);
		}

		return MMatrix.super.exactEquals(other);
	}

	@Override
	default ArrayVecN asRowVec() {
		return (ArrayVecN) MMatrix.super.asRowVec();
	}

	@Override
	default ArrayVecN asColumnVec() {
		return (ArrayVecN) MMatrix.super.asColumnVec();
	}

	@Override
	default ArrayMatrix asSymmetric() {
		assert exactEquals(transpose());

		return Matrix.ofSymmetric(backingArray(), layout());
	}

	@Override
	default ArrayMatrix asArrayBacked() {
		return (ArrayMatrix) GenericArrayElement.super.asArrayBacked();
	}

	@Override
	default ArrayMatrix arrayBackedCopy(MatrixLayout layout) {
		assert layout != null;

		if (layout().equals(layout)) {
			return Matrix.of(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		var result = Matrix.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default ArrayMatrix move() {
		return (ArrayMatrix) MMatrix.super.move();
	}
}
