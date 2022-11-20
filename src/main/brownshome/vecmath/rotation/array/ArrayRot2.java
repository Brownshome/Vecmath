package brownshome.vecmath.rotation.array;

import brownshome.vecmath.rotation.MRot2;
import brownshome.vecmath.rotation.MRot3;
import brownshome.vecmath.vector.array.ArrayVec2;
import brownshome.vecmath.vector.layout.Vec2Layout;

public interface ArrayRot2 extends MRot2, ArrayVec2 {
	@Override
	default MRot2 asRot() {
		return MRot2.super.asRot();
	}

	@Override
	default ArrayRot2 asArrayBacked() {
		return (ArrayRot2) ArrayVec2.super.asArrayBacked();
	}

	@Override
	default ArrayRot2 arrayBackedCopy(Vec2Layout layout) {
		return (ArrayRot2) ArrayVec2.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayRot2 move() {
		return (ArrayRot2) MRot2.super.move();
	}
}
