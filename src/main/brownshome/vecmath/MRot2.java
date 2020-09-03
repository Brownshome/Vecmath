package brownshome.vecmath;

public class MRot2 extends MVec2 implements Rot2 {
	public MRot2(Rot2 copy) {
		super(copy);
	}
	
	public MRot2(double angle) {
		super(Math.cos(angle), Math.sin(angle));
	}
	
	public MRot2(double x, double y) {
		super(x, y);
	}
	
	/** The identity rotation */
	public MRot2() {
		this(1, 0);
	}

	@Override
	public double sin() {
		return y();
	}

	@Override
	public double cos() {
		return x();
	}
	
	public void invert() {
		sin(-sin());
	}
	
	public void sin(double sin) {
		y(sin);
	}
	
	public void cos(double cos) {
		x(cos);
	}
}
