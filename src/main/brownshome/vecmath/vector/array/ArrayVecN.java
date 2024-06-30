package brownshome.vecmath.vector.array;

import java.util.Arrays;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.vector.*;
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
		if (vec instanceof ArrayVecN arrayVec && layout().isContinuous() && arrayVec.layout().equals(layout())) {
			GenericArrayVec.super.set(vec);
			return;
		}

		MVecN.super.set(vec);
	}

	@Override
	default int size() {
		return layout().elements();
	}

	@Override
	default void addToSelf(VecN vec) {
		if (vec instanceof ArrayVecN arrayVecN && layout().isContinuous() && arrayVecN.layout().equals(layout())) {
			GenericArrayVec.super.addToSelf(vec);
			return;
		}

		MVecN.super.addToSelf(vec);
	}

	@Override
	default void scaleSelf(double scale) {
		if (layout().isContinuous()) {
			GenericArrayVec.super.scaleSelf(scale);
			return;
		}

		MVecN.super.scaleSelf(scale);
	}

	@Override
	default void scaleSelf(VecN scale) {
		if (scale instanceof ArrayVecN arrayVecN && layout().isContinuous() && arrayVecN.layout().equals(layout())) {
			GenericArrayVec.super.scaleSelf(scale);
			return;
		}

		MVecN.super.addToSelf(scale);
	}

	@Override
	default double dot(VecN vec) {
		if (vec instanceof ArrayVecN arrayVecN && layout().isContinuous() && arrayVecN.layout().equals(layout())) {
			return GenericArrayVec.super.dot(vec);
		}

		return MVecN.super.dot(vec);
	}

	@Override
	default boolean exactEquals(VecN other) {
		if (other instanceof ArrayVecN arrayVecN && layout().isContinuous() && arrayVecN.layout().equals(layout())) {
			return GenericArrayVec.super.exactEquals(other);
		}

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
		return Vec2.of(backingArray(), layout().asVec2());
	}

	@Override
	default ArrayVec3 asVec3() {
		return Vec3.of(backingArray(), layout().asVec3());
	}

	@Override
	default ArrayVec4 asVec4() {
		return Vec4.of(backingArray(), layout().asVec4());
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
	default MMatrix asRow() {
		return GenericArrayVec.super.asRow();
	}

	@Override
	default MMatrix asColumn() {
		return GenericArrayVec.super.asColumn();
	}

	@Override
	default ArrayVecN move() {
		return (ArrayVecN) MVecN.super.move();
	}
}
