package topinterviewquestions;

public class Problem_0073_SetMatrixZeroes {

	//you will have to keep the first row or first colon not all zeros to keep track
	public static void setZeroes(int[][] matrix) {
		boolean row0Zero = false;
		boolean col0Zero = false;
		int i = 0;
		int j = 0;
		for (i = 0; i < matrix[0].length; i++) {//find if the first row need to be set 0 or not
			if (matrix[0][i] == 0) {
				row0Zero = true;
				break;
			}
		}
		for (i = 0; i < matrix.length; i++) {//find if the first col need to be set 0 or not
			if (matrix[i][0] == 0) {
				col0Zero = true;
				break;
			}
		}
		for (i = 1; i < matrix.length; i++) {//set the first row and first col to 0 if any element in the middle is 0
			for (j = 1; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					matrix[0][j] = 0;
				}
			}
		}
		for (i = 1; i < matrix.length; i++) {//set all the middle element which the corresponding first row or first col is 0 to 0
			for (j = 1; j < matrix[0].length; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) {
					matrix[i][j] = 0;
				}
			}
		}
		if (row0Zero) {//set the first row elements to zero is it is true
			for (i = 0; i < matrix[0].length; i++) {
				matrix[0][i] = 0;
			}
		}
		if (col0Zero) {//set the first col elements to zero is it is true
			for (i = 0; i < matrix.length; i++) {
				matrix[i][0] = 0;
			}
		}
	}

	public static void setZeroes2(int[][] matrix) {
		boolean col0 = false;
		int i = 0;
		int j = 0;
		for (i = 0; i < matrix.length; i++) {
			for (j = 0; j < matrix[0].length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][0] = 0;
					if (j == 0) {
						col0 = true;
					} else {
						matrix[0][j] = 0;
					}
				}
			}
		}
		for (i = matrix.length - 1; i >= 0; i--) {//it has to start from the matrix.length-1, to 0, cannot reverse. or it will be all 0s
			for (j = 1; j < matrix[0].length; j++) {
				if (matrix[i][0] == 0 || matrix[0][j] == 0) {
					matrix[i][j] = 0;
				}
			}
		}
		if (col0) {
			for (i = 0; i < matrix.length; i++) {
				matrix[i][0] = 0;
			}
		}
	}

}
