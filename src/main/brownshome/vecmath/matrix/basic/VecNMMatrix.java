package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;

public class VecNMMatrix extends VecNMatrix implements MMatrix {
	public VecNMMatrix(MVecN vec) {
		super(vec);
	}

	@Override
	protected MVecN vec() {
		return (MVecN) super.vec();
	}

	@Override
	public void setToNegated() {
		MMatrix.super.setToNegated();
	}

	@Override
	public void set(double value, int row, int column) {

	}

	@Override
	public void scaleSelf(double scale) {
		MMatrix.super.scaleSelf(scale);
	}

	@Override
	public MVecN column(int c) {
		return vec();
	}

	@Override
	public MMatrix move() {
		return MMatrix.super.move();
	}
}
