package brownshome.vecmath.complex.basic.array;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.array.ArrayComplex;
import brownshome.vecmath.complex.basic.BaseGenericComplex;
import brownshome.vecmath.complex.layout.ComplexLayout;
import brownshome.vecmath.vector.Vec2;

public class BasicArrayComplex extends BaseGenericComplex<Vec2, Complex> implements ArrayComplex {
	private final double[] backingArray;
	private final ComplexLayout layout;

	public BasicArrayComplex(double[] backingArray, ComplexLayout layout) {
		this.backingArray = backingArray;
		this.layout = layout;
	}

	@Override
	public ComplexLayout layout() {
		return layout;
	}

	@Override
	public double[] backingArray() {
		return backingArray;
	}
}
