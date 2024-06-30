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

public record SubGenericComplexLayout(GenericComplexLayout delegate, int startElement, int complexElements) implements ComplexLayout, Rot2Layout, ComplexNLayout, MatrixLayout, VecNLayout {
	public SubGenericComplexLayout {
		assert startElement <= delegate.complexElements();
		assert startElement >= 0;
		assert complexElements + startElement <= delegate.complexElements();
	}

	@Override
	public boolean isComponentPacked() {
		return complexElements == 1 || delegate.isComponentPacked();
	}

	@Override
	public ComplexLayout asComplex() {
		assert complexElements == 1;
		return this;
	}

	@Override
	public int arrayIndex(int index) {
		assert complexElements == 1;
		assert index < elements();
		return index == 0 ? delegate.realArrayIndex(startElement) : delegate.imaginaryArrayIndex(startElement);
	}

	@Override
	public ComplexNLayout asComplexN() {
		return this;
	}

	@Override
	public ComplexLayout asComplex(int index) {
		assert index >= 0;
		return new SubGenericComplexLayout(delegate, startElement + index, 1);
	}

	@Override
	public int realArrayIndex(int index) {
		assert index >= 0;
		return delegate.realArrayIndex(index + startElement);
	}

	@Override
	public int imaginaryArrayIndex(int index) {
		assert index >= 0;
		return delegate.imaginaryArrayIndex(index + startElement);
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
		assert row >= 0;
		return column == 0 ? delegate.realArrayIndex(row + startElement) : delegate.imaginaryArrayIndex(row + startElement);
	}

	@Override
	public int rows() {
		return complexElements;
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
		return complexElements == 1 || delegate.isComponentPacked();
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

	/*
	 * @note james.brown 21 July 2024
	 * This is the best we can do without resorting to more complex logic
	 */

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
		return size() == complexElements * 2;
	}

	@Override
	public boolean isPacked() {
		return isContinuous() && start() == 0;
	}

	@Override
	public int elements() {
		assert complexElements() == 1;
		return 2;
	}
}
