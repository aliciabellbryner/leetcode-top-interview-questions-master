package topinterviewquestions;

import java.util.*;
/*
Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input.



Example 1:

Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
Example 2:

Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping.


Constraints:

1 <= intervals.length <= 104
intervals[i].length == 2
0 <= starti <= endi <= 104
 */
public class Problem_0056_MergeIntervals {

	//time O(nlogn): Other than the sort invocation, we do a simple linear scan of the list,
	// so the runtime is dominated by the O(nlogn) complexity of sorting.
	//space O(logn): the quick sort itself takes O(logn) space ( (for the call stack).)
	public int[][] merge(int[][] intervals) {
		Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));//sort the intervals using the [0] element
		LinkedList<int[]> res = new LinkedList<>();
		for (int[] interval : intervals) {
			// if the list of res intervals is empty or if the current
			// interval does not overlap with the previous, simply append it.
			if (res.isEmpty() || res.getLast()[1] < interval[0]) {
				res.add(interval);
			}
			// otherwise, there is overlap, so we merge the current and previous
			// intervals.
			else {
				res.getLast()[1] = Math.max(res.getLast()[1], interval[1]);
			}
		}
		return res.toArray(new int[res.size()][]);
	}




	//Zuo's simple solution
	public static class Range {
		public int start;
		public int end;

		public Range(int s, int e) {
			start = s;
			end = e;
		}
	}

	public static class RangeComparator implements Comparator<Range> {

		@Override
		public int compare(Range o1, Range o2) {
			return o1.start - o2.start;
		}

	}
	// intervals  N * 2
	public static int[][] merge1(int[][] intervals) {
		if (intervals.length == 0) {
			return new int[0][0];
		}
		Range[] arr = new Range[intervals.length];
		for (int i = 0; i < intervals.length; i++) {
			arr[i] = new Range(intervals[i][0], intervals[i][1]);
		}
		Arrays.sort(arr, new RangeComparator());
		ArrayList<Range> ans = new ArrayList<>();
		int s = arr[0].start;
		int e = arr[0].end;
		for (int i = 1; i < arr.length; i++) {
			if (arr[i].start > e) {
				ans.add(new Range(s, e));
				s = arr[i].start;
				e = arr[i].end;
			} else {
				e = Math.max(e, arr[i].end);
			}
		}
		ans.add(new Range(s, e));
		return generateMatrix(ans);
	}

	public static int[][] generateMatrix(ArrayList<Range> list) {
		int[][] matrix = new int[list.size()][2];
		for (int i = 0; i < list.size(); i++) {
			matrix[i] = new int[] { list.get(i).start, list.get(i).end };
		}
		return matrix;
	}


	//bad solutions, don't use
	//this is my own way using priorityqueue, the zuo's solution is simpler just using an array
	public class Line {
		int start;
		int end;
		public Line (int s, int e) {
			this.start = s;
			this.end = e;
		}
	}

	public int[][] merge_j(int[][] intervals) {
		if (intervals == null || intervals.length == 0) {
			return null;
		}
		PriorityQueue<Line> heap = new PriorityQueue<>((l1, l2) -> l1.start - l2.start);
		for (int[] interval : intervals) {
			heap.add(new Line(interval[0], interval[1]));
		}
		List<Line> res = new ArrayList<>();
		while (heap.size() >= 2) {
			Line cur = heap.poll();
			Line next = heap.peek();
			if (cur.end >= next.start) {
				heap.poll();
				if (cur.end <= next.end) {
					heap.add(new Line(cur.start, next.end));
				} else {
					heap.add(cur);
				}
			} else {
				res.add(cur);
			}
		}
		if (!heap.isEmpty()) {
			res.add(heap.poll());
		}
		int[][] arr = new int[res.size()][2];
		for (int i = 0; i < res.size(); i++) {
			arr[i][0] = res.get(i).start;
			arr[i][1] = res.get(i).end;
		}
		return arr;
	}


}
