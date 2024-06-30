package brownshome.vecmath.vector.layout;

import brownshome.vecmath.basic.layout.BasicColumnLayout;
import brownshome.vecmath.rotation.layout.Rot3Layout;
import brownshome.vecmath.vector.generic.GenericVecLayout;
import brownshome.vecmath.vector.wrapped.layout.WrappedVecLayout;

/**
 * The layout of an array-backed 4-element vector
 */
public interface Vec4Layout extends GenericVecLayout {
	/**
	 * Gets an optimal layout
	 * @return a layout
	 */
	static Vec4Layout ofOptimal() {
		return of(0);
	}

	/**
	 * Gets a packed layout
	 * @return a layout
	 */
	static Vec4Layout ofPacked() {
		return of(0);
	}

	/**
	 * Gets a vector layout with a given offset
	 * @param offset the offset
	 * @return a layout
	 */
	static Vec4Layout of(int offset) {
		return of(offset, 1);
	}

	/**
	 * Gets a vector layout with a given offset and stride
	 * @param offset the offset
	 * @param stride the stride
	 * @return a layout
	 */
	static Vec4Layout of(int offset, int stride) {
		return new BasicColumnLayout(4, offset, stride);
	}

	/**
	 * Gets this layout as a 3D-rotation layout
	 * @return a new layout
	 */
	default Rot3Layout asRot3() {
		return new WrappedVecLayout(this);
	}
}
