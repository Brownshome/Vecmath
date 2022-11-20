package brownshome.vecmath.vector.basic.layout;

import brownshome.vecmath.vector.layout.Vec2Layout;

public final class BasicVec2Layout extends BasicVecLayout implements Vec2Layout {
	public BasicVec2Layout(int offset, int stride) {
		super(offset, stride);

		assert start() >= 0;
		assert end() >= 0;
	}

	@Override
	int elements() {
		return 2;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BasicVec2Layout otherLayout
				&& super.equals(otherLayout);
	}
}
