package topinterviewquestions;

import java.util.*;

/*
Given an m x n integer matrix grid where each entry is only 0 or 1, return the number of corner rectangles.

A corner rectangle is four distinct 1's on the grid that forms an axis-aligned rectangle. Note that only the corners need to have the value 1. Also, all four 1's used must be distinct.



Example 1:


Input: grid = [[1,0,0,1,0],[0,0,1,0,1],[0,0,0,1,0],[1,0,1,0,1]]
Output: 1
Explanation: There is only one corner rectangle, with corners grid[1][2], grid[1][4], grid[3][2], grid[3][4].
Example 2:


Input: grid = [[1,1,1],[1,1,1],[1,1,1]]
Output: 9
Explanation: There are four 2x2 rectangles, four 2x3 and 3x2 rectangles, and one 3x3 rectangle.
Example 3:


Input: grid = [[1,1,1,1]]
Output: 0
Explanation: Rectangles must have four distinct corners.


Constraints:

m == grid.length
n == grid[i].length
1 <= m, n <= 200
grid[i][j] is either 0 or 1.
The number of 1's in the grid is in the range [1, 6000].
 */
public class Problem_0750_NumberOfCornerRectangles {
/*
Approach #1: Count Corners [Accepted]
Intuition

We ask the question: for each additional row, how many more rectangles are added?

For each pair of 1s in the new row (say at new_row[i] and new_row[j]), we could create more rectangles where that pair forms the base. The number of new rectangles is the number of times some previous row had row[i] = row[j] = 1.

Algorithm

Let's maintain a count count[i, j], the number of times we saw row[i] = row[j] = 1. When we process a new row, for every pair new_row[i] = new_row[j] = 1, we add count[i, j] to the answer, then we increment count[i, j].


Complexity Analysis

Time Complexity: O(R*C^2)O(R∗C
2
 ) where R, CR,C is the number of rows and columns.

Space Complexity: O(C^2)O(C
2
 ) in additional space.

Approach #2: Heavy and Light Rows [Accepted]
Intuition and Algorithm

Can we improve on the ideas in Approach #1? When a row is filled with XX 1s, we do O(X^2)O(X
2
 ) work to enumerate every pair of 1s. This is okay when XX is small, but expensive when XX is big.

Say the entire top row is filled with 1s. When looking at the next row with say, f 1s that match the top row, the number of rectangles created is just the number of pairs of 1s, which is f * (f-1) / 2. We could find each f quickly using a Set and a simple linear scan of each row.

Let's call a row to be heavy if it has more than \sqrt N
N
​
  points. The above algorithm changes the complexity of counting a heavy row from O(C^2)O(C
2
 ) to O(N)O(N), and there are at most \sqrt N
N
​
  heavy rows.


Complexity Analysis

Time Complexity: O(N \sqrt N + R*C)O(N
N
​
 +R∗C) where NN is the number of ones in the grid.

Space Complexity: O(N + R + C^2)O(N+R+C
2
 ) in additional space, for rows, target, and count.
 */
class Solution1 {
    public int countCornerRectangles(int[][] grid) {
        Map<Integer, Integer> count = new HashMap();
        int ans = 0;
        for (int[] row: grid) {
            for (int c1 = 0; c1 < row.length; ++c1) if (row[c1] == 1) {
                for (int c2 = c1+1; c2 < row.length; ++c2) if (row[c2] == 1) {
                    int pos = c1 * 200 + c2;
                    int c = count.getOrDefault(pos, 0);
                    ans += c;
                    count.put(pos, c+1);
                }
            }
        }
        return ans;
    }
}


    class Solution2 {
        public int countCornerRectangles(int[][] grid) {
            List<List<Integer>> rows = new ArrayList();
            int N = 0;
            for (int r = 0; r < grid.length; ++r) {
                rows.add(new ArrayList());
                for (int c = 0; c < grid[r].length; ++c)
                    if (grid[r][c] == 1) {
                        rows.get(r).add(c);
                        N++;
                    }
            }

            int sqrtN = (int) Math.sqrt(N);
            int ans = 0;
            Map<Integer, Integer> count = new HashMap();

            for (int r = 0; r < grid.length; ++r) {
                if (rows.get(r).size() >= sqrtN) {
                    Set<Integer> target = new HashSet(rows.get(r));

                    for (int r2 = 0; r2 < grid.length; ++r2) {
                        if (r2 <= r && rows.get(r2).size() >= sqrtN)
                            continue;
                        int found = 0;
                        for (int c2: rows.get(r2))
                            if (target.contains(c2))
                                found++;
                        ans += found * (found - 1) / 2;
                    }
                } else {
                    for (int i1 = 0; i1 < rows.get(r).size(); ++i1) {
                        int c1 = rows.get(r).get(i1);
                        for (int i2 = i1 + 1; i2 < rows.get(r).size(); ++i2) {
                            int c2 = rows.get(r).get(i2);
                            int ct = count.getOrDefault(200*c1 + c2, 0);
                            ans += ct;
                            count.put(200*c1 + c2, ct + 1);
                        }
                    }
                }
            }
            return ans;
        }
    }



    //diss
    //To find an axis-aligned rectangle, my idea is to fix two rows (or two columns) first, then check column by column to find "1" on both rows. Say you find n pairs, then just pick any 2 of those to form an axis-aligned rectangle (calculating how many in total is just high school math, hint: combination).
    /**
     * first step: we can keep two horizontal line x1 and x2 unchanged, and use a moving vertical line to scan from
     * left to right to count number of valid vertical line we can find between x1 and x2 (valid means both of
     * grid[x1][k] and grid[x2][k] is equal to 1). Because any two valid vertical lines between x1 and x2 can form
     * a rectangle with connecting two horizontal lines x1 and x2
     *
     * second step: after counting number of vertical lines between two horizontal lines, we can calculate number of
     * rectangles between x1 and x2. The equation should be equivalent to select 2 from total number of vertical lines
     * i.e.: count * (count - 1) / 2
     * */
    public int countCornerRectangles(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int result = 0;

        // traverse all possible combination of two horizontal lines x1 and x2
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int count = 0;
                // scan from left to right, count number of valid vertical lines between two i and j
                for (int k = 0; k < m; k++) {
                    if (grid[i][k] == 1 && grid[j][k] == 1) {
                        count++;
                    }
                }

                // calculate number of new corner rectangles
                result += count * (count - 1) / 2;
            }
        }
        return result;
    }

}
