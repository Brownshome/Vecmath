package brownshome.vecmath.rotation.layout;

import brownshome.vecmath.vector.layout.Vec4Layout;

/**
 * The layout of a 3D rotation in an array
 */
public interface Rot3Layout extends Vec4Layout {
	/**
	 * Gets an optimal layout
	 * @return a layout
	 */
	static Rot3Layout ofOptimal() {
		return (Rot3Layout) Vec4Layout.ofOptimal();
	}

	/**
	 * Gets a packed layout
	 * @return a layout
	 */
	static Rot3Layout ofPacked() {
		return (Rot3Layout) Vec4Layout.ofPacked();
	}

	/**
	 * Gets a rotation layout with a given offset
	 * @param offset the offset
	 * @return a layout
	 */
	static Rot3Layout of(int offset) {
		return (Rot3Layout) Vec4Layout.of(offset);
	}

	/**
	 * Gets a rotation layout with a given offset and stride
	 * @param offset the offset
	 * @param stride the stride
	 * @return a layout
	 */
	static Rot3Layout of(int offset, int stride) {
		return (Rot3Layout) Vec4Layout.of(offset, stride);
	}

	default Rot3Layout asRot3() {
		return this;
	}
}
