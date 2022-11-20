package brownshome.vecmath.rotation;

import brownshome.vecmath.rotation.generic.GenericMRot;
import brownshome.vecmath.vector.*;

public interface MRot3 extends GenericMRot<Vec3, MVec3, Vec4, Rot3>, Rot3, MVec4 {
	@Override
	default void setToInverted() {
		x(-x());
		y(-y());
		z(-z());
	}

	@Override
	default void multiplyRightSelf(Rot3 right) {
		set(
				right.w() * x() + w() * right.x() + y() * right.z() - z() * right.y(),
				right.w() * y() + w() * right.y() + z() * right.x() - x() * right.z(),
				right.w() * z() + w() * right.z() + x() * right.y() - y() * right.x(),

				right.w() * w() - x() * right.x() - y() * right.y() - z() * right.z()
		);
	}

	@Override
	default MRot3 asRot() {
		return (MRot3) Rot3.super.asRot();
	}

	@Override
	default MRot3 move() {
		return (MRot3) GenericMRot.super.move();
	}
}
