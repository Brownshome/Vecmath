package brownshome.vecmath.benchmark;

import java.util.Random;

import brownshome.vecmath.matrix.*;
import brownshome.vecmath.matrix.basic.SymmetricMatrixView;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import org.openjdk.jmh.annotations.*;

public class SymmetricMatrixBenchmark {
	@State(Scope.Benchmark)
	public static class MatrixData {
		private static final int SEED = 1245;

		@Param({"8", "32", "128"})
		public int size;

		private SymmetricMatrixView A, B;
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

			A = SymmetricMatrixView.of(arrayA, size);
			B = SymmetricMatrixView.of(arrayB, size);

			factorisation = A.factorise();
		}
	}

	@Benchmark
	public Factorisation factoriseMatrix(MatrixData data) {
		return data.A.factorise();
	}

	@Benchmark
	public MatrixView solveLeft(MatrixData data) {
		return data.factorisation.leftSolve(data.B);
	}

	@Benchmark
	public MatrixView solveRight(MatrixData data) {
		return data.factorisation.rightSolve(data.B);
	}

	@Benchmark
	public MatrixView divideMatrix(MatrixData data) {
		return data.A.leftDivide(data.B);
	}
}
