package brownshome.vecmath.vector.wrapped;

import java.util.PrimitiveIterator;
import java.util.Spliterator;

import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.complex.MComplexN;
import brownshome.vecmath.complex.array.ArrayComplexN;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.DiagonalFactorisation;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.Vec3;
import brownshome.vecmath.vector.Vec4;
import brownshome.vecmath.vector.VecN;

public interface VecNWrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends VecWrapper<VecN, ELEMENT_TYPE> {
	interface ToVec2 extends VecNWrapper<Vec2>, VecWrapper.ToGenericVec<VecN, Vec2>, Vec2 {
		@Override
		default double x() {
			return delegate().get(0);
		}

		@Override
		default double y() {
			return delegate().get(1);
		}

		@Override
		default PrimitiveIterator.OfDouble iterator() {
			return ToGenericVec.super.iterator();
		}

		@Override
		default Spliterator.OfDouble spliterator() {
			return ToGenericVec.super.spliterator();
		}

		@Override
		default VecN asUnknownSize() {
			return delegate();
		}

		@Override
		default Matrix asRow() {
			return ToGenericVec.super.asRow();
		}

		@Override
		default Matrix asColumn() {
			return ToGenericVec.super.asColumn();
		}
	}

	class BasicToVec2 extends BasicWrapper<VecN, Vec2> implements ToVec2 {
		public BasicToVec2(VecN delegate) {
			super(delegate);
			assert delegate.size() == 2;
		}
	}

	interface ToVec3 extends VecNWrapper<Vec3>, VecWrapper.ToGenericVec<VecN, Vec3>, Vec3 {
		@Override
		default double x() {
			return delegate().get(0);
		}

		@Override
		default double y() {
			return delegate().get(1);
		}

		@Override
		default double z() {
			return delegate().get(2);
		}

		@Override
		default PrimitiveIterator.OfDouble iterator() {
			return ToGenericVec.super.iterator();
		}

		@Override
		default Spliterator.OfDouble spliterator() {
			return ToGenericVec.super.spliterator();
		}

		@Override
		default VecN asUnknownSize() {
			return delegate();
		}

		@Override
		default Matrix asRow() {
			return ToGenericVec.super.asRow();
		}

		@Override
		default Matrix asColumn() {
			return ToGenericVec.super.asColumn();
		}
	}

	class BasicToVec3 extends BasicWrapper<VecN, Vec3> implements ToVec3 {
		public BasicToVec3(VecN delegate) {
			super(delegate);
			assert delegate.size() == 3;
		}
	}

	interface ToVec4 extends VecNWrapper<Vec4>, VecWrapper.ToGenericVec<VecN, Vec4>, Vec4 {
		@Override
		default double x() {
			return delegate().get(0);
		}

		@Override
		default double y() {
			return delegate().get(1);
		}

		@Override
		default double z() {
			return delegate().get(2);
		}

		@Override
		default double w() {
			return delegate().get(3);
		}

		@Override
		default PrimitiveIterator.OfDouble iterator() {
			return ToGenericVec.super.iterator();
		}

		@Override
		default Spliterator.OfDouble spliterator() {
			return ToGenericVec.super.spliterator();
		}

		@Override
		default VecN asUnknownSize() {
			return delegate();
		}

		@Override
		default Matrix asRow() {
			return ToGenericVec.super.asRow();
		}

		@Override
		default Matrix asColumn() {
			return ToGenericVec.super.asColumn();
		}
	}

	class BasicToVec4 extends BasicWrapper<VecN, Vec4> implements ToVec4 {
		public BasicToVec4(VecN delegate) {
			super(delegate);
			assert delegate.size() == 4;
		}
	}

	interface ToMatrix extends VecNWrapper<Matrix>, Matrix {
		@Override
		default int rows() {
			return delegate().size();
		}

		@Override
		default double get(int row, int column) {
			assert column == 0;
			return delegate().get(row);
		}

		@Override
		default int columns() {
			return 1;
		}

		@Override
		default boolean isRowOptimal() {
			return true;
		}

		@Override
		default VecN column(int c) {
			assert c == 0;
			return delegate().asUnknownSize();
		}

		@Override
		default Matrix permuteByColumn(int... columns) {
			assert columns.length == 1;
			assert columns[0] == 0;
			return this;
		}

		@Override
		default Factorisation factorisation(double tolerance) {
			return new DiagonalFactorisation(asValue(), 1);
		}

		@Override
		default double asValue() {
			return delegate().asValue();
		}

		@Override
		default VecN asRowVec() {
			assert delegate().size() == 1;
			return delegate();
		}
	}

	class BasicToMatrix extends BasicWrapper<VecN, Matrix> implements ToMatrix {
		public BasicToMatrix(VecN delegate) {
			super(delegate);
		}
	}

	/**
	 * A pair of vectors constructing a complex number
	 */
	interface ToComplexN extends ComplexN {
		@Override
		default int size() {
			return real().size();
		}

		@Override
		default double real(int i) {
			return real().get(i);
		}

		@Override
		default double imaginary(int i) {
			return imaginary().get(i);
		}

		@Override
		VecN real();

		@Override
		VecN imaginary();

		@Override
		default ArrayComplexN arrayBackedCopy(ComplexNLayout layout) {
			assert layout.complexElements() == size();
			var result = ComplexN.of(layout);
			result.real().set(real());
			result.imaginary().set(imaginary());
			return result;
		}

		@Override
		default MComplexN move() {
			return ComplexN.of(real().move(), imaginary().move());
		}
	}

	class BasicToComplexN implements ToComplexN {
		private final VecN real;
		private final VecN imaginary;

		public BasicToComplexN(VecN real, VecN imaginary) {
			assert real.size() == imaginary.size();
			this.real = real;
			this.imaginary = imaginary;
		}

		@Override
		public VecN real() {
			return real;
		}

		@Override
		public VecN imaginary() {
			return imaginary;
		}
	}
}
