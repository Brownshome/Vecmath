package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.generic.GenericMElement;

/**
 * A wrapper of a modifiable element converting it to another type
 * @param <WRAPPED_TYPE> the type that is being wrapped
 * @param <ELEMENT_TYPE> the type that is converted to
 */
public interface MWrapper<
		WRAPPED_TYPE extends GenericElement<? super WRAPPED_TYPE>,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends GenericMElement<ELEMENT_TYPE>, Wrapper<WRAPPED_TYPE, ELEMENT_TYPE> {
	WRAPPED_TYPE delegate();

	@Override
	default void scaleSelf(double scale) {
		((GenericMElement<?>) delegate()).scaleSelf(scale);
	}
}
