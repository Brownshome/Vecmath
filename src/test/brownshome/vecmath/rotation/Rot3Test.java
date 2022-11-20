package brownshome.vecmath.rotation;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.*;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.*;
import static org.junit.jupiter.api.Assertions.*;

final class Rot3Test {
	private final Rot3 A = Rot3.ofAxisAngle(Vec3.X_AXIS, Math.toRadians(90));
	private final Rot3 B = Rot3.ofAxisAngle(Vec3.Y_AXIS, Math.toRadians(90));

	@Test
	void angle() {
		assertEquals(Math.toRadians(90.0), A.angle(), ACCURACY);
		assertEquals(Math.toRadians(90.0), B.angle(), ACCURACY);
	}

	@Test
	void rotate() {
		assertVecEquals(Vec3.Z_AXIS, A.rotated(Vec3.Y_AXIS));
	}

	@Test
	void asMatrix() {
		assertMatrixEquals(Matrix.of(new double[] {
				1.0, 0.0, 0.0,
				0.0, 0.0, -1.0,
				0.0, 1.0, 0.0
		}, MatrixLayout.ofRowMajor(3, 3)), A.asMatrix());
	}

	@Test
	void fromAxisAngle() {
		assertVecEquals(Rot3.of(Math.sqrt(0.5), 0, 0, Math.sqrt(0.5)), A);
	}

	@Test
	void inverted() {
		assertVecEquals(Rot3.ofAxisAngle(Vec3.X_AXIS.negated(), Math.toRadians(90)), A.inverted());
	}

	@Test
	void multiply() {
		assertVecEquals(Rot3.of(0.5, 0.5, 0.5, 0.5), A.multiply(B));
	}
}
