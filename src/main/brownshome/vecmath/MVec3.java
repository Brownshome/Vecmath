package brownshome.vecmath;

/** Represents a mutable Vec3, meaning that it can be edited by anyone who has the reference. */
public interface MVec3 extends Vec3 {
	void x(double x);

	void y(double y);

	void z(double z);

	default void set(Vec3 v) {
		set(v.x(), v.y(), v.z());
	}

	default void set(double x, double y, double z) {
		x(x);
		y(y);
		z(z);
	}

	default void add(Vec3 vec) {
		add(vec.x(), vec.y(), vec.z());
	}

	default void subtract(Vec3 vec) {
		add(-vec.x(), -vec.y(), -vec.z());
	}

	default void scale(double scale) {
		set(x() * scale, y() * scale, z() * scale);
	}

	default void scale(Vec3 scale) {
		set(scale.x() * x(), scale.y() * y(), scale.z() * z());
	}

	default void normalize() {
		double scale = 1 / length();
		scale(scale);
	}

	default void add(double x, double y, double z) {
		set(x() + x, y() + y, z() + z);
	}

	default void lerp(Vec3 other, double t) {
		scale(1.0 - t);
		scaleAdd(other, t);
	}

	default void cross(Vec3 l, Vec3 r) {
		double xc, yc, zc;

		xc = l.y() * r.z() - l.z() * r.y();
		yc = l.z() * r.x() - l.x() * r.z();
		zc = l.x() * r.y() - l.y() * r.x();

		set(xc, yc, zc);
	}

	default void scaleAdd(Vec3 vec, double scale) {
		set(x() + vec.x() * scale, y() + vec.y() * scale, z() + vec.z() * scale);
	}

	default void negate() {
		set(-x(), -y(), -z());
	}
}
