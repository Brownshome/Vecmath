package brownshome.vecmath.matrix;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
	private Matrix A;

	@BeforeEach
	void setUp() {
		A = Matrix.of(new double[] { 1, 0, 0.5, 0.5 }, 2, 2);
	}

	@Test
	void subMatrix() {
		Matrix subMatrix = Matrix.of(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, 3, 3).subMatrix(1, 1, 2, 1);
		Matrix expected = Matrix.of(new double[] { 5, 8 }, 2, 1);

		assertEquals(expected.rows(), subMatrix.rows());
		assertEquals(expected.columns(), subMatrix.columns());
		assertArrayEquals(expected.backingArray(), subMatrix.copy().backingArray());
	}

	@Test
	void add() {
		A.add(Matrix.of(new double[] { 1, 0, 0, 1 }, 2, 2));
		Matrix expected = Matrix.of(new double[] { 2, 0, 0.5, 1.5 }, 2, 2);

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray(), ACCURACY);
	}

	@Test
	void scaleAdd() {
		A.scaleAdd(2, Matrix.of(new double[] { 1, 0, 0, 1 }, 2, 2));
		Matrix expected = Matrix.of(new double[] { 3, 0, 0.5, 2.5 }, 2, 2);

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray(), ACCURACY);
	}

	@Test
	void set() {
		A.set(Matrix.of(new double[] { 1, 0, 0, 1 }, 2, 2));
		Matrix expected = Matrix.of(new double[] { 1, 0, 0, 1 }, 2, 2);

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray());
	}

	@Test
	void addView() {
		A.add(MatrixView.of(new double[] { 1, 0 }, new double[] { 0, 1 }));
		Matrix expected = Matrix.of(new double[] { 2, 0, 0.5, 1.5 }, 2, 2);

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray(), ACCURACY);
	}

	@Test
	void scaleAddView() {
		A.scaleAdd(2, MatrixView.of(new double[] { 1, 0 }, new double[] { 0, 1 }));
		Matrix expected = Matrix.of(new double[] { 3, 0, 0.5, 2.5 }, 2, 2);

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray(), ACCURACY);
	}

	@Test
	void setView() {
		A.set(MatrixView.of(new double[] { 1, 0 }, new double[] { 0, 1 }));
		Matrix expected = Matrix.of(new double[] { 1, 0, 0, 1 }, 2, 2);

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray());
	}

	@Test
	void transpose() {
		Matrix result = Matrix.of(new double[] { 1, 2, 3, 4, 5, 6 }, 2, 3).transpose().copy();
		Matrix expected = Matrix.of(new double[]{ 1, 4, 2, 5, 3, 6 }, 3, 2);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.backingArray());
	}

	@Test
	void asMatrix() {
		Matrix m = Matrix.of(new double[6], 2, 3);

		assertSame(m, m.asMatrix());
	}
}