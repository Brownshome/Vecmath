package brownshome.vecmath.matrix;

import brownshome.vecmath.matrix.basic.PermutationMatrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class PermutationMatrixTest {
	@Test
	void construct() {
		var result = new PermutationMatrix(new int[] { 2, 1, 0 });
		var expected = Matrix.of(new double[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 }, MatrixLayout.ofRowMajor(3, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}

	@Test
	void transpose() {
		var result = new PermutationMatrix(new int[] { 2, 0, 1 }).transpose();
		var expected = Matrix.of(new double[] { 0, 1, 0, 0, 0, 1, 1, 0, 0 }, MatrixLayout.ofRowMajor(3, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}

	@Test
	void permuteRows() {
		var result = new PermutationMatrix(new int[] { 2, 0, 1 }).permuteByRow(0, 2, 1);
		var expected = Matrix.of(new double[] { 0, 0, 1, 0, 1, 0, 1, 0, 0 }, MatrixLayout.ofRowMajor(3, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}

	@Test
	void permuteColumns() {
		var result = new PermutationMatrix(new int[] { 2, 0, 1 }).permuteByColumn(0, 2, 1);
		var expected = Matrix.of(new double[] { 0, 1, 0, 1, 0, 0, 0, 0, 1 }, MatrixLayout.ofRowMajor(3, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}

	@Test
	void determinant() {
		var result = new PermutationMatrix(new int[] { 2, 0, 1 }).determinant();
		var expected = 1;

		assertEquals(expected, result);
	}

	@Test
	void leftSolve() {
		var result = new PermutationMatrix(new int[] { 2, 0, 1 })
				.factorisation().leftSolve(Matrix.of(new double[] {
						4, 1,
						2, 1,
						2, 3
				}, MatrixLayout.ofRowMajor(3, 2)));
		var expected = Matrix.of(new double[] {
				2, 1,
				2, 3,
				4, 1
		}, MatrixLayout.ofRowMajor(3, 2));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.arrayBackedCopy().backingArray(), ACCURACY);
	}

	@Test
	void rightSolve() {
		var result = new PermutationMatrix(new int[] { 2, 0, 1 })
				.factorisation().rightSolve(Matrix.of(new double[] {
						4, 1, 2,
						1, 2, 3
				}, MatrixLayout.ofRowMajor(2, 3)));
		var expected = Matrix.of(new double[] { 2, 4, 1, 3, 1, 2 }, MatrixLayout.ofRowMajor(2, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.arrayBackedCopy().backingArray(), ACCURACY);
	}

	@Test
	void inverse() {
		var result = new PermutationMatrix(new int[] { 2, 0, 1 }).invert();
		var expected = Matrix.of(new double[] { 0, 1, 0, 0, 0, 1, 1, 0, 0 }, MatrixLayout.ofRowMajor(3, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}

	@Test
	void transposeOfTranspose() {
		var expected = new PermutationMatrix(new int[] { 2, 0, 1 });
		var result = expected.transpose().transpose();

		assert(result.exactEquals(expected));
	}
}