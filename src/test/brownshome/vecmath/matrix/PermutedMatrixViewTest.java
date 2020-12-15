package brownshome.vecmath.matrix;

import org.junit.jupiter.api.Test;

import static brownshome.vecmath.CompareConstant.ACCURACY;
import static org.junit.jupiter.api.Assertions.*;

class PermutedMatrixViewTest {
	@Test
	void permuteRows() {
		var matrix = Matrix.of(new double[] { 2, 3, 1, 2, 1, 0 }, 2, 3);
		var result = PermutedMatrixView.of(matrix, new int[] { 1, 0 }, new int[] { 1, 0, 2 })
				.permuteColumns(0, 2, 1);
		var expected = Matrix.of(new double[] { 1, 0, 2, 3, 1, 2 }, 2, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void permuteColumns() {
		var matrix = Matrix.of(new double[] { 2, 3, 1, 2, 1, 0 }, 2, 3);
		var result = PermutedMatrixView.of(matrix, new int[] { 1, 0 }, new int[] { 1, 0, 2 })
				.permuteRows(1, 0);
		var expected = Matrix.of(new double[] { 3, 2, 1, 1, 2, 0 }, 2, 3);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}

	@Test
	void transpose() {
		var matrix = Matrix.of(new double[] { 2, 3, 1, 2, 1, 0 }, 2, 3);
		var result = PermutedMatrixView.of(matrix, new int[] { 1, 0 }, new int[] { 1, 0, 2 })
				.transpose();
		var expected = Matrix.of(new double[] { 1, 3, 2, 2, 0, 1 }, 3, 2);

		assertEquals(expected.rows(), result.rows());
		assertEquals(expected.columns(), result.columns());
		assertArrayEquals(expected.backingArray(), result.copy().backingArray(), ACCURACY);
	}
}