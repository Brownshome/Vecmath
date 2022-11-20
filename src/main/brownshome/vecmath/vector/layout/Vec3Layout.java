package brownshome.vecmath.vector.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.basic.layout.BasicVec3Layout;
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
		return new BasicVec3Layout(offset, stride);
	}

	/**
	 * This layout as an arbitrary-length vector layout
	 * @return a layout
	 */
	default VecNLayout asVecNLayout() {
		return new VecNLayout() {
			@Override
			public int start() {
				return Vec3Layout.this.start();
			}

			@Override
			public int end() {
				return Vec3Layout.this.end();
			}

			@Override
			public int size() {
				return Vec3Layout.this.size();
			}

			@Override
			public boolean isContinuous() {
				return Vec3Layout.this.isContinuous();
			}

			@Override
			public boolean isPacked() {
				return Vec3Layout.this.isPacked();
			}

			@Override
			public int arrayIndex(int index) {
				return Vec3Layout.this.arrayIndex(index);
			}

			@Override
			public int elements() {
				return 3;
			}

			@Override
			public Vec2Layout asVec2Layout() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Vec3Layout asVec3Layout() {
				return Vec3Layout.this;
			}

			@Override
			public Vec4Layout asVec4Layout() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String toString() {
				return Vec3Layout.this.toString();
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
