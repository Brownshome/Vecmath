package brownshome.vecmath.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMatrixViewTest {
	@Test
	void testArrayMatrix() {
		var matrix = MatrixView.of(new double[][] {
				{ 1, 2, 3 },
				{ 3, 2, 1 },
				{ 5, 5, 5 },
				{ 1, 7, 0 }
		});

		assertEquals(4, matrix.rows());
		assertEquals(3, matrix.columns());
		assertEquals(7, matrix.get(3, 1));
	}
}