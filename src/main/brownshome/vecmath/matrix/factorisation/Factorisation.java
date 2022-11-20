package brownshome.vecmath.matrix.factorisation;

import brownshome.vecmath.matrix.Matrix;

/**
 * A factorisation of a matrix that allows the swift computation of the solutions to AX = B, XA = B, AX = I, |A| = x
 */
public interface Factorisation {
	int size();

	/**
	 * Computes the solution to AX = B, or in other words finds A \ B
	 * @param other B
	 * @return the solution X to AX = B
	 */
	Matrix leftSolve(Matrix other);

	/**
	 * Computes the solution to XA = B, or in other words finds B / A
	 * @param other B
	 * @return the solution X to XA = B
	 */
	Matrix rightSolve(Matrix other);

	/**
	 * Finds A⁻¹
	 * @return the inverse of A
	 */
	default Matrix inverse() {
		return leftSolve(Matrix.identity(size()));
	}

	/**
	 * Finds the determinant of A
	 * @return det(A)
	 */
	double determinant();
}
