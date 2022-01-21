package topinterviewquestions;

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
