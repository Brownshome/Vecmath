package brownshome.vecmath.matrix;

import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class SymmetricMatrixTest {
	@Test
	void get() {
		var a = SymmetricMatrix.of(new double[] {
				0,
				1, .5,
				1, -1, 2
		}, 3);

		assertEquals(-1, a.get(2, 1));
		assertEquals(-1, a.get(1, 2));
	}

	@Test
	void copy() {
		var result = SymmetricMatrix.of(new double[] {
				0,
				1, .5,
				1, -1, 2
		}, 3).copy();
		var expected = Matrix.of(new double[] {
				0,  1, 1,
				1, .5, -1,
				1, -1, 2
		}, 3, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.backingArray());
	}

	@Test
	void transpose() {
		var expected = SymmetricMatrix.of(new double[] {
				0,
				1, .5,
				1, -1, 2
		}, 3);

		assertSame(expected, expected.transpose());
	}

	@Test
	void factorise() {
		var result = SymmetricMatrix.of(new double[] {
				0,
				1, .5,
				1, -1, 2
		}, 3).factorise();

		assertTrue(result instanceof CholeskyFactorisation);
	}
}