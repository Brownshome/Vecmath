package brownshome.vecmath.matrix.factorisation.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.basic.DiagonalMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;

public record DiagonalFactorisation(double value, int size) implements Factorisation {
	public DiagonalFactorisation {
		assert size >= 0;
		assert value != 0.0;
	}

	@Override
	public Matrix leftSolve(Matrix other) {
		return other.scale(1.0 / value);
	}

	@Override
	public Matrix rightSolve(Matrix other) {
		return other.scale(1.0 / value);
	}

	@Override
	public Matrix inverse() {
		return new DiagonalMatrix(1.0 / value, size);
	}

	@Override
	public double determinant() {
		return Math.pow(value, size);
	}
}
