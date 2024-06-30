package brownshome.vecmath.complex.basic;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import brownshome.vecmath.complex.generic.GenericComplex;
import brownshome.vecmath.generic.GenericElement;

/**
 * The base class of all complex number implementations
 */
public abstract class BaseGenericComplex<
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>,
		COMPLEX_TYPE extends ELEMENT_TYPE> implements GenericComplex<ELEMENT_TYPE, COMPLEX_TYPE> {
	@Override
	public String toString() {
		var unknown = asComplexUnknownSize();
		return IntStream.range(0, unknown.size())
				.mapToObj(unknown::get)
				.map("%s"::formatted)
				.collect(Collectors.joining(", ", "(", ")"));
	}
}
