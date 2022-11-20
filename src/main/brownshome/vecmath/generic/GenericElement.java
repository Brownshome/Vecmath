package brownshome.vecmath.generic;

/**
 * An internal interface defining the properties of a mathematical vector as part of a vector field. Examples include
 * matrices and vectors.
 *
 * @param <ELEMENT_TYPE> the type of this element
 */
@SuppressWarnings("unchecked")
public interface GenericElement<ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> {
	/**
	 * The sum of this element and another one
	 * @param e the element to add
	 * @return the sum
	 */
	default ELEMENT_TYPE add(ELEMENT_TYPE e) {
		var result = (GenericMElement<ELEMENT_TYPE>) copy();
		result.addToSelf(e);
		return (ELEMENT_TYPE) result;
	}

	/**
	 * The result of subtracting the given element from this one
	 * @param e the element to subtract
	 * @return the difference
	 */
	default ELEMENT_TYPE subtract(ELEMENT_TYPE e) {
		var result = (GenericMElement<ELEMENT_TYPE>) copy();
		result.subtractFromSelf(e);
		return (ELEMENT_TYPE) result;
	}

	/**
	 * This element scaled
	 * @param scale the amount to scale by
	 * @return a scaled version of this element
	 */
	default ELEMENT_TYPE scale(double scale) {
		var result = (GenericMElement<ELEMENT_TYPE>) copy();
		result.scaleSelf(scale);
		return (ELEMENT_TYPE) result;
	}

	/**
	 * This element scaled element-wise
	 * @param scale the amount to scale by
	 * @return a scaled version of this element
	 */
	default ELEMENT_TYPE scale(ELEMENT_TYPE scale) {
		var result = (GenericMElement<ELEMENT_TYPE>) copy();
		result.scaleSelf(scale);
		return (ELEMENT_TYPE) result;
	}

	/**
	 * This element added to the given element scaled
	 * @param e the element to add
	 * @param scale the scale of that element
	 * @return the result
	 */
	default ELEMENT_TYPE scaleAdd(ELEMENT_TYPE e, double scale) {
		var result = (GenericMElement<ELEMENT_TYPE>) copy();
		result.scaleAddToSelf(e, scale);
		return (ELEMENT_TYPE) result;
	}

	/**
	 * This element scaled by -1
	 * @return the result
	 */
	default ELEMENT_TYPE negated() {
		var result = (GenericMElement<ELEMENT_TYPE>) copy();
		result.setToNegated();
		return (ELEMENT_TYPE) result;
	}

	/**
	 * A element interpolated between this element and the given element
	 * @param other the element to interpolate to
	 * @param t the interpolation value, 0 results in this element, and 1 results in the other element. This must be between zero and one.
	 * @return the result
	 */
	default ELEMENT_TYPE interpolated(ELEMENT_TYPE other, double t) {
		var result = (GenericMElement<ELEMENT_TYPE>) copy();
		result.setToInterpolated(other, t);
		return (ELEMENT_TYPE) result;
	}

	/**
	 * Returns true if this element is exactly equal to the given element
	 * @param other the other element
	 * @return if every element of this element is equal to the given one
	 */
	boolean exactEquals(ELEMENT_TYPE other);

	/**
	 * Returns an array-backed element with the same contents as this object. The result of this
	 * call might be the same object if it is already array-backed.
	 *
	 * @return an array-backed element
	 */
	default ELEMENT_TYPE asArrayBacked() {
		return arrayBackedCopy();
	}

	/**
	 * An array-backed copy of this element with an optimal layout
	 * @return a copy
	 */
	ELEMENT_TYPE arrayBackedCopy();

	/**
	 * Returns a mutable copy of this object
	 * @return a copy
	 */
	ELEMENT_TYPE copy();

	/**
	 * A mutable element with the same content as this object. This may or may not be a copy. This method should only be used
	 * if the original source of this element is no-longer needed.
	 * @return a mutable element
	 */
	default ELEMENT_TYPE move() {
		return copy();
	}
}
