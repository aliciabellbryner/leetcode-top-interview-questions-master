package topinterviewquestions;

import java.util.Arrays;

public class Problem_0028_ImplementStrStr {

	//time O(mn): m is the length of s, n is the length of m, space O(1)
	public static int getIndexOf(String haystack, String needle) {
		for (int i = 0; ; i++) {
			for (int j = 0; ; j++) {
				if (j == needle.length()) {
					return i;
				}
				if (i + j == haystack.length()) {
					return -1;
				}
				if (needle.charAt(j) != haystack.charAt(i + j)) {
					break;
				}
			}
		}
	}



	public static int strStr(String haystack, String needle) {
		return getIndexOf_KMP(haystack, needle);
	}


	//预补知识：KMP算法
	//根据两个复杂度的分析，一个是O（M），一个是O（N），M和N分别是两个数组的长度，所以综合时间复杂度是O（N），N是长的数组的长度，也就是s1的长度, space O(
	public static int getIndexOf_KMP(String s1, String s2) {
		if (s1 == null || s2 == null || s2.length() < 1 || s1.length() < s2.length()) {
			return -1;
		}
		char[] str1 = s1.toCharArray();
		char[] str2 = s2.toCharArray();
		int x = 0;
		int y = 0;
		// O(M) m <= n
		int[] next = getNextArray(str2);
		// O(N) 可以分析x和x-y的变化趋势，
		//step1: x变大，x-y不变
		//step2: x变大，x-y变大
		//step3: x不变，x-y变大
		//而x是从0->N的变化范围，x-y也一样，所以他们的最差时间复杂度是O（2N）也就是O（N）
		while (x < str1.length && y < str2.length) {
			if (str1[x] == str2[y]) {//step1
				x++;
				y++;
			} else if (next[y] == -1) { // step2 y == 0
				x++;
			} else {//step3
				y = next[y];
			}
		}
		return y == str2.length ? x - y : -1;
	}

	//this next array means when you are in i position, the maximum length of array that is
	// in [0, i-1] that the front part equals to the end part, but the two parts cannot override
	public static int[] getNextArray(char[] str2) {
		if (str2.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[str2.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2; // 目前在哪个位置上求next数组的值
		int cn = 0; // 当前是哪个位置的值再和i-1位置的字符比较
		while (i < next.length) {
			if (str2[i - 1] == str2[cn]) { //step1 配成功的时候
				next[i++] = ++cn;
			} else if (cn > 0) {//step2
				cn = next[cn];
			} else {//step3
				next[i++] = 0;
			}
		}
		return next;
	}

	public static void main(String[] args) {
		String h = "hello";
		String n = "llellll";
		System.out.println(Arrays.toString(getNextArray(n.toCharArray())));
	}
}
