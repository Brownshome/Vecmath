/**
 * A mutable vector mathematics library.
 *
 * If x is to the right, y is up and the coordinate system is right-handed:
 *  - 2D Rotations are counter-clockwise in the xy-plane
 *  - 3D Rotations given in axis-angle formulation are clockwise looking along the axis that are defined relative to
 */
module brownshome.vecmath {
	requires jdk.incubator.vector;

	exports brownshome.vecmath;
	exports brownshome.vecmath.matrix;
}
