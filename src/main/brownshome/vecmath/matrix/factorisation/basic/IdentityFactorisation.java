package brownshome.vecmath.matrix.factorisation.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.basic.IdentityMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;

public record IdentityFactorisation(int size) implements Factorisation {
	@Override
	public Matrix leftSolve(Matrix other) {
		assert other.rows() == size;

		return other;
	}

	@Override
	public Matrix rightSolve(Matrix other) {
		assert other.columns() == size;

		return other;
	}

	@Override
	public Matrix inverse() {
		return new IdentityMatrix(size);
	}

	@Override
	public double determinant() {
		return 1.0;
	}
}
