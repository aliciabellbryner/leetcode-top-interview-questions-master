package topinterviewquestions;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class Problem_0846_HandofStraights_G {

    //https://leetcode.com/problems/hand-of-straights/discuss/135598/C%2B%2BJavaPython-O(MlogM)-Complexity
    /*
    Follow Up
We just got lucky AC solution. Because W <= 10000.
What if W is huge, should we cut off card on by one?


Solution 2:
Count number of different cards to a map c
Cur represent current open straight groups.
In a deque start, we record the number of opened a straight group.
Loop from the smallest card number.
For example, hand = [1,2,3,2,3,4], W = 3
We meet one 1:
opened = 0, we open a new straight groups starting at 1, push (1,1) to start.
We meet two 2:
opened = 1, we need open another straight groups starting at 1, push (2,1) to start.
We meet two 3:
opened = 2, it match current opened groups.
We open one group at 1, now we close it. opened = opened - 1 = 1
We meet one 4:
opened = 1, it match current opened groups.
We open one group at 2, now we close it. opened = opened - 1 = 0

return if no more open groups.

Complexity
O(MlogM + N), where M is the number of different cards.
Because I count and sort cards.
In Cpp and Java it's O(NlogM), which can also be improved.

     */
    public boolean isNStraightHand(int[] hand, int W) {
        Map<Integer, Integer> c = new TreeMap<>();
        for (int i : hand) c.put(i, c.getOrDefault(i, 0)+1);
        Queue<Integer> start = new LinkedList<>();
        int last_checked = -1, opened = 0;
        for (int i : c.keySet()) {
            if (opened > 0 && i > last_checked + 1 || opened > c.get(i)) return false;
            start.add(c.get(i) - opened);
            last_checked = i; opened = c.get(i);
            if (start.size() == W) opened -= start.remove();
        }
        return opened == 0;
    }


    //https://leetcode.com/problems/hand-of-straights/discuss/135598/C%2B%2BJavaPython-O(MlogM)-Complexity
//Solution 1
//Count number of different cards to a map c
//Loop from the smallest card number.
//Everytime we meet a new card i, we cut off i - i + W - 1 from the counter.
//
//Complexity:
//Time O(MlogM + MW), where M is the number of different cards.
    public boolean isNStraightHand2(int[] hand, int W) {
        Map<Integer, Integer> c = new TreeMap<>();
        for (int i : hand) c.put(i, c.getOrDefault(i, 0)+1);
        for (int it : c.keySet())
            if (c.get(it) > 0)
                for (int i = W - 1; i >= 0; --i) {
                    if (c.getOrDefault(it + i, 0) < c.get(it)) return false;
                    c.put(it + i, c.get(it + i) - c.get(it));
                }
        return true;
    }
}
