package brownshome.vecmath.matrix.basic;

import java.util.Arrays;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;

public record NestedArrayMatrix(double[][] rowArrays) implements MMatrix {
	public NestedArrayMatrix {
		assert rowArrays != null;
		assert rowArrays.length != 0;
		assert rowArrays[0].length != 0;
		assert Arrays.stream(rowArrays).mapToInt(row -> row.length).distinct().count() == 1;
	}

	@Override
	public int rows() {
		return rowArrays.length;
	}

	@Override
	public int columns() {
		return rowArrays[0].length;
	}

	@Override
	public double get(int row, int column) {
		return rowArrays[row][column];
	}

	@Override
	public void set(double value, int row, int column) {
		rowArrays[row][column] = value;
	}

	@Override
	public boolean isRowOptimal() {
		return true;
	}

	@Override
	public MVecN row(int r) {
		return VecN.of(rowArrays[r]);
	}

	@Override
	public MMatrix permuteByRow(int... rows) {
		if (rows == PermutationUtil.IDENTITY_PERMUTATION) {
			return this;
		}

		var result = new double[rows()][];
		for (int old = 0; old < rows.length; old++) {
			result[rows[old]] = rowArrays[old];
		}

		return new NestedArrayMatrix(result);
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
