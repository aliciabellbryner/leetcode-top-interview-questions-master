package topinterviewquestions;

import java.util.LinkedList;
/*
There are n gas stations along a circular route, where the amount of gas at the ith station is gas[i].

You have a car with an unlimited gas tank and it costs cost[i] of gas to travel from the ith station to its next (i + 1)th station. You begin the journey with an empty tank at one of the gas stations.

Given two integer arrays gas and cost, return the starting gas station's index if you can travel around the circuit once in the clockwise direction, otherwise return -1. If there exists a solution, it is guaranteed to be unique

Example 1:

Input: gas = [1,2,3,4,5], cost = [3,4,5,1,2]
Output: 3
Explanation:
Start at station 3 (index 3) and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 4. Your tank = 4 - 1 + 5 = 8
Travel to station 0. Your tank = 8 - 2 + 1 = 7
Travel to station 1. Your tank = 7 - 3 + 2 = 6
Travel to station 2. Your tank = 6 - 4 + 3 = 5
Travel to station 3. The cost is 5. Your gas is just enough to travel back to station 3.
Therefore, return 3 as the starting index.
Example 2:

Input: gas = [2,3,4], cost = [3,4,3]
Output: -1
Explanation:
You can't start at station 0 or 1, as there is not enough gas to travel to the next station.
Let's start at station 2 and fill up with 4 unit of gas. Your tank = 0 + 4 = 4
Travel to station 0. Your tank = 4 - 3 + 2 = 3
Travel to station 1. Your tank = 3 - 3 + 3 = 3
You cannot travel back to station 2, as it requires 4 unit of gas but you only have 3.
Therefore, you can't travel around the circuit once no matter where you start.


Constraints:

gas.length == n
cost.length == n
1 <= n <= 105
0 <= gas[i], cost[i] <= 104
 */
public class Problem_0134_GasStation {

	//from discussion: easy solution:
	public int canCompleteCircuit(int[] gas, int[] cost) {
		int tank = 0;
		for(int i = 0; i < gas.length; i++)
			tank += gas[i] - cost[i];
		if(tank < 0) {//先把所有加起来，如果小于0那肯定不能往返，返回-1
			return -1;
		}
		int start = 0;
		int accumulate = 0;
		//we don't need to go from the tail to head again once we find the start, as per the problem description, there will be only one answer
		//first thing we can be sure is that the answer cannot be in [0, start-1], then if the answer exist in [start+1, gas.length-1],
		// then there does exists some place j that also can make the total gain > 0  from j to gas.length-1,
		// then it is definitely sure there will be two answer as the gain from start to j is also >= 0,
		// which contradict the only one answer statement, so we can know for sure the start is the answer
		for(int i = 0; i < gas.length; i++){
			int curGain = gas[i] - cost[i];
			if(accumulate + curGain < 0){//means cur job failed. you have to start from i+1
				start = i + 1;
				accumulate = 0;
			} else {
				accumulate += curGain;
			}
		}
		return start;
	}

	//from discussion: easy solution:
	public int canCompleteCircuit_2(String[] strArr) {
		int tank = 0;
		for(int i = 1; i < strArr.length; i++) {
			int gas = Integer.valueOf(strArr[i].split(":")[0]);
			int cost = Integer.valueOf(strArr[i].split(":")[1]);
			tank += gas - cost;
		}
		if(tank < 0) {//先把所有加起来，如果小于0那肯定不能往返，返回-1
			return -1;
		}
		int start = 0;
		int accumulate = 0;
		//we don't need to go from the tail to head again once we find the start, as per the problem description, there will be only one answer
		//first thing we can be sure is that the answer cannot be in [0, start-1], then if the answer exist in [start+1, gas.length-1],
		// then there does exists some place j that also can make the total gain > 0  from j to gas.length-1,
		// then it is definitely sure there will be two answer as the gain from start to j is also >= 0,
		// which contradict the only one answer statement, so we can know for sure the start is the answer
		for(int i = 1; i < strArr.length; i++){
			int curGain = Integer.valueOf(strArr[i].split(":")[0]) - Integer.valueOf(strArr[i].split(":")[1]);
			if(accumulate + curGain < 0){//means cur job failed. you have to start from i+1
				start = i + 1;
				accumulate = 0;
			} else {
				accumulate += curGain;
			}
		}
		return start - 1;
	}


	public static int canCompleteCircuit1(int[] gas, int[] cost) {
		boolean[] good = goodArray(gas, cost);
		for (int i = 0; i < gas.length; i++) {
			if (good[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean[] goodArray(int[] g, int[] c) {
		int N = g.length;
		int M = N << 1;
		int[] arr = new int[M];
		for (int i = 0; i < N; i++) {
			arr[i] = g[i] - c[i];
			arr[i + N] = g[i] - c[i];
		}
		for (int i = 1; i < M; i++) {
			arr[i] += arr[i - 1];
		}
		LinkedList<Integer> w = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			while (!w.isEmpty() && arr[w.peekLast()] >= arr[i]) {
				w.pollLast();
			}
			w.addLast(i);
		}
		boolean[] ans = new boolean[N];
		for (int offset = 0, i = 0, j = N; j < M; offset = arr[i++], j++) {
			if (arr[w.peekFirst()] - offset >= 0) {
				ans[i] = true;
			}
			if (w.peekFirst() == i) {
				w.pollFirst();
			}
			while (!w.isEmpty() && arr[w.peekLast()] >= arr[j]) {
				w.pollLast();
			}
			w.addLast(j);
		}
		return ans;
	}



	// 这个方法的时间复杂度O(N)，额外空间复杂度O(1) 训练营讲了
	//// 本实现比leetcode要求的要难
	//// 返回了所有节点是不是良好出发点
	public static int canCompleteCircuit2(int[] gas, int[] cost) {
		if (gas == null || gas.length == 0) {
			return -1;
		}
		if (gas.length == 1) {
			return gas[0] < cost[0] ? -1 : 0;
		}
		boolean[] good = stations(cost, gas);
		for (int i = 0; i < gas.length; i++) {
			if (good[i]) {
				return i;
			}
		}
		return -1;
	}

	public static boolean[] stations(int[] cost, int[] gas) {
		if (cost == null || gas == null || cost.length < 2 || cost.length != gas.length) {
			return null;
		}
		int init = changeDisArrayGetInit(cost, gas);
		return init == -1 ? new boolean[cost.length] : enlargeArea(cost, init);
	}

	public static int changeDisArrayGetInit(int[] dis, int[] oil) {
		int init = -1;
		for (int i = 0; i < dis.length; i++) {
			dis[i] = oil[i] - dis[i];
			if (dis[i] >= 0) {
				init = i;
			}
		}
		return init;
	}

	public static boolean[] enlargeArea(int[] dis, int init) {
		boolean[] res = new boolean[dis.length];
		int start = init;
		int end = nextIndex(init, dis.length);
		int need = 0;
		int rest = 0;
		do {
			// 当前来到的start已经在连通区域中，可以确定后续的开始点一定无法转完一圈
			if (start != init && start == lastIndex(end, dis.length)) {
				break;
			}
			// 当前来到的start不在连通区域中，就扩充连通区域
			if (dis[start] < need) { // 当前start无法接到连通区的头部
				need -= dis[start];
			} else { // 当前start可以接到连通区的头部，开始扩充连通区域的尾巴
				rest += dis[start] - need;
				need = 0;
				while (rest >= 0 && end != start) {
					rest += dis[end];
					end = nextIndex(end, dis.length);
				}
				// 如果连通区域已经覆盖整个环，当前的start是良好出发点，进入2阶段
				if (rest >= 0) {
					res[start] = true;
					connectGood(dis, lastIndex(start, dis.length), init, res);
					break;
				}
			}
			start = lastIndex(start, dis.length);
		} while (start != init);
		return res;
	}

	// 已知start的next方向上有一个良好出发点
	// start如果可以达到这个良好出发点，那么从start出发一定可以转一圈
	public static void connectGood(int[] dis, int start, int init, boolean[] res) {
		int need = 0;
		while (start != init) {
			if (dis[start] < need) {
				need -= dis[start];
			} else {
				res[start] = true;
				need = 0;
			}
			start = lastIndex(start, dis.length);
		}
	}

	public static int lastIndex(int index, int size) {
		return index == 0 ? (size - 1) : index - 1;
	}

	public static int nextIndex(int index, int size) {
		return index == size - 1 ? 0 : (index + 1);
	}

}
