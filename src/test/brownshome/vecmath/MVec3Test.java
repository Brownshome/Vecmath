package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MVec3Test {
	private MVec3 A;

	@BeforeEach
	void setUp() {
		A = Vec3.X_AXIS.copy();
	}

	@Test
	void addDoubles() {
		Vec3 result = A;
		A.add(1, -0.5, 0.5);
		Vec3 expected = Vec3.of(2, -0.5, 0.5);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void subtract() {
		Vec3 result = A;
		A.subtract(Vec3.of(1, -0.5, 0.5));
		Vec3 expected = Vec3.of(0, 0.5, -0.5);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void scaleVec() {
		Vec3 result = A;
		A.scale(Vec3.of(0.5, -0.5, 0.5));
		Vec3 expected = Vec3.of(0.5, 0, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void scaleDoubles() {
		Vec3 result = A;
		A.scale(0.5);
		Vec3 expected = Vec3.of(0.5, 0, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void normalize() {
		MVec3 result = Vec3.of(2, 0, 2);
		result.normalize();
		Vec3 expected = Vec3.of(Math.sqrt(0.5), 0, Math.sqrt(0.5));

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void normalizeNan() {
		MVec3 result = Vec3.of(0, 0, 0);
		result.normalize();

		assertTrue(Double.isNaN(result.x()));
		assertTrue(Double.isNaN(result.y()));
		assertTrue(Double.isNaN(result.z()));
	}

	@Test
	void addVec() {
		Vec3 result = A;
		A.add(Vec3.of(2, -0.5, 0.5));
		Vec3 expected = Vec3.of(3, -0.5, 0.5);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void scaleAdd() {
		Vec3 result = A;
		A.scaleAdd(Vec3.of(1, -0.5, 0.1), 0.5);
		Vec3 expected = Vec3.of(1.5, -0.25, 0.05);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void negate() {
		Vec3 result = A;
		A.negate();
		Vec3 expected = Vec3.of(-1, 0, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void lerp() {
		Vec3 result = A;
		A.lerp(Vec3.of(1, -0.5, 0.1), 0.25);
		Vec3 expected = Vec3.of(1, -0.125, 0.025);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}

	@Test
	void cross() {
		Vec3 result = A;
		A.cross(Vec3.of(0, 0, 1), A);
		Vec3 expected = Vec3.of(0, 1, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
	}
}