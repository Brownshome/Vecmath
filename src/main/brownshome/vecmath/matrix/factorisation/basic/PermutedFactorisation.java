package brownshome.vecmath.matrix.factorisation.basic;

import java.util.Arrays;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.basic.PermutationUtil;
import brownshome.vecmath.matrix.factorisation.Factorisation;

public record PermutedFactorisation(Factorisation delegate, int[] rowPermutation, int[] columnPermutation) implements Factorisation {
	public PermutedFactorisation {
		assert delegate != null;
		assert rowPermutation != PermutationUtil.IDENTITY_PERMUTATION || columnPermutation != PermutationUtil.IDENTITY_PERMUTATION;

		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || rowPermutation.length >= 2;
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || rowPermutation.length == delegate.size();
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(rowPermutation).distinct().count() == delegate.size();
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(rowPermutation).allMatch(i -> i < rowPermutation.length && i >= 0);
		assert rowPermutation == PermutationUtil.IDENTITY_PERMUTATION || PermutationUtil.invertPermutation(rowPermutation) != PermutationUtil.IDENTITY_PERMUTATION;

		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || columnPermutation.length >= 2;
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || columnPermutation.length == delegate.size();
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(columnPermutation).distinct().count() == delegate.size();
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || Arrays.stream(columnPermutation).allMatch(i -> i < columnPermutation.length && i >= 0);
		assert columnPermutation == PermutationUtil.IDENTITY_PERMUTATION || PermutationUtil.invertPermutation(columnPermutation) != PermutationUtil.IDENTITY_PERMUTATION;
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public Matrix leftSolve(Matrix other) {
		return delegate
				.leftSolve(other.permuteByRow(PermutationUtil.invertPermutation(rowPermutation)))
				.permuteByRow(columnPermutation);
	}

	@Override
	public Matrix rightSolve(Matrix other) {
		return delegate
				.rightSolve(other.permuteByColumn(PermutationUtil.invertPermutation(columnPermutation)))
				.permuteByColumn(rowPermutation);
	}

	@Override
	public Matrix inverse() {
		return delegate.inverse().permuteByRow(columnPermutation).permuteByColumn(rowPermutation);
	}

	@Override
	public double determinant() {
		return delegate.determinant() * (PermutationUtil.parity(rowPermutation) * PermutationUtil.parity(columnPermutation));
	}
}
