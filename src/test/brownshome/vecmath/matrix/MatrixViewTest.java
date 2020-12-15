package brownshome.vecmath.matrix;

import brownshome.vecmath.*;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MatrixViewTest {
	@Test
	void ofVec2() {
		var result = MatrixView.of(Vec2.Y_AXIS, Vec2.ZERO);
		var expected = Matrix.of(new double[] { 0, 1, 0, 0 }, 2, 2);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void ofVec3() {
		var result = MatrixView.of(Vec3.Y_AXIS, Vec3.ZERO);
		var expected = Matrix.of(new double[] { 0, 1, 0, 0, 0, 0 }, 2, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void ofVec4() {
		var result = MatrixView.of(Vec4.ZERO, Vec4.of(2, 1, -1, 0));
		var expected = Matrix.of(new double[] { 0, 0, 0, 0, 2, 1, -1, 0 }, 2, 4);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void concatByRows() {
		var result = MatrixView.concatByRows(
				MatrixView.of(new double[][] {
						{ 1, 2, 3, 4 },
						{ 5, 6, 7, 8 }
				}),
				MatrixView.of(new double[][] {
						{ 1, 2, 3, 4 },
						{ 8, 7, 6, 5 }
				}));

		var expected = MatrixView.of(new double[][] {
				{ 1, 2, 3, 4, 1, 2, 3, 4 },
				{ 5, 6, 7, 8, 8, 7, 6, 5 }
		}).asMatrix();

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void concatByColumns() {
		var result = MatrixView.concatByColumns(
				MatrixView.of(new double[][] {
						{ 1, 2, 3, 4 },
						{ 5, 6, 7, 8 }
				}),
				MatrixView.of(new double[][] {
						{ 1, 2, 3, 4 },
						{ 8, 7, 6, 5 }
				}));

		var expected = MatrixView.of(new double[][] {
				{ 1, 2, 3, 4 },
				{ 5, 6, 7, 8 },
				{ 1, 2, 3, 4 },
				{ 8, 7, 6, 5 }
		}).asMatrix();

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void identity() {
		MatrixView result = MatrixView.identity(3);
		Matrix expected = Matrix.of(new double[]{ 1, 0, 0, 0, 1, 0, 0, 0, 1 }, 3, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void zeros() {
		MatrixView result = MatrixView.zeros(2, 3);
		Matrix expected = Matrix.of(new double[6], 2, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void diagonal() {
		MatrixView result = MatrixView.diagonal(2.0, 3);
		Matrix expected = Matrix.of(new double[] { 2, 0, 0, 0, 2, 0, 0, 0, 2 }, 3, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void constant() {
		MatrixView result = MatrixView.constant(2.0, 2, 3);
		Matrix expected = Matrix.of(new double[] { 2, 2, 2, 2, 2, 2 }, 2, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void multiply() {
		Matrix result = Matrix.of(new double[] { 1, 0, 0.5, 0.5 }, 2, 2).multiply(Matrix.of(new double[] { 2, 0, 0, 1 }, 2, 2));
		Matrix expected = Matrix.of(new double[] { 2, 0, 1, 0.5 }, 2, 2);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.backingArray(), ACCURACY);
	}

	@Test
	void fastMultiply() {
		var fastChecker = new MatrixViewWithFastMultiply() {
			boolean wasLeftCalled = false;
			boolean wasGetCalled = false;

			@Override
			public Matrix leftMultiply(MatrixView other) {
				wasLeftCalled = true;
				return null;
			}

			@Override
			public Matrix multiply(MatrixView other) {
				return null;
			}

			@Override
			public int rows() {
				return 3;
			}

			@Override
			public int columns() {
				return 3;
			}

			@Override
			public double get(int row, int column) {
				wasGetCalled = true;

				return 0.0;
			}
		};

		new MatrixView() {
			@Override
			public int rows() {
				return 3;
			}

			@Override
			public int columns() {
				return 3;
			}

			@Override
			public double get(int row, int column) {
				return 0.0;
			}
		}.multiply(fastChecker);

		assertFalse(fastChecker.wasGetCalled);
		assertTrue(fastChecker.wasLeftCalled);
	}

	@Test
	void transpose() {
		var result = MatrixView.of(new double[][] {
				{ 1, 0 },
				{ 0.5, 0.5 }
		}).transpose();
		var expected = Matrix.of(new double[] { 1, 0.5, 0, 0.5 }, 2, 2);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray());
	}

	@Test
	void diagonalMultiply() {
		var result = MatrixView.diagonal(2.0, 3)
				.multiply(MatrixView.of(Vec3.of(1, 4, -2)).transpose());
		var expected = Matrix.of(new double[] { 2, 8, -4 }, 3, 1);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray());
	}
}