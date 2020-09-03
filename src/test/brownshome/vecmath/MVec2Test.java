package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static brownshome.vecmath.CompareConstant.*;

class MVec2Test {
	private MVec2 A;

	@BeforeEach
	void setUp() {
		A = IVec2.X_AXIS.mutable();
	}

	@Test
	void addDoubles() {
		Vec2 result = A;
		A.add(1, -0.5);
		Vec2 expected = new IVec2(2, -0.5);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void subtract() {
		Vec2 result = A;
		A.subtract(new IVec2(1, -0.5));
		Vec2 expected = new IVec2(0, 0.5);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void scaleVec() {
		Vec2 result = A;
		A.scale(new IVec2(0.5, -0.5));
		Vec2 expected = new IVec2(0.5, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void scaleDoubles() {
		Vec2 result = A;
		A.scale(0.5);
		Vec2 expected = new IVec2(0.5, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void normalize() {
		MVec2 result = new MVec2(2, 0);
		result.normalize();
		Vec2 expected = new IVec2(1, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void normalizeNan() {
		MVec2 result = new MVec2(0, 0);
		result.normalize();

		assertTrue(Double.isNaN(result.x()));
		assertTrue(Double.isNaN(result.y()));
	}

	@Test
	void addVec() {
		Vec2 result = A;
		A.add(new IVec2(2, -0.5));
		Vec2 expected = new IVec2(3, -0.5);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void tangentSelf() {
		Vec2 result = A;
		A.tangent();
		Vec2 expected = new IVec2(0, 1);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void scaleAdd() {
		Vec2 result = A;
		A.scaleAdd(new IVec2(1, -0.5), 0.5);
		Vec2 expected = new IVec2(1.5, -0.25);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void tangent() {
		Vec2 result = A;
		A.tangent(A);
		Vec2 expected = new IVec2(0, 1);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}

	@Test
	void negate() {
		Vec2 result = A;
		A.negate();
		Vec2 expected = new IVec2(-1, 0);

		assertEquals(expected.x(), result.x(), ACCURACY);
		assertEquals(expected.y(), result.y(), ACCURACY);
	}
}