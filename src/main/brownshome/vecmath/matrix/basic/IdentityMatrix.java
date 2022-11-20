package brownshome.vecmath.matrix.basic;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.IdentityFactorisation;
import brownshome.vecmath.vector.VecN;

public final class IdentityMatrix extends DiagonalMatrix {
	public IdentityMatrix(int size) {
		super(1.0, size);
	}

	@Override
	public VecN row(int r) {
		return VecN.axis(r, size());
	}

	@Override
	public VecN column(int c) {
		return VecN.axis(c, size());
	}

	@Override
	public Matrix permuteByRow(int... rows) {
		if (rows == PermutationUtil.IDENTITY_PERMUTATION) {
			return this;
		}

		assert rows.length == size();

		return new PermutationMatrix(rows);
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		if (columns == PermutationUtil.IDENTITY_PERMUTATION) {
			return this;
		}

		assert columns.length == size();

		return new PermutationMatrix(columns).transpose();
	}

	@Override
	public Matrix subMatrix(int r, int c, int rows, int columns) {
		assert r >= 0;
		assert r + rows <= rows();
		assert c >= 0;
		assert c + columns <= columns();

		if (r == c && rows == columns) {
			return new IdentityMatrix(rows);
		}

		return super.subMatrix(r, c, rows, columns);
	}

	@Override
	public Factorisation factorisation(double tolerance) {
		return new IdentityFactorisation(size());
	}

	@Override
	public Matrix multiply(Matrix other) {
		assert other.rows() == size();

		return other;
	}

	@Override
	public Matrix multiplyLeft(Matrix matrix) {
		assert matrix.columns() == size();

		return matrix;
	}
}
