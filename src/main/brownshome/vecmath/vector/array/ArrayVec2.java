package brownshome.vecmath.vector.array;

import java.util.Arrays;

import brownshome.vecmath.rotation.MRot2;
import brownshome.vecmath.rotation.Rot2;
import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.basic.array.BasicArrayVec2;
import brownshome.vecmath.vector.generic.GenericArrayVec;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.layout.Vec2Layout;

/**
 * An array-backed 2-element vector
 */
public interface ArrayVec2 extends MVec2, GenericArrayVec<Vec2Layout, Vec2> {
	@Override
	default double x() {
		return backingArray()[layout().arrayIndex(0)];
	}

	@Override
	default double y() {
		return backingArray()[layout().arrayIndex(1)];
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
	default void set(Vec2 vec) {
		MVec2.super.set(vec);
	}

	@Override
	default void addToSelf(Vec2 vec) {
		MVec2.super.addToSelf(vec);
	}

	@Override
	default void scaleSelf(double scale) {
		MVec2.super.scaleSelf(scale);
	}

	@Override
	default void scaleSelf(Vec2 scale) {
		MVec2.super.scaleSelf(scale);
	}

	@Override
	default double dot(Vec2 vec) {
		return MVec2.super.dot(vec);
	}

	@Override
	default boolean exactEquals(Vec2 other) {
		return MVec2.super.exactEquals(other);
	}

	@Override
	default ArrayVecN asUnknownSize() {
		return VecN.of(backingArray(), layout().asVecNLayout());
	}

	@Override
	default MRot2 asRot() {
		return Rot2.of(backingArray(), layout());
	}

	@Override
	default ArrayVec2 asArrayBacked() {
		return (ArrayVec2) GenericArrayVec.super.asArrayBacked();
	}

	@Override
	default ArrayVec2 arrayBackedCopy(Vec2Layout layout) {
		assert layout != null;

		if (layout().equals(layout)) {
			return Vec2.of(Arrays.copyOf(backingArray(), layout().end()), layout);
		}

		return MVec2.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayVec2 move() {
		return (ArrayVec2) MVec2.super.move();
	}
}
