package topinterviewquestions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Problem_0465_OptimalAccountBalancing_G {

    //https://leetcode.ca/2017-03-09-465-Optimal-Account-Balancing/
    //First loop over transactions and obtain each person’s remaining amount of money (either 0, positive or negative). Then do backtrack.
    //Starting from index 0 and transaction count 0, loop over each remaining amount after the start index.
    // If a next amount has a different sign with the amount at the start index, then add the amount of the start index by the current amount
    // with the number of transactions increased by 1, and do backtrack using the current index as the start index. If the end is reached,
    // update the minimum number of transactions. Finally, return the minimum number of transactions.
    class Solution {
        int minTransactions = Integer.MAX_VALUE;

        public int minTransfers(int[][] transactions) {
            Map<Integer, Integer> map = new HashMap<Integer, Integer>();
            for (int[] transaction : transactions) {
                int person1 = transaction[0], person2 = transaction[1], amount = transaction[2];
                int value1 = map.getOrDefault(person1, 0) - amount;
                int value2 = map.getOrDefault(person2, 0) + amount;
                map.put(person1, value1);
                map.put(person2, value2);
            }
            int groupSize = map.size();
            int[] balances = new int[groupSize];
            Set<Integer> keySet = map.keySet();
            int index = 0;
            for (int key : keySet) {
                int balance = map.get(key);
                balances[index++] = balance;
            }
            backtrack(balances, 0, 0);
            return minTransactions;
        }

        public void backtrack(int[] balances, int start, int count) {
            int length = balances.length;
            while (start < length && balances[start] == 0)
                start++;
            if (start == length)
                minTransactions = Math.min(minTransactions, count);
            else {
                boolean positive = balances[start] > 0;
                for (int i = start + 1; i < length; i++) {
                    if ((balances[i] > 0) ^ positive) {
                        balances[i] += balances[start];
                        backtrack(balances, start + 1, count + 1);
                        balances[i] -= balances[start];
                    }
                }
            }
        }
    }


    //https://cheonhyangzhang.gitbooks.io/leetcode-solutions/content/solutions-451-500/465-optimal-account-balancing.html
    public class Solution2 {
        public int minTransfers(int[][] transactions) {
            int[][] trans = transactions;
            HashMap<Integer, Integer> debts = new HashMap<Integer, Integer>();
            for (int i = 0; i < trans.length; i ++) {
                int give = trans[i][0];
                int get = trans[i][1];
                int money = trans[i][2];
                if (!debts.containsKey(give)) {
                    debts.put(give, 0);
                }
                if (!debts.containsKey(get)) {
                    debts.put(get, 0);
                }
                debts.put(give, debts.get(give) + money);
                debts.put(get, debts.get(get) - money);
            }
            int[] debtsArr = new int[debts.size()];
            int index = 0;
            for (Integer no:debts.keySet()) {
                debtsArr[index ++] = debts.get(no);
            }
            return process(debtsArr, 0);
        }
        private int process(int[] debts, int start) {
            int min = Integer.MAX_VALUE;
            while (start < debts.length && debts[start] == 0) {
                start ++;
            }
            for (int i = start + 1; i < debts.length; i ++) {
                if (debts[i] == 0) {
                    continue;
                }
                if ((debts[start] > 0 && debts[i] < 0) || (debts[start] < 0 && debts[i] > 0)) {
                    debts[i] += debts[start];
                    min = Math.min(min, process(debts, start + 1) + 1);
                    debts[i] -= debts[start];
                }
            }
            if (min != Integer.MAX_VALUE) {
                return min;
            }
            return 0;
        }
    }
}
