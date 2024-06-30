package brownshome.vecmath.basic.layout;

import brownshome.vecmath.matrix.basic.layout.EmptyMatrixLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;

public final class BasicRowLayout extends BasicLinearLayout {
	public BasicRowLayout(int size, int offset, int stride) {
		super(size, offset, stride);
	}

	@Override
	public int rows() {
		return 1;
	}

	@Override
	public int columns() {
		return elements();
	}

	@Override
	public int rowStride() {
		return 0;
	}

	@Override
	public int columnStride() {
		return stride();
	}

	@Override
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		if (rows == 0) {
			assert columns < columns();
			MatrixLayout.of(rows, columns, offset(), 0, stride());
		}

		assert r == 0;
		assert rows == 1;
		return new BasicRowLayout(columns, arrayIndex(c), stride());
	}

	@Override
	public MatrixLayout transpose() {
		return new BasicColumnLayout(elements(), offset(), stride());
	}

	@Override
	public MatrixLayout asComplexRowMatrix() {
		asComplex();
		return transpose();
	}

	@Override
	public MatrixLayout asComplexColumnMatrix() {
		asComplex();
		return this;
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return this;
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return transpose();
	}
}
