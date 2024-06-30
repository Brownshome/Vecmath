package brownshome.vecmath.vector.wrapped.layout;

import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.rotation.layout.Rot2Layout;
import brownshome.vecmath.rotation.layout.Rot3Layout;
import brownshome.vecmath.vector.generic.GenericVecLayout;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

public record WrappedVecLayout(GenericVecLayout delegate) implements ComplexLayout, ComplexNLayout, Rot2Layout, Vec3Layout, Rot3Layout, VecNLayout, MatrixLayout {
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
		return delegate.start();
	}

	@Override
	public int rows() {
		return 1;
	}

	@Override
	public int columns() {
		return delegate.elements();
	}

	@Override
	public int elements() {
		return delegate.elements();
	}

	@Override
	public int complexElements() {
		asComplexN();
		return 1;
	}

	@Override
	public int arrayIndex(int index) {
		return delegate.arrayIndex(index);
	}

	@Override
	public int arrayIndex(int row, int column) {
		assert row == 0;
		return delegate.arrayIndex(column);
	}

	@Override
	public int realArrayIndex(int index) {
		assert index == 0;
		asComplexN();
		return delegate.arrayIndex(0);
	}

	@Override
	public int imaginaryArrayIndex(int index) {
		assert index == 0;
		asComplexN();
		return delegate.arrayIndex(1);
	}

	@Override
	public boolean isComponentPacked() {
		asComplexN();
		return true;
	}

	@Override
	public boolean isElementPacked() {
		asComplexN();
		return delegate.isPacked();
	}

	@Override
	public boolean isRowPacked() {
		return delegate.isPacked();
	}

	@Override
	public boolean isColumnPacked() {
		return true;
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
	public ComplexLayout asComplex() {
		asComplexN();
		return this;
	}

	@Override
	public ComplexNLayout asComplexN() {
		asVec2();
		return this;
	}

	@Override
	public ComplexLayout asComplex(int index) {
		assert index == 0;
		asComplexN();
		return this;
	}

	@Override
	public MatrixLayout asComplexRowMatrix() {
		asComplexN();
		return transpose();
	}

	@Override
	public MatrixLayout asComplexColumnMatrix() {
		asComplexN();
		return this;
	}

	@Override
	public VecNLayout asVecN() {
		return delegate instanceof VecNLayout layout ? layout : this;
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return this;
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return transpose();
	}

	@Override
	public Rot2Layout asRot2() {
		return delegate instanceof Rot2Layout layout ? layout : this;
	}

	@Override
	public Rot3Layout asRot3() {
		return delegate instanceof Rot3Layout layout ? layout : this;
	}

	@Override
	public Vec2Layout asVec2() {
		assert delegate.elements() == 2;
		return delegate instanceof Vec2Layout layout ? layout : this;
	}

	@Override
	public Vec3Layout asVec3() {
		assert delegate.elements() == 3;
		return delegate instanceof Vec3Layout layout ? layout : this;
	}

	@Override
	public Vec4Layout asVec4() {
		assert delegate.elements() == 4;
		return delegate instanceof Vec4Layout layout ? layout : this;
	}
}
