package brownshome.vecmath.matrix.wrapped.layout;

import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * A matrix layout wrapped to support other layout types
 * @param delegate the matrix layout
 */
public record WrappedMatrixLayout(MatrixLayout delegate) implements VecNLayout, Vec2Layout, Vec3Layout, Vec4Layout, ComplexNLayout, ComplexLayout {
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
	public int elements() {
		return delegate.columns();
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
	public boolean isElementPacked() {
		return delegate.isRowPacked();
	}

	@Override
	public boolean isComponentPacked() {
		return delegate.isColumnPacked();
	}

	@Override
	public int arrayIndex(int index) {
		return delegate.arrayIndex(index, 0);
	}

	@Override
	public int realArrayIndex(int index) {
		return arrayIndex(index);
	}

	@Override
	public int imaginaryArrayIndex(int index) {
		return delegate.arrayIndex(index, 1);
	}

	@Override
	public int complexElements() {
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
	public ComplexNLayout asComplexN() {
		assert delegate.columns() == 2;
		return this;
	}

	@Override
	public ComplexLayout asComplex() {
		assert delegate.columns() == 2;
		assert complexElements() == 1;
		return this;
	}

	@Override
	public VecNLayout asVecN() {
		assert delegate.columns() == 1;
		return this;
	}

	@Override
	public Vec2Layout asVec2() {
		assert delegate.columns() == 1;
		assert complexElements() == 2;
		return this;
	}

	@Override
	public Vec3Layout asVec3() {
		assert delegate.columns() == 1;
		assert complexElements() == 3;
		return this;
	}

	@Override
	public Vec4Layout asVec4() {
		assert delegate.columns() == 1;
		assert complexElements() == 4;
		return this;
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
