package brownshome.vecmath.matrix.wrapped;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.wrapped.BasicWrapper;
import brownshome.vecmath.vector.wrapped.Wrapper;

public interface WrappedMatrix<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends Wrapper<Matrix, ELEMENT_TYPE> {
	interface ToVecN extends WrappedMatrix<VecN>, VecN {
		int column();

		@Override
		default int size() {
			return delegate().rows();
		}

		@Override
		default double get(int i) {
			return delegate().get(i, column());
		}

		@Override
		default Matrix asColumn() {
			return delegate().subMatrix(0, column(), size(), 1);
		}
	}

	class BasicToVecN extends BasicWrapper<Matrix, VecN> implements ToVecN {
		private final int column;

		public BasicToVecN(Matrix delegate, int column) {
			super(delegate);
			this.column = column;
		}

		@Override
		public int column() {
			return column;
		}
	}
}
