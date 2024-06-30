package brownshome.vecmath.complex;

import brownshome.vecmath.complex.array.ArrayComplex;
import brownshome.vecmath.complex.basic.BasicComplex;
import brownshome.vecmath.complex.basic.array.BasicArrayComplex;
import brownshome.vecmath.complex.generic.GenericComplex;
import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.complex.wrapped.ComplexWrapper;
import brownshome.vecmath.vector.Vec2;

/**
 * A complex number. When viewed as a vector the x is the imaginary component and y is the real component.
 */
public interface Complex extends GenericComplex<Vec2, Complex>, Vec2 {
	/**
	 * The number 0
	 */
	Complex ZERO = of(0.0, 0.0);

	/**
	 * The number i
	 */
	Complex I = of(0.0, 1.0);

	/**
	 * The number 1
	 */
	Complex ONE = of(1.0, 0.0);

	/**
	 * Creates a complex number from the given real and imaginary components
	 * @param real the real component
	 * @param imaginary the imaginary component
	 * @return a complex number
	 */
	static MComplex of(double real, double imaginary) {
		return new BasicComplex(imaginary, real);
	}

	/**
	 * Creates a complex number with a backing array. This array will be optimal for the current platform
	 * @return an array-backed complex number
	 */
	static ArrayComplex ofArrayBacked() {
		return of(ComplexLayout.ofOptimal());
	}

	/**
	 * Creates a complex number with a backing array and a provided layout
	 * @param layout the layout
	 * @return an array-backed complex number
	 */
	static ArrayComplex of(ComplexLayout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates an array-backed complex number using a provided backing array and layout. Writes to
	 * the complex number will update the given array and vice-versa.
	 *
	 * @param array the backing array
	 * @param layout the layout to use
	 * @return an array-backed complex number
	 */
	static ArrayComplex of(double[] array, ComplexLayout layout) {
		return new BasicArrayComplex(array, layout);
	}

	/**
	 * The real part of this number
	 * @return the real part
	 */
	double real();

	@Override
	default double y() {
		return real();
	}

	/**
	 * The imaginary part of this number
	 * @return the imaginary part
	 */
	double imaginary();

	@Override
	default double x() {
		return imaginary();
	}

	@Override
	default Complex normalised() {
		return (Complex) Vec2.super.normalised();
	}

	@Override
	default double magnitudeSquared() {
		return lengthSquared();
	}

	@Override
	default ComplexN asComplexUnknownSize() {
		return new ComplexWrapper.BasicToComplexN(this);
	}

	@Override
	default Complex asComplex() {
		return this;
	}

	@Override
	default ArrayComplex asArrayBacked() {
		return (ArrayComplex) Vec2.super.asArrayBacked();
	}

	@Override
	default ArrayComplex arrayBackedCopy() {
		return arrayBackedCopy(ComplexLayout.ofOptimal());
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	default ArrayComplex arrayBackedCopy(ComplexLayout layout) {
		var result = Complex.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MComplex copy() {
		return new BasicComplex(this);
	}

	@Override
	default MComplex move() {
		return (MComplex) Vec2.super.move();
	}
}
