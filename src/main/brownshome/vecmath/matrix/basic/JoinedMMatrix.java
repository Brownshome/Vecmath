package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;

public class JoinedMMatrix extends JoinedMatrix implements MMatrix {
	public JoinedMMatrix(MMatrix top, MMatrix bottom) {
		super(top, bottom);
	}

	@Override
	protected MMatrix top() {
		return (MMatrix) super.top();
	}

	@Override
	protected MMatrix bottom() {
		return (MMatrix) super.bottom();
	}

	@Override
	public void set(double value, int row, int column) {
		if (row < top().rows()) {
			top().set(value, row, column);
		} else {
			bottom().set(value, row - top().rows(), column);
		}
	}

	@Override
	public void set(Matrix matrix) {
		top().set(matrix.subMatrix(0, 0, top().rows(), top().columns()));
		bottom().set(matrix.subMatrix(top().rows(), 0, bottom().rows(), bottom().columns()));
	}

	@Override
	public MMatrix subMatrix(int r, int c, int rows, int columns) {
		return super.subMatrix(r, c, rows, columns).move();
	}

	@Override
	public void subtractFromSelf(Matrix e) {
		top().subtractFromSelf(e.subMatrix(0, 0, top().rows(), top().columns()));
		bottom().subtractFromSelf(e.subMatrix(top().rows(), 0, bottom().rows(), bottom().columns()));
	}

	@Override
	public void scaleAddToSelf(Matrix e, double scale) {
		top().scaleAddToSelf(e.subMatrix(0, 0, top().rows(), top().columns()), scale);
		bottom().scaleAddToSelf(e.subMatrix(top().rows(), 0, bottom().rows(), bottom().columns()), scale);
	}

	@Override
	public void setToNegated() {
		top().setToNegated();
		bottom().setToNegated();
	}

	@Override
	public void setToInterpolated(Matrix other, double t) {
		top().setToInterpolated(other.subMatrix(0, 0, top().rows(), top().columns()), t);
		bottom().setToInterpolated(other.subMatrix(top().rows(), 0, bottom().rows(), bottom().columns()), t);
	}

	@Override
	public MVecN row(int r) {
		return (MVecN) super.row(r);
	}

	@Override
	public MMatrix permuteByColumn(int... columns) {
		return super.permuteByColumn(columns).move();
	}

	@Override
	public void addToSelf(Matrix matrix) {
		top().addToSelf(matrix.subMatrix(0, 0, top().rows(), top().columns()));
		bottom().addToSelf(matrix.subMatrix(top().rows(), 0, bottom().rows(), bottom().columns()));
	}

	@Override
	public void scaleSelf(double scale) {
		top().scaleSelf(scale);
		bottom().scaleSelf(scale);
	}

	@Override
	public void scaleSelf(Matrix matrix) {
		top().scaleSelf(matrix.subMatrix(0, 0, top().rows(), top().columns()));
		bottom().scaleSelf(matrix.subMatrix(top().rows(), 0, bottom().rows(), bottom().columns()));
	}

	@Override
	public void permuteSelfByColumns(int... columns) {
		top().permuteSelfByColumns(columns);
		bottom().permuteSelfByColumns(columns);
	}

	@Override
	public void multiplyRightSelf(Matrix right) {
		top().multiplyRightSelf(right);
		bottom().multiplyRightSelf(right);
	}

	@Override
	public void multiplyLeftSelf(Matrix left) {
		var topResult = top().multiply(left.subMatrix(0, 0, left.rows(), top().rows()));
		var bottomResult = bottom().multiply(left.subMatrix(0, left.rows(), left.rows(), bottom().rows()));
		addToSelf(topResult);
		addToSelf(bottomResult);
	}

	@Override
	public void divideRightSelf(Matrix divider) {
		top().divideRightSelf(divider);
		bottom().divideRightSelf(divider);
	}

	@Override
	public MVecN asRowVec() {
		return (MVecN) super.asRowVec();
	}

	@Override
	public MMatrix move() {
		return MMatrix.super.move();
	}
}
