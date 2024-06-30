package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.Vec3;
import brownshome.vecmath.vector.Vec4;
import brownshome.vecmath.vector.VecN;

public interface Vec3Wrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends VecWrapper<Vec3, ELEMENT_TYPE> {
	interface ToVecN extends Vec3Wrapper<VecN>, VecWrapper.ToVecN<Vec3> {
		@Override
		default int size() {
			return 3;
		}

		@Override
		default double get(int i) {
			assert i < size();
			return switch (i) {
				case 0 -> delegate().x();
				case 1 -> delegate().y();
				default -> delegate().z();
			};
		}

		@Override
		default Vec2 asVec2() {
			throw new UnsupportedOperationException("A Vec3 cannot be converted to a Vec2");
		}

		@Override
		default Vec3 asVec3() {
			return delegate();
		}

		@Override
		default Vec4 asVec4() {
			throw new UnsupportedOperationException("A Vec3 cannot be converted to a Vec4");
		}
	}

	final class BasicToVecN extends BasicWrapper<Vec3, VecN> implements ToVecN {
		public BasicToVecN(Vec3 delegate) {
			super(delegate);
		}
	}

	interface ToMatrix extends Vec3Wrapper<Matrix>, VecWrapper.ToMatrix<Vec3> {
		@Override
		default int rows() {
			return 3;
		}

		@Override
		default double get(int row, int column) {
			assert column == 0;
			assert row < rows();
			return switch (row) {
				case 0 -> delegate().x();
				case 1 -> delegate().y();
				default -> delegate().z();
			};
		}
	}

	final class BasicToMatrix extends BasicWrapper<Vec3, Matrix> implements ToMatrix {
		public BasicToMatrix(Vec3 delegate) {
			super(delegate);
		}
	}
}
