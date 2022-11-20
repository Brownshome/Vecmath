package brownshome.vecmath.vector.layout;

import brownshome.vecmath.matrix.basic.layout.VecNMatrixLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.basic.layout.BasicVecNLayout;
import brownshome.vecmath.vector.basic.layout.WrappedVecLayout;
import brownshome.vecmath.vector.generic.GenericVecLayout;

/**
 * The layout of an array-backed arbitrary-element vector
 */
public interface VecNLayout extends GenericVecLayout {
	/**
	 * Gets an optimal layout
	 * @param size the number of elements in this vector
	 * @return a layout
	 */
	static VecNLayout ofOptimal(int size) {
		return of(size);
	}

	/**
	 * Gets a layout with zero offset and one stride.
	 * @param size the number of elements in this vector
	 * @return a layout
	 */
	static VecNLayout of(int size) {
		return of(size, 0);
	}

	/**
	 * Gets a vector layout with a given offset
	 * @param size the number of elements in this vector
	 * @param offset the offset
	 * @return a layout
	 */
	static VecNLayout of(int size, int offset) {
		return of(size, offset, 1);
	}

	/**
	 * Gets a vector layout with a given offset and stride
	 * @param size the number of elements in this vector
	 * @param offset the offset
	 * @param stride the stride
	 * @return a layout
	 */
	static VecNLayout of(int size, int offset, int stride) {
		return new BasicVecNLayout(size, offset, stride);
	}

	/**
	 * The number of elements in this vector
	 * @return the number of elements
	 */
	int elements();

	/**
	 * This layout as a Vec2 layout if it only has 2 elements.
	 * @return a layout
	 */
	default Vec2Layout asVec2Layout() {
		assert elements() == 2;

		return new WrappedVecLayout(this);
	}

	/**
	 * This layout as a Vec3 layout if it only has 3 elements.
	 * @return a layout
	 */
	default Vec3Layout asVec3Layout() {
		assert elements() == 3;

		return new WrappedVecLayout(this);
	}

	/**
	 * This layout as a Vec4 layout if ti only has 4 elements.
	 * @return a layout
	 */
	default Vec4Layout asVec4Layout() {
		assert elements() == 4;

		return new WrappedVecLayout(this);
	}

	@Override
	default MatrixLayout asRowMatrix() {
		return new VecNMatrixLayout(this).transpose();
	}

	@Override
	default MatrixLayout asColumnMatrix() {
		return new VecNMatrixLayout(this);
	}
}
