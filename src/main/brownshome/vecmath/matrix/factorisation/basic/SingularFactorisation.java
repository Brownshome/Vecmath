package brownshome.vecmath.matrix.factorisation.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.SingularMatrixException;

public record SingularFactorisation(int size) implements Factorisation {
	@Override
	public Matrix leftSolve(Matrix other) {
		throw new SingularMatrixException();
	}

	@Override
	public Matrix rightSolve(Matrix other) {
		throw new SingularMatrixException();
	}

	@Override
	public Matrix inverse() {
		throw new SingularMatrixException();
	}

	@Override
	public double determinant() {
		return 0;
	}
}
