package topinterviewquestions;

public class Problem_0387_FirstUniqueCharacterInString {

	public int firstUniqChar(String s) {
		int N = s.length();
		int count[] = new int[26];
		for (int i = 0; i < N; i++) {
			count[s.charAt(i) - 'a']++;
		}
		for (int i = 0; i < N; i++) {
			if (count[s.charAt(i) - 'a'] == 1) {
				return i;
			}
		}
		return -1;
	}

}
