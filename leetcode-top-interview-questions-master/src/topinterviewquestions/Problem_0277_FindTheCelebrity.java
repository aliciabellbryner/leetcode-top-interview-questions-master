package topinterviewquestions;
/*
Suppose you are at a party with n people labeled from 0 to n - 1 and among them, there may exist one celebrity. The definition of a celebrity is that all the other n - 1 people know the celebrity, but the celebrity does not know any of them.

Now you want to find out who the celebrity is or verify that there is not one. The only thing you are allowed to do is ask questions like: "Hi, A. Do you know B?" to get information about whether A knows B. You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).

You are given a helper function bool knows(a, b) that tells you whether A knows B. Implement a function int findCelebrity(n). There will be exactly one celebrity if they are at the party.

Return the celebrity's label if there is a celebrity at the party. If there is no celebrity, return -1.



Example 1:


Input: graph = [[1,1,0],[0,1,0],[1,1,1]]
Output: 1
Explanation: There are three persons labeled with 0, 1 and 2. graph[i][j] = 1 means person i knows person j, otherwise graph[i][j] = 0 means person i does not know person j. The celebrity is the person labeled as 1 because both 0 and 2 know him but 1 does not know anybody.
Example 2:


Input: graph = [[1,0,1],[1,1,0],[0,1,1]]
Output: -1
Explanation: There is no celebrity.


Constraints:

n == graph.length
n == graph[i].length
2 <= n <= 100
graph[i][j] is 0 or 1.
graph[i][i] == 1


Follow up: If the maximum number of allowed calls to the API knows is 3 * n, could you find a solution without exceeding the maximum number of calls?
 */
public class Problem_0277_FindTheCelebrity {

	// 提交时不要提交这个函数，只提交下面的方法
	public static boolean knows(int x, int i) {
		return true;
	}

	//time O（N）
	//space O（1）
	//In detail, suppose the candidate after the first for loop is person k,
	// it means 0 to k-1 cannot be the celebrity, because either they know any candidate or they are not known by 0 to k-1.
	// Also, since k knows no one between k+1 and n-1, k+1 to n-1 can not be the celebrity either.
	// Therefore, k is the only possible celebrity, if there exists one.
	//The remaining job is to check if k indeed does not know any other persons and all other persons know k.
	public int findCelebrity(int n) {
		int res = 0;
		for (int i = 0; i < n; ++i) {//find the candidate, he is the only possible celebrity, if there exists one.
			if (knows(res, i)) {
				res = i;
			}
		}
		for (int i = 0; i < res; ++i) {//verify step1, to make sure the cand knows no one in [0, res),
			// 不需要验证后面的因为如果cand认识后面的前面的loop肯定会找到比cand后面的值
			if (knows(res, i)) {
				return -1;
			}
		}
		for (int i = 0; i < n; ++i) {//verify step2，to make sure everyone knows cand
			if (!knows(i, res)) {
				return -1;
			}
		}
		return res;
	}

}
