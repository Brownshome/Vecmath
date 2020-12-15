package brownshome.vecmath.matrix;

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
	MatrixView leftSolve(MatrixView other);

	/**
	 * Computes the solution to XA = B, or in other words finds B / A
	 * @param other B
	 * @return the solution X to XA = B
	 */
	MatrixView rightSolve(MatrixView other);

	/**
	 * Finds A^-1
	 * @return the inverse of A
	 */
	default MatrixView inverse() {
		return leftSolve(MatrixView.identity(size()));
	}

	/**
	 * Finds the determinant of A
	 * @return det(A)
	 */
	double determinant();
}
