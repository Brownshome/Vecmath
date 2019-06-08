package brownshome.vecmath;

/** Represents a mutable Vec4, meaning that it can be edited by anyone who has the reference. */
public class MVec4 implements Vec4 {
	private double x, y, z, w;
	
	public MVec4(Vec4 copy) {
		this(copy.x(), copy.y(), copy.z(), copy.w());
	}
	
	public MVec4(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public MVec4() {
		this(0, 0, 0, 0);
	}
	
	public void set(Vec4 v) {
		set(v.x(), v.y(), v.z(), v.w());
	}
	
	public void set(double x, double y, double z, double w) {
		x(x);
		y(y);
		z(z);
		w(w);
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
	
	@Override
	public double w() {
		return w;
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
	
	public void w(double w) {
		this.w = w;
	}
	
	public void add(Vec4 vec) {
		add(vec.x(), vec.y(), vec.z(), vec.w());
	}

	public void subtract(Vec4 vec) {
		add(-vec.x(), -vec.y(), -vec.z(), -vec.w());
	}

	public void scale(double scale) {
		set(x() * scale, y() * scale, z() * scale, w() * scale);
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
	 * @param w The amount to add to the w value
	 */
	public void add(double x, double y, double z, double w) {
		set(x() + x, y() + y, z() + z, w() + w);
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f, %.3f, %.3f)", x(), y(), z(), w());
	}

	/**
	 * Performs a linear interpolation between this and other.
	 * this = this * (1-t) + other * t
	 * @param other The other vector to interpolate to
	 * @param t The time factor
	 */
	public void lerp(Vec4 other, double t) {
		scale(1.0 - t);
		scaleAdd(other, t);
	}

	/**
	 * Sets this to be this + vec * scale
	 * @param vec The vector to add
	 * @param scale The number to scale it by
	 */
	public void scaleAdd(Vec4 vec, double scale) {
		set(x() + vec.x() * scale, y() + vec.y() * scale, z() + vec.z() * scale, w() + vec.w() * scale);
	}

	public void negate() {
		set(-x(), -y(), -z(), -w());
	}
	
	/** Gets an angle between this vector and the given vector in radians*/
	public double angle(Vec4 vec) {
		double angle = this.dot(vec) / (this.length() * vec.length());
		angle = Math.acos(angle);
		return angle;
	}

	/**
	 * Returns an mutable version of this object. This object's changes will effect the original if it was mutable.
	 **/
	@Override
	public MVec4 mutable() {
		return this;
	}
}
