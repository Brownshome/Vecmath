package brownshome.vecmath.complex.array;

import java.util.Arrays;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.complex.MComplex;
import brownshome.vecmath.complex.MComplexN;
import brownshome.vecmath.complex.generic.GenericArrayComplex;
import brownshome.vecmath.complex.layout.ComplexNLayout;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;

public interface ArrayComplexN extends GenericArrayComplex<ComplexNLayout, ComplexNLayout, ComplexN, ComplexN>, MComplexN {
	@Override
	default int size() {
		return layout().complexElements();
	}

	@Override
	default double real(int i) {
		return backingArray()[layout().realArrayIndex(i)];
	}

	@Override
	default void real(double r, int index) {
		backingArray()[layout().realArrayIndex(index)] = r;
	}

	@Override
	default double imaginary(int i) {
		return backingArray()[layout().imaginaryArrayIndex(i)];
	}

	@Override
	default void imaginary(double i, int index) {
		backingArray()[layout().imaginaryArrayIndex(index)] = i;
	}

	@Override
	default MVecN real() {
		return VecN.of(backingArray(), layout().real());
	}

	@Override
	default MVecN imaginary() {
		return VecN.of(backingArray(), layout().imaginary());
	}

	@Override
	default MComplex get(int i) {
		assert i < size();
		return Complex.of(backingArray(), layout().asComplex(i));
	}

	@Override
	default ArrayComplex asScalar() {
		return (ArrayComplex) MComplexN.super.asScalar();
	}

	@Override
	default ArrayComplexN asComplexUnknownSize() {
		return (ArrayComplexN) MComplexN.super.asComplexUnknownSize();
	}

	@Override
	default void set(ComplexN e) {
		if (layout().isContinuous() && e instanceof ArrayComplexN other && other.layout().equals(layout())) {
			GenericArrayComplex.super.set(e);
			return;
		}

		MComplexN.super.set(e);
	}

	@Override
	default double magnitudeSquared() {
		if (layout().isContinuous()) {
			return GenericArrayComplex.super.magnitudeSquared();
		}

		return MComplexN.super.magnitudeSquared();
	}

	@Override
	default void addToSelf(ComplexN e) {
		if (layout().isContinuous() && e instanceof ArrayComplexN other && other.layout().equals(layout())) {
			GenericArrayComplex.super.addToSelf(e);
			return;
		}

		MComplexN.super.addToSelf(e);
	}

	@Override
	default void scaleSelf(double scale) {
		if (layout().isContinuous()) {
			GenericArrayComplex.super.scaleSelf(scale);
			return;
		}

		MComplexN.super.scaleSelf(scale);
	}

	@Override
	default void scaleSelf(ComplexN scale) {
		if (layout().isContinuous() && scale instanceof ArrayComplexN other && other.layout().equals(layout())) {
			GenericArrayComplex.super.scaleSelf(scale);
			return;
		}

		MComplexN.super.scaleSelf(scale);
	}

	@Override
	default boolean exactEquals(ComplexN other) {
		if (layout().isContinuous() && other instanceof ArrayComplexN otherArray && otherArray.layout().equals(layout())) {
			return GenericArrayComplex.super.exactEquals(otherArray);
		}

		return MComplexN.super.exactEquals(other);
	}

	@Override
	default ArrayComplexN asArrayBacked() {
		return (ArrayComplexN) GenericArrayComplex.super.asArrayBacked();
	}

	@Override
	default ArrayComplexN arrayBackedCopy(ComplexNLayout layout) {
		assert layout != null;
		if (layout.equals(layout())) {
			return ComplexN.of(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		return MComplexN.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayComplexN move() {
		return (ArrayComplexN) MComplexN.super.move();
	}
}
