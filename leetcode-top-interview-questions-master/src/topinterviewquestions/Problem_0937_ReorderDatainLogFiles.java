package topinterviewquestions;

import java.util.Arrays;

public class Problem_0937_ReorderDatainLogFiles {
    class Solution {
        public String[] reorderLogFiles(String[] logs) {
            Arrays.sort(logs, (l1, l2) -> {
                String[] sa1 = l1.split(" ", 2);//limit − the result threshold, which means how many strings to be returned.
                String[] sa2 = l2.split(" ", 2);

                boolean isDigit1 = Character.isDigit(sa1[1].charAt(0));
                boolean isDigit2 = Character.isDigit(sa2[1].charAt(0));

                if(!isDigit1 && !isDigit2) {
                    // both letter-logs.
                    if (sa1[1].compareTo(sa2[1]) == 0) {
                        return sa1[0].compareTo(sa2[0]);
                    } else {
                        return sa1[1].compareTo(sa2[1]);
                    }
                } else if (isDigit1 && isDigit2) {
                    // both digit-logs. So keep them in original order
                    return 0;
                } else if (!isDigit1 && isDigit2) {
                    //first is letter, second is digit. keep them in this order.
                    return -1;
                } else {
                    // first is digit, second is letter. bring letter to forward.
                    return 1;
                }
            });
            return logs;
        }
    }
}
