package brownshome.vecmath.matrix.basic;

import java.util.Arrays;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.MatrixWithFastMultiply;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.factorisation.basic.PermutationFactorisation;

public record PermutationMatrix(int[] rowPermutation) implements MatrixWithFastMultiply {
	public PermutationMatrix {
		assert rowPermutation != null;
		assert Arrays.stream(rowPermutation).distinct().count() == rowPermutation.length;
		assert Arrays.stream(rowPermutation).allMatch(i -> i < rowPermutation.length && i >= 0);
		assert PermutationUtil.invertPermutation(rowPermutation) != PermutationUtil.IDENTITY_PERMUTATION;
	}

	@Override
	public int rows() {
		return rowPermutation.length;
	}

	@Override
	public int columns() {
		return rowPermutation.length;
	}

	@Override
	public double get(int row, int column) {
		assert column < columns();

		return rowPermutation[row] == column ? 1.0 : 0.0;
	}

	@Override
	public Matrix multiply(Matrix other) {
		return other.permuteByRow(rowPermutation);
	}

	@Override
	public Matrix multiplyLeft(Matrix other) {
		return other.permuteByColumn(PermutationUtil.invertPermutation(rowPermutation));
	}

	@Override
	public Matrix transpose() {
		return new PermutationMatrix(PermutationUtil.invertPermutation(rowPermutation));
	}

	@Override
	public Matrix permuteByRow(int... rows) {
		if (rows == PermutationUtil.IDENTITY_PERMUTATION) {
			return this;
		}

		assert rows.length == rows();

		int[] combined = PermutationUtil.combinePermutations(rowPermutation, rows);

		if (combined == PermutationUtil.IDENTITY_PERMUTATION) {
			return new IdentityMatrix(rows());
		}

		return new PermutationMatrix(combined);
	}

	@Override
	public Matrix permuteByColumn(int... columns) {
		if (columns == PermutationUtil.IDENTITY_PERMUTATION) {
			return this;
		}

		assert columns.length == columns();

		int[] combined = PermutationUtil.combinePermutations(columns, rowPermutation);

		if (combined == PermutationUtil.IDENTITY_PERMUTATION) {
			return new IdentityMatrix(rows());
		}

		return new PermutationMatrix(combined);
	}

	@Override
	public boolean isRowOptimal() {
		return true;
	}

	@Override
	public boolean isColumnOptimal() {
		return true;
	}

	@Override
	public Factorisation factorisation(double tolerance) {
		return new PermutationFactorisation(rowPermutation);
	}

	@Override
	public Matrix asSymmetric() {
		throw new UnsupportedOperationException("Permutation matrices are not symmetric");
	}

	@Override
	public MMatrix asSymmetricCopy() {
		throw new UnsupportedOperationException("Permutation matrices are not symmetric");
	}

	@Override
	public String toString() {
		return Matrix.toString(this);
	}
}
