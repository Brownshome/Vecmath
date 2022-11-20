package brownshome.vecmath.rotation;

import brownshome.vecmath.rotation.generic.GenericMRot;
import brownshome.vecmath.vector.MVec2;
import brownshome.vecmath.vector.Vec2;

public interface MRot2 extends GenericMRot<Vec2, MVec2, Vec2, Rot2>, Rot2, MVec2 {
	@Override
	default void setToInverted() {
		x(-x());
	}

	@Override
	default void multiplyRightSelf(Rot2 right) {
		set(x() * right.y() + right.x() * y(), y() * right.y() - x() * right.x());
	}

	@Override
	default MRot2 asRot() {
		return (MRot2) Rot2.super.asRot();
	}

	@Override
	default MRot2 move() {
		return (MRot2) GenericMRot.super.move();
	}
}
