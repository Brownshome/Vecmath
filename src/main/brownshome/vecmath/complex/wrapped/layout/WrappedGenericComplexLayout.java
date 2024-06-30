package brownshome.vecmath.complex.wrapped.layout;

import brownshome.vecmath.complex.generic.GenericComplexLayout;
import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.rotation.layout.Rot2Layout;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

public record WrappedGenericComplexLayout(GenericComplexLayout delegate) implements ComplexLayout, Rot2Layout, ComplexNLayout, MatrixLayout, VecNLayout {
	@Override
	public int complexElements() {
		return delegate.complexElements();
	}

	@Override
	public boolean isComponentPacked() {
		return delegate.isComponentPacked();
	}

	@Override
	public ComplexLayout asComplex() {
		assert complexElements() == 1;
		return delegate instanceof ComplexLayout layout ? layout : this;
	}

	@Override
	public int arrayIndex(int index) {
		assert complexElements() == 1;
		assert index < elements();
		return index == 0 ? delegate.realArrayIndex(0) : delegate.imaginaryArrayIndex(0);
	}

	@Override
	public ComplexNLayout asComplexN() {
		return delegate instanceof ComplexNLayout layout ? layout : this;
	}

	@Override
	public ComplexLayout asComplex(int index) {
		return new SubGenericComplexLayout(delegate, index, 1);
	}

	@Override
	public int realArrayIndex(int index) {
		return delegate.realArrayIndex(index);
	}

	@Override
	public int imaginaryArrayIndex(int index) {
		return delegate.imaginaryArrayIndex(index);
	}

	@Override
	public boolean isElementPacked() {
		return delegate.isElementPacked();
	}

	@Override
	public MatrixLayout asComplexRowMatrix() {
		return transpose();
	}

	@Override
	public MatrixLayout asComplexColumnMatrix() {
		return this;
	}

	@Override
	public int arrayIndex(int row, int column) {
		assert column < columns();
		return column == 0 ? delegate.realArrayIndex(row) : delegate.imaginaryArrayIndex(row);
	}

	@Override
	public int rows() {
		return delegate.complexElements();
	}

	@Override
	public int columns() {
		return 2;
	}

	@Override
	public boolean isRowPacked() {
		return delegate.isElementPacked();
	}

	@Override
	public boolean isColumnPacked() {
		return delegate.isComponentPacked();
	}

	@Override
	public VecNLayout asVecN() {
		return this;
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return transpose();
	}

	@Override
	public Rot2Layout asRot2() {
		return this;
	}

	@Override
	public Vec2Layout asVec2() {
		return asComplex();
	}

	@Override
	public Vec3Layout asVec3() {
		throw new UnsupportedOperationException("A complex vector layout cannot be converted to a Vec3 layout");
	}

	@Override
	public Vec4Layout asVec4() {
		throw new UnsupportedOperationException("A complex vector layout cannot be converted to a Vec4 layout");
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return this;
	}

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
	public int elements() {
		assert complexElements() == 1;
		return 2;
	}
}
