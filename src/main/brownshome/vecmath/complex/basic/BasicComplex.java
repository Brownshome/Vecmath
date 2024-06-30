package brownshome.vecmath.complex.basic;

import brownshome.vecmath.complex.Complex;
import brownshome.vecmath.complex.MComplex;
import brownshome.vecmath.vector.Vec2;

public class BasicComplex extends BaseGenericComplex<Vec2, Complex> implements MComplex {
	private double real, imaginary;

	public BasicComplex(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public BasicComplex(Complex x) {
		this(x.real(), x.imaginary());
	}

	@Override
	public void real(double r) {
		real = r;
	}

	@Override
	public void imaginary(double i) {
		imaginary = i;
	}

	@Override
	public double real() {
		return real;
	}

	@Override
	public double imaginary() {
		return imaginary;
	}
}
