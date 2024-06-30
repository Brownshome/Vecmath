package brownshome.vecmath.basic.layout;

public final class BasicMatrixLayout extends BasicLayout {
	private final int rows, columns;
	private final int rowStride, columnStride;

	public BasicMatrixLayout(int rows, int columns, int offset, int rowStride, int columnStride) {
		super(rows, columns, offset, rowStride, columnStride);
		this.rows = rows;
		this.columns = columns;
		this.rowStride = rowStride;
		this.columnStride = columnStride;
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
	public int rowStride() {
		return rowStride;
	}

	@Override
	public int columnStride() {
		return columnStride;
	}
}
