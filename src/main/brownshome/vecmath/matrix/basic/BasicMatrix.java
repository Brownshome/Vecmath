package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.layout.MatrixLayout;

public record BasicMatrix(double[] backingArray, MatrixLayout layout) implements ArrayMatrix {
	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
