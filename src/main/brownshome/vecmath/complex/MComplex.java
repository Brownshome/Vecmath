package brownshome.vecmath.complex;

import brownshome.vecmath.complex.array.ArrayComplex;
import brownshome.vecmath.complex.generic.GenericMComplex;
import brownshome.vecmath.complex.wrapped.MComplexWrapper;
import brownshome.vecmath.vector.MVec2;
import brownshome.vecmath.vector.Vec2;

/**
 * A modifiable complex number
 */
public interface MComplex extends GenericMComplex<Vec2, Complex>, Complex, MVec2 {
	/**
	 * Sets the real component of this number
	 * @param r the real component
	 */
	void real(double r);

	/**
	 * Sets the imaginary component of this number
	 * @param i the imaginary component
	 */
	void imaginary(double i);

	@Override
	default void x(double x) {
		imaginary(x);
	}

	@Override
	default void y(double y) {
		real(y);
	}

	@Override
	default void scaleSelf(Complex scale) {
		double real = real() * scale.real() - imaginary() * scale.imaginary();
		double imaginary = imaginary() * scale.real() + real() * scale.imaginary();
		real(real);
		imaginary(imaginary);
	}

	@Override
	default void setToConjugate() {
		imaginary(-imaginary());
	}

	@Override
	default void setToNormalised() {
		MVec2.super.setToNormalised();
	}

	@Override
	default ArrayComplex asArrayBacked() {
		return Complex.super.asArrayBacked();
	}

	@Override
	default ArrayComplex arrayBackedCopy() {
		return Complex.super.arrayBackedCopy();
	}

	@Override
	default MComplex asComplex() {
		return (MComplex) Complex.super.asComplex();
	}

	@Override
	default MComplexN asComplexUnknownSize() {
		return new MComplexWrapper.BasicToComplexN(this);
	}

	@Override
	default MComplex copy() {
		return Complex.super.copy();
	}

	@Override
	default MComplex move() {
		return (MComplex) MVec2.super.move();
	}
}
