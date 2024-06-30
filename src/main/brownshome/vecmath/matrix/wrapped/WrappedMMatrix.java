package brownshome.vecmath.matrix.wrapped;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.wrapped.BasicWrapper;
import brownshome.vecmath.vector.wrapped.MWrapper;
import brownshome.vecmath.vector.wrapped.Wrapper;

public interface WrappedMMatrix<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends WrappedMatrix<ELEMENT_TYPE>, MWrapper<Matrix, ELEMENT_TYPE> {
	@Override
	MMatrix delegate();

	abstract class BasicWrappedMMatrix<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends BasicWrapper<Matrix, ELEMENT_TYPE> implements WrappedMMatrix<ELEMENT_TYPE> {
		public BasicWrappedMMatrix(MMatrix delegate) {
			super(delegate);
		}

		@Override
		public MMatrix delegate() {
			return (MMatrix) super.delegate();
		}
	}

	interface ToVecN extends WrappedMMatrix<VecN>, WrappedMatrix.ToVecN, MVecN {
		@Override
		default MMatrix asColumn() {
			return (MMatrix) WrappedMatrix.ToVecN.super.asColumn();
		}

		@Override
		default void set(double value, int index) {
			delegate().set(value, index, column());
		}

		@Override
		default void scaleSelf(double scale) {
			MVecN.super.scaleSelf(scale);
		}
	}

	class BasicToVecN extends BasicWrappedMMatrix<VecN> implements ToVecN {
		private final int column;

		public BasicToVecN(MMatrix delegate, int column) {
			super(delegate);
			this.column = column;
		}

		@Override
		public int column() {
			return column;
		}
	}
}
