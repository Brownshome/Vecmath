package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.complex.MComplexN;
import brownshome.vecmath.complex.array.ArrayComplexN;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.*;

public interface MVecNWrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends VecNWrapper<ELEMENT_TYPE>, MVecWrapper<VecN, ELEMENT_TYPE> {
	@Override
	MVecN delegate();

	abstract class BasicMVecNWrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends BasicWrapper<VecN, ELEMENT_TYPE> implements MVecNWrapper<ELEMENT_TYPE> {
		public BasicMVecNWrapper(MVecN delegate) {
			super(delegate);
		}

		@Override
		public MVecN delegate() {
			return (MVecN) super.delegate();
		}
	}

	interface ToVec2 extends MVecNWrapper<Vec2>, VecNWrapper.ToVec2, MVecWrapper.ToGenericVec<VecN, Vec2>, MVec2 {
		@Override
		default void x(double x) {
			delegate().set(x, 0);
		}

		@Override
		default void y(double y) {
			delegate().set(y, 1);
		}

		@Override
		default void scaleSelf(double scale) {
			MVecNWrapper.super.scaleSelf(scale);
		}

		@Override
		default MVecN asUnknownSize() {
			return (MVecN) VecNWrapper.ToVec2.super.asUnknownSize();
		}

		@Override
		default MMatrix asRow() {
			return (MMatrix) VecNWrapper.ToVec2.super.asRow();
		}

		@Override
		default MMatrix asColumn() {
			return (MMatrix) VecNWrapper.ToVec2.super.asColumn();
		}
	}

	class BasicToVec2 extends BasicMVecNWrapper<Vec2> implements ToVec2 {
		public BasicToVec2(MVecN delegate) {
			super(delegate);
			assert delegate.size() == 2;
		}
	}

	interface ToVec3 extends MVecNWrapper<Vec3>, VecNWrapper.ToVec3, MVecWrapper.ToGenericVec<VecN, Vec3>, MVec3 {
		@Override
		default void x(double x) {
			delegate().set(x, 0);
		}

		@Override
		default void y(double y) {
			delegate().set(y, 1);
		}

		@Override
		default void z(double z) {
			delegate().set(z, 2);
		}

		@Override
		default void scaleSelf(double scale) {
			MVecNWrapper.super.scaleSelf(scale);
		}

		@Override
		default MVecN asUnknownSize() {
			return (MVecN) VecNWrapper.ToVec3.super.asUnknownSize();
		}

		@Override
		default MMatrix asRow() {
			return (MMatrix) VecNWrapper.ToVec3.super.asRow();
		}

		@Override
		default MMatrix asColumn() {
			return (MMatrix) VecNWrapper.ToVec3.super.asColumn();
		}
	}

	class BasicToVec3 extends BasicMVecNWrapper<Vec3> implements ToVec3 {
		public BasicToVec3(MVecN delegate) {
			super(delegate);
			assert delegate.size() == 3;
		}
	}

	interface ToVec4 extends MVecNWrapper<Vec4>, VecNWrapper.ToVec4, MVecWrapper.ToGenericVec<VecN, Vec4>, MVec4 {
		@Override
		default void x(double x) {
			delegate().set(x, 0);
		}

		@Override
		default void y(double y) {
			delegate().set(y, 1);
		}

		@Override
		default void z(double z) {
			delegate().set(z, 2);
		}

		@Override
		default void w(double w) {
			delegate().set(w, 3);
		}

		@Override
		default void scaleSelf(double scale) {
			MVecNWrapper.super.scaleSelf(scale);
		}

		@Override
		default MVecN asUnknownSize() {
			return (MVecN) VecNWrapper.ToVec4.super.asUnknownSize();
		}

		@Override
		default MMatrix asRow() {
			return (MMatrix) VecNWrapper.ToVec4.super.asRow();
		}

		@Override
		default MMatrix asColumn() {
			return (MMatrix) VecNWrapper.ToVec4.super.asColumn();
		}
	}

	class BasicToVec4 extends BasicMVecNWrapper<Vec4> implements ToVec4 {
		public BasicToVec4(MVecN delegate) {
			super(delegate);
			assert delegate.size() == 4;
		}
	}

	interface ToMatrix extends MVecNWrapper<Matrix>, VecNWrapper.ToMatrix, MMatrix {
		@Override
		default void set(double value, int row, int column) {
			assert column == 1;
			delegate().set(value, row);
		}

		@Override
		default MVecN column(int c) {
			return (MVecN) VecNWrapper.ToMatrix.super.column(c);
		}

		@Override
		default MMatrix permuteByColumn(int... columns) {
			return (MMatrix) VecNWrapper.ToMatrix.super.permuteByColumn(columns);
		}

		@Override
		default void permuteSelfByColumns(int... columns) {
			assert columns.length == 1;
			assert columns[0] == 0;
		}

		@Override
		default void scaleSelf(double scale) {
			MVecNWrapper.super.scaleSelf(scale);
		}

		@Override
		default MVecN asRowVec() {
			assert delegate().size() == 1;
			return delegate();
		}

		@Override
		default void transposeSelf() {
			assert delegate().size() == 1;
		}
	}

	final class BasicToMatrix extends BasicMVecNWrapper<Matrix> implements ToMatrix {
		public BasicToMatrix(MVecN delegate) {
			super(delegate);
		}
	}

	interface ToComplexN extends VecNWrapper.ToComplexN, MComplexN {
		@Override
		MVecN real();

		@Override
		MVecN imaginary();

		@Override
		default void real(double r, int index) {
			real().set(r, index);
		}

		@Override
		default void imaginary(double i, int index) {
			imaginary().set(i, index);
		}

		@Override
		default void scaleSelf(double scale) {
			real().scaleSelf(scale);
			imaginary().scaleSelf(scale);
		}

		@Override
		default void setToNegated() {
			real().setToNegated();
			imaginary().setToNegated();
		}

		@Override
		default MComplexN move() {
			return MComplexN.super.move();
		}
	}

	final class BasicToComplexN extends VecNWrapper.BasicToComplexN implements ToComplexN {
		public BasicToComplexN(MVecN real, MVecN imaginary) {
			super(real, imaginary);
		}

		@Override
		public MVecN real() {
			return (MVecN) super.real();
		}

		@Override
		public MVecN imaginary() {
			return (MVecN) super.imaginary();
		}
	}
}
