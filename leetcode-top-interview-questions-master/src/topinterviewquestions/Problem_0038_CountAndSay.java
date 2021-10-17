package topinterviewquestions;

public class Problem_0038_CountAndSay {

	public static String countAndSay(int n) {
		if (n < 1) {
			return "";
		}
		if (n == 1) {
			return "1";
		}
		String last = countAndSay(n - 1);
		StringBuilder ans = new StringBuilder();
		int times = 1;
		for (int i = 1; i < last.length(); i++) {
			if (last.charAt(i-1) == last.charAt(i)) {
				times++;
			} else {
				ans.append(times);
				ans.append(last.charAt(i-1));
				times = 1;
			}
		}
		ans.append(times);
		ans.append(last.charAt(last.length() - 1));
		return ans.toString();
	}

	public static void main(String[] args) {
		System.out.println(countAndSay(5));
	}

}
