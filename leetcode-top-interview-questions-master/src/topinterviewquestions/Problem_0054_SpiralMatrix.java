package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;

public class Problem_0054_SpiralMatrix {

    public static List<Integer> spiralOrder(int[][] matrix) {
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
