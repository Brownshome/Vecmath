package brownshome.vecmath.matrix;

import java.util.Arrays;

final class ArrayMatrixView implements MatrixView {
	private final double[][] array;

	ArrayMatrixView(double[][] array) {
		assert array != null;
		assert array.length != 0;
		assert array[0].length != 0;
		assert Arrays.stream(array).mapToInt(row -> row.length).distinct().count() == 1;

		this.array = array;
	}

	@Override
	public int rows() {
		return array.length;
	}

	@Override
	public int columns() {
		return array[0].length;
	}

	@Override
	public double get(int row, int column) {
		return array[row][column];
	}
}
