package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.generic.GenericMVec;
import brownshome.vecmath.vector.generic.GenericVec;

public interface MVecWrapper<
		WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends MWrapper<WRAPPED_TYPE, ELEMENT_TYPE> {
	interface ToGenericVec<
			WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>,
			VEC_TYPE extends GenericVec<VEC_TYPE>> extends VecWrapper.ToGenericVec<WRAPPED_TYPE, VEC_TYPE>, MVecWrapper<WRAPPED_TYPE, VEC_TYPE>, GenericMVec<VEC_TYPE> {
		@Override
		default void setToNormalised() {
			((GenericMVec<?>) delegate()).setToNormalised();
		}

		@Override
		default MVecN asUnknownSize() {
			return ((GenericMVec<?>) delegate()).asUnknownSize();
		}

		@Override
		default MMatrix asRow() {
			return ((GenericMVec<?>) delegate()).asRow();
		}

		@Override
		default MMatrix asColumn() {
			return ((GenericMVec<?>) delegate()).asColumn();
		}
	}

	interface ToVecN<WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>> extends VecWrapper.ToVecN<WRAPPED_TYPE>, ToGenericVec<WRAPPED_TYPE, VecN>, MVecN {
		@Override
		default MVecN asUnknownSize() {
			return MVecN.super.asUnknownSize();
		}

		@Override
		default void scaleSelf(double scale) {
			MVecWrapper.ToGenericVec.super.scaleSelf(scale);
		}

		@Override
		default MMatrix asRow() {
			return MVecWrapper.ToGenericVec.super.asRow();
		}

		@Override
		default MMatrix asColumn() {
			return MVecWrapper.ToGenericVec.super.asColumn();
		}
	}

	interface ToMatrix<WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>> extends MVecWrapper<WRAPPED_TYPE, Matrix>, VecWrapper.ToMatrix<WRAPPED_TYPE>, MMatrix {
		@Override
		default MVecN column(int c) {
			return (MVecN) VecWrapper.ToMatrix.super.column(c);
		}

		@Override
		default MMatrix permuteByColumn(int... columns) {
			return (MMatrix) VecWrapper.ToMatrix.super.permuteByColumn(columns);
		}

		@Override
		default void permuteSelfByColumns(int... columns) {
			assert columns.length == 1;
			assert columns[0] == 0;
		}

		@Override
		default void scaleSelf(double scale) {
			MVecWrapper.super.scaleSelf(scale);
		}

		@Override
		default void transposeSelf() {
			throw new UnsupportedOperationException("This matrix is not square");
		}

		@Override
		default MVecN asRowVec() {
			return (MVecN) VecWrapper.ToMatrix.super.asRowVec();
		}

		@Override
		default MMatrix asSymmetric() {
			return (MMatrix) VecWrapper.ToMatrix.super.asSymmetric();
		}
	}
}
