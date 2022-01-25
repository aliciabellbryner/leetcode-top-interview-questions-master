package topinterviewquestions;

import java.util.HashMap;

//https://leetcode.com/problems/logger-rate-limiter/solution/
//Approach 2: Hashtable / Dictionary
//Time Complexity: O(1). The lookup and update of the hashtable takes a constant time.
//Space Complexity: O(M) where MM is the size of all incoming messages. Over the time,
// the hashtable would have an entry for each unique message that has appeared.
public class Problem_0359_LoggerRateLimiter_G {
    class Logger {
        private HashMap<String, Integer> map;

        /** Initialize your data structure here. */
        public Logger() {
            map = new HashMap<String, Integer>();
        }

        /**
         * Returns true if the message should be printed in the given timestamp, otherwise returns false.
         */
        public boolean shouldPrintMessage(int timestamp, String message) {

            if (!this.map.containsKey(message)) {
                this.map.put(message, timestamp);
                return true;
            }

            Integer oldTimestamp = this.map.get(message);
            if (timestamp - oldTimestamp >= 10) {
                this.map.put(message, timestamp);
                return true;
            } else
                return false;
        }
    }
}
