package brownshome.vecmath.complex.wrapped;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.complex.MComplex;
import brownshome.vecmath.complex.MComplexN;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.vector.wrapped.BasicWrapper;
import brownshome.vecmath.vector.wrapped.MWrapper;

public interface MComplexWrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends
		ComplexWrapper<ELEMENT_TYPE>, MWrapper<Complex, ELEMENT_TYPE> {
	abstract class BasicMComplexWrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>>
			extends BasicWrapper<Complex, ELEMENT_TYPE> {
		public BasicMComplexWrapper(MComplex delegate) {
			super(delegate);
		}

		@Override
		public MComplex delegate() {
			return (MComplex) super.delegate();
		}
	}

	interface ToComplexN extends ComplexWrapper.ToComplexN, MComplexN {
		@Override
		MComplex delegate();

		@Override
		default MComplex get(int i) {
			return (MComplex) ToComplexN.super.get(i);
		}

		@Override
		default void real(double r, int index) {
			assert index == 0;
			delegate().real(r);
		}

		@Override
		default void imaginary(double i, int index) {
			assert index == 0;
			delegate().imaginary(i);
		}

		@Override
		default MMatrix asRow() {
			return (MMatrix) ToComplexN.super.asRow();
		}

		@Override
		default MMatrix asColumn() {
			return (MMatrix) ToComplexN.super.asColumn();
		}

		@Override
		default void scaleSelf(Complex scale) {
			delegate().scaleSelf(scale);
		}

		@Override
		default void setToConjugate() {
			delegate().setToConjugate();
		}

		@Override
		default void divideSelf(Complex divider) {
			delegate().divideSelf(divider);
		}

		@Override
		default void setToInverted() {
			delegate().setToInverted();
		}

		@Override
		default void setToNormalised() {
			delegate().setToNormalised();
		}
	}

	final class BasicToComplexN extends BasicMComplexWrapper<ComplexN> implements ToComplexN {
		public BasicToComplexN(MComplex delegate) {
			super(delegate);
		}

		@Override
		public MComplex delegate() {
			return (MComplex) super.delegate();
		}
	}
}
