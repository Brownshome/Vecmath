package brownshome.vecmath.vector;

import brownshome.vecmath.rotation.Rot3;
import brownshome.vecmath.vector.array.ArrayVec4;
import brownshome.vecmath.vector.array.ArrayVecN;
import brownshome.vecmath.vector.basic.BasicVec4;
import brownshome.vecmath.vector.basic.array.BasicArrayVec4;
import brownshome.vecmath.vector.generic.GenericVec;
import brownshome.vecmath.vector.layout.Vec4Layout;
import brownshome.vecmath.vector.layout.VecNLayout;

/**
 * A 4-element vector
 */
public interface Vec4 extends GenericVec<Vec4> {
	/**
	 * The vector (0, 0, 0, 0)
	 */
	Vec4 ZERO = new BasicVec4(0, 0, 0, 0);

	/**
	 * Creates a new vector with the given components
	 * @param x the x component
	 * @param y the y component
	 * @param z the z component
	 * @param w the w component
	 * @return a newly created vector
	 */
	static MVec4 of(double x, double y, double z, double w) {
		return new BasicVec4(x, y, z, w);
	}

	/**
	 * Creates an array-backed vector. The vector will have an optimal array layout for the given platform.
	 * @return a vector
	 */
	static ArrayVec4 ofArrayBacked() {
		return of(Vec4Layout.ofOptimal());
	}

	/**
	 * Creates an array-backed vector with the given layout
	 * @param layout the layout
	 * @return a vector
	 */
	static ArrayVec4 of(Vec4Layout layout) {
		return of(new double[layout.end()], layout);
	}

	/**
	 * Creates a vector from the components in this array. Edits to the array will change this vector and vice-versa.
	 * @param array the array
	 * @param layout the layout of the array
	 * @return a vector
	 */
	static ArrayVec4 of(double[] array, Vec4Layout layout) {
		return new BasicArrayVec4(array, layout);
	}

	double x();
	double y();
	double z();
	double w();
	
	@Override
	default double dot(Vec4 vec) {
		return vec.x() * x() + vec.y() * y() + vec.z() * z() + vec.w() * w();
	}
	
	@Override
	default boolean exactEquals(Vec4 other) {
		return x() == other.x() && y() == other.y() && z() == other.z() && w() == other.w();
	}

	@Override
	default VecN asUnknownSize() {
		return new VecN() {
			@Override
			public int size() {
				return 4;
			}

			@Override
			public double get(int i) {
				assert i < size();

				return switch (i) {
					case 0 -> x();
					case 1 -> y();
					case 2 -> z();
					default -> w();
				};
			}

			@Override
			public Vec4 asVec4() {
				return Vec4.this;
			}

			@Override
			public ArrayVecN asArrayBacked() {
				return Vec4.this.asArrayBacked().asUnknownSize();
			}

			@Override
			public ArrayVecN arrayBackedCopy(VecNLayout layout) {
				return Vec4.this.arrayBackedCopy(layout.asVec4Layout()).asUnknownSize();
			}

			@Override
			public String toString() {
				return Vec4.this.toString();
			}
		};
	}

	/**
	 * Returns this vector as a 3D rotation
	 * @return a 3D rotation
	 */
	default Rot3 asRot() {
		return new Rot3() {
			@Override
			public double x() {
				return Vec4.this.x();
			}

			@Override
			public double y() {
				return Vec4.this.y();
			}

			@Override
			public double z() {
				return Vec4.this.z();
			}

			@Override
			public double w() {
				return Vec4.this.w();
			}
		};
	}

	@Override
	default ArrayVec4 asArrayBacked() {
		return (ArrayVec4) GenericVec.super.asArrayBacked();
	}

	@Override
	default ArrayVec4 arrayBackedCopy() {
		return arrayBackedCopy(Vec4Layout.ofOptimal());
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	default ArrayVec4 arrayBackedCopy(Vec4Layout layout) {
		var result = Vec4.of(layout);
		result.set(this);
		return result;
	}

	@Override
	default MVec4 copy() {
		return new BasicVec4(this);
	}

	@Override
	default MVec4 move() {
		return (MVec4) GenericVec.super.move();
	}
}
