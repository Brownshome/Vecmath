package brownshome.vecmath.vector.wrapped;

import brownshome.vecmath.generic.GenericElement;

/**
 * A basic shared implementation of a wrapper
 * @param <WRAPPED_TYPE> the type that is being wrapped
 * @param <ELEMENT_TYPE> the type that is converted to
 */
public abstract class BasicWrapper<
		WRAPPED_TYPE extends GenericElement<? super WRAPPED_TYPE>,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> implements Wrapper<WRAPPED_TYPE, ELEMENT_TYPE> {
	private final WRAPPED_TYPE delegate;

	public BasicWrapper(WRAPPED_TYPE delegate) {
		assert delegate != null;
		this.delegate = delegate;
	}

	@Override
	public WRAPPED_TYPE delegate() {
		return delegate;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass()
				&& ((BasicWrapper<?, ?>) obj).delegate.equals(delegate);
	}

	@Override
	public String toString() {
		return delegate.toString();
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}
}
