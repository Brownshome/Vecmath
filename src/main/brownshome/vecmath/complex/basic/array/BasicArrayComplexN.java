package brownshome.vecmath.complex.basic.array;

import brownshome.vecmath.complex.array.ArrayComplexN;
import brownshome.vecmath.complex.layout.ComplexNLayout;

public final class BasicArrayComplexN implements ArrayComplexN {
	private final double[] array;
	private final ComplexNLayout layout;

	public BasicArrayComplexN(double[] array, ComplexNLayout layout) {
		this.array = array;
		this.layout = layout;

		assert layout.start() < array.length;
		assert layout.end() <= array.length;
	}

	@Override
	public ComplexNLayout layout() {
		return layout;
	}

	@Override
	public double[] backingArray() {
		return array;
	}
}
