package brownshome.vecmath.complex.wrapped;

import brownshome.vecmath.complex.ComplexN;
import brownshome.vecmath.complex.MComplex;
import brownshome.vecmath.complex.MComplexN;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.vector.MVecN;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.wrapped.BasicWrapper;
import brownshome.vecmath.vector.wrapped.MWrapper;

public interface MComplexNWrapper<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends ComplexNWrapper<ELEMENT_TYPE>, MWrapper<ComplexN, ELEMENT_TYPE> {
	@Override
	MComplexN delegate();

	abstract class BasicMComplexNWrapper<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>>
			extends BasicWrapper<ComplexN, ELEMENT_TYPE> implements MWrapper<ComplexN, ELEMENT_TYPE> {
		public BasicMComplexNWrapper(MComplexN delegate) {
			super(delegate);
		}

		@Override
		public MComplexN delegate() {
			return (MComplexN) super.delegate();
		}
	}

	interface ToMatrix extends MComplexNWrapper<Matrix>, ComplexNWrapper.ToMatrix, MMatrix {
		@Override
		default void set(double value, int row, int column) {
			assert column < columns();
			if (column == 0) {
				delegate().real(value, row);
			} else {
				delegate().imaginary(value, row);
			}
		}

		@Override
		default MVecN row(int r) {
			return (MVecN) ComplexNWrapper.ToMatrix.super.row(r);
		}

		@Override
		default MVecN column(int c) {
			return (MVecN) ComplexNWrapper.ToMatrix.super.column(c);
		}

		@Override
		default MVecN asRowVec() {
			return (MVecN) ComplexNWrapper.ToMatrix.super.asRowVec();
		}

		@Override
		default MVecN asColumnVec() {
			return (MVecN) ComplexNWrapper.ToMatrix.super.asColumnVec();
		}

		@Override
		default void scaleSelf(double scale) {
			MComplexNWrapper.super.scaleSelf(scale);
		}

		@Override
		default void setToNegated() {
			MComplexNWrapper.super.setToNegated();
		}
	}

	final class BasicToMatrix extends BasicMComplexNWrapper<Matrix> implements ToMatrix {
		public BasicToMatrix(MComplexN delegate) {
			super(delegate);
		}
	}

	interface ToComponent extends MComplexNWrapper<VecN>, ComplexNWrapper.ToComponent, MVecN {
		@Override
		default void scaleSelf(double scale) {
			MVecN.super.scaleSelf(scale);
		}
	}

	interface ToReal extends ComplexNWrapper.ToReal, ToComponent {
		@Override
		default void set(double value, int index) {
			delegate().real(value, index);
		}
	}

	final class BasicToReal extends BasicMComplexNWrapper<VecN> implements ToReal {
		public BasicToReal(MComplexN delegate) {
			super(delegate);
		}
	}

	interface ToImaginary extends ComplexNWrapper.ToImaginary, ToComponent {
		@Override
		default void set(double value, int index) {
			delegate().imaginary(value, index);
		}
	}

	final class BasicToImaginary extends BasicMComplexNWrapper<VecN> implements ToImaginary {
		public BasicToImaginary(MComplexN delegate) {
			super(delegate);
		}
	}

	interface ToComplex extends MComplexNWrapper<Vec2>, ComplexNWrapper.ToComplex, MComplex {
		@Override
		default void real(double r) {
			delegate().real(r, index());
		}

		@Override
		default void imaginary(double i) {
			delegate().imaginary(i, index());
		}

		@Override
		default void scaleSelf(double scale) {
			MComplex.super.scaleSelf(scale);
		}
	}

	final class BasicToComplex extends BasicMComplexNWrapper<Vec2> implements ToComplex {
		private final int index;

		public BasicToComplex(MComplexN delegate, int index) {
			super(delegate);
			this.index = index;
		}

		@Override
		public int index() {
			return index;
		}
	}
}
