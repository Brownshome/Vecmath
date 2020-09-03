package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MVec4Test {
	private MVec4 A;

	@BeforeEach
	void setUp() {
		A = new MVec4(1, 0, 0, 0);
	}

	@Test
	void addDoubles() {
		Vec4 result = A;
		A.add(1, -0.5, 0.5, 2);
		Vec4 expected = new IVec4(2, -0.5, 0.5, 2);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void subtract() {
		Vec4 result = A;
		A.subtract(new IVec4(1, -0.5, 0.5, 0));
		Vec4 expected = new IVec4(0, 0.5, -0.5, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void scaleVec() {
		Vec4 result = A;
		A.scale(new IVec4(0.5, -0.5, 0.5, 0));
		Vec4 expected = new IVec4(0.5, 0, 0, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void scaleDoubles() {
		Vec4 result = A;
		A.scale(0.5);
		Vec4 expected = new IVec4(0.5, 0, 0, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void normalize() {
		MVec4 result = new MVec4(2, 0, 2, 0);
		result.normalize();
		Vec4 expected = new IVec4(Math.sqrt(0.5), 0, Math.sqrt(0.5), 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void normalizeNan() {
		MVec4 result = new MVec4(0, 0, 0, 0);
		result.normalize();

		assertTrue(Double.isNaN(result.x()));
		assertTrue(Double.isNaN(result.y()));
		assertTrue(Double.isNaN(result.z()));
		assertTrue(Double.isNaN(result.w()));
	}

	@Test
	void addVec() {
		Vec4 result = A;
		A.add(new IVec4(2, -0.5, 0.5, 0));
		Vec4 expected = new IVec4(3, -0.5, 0.5, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void scaleAdd() {
		Vec4 result = A;
		A.scaleAdd(new IVec4(1, -0.5, 0.1, 1), 0.5);
		Vec4 expected = new IVec4(1.5, -0.25, 0.05, 0.5);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void negate() {
		Vec4 result = A;
		A.negate();
		Vec4 expected = new IVec4(-1, 0, 0, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}

	@Test
	void lerp() {
		Vec4 result = A;
		A.lerp(new IVec4(1, -0.5, 0.1, 0), 0.25);
		Vec4 expected = new IVec4(1, -0.125, 0.025, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
		assertEquals(expected.z(), result.z(), ACCURACY);
		assertEquals(expected.w(), result.w(), ACCURACY);
	}
}