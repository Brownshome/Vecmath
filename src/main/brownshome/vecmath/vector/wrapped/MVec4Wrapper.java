package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.rotation.MRot3;
import brownshome.vecmath.vector.*;

public interface MVec4Wrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends Vec4Wrapper<ELEMENT_TYPE>, MVecWrapper<Vec4, ELEMENT_TYPE> {
	@Override
	MVec4 delegate();

	abstract class BasicMVec4Wrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends BasicWrapper<Vec4, ELEMENT_TYPE> implements MVec4Wrapper<ELEMENT_TYPE> {
		public BasicMVec4Wrapper(MVec4 delegate) {
			super(delegate);
		}

		@Override
		public MVec4 delegate() {
			return (MVec4) super.delegate();
		}
	}

	interface ToVecN extends MVec4Wrapper<VecN>, Vec4Wrapper.ToVecN, MVecWrapper.ToVecN<Vec4>, MVecN {
		@Override
		default void set(double value, int index) {
			assert index < size();
			switch (index) {
				case 0 -> delegate().x(value);
				case 1 -> delegate().y(value);
				case 2 -> delegate().z(value);
				default -> delegate().w(value);
			}
		}

		@Override
		default MVec2 asVec2() {
			return (MVec2) Vec4Wrapper.ToVecN.super.asVec2();
		}

		@Override
		default MVec3 asVec3() {
			return (MVec3) Vec4Wrapper.ToVecN.super.asVec3();
		}

		@Override
		default MVec4 asVec4() {
			return (MVec4) Vec4Wrapper.ToVecN.super.asVec4();
		}
	}

	final class BasicToVecN extends BasicMVec4Wrapper<VecN> implements ToVecN {
		public BasicToVecN(MVec4 delegate) {
			super(delegate);
		}
	}

	interface ToVec4 extends MVec4Wrapper<Vec4>, Vec4Wrapper.ToVec4, MVecWrapper.ToGenericVec<Vec4, Vec4>, MVec4 {
		@Override
		default void x(double x) {
			delegate().x(x);
		}

		@Override
		default void y(double y) {
			delegate().y(y);
		}

		@Override
		default void z(double z) {
			delegate().z(z);
		}

		@Override
		default void w(double w) {
			delegate().w(w);
		}

		@Override
		default void set(double x, double y, double z, double w) {
			delegate().set(x, y, z, w);
		}

		@Override
		default void set(Vec4 v) {
			delegate().set(v);
		}

		@Override
		default void addToSelf(double x, double y, double z, double w) {
			delegate().addToSelf(x, y, z, w);
		}

		@Override
		default void addToSelf(Vec4 vec) {
			delegate().addToSelf(vec);
		}

		@Override
		default void scaleSelf(Vec4 scale) {
			delegate().scaleSelf(scale);
		}

		@Override
		default MVecN asUnknownSize() {
			return delegate().asUnknownSize();
		}

		@Override
		default MMatrix asRow() {
			return delegate().asRow();
		}

		@Override
		default MMatrix asColumn() {
			return delegate().asColumn();
		}

		@Override
		default void scaleSelf(double scale) {
			delegate().scaleSelf(scale);
		}

		@Override
		default void subtractFromSelf(Vec4 e) {
			delegate().subtractFromSelf(e);
		}

		@Override
		default void scaleAddToSelf(Vec4 e, double scale) {
			delegate().scaleAddToSelf(e, scale);
		}

		@Override
		default void setToInterpolated(Vec4 other, double t) {
			delegate().setToInterpolated(other, t);
		}
	}

	interface ToRot3 extends ToVec4, Vec4Wrapper.ToRot3, MRot3 { }

	final class BasicToRot3 extends BasicMVec4Wrapper<Vec4> implements ToRot3 {
		public BasicToRot3(MVec4 delegate) {
			super(delegate);
		}
	}

	interface ToMatrix extends MVec4Wrapper<Matrix>, Vec4Wrapper.ToMatrix, MVecWrapper.ToMatrix<Vec4> {
		@Override
		default void set(double value, int row, int column) {
			assert column == 0;
			assert row < rows();
			switch (row) {
				case 0 -> delegate().x(value);
				case 1 -> delegate().y(value);
				case 2 -> delegate().z(value);
				default -> delegate().w(value);
			}
		}
	}

	final class BasicToMatrix extends BasicMVec4Wrapper<Matrix> implements ToMatrix {
		public BasicToMatrix(MVec4 delegate) {
			super(delegate);
		}
	}
}
