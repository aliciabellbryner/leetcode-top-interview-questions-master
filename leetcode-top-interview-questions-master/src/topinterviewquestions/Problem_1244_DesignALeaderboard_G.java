package topinterviewquestions;

import java.util.*;

/*
Design a Leaderboard class, which has 3 functions:

addScore(playerId, score): Update the leaderboard by adding score to the given player's score. If there is no player with such id in the leaderboard, add him to the leaderboard with the given score.
top(K): Return the score sum of the top K players.
reset(playerId): Reset the score of the player with the given id to 0 (in other words erase it from the leaderboard). It is guaranteed that the player was added to the leaderboard before calling this function.
Initially, the leaderboard is empty.



Example 1:

Input:
["Leaderboard","addScore","addScore","addScore","addScore","addScore","top","reset","reset","addScore","top"]
[[],[1,73],[2,56],[3,39],[4,51],[5,4],[1],[1],[2],[2,51],[3]]
Output:
[null,null,null,null,null,null,73,null,null,null,141]

Explanation:
Leaderboard leaderboard = new Leaderboard ();
leaderboard.addScore(1,73);   // leaderboard = [[1,73]];
leaderboard.addScore(2,56);   // leaderboard = [[1,73],[2,56]];
leaderboard.addScore(3,39);   // leaderboard = [[1,73],[2,56],[3,39]];
leaderboard.addScore(4,51);   // leaderboard = [[1,73],[2,56],[3,39],[4,51]];
leaderboard.addScore(5,4);    // leaderboard = [[1,73],[2,56],[3,39],[4,51],[5,4]];
leaderboard.top(1);           // returns 73;
leaderboard.reset(1);         // leaderboard = [[2,56],[3,39],[4,51],[5,4]];
leaderboard.reset(2);         // leaderboard = [[3,39],[4,51],[5,4]];
leaderboard.addScore(2,51);   // leaderboard = [[2,51],[3,39],[4,51],[5,4]];
leaderboard.top(3);           // returns 141 = 51 + 51 + 39;


Constraints:

1 <= playerId, K <= 10000
It's guaranteed that K is less than or equal to the current number of players.
1 <= score <= 100
There will be at most 1000 function calls.
 */
public class Problem_1244_DesignALeaderboard_G {
    /*
    Overview
There are a lot of implementations for this particular problem out there. The problem statement is pretty straightforward on the surface:

We need to maintain a list of playerId to score mappings.
Whenever required, obtain the top K scores, add them up, and return them.
Finally, reset the score for a particular player.
We will start with the most basic, brute-force implementations for this problem and then move on to slightly complex implementations. To understand, what these complicated implementations will be using, we need to see the basic requirement of this problem.

We have a dynamically updating list of values and we need to be able to extract the top-k elements from that list.

Whenever we have such a problem statement which requires us to obtain the top-K values from a list which is dynamically updating, relying on a priority-queue seems like a good bet. A heap is one of the best data structures for handling such a requirement. So, we will be looking at a solution that makes use of the heap data structure.

Additionally, we will be looking at a binary search tree based solution. Although the heap is a great data structure for finding the top-K elements from a list without having to actually sort the list, it is not great at find-and-update kind of operations. General rule of thumb with the heap data structure is to use lazy-updates rather than having to traverse and update the entries themselves. We won't get a deterministic performance if we resort to lazy score updates here because we don't know the number of update operations and hence, the size of the heap can continue to grow if we have millions of score updates and no top-K function calls (or proportionally lower).



Approach 1: Brute Force
The brute-force approach is pretty straightforward in the sense that we will maintain a dictionary of playerId as the key and the score as the dictionary. Then, for each top operation, we will simply obtain all the values from the dictionary, sort them, and the obtain the top K elements.

Algorithm

Initialize a dictionary scores that will use the playerId as the key and score as the value.
addScore ~
Simply update the dictionary with the new score for the player.
If the player doesn't exist, initialize the score to score
top ~
Obtain a list of all the values from the dictionary.
Sort the list in reverse order.
Sum up the first K values from the sorted list.
reset ~
Delete the entry containing the playerId
Note that we can also set the value (score) to 0. The only disadvantage of this is that we will be sorting even reset players in the top function. This doesn't matter much for smaller test cases though.
Implementation


Complexity Analysis

Time Complexity:

O(1)O(1) for addScore.
O(1)O(1) for reset.
O(N \text{log}N)O(NlogN) for top where NN represents the total number of players since we sort all of the player scores and then take the top K from the sorted list.
Space Complexity:

O(N)O(N) used by the scores dictionary and also by the new list formed using the dictionary values in the top function.

Approach 2: Heap for top-K
This is a slight modification to the previous approach in that instead of sorting the list of the values, we will be making use of a min-heap to find the top-K values. This is a slightly modified version of the previous implementation.

Algorithm

Initialize a dictionary scores that will use the playerId as the key and score as the value.
addScore ~
Simply update the dictionary with the new score for the player.
If the player doesn't exist, initialize the score to score
top ~
Initialize a new min-heap, scoreHeap.
Iterate over the first K values in the dictionary and add them to the heap.
Then, for the rest of the N-KN−K values, we will simply do the following. We will add the new value to the heap, and pop the smallest value from the heap. In doing this, we maintain the size of the heap to K and also remove the smaller of the two values.
We do this for all of the N-KN−K values and then, simply add up all the values remaining in the heap since those would be the largest K values left.
reset ~
Delete the entry containing the playerId
Note that we can also set the value (score) to 0. The only disadvantage of this is that we will be sorting even reset players in the top function. This doesn't matter much for smaller test cases though.
Implementation


Complexity Analysis

Time Complexity:

O(1)O(1) for addScore.
O(1)O(1) for reset.
O(K) + O(N \text{log}K)O(K)+O(NlogK) = O(N \text{log}K)O(NlogK). It takes O(K)O(K) to construct the initial heap and then for the rest of the N-KN−K elements, we perform the extractMin and add operations on the heap each of which take (\text{log}K)(logK) time.
Space Complexity:

O(N + K)O(N+K) where O(N)O(N) is used by the scores dictionary and O(K)O(K) is used by the heap.

Approach 3: Using a TreeMap / SortedMap
This approach is inspired by this discussion thread. Here we will try to improve on the overall time complexity of the top function at the expense of the time complexity of the addScore function. As discussed before, a heap doesn't have any properties that aid in search. At the end of the day, it is simply list of elements with certain properties associating them. However, these properties do not enhance the searchability of the data structure as a whole. We can definitely do enhancements where we maintain references to nodes in the heap, in a dictionary and then use those references for making updates. However, we will be looking at the TreeMap data structure (java) which uses the balanced-binary-search tree instead.

The great advantage we get with a balanced BST is that the search/add/remove operations are all bounded by a logarithmic complexity in terms of the number of elements in the tree. This is achievable due to the structure of the tree and the relationship between the subtrees and a root.

Algorithm

Initialize a dictionary scores that will use the playerId as the key and score as the value.
Initialize a TreeMap (java) or a SortedMap (python) called sortedScoreMap. The way this would be structured is that the key would be a score and the value would be the number of players that have this score. Imagine this being represented as a balanced BST with the keys being used for arranging the tree. We need the top function to use the scores and so, we use them as the key.
addScore ~
Note the old score for the player. Let it be called oldScore.
Update the value of oldScore in sortedScoreMap TreeMap. If the value has reached 0, remove the score entry.
Simply update the dictionary with the new score for the player.
Add the updated value to the sortedScoreMap as well by incrementing the value by 1 i.e. one more player has this score.
If the player doesn't exist, initialize the score to score.
top ~
Iterate over all the keys in the sortedScoreMap. Note that since the data structure is a BST, an inorder traversal of the keys would return them in the sorted order. We don't need to sort them again. Hence, we will simply iterate over the keys and pick the first K. Also, we have arranged the tree with each score mapped to how many players have that score. So there are no duplicates in the tree.
Pick the first K entries i.e. first K values.
For each key, multiply (key * value) and add it to the total sum.
Also, reduce the counter counting down to K by value.
reset ~
Note the old score for the player. Let it be called oldScore.
Update the value of oldScore in sortedScoreMap TreeMap. If the value has reached 0, remove the score entry.
Delete the entry containing the playerId.
Implementation

Note that we are using SortedDict in Python. This is an external, apache licensed package that is supported by the Leetcode platform. You can read more about it here. We don't have a way to construct a reverse SortedDict in Python and hence, we negate the scores before adding them to the dict (TreeMap like data structure) so that the normal in-order traversal would give us the scores in the reverse order i.e. descending order.


Complexity Analysis

Time Complexity:

O(\text{log}N)O(logN) for addScore. This is because each addition to the BST takes a logarithmic time for search. The addition itself once the location of the parent is known, takes constant time.
O(\text{log}N)O(logN) for reset since we need to search for the score in the BST and then update/remove it. Note that this complexity is in the case when every player always maintains a unique score.
It takes O(K)O(K) for our top function since we simply iterate over the keys of the TreeMap and stop once we're done considering K scores. Note that if the data structure doesn't provide a natural iterator, then we can simply get a list of all the key-value pairs and they will naturally be sorted due to the nature of this data structure. In that case, the complexity would be O(N)O(N) since we would be forming a new list.
Space Complexity:

O(N)O(N) used by the scores dictionary. Also, if you obtain all the key-value pairs in a new list in the top function, then an additional O(N)O(N) would be used.
     */

    class Leaderboard2 {

        private HashMap<Integer, Integer> scores;

        public Leaderboard2() {
            this.scores = new HashMap<Integer, Integer>();
        }

        public void addScore(int playerId, int score) {

            if (!this.scores.containsKey(playerId)) {
                this.scores.put(playerId, 0);
            }

            this.scores.put(playerId, this.scores.get(playerId) + score);
        }

        public int top(int K) {

            // A min-heap in java containing entries of a hash map. Note that we have to provide
            // a comparator of our own to make sure we get the ordering right of these objects.
            PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

            for (Map.Entry<Integer, Integer> entry : this.scores.entrySet()) {
                heap.offer(entry);
                if (heap.size() > K) {
                    heap.poll();
                }
            }

            int total = 0;
            Iterator value = heap.iterator();
            while (value.hasNext()) {
                total += ((Map.Entry<Integer, Integer>)value.next()).getValue();
            }

            return total;
        }

        public void reset(int playerId) {
            this.scores.put(playerId, 0);
        }
    }

    class Leaderboard3 {

        Map<Integer, Integer> scores;
        TreeMap<Integer, Integer> sortedScores;

        public Leaderboard3() {
            this.scores = new HashMap<Integer, Integer>();
            this.sortedScores = new TreeMap<>(Collections.reverseOrder());
        }

        public void addScore(int playerId, int score) {

            // The scores dictionary simply contains the mapping from the
            // playerId to their score. The sortedScores contain a BST with
            // key as the score and value as the number of players that have
            // that score.
            if (!this.scores.containsKey(playerId)) {
                this.scores.put(playerId, score);
                this.sortedScores.put(score, this.sortedScores.getOrDefault(score, 0) + 1);
            } else {

                // Since the current player's score is changing, we need to
                // update the sortedScores map to reduce count for the old
                // score.
                int preScore = this.scores.get(playerId);
                int playerCount = this.sortedScores.get(preScore);


                // If no player has this score, remov it from the tree.
                if (playerCount == 1) {
                    this.sortedScores.remove(preScore);
                } else {
                    this.sortedScores.put(preScore, playerCount - 1);
                }

                // Updated score
                int newScore = preScore + score;
                this.scores.put(playerId, newScore);
                this.sortedScores.put(newScore, this.sortedScores.getOrDefault(newScore, 0) + 1);
            }
        }

        public int top(int K) {

            int count = 0;
            int sum = 0;

            // In-order traversal over the scores in the TreeMap
            for (Map.Entry<Integer, Integer> entry: this.sortedScores.entrySet()) {

                // Number of players that have this score.
                int times = entry.getValue();
                int key = entry.getKey();

                for (int i = 0; i < times; i++) {
                    sum += key;
                    count++;

                    // Found top-K scores, break.
                    if (count == K) {
                        break;
                    }
                }

                // Found top-K scores, break.
                if (count == K) {
                    break;
                }
            }

            return sum;
        }

        public void reset(int playerId) {
            int preScore = this.scores.get(playerId);
            this.sortedScores.put(preScore, this.sortedScores.get(preScore) - 1);
            if (this.sortedScores.get(preScore) == 0) {
                this.sortedScores.remove(preScore);
            }

            this.scores.remove(playerId);
        }
    }


    //diss
    class Leaderboard4 {
        Map<Integer, Integer> scores = new TreeMap<>(Comparator.reverseOrder()), players = new HashMap<>();

        public void addScore4(int playerId, int score) {
            int oldScore = players.getOrDefault(playerId, 0), newScore = oldScore + score;
            if (oldScore > 0)
                scores.put(oldScore, scores.get(oldScore) - 1);
            players.put(playerId, newScore);
            scores.put(newScore, scores.getOrDefault(newScore, 0) + 1);
        }

        public int top(int K) {
            int top = 0;
            for (Integer score : scores.keySet())
                for (int i = 0; i < scores.get(score); i++) {
                    top += score;
                    if (--K == 0)
                        return top;
                }
            return top;
        }

        public void reset(int playerId) {
            int score = players.remove(playerId);
            scores.put(score, scores.get(score) - 1);
        }
    }


}
