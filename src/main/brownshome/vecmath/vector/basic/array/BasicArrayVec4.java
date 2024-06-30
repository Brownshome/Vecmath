package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.rotation.array.ArrayRot3;
import brownshome.vecmath.vector.Vec4;
import brownshome.vecmath.vector.layout.Vec4Layout;

public final class BasicArrayVec4 extends BasicArrayVec<Vec4Layout, Vec4> implements ArrayRot3 {
	public BasicArrayVec4(double[] array, Vec4Layout layout) {
		super(array, layout);
	}

	private BasicArrayVec4(BasicArrayVec4 copy) {
		super(copy);
	}

	@Override
	public BasicArrayVec4 copy() {
		return new BasicArrayVec4(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vec4 vec && exactEquals(vec);
	}
}
