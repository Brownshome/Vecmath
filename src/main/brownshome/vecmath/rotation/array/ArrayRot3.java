package brownshome.vecmath.rotation.array;

import brownshome.vecmath.rotation.MRot3;
import brownshome.vecmath.vector.array.ArrayVec4;
import brownshome.vecmath.vector.layout.Vec4Layout;

public interface ArrayRot3 extends MRot3, ArrayVec4 {
	@Override
	default MRot3 asRot() {
		return MRot3.super.asRot();
	}

	@Override
	default ArrayRot3 asArrayBacked() {
		return (ArrayRot3) ArrayVec4.super.asArrayBacked();
	}

	@Override
	default ArrayRot3 arrayBackedCopy(Vec4Layout layout) {
		return (ArrayRot3) ArrayVec4.super.arrayBackedCopy(layout);
	}

	@Override
	default ArrayRot3 move() {
		return (ArrayRot3) MRot3.super.move();
	}
}
