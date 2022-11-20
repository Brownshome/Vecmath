package brownshome.vecmath.vector.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.basic.layout.BasicVec4Layout;
import brownshome.vecmath.vector.generic.GenericVecLayout;

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
		return new BasicVec4Layout(offset, stride);
	}

	/**
	 * This layout as an arbitrary-length vector layout
	 * @return a layout
	 */
	default VecNLayout asVecNLayout() {
		return new VecNLayout() {
			@Override
			public int start() {
				return Vec4Layout.this.start();
			}

			@Override
			public int end() {
				return Vec4Layout.this.end();
			}

			@Override
			public int size() {
				return Vec4Layout.this.size();
			}

			@Override
			public boolean isContinuous() {
				return Vec4Layout.this.isContinuous();
			}

			@Override
			public boolean isPacked() {
				return Vec4Layout.this.isPacked();
			}

			@Override
			public int arrayIndex(int index) {
				return Vec4Layout.this.arrayIndex(index);
			}

			@Override
			public int elements() {
				return 4;
			}

			@Override
			public Vec2Layout asVec2Layout() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Vec3Layout asVec3Layout() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Vec4Layout asVec4Layout() {
				return Vec4Layout.this;
			}

			@Override
			public String toString() {
				return Vec4Layout.this.toString();
			}
		};
	}

	@Override
	default MatrixLayout asRowMatrix() {
		return asVecNLayout().asRowMatrix();
	}

	@Override
	default MatrixLayout asColumnMatrix() {
		return asVecNLayout().asColumnMatrix();
	}
}
