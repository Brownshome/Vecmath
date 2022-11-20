package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.MatrixWithFastMultiply;
import brownshome.vecmath.vector.VecN;

public class JoinedMatrix implements MatrixWithFastMultiply {
	private final Matrix top, bottom;

	public JoinedMatrix(Matrix top, Matrix bottom) {
		assert top.columns() == bottom.columns();

		this.top = top;
		this.bottom = bottom;
	}

	protected Matrix top() {
		return top;
	}

	protected Matrix bottom() {
		return bottom;
	}

	@Override
	public Matrix add(Matrix e) {
		return new JoinedMatrix(
				top.add(e.subMatrix(0, 0, top.rows(), top.columns())),
				bottom.add(e.subMatrix(top.rows(), 0, bottom.rows(), bottom.columns()))
		);
	}

	@Override
	public Matrix subtract(Matrix e) {
		return new JoinedMatrix(
				top.subtract(e.subMatrix(0, 0, top.rows(), top.columns())),
				bottom.subtract(e.subMatrix(top.rows(), 0, bottom.rows(), bottom.columns()))
		);
	}

	@Override
	public Matrix scale(double scale) {
		return new JoinedMatrix(
				top.scale(scale),
				bottom.scale(scale)
		);
	}

	@Override
	public Matrix scale(Matrix scale) {
		return new JoinedMatrix(
				top.scale(scale.subMatrix(0, 0, top.rows(), top.columns())),
				bottom.scale(scale.subMatrix(top.rows(), 0, bottom.rows(), bottom.columns()))
		);
	}

	@Override
	public Matrix scaleAdd(Matrix e, double scale) {
		return new JoinedMatrix(
				top.scaleAdd(e.subMatrix(0, 0, top.rows(), top.columns()), scale),
				bottom.scaleAdd(e.subMatrix(top.rows(), 0, bottom.rows(), bottom.columns()), scale)
		);
	}

	@Override
	public Matrix negated() {
		return new JoinedMatrix(
				top.negated(),
				bottom.negated()
		);
	}

	@Override
	public Matrix interpolated(Matrix other, double t) {
		return new JoinedMatrix(
				top.interpolated(other.subMatrix(0, 0, top.rows(), top.columns()), t),
				bottom.interpolated(other.subMatrix(top.rows(), 0, bottom.rows(), bottom.columns()), t)
		);
	}

	@Override
	public int rows() {
		return top.rows() + bottom.rows();
	}

	@Override
	public int columns() {
		return top.columns();
	}

	@Override
	public boolean isRowOptimal() {
		return top.isRowOptimal() && bottom.isRowOptimal();
	}

	@Override
	public boolean isColumnOptimal() {
		return top.isColumnOptimal() && bottom.isColumnOptimal();
	}

	@Override
	public double get(int row, int column) {
		return row < top.rows() ? top.get(row, column) : bottom.get(row - top.rows(), column);
	}

	@Override
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		if (r < top.rows() && r + rows <= top.rows()) {
			return top.subMatrix(r, c, rows, columns);
		}

		if (r >= top.rows()) {
			return top.subMatrix(r - top.rows(), c, rows, columns);
		}

		return new JoinedMatrix(
			top.subMatrix(r, c, top.rows() - r, columns),
			bottom.subMatrix(0, c, rows - top.rows(), columns)
		);
	}

	@Override
	public VecN row(int r) {
		return r < top.rows() ?
				top.row(r) :
				bottom.row(r - top.rows());
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		return new JoinedMatrix(
				top.permuteByColumn(columns),
				bottom.permuteByColumn(columns)
		);
	}

	@Override
	public Matrix multiply(Matrix other) {
		return new JoinedMatrix(
				top.multiply(other),
				bottom.multiply(other)
		);
	}

	@Override
	public Matrix multiplyLeft(Matrix left) {
		assert left.columns() == rows();

		var topResult = top.multiply(left.subMatrix(0, 0, left.rows(), top.rows()));
		var bottomResult = bottom.multiply(left.subMatrix(0, top.rows(), left.rows(), bottom.rows()));
		return topResult.add(bottomResult);
	}

	@Override
	public Matrix divideRight(Matrix divider) {
		return new JoinedMatrix(
				top.divideRight(divider),
				bottom.divideRight(divider)
		);
	}

	@Override
	public boolean exactEquals(Matrix other) {
		return top.exactEquals(other.subMatrix(0, 0, top.rows(), top.columns())) &&
				bottom.exactEquals(other.subMatrix(top.rows(), 0, bottom.rows(), bottom.columns()));
	}

	@Override
	public VecN asRowVec() {
		assert rows() == 1;
		return top.rows() == 1 ? top.asRowVec() : bottom.asRowVec();
	}

	@Override
	public MMatrix move() {
		return new JoinedMMatrix(
				top.move(),
				bottom.move()
		);
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
