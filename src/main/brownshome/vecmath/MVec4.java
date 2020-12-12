package brownshome.vecmath;

/** Represents a mutable Vec4, meaning that it can be edited by anyone who has the reference. */
public interface MVec4 extends Vec4 {
	void x(double x);

	void y(double y);

	void z(double z);

	void w(double w);

	default void set(double x, double y, double z, double w) {
		x(x);
		y(y);
		z(z);
		w(w);
	}

	default void set(Vec4 v) {
		set(v.x(), v.y(), v.z(), v.w());
	}

	default void add(Vec4 vec) {
		add(vec.x(), vec.y(), vec.z(), vec.w());
	}

	default void subtract(Vec4 vec) {
		add(-vec.x(), -vec.y(), -vec.z(), -vec.w());
	}

	default void scale(double scale) {
		set(x() * scale, y() * scale, z() * scale, w() * scale);
	}

	default void scale(Vec4 scale) {
		set(scale.x() * x(), scale.y() * y(), scale.z() * z(), scale.w() * w());
	}

	/**
	 * Sets this vector to have a length of one. This will set the vector to NaN if
	 * the length is zero.
	 */
	default void normalize() {
		double scale = 1 / length();
		scale(scale);
	}

	/**
	 * Adds an amount to this vector
	 * @param x The amount to add to the x value
	 * @param y The amount to add to the y value
	 * @param z The amount to add to the z value
	 * @param w The amount to add to the w value
	 */
	default void add(double x, double y, double z, double w) {
		set(x() + x, y() + y, z() + z, w() + w);
	}

	/**
	 * Performs a linear interpolation between this and other.
	 * this = this * (1-t) + other * t
	 * @param other The other vector to interpolate to
	 * @param t The time factor
	 */
	default void lerp(Vec4 other, double t) {
		scale(1.0 - t);
		scaleAdd(other, t);
	}

	/**
	 * Sets this to be this + vec * scale
	 * @param vec The vector to add
	 * @param scale The number to scale it by
	 */
	default void scaleAdd(Vec4 vec, double scale) {
		set(x() + vec.x() * scale, y() + vec.y() * scale, z() + vec.z() * scale, w() + vec.w() * scale);
	}

	default void negate() {
		set(-x(), -y(), -z(), -w());
	}
}
