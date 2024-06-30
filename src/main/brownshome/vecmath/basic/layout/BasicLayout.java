package brownshome.vecmath.basic.layout;

import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.rotation.layout.Rot2Layout;
import brownshome.vecmath.rotation.layout.Rot3Layout;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

public abstract class BasicLayout implements
		ComplexLayout,
		ComplexNLayout,
		Rot2Layout,
		Vec3Layout,
		Rot3Layout,
		VecNLayout,
		MatrixLayout {
	private final int offset;

	/**
	 * Creates a basic matrix layout-like
	 *
	 * @param rows         the number of rows in this matrix-like layout
	 * @param columns      the number of columns in this matrix-like layout
	 * @param offset       the offset from the start of the array that this matrix-like layout will use
	 * @param rowStride    the difference in indices that results from increasing the row index.
	 *                     This may be negative (or zero if there are no rows)
	 * @param columnStride the difference in indices that results from increasing the column index.
	 *                     This may be negative (or zero if there are no columns)
	 */
	protected BasicLayout(int rows, int columns, int offset, int rowStride, int columnStride) {
		assert rows > 0;
		assert columns > 0;
		assert noIndexesOverlap(rows, columns, rowStride, columnStride, offset);

		this.offset = offset;
	}

	private static boolean noIndexesOverlap(int rows, int columns, int rowStride, int columnStride, int offset) {
		int r = rowStride < 0 ? 0 : (rows - 1);
		int c = columnStride < 0 ? 0 : (columns - 1);

		boolean[] v = new boolean[offset + rowStride * r + columnStride * c + 1];

		for (r = 0; r < rows; r++) {
			for (c = 0; c < columns; c++) {
				int i = r * rowStride + c * columnStride + offset;
				if (i >= v.length || v[i]) {
					return false;
				}

				v[i] = true;
			}
		}

		return true;
	}

	public abstract int rows();

	public abstract int columns();

	public final int offset() {
		return offset;
	}

	public abstract int rowStride();

	public abstract int columnStride();

	@Override
	public int start() {
		return offset() + Math.min(0, rowStride()) * (rows() - 1) + Math.min(0, columnStride()) * (columns() - 1);
	}

	@Override
	public int end() {
		return offset() + Math.max(0, rowStride()) * (rows() - 1) + Math.max(0, columnStride()) * (columns() - 1) + 1;
	}

	@Override
	public int size() {
		return Math.abs(rowStride()) * (rows() - 1) + Math.abs(columnStride()) * (columns() - 1) + 1;
	}

	@Override
	public int elements() {
		asVecN();
		return columns();
	}

	@Override
	public int complexElements() {
		asComplexN();
		return rows();
	}

	@Override
	public int arrayIndex(int row, int column) {
		assert row < rows();
		assert column < columns();
		assert row >= 0;
		assert column >= 0;

		return offset() + row * rowStride() + column * columnStride();
	}

	@Override
	public int arrayIndex(int index) {
		asVecN();
		return arrayIndex(0, index);
	}

	@Override
	public int realArrayIndex(int index) {
		asComplexN();
		return arrayIndex(index, 0);
	}

	@Override
	public int imaginaryArrayIndex(int index) {
		asComplexN();
		return arrayIndex(index, 1);
	}

	@Override
	public boolean isContinuous() {
		return size() == rows() * columns();
	}

	@Override
	public boolean isPacked() {
		return end() == rows() * columns();
	}

	@Override
	public boolean isRowPacked() {
		return columns() == 1 || Math.abs(columnStride()) == 1;
	}

	@Override
	public boolean isColumnPacked() {
		return rows() == 1 || Math.abs(rowStride()) == 1;
	}

	@Override
	public boolean isComponentPacked() {
		return isColumnPacked();
	}

	@Override
	public boolean isElementPacked() {
		asComplexN();
		return isRowPacked();
	}

	@Override
	public MatrixLayout subLayout(int r, int c, int rows, int columns) {
		assert r < rows();
		assert c < columns();
		assert r >= 0;
		assert c >= 0;
		assert r + rows <= rows();
		assert c + columns <= columns();
		assert rows >= 0;
		assert columns >= 0;

		if (rows == 0 || columns == 0) {
			return MatrixLayout.super.subLayout(r, c, rows, columns);
		}

		return MatrixLayout.of(rows, columns, arrayIndex(r, c), rowStride(), columnStride());
	}

	@Override
	public MatrixLayout transpose() {
		return MatrixLayout.of(columns(), rows(), offset(), columnStride(), rowStride());
	}

	@Override
	public VecNLayout row(int r) {
		assert r < rows();
		assert r >= 0;

		return VecNLayout.of(columns(), arrayIndex(r, 0), columnStride());
	}

	@Override
	public VecNLayout column(int c) {
		assert c < columns();
		assert c >= 0;

		return VecNLayout.of(rows(), arrayIndex(0, c), rowStride());
	}

	@Override
	public ComplexLayout asComplex() {
		assert complexElements() == 1;
		return this;
	}

	@Override
	public ComplexLayout asComplex(int index) {
		asComplexN();
		return ComplexLayout.of(realArrayIndex(index), columnStride());
	}

	@Override
	public ComplexNLayout asComplexN() {
		assert columns() == 2;
		return this;
	}

	@Override
	public MatrixLayout asComplexRowMatrix() {
		asComplexN();
		return transpose();
	}

	@Override
	public MatrixLayout asComplexColumnMatrix() {
		asComplexN();
		return this;
	}

	@Override
	public MatrixLayout asRowMatrix() {
		asVecN();
		return this;
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		asVecN();
		return transpose();
	}

	@Override
	public Rot2Layout asRot2() {
		asVec2();
		return this;
	}

	@Override
	public Vec2Layout asVec2() {
		assert elements() == 2;
		return this;
	}

	@Override
	public Vec3Layout asVec3() {
		assert elements() == 3;
		return this;
	}

	@Override
	public Vec4Layout asVec4() {
		assert elements() == 4;
		return this;
	}

	@Override
	public VecNLayout asVecN() {
		assert rows() == 1;
		return this;
	}
}
