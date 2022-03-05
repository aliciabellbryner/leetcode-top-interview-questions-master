package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0054_SpiralMatrix {

    public static List<Integer> spiralOrder(int[][] matrix) {


		//https://leetcode.com/problems/spiral-matrix/discuss/20599/Super-Simple-and-Easy-to-Understand-Solution
		//I traverse right and increment rowBegin, then traverse down and decrement colEnd, then I traverse left and decrement rowEnd, and finally I traverse up and increment colBegin.
		//
		//The only tricky part is that when I traverse left or up I have to check whether the row or col still exists to prevent duplicates.
		class Solution {
			public List<Integer> spiralOrder(int[][] matrix) {

				List<Integer> res = new ArrayList<Integer>();

				if (matrix.length == 0) {
					return res;
				}

				int rowBegin = 0;
				int rowEnd = matrix.length-1;
				int colBegin = 0;
				int colEnd = matrix[0].length - 1;

				while (rowBegin <= rowEnd && colBegin <= colEnd) {
					// Traverse Right
					for (int j = colBegin; j <= colEnd; j ++) {
						res.add(matrix[rowBegin][j]);
					}
					rowBegin++;

					// Traverse Down
					for (int j = rowBegin; j <= rowEnd; j ++) {
						res.add(matrix[j][colEnd]);
					}
					colEnd--;

					if (rowBegin <= rowEnd) {
						// Traverse Left
						for (int j = colEnd; j >= colBegin; j --) {
							res.add(matrix[rowEnd][j]);
						}
					}
					rowEnd--;

					if (colBegin <= colEnd) {
						// Traver Up
						for (int j = rowEnd; j >= rowBegin; j --) {
							res.add(matrix[j][colBegin]);
						}
					}
					colBegin ++;
				}

				return res;
			}
		}



		//solution2
		//matrix不一定是正方形
		List<Integer> ans = new ArrayList<>();
		if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
			return ans;
		}
		int tR = 0;//top row
		int tC = 0;//top column
		int dR = matrix.length - 1;//down row
		int dC = matrix[0].length - 1;//down column
		while (tR <= dR && tC <= dC) {
			addEdge(matrix, tR++, tC++, dR--, dC--, ans);
		}
		return ans;
	}

	public static void addEdge(int[][] m, int tR, int tC, int dR, int dC, List<Integer> ans) {
		if (tR == dR) {
			for (int i = tC; i <= dC; i++) {
				ans.add(m[tR][i]);
			}
		} else if (tC == dC) {
			for (int i = tR; i <= dR; i++) {
				ans.add(m[i][tC]);
			}
		} else {
			int curC = tC;
			int curR = tR;
			while (curC != dC) {
				ans.add(m[tR][curC]);
				curC++;
			}
			while (curR != dR) {
				ans.add(m[curR][dC]);
				curR++;
			}
			while (curC != tC) {
				ans.add(m[dR][curC]);
				curC--;
			}
			while (curR != tR) {
				ans.add(m[curR][tC]);
				curR--;
			}
		}
	}

}
