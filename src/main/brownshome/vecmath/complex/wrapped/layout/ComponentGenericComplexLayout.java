package brownshome.vecmath.complex.wrapped.layout;

import brownshome.vecmath.basic.layout.BasicColumnLayout;
import brownshome.vecmath.complex.generic.GenericComplexLayout;
import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.rotation.layout.Rot2Layout;
import brownshome.vecmath.rotation.layout.Rot3Layout;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * A layout targeting one component of a complex layout
 */
public abstract class ComponentGenericComplexLayout implements
		ComplexLayout,
		ComplexNLayout,
		Rot2Layout,
		Vec3Layout,
		Rot3Layout,
		MatrixLayout,
		VecNLayout {
	private final GenericComplexLayout delegate;

	protected ComponentGenericComplexLayout(GenericComplexLayout delegate) {
		this.delegate = delegate;
	}

	protected GenericComplexLayout delegate() {
		return delegate;
	}

	@Override
	public int complexElements() {
		asComplex();
		return ComplexLayout.super.complexElements();
	}

	@Override
	public VecNLayout real() {
		asComplex();
		return row(0);
	}

	@Override
	public VecNLayout imaginary() {
		asComplex();
		return row(1);
	}

	@Override
	public ComplexLayout asComplex(int index) {
		assert index == 0;
		return asComplex();
	}

	@Override
	public ComplexLayout asComplex() {
		assert elements() == 2;
		return this;
	}

	@Override
	public ComplexNLayout asComplexN() {
		asComplex();
		return this;
	}

	@Override
	public int realArrayIndex(int index) {
		assert index == 0;
		asComplex();
		return arrayIndex(0);
	}

	@Override
	public int imaginaryArrayIndex(int index) {
		assert index == 0;
		asComplex();
		return arrayIndex(1);
	}

	@Override
	public boolean isElementPacked() {
		asComplex();
		return isPacked();
	}

	@Override
	public MatrixLayout asComplexRowMatrix() {
		asComplex();
		return this;
	}

	@Override
	public MatrixLayout asComplexColumnMatrix() {
		return asComplexRowMatrix().transpose();
	}

	@Override
	public int arrayIndex(int row, int column) {
		assert column == 0;
		return arrayIndex(row);
	}

	@Override
	public int rows() {
		return elements();
	}

	@Override
	public int columns() {
		return 1;
	}

	@Override
	public boolean isRowPacked() {
		return true;
	}

	@Override
	public boolean isColumnPacked() {
		return isPacked();
	}

	@Override
	public MatrixLayout permuteByColumn(int... columns) {
		assert columns.length == 1;
		assert columns[0] == 0;
		return this;
	}

	@Override
	public VecNLayout row(int r) {
		return new BasicColumnLayout(1, arrayIndex(r), 0);
	}

	@Override
	public VecNLayout column(int c) {
		assert c == 0;
		return this;
	}

	@Override
	public Rot2Layout asRot2() {
		asVec2();
		return this;
	}

	@Override
	public Rot3Layout asRot3() {
		asVec4();
		return this;
	}

	@Override
	public Vec2Layout asVec2() {
		assert elements() == 2;
		return this;
	}

	@Override
	public Vec3Layout asVec3() {
		assert elements() == 3;
		return this;
	}

	@Override
	public Vec4Layout asVec4() {
		assert elements() == 4;
		return this;
	}

	@Override
	public MatrixLayout asRowMatrix() {
		return transpose();
	}

	@Override
	public MatrixLayout asColumnMatrix() {
		return this;
	}

	@Override
	public VecNLayout asVecN() {
		return this;
	}

	@Override
	public int elements() {
		return delegate.complexElements();
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
		return false;
	}

	@Override
	public boolean isPacked() {
		return false;
	}

	public static final class Real extends ComponentGenericComplexLayout {
		public Real(GenericComplexLayout delegate) {
			super(delegate);
		}

		@Override
		public int arrayIndex(int index) {
			return delegate().realArrayIndex(index);
		}
	}

	public static final class Imaginary extends ComponentGenericComplexLayout {
		public Imaginary(GenericComplexLayout delegate) {
			super(delegate);
		}

		@Override
		public int arrayIndex(int index) {
			return delegate().imaginaryArrayIndex(index);
		}
	}
}
