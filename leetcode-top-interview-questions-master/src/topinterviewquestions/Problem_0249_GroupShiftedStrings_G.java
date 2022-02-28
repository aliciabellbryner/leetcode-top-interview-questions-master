package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
We can shift a string by shifting each of its letters to its successive letter.

For example, "abc" can be shifted to be "bcd".
We can keep shifting the string to form a sequence.

For example, we can keep shifting "abc" to form the sequence: "abc" -> "bcd" -> ... -> "xyz".
Given an array of strings strings, group all strings[i] that belong to the same shifting sequence. You may return the answer in any order.



Example 1:

Input: strings = ["abc","bcd","acef","xyz","az","ba","a","z"]
Output: [["acef"],["a","z"],["abc","bcd","xyz"],["az","ba"]]
Example 2:

Input: strings = ["a"]
Output: [["a"]]


Constraints:

1 <= strings.length <= 200
1 <= strings[i].length <= 50
strings[i] consists of lowercase English letters.
 */
public class Problem_0249_GroupShiftedStrings_G {

    //https://tenderleo.gitbooks.io/leetcode-solutions-/content/GoogleEasy/249.html?q=
    public class Solution {
        public List<List<String>> groupStrings(String[] strings) {
            Map<String,   List<String>> map = new HashMap<>();

            for(String s : strings){
                String hash = getHash(s);
                if(map.containsKey(hash)){
                    map.get(hash).add(s);
                }else{
                    List<String> l = new ArrayList<>();
                    l.add(s);
                    map.put(hash, l);
                }
            }

            return new ArrayList<List<String>>(map.values());
        }

        String getHash(String s){
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< s.length(); i++){
                sb.append((s.charAt(i) - s.charAt(0) + 26) % 26);
                sb.append('.');// to make sure there is no overlap.
            }

            return sb.toString();
        }
    }

    /*
    Overview
Given some strings, we need to group the ones that are in the same shifting sequence. Two Strings are said to be in the same shifting sequence if one can be converted to the other by shifting every character of the first string to its successive or preceding character a fixed number of times.

We need a way to represent these strings such that the ones belonging to the same shifting sequence have the same value. This need can be done by using Hashing in a unique manner. Typically when we design a hash function we do it in such a way that all the possible keys get mapped to a unique value. There should not be any many-to-one mappings i.e., hash collisions. However, in this case, by carefully selecting a hash function, we can use the hash collisions to our advantage.

We need to design a hash function that ensures that the hash values for strings in the same shifting sequence will collide and thus the strings will be grouped together. There can be any number of such hash functions and we can use any of them. We will discuss two such hash functions in this article.

Approach 1: Hashing
Intuition

All the strings in the below table belong to the same shifting sequence. We want to represent each one of these by a single string. We can represent each of these strings by abc as shown in the below table.

fig

To make every string the same, the first character in all the strings will also have to be the same. Hence, we will first convert the first character of all the strings to any character, let's say a. To convert the first character to a we would require some number of shifting say shift (shift \geq≥ 0), and we need to shift the other characters of string by the same value shift.

In this way, the strings in the same shifting sequence will end up having the same value.

Algorithm

Iterate over strings, and for each string:

a. Find its Hash value, that is, the string starts with an a after some shifts. The value of shift is equal to the first character of the string. Then shift all the characters by the same value shift. Notice that we also have to do a mod of 26 on the resulting character for the circular shift.

b. Map the original string to the above Hash value in the map mapHashToList. More specifically, add the original string to the list corresponding to its Hash value.

Iterate over the mapHashToList and store the list for every key in the map in the answer array groups.

Implementation

Note: Modulo operator % behaves differently in C++/Java and Python. The difference lies in the operator implementation.

C++/Java: a % b = a - int(a / b) * b
Python: a % b = a - floor(a / b) * b
In C++/Java expression like (a - b) % c can be negative if a < b hence we need to manually add c to make it non-negative (a - b + c) % c. On the other hand, Python automatically performs this step. This is why we add 26 before taking the modulus in Java and C++ but we do not need to do so in the Python implementation.

 class Solution {
    char shiftLetter(char letter, int shift) {
        return (char) ((letter - shift + 26) % 26 + 'a');
    }

    // Create a hash value
    String getHash(String s) {
        char[] chars = s.toCharArray();

        // Calculate the number of shifts to make the first character to be 'a'
        int shift = chars[0];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = shiftLetter(chars[i], shift);
        }

        String hashKey = String.valueOf(chars);
        return hashKey;
    }

    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> mapHashToList = new HashMap<>();

        // Create a hash_value (hashKey) for each string and append the string
        // to the list of hash values i.e. mapHashToList["abc"] = ["abc", "bcd"]
        for (String str : strings) {
            String hashKey = getHash(str);
            if (mapHashToList.get(hashKey) == null) {
                mapHashToList.put(hashKey, new ArrayList<>());
            }
            mapHashToList.get(hashKey).add(str);
        }

        // Iterate over the map, and add the values to groups
        List<List<String>> groups = new ArrayList<>();
        for (List<String> group : mapHashToList.values()) {
            groups.add(group);
        }

        return groups;
    }
}

Note: Any hash function that results in collisions for strings with a common shifting sequence will group the strings as required. Let's consider another possible way to implement the getHash() function: we can map each string to the collection of differences in ASCII code values between each of its consecutive characters. This works because strings in the same shifting sequence will have the same collection of differences between all the consecutive letters. For example, every string in the group ["acf","gil","xzc"] can be represented as 2,3 because c - a = i - g = z - x = 2 and f - c = l - i = c - z = 3. Here the last expression c - z is -23, however, we are doing a circular shift, so we will do a modulo operation on the result to make it 3. As in the previous implementation, we will use a string for the hash key. So let's shift the values back to the alphabetical range so that our list of differences 2,3 becomes "cd". Let's take a look at the code for this hashing method:

class Solution {
    // Create a hash value
    String getHash(String s) {
        char[] chars = s.toCharArray();
        StringBuilder hashKey = new StringBuilder();

        for (int i = 1; i < chars.length; i++) {
            hashKey.append((char) ((chars[i] - chars[i - 1] + 26) % 26 + 'a'));
        }

        return hashKey.toString();
    }

    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> mapHashToList = new HashMap<>();

        // Create a hash_value (hashKey) for each string and append the string
        // to the list of hash values i.e. mapHashToList["cd"] = ["acf", "gil", "xzc"]
        for (String str : strings ) {
            String hashKey = getHash(str);
            if (mapHashToList.get(hashKey) == null) {
                mapHashToList.put(hashKey, new ArrayList<>());
            }
            mapHashToList.get(hashKey).add(str);
        }

        // Iterate over the map, and add the values to groups
        List<List<String>> groups = new ArrayList<>();
        for (List<String> group : mapHashToList.values()) {
            groups.add(group);
        }

        // Return a list of all of the grouped strings
        return groups;
    }
}


Complexity Analysis

Let NN be the length of strings and KK be the maximum length of a string in strings.

Time complexity: O(N * K)O(N∗K)

We iterate over all NN strings and for each string, we iterate over all the characters to generate the Hash value, which takes O(K)O(K) time. To sum up, the overall time complexity is O(N * K)O(N∗K).

Space complexity: O(N * K)O(N∗K)

We need to store all the strings plus their Hash values in mapHashToList. In the worst scenario, when each string in the given list belongs to a different Hash value, the maximum number of strings stored in mapHashToList is 2 * N2∗N. Each string takes at most O(K)O(K) space. Hence the overall space complexity is O(N * K)O(N∗K).

Note: The time and space complexity for both solutions are same because the getHash() function has the same time and space complexity, O(K)O(K).
     */


}
