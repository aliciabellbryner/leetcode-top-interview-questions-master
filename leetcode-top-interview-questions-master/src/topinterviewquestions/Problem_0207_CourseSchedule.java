package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Problem_0207_CourseSchedule {

	// 一个info，就是一个课程
	// name是课程的编号
	// in是课程的入度
	public static class Info {
		public int name;
		public int in;
		public ArrayList<Info> nexts;

		public Info(int n) {
			name = n;
			in = 0;
			nexts = new ArrayList<>();
		}
	}

	//use topology sort
	public static boolean canFinish(int numCourses, int[][] prerequisites) {
		if (prerequisites == null || prerequisites.length == 0) {
			return true;
		}
		HashMap<Integer, Info> nodes = new HashMap<>();
		for (int[] arr : prerequisites) {
			int to = arr[0];
			int from = arr[1];
			if (!nodes.containsKey(to)) {
				nodes.put(to, new Info(to));
			}
			if (!nodes.containsKey(from)) {
				nodes.put(from, new Info(from));
			}
			Info t = nodes.get(to);
			Info f = nodes.get(from);
			f.nexts.add(t);
			t.in++;
		}
		int needPrerequisiteNums = nodes.size();
		Queue<Info> zeroInQueue = new LinkedList<>();
		for (Info info : nodes.values()) {
			if (info.in == 0) {
				zeroInQueue.add(info);
			}
		}
		int count = 0;
		while (!zeroInQueue.isEmpty()) {
			Info cur = zeroInQueue.poll();
			count++;
			for (Info next : cur.nexts) {
				if (--next.in == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return count == needPrerequisiteNums;
	}

}
