/**
 * A mutable vector mathematics library.
 * <p>
 * If x is to the right, y is up and the coordinate system is right-handed:
 *  - 2D Rotations are counter-clockwise in the xy-plane
 *  - 3D Rotations given in axis-angle formulation are clockwise looking along the axis that are defined relative to
 */
module brownshome.vecmath {
	exports brownshome.vecmath.matrix;

	exports brownshome.vecmath.vector;
	exports brownshome.vecmath.vector.array;
	exports brownshome.vecmath.vector.layout;

	exports brownshome.vecmath.rotation;
	exports brownshome.vecmath.rotation.array;
}
