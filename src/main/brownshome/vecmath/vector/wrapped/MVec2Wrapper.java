package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.complex.MComplex;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.rotation.MRot2;
import brownshome.vecmath.vector.*;

public interface MVec2Wrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends Vec2Wrapper<ELEMENT_TYPE>, MVecWrapper<Vec2, ELEMENT_TYPE> {
	@Override
	MVec2 delegate();

	abstract class BasicMVec2Wrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends BasicWrapper<Vec2, ELEMENT_TYPE> implements MVec2Wrapper<ELEMENT_TYPE> {
		public BasicMVec2Wrapper(MVec2 delegate) {
			super(delegate);
		}

		@Override
		public MVec2 delegate() {
			return (MVec2) super.delegate();
		}
	}

	interface ToVecN extends MVec2Wrapper<VecN>, Vec2Wrapper.ToVecN, MVecWrapper.ToVecN<Vec2>, MVecN {
		@Override
		default void set(double value, int index) {
			assert index < size();
			if (index == 0) {
				delegate().x(value);
			} else {
				delegate().y(value);
			}
		}

		@Override
		default MVec2 asVec2() {
			return (MVec2) Vec2Wrapper.ToVecN.super.asVec2();
		}

		@Override
		default MVec3 asVec3() {
			return (MVec3) Vec2Wrapper.ToVecN.super.asVec3();
		}

		@Override
		default MVec4 asVec4() {
			return (MVec4) Vec2Wrapper.ToVecN.super.asVec4();
		}

		@Override
		default MMatrix asColumn() {
			return MVecWrapper.ToVecN.super.asColumn();
		}

		@Override
		default MMatrix asRow() {
			return MVecWrapper.ToVecN.super.asRow();
		}
	}

	final class BasicToVecN extends BasicMVec2Wrapper<VecN> implements ToVecN {
		public BasicToVecN(MVec2 delegate) {
			super(delegate);
		}
	}

	interface ToVec2 extends MVec2Wrapper<Vec2>, Vec2Wrapper.ToVec2, MVecWrapper.ToGenericVec<Vec2, Vec2>, MVec2 {
		@Override
		default void x(double x) {
			delegate().x(x);
		}

		@Override
		default void y(double y) {
			delegate().y(y);
		}

		@Override
		default void set(double x, double y) {
			delegate().set(x, y);
		}

		@Override
		default void set(Vec2 v) {
			delegate().set(v);
		}

		@Override
		default void addToSelf(double x, double y) {
			delegate().addToSelf(x, y);
		}

		@Override
		default void addToSelf(Vec2 vec) {
			delegate().addToSelf(vec);
		}

		@Override
		default void scaleSelf(Vec2 scale) {
			delegate().scaleSelf(scale);
		}

		@Override
		default void scaleSelf(double scale) {
			MVec2Wrapper.super.scaleSelf(scale);
		}

		@Override
		default void setToTangent() {
			delegate().setToTangent();
		}

		@Override
		default MRot2 asRot() {
			return delegate().asRot();
		}

		@Override
		default MComplex asComplex() {
			return delegate().asComplex();
		}

		@Override
		default MVecN asUnknownSize() {
			return (MVecN) Vec2Wrapper.ToVec2.super.asUnknownSize();
		}

		@Override
		default MMatrix asRow() {
			return MVecWrapper.ToGenericVec.super.asRow();
		}

		@Override
		default MMatrix asColumn() {
			return MVecWrapper.ToGenericVec.super.asColumn();
		}

		@Override
		default void subtractFromSelf(Vec2 e) {
			delegate().subtractFromSelf(e);
		}

		@Override
		default void scaleAddToSelf(Vec2 e, double scale) {
			delegate().scaleAddToSelf(e, scale);
		}

		@Override
		default void setToInterpolated(Vec2 other, double t) {
			delegate().setToInterpolated(other, t);
		}
	}

	interface ToRot2 extends ToVec2, Vec2Wrapper.ToRot2, MRot2 {
		@Override
		default MRot2 asRot() {
			return MRot2.super.asRot();
		}
	}

	final class BasicToRot2 extends BasicMVec2Wrapper<Vec2> implements ToRot2 {
		public BasicToRot2(MVec2 delegate) {
			super(delegate);
		}
	}

	interface ToComplex extends ToVec2, Vec2Wrapper.ToComplex, MComplex {
		@Override
		default void real(double r) {
			y(r);
		}

		@Override
		default void imaginary(double i) {
			x(i);
		}

		@Override
		default void x(double x) {
			MVec2Wrapper.ToVec2.super.x(x);
		}

		@Override
		default void y(double y) {
			MVec2Wrapper.ToVec2.super.y(y);
		}

		@Override
		default void setToNormalised() {
			MVec2Wrapper.ToVec2.super.setToNormalised();
		}

		@Override
		default MComplex asComplex() {
			return MComplex.super.asComplex();
		}
	}

	final class BasicToComplex extends BasicMVec2Wrapper<Vec2> implements ToComplex {
		public BasicToComplex(MVec2 delegate) {
			super(delegate);
		}
	}

	interface ToMatrix extends MVec2Wrapper<Matrix>, Vec2Wrapper.ToMatrix, MVecWrapper.ToMatrix<Vec2> {
		@Override
		default void set(double value, int row, int column) {
			assert column == 0;
			assert row < rows();
			if (row == 0) {
				delegate().x(value);
			} else {
				delegate().y(value);
			}
		}
	}

	final class BasicToMatrix extends BasicMVec2Wrapper<Matrix> implements ToMatrix {
		public BasicToMatrix(MVec2 delegate) {
			super(delegate);
		}
	}
}
