package brownshome.vecmath.complex.array;

import java.util.Arrays;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.complex.MComplex;
import brownshome.vecmath.complex.generic.GenericArrayComplex;
import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.array.ArrayVec2;
import brownshome.vecmath.vector.layout.Vec2Layout;

/**
 * A single complex number backed by an array
 */
public interface ArrayComplex extends MComplex, ArrayVec2, GenericArrayComplex<Vec2Layout, ComplexLayout, Vec2, Complex> {
	@Override
	default double x() {
		return MComplex.super.x();
	}

	@Override
	default double y() {
		return MComplex.super.y();
	}

	@Override
	default void x(double x) {
		MComplex.super.x(x);
	}

	@Override
	default void y(double y) {
		MComplex.super.y(y);
	}

	@Override
	default double real() {
		return backingArray()[layout().realArrayIndex(0)];
	}

	@Override
	default double imaginary() {
		return backingArray()[layout().imaginaryArrayIndex(0)];
	}

	@Override
	default void real(double r) {
		backingArray()[layout().realArrayIndex(0)] = r;
	}

	@Override
	default void imaginary(double i) {
		backingArray()[layout().imaginaryArrayIndex(0)] = i;
	}

	@Override
	default double magnitudeSquared() {
		return MComplex.super.magnitudeSquared();
	}

	@Override
	default ArrayComplexN asComplexUnknownSize() {
		return ComplexN.of(backingArray(), layout().asComplexN());
	}

	@Override
	default ArrayComplex asComplex() {
		return (ArrayComplex) MComplex.super.asComplex();
	}

	@Override
	default ArrayComplex asArrayBacked() {
		return (ArrayComplex) ArrayVec2.super.asArrayBacked();
	}

	@Override
	default ArrayComplex arrayBackedCopy(ComplexLayout layout) {
		assert layout != null;

		if (layout().equals(layout)) {
			return Complex.of(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		return MComplex.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayComplex move() {
		return (ArrayComplex) MComplex.super.move();
	}
}
