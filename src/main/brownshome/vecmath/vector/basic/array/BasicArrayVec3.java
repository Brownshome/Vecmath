package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.array.ArrayVec3;
import brownshome.vecmath.vector.layout.Vec3Layout;

public final class BasicArrayVec3 extends BasicArrayVec<Vec3Layout, Vec3> implements ArrayVec3 {
	public BasicArrayVec3(double[] array, Vec3Layout layout) {
		super(array, layout);
	}

	private BasicArrayVec3(BasicArrayVec3 copy) {
		super(copy);
	}

	@Override
	public BasicArrayVec3 copy() {
		return new BasicArrayVec3(this);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vec3 vec && exactEquals(vec);
	}
}
