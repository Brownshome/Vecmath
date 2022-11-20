package brownshome.vecmath.vector.basic.layout;

import brownshome.vecmath.vector.layout.Vec3Layout;

public final class BasicVec3Layout extends BasicVecLayout implements Vec3Layout {
	public BasicVec3Layout(int offset, int stride) {
		super(offset, stride);

		assert start() >= 0;
		assert end() >= 0;
	}

	@Override
	int elements() {
		return 3;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BasicVec3Layout otherLayout
				&& super.equals(otherLayout);
	}
}
