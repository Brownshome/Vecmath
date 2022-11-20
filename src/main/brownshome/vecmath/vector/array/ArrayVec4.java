package brownshome.vecmath.vector.array;

import java.util.Arrays;

import brownshome.vecmath.rotation.MRot2;
import brownshome.vecmath.rotation.MRot3;
import brownshome.vecmath.rotation.Rot2;
import brownshome.vecmath.rotation.Rot3;
import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.basic.array.BasicArrayVec4;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.generic.GenericArrayVec;

/**
 * An array-backed 4-element vector
 */
public interface ArrayVec4 extends MVec4, GenericArrayVec<Vec4Layout, Vec4> {
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
	default double w() {
		return backingArray()[layout().arrayIndex(3)];
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
	default void w(double w) {
		backingArray()[layout().arrayIndex(3)] = w;
	}

	@Override
	default void set(Vec4 vec) {
		MVec4.super.set(vec);
	}

	@Override
	default void addToSelf(Vec4 vec) {
		MVec4.super.addToSelf(vec);
	}

	@Override
	default void scaleSelf(double scale) {
		MVec4.super.scaleSelf(scale);
	}

	@Override
	default void scaleSelf(Vec4 scale) {
		MVec4.super.scaleSelf(scale);
	}

	@Override
	default double dot(Vec4 vec) {
		return MVec4.super.dot(vec);
	}

	@Override
	default boolean exactEquals(Vec4 other) {
		return MVec4.super.exactEquals(other);
	}

	@Override
	default ArrayVecN asUnknownSize() {
		return VecN.of(backingArray(), layout().asVecNLayout());
	}

	@Override
	default MRot3 asRot() {
		return Rot3.of(backingArray(), layout());
	}

	@Override
	default ArrayVec4 asArrayBacked() {
		return (ArrayVec4) GenericArrayVec.super.asArrayBacked();
	}

	@Override
	default ArrayVec4 arrayBackedCopy(Vec4Layout layout) {
		assert layout != null;

		if (layout().equals(layout)) {
			return Vec4.of(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		return MVec4.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayVec4 move() {
		return (ArrayVec4) MVec4.super.move();
	}
}
