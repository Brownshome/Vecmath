package brownshome.vecmath.complex.generic;

import brownshome.vecmath.generic.ElementLayout;
import brownshome.vecmath.generic.GenericArrayElement;
import brownshome.vecmath.generic.GenericElement;

/**
 * An array-backed complex numbered type
 * @param <LAYOUT_TYPE> the common layout-type
 * @param <COMPLEX_LAYOUT_TYPE> the layout type
 * @param <ELEMENT_TYPE> the common type
 * @param <COMPLEX_TYPE> the complex type
 */
@SuppressWarnings("unchecked")
public interface GenericArrayComplex<
		LAYOUT_TYPE extends ElementLayout,
		COMPLEX_LAYOUT_TYPE extends LAYOUT_TYPE,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>,
		COMPLEX_TYPE extends ELEMENT_TYPE> extends GenericMComplex<ELEMENT_TYPE, COMPLEX_TYPE>, GenericArrayElement<LAYOUT_TYPE, ELEMENT_TYPE> {
	@Override
	COMPLEX_LAYOUT_TYPE layout();

	@Override
	default COMPLEX_TYPE asArrayBacked() {
		return (COMPLEX_TYPE) GenericArrayElement.super.asArrayBacked();
	}

	@Override
	default double magnitudeSquared() {
		assert layout().isContinuous();

		double result = 0.0;
		for (int i = layout().start(); i < layout().end(); i++) {
			result += backingArray()[i] * backingArray()[i];
		}

		return result;
	}
}
