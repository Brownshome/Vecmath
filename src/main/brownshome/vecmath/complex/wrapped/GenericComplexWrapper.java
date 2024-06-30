package brownshome.vecmath.complex.wrapped;

import brownshome.vecmath.complex.generic.GenericComplex;
import brownshome.vecmath.generic.GenericElement;
import brownshome.vecmath.vector.wrapped.Wrapper;

public interface GenericComplexWrapper<
		WRAPPED_TYPE extends GenericComplex<? super WRAPPED_TYPE, WRAPPED_TYPE>,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends Wrapper<WRAPPED_TYPE, ELEMENT_TYPE> {
}
