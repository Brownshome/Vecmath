package brownshome.vecmath.vector.layout;

import brownshome.vecmath.basic.layout.BasicColumnLayout;
import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.rotation.layout.Rot2Layout;
import brownshome.vecmath.vector.generic.GenericVecLayout;
import brownshome.vecmath.vector.wrapped.layout.WrappedVecLayout;

/**
 * The layout of an array-backed 2-element vector
 */
public interface Vec2Layout extends GenericVecLayout {
	/**
	 * Gets an optimal layout
	 * @return a layout
	 */
	static Vec2Layout ofOptimal() {
		return of(0);
	}

	/**
	 * Gets a packed layout
	 * @return a layout
	 */
	static Vec2Layout ofPacked() {
		return of(0);
	}

	/**
	 * Gets a vector layout with a given offset
	 * @param offset the offset
	 * @return a layout
	 */
	static Vec2Layout of(int offset) {
		return of(offset, 1);
	}

	/**
	 * Gets a vector layout with a given offset and stride
	 * @param offset the offset
	 * @param stride the stride
	 * @return a layout
	 */
	static Vec2Layout of(int offset, int stride) {
		return new BasicColumnLayout(2, offset, stride);
	}

	/**
	 * Gets this vector layout as a complex number layout
	 * @return a complex number layout
	 */
	default ComplexLayout asComplex() {
		return new WrappedVecLayout(this);
	}

	/**
	 * Gets this vector layout as a rotation layout
	 * @return a rotation layout
	 */
	default Rot2Layout asRot2() {
		return new WrappedVecLayout(this);
	}
}
