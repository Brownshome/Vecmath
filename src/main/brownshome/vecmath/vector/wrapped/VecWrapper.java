package brownshome.vecmath.vector.wrapped;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.DoubleStream;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.vector.VecN;
import brownshome.vecmath.vector.array.ArrayVecN;
import brownshome.vecmath.vector.generic.GenericVec;

public interface VecWrapper<
		WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends Wrapper<WRAPPED_TYPE, ELEMENT_TYPE> {
	interface ToGenericVec<
			WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>,
			VEC_TYPE extends GenericVec<VEC_TYPE>> extends VecWrapper<WRAPPED_TYPE, VEC_TYPE>, GenericVec<VEC_TYPE> {
		@Override
		default PrimitiveIterator.OfDouble iterator() {
			return delegate().iterator();
		}

		@Override
		default Spliterator.OfDouble spliterator() {
			return delegate().spliterator();
		}

		@Override
		default DoubleStream values() {
			return delegate().values();
		}

		@Override
		default double lengthSquared() {
			return delegate().lengthSquared();
		}

		@Override
		default double length() {
			return delegate().length();
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
		default void forEach(Consumer<? super Double> action) {
			delegate().forEach(action);
		}
	}

	interface ToVecN<WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>> extends ToGenericVec<WRAPPED_TYPE, VecN>, VecN {
		@Override
		default ArrayVecN asArrayBacked() {
			return (ArrayVecN) delegate().asArrayBacked().asUnknownSize();
		}

		@Override
		default VecN asUnknownSize() {
			return VecN.super.asUnknownSize();
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
		default Matrix asColumn() {
			return ToGenericVec.super.asColumn();
		}

		@Override
		default Matrix asRow() {
			return ToGenericVec.super.asRow();
		}
	}

	interface ToMatrix<WRAPPED_TYPE extends GenericVec<? super WRAPPED_TYPE>> extends VecWrapper<WRAPPED_TYPE, Matrix>, Matrix {
		@Override
		default int columns() {
			return 1;
		}

		@Override
		default boolean isRowOptimal() {
			return true;
		}

		@Override
		default VecN column(int c) {
			assert c == 0;
			return delegate().asUnknownSize();
		}

		@Override
		default Matrix permuteByColumn(int... columns) {
			assert columns.length == 1;
			assert columns[0] == 0;
			return this;
		}

		@Override
		default Factorisation factorisation(double tolerance) {
			throw new UnsupportedOperationException("This matrix is not square");
		}

		@Override
		default double asValue() {
			throw new UnsupportedOperationException("This matrix has more than one value");
		}

		@Override
		default VecN asRowVec() {
			throw new UnsupportedOperationException("This matrix has more than one column");
		}

		@Override
		default Matrix asSymmetric() {
			throw new UnsupportedOperationException("This matrix is not square");
		}

		@Override
		default MMatrix asSymmetricCopy() {
			throw new UnsupportedOperationException("This matrix is not square");
		}
	}
}
