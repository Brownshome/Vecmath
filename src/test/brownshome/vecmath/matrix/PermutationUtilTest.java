package brownshome.vecmath.matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PermutationUtilTest {
	@Test
	void combinePermutations() {
		int[] expected = new int[] { 2, 1, 0 };
		int[] result = PermutationUtil.combinePermutations(new int[] { 0, 2, 1 }, new int[] { 1, 2, 0 });

		assertArrayEquals(expected, result);
	}

	@Test
	void combinePermutationsWithNull() {
		int[] expected = new int[] { 0, 2, 1 };
		int[] result = PermutationUtil.combinePermutations(new int[] { 0, 2, 1 }, null);

		assertArrayEquals(expected, result);
	}

	@Test
	void combinePermutationsToNull() {
		int[] expected = new int[] { 0, 2, 1 };
		int[] result = PermutationUtil.combinePermutations(null, new int[] { 0, 2, 1 });

		assertArrayEquals(expected, result);
	}

	@Test
	void combinePermutationsNullWithNull() {
		int[] result = PermutationUtil.combinePermutations(null, null);

		assertNull(result);
	}

	@Test
	void combinePermutationsToIdentity() {
		int[] result = PermutationUtil.combinePermutations(new int[] { 0, 2, 1 }, new int[] { 0, 2, 1 });

		assertNull(result);
	}

	@Test
	void invertPermutation() {
		int[] expected = new int[] { 2, 0, 1 };
		int[] result = PermutationUtil.invertPermutation(new int[] { 1, 2, 0 });

		assertArrayEquals(expected, result);
	}

	@Test
	void invertPermutationIdentity() {
		int[] result = PermutationUtil.invertPermutation(new int[] { 0, 1, 2 });

		assertNull(result);
	}

	@Test
	void invertPermutationNull() {
		int[] result = PermutationUtil.invertPermutation(null);

		assertNull(result);
	}

	@Test
	void parityNull() {
		int expected = 1;
		int result = PermutationUtil.parity(null);

		assertEquals(expected, result);
	}

	@Test
	void parityIdentity() {
		int expected = 1;
		int result = PermutationUtil.parity(new int[] { 0, 1, 2 });

		assertEquals(expected, result);
	}

	@Test
	void parity() {
		int expected = -1;
		int result = PermutationUtil.parity(new int[] { 1, 0, 2 });

		assertEquals(expected, result);
	}
}