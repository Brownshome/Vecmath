package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.VecN;

public class SubMatrix implements Matrix {
	private final Matrix delegate;
	private final int startRow, startColumn;
	private final int rows, columns;

	public SubMatrix(Matrix delegate, int startRow, int startColumn, int rows, int columns) {
		assert delegate != null;
		assert rows >= 0;
		assert startRow >= 0;
		assert startRow + rows <= delegate.rows();
		assert columns >= 0;
		assert startColumn >= 0;
		assert startColumn + columns <= delegate.columns();

		this.delegate = delegate;
		this.startRow = startRow;
		this.startColumn = startColumn;
		this.rows = rows;
		this.columns = columns;
	}

	protected Matrix delegate() {
		return delegate;
	}

	protected final int startRow() {
		return startRow;
	}

	protected final int startColumn() {
		return startColumn;
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
	public boolean isRowOptimal() {
		return delegate.isRowOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return delegate.isColumnOptimal();
	}

	@Override
	public double get(int row, int column) {
		assert row < rows;
		assert column < columns;

		return delegate.get(row + startRow, column + startColumn);
	}

	@Override
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		assert r >= 0;
		assert c >= 0;
		assert r + rows <= this.rows;
		assert c + columns <= this.columns;

		return delegate.subMatrix(r + startRow, c + startColumn, rows, columns);
	}

	@Override
	public VecN row(int r) {
		return columns == delegate.columns() ? delegate.row(r + startRow) : Matrix.super.row(r);
	}

	@Override
	public VecN column(int c) {
		return rows == delegate.rows() ? delegate.column(c + startColumn) : Matrix.super.column(c);
	}

	@Override
	public Matrix transpose() {
		return new SubMatrix(delegate.transpose(), startColumn, startRow, columns, rows);
	}

	@Override
	public Matrix permuteByRow(int... rows) {
		var totalPermutation = new int[delegate.rows()];
		for (int r = 0; r < startRow; r++) {
			totalPermutation[r] = r;
		}

		for (int r = 0; r < this.rows; r++) {
			totalPermutation[r + startRow] = rows[r] + startRow;
		}

		for (int r = startRow + this.rows; r < delegate.rows(); r++) {
			totalPermutation[r] = r;
		}

		return delegate
				.permuteByRow(totalPermutation)
				.subMatrix(startRow, startColumn, this.rows, this.columns);
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		var totalPermutation = new int[delegate.columns()];
		for (int c = 0; c < startColumn; c++) {
			totalPermutation[c] = c;
		}

		for (int c = 0; c < this.columns; c++) {
			totalPermutation[c + startColumn] = columns[c] + startColumn;
		}

		for (int c = startColumn + this.columns; c < delegate.columns(); c++) {
			totalPermutation[c] = c;
		}

		return delegate
				.permuteByColumn(totalPermutation)
				.subMatrix(startRow, startColumn, this.rows, this.columns);
	}

	@Override
	public MMatrix move() {
		return delegate.move().subMatrix(startRow, startColumn, rows, columns);
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
