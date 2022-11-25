package brownshome.vecmath.benchmark;

import java.util.Random;

import brownshome.vecmath.matrix.Matrix;
import brownshome.vecmath.matrix.basic.SymmetricMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import org.openjdk.jmh.annotations.*;

public class SymmetricMatrixBenchmark {
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
			var arrayA = new double[size * size];
			var arrayB = new double[size * size];

			for (int i = 0; i < arrayA.length; i++) {
				arrayA[i] = random.nextDouble();
				arrayB[i] = random.nextDouble();
			}

			A = Matrix.ofSymmetric(arrayA, MatrixLayout.ofSymmetricRowMajor(size));
			B = Matrix.ofSymmetric(arrayB, MatrixLayout.ofSymmetricRowMajor(size));

			factorisation = A.factorisation();
		}
	}

	@Benchmark
	public Factorisation factoriseMatrix(MatrixData data) {
		return data.A.factorisation();
	}

	@Benchmark
	public Matrix solveLeft(MatrixData data) {
		return data.factorisation.leftSolve(data.B);
	}

	@Benchmark
	public Matrix solveRight(MatrixData data) {
		return data.factorisation.rightSolve(data.B);
	}

	@Benchmark
	public Matrix divideMatrix(MatrixData data) {
		return data.A.divideLeft(data.B);
	}
}
