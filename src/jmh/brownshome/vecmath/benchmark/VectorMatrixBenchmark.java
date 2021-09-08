package brownshome.vecmath.benchmark;

import java.util.Random;

import brownshome.vecmath.matrix.*;
import org.openjdk.jmh.annotations.*;

public class VectorMatrixBenchmark {
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
			A = Matrix.zeros(MatrixLayout.optimal(size, size));
			B = Matrix.zeros(MatrixLayout.optimal(size, size));

			for (int i = 0; i < A.backingArray().length; i++) {
				A.backingArray()[i] = random.nextDouble();
				B.backingArray()[i] = random.nextDouble();
			}

			factorisation = A.factorise();
		}
	}

	@Benchmark
	public Matrix multiplyMatrix(MatrixData data) {
		return data.A.multiply(data.B);
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