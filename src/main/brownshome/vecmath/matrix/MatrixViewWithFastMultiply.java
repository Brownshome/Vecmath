package brownshome.vecmath.matrix;

/**
 * Marker interface indicating that this class can perform multiplications more quickly.
 *
 * Classes implementing this interface must not delegate multiplication operations to the superclass or the other operand
 */
public interface MatrixViewWithFastMultiply extends MatrixView {
	Matrix leftMultiply(MatrixView other);
}
