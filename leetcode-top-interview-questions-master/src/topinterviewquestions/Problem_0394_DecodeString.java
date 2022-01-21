package topinterviewquestions;

public class Problem_0394_DecodeString {

	public static String decodeString(String s) {
		char[] str = s.toCharArray();
		return process(str, 0).ans;
	}

	public static class Info {
		public String ans;//表示他的decoded的string
		public int end;//表示它的decoded string终点在哪

		public Info(String a, int e) {
			ans = a;
			end = e;
		}
	}

	// s[i....]  何时停？遇到   ']'  或者遇到 s的终止位置，停止
	// 返回Info
	//Time Complexity: O(maxK⋅n) maxK is the maximum value of times
	//Space Complexity: O(n). n is the length of the string s
	// This is the space used to store the internal call stack used for recursion.
	// As we are recursively decoding each nested pattern,
	// the maximum depth of recursive call stack would not be more than nn
	public static Info process(char[] s, int i) {
		StringBuilder res = new StringBuilder();
		int times = 0;
		while (i < s.length && s[i] != ']') {
			if ((s[i] >= 'a' && s[i] <= 'z') || (s[i] >= 'A' && s[i] <= 'Z')) {
				res.append(s[i++]);
			} else if (s[i] >= '0' && s[i] <= '9') {
				times = times * 10 + s[i++] - '0';
			} else { // str[index] = '['
				Info next = process(s, i + 1);
				res.append(timesString(times, next.ans));
				times = 0;//恢复times
				i = next.end + 1;//从]的下一个index重新开始
			}
		}
		return new Info(res.toString(), i);
	}

	public static String timesString(int times, String str) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < times; i++) {
			res.append(str);
		}
		return res.toString();
	}

}
