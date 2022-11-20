package brownshome.vecmath.vector.basic.layout;

import brownshome.vecmath.vector.layout.Vec4Layout;

public final class BasicVec4Layout extends BasicVecLayout implements Vec4Layout {
	public BasicVec4Layout(int offset, int stride) {
		super(offset, stride);

		assert start() >= 0;
		assert end() >= 0;
	}

	@Override
	int elements() {
		return 4;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BasicVec4Layout otherLayout
				&& super.equals(otherLayout);
	}
}
