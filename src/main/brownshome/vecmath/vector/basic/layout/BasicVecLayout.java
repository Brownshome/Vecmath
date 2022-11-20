package brownshome.vecmath.vector.basic.layout;

import java.util.Objects;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.generic.GenericVecLayout;

public abstract class BasicVecLayout implements GenericVecLayout {
	private final int offset;
	private final int stride;

	BasicVecLayout(int offset, int stride) {
		assert stride != 0;

		this.offset = offset;
		this.stride = stride;
	}

	abstract int elements();

	@Override
	public final int start() {
		assert elements() != 0;

		return Math.min(offset, offset + (elements() - 1) * stride);
	}

	@Override
	public final int end() {
		assert elements() != 0;

		return Math.max(offset, offset + (elements() - 1) * stride) + 1;
	}

	@Override
	public final int size() {
		if (elements() == 0) {
			return 0;
		}

		return (elements() - 1) * Math.abs(stride) + 1;
	}

	@Override
	public final boolean isContinuous() {
		return elements() <= 1 || Math.abs(stride) == 1;
	}

	@Override
	public final boolean isPacked() {
		return elements() == 0
				|| (stride == 1 || elements() == 1) && offset == 0
				|| stride == -1 && offset == elements() - 1;
	}

	@Override
	public final int arrayIndex(int index) {
		assert elements() != 0;
		assert index >= 0;
		assert index < elements();

		return offset + stride * index;
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return MatrixLayout.of(1, elements(), offset, 0, stride);
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return MatrixLayout.of(elements(), 1, offset, stride, 0);
	}

	@Override
	public int hashCode() {
		return Objects.hash(offset, stride);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BasicVecLayout otherLayout
				&& offset == otherLayout.offset
				&& stride == otherLayout.stride;
	}
}
