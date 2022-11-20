package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.array.ArrayVecN;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * An array based vector of unknown length
 */
public final class BasicArrayVecN extends BasicArrayVec<VecNLayout, VecN> implements ArrayVecN {
	public BasicArrayVecN(double[] array, VecNLayout layout) {
		super(array, layout);
	}

	private BasicArrayVecN(BasicArrayVecN copy) {
		super(copy);
	}

	@Override
	public BasicArrayVecN copy() {
		return new BasicArrayVecN(this);
	}
}
