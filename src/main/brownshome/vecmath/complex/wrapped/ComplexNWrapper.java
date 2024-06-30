package brownshome.vecmath.complex.wrapped;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.wrapped.BasicWrapper;

public interface ComplexNWrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends GenericComplexWrapper<ComplexN, ELEMENT_TYPE> {
	interface ToMatrix extends ComplexNWrapper<Matrix>, Matrix {
		@Override
		default int rows() {
			return delegate().size();
		}

		@Override
		default int columns() {
			return 2;
		}

		@Override
		default double get(int row, int column) {
			assert column < columns();
			return column == 0 ? delegate().real(row) : delegate().imaginary(row);
		}

		@Override
		default VecN row(int r) {
			return delegate().get(r).asUnknownSize();
		}

		@Override
		default VecN column(int c) {
			assert c < columns();
			return c == 0 ? delegate().real() : delegate().imaginary();
		}

		@Override
		default double asValue() {
			throw new UnsupportedOperationException("ComplexN values cannot be converted to a scalar");
		}

		@Override
		default VecN asRowVec() {
			return delegate().asScalar().asUnknownSize();
		}

		@Override
		default VecN asColumnVec() {
			throw new UnsupportedOperationException("ComplexN values cannot be converted to a column");
		}
	}

	class BasicToMatrix extends BasicWrapper<ComplexN, Matrix> implements ToMatrix {
		public BasicToMatrix(ComplexN delegate) {
			super(delegate);
		}
	}

	interface ToComponent extends ComplexNWrapper<VecN>, VecN {
		@Override
		default int size() {
			return delegate().size();
		}
	}

	interface ToReal extends ToComponent {
		@Override
		default double get(int i) {
			return delegate().real(i);
		}
	}

	final class BasicToReal extends BasicWrapper<ComplexN, VecN> implements ToReal {
		public BasicToReal(ComplexN delegate) {
			super(delegate);
		}
	}

	interface ToImaginary extends ToComponent {
		@Override
		default double get(int i) {
			return delegate().imaginary(i);
		}
	}

	final class BasicToImaginary extends BasicWrapper<ComplexN, VecN> implements ToImaginary {
		public BasicToImaginary(ComplexN delegate) {
			super(delegate);
		}
	}

	interface ToComplex extends ComplexNWrapper<Vec2>, Complex {
		/**
		 * The index in the delegate of this complex
		 * @return the index of the wrapped complex value
		 */
		int index();

		@Override
		default double real() {
			return delegate().real(index());
		}

		@Override
		default double imaginary() {
			return delegate().imaginary(index());
		}
	}

	final class BasicToComplex extends BasicWrapper<ComplexN, Vec2> implements ToComplex {
		private final int index;

		public BasicToComplex(ComplexN delegate, int index) {
			super(delegate);
			this.index = index;
		}

		@Override
		public int index() {
			return index;
		}
	}
}
