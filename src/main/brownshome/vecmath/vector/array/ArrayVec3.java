package brownshome.vecmath.vector.array;

import java.util.Arrays;

import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.basic.array.BasicArrayVec3;
import brownshome.vecmath.vector.layout.Vec3Layout;
import brownshome.vecmath.vector.generic.GenericArrayVec;

/**
 * An array-backed 3-element vector
 */
public interface ArrayVec3 extends MVec3, GenericArrayVec<Vec3Layout, Vec3> {
	@Override
	default double x() {
		return backingArray()[layout().arrayIndex(0)];
	}

	@Override
	default double y() {
		return backingArray()[layout().arrayIndex(1)];
	}

	@Override
	default double z() {
		return backingArray()[layout().arrayIndex(2)];
	}

	@Override
	default void x(double x) {
		backingArray()[layout().arrayIndex(0)] = x;
	}

	@Override
	default void y(double y) {
		backingArray()[layout().arrayIndex(1)] = y;
	}

	@Override
	default void z(double z) {
		backingArray()[layout().arrayIndex(2)] = z;
	}

	@Override
	default void set(Vec3 vec) {
		MVec3.super.set(vec);
	}

	@Override
	default void addToSelf(Vec3 vec) {
		MVec3.super.addToSelf(vec);
	}

	@Override
	default void scaleSelf(double scale) {
		MVec3.super.scaleSelf(scale);
	}

	@Override
	default void scaleSelf(Vec3 scale) {
		MVec3.super.scaleSelf(scale);
	}

	@Override
	default double dot(Vec3 vec) {
		return MVec3.super.dot(vec);
	}

	@Override
	default boolean exactEquals(Vec3 other) {
		return MVec3.super.exactEquals(other);
	}

	@Override
	default ArrayVecN asUnknownSize() {
		return VecN.of(backingArray(), layout().asVecNLayout());
	}

	@Override
	default ArrayVec3 asArrayBacked() {
		return (ArrayVec3) GenericArrayVec.super.asArrayBacked();
	}

	@Override
	default ArrayVec3 arrayBackedCopy(Vec3Layout layout) {
		assert layout != null;

		if (layout().equals(layout)) {
			return Vec3.of(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		return MVec3.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayVec3 move() {
		return (ArrayVec3) MVec3.super.move();
	}
}
