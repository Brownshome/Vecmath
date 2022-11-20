package brownshome.vecmath.matrix;

import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
	private ArrayMatrix A;

	@BeforeEach
	void setUp() {
		A = Matrix.of(new double[] { 1, 0, 0.5, 0.5 }, MatrixLayout.ofRowMajor(2, 2));
	}

	@Test
	void subMatrix() {
		var subMatrix = Matrix.of(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }, MatrixLayout.ofRowMajor(3, 3)).subMatrix(1, 1, 2, 1);
		var expected = Matrix.of(new double[] { 5, 8 }, MatrixLayout.ofRowMajor(2, 1));

		assertEquals(expected.rows(), subMatrix.rows());
		assertEquals(expected.columns(), subMatrix.columns());
		assertArrayEquals(expected.backingArray(), subMatrix.arrayBackedCopy().backingArray());
	}

	@Test
	void add() {
		A.addToSelf(Matrix.of(new double[] { 1, 0, 0, 1 }, MatrixLayout.ofRowMajor(2, 2)));
		var expected = Matrix.of(new double[] { 2, 0, 0.5, 1.5 }, MatrixLayout.ofRowMajor(2, 2));

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray(), ACCURACY);
	}

	@Test
	void scaleAdd() {
		A.scaleAddToSelf(Matrix.of(new double[] { 1, 0, 0, 1 }, MatrixLayout.ofRowMajor(2, 2)), 2);
		var expected = Matrix.of(new double[] { 3, 0, 0.5, 2.5 }, MatrixLayout.ofRowMajor(2, 2));

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray(), ACCURACY);
	}

	@Test
	void set() {
		A.set(Matrix.of(new double[] { 1, 0, 0, 1 }, MatrixLayout.ofRowMajor(2, 2)));
		var expected = Matrix.of(new double[] { 1, 0, 0, 1 }, MatrixLayout.ofRowMajor(2, 2));

		assertEquals(expected.rows(), A.rows());
		assertEquals(expected.columns(), A.columns());
		assertArrayEquals(expected.backingArray(), A.backingArray());
	}

	@Test
	void transpose() {
		var result = Matrix.of(new double[] { 1, 2, 3, 4, 5, 6 }, MatrixLayout.ofRowMajor(2, 3)).transpose().arrayBackedCopy();
		var expected = Matrix.of(new double[]{ 1, 4, 2, 5, 3, 6 }, MatrixLayout.ofRowMajor(3, 2)).arrayBackedCopy();

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.backingArray());
	}

	@Test
	void constant() {
		var result = Matrix.constant(5.0, 2, 3);
		var expected = Matrix.of(new double[] {
				5.0, 5.0, 5.0,
				5.0, 5.0, 5.0
		}, MatrixLayout.ofRowMajor(2, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray());
	}

	@Test
	void identity() {
		var result = Matrix.identity(2);
		var expected = Matrix.of(new double[] {
				1.0, 0.0,
				0.0, 1.0
		}, MatrixLayout.ofRowMajor(2, 2));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray());
	}

	@Test
	void zeros() {
		var result = Matrix.zero(2, 3);
		var expected = Matrix.of(new double[] {
				0.0, 0.0, 0.0,
				0.0, 0.0, 0.0,
		}, MatrixLayout.ofRowMajor(2, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray());
	}

	@Test
	void diagonal() {
		var result = Matrix.diagonal(1.0, 2);
		var expected = Matrix.of(new double[] {
				1.0, 0.0,
				0.0, 1.0
		}, MatrixLayout.ofRowMajor(2, 2));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray());
	}

	@Test
	void scale() {
		var result = Matrix.identity(6).scale(5.0);
		var expected = Matrix.diagonal(5.0, 6);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.asArrayBacked().backingArray(), result.asArrayBacked().backingArray());
	}

	@Test
	void scaleSlowPath() {
		var result = Matrix.identity(6).subMatrix(0, 0, 3, 3).scale(5.0);
		var expected = Matrix.diagonal(5.0, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.asArrayBacked().backingArray(), result.asArrayBacked().backingArray());
	}

	@Test
	void scaleMatrix() {
		var result = Matrix.identity(6).scale(Matrix.diagonal(5.0, 6));
		var expected = Matrix.diagonal(5.0, 6);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.asArrayBacked().backingArray(), result.asArrayBacked().backingArray());
	}
}