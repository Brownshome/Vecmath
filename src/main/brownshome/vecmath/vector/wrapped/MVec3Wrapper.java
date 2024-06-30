package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.*;

public interface MVec3Wrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends Vec3Wrapper<ELEMENT_TYPE>, MVecWrapper<Vec3, ELEMENT_TYPE> {
	@Override
	MVec3 delegate();

	abstract class BasicMVec3Wrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends BasicWrapper<Vec3, ELEMENT_TYPE> implements MVec3Wrapper<ELEMENT_TYPE> {
		public BasicMVec3Wrapper(MVec3 delegate) {
			super(delegate);
		}

		@Override
		public MVec3 delegate() {
			return (MVec3) super.delegate();
		}
	}

	interface ToVecN extends MVec3Wrapper<VecN>, Vec3Wrapper.ToVecN, MVecWrapper.ToVecN<Vec3>, MVecN {
		@Override
		default void set(double value, int index) {
			assert index < size();
			switch (index) {
				case 0 -> delegate().x(value);
				case 1 -> delegate().y(value);
				default -> delegate().z(value);
			}
		}

		@Override
		default MVec2 asVec2() {
			return (MVec2) Vec3Wrapper.ToVecN.super.asVec2();
		}

		@Override
		default MVec3 asVec3() {
			return (MVec3) Vec3Wrapper.ToVecN.super.asVec3();
		}

		@Override
		default MVec4 asVec4() {
			return (MVec4) Vec3Wrapper.ToVecN.super.asVec4();
		}
	}

	final class BasicToVecN extends BasicMVec3Wrapper<VecN> implements ToVecN {
		public BasicToVecN(MVec3 delegate) {
			super(delegate);
		}
	}

	interface ToMatrix extends MVec3Wrapper<Matrix>, Vec3Wrapper.ToMatrix, MVecWrapper.ToMatrix<Vec3> {
		@Override
		default void set(double value, int row, int column) {
			assert column == 0;
			assert row < rows();
			switch (row) {
				case 0 -> delegate().x(value);
				case 1 -> delegate().y(value);
				case 2 -> delegate().z(value);
			}
		}
	}

	final class BasicToMatrix extends BasicMVec3Wrapper<Matrix> implements ToMatrix {
		public BasicToMatrix(MVec3 delegate) {
			super(delegate);
		}
	}
}
