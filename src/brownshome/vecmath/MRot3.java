package brownshome.vecmath;

public class MRot3 extends MVec4 implements Rot3 {
	public static MRot3 fromAxisAngle(Vec3 axis, double angle) {
		angle = angle / 2;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		
		return new MRot3(axis.x() * sin, axis.y() * sin, axis.z() * sin, cos);
	}
	
	public MRot3(Rot3 copy) {
		super(copy);
	}

	/** Sets this to be rot * this */
	public void multiplyLeft(Rot3 rot) {
		MRot3 tmp = new MRot3(rot);
		tmp.multiplyRight(this);
		set(tmp);
	}
	
	/** Sets this to be this * rot */
	public void multiplyRight(Rot3 rot) {
		set(
				rot.w() * x() + w() * rot.x() + y() * rot.z() - z() * rot.y(),
				rot.w() * y() + w() * rot.y() + z() * rot.x() - x() * rot.z(),
				rot.w() * z() + w() * rot.z() + x() * rot.y() - y() * rot.x(),
				
				rot.w() * w() - x() * rot.x() - y() * rot.y() - z() * rot.z()
		);
	}
	
	public void conj() {
		set(-x(), -y(), -x(), w());
	}
	
	public MRot3(double x, double y, double z, double w) {
		super(x, y, z, w);
	}
	
	/** The identity rotation */
	public MRot3() {
		this(0, 0, 0, 1);
	}
	
	public void invert() {
		w(-w());
	}
}
