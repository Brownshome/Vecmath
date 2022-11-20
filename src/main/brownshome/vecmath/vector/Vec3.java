package brownshome.vecmath.vector;

import brownshome.vecmath.vector.array.ArrayVec3;
import brownshome.vecmath.vector.array.ArrayVecN;
import brownshome.vecmath.vector.basic.BasicVec3;
import brownshome.vecmath.vector.basic.array.BasicArrayVec3;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.layout.Vec3Layout;

/**
 * A 3-element vector
 */
public interface Vec3 extends GenericVec<Vec3> {
	/**
	 * The vector (0, 0, 0)
	 */
	Vec3 ZERO = new BasicVec3(0, 0, 0);

	/**
	 * The vector (1, 0, 0)
	 */
	Vec3 X_AXIS = new BasicVec3(1, 0, 0);

	/**
	 * The vector (0, 1, 0)
	 */
	Vec3 Y_AXIS = new BasicVec3(0, 1, 0);

	/**
	 * The vector (0, 0, 1)
	 */
	Vec3 Z_AXIS = new BasicVec3(0, 0, 1);

	/**
	 * Creates a new vector with the given components
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 * @return a newly created vector
	 */
	static MVec3 of(double x, double y, double z) {
		return new BasicVec3(x, y, z);
	}

	/**
	 * Creates an array-backed vector. The vector will have an optimal array layout for the given platform.
	 * @return a vector
	 */
	static ArrayVec3 ofArrayBacked() {
		return of(Vec3Layout.ofOptimal());
	}

	/**
	 * Creates an array-backed vector with the given layout
	 * @param layout the layout
	 * @return a vector
	 */
	static ArrayVec3 of(Vec3Layout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a vector
	 */
	static ArrayVec3 of(double[] array, Vec3Layout layout) {
		return new BasicArrayVec3(array, layout);
	}

	double x();
	double y();
	double z();
	
	@Override
	default double dot(Vec3 vec) {
 		return vec.x() * x() + vec.y() * y() + vec.z() * z();
	}

	/**
	 * The cross product between this vector and the given one
	 * @param vec the other vector
	 * @return the cross product between this vector and the given vector
	 */
	default Vec3 cross(Vec3 vec) {
		var result = copy();
		result.setToRightCross(vec);
		return result;
	}

	@Override
	default boolean exactEquals(Vec3 other) {
		return x() == other.x() && y() == other.y() && z() == other.z();
	}

	@Override
	default VecN asUnknownSize() {
		return new VecN() {
			@Override
			public int size() {
				return 3;
			}

			@Override
			public double get(int i) {
				assert i < size();

				return switch (i) {
					case 0 -> x();
					case 1 -> y();
					default -> z();
				};
			}

			@Override
			public Vec3 asVec3() {
				return Vec3.this;
			}

			@Override
			public ArrayVecN asArrayBacked() {
				return Vec3.this.asArrayBacked().asUnknownSize();
			}

			@Override
			public String toString() {
				return Vec3.this.toString();
			}
		};
	}

	@Override
	default ArrayVec3 asArrayBacked() {
		return (ArrayVec3) GenericVec.super.asArrayBacked();
	}

	@Override
	default ArrayVec3 arrayBackedCopy() {
		return arrayBackedCopy(Vec3Layout.ofOptimal());
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	default ArrayVec3 arrayBackedCopy(Vec3Layout layout) {
		var result = Vec3.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MVec3 copy() {
		return new BasicVec3(this);
	}

	@Override
	default MVec3 move() {
		return (MVec3) GenericVec.super.move();
	}
}
