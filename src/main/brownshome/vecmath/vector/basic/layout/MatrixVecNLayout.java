package brownshome.vecmath.vector.basic.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * An arbitrary-size vector layout created from a single-column matrix
 * @param delegate the matrix layout
 */
public record MatrixVecNLayout(MatrixLayout delegate) implements VecNLayout {
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
		return delegate.arrayIndex(index, 0);
	}

	@Override
	public int elements() {
		return delegate.rows();
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return delegate.transpose();
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return delegate;
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
