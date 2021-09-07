package brownshome.vecmath.matrix;

/**
 * A marker interface noting that this matrix-view is symmetric
 */
public interface SymmetricMatrixView extends MatrixView {
	@Override
	default MatrixView multiply(MatrixView other) {
		if (other instanceof MatrixViewWithFastMultiply fastMultiply) {
			return fastMultiply.leftMultiply(this);
		}

		if (other instanceof SymmetricMatrixView sym) {
			double[] n = new double[sym.rows() * (sym.rows() + 1) / 2];

			for (int r = 0; r < rows(); r++) {
				for (int k = 0; k < columns(); k++) {
					for (int c = 0; c <= r; c++) {
						n[r * (r + 1) / 2 + c] += get(r, k) * other.get(k, c);
					}
				}
			}

			return SymmetricMatrix.of(n, sym.rows());
		}

		return MatrixView.super.multiply(other);
	}

	@Override
	default SymmetricMatrixView asSymmetricMatrix() {
		return this;
	}
}
