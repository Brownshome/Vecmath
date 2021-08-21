package brownshome.vecmath.matrix;

final class SingularFactorisation implements Factorisation {
	private final int size;

	SingularFactorisation(int size) {
		this.size = size;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public MatrixView leftSolve(MatrixView other) {
		throw new SingularMatrixException();
	}

	@Override
	public MatrixView rightSolve(MatrixView other) {
		throw new SingularMatrixException();
	}

	@Override
	public MatrixView inverse() {
		throw new SingularMatrixException();
	}

	@Override
	public double determinant() {
		return 0;
	}
}
