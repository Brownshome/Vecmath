package brownshome.vecmath.matrix;

import brownshome.vecmath.matrix.basic.PermutedMatrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import org.junit.jupiter.api.Test;

import static brownshome.vecmath.VecmathTesting.ACCURACY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PermutedMatrixTest {
	@Test
	void permuteRows() {
		var matrix = Matrix.of(new double[] {
				2, 3, 1,
				2, 1, 0
		}, MatrixLayout.ofRowMajor(2, 3));

		var result = PermutedMatrix.of(matrix, new int[] { 1, 0 }, new int[] { 1, 0, 2 })
				.permuteByColumn(0, 2, 1);
		var expected = Matrix.of(new double[] {
				1, 0, 2,
				3, 1, 2
		}, MatrixLayout.ofRowMajor(2, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}

	@Test
	void permuteColumns() {
		var matrix = Matrix.of(new double[] { 2, 3, 1, 2, 1, 0 }, MatrixLayout.ofRowMajor(2, 3));
		var result = PermutedMatrix.of(matrix, new int[] { 1, 0 }, new int[] { 1, 0, 2 })
				.permuteByRow(1, 0);
		var expected = Matrix.of(new double[] { 3, 2, 1, 1, 2, 0 }, MatrixLayout.ofRowMajor(2, 3));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}

	@Test
	void transpose() {
		var matrix = Matrix.of(new double[] { 2, 3, 1, 2, 1, 0 }, MatrixLayout.ofRowMajor(2, 3));
		var result = PermutedMatrix.of(matrix, new int[] { 1, 0 }, new int[] { 1, 0, 2 })
				.transpose();
		var expected = Matrix.of(new double[] { 1, 3, 2, 2, 0, 1 }, MatrixLayout.ofRowMajor(3, 2));

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.asArrayBacked().backingArray(), ACCURACY);
	}
}