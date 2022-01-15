package topinterviewquestions;

import java.util.ArrayList;

public class Problem_0005_LongestPalindromicSubstring {

	//time O(N^2) space O(1)
	public static String longestPalindrome(String s) {
		int start = 0, end = 0, len1 = 0, len2 = 0, len = 0;
		for (int i = 0; i < s.length(); i++) {
			len1 = getLen(s, i, i);
			len2 = getLen(s, i, i+1);
			len = Math.max(len1, len2);
			if (len > end - start) {
				start = i - (len-1)/2;
				end = start + len;
			}
		}
		return s.substring(start, end);
	}

	private static int getLen(String s, int l, int r) {
		int N = s.length();
		while (l >= 0 && r < N && s.charAt(l) == s.charAt(r)) {
			l--;
			r++;
		}
		return r - l - 1;
	}

	public static void main(String[] args) {
		System.out.println(longestPalindrome("cccddccc"));
	}

	//time O(N)，space O(N)
	public static String longestPalindrome_manacher(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		//"12132" -> "#1#2#1#3#2#"
		char[] charArr = manacherString(str);
		int[] pArr = new int[charArr.length];//pArr是代表以charArr[i]为中点的最长回文子序列长度的半径值！！注意不是直径
		int index = -1;//index是表示当前位置的中点，但mid是表示最大位置的中点，只有当目前的位置回文大于之前的最大值时才需要更新mid
		//R代表最右的扩成功的位置。这个coding中R的实际意义：最右的扩成功位置的在下一个位置
		int R = -1;
		int max = Integer.MIN_VALUE;//回文半径
		int mid = 0;
		for (int i = 0; i != charArr.length; i++) {
			//i位置扩出来的答案，i位置扩的区域，至少是多大。
			pArr[i] = R > i ? Math.min(pArr[2 * index - i], R - i) : 1;
			while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
				if (charArr[i + pArr[i]] == charArr[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				index = i;
			}
			if (max < pArr[i]) {
				max = pArr[i];
				mid = i;
			}
		}
		mid = (mid - 1) / 2;//用实际例子来推就行。因为manacher算出来的长度是包括#的，所以减一除二就得到了原始str中点或者upmedian的位置
		max = max - 1;//比如“121” -> "#1#2#1#”， 所以算出来的加了#之后的array的回文半径max=4，而实际的最大回文直径长度是3，所以max=max-1
		return str.substring((max & 1) == 0 ? mid - (max / 2) + 1 : mid - (max / 2), mid + (max / 2) + 1);
	}

	//这个函数的意义是考虑到如果元str是偶数长度，则不能通过轴线两边扩充，所以中间和两边都加#就可以利用#作为中轴考虑到所有情况
	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}



	//预补知识：KMP算法
	//根据两个复杂度的分析，一个是O（M），一个是O（N），M和N分别是两个数组的长度，所以综合时间复杂度是O（N），N是长的数组的长度，也就是s1的长度
	public static int getIndexOf(String s1, String s2) {
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

	//KMP相关题扩展
	//求两个tree是否是子树关系，注意子树的定义是必须要从head开始所有的子树都一样
	//思路是可以把数先序列化（注意空数组一定要用“null”表示出来），可以用先序、中序后序任何一种，然后用KMP判断small的那个tree的子序列是否在big的那个序列里
	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int v) {
			value = v;
		}
	}

	public static boolean containsTree1(Node big, Node small) {
		if (small == null) {
			return true;
		}
		if (big == null) {
			return false;
		}
		if (isSameValueStructure(big, small)) {
			return true;
		}
		return containsTree1(big.left, small) || containsTree1(big.right, small);
	}

	public static boolean isSameValueStructure(Node head1, Node head2) {
		if (head1 == null && head2 != null) {
			return false;
		}
		if (head1 != null && head2 == null) {
			return false;
		}
		if (head1 == null && head2 == null) {
			return true;
		}
		if (head1.value != head2.value) {
			return false;
		}
		return isSameValueStructure(head1.left, head2.left)
				&& isSameValueStructure(head1.right, head2.right);
	}

	public static boolean containsTree2(Node big, Node small) {
		if (small == null) {
			return true;
		}
		if (big == null) {
			return false;
		}
		ArrayList<String> b = preSerial(big);
		ArrayList<String> s = preSerial(small);
		String[] str = new String[b.size()];
		for (int i = 0; i < str.length; i++) {
			str[i] = b.get(i);
		}

		String[] match = new String[s.size()];
		for (int i = 0; i < match.length; i++) {
			match[i] = s.get(i);
		}
		return getIndexOf(str, match) != -1;
	}

	public static ArrayList<String> preSerial(Node head) {
		ArrayList<String> ans = new ArrayList<>();
		pres(head, ans);
		return ans;
	}

	public static void pres(Node head, ArrayList<String> ans) {
		if (head == null) {
			ans.add(null);
		} else {
			ans.add(String.valueOf(head.value));
			pres(head.left, ans);
			pres(head.right, ans);
		}
	}

	public static int getIndexOf(String[] str1, String[] str2) {
		if (str1 == null || str2 == null || str1.length < 1 || str1.length < str2.length) {
			return -1;
		}
		int x = 0;
		int y = 0;
		int[] next = getNextArray(str2);
		while (x < str1.length && y < str2.length) {
			if (isEqual(str1[x], str2[y])) {
				x++;
				y++;
			} else if (next[y] == -1) {
				x++;
			} else {
				y = next[y];
			}
		}
		return y == str2.length ? x - y : -1;
	}

	public static int[] getNextArray(String[] ms) {
		if (ms.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[ms.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int cn = 0;
		while (i < next.length) {
			if (isEqual(ms[i - 1], ms[cn])) {
				next[i++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

	public static boolean isEqual(String a, String b) {
		if (a == null && b == null) {
			return true;
		} else {
			if (a == null || b == null) {
				return false;
			} else {
				return a.equals(b);
			}
		}
	}

	//KMP题目扩展2
	//求一个string是否是另一个的旋转序列
	//思路是把其中的b重复一遍加在后面，然后判断a是否是这个扩展后的string里的子序列
	public static boolean isRotation(String a, String b) {
		if (a == null || b == null || a.length() != b.length()) {
			return false;
		}
		String b2 = b + b;
		return getIndexOf2(b2, a) != -1;
	}

	// KMP Algorithm
	public static int getIndexOf2(String s, String m) {
		if (s.length() < m.length()) {
			return -1;
		}
		char[] ss = s.toCharArray();
		char[] ms = m.toCharArray();
		int si = 0;
		int mi = 0;
		int[] next = getNextArray(ms);
		while (si < ss.length && mi < ms.length) {
			if (ss[si] == ms[mi]) {
				si++;
				mi++;
			} else if (next[mi] == -1) {
				si++;
			} else {
				mi = next[mi];
			}
		}
		return mi == ms.length ? si - mi : -1;
	}



	//manacher算法
	//时间复杂度是O（N）因为：
	// 1. 当i在R外的时候，暴力扩，所以R递增， 最大的位置是str的最右
	// 2. 当i在R内，且通过C中线对称的i‘位置最大回文位置如果在LR内，则直接i位置的最大回文长度等于i’的最大回文长度，时间O（1）
	// 3. 当i在R内且i‘的最大回文区域不在LR范围内，则i的最大回文区域半径一定是i-R位置，时间O（1）
	// 4. 如果i在R内且i‘的最大回文区域正好和L压线，则R就递增，最大的位置是str的最右
	// 所以整个过程最大的时间复杂度就是O（N）， N是str的长度
	public static int manacher1(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		// "12132" -> "#1#2#1#3#2#"
		char[] str = manacherString(s);
		// 回文半径的大小
		int[] pArr = new int[str.length];
		int mid = -1;//回文的中心点位置
		// 讲述中：R代表最右的扩成功的位置
		// coding：最右的扩成功位置的，再下一个位置
		int R = -1;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < str.length; i++) { // 0 1 2
			// R第一个违规的位置，i>= R
			// i位置扩出来的答案，i位置扩的区域，至少是多大。
			// 也就是前面说的2，3，4的情况，
			// 1.如果是2，3，则pArr[i]得到的值就是他的最终值
			// 2. 如果是4， 则先给pArr[i]一个至少的值，然后通过下面的while来扩容，
			pArr[i] = R > i ? Math.min(pArr[2 * mid - i], R - i) : 1;
			// 如果满足i位置在（mid，R）之外两边的值还相当，则扩容，一直扩到不能扩为止
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			//如果前面确实有扩容，说明得更新pArr[i]
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				mid = i;
			}
			max = Math.max(max, pArr[i]);
		}
		return max - 1;
	}

	public static char[] manacherString1(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	//Manacher相关题扩展

	//求一个String在后面如何加一个最短的string从而使得它变成回文序列
	//思路就是用manacher算法求出当R包含住最后一位的时候，
	//pArr[i]的值即为最长回文序列长度，那前面的【0，R-pArr【i】】就是后面需要反序添加的子序列
	public static String shortestEnd(String s) {
		if (s == null || s.length() == 0) {
			return null;
		}
		char[] str = manacherString(s);
		int[] pArr = new int[str.length];
		int C = -1;
		int R = -1;
		int maxContainsEnd = -1;
		for (int i = 0; i != str.length; i++) {
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			if (R == str.length) {
				maxContainsEnd = pArr[i];//此时便可求得回文的长度，减去这个长度就是前面没回文需要加的string
				break;
			}
		}
		//maxContainsEnd-1就是最长回文子序列的长度，注意是没有#的长度
		char[] res = new char[s.length() - maxContainsEnd + 1];
		for (int i = 0; i < res.length; i++) {
			res[res.length - 1 - i] = str[i * 2 + 1];
		}
		return String.valueOf(res);
	}

}
