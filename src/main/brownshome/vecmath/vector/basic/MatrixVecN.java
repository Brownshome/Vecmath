package brownshome.vecmath.vector.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;

/**
 * A MVecN wrapping a column matrix
 */
public class MatrixVecN extends BasicVec<VecN> implements MVecN {
	private final Matrix matrix;
	private final int column;

	public MatrixVecN(Matrix matrix, int column) {
		assert matrix != null;
		assert column < matrix.columns();

		this.matrix = matrix;
		this.column = column;
	}

	@Override
	public double get(int i) {
		assert i < size();

		return matrix.get(i, column);
	}

	@Override
	public void set(double value, int index) {
		assert index < size();

		((MMatrix) matrix).set(value, index, column);
	}

	@Override
	public int size() {
		return matrix.rows();
	}

	@Override
	public MVecN move() {
		var movedMatrix = matrix.move();
		if (movedMatrix == matrix) {
			return this;
		}

		return new MatrixVecN(movedMatrix, column);
	}
}
