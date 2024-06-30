package brownshome.vecmath.vector;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.rotation.Rot2;
import brownshome.vecmath.vector.array.ArrayVec2;
import brownshome.vecmath.vector.basic.array.BasicArrayVec2;
import brownshome.vecmath.vector.layout.Vec2Layout;
import brownshome.vecmath.vector.basic.BasicVec2;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.wrapped.Vec2Wrapper;

/**
 * A 2-element vector
 */
public interface Vec2 extends GenericVec<Vec2> {
	/**
	 * The vector (0, 0)
	 */
	Vec2 ZERO = new BasicVec2(0, 0);

	/**
	 * The vector (1, 0)
	 */
	Vec2 X_AXIS = new BasicVec2(1, 0);

	/**
	 * The vector (0, 1)
	 */
	Vec2 Y_AXIS = new BasicVec2(0, 1);

	/**
	 * Creates a new vector with the given components
	 * @param x the x component
	 * @param y the y component
	 * @return a newly created vector
	 */
	static MVec2 of(double x, double y) {
		return new BasicVec2(x, y);
	}

	/**
	 * Creates an array-backed vector. The vector will have an optimal array layout for the given platform.
	 * @return a vector
	 */
	static ArrayVec2 ofArrayBacked() {
		return of(Vec2Layout.ofOptimal());
	}

	/**
	 * Creates an array-backed vector with the given layout
	 * @param layout the layout
	 * @return a vector
	 */
	static ArrayVec2 of(Vec2Layout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a vector
	 */
	static ArrayVec2 of(double[] array, Vec2Layout layout) {
		return new BasicArrayVec2(array, layout);
	}

	/**
	 * The x-component of this vector
	 * @return x
	 */
	double x();

	/**
	 * The y-component of this vector
	 * @return y
	 */
	double y();

	@Override
	default Spliterator.OfDouble spliterator() {
		return Spliterators.spliterator(iterator(), 2, Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL | Spliterator.CONCURRENT | Spliterator.SUBSIZED);
	}

	@Override
	default PrimitiveIterator.OfDouble iterator() {
		return new PrimitiveIterator.OfDouble() {
			int i = 0;

			@Override
			public double nextDouble() {
				return switch (i) {
					case 0 -> {
						i++;
						yield x();
					}
					case 1 -> {
						i++;
						yield y();
					}
					default -> throw new NoSuchElementException();
				};
			}

			@Override
			public boolean hasNext() {
				return i != 2;
			}
		};
	}

	/**
	 * A vector that is tangential to this vector. This function rotates the vector counterclockwise in the x-right, y-up coordinate system
	 * @return a tangent vector
	 */
	default Vec2 tangent() {
		var result = copy();
		result.setToTangent();
		return result;
	}

	@Override
	default double dot(Vec2 vec) {
		return x() * vec.x() + y() * vec.y();
	}

	@Override
	default boolean exactEquals(Vec2 other) {
		return x() == other.x() && y() == other.y();
	}

	@Override
	default VecN asUnknownSize() {
		return new Vec2Wrapper.BasicToVecN(this);
	}

	/**
	 * Returns this vector as a 2D-rotation
	 * @return a rotation
	 */
	default Rot2 asRot() {
		return new Vec2Wrapper.BasicToRot2(this);
	}

	/**
	 * Returns this vector as a complex number
	 * @return a complex number of the form {@code y + i × x}
	 */
	default Complex asComplex() {
		return new Vec2Wrapper.BasicToComplex(this);
	}

	@Override
	default ArrayVec2 asArrayBacked() {
		return (ArrayVec2) GenericVec.super.asArrayBacked();
	}

	@Override
	default Matrix asRow() {
		return asColumn().transpose();
	}

	@Override
	default Matrix asColumn() {
		return new Vec2Wrapper.BasicToMatrix(this);
	}

	@Override
	default ArrayVec2 arrayBackedCopy() {
		return arrayBackedCopy(Vec2Layout.ofOptimal());
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	default ArrayVec2 arrayBackedCopy(Vec2Layout layout) {
		var result = Vec2.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MVec2 copy() {
		return new BasicVec2(this);
	}

	@Override
	default MVec2 move() {
		return (MVec2) GenericVec.super.move();
	}
}
