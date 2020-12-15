package brownshome.vecmath.matrix;

class PermutationMatrix implements MatrixViewWithFastMultiply {
	private final int[] rows;

	PermutationMatrix(int[] rows) {
		assert rows != null;

		this.rows = rows;
	}

	@Override
	public int rows() {
		return rows.length;
	}

	@Override
	public int columns() {
		return rows.length;
	}

	@Override
	public double get(int row, int column) {
		return rows[row] == column ? 1.0 : 0.0;
	}

	@Override
	public Matrix multiply(MatrixView other) {
		return other.permuteRows(rows).copy();
	}

	@Override
	public Matrix leftMultiply(MatrixView other) {
		return other.permuteColumns(rows).copy();
	}

	@Override
	public MatrixView transpose() {
		return new PermutationMatrix(PermutationUtil.invertPermutation(rows)) {
			@Override
			public MatrixView transpose() {
				return PermutationMatrix.this;
			}
		};
	}

	@Override
	public MatrixView permuteRows(int... rows) {
		if (rows == null) {
			return this;
		}

		assert rows.length == this.rows.length;

		int[] combined = PermutationUtil.combinePermutations(this.rows, rows);

		if (combined == null) {
			return MatrixView.identity(this.rows.length);
		}

		return new PermutationMatrix(combined);
	}

	@Override
	public MatrixView permuteColumns(int... columns) {
		if (columns == null) {
			return this;
		}

		assert columns.length == this.rows.length;

		int[] combined = PermutationUtil.combinePermutations(columns, rows);

		if (combined == null) {
			return MatrixView.identity(this.rows.length);
		}

		return new PermutationMatrix(combined);
	}

	@Override
	public Factorisation factorise() {
		return new Factorisation() {
			@Override
			public int size() {
				return rows.length;
			}

			@Override
			public MatrixView leftSolve(MatrixView other) {
				return transpose().multiply(other);
			}

			@Override
			public MatrixView rightSolve(MatrixView other) {
				return other.multiply(transpose());
			}

			@Override
			public double determinant() {
				return PermutationUtil.parity(rows);
			}

			@Override
			public MatrixView inverse() {
				return transpose();
			}
		};
	}
}
