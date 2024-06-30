package brownshome.vecmath.complex.basic.array;

import brownshome.vecmath.complex.basic.BaseGenericComplex;
import brownshome.vecmath.generic.ElementLayout;
import brownshome.vecmath.generic.GenericArrayElement;
import brownshome.vecmath.generic.GenericElement;

@SuppressWarnings("unchecked")
public abstract class BaseGenericArrayComplex<
		LAYOUT_TYPE extends ElementLayout,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>,
		COMPLEX_TYPE extends ELEMENT_TYPE> extends BaseGenericComplex<ELEMENT_TYPE, COMPLEX_TYPE> implements GenericArrayElement<LAYOUT_TYPE, ELEMENT_TYPE> {
	@Override
	public COMPLEX_TYPE move() {
		return (COMPLEX_TYPE) GenericArrayElement.super.move();
	}

	@Override
	public COMPLEX_TYPE asArrayBacked() {
		return (COMPLEX_TYPE) GenericArrayElement.super.asArrayBacked();
	}
}
