package brownshome.vecmath.matrix;

import jdk.incubator.vector.DoubleVector;

final class VectorisationUtil {
	private VectorisationUtil() { }

	static int roundUpToVectorLength(int length) {
		return (length + DoubleVector.SPECIES_PREFERRED.length() - 1)
				/ DoubleVector.SPECIES_PREFERRED.length()
				* DoubleVector.SPECIES_PREFERRED.length();
	}
}
