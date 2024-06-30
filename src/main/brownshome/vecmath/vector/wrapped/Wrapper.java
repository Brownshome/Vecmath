package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.generic.GenericElement;

/**
 * A wrapper of an element converting it to another type
 * @param <WRAPPED_TYPE> the type that is being wrapped
 * @param <ELEMENT_TYPE> the type that is converted to
 */
public interface Wrapper<
		WRAPPED_TYPE extends GenericElement<? super WRAPPED_TYPE>,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends GenericElement<ELEMENT_TYPE> {
	WRAPPED_TYPE delegate();
}
