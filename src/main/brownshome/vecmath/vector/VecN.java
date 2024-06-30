package brownshome.vecmath.vector;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.array.*;
import brownshome.vecmath.vector.basic.array.BasicArrayVecN;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.layout.*;
import brownshome.vecmath.vector.wrapped.VecNWrapper;

/**
 * An arbitrary-element vector
 */
public interface VecN extends GenericVec<VecN> {
	/**
	 * Returns an unknown-size vector of all zeros
	 * @param size the size of the vector
	 * @return the new vector
	 */
	static ArrayVecN zero(int size) {
		return of(VecNLayout.ofOptimal(size));
	}

	/**
	 * Creates a vector for the named axis of a given size
	 * @param axis the axis to set to 1.0
	 * @param size the size of the vector
	 * @return the new vector
	 */
	static ArrayVecN axis(int axis, int size) {
		var result = zero(size);
		result.set(1.0, axis);
		return result;
	}

	/**
	 * Creates a new vector with the given elements
	 * @param elements the elements in this vector
	 * @return a newly created vector
	 */
	static ArrayVecN of(double... elements) {
		return of(elements, VecNLayout.of(elements.length));
	}

	/**
	 * Creates an array-backed vector with the given layout
	 * @param layout the layout
	 * @return a vector
	 */
	static ArrayVecN of(VecNLayout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a vector
	 */
	static ArrayVecN of(double[] array, VecNLayout layout) {
		return new BasicArrayVecN(array, layout);
	}

	/**
	 * Returns the number of items in this vector
	 */
	int size();

	/**
	 * Gets the ith item of the vector
	 * @param i the index
	 * @return the item
	 */
	double get(int i);

	@Override
	default PrimitiveIterator.OfDouble iterator() {
		return new PrimitiveIterator.OfDouble() {
			int i = 0;

			@Override
			public double nextDouble() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}

				i++;

				return get(i);
			}

			@Override
			public boolean hasNext() {
				return i != size();
			}
		};
	}

	@Override
	default Spliterator.OfDouble spliterator() {
		return Spliterators.spliterator(iterator(), size(), Spliterator.ORDERED | Spliterator.SIZED | Spliterator.NONNULL | Spliterator.CONCURRENT | Spliterator.SUBSIZED);
	}

	@Override
	default double dot(VecN other) {
		assert size() == other.size();

		double dot = 0.0;
		for (int i = 0; i < size(); i++) {
			dot += get(i) * other.get(i);
		}

		return dot;
	}

	@Override
	default boolean exactEquals(VecN other) {
		assert other.size() == size();

		boolean equal = true;
		for (int i = 0; i < size(); i++) {
			equal &= get(i) == other.get(i);
		}

		return equal;
	}

	@Override
	default VecN asUnknownSize() {
		return this;
	}

	/**
	 * Returns this vector as a single value if it is one
	 * @return the single value in this vector
	 */
	default double asValue() {
		assert size() == 1;
		return get(0);
	}

	/**
	 * Returns a {@link Vec2} mirroring this vector. This method must only be called if {@link VecN#size()} is 2.
	 * @return a {@link Vec2}
	 */
	default Vec2 asVec2() {
		return new VecNWrapper.BasicToVec2(this);
	}

	/**
	 * Returns a {@link Vec3} mirroring this vector. This method must only be called if {@link VecN#size()} is 3.
	 * @return a {@link Vec3}
	 */
	default Vec3 asVec3() {
		return new VecNWrapper.BasicToVec3(this);
	}

	/**
	 * Returns a {@link Vec4} mirroring this vector. This method must only be called if {@link VecN#size()} is 4.
	 * @return a {@link Vec4}
	 */
	default Vec4 asVec4() {
		return new VecNWrapper.BasicToVec4(this);
	}

	@Override
	default Matrix asRow() {
		return asColumn().transpose();
	}

	@Override
	default Matrix asColumn() {
		return new VecNWrapper.BasicToMatrix(this);
	}

	@Override
	default ArrayVecN asArrayBacked() {
		return (ArrayVecN) GenericVec.super.asArrayBacked();
	}

	@Override
	default ArrayVecN arrayBackedCopy() {
		return arrayBackedCopy(VecNLayout.ofOptimal(size()));
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	default ArrayVecN arrayBackedCopy(VecNLayout layout) {
		assert layout.elements() == size();

		var result = VecN.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MVecN copy() {
		return arrayBackedCopy();
	}

	@Override
	default MVecN move() {
		return (MVecN) GenericVec.super.move();
	}
}
