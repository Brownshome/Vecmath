package brownshome.vecmath.vector.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

public record WrappedVecLayout(VecNLayout delegate) implements Vec2Layout, Vec3Layout, Vec4Layout {
	@Override
	public int start() {
		return delegate.start();
	}

	@Override
	public int end() {
		return delegate.end();
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isContinuous() {
		return delegate.isContinuous();
	}

	@Override
	public boolean isPacked() {
		return delegate.isPacked();
	}

	@Override
	public int arrayIndex(int index) {
		return delegate.arrayIndex(index);
	}

	@Override
	public VecNLayout asVecNLayout() {
		return delegate;
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return delegate.asRowMatrix();
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return delegate.asColumnMatrix();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
