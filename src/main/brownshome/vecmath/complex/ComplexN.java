package brownshome.vecmath.complex;

import brownshome.vecmath.complex.array.ArrayComplexN;
import brownshome.vecmath.complex.basic.array.BasicArrayComplexN;
import brownshome.vecmath.complex.generic.GenericComplex;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.complex.wrapped.ComplexNWrapper;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.wrapped.MVecNWrapper;
import brownshome.vecmath.vector.wrapped.VecNWrapper;

/**
 * An arbitrary-length complex vector
 */
public interface ComplexN extends GenericComplex<ComplexN, ComplexN> {
	/**
	 * Creates a complex vector of zeros
	 * @param size the number of zeros
	 * @return a complex vector
	 */
	static ArrayComplexN zero(int size) {
		return of(ComplexNLayout.ofOptimal(size));
	}

	/**
	 * Creates a complex vector of zeros from the given layout
	 * @param layout the layout
	 * @return a complex vector
	 */
	static ArrayComplexN of(ComplexNLayout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a complex vector with the given pairs of complex and real numbers
	 * @param elements the elements in this vector, each item is a pair of numbers, real followed by complex
	 * @return a new complex vector
	 */
	static ArrayComplexN of(double... elements) {
		assert elements.length % 2 == 0;
		return of(elements, ComplexNLayout.of(elements.length / 2));
	}

	/**
	 * Creates a complex vector wrapping the given array
	 * @param values the values in the complex vector
	 * @param layout the layout of the array
	 * @return a complex vector
	 */
	static ArrayComplexN of(double[] values, ComplexNLayout layout) {
		return new BasicArrayComplexN(values, layout);
	}

	/**
	 * Creates a complex vector wrapping a real and imaginary vector
	 * @param real the real components
	 * @param imaginary the imaginary components
	 * @return a complex vector
	 */
	static ComplexN of(VecN real, VecN imaginary) {
		return new VecNWrapper.BasicToComplexN(real, imaginary);
	}

	/**
	 * Creates a modifiable complex vector wrapping a real and imaginary vector
	 * @param real the real components
	 * @param imaginary the imaginary components
	 * @return a complex vector
	 */
	static MComplexN of(MVecN real, MVecN imaginary) {
		return new MVecNWrapper.BasicToComplexN(real, imaginary);
	}

	/**
	 * The number of complex numbers in this vector
	 * @return the number of elements
	 */
	int size();

	/**
	 * The real components of this vector
	 * @return a vector
	 */
	default VecN real() {
		return new ComplexNWrapper.BasicToReal(this);
	}

	/**
	 * The imaginary components of this vector
	 * @return a vector
	 */
	default VecN imaginary() {
		return new ComplexNWrapper.BasicToImaginary(this);
	}

	/**
	 * Gets a complex number wrapping the i-th term of this vector
	 * @param i the index to get
	 * @return a complex number
	 */
	default Complex get(int i) {
		assert i < size();

		return new ComplexNWrapper.BasicToComplex(this, i);
	}

	/**
	 * The i-th real component of this vector
	 * @param i the index to get
	 * @return the real component
	 */
	double real(int i);

	/**
	 * The i-th imaginary component of this vector
	 * @param i the index to get
	 * @return the imaginary component
	 */
	double imaginary(int i);

	@Override
	default double magnitudeSquared() {
		double sum = 0.0;
		for (int i = 0; i < size(); i++) {
			sum += get(i).magnitudeSquared();
		}

		return sum;
	}

	@Override
	default ComplexN asComplexUnknownSize() {
		return this;
	}

	/**
	 * Returns a {@link Complex} mirroring this value. This method must only be called if {@link ComplexN#size()} is 1.
	 * @return a {@link Complex}
	 */
	default Complex asScalar() {
		assert size() == 1;
		return get(0);
	}

	/**
	 * Gets this complex vector as a row matrix where each column is a complex number
	 * @return a matrix
	 */
	default Matrix asRow() {
		return asColumn().transpose();
	}

	/**
	 * Gets this complex vector as a column matrix where each row is a complex number
	 * @return a matrix
	 */
	default Matrix asColumn() {
		return new ComplexNWrapper.BasicToMatrix(this);
	}

	@Override
	default ArrayComplexN asArrayBacked() {
		return (ArrayComplexN) GenericComplex.super.asArrayBacked();
	}

	@Override
	default ArrayComplexN arrayBackedCopy() {
		return arrayBackedCopy(ComplexNLayout.ofOptimal(size()));
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @param layout the layout
	 * @return a copy
	 */
	default ArrayComplexN arrayBackedCopy(ComplexNLayout layout) {
		assert layout.complexElements() == size();

		var result = ComplexN.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MComplexN copy() {
		return arrayBackedCopy();
	}

	@Override
	default MComplexN move() {
		return (MComplexN) GenericComplex.super.move();
	}

	@Override
	default boolean exactEquals(ComplexN other) {
		for (var i = 0; i < other.size(); i++) {
			if (!other.get(i).exactEquals(get(i))) {
				return false;
			}
		}

		return true;
	}
}
