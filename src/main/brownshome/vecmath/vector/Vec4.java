package brownshome.vecmath.vector;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.rotation.Rot3;
import brownshome.vecmath.vector.array.ArrayVec4;
import brownshome.vecmath.vector.basic.BasicVec4;
import brownshome.vecmath.vector.basic.array.BasicArrayVec4;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.wrapped.Vec4Wrapper;

/**
 * A 4-element vector
 */
public interface Vec4 extends GenericVec<Vec4> {
	/**
	 * The vector (0, 0, 0, 0)
	 */
	Vec4 ZERO = new BasicVec4(0, 0, 0, 0);

	/**
	 * Creates a new vector with the given components
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 * @param w the w component
	 * @return a newly created vector
	 */
	static MVec4 of(double x, double y, double z, double w) {
		return new BasicVec4(x, y, z, w);
	}

	/**
	 * Creates an array-backed vector. The vector will have an optimal array layout for the given platform.
	 * @return a vector
	 */
	static ArrayVec4 ofArrayBacked() {
		return of(Vec4Layout.ofOptimal());
	}

	/**
	 * Creates an array-backed vector with the given layout
	 * @param layout the layout
	 * @return a vector
	 */
	static ArrayVec4 of(Vec4Layout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a vector
	 */
	static ArrayVec4 of(double[] array, Vec4Layout layout) {
		return new BasicArrayVec4(array, layout);
	}

	double x();
	double y();
	double z();
	double w();

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
					case 3 -> {
						i++;
						yield w();
					}
					default -> throw new NoSuchElementException();
				};
			}

			@Override
			public boolean hasNext() {
				return i != 4;
			}
		};
	}

	@Override
	default Spliterator.OfDouble spliterator() {
		return Spliterators.spliterator(iterator(), 4, Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL | Spliterator.CONCURRENT | Spliterator.SUBSIZED);
	}

	@Override
	default double dot(Vec4 vec) {
		return vec.x() * x() + vec.y() * y() + vec.z() * z() + vec.w() * w();
	}
	
	@Override
	default boolean exactEquals(Vec4 other) {
		return x() == other.x() && y() == other.y() && z() == other.z() && w() == other.w();
	}

	@Override
	default VecN asUnknownSize() {
		return new Vec4Wrapper.BasicToVecN(this);
	}

	/**
	 * Returns this vector as a 3D rotation
	 * @return a 3D rotation
	 */
	default Rot3 asRot() {
		return new Vec4Wrapper.BasicToRot3(this);
	}

	@Override
	default ArrayVec4 asArrayBacked() {
		return (ArrayVec4) GenericVec.super.asArrayBacked();
	}

	@Override
	default Matrix asRow() {
		return asColumn().transpose();
	}

	@Override
	default Matrix asColumn() {
		return new Vec4Wrapper.BasicToMatrix(this);
	}

	@Override
	default ArrayVec4 arrayBackedCopy() {
		return arrayBackedCopy(Vec4Layout.ofOptimal());
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	default ArrayVec4 arrayBackedCopy(Vec4Layout layout) {
		var result = Vec4.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MVec4 copy() {
		return new BasicVec4(this);
	}

	@Override
	default MVec4 move() {
		return (MVec4) GenericVec.super.move();
	}
}
