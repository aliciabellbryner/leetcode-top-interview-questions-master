package topinterviewquestions;

import java.util.*;

public class Problem_0057_InsertInterval {

	//Time complexity : O(N) since it's one pass along the input array.
	//Space complexity : O(N) to keep the res.
	public int[][] insert(int[][] intervals, int[] newin) {
		List<int[]> res = new ArrayList<>();

		for(int[] interval : intervals) {
			if(newin[1] < interval[0]) {
				res.add(newin);
				newin = interval ;
			} else if(interval[1] < newin[0]) {
				res.add(interval);
			} else {
				newin[0] = Math.min(newin[0],interval[0]) ;
				newin[1] = Math.max(newin[1],interval[1]) ;
			}
		}
		res.add(newin);

		return res.toArray(new int[res.size()][]);
	}
}
