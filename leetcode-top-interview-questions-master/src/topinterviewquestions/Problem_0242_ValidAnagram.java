package topinterviewquestions;

public class Problem_0242_ValidAnagram {

	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length()) {
			return false;
		}
		int[] count = new int[256];
		for (char cha : s.toCharArray()) {
			count[cha]++;
		}
		for (char cha : t.toCharArray()) {
			if (--count[cha] < 0) {
				return false;
			}
		}
		return true;
	}

}
