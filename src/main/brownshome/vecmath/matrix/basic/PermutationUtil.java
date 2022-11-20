package brownshome.vecmath.matrix.basic;

public record PermutationUtil() {
	public static final int[] IDENTITY_PERMUTATION = null;

	public static int[] combinePermutations(int[] existing, int[] nextPermutation) {
		if (existing == IDENTITY_PERMUTATION) {
			return nextPermutation;
		}

		if (nextPermutation == IDENTITY_PERMUTATION) {
			return existing;
		}

		int[] combined = new int[nextPermutation.length];

		boolean isIdentity = true;
		for (int i = 0; i < nextPermutation.length; i++) {
			combined[i] = existing[nextPermutation[i]];
			if (combined[i] != i) {
				isIdentity = false;
			}
		}

		return isIdentity ? IDENTITY_PERMUTATION : combined;
	}

	public static int[] invertPermutation(int[] permutation) {
		if (permutation == IDENTITY_PERMUTATION) {
			return IDENTITY_PERMUTATION;
		}

		int[] inverted = new int[permutation.length];
		boolean isIdentity = true;
		for (int i = 0; i < inverted.length; i++) {
			inverted[permutation[i]] = i;
			if (inverted[i] != i) {
				isIdentity = false;
			}
		}

		return isIdentity ? IDENTITY_PERMUTATION : inverted;
	}

	public static int parity(int[] permutation) {
		if (permutation == IDENTITY_PERMUTATION) {
			return 1;
		}

		// Count the number of odd cycles in the permutation
		boolean[] found = new boolean[permutation.length];
		int parity = 1;
		for (int i = 0; i < permutation.length; i++) {
			if (found[i]) {
				continue;
			}

			for (int j = permutation[i]; j != i; j = permutation[j]) {
				found[j] = true;
				parity = -parity;
			}
		}

		return parity;
	}
}
