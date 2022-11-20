package brownshome.vecmath.rotation;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.Vec2;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.*;
import static org.junit.jupiter.api.Assertions.*;

final class Rot2Test {
	private final Rot2 A = Rot2.ofAngle(Math.toRadians(20.0));
	private final Rot2 B = Rot2.ofAngle(Math.toRadians(-120.0));

	@Test
	void angle() {
		assertEquals(Math.toRadians(20.0), A.angle(), ACCURACY);
		assertEquals(Math.toRadians(120.0), B.angle(), ACCURACY);
	}

	@Test
	void rotate() {
		assertVecEquals(Vec2.of(-0.5, -Math.sqrt(0.75)), B.rotated(Vec2.of(1.0, 0.0)));
	}

	@Test
	void multiply() {
		assertVecEquals(Rot2.ofAngle(Math.toRadians(-100.0)), B.multiply(A));
	}

	@Test
	void asMatrix() {
		assertMatrixEquals(Matrix.of(new double[] {
				-0.5, Math.sqrt(0.75),
				-Math.sqrt(0.75), -0.5
		}, MatrixLayout.ofRowMajor(2, 2)), B.asMatrix());
	}

	@Test
	void invert() {
		assertEquals(A.inverted().angularDistance(Rot2.ofAngle(Math.toRadians(-20.0))), 0.0, ACCURACY);
	}
}
