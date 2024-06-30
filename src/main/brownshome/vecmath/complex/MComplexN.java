package brownshome.vecmath.complex;

import brownshome.vecmath.complex.generic.GenericMComplex;
import brownshome.vecmath.complex.wrapped.MComplexNWrapper;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.vector.MVecN;

public interface MComplexN extends GenericMComplex<ComplexN, ComplexN>, ComplexN {
	/**
	 * Sets the real part
	 * @param r the value
	 * @param index the index
	 */
	void real(double r, int index);

	/**
	 * Sets the imaginary part
	 * @param i the value
	 * @param index the index
	 */
	void imaginary(double i, int index);

	@Override
	default MVecN real() {
		return new MComplexNWrapper.BasicToReal(this);
	}

	@Override
	default MVecN imaginary() {
		return new MComplexNWrapper.BasicToImaginary(this);
	}

	@Override
	default MComplex get(int i) {
		assert i < size();
		return new MComplexNWrapper.BasicToComplex(this, i);
	}

	@Override
	default MComplex asScalar() {
		return (MComplex) ComplexN.super.asScalar();
	}

	@Override
	default MMatrix asRow() {
		return (MMatrix) ComplexN.super.asRow();
	}

	@Override
	default MMatrix asColumn() {
		return new MComplexNWrapper.BasicToMatrix(this);
	}

	@Override
	default void scaleSelf(Complex scale) {
		for (int i = 0; i < size(); i++) {
			get(i).scaleSelf(scale);
		}
	}

	@Override
	default void setToConjugate() {
		for (int i = 0; i < size(); i++) {
			get(i).setToConjugate();
		}
	}

	@Override
	default MComplexN asComplexUnknownSize() {
		return this;
	}

	@Override
	default void set(ComplexN e) {
		assert size() == e.size();
		for (int i = 0; i < size(); i++) {
			get(i).set(e.get(i));
		}
	}

	@Override
	default void addToSelf(ComplexN e) {
		assert size() == e.size();
		for (int i = 0; i < size(); i++) {
			get(i).addToSelf(e.get(i));
		}
	}

	@Override
	default void scaleSelf(double scale) {
		for (int i = 0; i < size(); i++) {
			get(i).scaleSelf(scale);
		}
	}

	@Override
	default void scaleSelf(ComplexN scale) {
		assert size() == scale.size();
		for (int i = 0; i < size(); i++) {
			get(i).scaleSelf(scale.get(i));
		}
	}

	@Override
	default boolean exactEquals(ComplexN other) {
		assert other.size() == size();

		for (int i = 0; i < size(); i++) {
			if (!get(i).exactEquals(other.get(i))) {
				return false;
			}
		}

		return true;
	}

	@Override
	default MComplexN move() {
		return (MComplexN) GenericMComplex.super.move();
	}
}
