package topinterviewquestions;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
/*
Given an array of meeting time intervals intervals where intervals[i] = [starti, endi], return the minimum number of conference rooms required.

Example 1:

Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2
Example 2:

Input: intervals = [[7,10],[2,4]]
Output: 1


Constraints:

1 <= intervals.length <= 104
0 <= starti < endi <= 106
 */
public class Problem_0253_MeetingRoomsII {

	//Time Complexity: O(NlogN) because of the sorting
	//space O(N) as the minHeap
	public static int minMeetingRooms(int[][] m) {
		Line[] lines = new Line[m.length];
		for (int i = 0; i < m.length; i++) {
			lines[i] = new Line(m[i][0], m[i][1]);
		}
		Arrays.sort(lines, (o1, o2) -> o1.start - o2.start);//minheap, 这里一定要用start升序排列
		PriorityQueue<Line> heap = new PriorityQueue<>((o1, o2) -> o1.end - o2.end);//这里一定要用end升序排列，所以得到的heap的minheap
		int max = 0;
		for (int i = 0; i < lines.length; i++) {
			while (!heap.isEmpty() && heap.peek().end <= lines[i].start) {
				//前面的结束时间早于目前会议的开始时间，就把前面的从stack弹出
				heap.poll();
			}
			heap.add(lines[i]);
			max = Math.max(max, heap.size());
		}
		return max;
	}

	public static class Line {
		public int start;
		public int end;

		public Line(int s, int e) {
			start = s;
			end = e;
		}
	}
//
//	public static class StartComparator implements Comparator<Line> {
//
//		@Override
//		public int compare(Line o1, Line o2) {
//			return o1.start - o2.start;
//		}
//
//	}
//
//	public static class EndComparator implements Comparator<Line> {
//
//		@Override
//		public int compare(Line o1, Line o2) {
//			return o1.end - o2.end;
//		}
//
//	}

}
