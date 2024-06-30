package brownshome.vecmath.complex.generic;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.MComplexN;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.generic.GenericMElement;

public interface GenericMComplex<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>,
		COMPLEX_TYPE extends ELEMENT_TYPE> extends GenericMElement<ELEMENT_TYPE>, GenericComplex<ELEMENT_TYPE, COMPLEX_TYPE> {
	/**
	 * Scales each element of this complex vector by the given number
	 * @param scale the scale
	 */
	void scaleSelf(Complex scale);

	/**
	 * Divides each element of this complex vector by the given number
	 * @param divider the divider
	 */
	default void divideSelf(Complex divider) {
		scaleSelf(divider.inverted());
	}

	/**
	 * Sets this element to the multiplicative inverse
	 */
	default void setToInverted() {
		setToConjugate();
		scaleSelf(1.0 / magnitudeSquared());
	}

	/**
	 * Sets each element of this complex vector to its complex conjugate
	 */
	void setToConjugate();

	/**
	 * Sets this complex vector to the normal. The results are undefined if the length is zero
	 */
	default void setToNormalised() {
		scaleSelf(1.0 / magnitude());
	}

	@Override
	MComplexN asComplexUnknownSize();

	@Override
	@SuppressWarnings("unchecked")
	default COMPLEX_TYPE move() {
		return (COMPLEX_TYPE) GenericMElement.super.move();
	}
}
