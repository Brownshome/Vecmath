package brownshome.vecmath;

/** Represents a mutable Vec3, meaning that it can be edited by anyone who has the reference. */
public class MVec3 implements Vec3 {
	private double x, y, z;
	
	public MVec3(Vec3 copy) {
		this(copy.x(), copy.y(), copy.z());
	}
	
	public MVec3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public MVec3() {
		this(0, 0, 0);
	}
	
	public void set(Vec3 v) {
		set(v.x(), v.y(), v.z());
	}
	
	public void set(double x, double y, double z) {
		x(x);
		y(y);
		z(z);
	}
	
	@Override
	public double x() {
		return x;
	}
	
	@Override
	public double y() {
		return y;
	}
	
	@Override
	public double z() {
		return z;
	}
	
	public void x(double x) {
		this.x = x;
	}
	
	public void y(double y) {
		this.y = y;
	}
	
	public void z(double z) {
		this.z = z;
	}
	
	public void add(Vec3 vec) {
		add(vec.x(), vec.y(), vec.z());
	}

	public void subtract(Vec3 vec) {
		add(-vec.x(), -vec.y(), -vec.z());
	}

	public void scale(double scale) {
		set(x() * scale, y() * scale, z() * scale);
	}

	/**
	 * Sets this vector to have a length of one. This will set the vector to NaN if
	 * the length is zero.
	 */
	public void normalize() {
		double scale = 1 / length();
		scale(scale);
	}

	/**
	 * Adds an amount to this vector
	 * @param x The amount to add to the x value
	 * @param y The amount to add to the y value
	 * @param z The amount to add to the z value
	 */
	public void add(double x, double y, double z) {
		set(x() + x, y() + y, z() + z);
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f)", x(), y(), z());
	}

	/**
	 * Performs a linear interpolation between this and other.
	 * this = this * (1-t) + other * t
	 * @param other The other vector to interpolate to
	 * @param t The time factor
	 */
	public void lerp(Vec3 other, double t) {
		scale(1.0 - t);
		scaleAdd(other, t);
	}

	/**
	 * Sets this to be this + vec * scale
	 * @param vec The vector to add
	 * @param scale The number to scale it by
	 */
	public void scaleAdd(Vec3 vec, double scale) {
		set(x() + vec.x() * scale, y() + vec.y() * scale, z() + vec.z() * scale);
	}

	public void negate() {
		set(-x(), -y(), -z());
	}
	
	/** Gets an angle between this vector and the given vector in radians*/
	public double angle(Vec3 vec) {
		double angle = this.dot(vec) / (this.length() * vec.length());
		angle = Math.acos(angle);
		return angle;
	}
}
