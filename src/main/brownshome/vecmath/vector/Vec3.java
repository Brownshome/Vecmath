package brownshome.vecmath.vector;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.array.ArrayVec3;
import brownshome.vecmath.vector.basic.BasicVec3;
import brownshome.vecmath.vector.basic.array.BasicArrayVec3;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.wrapped.Vec3Wrapper;

/**
 * A 3-element vector
 */
public interface Vec3 extends GenericVec<Vec3> {
	/**
	 * The vector (0, 0, 0)
	 */
	Vec3 ZERO = new BasicVec3(0, 0, 0);

	/**
	 * The vector (1, 0, 0)
	 */
	Vec3 X_AXIS = new BasicVec3(1, 0, 0);

	/**
	 * The vector (0, 1, 0)
	 */
	Vec3 Y_AXIS = new BasicVec3(0, 1, 0);

	/**
	 * The vector (0, 0, 1)
	 */
	Vec3 Z_AXIS = new BasicVec3(0, 0, 1);

	/**
	 * Creates a new vector with the given components
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 * @return a newly created vector
	 */
	static MVec3 of(double x, double y, double z) {
		return new BasicVec3(x, y, z);
	}

	/**
	 * Creates an array-backed vector. The vector will have an optimal array layout for the given platform.
	 * @return a vector
	 */
	static ArrayVec3 ofArrayBacked() {
		return of(Vec3Layout.ofOptimal());
	}

	/**
	 * Creates an array-backed vector with the given layout
	 * @param layout the layout
	 * @return a vector
	 */
	static ArrayVec3 of(Vec3Layout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a vector
	 */
	static ArrayVec3 of(double[] array, Vec3Layout layout) {
		return new BasicArrayVec3(array, layout);
	}

	double x();
	double y();
	double z();

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
					case 2 -> {
						i++;
						yield z();
					}
					default -> throw new NoSuchElementException();
				};
			}

			@Override
			public boolean hasNext() {
				return i != 3;
			}
		};
	}

	@Override
	default Spliterator.OfDouble spliterator() {
		return Spliterators.spliterator(iterator(), 3, Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL | Spliterator.CONCURRENT | Spliterator.SUBSIZED);
	}

	@Override
	default double dot(Vec3 vec) {
 		return vec.x() * x() + vec.y() * y() + vec.z() * z();
	}

	/**
	 * The cross product between this vector and the given one
	 * @param vec the other vector
	 * @return the cross product between this vector and the given vector
	 */
	default Vec3 cross(Vec3 vec) {
		var result = copy();
		result.setToRightCross(vec);
		return result;
	}

	@Override
	default boolean exactEquals(Vec3 other) {
		return x() == other.x() && y() == other.y() && z() == other.z();
	}

	@Override
	default VecN asUnknownSize() {
		return new Vec3Wrapper.BasicToVecN(this);
	}

	@Override
	default ArrayVec3 asArrayBacked() {
		return (ArrayVec3) GenericVec.super.asArrayBacked();
	}

	@Override
	default Matrix asRow() {
		return asColumn().transpose();
	}

	@Override
	default Matrix asColumn() {
		return new Vec3Wrapper.BasicToMatrix(this);
	}

	@Override
	default ArrayVec3 arrayBackedCopy() {
		return arrayBackedCopy(Vec3Layout.ofOptimal());
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	default ArrayVec3 arrayBackedCopy(Vec3Layout layout) {
		var result = Vec3.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MVec3 copy() {
		return new BasicVec3(this);
	}

	@Override
	default MVec3 move() {
		return (MVec3) GenericVec.super.move();
	}
}
