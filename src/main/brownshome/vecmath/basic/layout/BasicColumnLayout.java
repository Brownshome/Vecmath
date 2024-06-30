package brownshome.vecmath.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;

public final class BasicColumnLayout extends BasicLinearLayout {
	public BasicColumnLayout(int size, int offset, int stride) {
		super(size, offset, stride);
	}

	@Override
	public int rows() {
		return elements();
	}

	@Override
	public int columns() {
		return 1;
	}

	@Override
	public int rowStride() {
		return stride();
	}

	@Override
	public int columnStride() {
		return 0;
	}

	@Override
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		if (columns == 0) {
			assert rows < rows();
			MatrixLayout.of(rows, columns, offset(), stride(), 0);
		}

		assert c == 0;
		assert columns == 1;
		return new BasicColumnLayout(rows, arrayIndex(r), stride());
	}

	@Override
	public MatrixLayout transpose() {
		return new BasicRowLayout(elements(), offset(), stride());
	}

	@Override
	public MatrixLayout asComplexRowMatrix() {
		asComplex();
		return this;
	}

	@Override
	public MatrixLayout asComplexColumnMatrix() {
		asComplex();
		return transpose();
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return transpose();
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return this;
	}
}
