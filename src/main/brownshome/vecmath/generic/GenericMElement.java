package brownshome.vecmath.generic;

/**
 * An internal interface defining the properties of a mathematical vector as part of a vector field. Examples include
 * matrices and vectors.
 *
 * @param <ELEMENT_TYPE> the type of this element
 */
@SuppressWarnings("unchecked")
public interface GenericMElement<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends GenericElement<ELEMENT_TYPE> {
	/**
	 * Sets this element
	 * @param e the value to set this one to
	 */
	void set(ELEMENT_TYPE e);

	/**
	 * Adds an element to this one
	 * @param e the element to add
	 */
	void addToSelf(ELEMENT_TYPE e);

	/**
	 * Subtracts an element from this one
	 * @param e the element to subtract
	 */
	default void subtractFromSelf(ELEMENT_TYPE e) {
		scaleAddToSelf(e, -1.0);
	}

	/**
	 * Scales this element
	 * @param scale the scale to apply
	 */
	void scaleSelf(double scale);

	/**
	 * Scales this element
	 * @param scale the scale to apply
	 */
	void scaleSelf(ELEMENT_TYPE scale);

	/**
	 * Sets this to be this + element * scale
	 * @param e The element to add
	 * @param scale The number to scale it by
	 */
	default void scaleAddToSelf(ELEMENT_TYPE e, double scale) {
		addToSelf(e.scale(scale));
	}

	/**
	 * Negates this element
	 */
	default void setToNegated() {
		scaleSelf(-1.0);
	}

	/**
	 * Performs a linear interpolation between this and other.
	 * this = this * (1-t) + other * t
	 * @param other The other element to interpolate to
	 * @param t The time factor
	 */
	default void setToInterpolated(ELEMENT_TYPE other, double t) {
		scaleSelf(1.0 - t);
		scaleAddToSelf(other, t);
	}

	@Override
	default ELEMENT_TYPE move() {
		return (ELEMENT_TYPE) this;
	}
}
