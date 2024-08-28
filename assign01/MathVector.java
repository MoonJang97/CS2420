package assign01;

/**
 * This class represents a simple row or column vector of numbers. In a row
 * vector, the numbers are written horizontally (i.e., along the columns). In a
 * column vector, the numbers are written vertically (i.e., along the rows).
 * 
 * @author CS 2420 course staff and Moon Jang
 * 
 * @version August 27, 2024
 */
public class MathVector {

	// 2D array to hold the numbers of the vector, either along the columns or rows
	private double[][] data;
	// set to true for a row vector and false for a column vector
	private boolean isRowVector;
	// count of elements in the vector
	private int vectorSize;

	/**
	 * Creates a new row or column vector. For a row vector, the input array is
	 * expected to have 1 row and a positive number of columns, and this number of
	 * columns represents the vector's length. For a column vector, the input array
	 * is expected to have 1 column and a positive number of rows, and this number
	 * of rows represents the vector's length.
	 * 
	 * @param data - a 2D array to hold the numbers of the vector
	 * @throws IllegalArgumentException if the numbers of rows and columns in the
	 *                                  input 2D array is not compatible with a row
	 *                                  or column vector
	 */
	public MathVector(double[][] data) {
		if (data.length == 0)
			throw new IllegalArgumentException("Number of rows must be positive.");
		if (data[0].length == 0)
			throw new IllegalArgumentException("Number of columns must be positive.");

		if (data.length == 1) {
			// This is a row vector with length = number of columns.
			this.isRowVector = true;
			this.vectorSize = data[0].length;
		} else if (data[0].length == 1) {
			for (int i = 1; i < data.length; i++)
				if (data[i].length != 1)
					throw new IllegalArgumentException("For each row, the number of columns must be 1.");
			// This is a column vector with length = number of rows.
			this.isRowVector = false;
			this.vectorSize = data.length;
		} else
			throw new IllegalArgumentException("Either the number of rows or the number of columns must be 1.");

		// Create the array and copy data over.
		if (this.isRowVector)
			this.data = new double[1][vectorSize];
		else
			this.data = new double[vectorSize][1];
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				this.data[i][j] = data[i][j];
			}
		}
	}

	/**
	 * Generates a textual representation of this vector.
	 * 
	 * For example, "1.0 2.0 3.0 4.0 5.0" for a sample row vector of length 5 and
	 * "1.0\n2.0\n3.0\n4.0\n5.0" for a sample column vector of length 5. In both
	 * cases, notice the lack of a newline or space after the last number.
	 * 
	 * @return textual representation of this vector
	 */
	public String toString() {
		// Create stringbuilder to convert it as string
		StringBuilder sb = new StringBuilder();

		// If the vector is row vector
		if (isRowVector) {
			for (int i = 0; i < vectorSize; i++) {
				sb.append(data[0][i]);
				if (i < vectorSize - 1)
					sb.append(" ");
			}
			// If the vector is column vector
		} else {
			for (int j = 0; j < vectorSize; j++) {
				sb.append(data[j][0]);
				if (j < vectorSize - 1)
					sb.append("\n");
			}
		}

		return sb.toString();

	}

	/**
	 * Determines whether this vector is "equal to" another vector, where equality
	 * is defined as both vectors being row (or both being column), having the same
	 * vector length, and containing the same numbers in the same positions.
	 * 
	 * @param other - another vector to compare
	 * @return true if this vector is equivalent to other, false otherwise
	 */
	public boolean equals(Object other) {
		if (!(other instanceof MathVector))
			return false;

		MathVector otherVec = (MathVector) other;

		// compare if they are either row or column vectors
		if (this.isRowVector != otherVec.isRowVector)
			return false;

		// compare if they have same size of the length
		if (this.vectorSize != otherVec.vectorSize)
			return false;

		// compare all elements in both vectors
		for (int i = 0; i < this.data.length; i++) {
			for (int j = 0; j < this.data[0].length; j++) {
				if (this.data[i][j] != otherVec.data[i][j])
					return false;
			}
		}

		// both vectors are equal if everything passed
		return true;
	}

	/**
	 * Updates this vector by using a given scaling factor to multiply each entry.
	 * 
	 * @param factor - the scaling factor
	 */
	public void scale(double factor) {
		// multiply each element by the scaling factor
		for (int i = 0; i < this.data[0].length; i++)
			for (int j = 0; j < this.data.length; j++)
				this.data[j][i] *= factor;
	}

	/**
	 * Generates a new vector that is the transposed version of this vector.
	 * 
	 * @return transposed version of this vector
	 */
	public MathVector transpose() {
		double[][] newVector;

		if (isRowVector) {
			newVector = new double[vectorSize][1];

			for (int i = 0; i < vectorSize; i++)
				newVector[i][0] = this.data[0][i];
		} else {
			newVector = new double[1][vectorSize];

			for (int j = 0; j < vectorSize; j++)
				newVector[0][j] = this.data[j][0];
		}

		return new MathVector(newVector);
	}

	/**
	 * Generates a new vector representing the sum of this vector and another
	 * vector.
	 * 
	 * @param other - another vector to be added to this vector
	 * @throws IllegalArgumentException if the other vector and this vector are not
	 *                                  both row vectors of the same length or
	 *                                  column vectors of the same length
	 * @return sum of this vector and other
	 */
	public MathVector add(MathVector other) {
		// throw if they are not same type of vectors
		if (this.isRowVector != other.isRowVector)
			throw new IllegalArgumentException("Both vectors should be either row or colunm vectors");

		// throw if they different size of the vector length
		if (this.vectorSize != other.vectorSize)
			throw new IllegalArgumentException("Both vectors's length should be samne");

		double[][] newVector;

		// if both are row vectors
		if (this.isRowVector) {
			// set up the size of the newVecto
			newVector = new double[1][this.vectorSize];
			for (int i = 0; i < vectorSize; i++)
				newVector[0][i] = this.data[0][i] + other.data[0][i];
		} else {
			// set up the size of the newVecto
			newVector = new double[this.vectorSize][1];
			for (int j = 0; j < vectorSize; j++)
				newVector[j][0] = this.data[j][0] + other.data[j][0];
		}

		return new MathVector(newVector);

	}

	/**
	 * Computes the dot product of this vector and another vector.
	 * 
	 * @param other - another vector to be combined with this vector to produce the
	 *              dot product
	 * @throws IllegalArgumentException if the other vector and this vector are not
	 *                                  both row vectors of the same length or
	 *                                  column vectors of the same length
	 * @return dot product of this vector and other
	 */
	public double dotProduct(MathVector other) {
		// throw if they are not same type of vectors
		if (this.isRowVector != other.isRowVector)
			throw new IllegalArgumentException("Both vectors should be either row or colunm vectors");

		// throw if they different size of the vector length
		if (this.vectorSize != other.vectorSize)
			throw new IllegalArgumentException("Both vectors's length should be samne");

		double result = 0.0;

		if (this.isRowVector) {
			// the sum multiplied by two vectors
			for (int i = 0; i < vectorSize; i++)
				result += this.data[0][i] * other.data[0][i];
		} else {
			// the sum multiplied by two vectors
			for (int j = 0; j < vectorSize; j++)
				result += this.data[j][0] * other.data[j][0];
		}

		return result;
	}

	/**
	 * Computes this vector's magnitude (also known as a vector's length).
	 * 
	 * @return magnitude of this vector
	 */
	public double magnitude() {
		double result = 0.0;

		if (isRowVector) {
			// Add the square of each value
			for (int i = 0; i < vectorSize; i++)
				result += this.data[0][i] * this.data[0][i];
		} else {
			// Add the square of each value
			for (int j = 0; j < vectorSize; j++)
				result += this.data[j][0] * this.data[j][0];
		}

		return Math.sqrt(result);
	}

	/**
	 * Updates this vector by using standardizing it (AKA normalization).
	 */
	public void normalize() {
		double mag = this.magnitude();

		// divide each element by magnitude of the vector
		for (int i = 0; i < this.data.length; i++)
			for (int j = 0; j < this.data[0].length; j++)
				this.data[i][j] /= mag;
	}

}