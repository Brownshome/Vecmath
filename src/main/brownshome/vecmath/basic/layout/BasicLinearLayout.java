package brownshome.vecmath.basic.layout;

import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import brownshome.vecmath.vector.layout.VecNLayout;

public abstract class BasicLinearLayout extends BasicLayout {
	private final int elements, stride;

	protected BasicLinearLayout(int size, int offset, int stride) {
		super(1, size, offset, 0, stride);

		this.elements = size;
		this.stride = stride;
	}

	@Override
	public final int start() {
		return offset() + Math.min(0, stride) * (elements - 1);
	}

	@Override
	public final int end() {
		return offset() + Math.max(0, stride) * (elements - 1) + 1;
	}

	@Override
	public final int size() {
		return stride * (elements - 1) + 1;
	}

	protected final int stride() {
		return stride;
	}

	@Override
	public final int elements() {
		return elements;
	}

	@Override
	public final int complexElements() {
		asComplex();
		return 1;
	}

	@Override
	public final int arrayIndex(int index) {
		assert index >= 0 && index < elements;
		return offset() + stride * index;
	}

	@Override
	public final int realArrayIndex(int index) {
		assert index == 0;
		asComplex();
		return arrayIndex(0);
	}

	@Override
	public final int imaginaryArrayIndex(int index) {
		assert index == 0;
		asComplex();
		return arrayIndex(1);
	}

	@Override
	public final boolean isComponentPacked() {
		asComplex();
		return true;
	}

	@Override
	public final boolean isElementPacked() {
		asComplex();
		return isPacked();
	}

	@Override
	public abstract MatrixLayout subLayout(int r, int c, int rows, int columns);

	@Override
	public abstract MatrixLayout transpose();

	@Override
	public final ComplexLayout asComplex(int index) {
		assert index == 0;
		return asComplex();
	}

	@Override
	public abstract MatrixLayout asComplexRowMatrix();

	@Override
	public abstract MatrixLayout asComplexColumnMatrix();

	@Override
	public abstract MatrixLayout asRowMatrix();

	@Override
	public abstract MatrixLayout asColumnMatrix();

	@Override
	public final VecNLayout asVecN() {
		return this;
	}
}
