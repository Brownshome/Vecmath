package brownshome.vecmath.benchmark;

import java.util.Random;

import brownshome.vecmath.matrix.*;
import jdk.incubator.vector.DoubleVector;
import org.openjdk.jmh.annotations.*;

public class NoOverheadBenchmark {
	@State(Scope.Benchmark)
	public static class MatrixData {
		private static final int SEED = 1245;

		@Param({"8", "32", "128"})
		public int size;

		private Matrix A, B;
		private Factorisation factorisation;

		@Setup
		public void setup() {
			Random random = new Random(SEED);
			A = Matrix.zeros(size, size);
			B = Matrix.zeros(size, size);

			for (int i = 0; i < A.backingArray().length; i++) {
				A.backingArray()[i] = random.nextDouble();
				B.backingArray()[i] = random.nextDouble();
			}

			factorisation = A.factorise();
		}
	}

	@Benchmark
	public double[] multiplyMatrixVector(MatrixData data) {
		var species = DoubleVector.SPECIES_PREFERRED;

		double[] a = data.A.backingArray();
		double[] b = data.B.backingArray();
		double[] n = new double[data.size * data.size];

		for (int r = 0; r < data.size; r++) {
			for (int c = 0; c < data.size; c += species.length()) {
				var acc = DoubleVector.zero(species);

				for (int k = 0; k < data.size; k++) {
					var scale = DoubleVector.broadcast(species, a[r * data.size + c]);
					var row = DoubleVector.fromArray(species, b, k * data.size + c);

					acc = row.fma(scale, acc);
				}

				acc.intoArray(n, r * data.size + c);
			}
		}

		return n;
	}

	@Benchmark
	public double[] multiplyMatrixScalar(MatrixData data) {
		var species = DoubleVector.SPECIES_PREFERRED;

		double[] a = data.A.backingArray();
		double[] b = data.B.backingArray();
		double[] n = new double[data.size * data.size];

		for (int r = 0; r < data.size; r++) {
			for (int k = 0; k < data.size; k++) {
				for (int c = 0; c < data.size; c++) {
					n[r * data.size + c] += a[r * data.size + k] * b[k * data.size + c];
				}
			}
		}

		return n;
	}
}
