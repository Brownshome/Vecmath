package brownshome.vecmath.matrix.basic;

import java.util.Arrays;

import brownshome.vecmath.generic.GenericArrayElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;

public record BasicSymmetricMatrix(double[] backingArray, MatrixLayout layout) implements ArrayMatrix, SymmetricMMatrix {
	public BasicSymmetricMatrix {
		assert backingArray.length >= layout.end();
		assert layout.rows() == layout.columns();
	}

	@Override
	public int rows() {
		return SymmetricMMatrix.super.rows();
	}

	@Override
	public int columns() {
		return SymmetricMMatrix.super.columns();
	}

	@Override
	public boolean isRowOptimal() {
		return ArrayMatrix.super.isRowOptimal() && ArrayMatrix.super.isColumnOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return ArrayMatrix.super.isRowOptimal() && ArrayMatrix.super.isColumnOptimal();
	}

	@Override
	public double get(int row, int column) {
		if (row < column) {
			return get(column, row);
		}

		return ArrayMatrix.super.get(row, column);
	}

	@Override
	public void set(double value, int row, int column) {
		if (row < column) {
			set(value, column, row);
			return;
		}

		ArrayMatrix.super.set(value, row, column);
	}

	@Override
	public void set(Matrix e) {
		assert e != null;

		if (layout().isContinuous() && e instanceof ArrayMatrix a && a.layout().equals(layout())) {
			ArrayMatrix.super.set(a);
			return;
		}

		SymmetricMMatrix.super.set(e);
	}

	@Override
	public ArrayMatrix subMatrix(int r, int c, int rows, int columns) {
		var result = ArrayMatrix.super.subMatrix(r, c, rows, columns);

		if (r == c && rows == columns) {
			result = result.asSymmetric();
		}

		return result;
	}

	@Override
	public ArrayMatrix transpose() {
		return this;
	}

	@Override
	public void addToSelf(Matrix e) {
		assert e != null;

		if (layout().isContinuous() && e instanceof ArrayMatrix a && a.layout().equals(layout())) {
			ArrayMatrix.super.addToSelf(a);
			return;
		}

		SymmetricMMatrix.super.addToSelf(e);
	}

	@Override
	public void scaleSelf(double scale) {
		if (layout().isContinuous()) {
			ArrayMatrix.super.scaleSelf(scale);
			return;
		}

		SymmetricMMatrix.super.scaleSelf(scale);
	}

	@Override
	public void scaleSelf(Matrix scale) {
		assert scale != null;

		if (layout().isContinuous() && scale instanceof ArrayMatrix a && a.layout().equals(layout())) {
			ArrayMatrix.super.scaleSelf(a);
			return;
		}

		SymmetricMMatrix.super.scaleSelf(scale);
	}

	@Override
	public boolean exactEquals(Matrix other) {
		assert other != null;

		if (layout().isContinuous() && other instanceof ArrayMatrix a && a.layout().equals(layout())) {
			return ArrayMatrix.super.exactEquals(a);
		}

		return SymmetricMMatrix.super.exactEquals(other);
	}

	@Override
	public ArrayMatrix asSymmetric() {
		return (ArrayMatrix) SymmetricMMatrix.super.asSymmetric();
	}

	@Override
	public ArrayMatrix asArrayBacked() {
		return ArrayMatrix.super.asArrayBacked();
	}

	@Override
	public ArrayMatrix arrayBackedCopy(MatrixLayout layout) {
		assert layout != null;

		if (layout().equals(layout)) {
			return Matrix.ofSymmetric(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		var result = Matrix.ofSymmetric(layout);
		result.set(this);
		return result;
	}

	@Override
	public ArrayMatrix move() {
		return ArrayMatrix.super.move();
	}

	@Override
	public int size() {
		return layout.rows();
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
