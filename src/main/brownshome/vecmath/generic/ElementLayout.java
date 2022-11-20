package brownshome.vecmath.generic;

/**
 * The layout of the backing array for an element
 */
public interface ElementLayout {
	/**
	 * The index of the first element in the backing array
	 * @return the index
	 */
	default int start() {
		return end() - size();
	}

	/**
	 * The index one past the last element in the backing array
	 * @return the index
	 */
	default int end() {
		return start() + size();
	}

	/**
	 * The size of the segment of the backing array relevant to this element
	 * @return the size of the segment
	 */
	default int size() {
		return end() - start();
	}

	/**
	 * Whether all array elements between start and end belong to this element
	 * @return a boolean
	 */
	boolean isContinuous();

	/**
	 * Whether every item of the backing array is part of this element
	 * @return a boolean
	 */
	boolean isPacked();
}
