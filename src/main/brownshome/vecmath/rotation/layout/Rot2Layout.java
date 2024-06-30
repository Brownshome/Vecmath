package brownshome.vecmath.rotation.layout;

import brownshome.vecmath.vector.layout.Vec2Layout;

/**
 * A layout object for 2D rotations
 */
public interface Rot2Layout extends Vec2Layout {
	default Rot2Layout asRot2() {
		return this;
	}
}
