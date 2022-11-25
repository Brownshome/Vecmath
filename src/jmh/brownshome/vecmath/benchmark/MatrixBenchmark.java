package brownshome.vecmath.benchmark;

import java.util.Random;

import brownshome.vecmath.matrix.*;
import brownshome.vecmath.matrix.array.ArrayMatrix;
import brownshome.vecmath.matrix.factorisation.Factorisation;
import brownshome.vecmath.matrix.layout.MatrixLayout;
import org.openjdk.jmh.annotations.*;

public class MatrixBenchmark {
	@State(Scope.Benchmark)
	public static class MatrixData {
		private static final int SEED = 1245;

		@Param({"8", "32", "128"})
		public int size;

		private ArrayMatrix A, B;
		private Factorisation factorisation;

		@Setup
		public void setup() {
			Random random = new Random(SEED);
			A = Matrix.of(MatrixLayout.ofOptimal(size, size));
			B = Matrix.of(MatrixLayout.ofOptimal(size, size));

			for (int i = 0; i < A.backingArray().length; i++) {
				A.backingArray()[i] = random.nextDouble();
				B.backingArray()[i] = random.nextDouble();
			}

			factorisation = A.factorisation();
		}
	}

	@Benchmark
	public Matrix multiplyMatrix(MatrixData data) {
		return data.A.multiply(data.B);
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
