package brownshome.vecmath.vector.basic.layout;

import brownshome.vecmath.vector.layout.VecNLayout;

public final class BasicVecNLayout extends BasicVecLayout implements VecNLayout {
	private final int size;

	public BasicVecNLayout(int size, int offset, int stride) {
		super(offset, stride);

		this.size = size;

		assert start() >= 0;
		assert end() >= 0;
	}

	@Override
	public int elements() {
		return size;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BasicVecNLayout otherLayout
				&& size == otherLayout.size
				&& super.equals(otherLayout);
	}
}
