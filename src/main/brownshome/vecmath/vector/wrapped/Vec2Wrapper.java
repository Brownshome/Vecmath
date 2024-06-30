package brownshome.vecmath.vector.wrapped;

import java.util.PrimitiveIterator;
import java.util.Spliterator;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.rotation.Rot2;
import brownshome.vecmath.vector.*;
import brownshome.vecmath.vector.array.ArrayVec2;
import brownshome.vecmath.vector.layout.Vec2Layout;

public interface Vec2Wrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends VecWrapper<Vec2, ELEMENT_TYPE> {
	interface ToVecN extends Vec2Wrapper<VecN>, VecWrapper.ToVecN<Vec2> {
		@Override
		default int size() {
			return 2;
		}

		@Override
		default double get(int i) {
			assert i < size();
			return i == 0 ? delegate().x() : delegate().y();
		}

		@Override
		default Vec2 asVec2() {
			return delegate();
		}

		@Override
		default Vec3 asVec3() {
			throw new UnsupportedOperationException("A Vec2 cannot be converted to a Vec3");
		}

		@Override
		default Vec4 asVec4() {
			throw new UnsupportedOperationException("A Vec2 cannot be converted to a Vec4");
		}
	}

	final class BasicToVecN extends BasicWrapper<Vec2, VecN> implements ToVecN {
		public BasicToVecN(Vec2 delegate) {
			super(delegate);
		}
	}

	interface ToVec2 extends Vec2Wrapper<Vec2>, VecWrapper.ToGenericVec<Vec2, Vec2>, Vec2 {
		@Override
		default double x() {
			return delegate().x();
		}

		@Override
		default double y() {
			return delegate().y();
		}

		@Override
		default Vec2 tangent() {
			return delegate().tangent();
		}

		@Override
		default double dot(Vec2 vec) {
			return delegate().dot(vec);
		}

		@Override
		default boolean exactEquals(Vec2 other) {
			return delegate().exactEquals(other);
		}

		@Override
		default Rot2 asRot() {
			return delegate().asRot();
		}

		@Override
		default Complex asComplex() {
			return delegate().asComplex();
		}

		@Override
		default Matrix asRow() {
			return ToGenericVec.super.asRow();
		}

		@Override
		default Matrix asColumn() {
			return ToGenericVec.super.asColumn();
		}

		@Override
		default VecN asUnknownSize() {
			return ToGenericVec.super.asUnknownSize();
		}

		@Override
		default ArrayVec2 arrayBackedCopy(Vec2Layout layout) {
			return delegate().arrayBackedCopy(layout);
		}

		@Override
		default PrimitiveIterator.OfDouble iterator() {
			return ToGenericVec.super.iterator();
		}

		@Override
		default Spliterator.OfDouble spliterator() {
			return ToGenericVec.super.spliterator();
		}

		@Override
		default double distanceSquared(Vec2 position) {
			return delegate().distanceSquared(position);
		}

		@Override
		default double distance(Vec2 position) {
			return delegate().distance(position);
		}

		@Override
		default double angle(Vec2 vec) {
			return delegate().angle(vec);
		}
	}

	interface ToRot2 extends ToVec2, Rot2 {
		@Override
		default Rot2 asRot() {
			return Rot2.super.asRot();
		}
	}

	final class BasicToRot2 extends BasicWrapper<Vec2, Vec2> implements ToRot2 {
		public BasicToRot2(Vec2 delegate) {
			super(delegate);
		}
	}

	interface ToComplex extends ToVec2, Complex {
		@Override
		default double real() {
			return y();
		}

		@Override
		default double imaginary() {
			return x();
		}

		@Override
		default double x() {
			return Vec2Wrapper.ToVec2.super.x();
		}

		@Override
		default double y() {
			return Vec2Wrapper.ToVec2.super.y();
		}

		@Override
		default double magnitudeSquared() {
			return delegate().lengthSquared();
		}

		@Override
		default double magnitude() {
			return delegate().length();
		}

		@Override
		default Complex asComplex() {
			return this;
		}
	}

	final class BasicToComplex extends BasicWrapper<Vec2, Vec2> implements ToComplex {
		public BasicToComplex(Vec2 delegate) {
			super(delegate);
		}
	}

	interface ToMatrix extends Vec2Wrapper<Matrix>, VecWrapper.ToMatrix<Vec2> {
		@Override
		default int rows() {
			return 2;
		}

		@Override
		default double get(int row, int column) {
			assert column == 0;
			assert row < rows();
			return row == 0 ? delegate().x() : delegate().y();
		}
	}

	final class BasicToMatrix extends BasicWrapper<Vec2, Matrix> implements ToMatrix {
		public BasicToMatrix(Vec2 delegate) {
			super(delegate);
		}
	}
}
