package brownshome.vecmath.complex.generic;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.generic.GenericElement;

/**
 * A generic complex number, representing both scalars, and vectors
 *
 * @param <ELEMENT_TYPE> the type of the base element of this type
 * @param <COMPLEX_TYPE> the type of the complex number
 */
@SuppressWarnings("unchecked")
public interface GenericComplex<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>,
		COMPLEX_TYPE extends ELEMENT_TYPE> extends GenericElement<ELEMENT_TYPE> {
	@Override
	default COMPLEX_TYPE add(ELEMENT_TYPE e) {
		return (COMPLEX_TYPE) GenericElement.super.add(e);
	}

	@Override
	default COMPLEX_TYPE subtract(ELEMENT_TYPE e) {
		return (COMPLEX_TYPE) GenericElement.super.subtract(e);
	}

	@Override
	default COMPLEX_TYPE scale(double scale) {
		return (COMPLEX_TYPE) GenericElement.super.scale(scale);
	}

	@Override
	default COMPLEX_TYPE scale(ELEMENT_TYPE scale) {
		return (COMPLEX_TYPE) GenericElement.super.scale(scale);
	}

	/**
	 * Returns a complex vector with all the elements scaled by the given complex number
	 * @param scale the number to scale by
	 * @return a scaled complex vector
	 */
	default COMPLEX_TYPE scale(Complex scale) {
		var result = (GenericMComplex<ELEMENT_TYPE, COMPLEX_TYPE>) copy();
		result.scaleSelf(scale);
		return (COMPLEX_TYPE) result;
	}

	/**
	 * Divides this number by another
	 * @param divider the number to divide by
	 * @return the result
	 */
	default COMPLEX_TYPE divide(Complex divider) {
		var result = (GenericMComplex<ELEMENT_TYPE, COMPLEX_TYPE>) copy();
		result.divide(divider);
		return (COMPLEX_TYPE) result;
	}

	@Override
	default COMPLEX_TYPE scaleAdd(ELEMENT_TYPE e, double scale) {
		return (COMPLEX_TYPE) GenericElement.super.scaleAdd(e, scale);
	}

	/**
	 * Gets the multiplicative inverse of this number
	 * @return one divided by this number
	 */
	default COMPLEX_TYPE inverted() {
		var result = (GenericMComplex<ELEMENT_TYPE, COMPLEX_TYPE>) copy();
		result.setToInverted();
		return (COMPLEX_TYPE) result;
	}

	@Override
	default COMPLEX_TYPE negated() {
		return (COMPLEX_TYPE) GenericElement.super.negated();
	}

	@Override
	default COMPLEX_TYPE interpolated(ELEMENT_TYPE other, double t) {
		return (COMPLEX_TYPE) GenericElement.super.interpolated(other, t);
	}

	/**
	 * Returns the complex conjugate of this complex vector
	 * @return a vector with all the elements set to their conjugate
	 */
	default COMPLEX_TYPE conjugate() {
		var result = (GenericMComplex<ELEMENT_TYPE, COMPLEX_TYPE>) copy();
		result.setToConjugate();
		return (COMPLEX_TYPE) result;
	}

	default COMPLEX_TYPE normalised() {
		var result = (GenericMComplex<ELEMENT_TYPE, COMPLEX_TYPE>) copy();
		result.setToNormalised();
		return (COMPLEX_TYPE) result;
	}

	/**
	 * The magnitude of this complex number squared
	 * @return the squared magnitude
	 */
	double magnitudeSquared();

	/**
	 * The magnitude of this complex number
	 * @return the magnitude
	 */
	default double magnitude() {
		return Math.sqrt(magnitudeSquared());
	}

	ComplexN asComplexUnknownSize();

	@Override
	default COMPLEX_TYPE asArrayBacked() {
		return (COMPLEX_TYPE) GenericElement.super.asArrayBacked();
	}

	@Override
	COMPLEX_TYPE arrayBackedCopy();

	@Override
	COMPLEX_TYPE copy();

	@Override
	default COMPLEX_TYPE move() {
		return (COMPLEX_TYPE) GenericElement.super.move();
	}
}
