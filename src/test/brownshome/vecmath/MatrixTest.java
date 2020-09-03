package brownshome.vecmath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
	private Matrix A;

	@BeforeEach
	void setUp() {
		A = new Matrix(new double[] { 1, 0, 0.5, 0.5 }, 2, 2);
	}

	@Test
	void add() {
		Matrix result = A.add(new Matrix(new double[] { 1, 0, 0, 1 }, 2, 2));
		Matrix expected = new Matrix(new double[] { 2, 0, 0.5, 1.5 }, 2, 2);

		assertArrayEquals(expected.backingArray(), result.backingArray(), ACCURACY);
	}

	@Test
	void scaleAdd() {
		Matrix result = A.scaleAdd(2, new Matrix(new double[] { 1, 0, 0, 1 }, 2, 2));
		Matrix expected = new Matrix(new double[] { 3, 0, 0.5, 2.5 }, 2, 2);

		assertArrayEquals(expected.backingArray(), result.backingArray(), ACCURACY);
	}

	@Test
	void multiply() {
		Matrix result = A.multiply(new Matrix(new double[] { 2, 0, 0, 1 }, 2, 2));
		Matrix expected = new Matrix(new double[] { 2, 0, 1, 0.5 }, 2, 2);

		assertArrayEquals(expected.backingArray(), result.backingArray(), ACCURACY);
	}

	@Test
	void transpose() {
		Matrix result = A.transpose();
		Matrix expected = new Matrix(new double[] { 1, 0.5, 0, 0.5 }, 2, 2);

		assertArrayEquals(expected.backingArray(), result.backingArray(), ACCURACY);
	}

	@Test
	void resize() {
		Matrix result = A.resize(1, 4);
		Matrix expected = new Matrix(new double[] { 1, 0, 0.5, 0.5 }, 1, 4);

		assertArrayEquals(expected.backingArray(), result.backingArray(), ACCURACY);
	}
}