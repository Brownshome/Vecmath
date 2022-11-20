package brownshome.vecmath.vector.array;

import java.util.Arrays;

import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.basic.layout.WrappedVecLayout;
import brownshome.vecmath.vector.layout.VecNLayout;
import brownshome.vecmath.vector.generic.GenericArrayVec;

/**
 * An array-backed arbitrary-element vector
 */
public interface ArrayVecN extends MVecN, GenericArrayVec<VecNLayout, VecN> {
	@Override
	default double get(int i) {
		return backingArray()[layout().arrayIndex(i)];
	}

	@Override
	default void set(double value, int index) {
		backingArray()[layout().arrayIndex(index)] = value;
	}

	@Override
	default void set(VecN vec) {
		MVecN.super.set(vec);
	}

	@Override
	default int size() {
		return layout().elements();
	}

	@Override
	default void addToSelf(VecN vec) {
		MVecN.super.addToSelf(vec);
	}

	@Override
	default void scaleSelf(double scale) {
		MVecN.super.scaleSelf(scale);
	}

	@Override
	default void scaleSelf(VecN scale) {
		MVecN.super.scaleSelf(scale);
	}

	@Override
	default double dot(VecN vec) {
		return MVecN.super.dot(vec);
	}

	@Override
	default boolean exactEquals(VecN other) {
		return MVecN.super.exactEquals(other);
	}

	@Override
	default ArrayVecN asUnknownSize() {
		return (ArrayVecN) MVecN.super.asUnknownSize();
	}

	@Override
	default ArrayVecN asArrayBacked() {
		return (ArrayVecN) GenericArrayVec.super.asArrayBacked();
	}

	@Override
	default ArrayVec2 asVec2() {
		assert size() == 2;

		return Vec2.of(backingArray(), new WrappedVecLayout(layout()));
	}

	@Override
	default ArrayVec3 asVec3() {
		assert size() == 3;

		return Vec3.of(backingArray(), new WrappedVecLayout(layout()));
	}

	@Override
	default ArrayVec4 asVec4() {
		assert size() == 4;

		return Vec4.of(backingArray(), new WrappedVecLayout(layout()));
	}

	@Override
	default ArrayVecN arrayBackedCopy(VecNLayout layout) {
		assert layout != null;

		if (layout().equals(layout)) {
			return VecN.of(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		return MVecN.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayVecN move() {
		return (ArrayVecN) MVecN.super.move();
	}
}
