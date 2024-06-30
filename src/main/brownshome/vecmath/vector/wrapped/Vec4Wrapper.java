package brownshome.vecmath.vector.wrapped;

import java.util.PrimitiveIterator;
import java.util.Spliterator;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.rotation.Rot3;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.Vec3;
import brownshome.vecmath.vector.Vec4;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.array.ArrayVec4;
import brownshome.vecmath.vector.layout.Vec4Layout;

public interface Vec4Wrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends VecWrapper<Vec4, ELEMENT_TYPE> {
	interface ToVecN extends Vec4Wrapper<VecN>, VecWrapper.ToVecN<Vec4> {
		@Override
		default int size() {
			return 4;
		}

		@Override
		default double get(int i) {
			assert i < size();
			return switch (i) {
				case 0 -> delegate().x();
				case 1 -> delegate().y();
				case 2 -> delegate().z();
				default -> delegate().w();
			};
		}

		@Override
		default Vec2 asVec2() {
			throw new UnsupportedOperationException("A Vec4 cannot be converted to a Vec2");
		}

		@Override
		default Vec3 asVec3() {
			throw new UnsupportedOperationException("A Vec4 cannot be converted to a Vec3");
		}

		@Override
		default Vec4 asVec4() {
			return delegate();
		}
	}

	final class BasicToVecN extends BasicWrapper<Vec4, VecN> implements ToVecN {
		public BasicToVecN(Vec4 delegate) {
			super(delegate);
		}
	}

	interface ToVec4 extends Vec4Wrapper<Vec4>, VecWrapper.ToGenericVec<Vec4, Vec4>, Vec4 {
		@Override
		default double x() {
			return delegate().x();
		}

		@Override
		default double y() {
			return delegate().y();
		}

		@Override
		default double z() {
			return delegate().z();
		}

		@Override
		default double w() {
			return delegate().w();
		}

		@Override
		default double dot(Vec4 vec) {
			return delegate().dot(vec);
		}

		@Override
		default VecN asUnknownSize() {
			return delegate().asUnknownSize();
		}

		@Override
		default Matrix asRow() {
			return delegate().asRow();
		}

		@Override
		default Matrix asColumn() {
			return delegate().asColumn();
		}

		@Override
		default boolean exactEquals(Vec4 other) {
			return delegate().exactEquals(other);
		}

		@Override
		default ArrayVec4 arrayBackedCopy(Vec4Layout layout) {
			return delegate().arrayBackedCopy(layout);
		}

		@Override
		default PrimitiveIterator.OfDouble iterator() {
			return delegate().iterator();
		}

		@Override
		default Spliterator.OfDouble spliterator() {
			return delegate().spliterator();
		}

		@Override
		default double distanceSquared(Vec4 position) {
			return delegate().distanceSquared(position);
		}

		@Override
		default double distance(Vec4 position) {
			return delegate().distance(position);
		}

		@Override
		default double angle(Vec4 vec) {
			return delegate().angle(vec);
		}
	}

	interface ToRot3 extends ToVec4, Rot3 {	}

	final class BasicToRot3 extends BasicWrapper<Vec4, Vec4> implements ToRot3 {
		public BasicToRot3(Vec4 delegate) {
			super(delegate);
		}
	}

	interface ToMatrix extends Vec4Wrapper<Matrix>, VecWrapper.ToMatrix<Vec4> {
		@Override
		default int rows() {
			return 4;
		}

		@Override
		default double get(int row, int column) {
			assert column == 0;
			assert row < rows();
			return switch (row) {
				case 0 -> delegate().x();
				case 1 -> delegate().y();
				case 2 -> delegate().z();
				default -> delegate().w();
			};
		}
	}

	final class BasicToMatrix extends BasicWrapper<Vec4, Matrix> implements ToMatrix {
		public BasicToMatrix(Vec4 delegate) {
			super(delegate);
		}
	}
}
