package topinterviewquestions;

import java.util.*;

/*
Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits. There is no limit on how many times a digit can be reused.

You may assume the given input string is always valid. For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.



Example 1:

Input: time = "19:34"
Output: "19:39"
Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.
It is not 19:33, because this occurs 23 hours and 59 minutes later.
Example 2:

Input: time = "23:59"
Output: "22:22"
Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22.
It may be assumed that the returned time is next day's time since it is smaller than the input time numerically.


Constraints:

time.length == 5
time is a valid time in the form "HH:MM".
0 <= HH < 24
0 <= MM < 60
 */
public class Problem_0681_NextClosestTime {
    //Since there are at max 4 * 4 * 4 * 4 = 256 possible times, just try them all...
    class Solution {
        int diff = Integer.MAX_VALUE;
        String result = "";

        public String nextClosestTime(String time) {
            Set<Integer> set = new HashSet<>();
            set.add(Integer.parseInt(time.substring(0, 1)));
            set.add(Integer.parseInt(time.substring(1, 2)));
            set.add(Integer.parseInt(time.substring(3, 4)));
            set.add(Integer.parseInt(time.substring(4, 5)));

            if (set.size() == 1) return time;

            List<Integer> digits = new ArrayList<>(set);
            int minute = Integer.parseInt(time.substring(0, 2)) * 60 + Integer.parseInt(time.substring(3, 5));

            dfs(digits, "", 0, minute);

            return result;
        }

        private void dfs(List<Integer> digits, String cur, int pos, int target) {
            if (pos == 4) {
                int m = Integer.parseInt(cur.substring(0, 2)) * 60 + Integer.parseInt(cur.substring(2, 4));
                if (m == target) return;
                int d = m - target > 0 ? m - target : 1440 + m - target;
                if (d < diff) {
                    diff = d;
                    result = cur.substring(0, 2) + ":" + cur.substring(2, 4);
                }
                return;
            }

            for (int i = 0; i < digits.size(); i++) {
                if (pos == 0 && digits.get(i) > 2) continue;
                if (pos == 1 && Integer.parseInt(cur) * 10 + digits.get(i) > 23) continue;
                if (pos == 2 && digits.get(i) > 5) continue;
                if (pos == 3 && Integer.parseInt(cur.substring(2)) * 10 + digits.get(i) > 59) continue;
                dfs(digits, cur + digits.get(i), pos + 1, target);
            }
        }
    }

    //DFS + backtracking is probably the most natural way to solve this problem. It's easier if we can follow the template in #46 . Here is my version with comments.
    public class Solution22{
        List<Integer> ans;
        int curTime = 0;

        public String nextClosestTime(String time) {
            //put everything into a list for permuation
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < time.length(); i++) {
                if (time.charAt(i) != ':')
                    list.add(time.charAt(i) - '0');
            }

            curTime = toMinutes(list); //cache the current time (will change after sort)
            //standard dfs that considers duplicate
            Collections.sort(list);
            List<Integer> curList = new ArrayList<>();
            dfs(list, curList);

            //return final result
            StringBuilder sb = new StringBuilder();
            for (Integer x : ans) {
                sb.append(x);
                if (sb.length() == 2) sb.append(":");
            }
            return sb.toString();
        }

        public void dfs(List<Integer> list, List<Integer> curList) {
            if (curList.size() == list.size()) {
                if (isValid(curList)) {
                    //getDiff(curList)!=0 cannot be zero which is just the same time
                    if (ans == null || getDiff(curList) < getDiff(ans) && getDiff(curList) != 0)
                        ans = new ArrayList<>(curList);
                }
                return;
            }

            for (int i = 0; i < list.size(); i++) {
                //avoid duplicate
                if (i > 0 && list.get(i) == list.get(i - 1)) continue;
                curList.add(list.get(i));
                dfs(list, curList);
                curList.remove(curList.size() - 1);
            }
        }

        //convert a time to minutes, which makes comparsion easier
        public int toMinutes(List<Integer> list) {
            int hr = 10 * list.get(0) + list.get(1);
            int mins = 10 * list.get(2) + list.get(3);
            return hr * 60 + mins;
        }

        //get the difference between list and reference time. always return a positive number
        public int getDiff(List<Integer> list) {
            int t1 = curTime;
            int t2 = toMinutes(list);
            return t2 - t1 >= 0 ? t2 - t1 : t2 + 24 * 60 - t1;
        }

        //check if a string is valid, thing like 25:62 is not
        public boolean isValid(List<Integer> list) {
            int hr = 10 * list.get(0) + list.get(1);
            int mins = 10 * list.get(2) + list.get(3);
            return hr >= 0 && hr < 24 && mins >= 0 && mins < 60;
        }
    }

//diss
class Solution5 {


    //diss
    //This approach here is trying to find next digit for each postion in "HH:MM" from right to left. If the next digit is greater than current digit, return directly and keep other digits unchanged.
    //Here is the steps: (e.g. "17:38")
    //
    //Retrieve all four digits from given string and sort them in asscending order, "17:38" -> digits[] {'1', '3', '7', '8'}
    //
    //Apply findNext() from the right most digit to left most digit, try to find next greater digit from digits[] (if exist) which is suitable for current position, otherwise return the minimum digit (digits[0]):
    //
    //"HH:M_": there is no upperLimit for this position (0-9). Just pick the next different digit in the sequence. In the example above, '8' is already the greatest one, so we change it into the smallest one (digits[0] i.e. '1') and move to next step: "17:38" -> "17:31"
    //
    //"HH:_M": the upperLimit is '5' (00~59). The next different digit for '3' is '7', which is greater than '5', so we should omit it and try next. Similarly, '8' is beyond the limit, so we should choose next, i.e. '1': "17:38" -> "17:11"
    //
    //"H_:MM": the upperLimit depends on result[0]. If result[0] == '2', then it should be no more than '3'; else no upperLimit (0-9). Here we have result[0] = '1', so we could choose any digit we want. The next digit for '7' is '8', so we change it and return the result directly. "17:38" -> "18:11"
    //
    //"_H:MM": the upperLimit is '2' (00~23). e.g. "03:33" -> "00:00"
    class Solution2 {

        public String nextClosestTime(String time) {
            char[] result = time.toCharArray();
            char[] digits = new char[] {result[0], result[1], result[3], result[4]};
            Arrays.sort(digits);

            // find next digit for HH:M_
            result[4] = findNext(result[4], (char)('9' + 1), digits);  // no upperLimit for this digit, i.e. 0-9
            if(result[4] > time.charAt(4)) return String.valueOf(result);  // e.g. 23:43 -> 23:44

            // find next digit for HH:_M
            result[3] = findNext(result[3], '5', digits);
            if(result[3] > time.charAt(3)) return String.valueOf(result);  // e.g. 14:29 -> 14:41

            // find next digit for H_:MM
            result[1] = result[0] == '2' ? findNext(result[1], '3', digits) : findNext(result[1], (char)('9' + 1), digits);
            if(result[1] > time.charAt(1)) return String.valueOf(result);  // e.g. 02:37 -> 03:00

            // find next digit for _H:MM
            result[0] = findNext(result[0], '2', digits);
            return String.valueOf(result);  // e.g. 19:59 -> 11:11
        }

        /**
         * find the next bigger digit which is no more than upperLimit.
         * If no such digit exists in digits[], return the minimum one i.e. digits[0]
         * @param current the current digit
         * @param upperLimit the maximum possible value for current digit
         * @param //digits[] the sorted digits array
         * @return
         */
        private char findNext(char current, char upperLimit, char[] digits) {
            //System.out.println(current);
            if(current == upperLimit) {
                return digits[0];
            }
            int pos = Arrays.binarySearch(digits, current) + 1;
            while(pos < 4 && (digits[pos] > upperLimit || digits[pos] == current)) { // traverse one by one to find next greater digit
                pos++;
            }
            return pos == 4 ? digits[0] : digits[pos];
        }

    }



    //Similar idea but with a for-loop that moves right to left.
    public String nextClosestTime2(String time) {
        StringBuilder t = new StringBuilder(time.replace(":",""));
        // Get unique digits
        Set<Character> set = new HashSet<>();
        for(int i=0; i<4; i++) {
            set.add(t.charAt(i));
        }
        // Sort them
        List<Character> digits = new ArrayList<>(set);
        Collections.sort(digits);
        // Create digit to index map for O(1) lookup
        Map<Character,Integer> indexMap = new HashMap<>();
        for(int i=0; i<digits.size(); i++) {
            indexMap.put(digits.get(i),i);
        }
        // For each digit, right to left
        for(int digit=3; digit>=0; digit--) {
            char val = t.charAt(digit);
            int index = indexMap.get(val);
            // If there is a larger value available, try it
            if(index < digits.size()-1) {
                char tmp = t.charAt(digit);
                t.setCharAt(digit,digits.get(++index));
                if(isValidTime(t))
                    break;
                t.setCharAt(digit,tmp);
            }
            // Otherwise, replace with smallest value and move to next digit
            t.setCharAt(digit,digits.get(0));
        }
        t.insert(2,':');
        return t.toString();
    }

    private boolean isValidTime(StringBuilder time) {
        return ((time.charAt(0) <= '2' && time.charAt(1) <= '4') || (time.charAt(0) <= '1' && time.charAt(1) <= '9')) && time.charAt(2) <= '5';
    }
}

//A better way would be to find the highest for each of the digits from right to left and check if it is valid, rather than having multiple if's to fit the solution.
class Solution3 {
    public String nextClosestTime(String time) {
        int[] digits = getDigits(time);
        int length = digits.length;
        int[] sorted = Arrays.copyOf(digits, length);
        Arrays.sort(sorted);
        int next = 0;

        for(int i = length - 1; i >= 0; --i) {
            next = nextHighest(sorted, digits[i]);

            if(next > digits[i]) {
                digits[i] = next;
                if(isValid(digits)) {
                    break;
                } else {
                    digits[i] = sorted[0];
                }
            } else {
                digits[i] = sorted[0];
            }
        }

        return String.format("%02d:%02d", digits[0] * 10 + digits[1], digits[2] * 10 + digits[3]);
    }

    public int[] getDigits(String time) {
        String[] parts = time.split(":");
        int[] digits = new int[4];
        int index = 0;
        int cur = 0;

        for(String s : parts) {
            cur = Integer.parseInt(s);
            digits[index++] = cur / 10;
            digits[index++] = cur % 10;
        }

        return digits;
    }

    public int nextHighest(int[] sorted, int cur) {
        for(int num : sorted) {
            if(num > cur) {
                return num;
            }
        }

        return cur;
    }

    public boolean isValid(int[] digits) {
        if(digits[0] * 10 + digits[1] < 24 && digits[2] * 10 + digits[3] < 60) {
            return true;
        }

        return false;
    }
}

}
