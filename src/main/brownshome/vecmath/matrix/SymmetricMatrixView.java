package brownshome.vecmath.matrix;

/**
 * A marker interface noting that this matrix-view is symmetric
 */
public interface SymmetricMatrixView extends MatrixView {
	/**
	 * Creates a symmetric matrix from the given lower triangular row-major array
	 * @param array a row-major array of matrix elements with all elements above the main diagonal missing
	 * @param size the size of the matrix
	 * @return a newly constructed symmetric matrix backed by the given array
	 */
	static SymmetricMatrixView of(double[] array, int size) {
		return SymmetricMatrix.of(array, size);
	}

	@Override
	default SymmetricMatrixView asSymmetricMatrix() {
		return this;
	}

	@Override
	default Factorisation factorise() {
		return new CholeskyFactorisation(SymmetricMatrix.of(this), 1e-10);
	}
}
