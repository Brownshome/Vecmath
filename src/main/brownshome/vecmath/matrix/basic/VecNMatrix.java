package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.VecN;

public class VecNMatrix implements Matrix {
	private final VecN vec;

	public VecNMatrix(VecN vec) {
		this.vec = vec;
	}

	protected VecN vec() {
		return vec;
	}

	@Override
	public int rows() {
		return vec.size();
	}

	@Override
	public int columns() {
		return 1;
	}

	@Override
	public boolean isRowOptimal() {
		return true;
	}

	@Override
	public double get(int row, int column) {
		assert row >= 0;
		assert row < vec.size();

		return vec.get(row);
	}

	@Override
	public VecN column(int c) {
		assert c == 0;

		return vec;
	}

	@Override
	public MMatrix move() {
		return new VecNMMatrix(vec.move());
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
