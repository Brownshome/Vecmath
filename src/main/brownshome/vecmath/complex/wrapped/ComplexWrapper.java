package brownshome.vecmath.complex.wrapped;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.wrapped.BasicWrapper;
import brownshome.vecmath.vector.wrapped.VecNWrapper;
import brownshome.vecmath.vector.wrapped.VecWrapper;

public interface ComplexWrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends GenericComplexWrapper<Complex, ELEMENT_TYPE> {
	interface ToComplexN extends ComplexWrapper<ComplexN>, ComplexN {
		@Override
		default int size() {
			return 1;
		}

		@Override
		default Complex get(int i) {
			assert i == 0;
			return delegate();
		}

		@Override
		default double real(int i) {
			return delegate().real();
		}

		@Override
		default double imaginary(int i) {
			return delegate().imaginary();
		}

		@Override
		default Matrix asRow() {
			return delegate().asColumn();
		}

		@Override
		default Matrix asColumn() {
			return delegate().asRow();
		}
	}

	class BasicToComplexN extends BasicWrapper<Complex, ComplexN> implements ToComplexN {
		public BasicToComplexN(Complex delegate) {
			super(delegate);
		}
	}
}
