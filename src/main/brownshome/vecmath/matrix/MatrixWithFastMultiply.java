package brownshome.vecmath.matrix;

/**
 * Marker interface indicating that this class can perform multiplications more quickly.
 * <p>
 * Classes should take care delegating multiplications to the other operand to avoid infinite recusions
 */
public interface MatrixWithFastMultiply extends Matrix {
	/**
	 * Gets the result of multiplying other by this matrix
	 * @param other the other matrix
	 * @return the result
	 */
	default Matrix multiplyLeft(Matrix other) {
		return transpose().multiply(other.transpose()).transpose();
	}
}
