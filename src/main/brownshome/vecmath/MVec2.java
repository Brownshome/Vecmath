package brownshome.vecmath;

/** Represents a mutable Vec2, meaning that it can be edited by anyone who has the reference. */
public interface MVec2 extends Vec2 {
	void x(double x);

	void y(double y);

	default void set(Vec2 v) {
		set(v.x(), v.y());
	}

	default void set(double x, double y) {
		x(x);
		y(y);
	}

	default void add(Vec2 vec) {
		add(vec.x(), vec.y());
	}

	default void subtract(Vec2 vec) {
		add(-vec.x(), -vec.y());
	}

	default void scale(double scale) {
		set(x() * scale, y() * scale);
	}

	default void scale(Vec2 scale) {
		set(scale.x() * x(), scale.y() * y());
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
	 * @param x The amount to the x value
	 * @param y The amount to add to the y value
	 */
	default void add(double x, double y) {
		set(x() + x, y() + y);
	}

	/**
	 * Sets this vector to be the tangent of the given vector. This function rotates the vector counterclockwise in the x-right, y-up coordinate system
	 */
	default void tangent(Vec2 v) {
		set(-v.y(), v.x());
	}

	/**
	 * Performs a linear interpolation between this and other.
	 * this = this * (1-t) + other * t
	 * @param other The other vector to interpolate to
	 * @param t The time factor
	 */
	default void lerp(Vec2 other, double t) {
		scale(1.0 - t);
		scaleAdd(other, t);
	}

	/**
	 * Sets this to be this + vec * scale
	 * @param vec The vector to add
	 * @param scale The number to scale it by
	 */
	default void scaleAdd(Vec2 vec, double scale) {
		set(x() + vec.x() * scale, y() + vec.y() * scale);
	}

	/**
	 * Sets this vector to be the tangent of itself. This function rotates the vector counterclockwise in the x-right, y-up coordinate system
	 */
	default void tangent() {
		tangent(this);
	}

	default void negate() {
		set(-x(), -y());
	}
}
