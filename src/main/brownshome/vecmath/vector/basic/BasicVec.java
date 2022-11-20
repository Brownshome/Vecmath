package brownshome.vecmath.vector.basic;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import brownshome.vecmath.vector.generic.GenericMVec;
import brownshome.vecmath.vector.generic.GenericVec;

public abstract class BasicVec<VEC_TYPE extends GenericVec<VEC_TYPE>> implements GenericMVec<VEC_TYPE> {
	@Override
	public final String toString() {
		return IntStream.range(0, asUnknownSize().size())
				.mapToDouble(asUnknownSize()::get)
				.boxed()
				.map("%.3f"::formatted)
				.collect(Collectors.joining(", ", "(", ")"));
	}
}
