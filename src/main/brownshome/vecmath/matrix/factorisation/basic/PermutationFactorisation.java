package brownshome.vecmath.matrix.factorisation.basic;

import java.util.Arrays;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.basic.PermutationMatrix;
import brownshome.vecmath.matrix.basic.PermutationUtil;
import brownshome.vecmath.matrix.factorisation.Factorisation;

public record PermutationFactorisation(int[] permutationRows) implements Factorisation {
	public PermutationFactorisation {
		assert permutationRows != null;
		assert Arrays.stream(permutationRows).distinct().count() == permutationRows.length;
		assert Arrays.stream(permutationRows).allMatch(i -> i < permutationRows.length && i >= 0);
		assert PermutationUtil.invertPermutation(permutationRows) != null;
	}

	@Override
	public int size() {
		return permutationRows.length;
	}

	@Override
	public Matrix leftSolve(Matrix other) {
		return other.permuteByRow(PermutationUtil.invertPermutation(permutationRows));
	}

	@Override
	public Matrix rightSolve(Matrix other) {
		return other.permuteByColumn(permutationRows);
	}

	@Override
	public Matrix inverse() {
		return new PermutationMatrix(PermutationUtil.invertPermutation(permutationRows));
	}

	@Override
	public double determinant() {
		return PermutationUtil.parity(permutationRows);
	}
}
