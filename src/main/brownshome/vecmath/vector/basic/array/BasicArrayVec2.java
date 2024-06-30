package brownshome.vecmath.vector.basic.array;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.array.ArrayComplex;
import brownshome.vecmath.rotation.Rot2;
import brownshome.vecmath.rotation.array.ArrayRot2;
import brownshome.vecmath.vector.Vec2;
import brownshome.vecmath.vector.layout.Vec2Layout;

public final class BasicArrayVec2 extends BasicArrayVec<Vec2Layout, Vec2> implements ArrayRot2 {
	public BasicArrayVec2(double[] array, Vec2Layout layout) {
		super(array, layout);
	}

	private BasicArrayVec2(BasicArrayVec2 copy) {
		super(copy);
	}

	@Override
	public ArrayComplex asComplex() {
		return Complex.of(backingArray(), layout().asComplex());
	}

	@Override
	public ArrayRot2 asRot() {
		return Rot2.of(backingArray(), layout().asRot2());
	}

	@Override
	public BasicArrayVec2 copy() {
		return new BasicArrayVec2(this);
	}

	@Override
	public BasicArrayVec2 move() {
		return (BasicArrayVec2) ArrayRot2.super.move();
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof Vec2 vec && exactEquals(vec);
	}
}
