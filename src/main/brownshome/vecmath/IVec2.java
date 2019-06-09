package brownshome.vecmath;

public class IVec2 implements Vec2 {
	public static final IVec2 ZERO = new IVec2(0, 0);
	public static final IVec2 X_AXIS = new IVec2(1, 0);
	public static final IVec2 Y_AXIS = new IVec2(0, 1);

	private final double x, y;
	
	public IVec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public IVec2(Vec2 copy) {
		this.x = copy.x();
		this.y = copy.y();
	}
	
	@Override
	public final double x() {
		return x;
	}

	@Override
	public final double y() {
		return y;
	}
	
	@Override
	public String toString() {
		return String.format("(%.3f, %.3f)", x(), y());
	}

	/**
	 * Returns an immutable version of this object.
	 **/
	@Override
	public IVec2 immutable() {
		return this;
	}
}
