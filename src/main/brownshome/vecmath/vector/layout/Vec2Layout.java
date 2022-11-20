package brownshome.vecmath.vector.layout;

import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.basic.layout.BasicVec2Layout;
import brownshome.vecmath.vector.generic.GenericVecLayout;

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
		return new BasicVec2Layout(offset, stride);
	}

	/**
	 * This layout as an arbitrary-length vector layout
	 * @return a layout
	 */
	default VecNLayout asVecNLayout() {
		return new VecNLayout() {
			@Override
			public int start() {
				return Vec2Layout.this.start();
			}

			@Override
			public int end() {
				return Vec2Layout.this.end();
			}

			@Override
			public int size() {
				return Vec2Layout.this.size();
			}

			@Override
			public boolean isContinuous() {
				return Vec2Layout.this.isContinuous();
			}

			@Override
			public boolean isPacked() {
				return Vec2Layout.this.isPacked();
			}

			@Override
			public int arrayIndex(int index) {
				return Vec2Layout.this.arrayIndex(index);
			}

			@Override
			public int elements() {
				return 2;
			}

			@Override
			public Vec2Layout asVec2Layout() {
				return Vec2Layout.this;
			}

			@Override
			public Vec3Layout asVec3Layout() {
				throw new UnsupportedOperationException();
			}

			@Override
			public Vec4Layout asVec4Layout() {
				throw new UnsupportedOperationException();
			}

			@Override
			public String toString() {
				return Vec2Layout.this.toString();
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
