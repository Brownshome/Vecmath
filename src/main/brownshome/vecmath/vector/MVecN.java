package brownshome.vecmath.vector;

import brownshome.vecmath.matrix.MMatrix;
import brownshome.vecmath.vector.generic.GenericMVec;
import brownshome.vecmath.vector.wrapped.MVecNWrapper;

/**
 * A mutable arbitrary-element vector
 */
public interface MVecN extends GenericMVec<VecN>, VecN {
	/**
	 * Sets the ith element of this vector
	 * @param value the value to set
	 * @param index the index to set
	 */
	void set(double value, int index);

	/**
	 * Sets this vector component-wise
	 * @param values the components in this vector
	 */
	default void set(double... values) {
		set(VecN.of(values));
	}

	@Override
	default void set(VecN vec) {
		assert size() == vec.size();

		for (int i = 0; i < size(); i++) {
			set(vec.get(i), i);
		}
	}

	/**
	 * Adds an amount to this vector
	 * @param values that amount to add to each element in this vector
	 */
	default void addToSelf(double... values) {
		addToSelf(VecN.of(values));
	}

	@Override
	default void addToSelf(VecN vec) {
		assert size() == vec.size();

		for (int i = 0; i < size(); i++) {
			set(get(i) + vec.get(i), i);
		}
	}

	@Override
	default void scaleSelf(double scale) {
		for (int i = 0; i < size(); i++) {
			set(get(i) * scale, i);
		}
	}

	@Override
	default void scaleSelf(VecN scale) {
		assert size() == scale.size();

		for (int i = 0; i < size(); i++) {
			set(get(i) * scale.get(i), i);
		}
	}

	@Override
	default MVecN asUnknownSize() {
		return this;
	}

	@Override
	default MVec2 asVec2() {
		return new MVecNWrapper.BasicToVec2(this);
	}

	@Override
	default MVec3 asVec3() {
		return new MVecNWrapper.BasicToVec3(this);
	}

	@Override
	default MVec4 asVec4() {
		return new MVecNWrapper.BasicToVec4(this);
	}

	@Override
	default MMatrix asRow() {
		return (MMatrix) VecN.super.asRow();
	}

	@Override
	default MMatrix asColumn() {
		return new MVecNWrapper.BasicToMatrix(this);
	}

	@Override
	default MVecN move() {
		return (MVecN) GenericMVec.super.move();
	}
}
