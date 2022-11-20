package brownshome.vecmath.generic;

import java.util.Arrays;

/**
 * An element backed by an array
 *
 * @param <LAYOUT_TYPE> the layout type of this array-backed element
 * @param <ELEMENT_TYPE> the element type
 */
@SuppressWarnings("unchecked")
public interface GenericArrayElement<
		LAYOUT_TYPE extends ElementLayout,
		ELEMENT_TYPE extends GenericElement<ELEMENT_TYPE>> extends GenericMElement<ELEMENT_TYPE> {
	/**
	 * The layout of this element
	 * @return the layout of this element
	 */
	LAYOUT_TYPE layout();

	/**
	 * The backing array for this element
	 * @return the backing array
	 */
	double[] backingArray();

	@Override
	default void set(ELEMENT_TYPE e) {
		var cast = (GenericArrayElement<LAYOUT_TYPE, ELEMENT_TYPE>) e;
		assert layout().isContinuous();
		assert cast.layout().equals(layout());

		System.arraycopy(cast.backingArray(), cast.layout().start(), backingArray(), layout().start(), layout().size());
	}

	@Override
	default void addToSelf(ELEMENT_TYPE e) {
		var cast = (GenericArrayElement<LAYOUT_TYPE, ELEMENT_TYPE>) e;
		assert layout().isContinuous();
		assert cast.layout().equals(layout());

		for (int i = layout().start(), j = cast.layout().start(); i < layout().end(); i++, j++) {
			backingArray()[i] += cast.backingArray()[j];
		}
	}

	@Override
	default void scaleSelf(double scale) {
		assert layout().isContinuous();

		for (int i = layout().start(); i < layout().end(); i++) {
			backingArray()[i] *= scale;
		}
	}

	@Override
	default void scaleSelf(ELEMENT_TYPE scale) {
		var cast = (GenericArrayElement<LAYOUT_TYPE, ELEMENT_TYPE>) scale;
		assert layout().isContinuous();
		assert cast.layout().equals(layout());

		for (int i = layout().start(), j = cast.layout().start(); i < layout().end(); i++, j++) {
			backingArray()[i] += cast.backingArray()[j];
		}
	}

	@Override
	default boolean exactEquals(ELEMENT_TYPE other) {
		var cast = (GenericArrayElement<LAYOUT_TYPE, ELEMENT_TYPE>) other;
		assert layout().isContinuous();
		assert cast.layout().equals(layout());

		return Arrays.equals(backingArray(), layout().start(), layout().end(), cast.backingArray(), cast.layout().start(), cast.layout().end());
	}

	@Override
	default ELEMENT_TYPE asArrayBacked() {
		return (ELEMENT_TYPE) this;
	}

	/**
	 * An array-backed copy of this element with the given layout
	 * @return a copy
	 */
	ELEMENT_TYPE arrayBackedCopy(LAYOUT_TYPE layout);
}
