package brownshome.vecmath;

public class IRot2 extends IVec2 implements Rot2 {
	public IRot2(double x, double y) {
		super(x, y);
	}
	
	public IRot2(double angle) {
		this(Math.cos(angle), -Math.sin(angle));
	}
	
	public IRot2() {
		this(1, 0);
	}
	
	public IRot2(Rot2 r) {
		this(r.x(), r.y());
	}
	
	@Override
	public final double sin() {
		return y();
	}

	@Override
	public final double cos() {
		return x();
	}

	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}

	/**
	 * Returns an immutable version of this object.
	 **/
	@Override
	public IRot2 immutable() {
		return this;
	}
}
