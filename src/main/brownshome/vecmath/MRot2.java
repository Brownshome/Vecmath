package brownshome.vecmath;

public interface MRot2 extends MVec2, Rot2 {
	default void invert() {
		sin(-sin());
	}

	default void sin(double sin) {
		y(sin);
	}

	default void cos(double cos) {
		x(cos);
	}
}
