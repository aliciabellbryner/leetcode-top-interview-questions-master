package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;

public class Problem_0290_WordPattern {
	//https://leetcode.com/problems/word-pattern/discuss/1695928/As-simple-explanation-as-you-want
	//Time Complexity :- BigO(N)
	//Space Complexity :- BigO(N)
	//the concept is very simple. We will first of all gonna check whether the character has not been assigned with any word.
	//If not, then assign it with that word and mark it assigned. So, that if any different character want to assign with same previous word.
	//It will see it as marked and simply return false.
	//Otherwise, return true means every different character has been assigned with different word.
	public boolean wordPattern(String pattern, String s) {
		String words[] = s.split(" ");

		if(words.length != pattern.length()) return false; // base condition, if words length not equals to pattern length
		// eg :- aaa != aa aa aa aa

		Map<Character, String> map1 = new HashMap<>(); // this hashmap assign the characters with words
		Map<String, Boolean> map2 = new HashMap<>(); // in this map we will mark the words as used

		for(int i = 0; i < pattern.length(); i++){
			char ch = pattern.charAt(i);

			if(map1.containsKey(ch) == false){ // if the pattern character has not been mapped
				if(map2.containsKey(words[i]) == true){ // but the word which we will assign to i character has been used
					return false; // eg :- dog = a & now dog = b;
				}
				else{ // if the word has not been used;
					map2.put(words[i], true); // now mark it as used
					map1.put(ch, words[i]); // and put that word infront of character
				}
			}
			else{
				// if that character has already been mapped, now check with whom it has been mapped
				String mwith = map1.get(ch);
				// if mwith word is not equals to words[i], means already mapped with someone, then return false;
				// eg :- a = dog & now a = cat
				if(mwith.equals(words[i]) == false) return false;
			}
		}
		return true;
	}

}
