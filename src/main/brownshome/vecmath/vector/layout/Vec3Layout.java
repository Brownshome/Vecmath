package brownshome.vecmath.vector.layout;

import brownshome.vecmath.basic.layout.BasicColumnLayout;
import brownshome.vecmath.vector.generic.GenericVecLayout;

/**
 * The layout of an array-backed 3-element vector
 */
public interface Vec3Layout extends GenericVecLayout {
	/**
	 * Gets an optimal layout
	 * @return a layout
	 */
	static Vec3Layout ofOptimal() {
		return of(0);
	}

	/**
	 * Gets a packed layout
	 * @return a layout
	 */
	static Vec3Layout ofPacked() {
		return of(0);
	}

	/**
	 * Gets a vector layout with a given offset
	 * @param offset the offset
	 * @return a layout
	 */
	static Vec3Layout of(int offset) {
		return of(offset, 1);
	}

	/**
	 * Gets a vector layout with a given offset and stride
	 * @param offset the offset
	 * @param stride the stride
	 * @return a layout
	 */
	static Vec3Layout of(int offset, int stride) {
		return new BasicColumnLayout(3, offset, stride);
	}
}
