package topinterviewquestions;

import java.util.*;

/*
On a campus represented as a 2D grid, there are n workers and m bikes, with n <= m. Each worker and bike is a 2D coordinate on this grid.

We assign one unique bike to each worker so that the sum of the Manhattan distances between each worker and their assigned bike is minimized.

Return the minimum possible sum of Manhattan distances between each worker and their assigned bike.

The Manhattan distance between two points p1 and p2 is Manhattan(p1, p2) = |p1.x - p2.x| + |p1.y - p2.y|.



Example 1:


Input: workers = [[0,0],[2,1]], bikes = [[1,2],[3,3]]
Output: 6
Explanation:
We assign bike 0 to worker 0, bike 1 to worker 1. The Manhattan distance of both assignments is 3, so the output is 6.
Example 2:


Input: workers = [[0,0],[1,1],[2,0]], bikes = [[1,0],[2,2],[2,1]]
Output: 4
Explanation:
We first assign bike 0 to worker 0, then assign bike 1 to worker 1 or worker 2, bike 2 to worker 2 or worker 1. Both assignments lead to sum of the Manhattan distances as 4.
Example 3:

Input: workers = [[0,0],[1,0],[2,0],[3,0],[4,0]], bikes = [[0,999],[1,999],[2,999],[3,999],[4,999]]
Output: 4995


Constraints:

n == workers.length
m == bikes.length
1 <= n <= m <= 10
workers[i].length == 2
bikes[i].length == 2
0 <= workers[i][0], workers[i][1], bikes[i][0], bikes[i][1] < 1000
All the workers and the bikes locations are unique.
 */
public class Problem_1066_CampusBikesII_G {
    /*
    Solution
Approach 1: Greedy Backtracking
Intuition

We have NN workers and MM bikes, each worker needs to be assigned a unique bike. Since our objective is to find the combination of workers and bikes that results in the minimum distance sum, intuitively, we can create every possible combination of workers paired with bikes. Then, among all of these combinations, the one that has the minimum distance sum will be our answer.

Let's see how many combinations we can have. The first worker will have MM choices, the second worker will have M-1M−1 choices, and so on up to the NthNth worker who will have M-N+1M−N+1 bikes to choose from. The number of combinations we can have will be equal to M*(M-1)*(M-2)*............(M-N+1)M∗(M−1)∗(M−2)∗............(M−N+1). This expression is equivalent to (M! / (M - N)!)(M!/(M−N)!). When NN is equal to MM, the above expression simplifies to M!M!

When MM is constrained to be less than or equal to approximately 1212, solutions with a time complexity of O(M!)O(M!) are feasible. However, a time complexity of O(M!)O(M!) is generally not ideal. Let's consider how we can optimize this approach to reduce the execution time.

Suppose we've obtained one combination of workers and bikes with the total distance sum as smallestDistanceSum and we are still searching for other combinations with the hope of finding a better result. Now during this process, if after assigning bikes to some workers we have incurred a total distance sum of currDistanceSum and currDistanceSum >= smallestDistanceSum then we don't need to go further to assign bikes to the remaining workers because the total distance sum at the end will never be smaller than our smallest distance we have achieved so far which is smallestDistanceSum. Therefore we can greedily stop searching when our current solution cannot be better than the best solution we have found so far.

Algorithm

For every worker starting from the worker at index 0, traverse over the bikes and assign the bike to the worker if it is available (visited[bikeIndex] = false). After assigning the bike mark it as unavailable (visited[bikeIndex] = true).
Add the Manhattan distance of the above assignment to the total distance incurred so far represented by currDistanceSum and enter the recursive call for the next worker.
When the recursive call is finished, make the bike as available again by setting visited[bikeIndex] to false.
If we have assigned bikes to all the workers, compare the currDistanceSum with the smallestDistanceSum and update the smallestDistanceSum accordingly.
Before assigning any bike to the worker, check if the currDistanceSum is already greater than or equal to smallestDistanceSum. If so, then skip the rest of the workers and return. This is because currDistanceSum can only increase, and thus we will not find a better result than smallestDistanceSum using the current combination of workers and bikes.
Implementation


Complexity Analysis

Here NN is the number of workers, and MM is the number of bikes.

Time complexity: O(M! / (M - N)!)O(M!/(M−N)!)

As discussed above, in the worst case, we will end up finding all the combinations of workers and bikes. Notice that this is equivalent to the number of permutations of NN bikes taken from MM total bikes.

Space complexity: O(N + M)O(N+M)

We have used an array visited to mark if the bike is available or not this will use O(M)O(M) space. There will also be some stack space used while making recursive calls. The recursion stack space used is proportional to the maximum number of active function calls in the stack. At most, this will be equal to the number of workers O(N)O(N).


Approach 2: Top-Down Dynamic Programming + BitMasking
Intuition

The time complexity of the previous approach, O(M! / (M - N)!)O(M!/(M−N)!), means it is only reasonable to use when the number of bikes (MM) or the number of workers (NN) is small. Let's see if we can find a way to solve this problem more efficiently.
There are two elements of this problem that serve as hints for another way to approach the problem:

The problem requires us to minimize the distance sum by making sequential decisions (assigning bikes to workers).
Each decision we make is affected by the previous decisions we made (which bikes are available depends on which bikes have already been assigned). These are both characteristics of problems that can be solved using dynamic programming. Thus, in this approach, we will leverage recursive dynamic programming.
Previously, we used the visited array to mark the availability of bikes. In this approach, we will be using bits to represent the above. Since the maximum number of bikes is less than 3232, we can use bitmasking to represent which bikes have been taken with a single integer.

The availability of bikes is now represented by an integer mask having 1010 bits. The 1010 bits represent the states of 1010 bikes. A value of 00 at the ith bit signifies that the bike at the ith index is available while a value of 11 signifies that the bike has been assigned to a worker.

Similar to the previous approach, for every worker starting from the worker at index 0, we will traverse over the bikes and assign it to the worker if it is available. Availability of ith bike can be checked by the ith bit in mask, the bike is available if the ith bit in mask is 00. When we assign a bike to the worker we should mark it as unavailable for further workers and for that we need to change the ith bit to 11.

In this approach we need to check/set/unset a particular bit in an integer. The below slides show how bitwise AND (&) can be used to check if the ith bit is set, how bitwise OR (|) can be used to set the ith bit, and how bitwise XOR (^) can be used to unset the ith bit.

Current
1 / 3
Algorithm

For every worker starting from the worker at index 0, traverse over the bits of mask and assign it to the worker if it is available (bit at bikeIndex in mask is 0 ). After assigning the bike mark it is unavailable (change the bit at bikeIndex in mask to 1).
Add the Manhattan distance of the above assignment and add it to the distance that will be returned by the recursive function minimumDistanceSum for the next worker workerIndex.
If we have assigned bikes to all the workers (workerIndex >= workers.size()) then we can return the distance as 0.
Use memoization to store the result corresponding to mask, because there will be repeated subproblems as shown below. This will help us to avoid recalculating subproblems.
fig

Note: Although we have two states in our recursive functions, mask and workerIndex still we don't need to memoize the result corresponding to both workerIndex and mask because workerIndex is equal to the number of set bits in mask. Hence both this information can be represented by mask itself.

Implementation


Complexity Analysis

Here NN is the number of workers, and MM is the number of bikes.

Time complexity: O(M \cdot 2^M)O(M⋅2
M
 )

Time complexity is equal to the number of unique states in the memo table multiplied by the average time that the minimumDistanceSum function takes. The number of states is equal to unique values of mask that is 2^M2
M
  and the minimumDistanceSum function takes O(M)O(M) time. So the time complexity is O(M \cdot 2^M)O(M⋅2
M
 ).

Space complexity: O(2^M)O(2
M
 )

We have used an array memo to store the results corresponding to mask. Also, there will be some stack space used during recursion. The recursion space will be equal to the maximum number of the active function calls in the stack that will be equal to the number of workers i.e., NN. Hence the space complexity will be equal to O(2^M + N)O(2
M
 +N).


Approach 3: Bottom-Up Dynamic Programming + BitMasking
Intuition

In the previous approach, the recursive calls incurred stack space. We can avoid this by applying the same approach in an iterative manner which is generally faster than the top-down approach. As explained in the previous approach, mask represents the availability of bikes. The 1010 bits represent the states of 1010 bikes. A value of 00 at the ith bit signifies that the bike at the ith index is available, while a value of 11 signifies that the bike has been assigned to a worker.

It is given in the constraints that the number of bikes (numOfBikes) will always be greater than and equal to the number of workers (numOfworkers). We need to assign one bike to all the workers, so our final representation of mask will have numOfWorkers number of 11's denoting that the bikes at these indices have been assigned. Among all the possible representations of mask with numOfWorkers number of 11's, we need the one that has the minimum distance sum.

We will traverse over all the possible values of mask. For each value, we will use its distance sum to find the distance sum for other values of mask by changing the zeroes to ones. Suppose the current value of mask is 1001011011 with 66 ones, which signifies that 66 workers have been assigned a bike. From the current value of the mask, we want to find the possible representations having 77 ones. In the current value 1001011011, we can replace any 0 with 1 and add the additional distance of this assignment to the distance sum of the original mask value.

In the above process, the value of mask will be repeated hence we will memoize the result corresponding to mask to avoid recalculation.

Algorithm

Traverse over mask from 00 to 2^{10}2
10
 .
For every value of mask traverse over the bikeIndex. If the bike (at index bikeIndex) has not been assigned (bit at bikeIndex in mask is 0) then change the bit at bikeIndex to 1. The new value of mask is denoted by newMask.
The worker to which the above bike is assigned is given by the number of bikes already assigned (equal to the number of 1s in mask) denoted by nextWorkerIndex.
The distance sum for newMask will be equal to the distance sum for mask (memo[mask]) plus the Manhattan distance between nextWorkerIndex and bikeIndex. Record the distance for newMask in memo for future reference.
Base case will be when we have an equal or more number of ones in mask (nextWorkerIndex) than the number of workers (numOfWorkers). Note that for mask having more number of ones than the numOfWorkers, the value of smallestDistanceSum will not be affected because the value for such mask in memo is initially set to INT_MAX.
Implementation


Complexity Analysis

Here NN is the number of workers, and MM is the number of bikes.

Time complexity: O(M \cdot 2^M)O(M⋅2
M
 )

We traverse over all of the values for mask from 00 to 2^M2
M
  and for each value, we traverse over the MM bikes and also count the number of ones in mask, which on average takes M / 2M/2 iterations using Kernighan's Algorithm. So the time complexity will be O(2^M \cdot (M + M / 2))O(2
M
 ⋅(M+M/2)) which simplifies to O(M \cdot 2^M)O(M⋅2
M
 ).

Space complexity: O(2^M)O(2
M
 ).

We are only using space in memo with the size equal to 2^M2
M
 .


Approach 4: Priority Queue (Similar to Dijkstra's Algorithm)
Intuition

In the previous approach, we traversed over all of the values for mask from 00 to 2^M2
M
  and generated newMask each time. In this approach instead of traversing over mask in a sequential manner, we will traverse mask in increasing order of the total distance sum. This is because it is more likely to find the smallest distance sum from a mask that currently has a smaller sum of distance.

To find the next mask with the lowest distance sum, we will use a priority queue. With every mask, we will store the total Manhattan distance as a pair/vector ({distanceSum, mask}) in the priority queue. In order to avoid processing a repeated mask, we will use a HashSet/Map visited to store the processed mask. Since we are traversing the mask in increasing order of their distance sum, the first time when the mask consists of 11's equal to the number of workers numOfWorkers we will know that it's the smallest distance sum possible.

Algorithm

As an initial state push {0, 0} to the priority queue, signifying that the mask is 00 and the sum of distance is 00.
Pop the top pair ({currentDistanceSum, currentMask}) from the priority queue. We will discard this pair and continue to the next pair if the currentMask has been already visited.
Traverse over bikes and if the bike at bikeIndex has not been assigned (the bit at bikeIndex in currentMask is 00), then assign it to the worker workerIndex.
Add the next state pair {nextStateDistanceSum, nextStateMask} to the priority queue.
Return the currentDistanceSum if the workerIndex is equal to numOfWorkers.
Implementation


Complexity Analysis

Here NN is the number of workers, MM is the number of bikes and,

P(M, N) = M! / (M - N)!P(M,N)=M!/(M−N)! is the number of permutations for NN bikes taken from MM total bikes,

C(M, N) = M! / ((M - N)! \cdot N!)C(M,N)=M!/((M−N)!⋅N!) is the number of ways to choose NN bikes from MM total bikes.

Time complexity: O(P(M, N) \cdot \log (P(M, N)) + (M \cdot \log (P(M, N)) \cdot 2^M)O(P(M,N)⋅log(P(M,N))+(M⋅log(P(M,N))⋅2
M
 )

Priority queue might have more than 11 copy of a mask. For instance 0011 will be inserted into the priority queue twice, the first occasion, 0001 -> 0011, the second occasion 0010 -> 0011.

The total number of the possible mask with size M and having N ones will be C(M, N). For each such mask, the order in which 1's are added to mask will also matter, this can be done in N! ways. So in total, there can be C(M, N) \cdot N! = P(M, N)C(M,N)⋅N!=P(M,N) number of mask in the priority queue. All these mask will be iterated in the while loop and for each mask, \log (P(M, N))log(P(M,N)) number of operations will be required for removing the top pair from the priority queue.

Since we are tracking the mask that we have traversed using visited set, the inner for loop where we are traversing over the bikes will only be executed for only unique values of mask that is 2^M2
M
 . Also pushing into priority queue will cost \log (P(M, N))log(P(M,N)) time.

Hence the total time complexity becomes O(P(M, N) \cdot \log (P(M, N)) + (M \cdot \log (P(M, N)) \cdot 2^M)O(P(M,N)⋅log(P(M,N))+(M⋅log(P(M,N))⋅2
M
 ).

Space complexity: O(P(M, N) + 2^M)O(P(M,N)+2
M
 )

The number of mask that can be stored in the priority queue is O(P(M, N))O(P(M,N)), and the number of mask that can be inserted into the set visited will be O(2^M)O(2
M
 ).
     */


    class Solution1 {
        // Maximum number of bikes is 10
        boolean visited [] = new boolean[10];
        int smallestDistanceSum = Integer.MAX_VALUE;

        // Manhattan distance
        private int findDistance(int[] worker, int[] bike) {
            return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
        }

        private void minimumDistanceSum(int[][] workers, int workerIndex, int[][] bikes, int currDistanceSum) {
            if (workerIndex >= workers.length) {
                smallestDistanceSum = Math.min(smallestDistanceSum, currDistanceSum);
                return;
            }
            // If the current distance sum is greater than the smallest result
            // found then stop exploring this combination of workers and bikes
            if (currDistanceSum >= smallestDistanceSum) {
                return;
            }
            for (int bikeIndex = 0; bikeIndex < bikes.length; bikeIndex++) {
                // If bike is available
                if (!visited[bikeIndex]) {
                    visited[bikeIndex] = true;
                    minimumDistanceSum(workers, workerIndex + 1, bikes,
                            currDistanceSum + findDistance(workers[workerIndex], bikes[bikeIndex]));
                    visited[bikeIndex] = false;
                }
            }
        }

        public int assignBikes(int[][] workers, int[][] bikes) {
            minimumDistanceSum(workers, 0, bikes, 0);
            return smallestDistanceSum;
        }
    }

    class Solution2 {
        // Maximum value of mask will be 2^(Number of bikes)
        // and number of bikes can be 10 at max
        int memo [] = new int[1024];

        // Manhattan distance
        private int findDistance(int[] worker, int[] bike) {
            return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
        }

        private int minimumDistanceSum(int[][] workers, int[][] bikes, int workerIndex, int mask) {
            if (workerIndex >= workers.length) {
                return 0;
            }

            // If result is already calculated, return it no recursion needed
            if (memo[mask] != -1)
                return memo[mask];

            int smallestDistanceSum = Integer.MAX_VALUE;
            for (int bikeIndex = 0; bikeIndex < bikes.length; bikeIndex++) {
                // Check if the bike at bikeIndex is available
                if ((mask & (1 << bikeIndex)) == 0) {
                    // Adding the current distance and repeat the process for next worker
                    // also changing the bit at index bikeIndex to 1 to show the bike there is assigned
                    smallestDistanceSum = Math.min(smallestDistanceSum,
                            findDistance(workers[workerIndex], bikes[bikeIndex]) +
                                    minimumDistanceSum(workers, bikes, workerIndex + 1,
                                            mask | (1 << bikeIndex)));
                }
            }

            // Memoizing the result corresponding to mask
            return memo[mask] = smallestDistanceSum;
        }

        public int assignBikes(int[][] workers, int[][] bikes) {
            Arrays.fill(memo, -1);
            return minimumDistanceSum(workers, bikes, 0, 0);
        }
    }

    class Solution3 {
        // Maximum value of mask will be 2^(Number of bikes)
        // And number of bikes can be 10 at max
        int memo [] = new int[1024];

        // Count the number of ones using Brian Kernighan’s Algorithm
        private int countNumOfOnes(int mask) {
            int count = 0;
            while (mask != 0) {
                mask &= (mask - 1);
                count++;
            }
            return count;
        }

        // Manhattan distance
        private int findDistance(int[] worker, int[] bike) {
            return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
        }

        private int minimumDistanceSum(int[][] workers, int[][] bikes) {
            int numOfBikes = bikes.length;
            int numOfWorkers = workers.length;
            int smallestDistanceSum = Integer.MAX_VALUE;

            // 0 signifies that no bike has been assigned and
            // Distance sum for not assigning any bike is equal to 0
            memo[0] = 0;

            // Traverse over all the possible values of mask
            for (int mask = 0; mask < (1 << numOfBikes); mask++) {
                int nextWorkerIndex = countNumOfOnes(mask);

                // If mask has more number of 1's than the number of workers
                // Then we can update our answer accordingly
                if (nextWorkerIndex >= numOfWorkers) {
                    smallestDistanceSum = Math.min(smallestDistanceSum, memo[mask]);
                    continue;
                }

                for (int bikeIndex = 0; bikeIndex < numOfBikes; bikeIndex++) {
                    // Checking if the bike at bikeIndex has already been assigned
                    if ((mask & (1 << bikeIndex)) == 0) {
                        int newMask = (1 << bikeIndex) | mask;

                        // Updating the distance sum for newMask
                        memo[newMask] = Math.min(memo[newMask], memo[mask] +
                                findDistance(workers[nextWorkerIndex], bikes[bikeIndex]));
                    }
                }
            }

            return smallestDistanceSum;
        }

        public int assignBikes(int[][] workers, int[][] bikes) {
            // Initializing the answers for all masks to be INT_MAX
            Arrays.fill(memo, Integer.MAX_VALUE);
            return minimumDistanceSum(workers, bikes);
        }
    }

    class Solution4 {
        // Manhattan distance
        private int findDistance(int[] worker, int[] bike) {
            return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
        }

        // Count the number of ones using Brian Kernighan’s Algorithm
        private int countNumOfOnes(int mask) {
            int count = 0;
            while (mask != 0) {
                mask &= (mask - 1);
                count++;
            }
            return count;
        }

        public int assignBikes(int[][] workers, int[][] bikes) {
            int numOfBikes = bikes.length, numOfWorkers = workers.length;

            PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
            Set<Integer> visited = new HashSet<>();

            // Initially both distance sum and mask is 0
            priorityQueue.add(new int[]{0, 0});
            while (!priorityQueue.isEmpty()) {
                int currentDistanceSum = priorityQueue.peek()[0];
                int currentMask = priorityQueue.peek()[1];
                priorityQueue.remove();

                // Continue if the mask is already traversed
                if (visited.contains(currentMask))
                    continue;

                // Marking the mask as visited
                visited.add(currentMask);
                // Next Worker index would be equal to the number of 1's in currentMask
                int workerIndex = countNumOfOnes(currentMask);

                // Return the current distance sum if all workers are covered
                if (workerIndex == numOfWorkers) {
                    return currentDistanceSum;
                }

                for (int bikeIndex = 0; bikeIndex < numOfBikes; bikeIndex++) {
                    // Checking if the bike at bikeIndex has been assigned or not
                    if ((currentMask & (1 << bikeIndex)) == 0) {
                        int nextStateDistanceSum = currentDistanceSum +
                                findDistance(workers[workerIndex], bikes[bikeIndex]);

                        // Put the next state pair into the priority queue
                        int nextStateMask = currentMask | (1 << bikeIndex);
                        priorityQueue.add(new int[]{nextStateDistanceSum, nextStateMask});
                    }
                }
            }

            // This statement will never be executed provided there is at least one bike per worker
            return -1;
        }
    }


    //diss
    class Solution {
        public int assignBikes(int[][] workers, int[][] bikes) {
            Queue<Node> pq = new PriorityQueue<>(1,(a, b)->(a.cost-b.cost));
            Set<String> seen = new HashSet<>();
            pq.offer(new Node(0,0,0));
            while (!pq.isEmpty()){
                Node curr = pq.poll();
                String key = "$"+curr.worker+"$"+curr.mask;
                // reason - you can skip if you have already seen this mask
                // is because this is a PQ - and lower cost has already been seen
                // with this exact mask (i.e., those bikes used in some order)
                // then there is no point to consider a higher cost one
                if (seen.contains(key))
                    continue;
                seen.add(key);
                // all workers have a bike if this is true
                if (curr.worker == workers.length)
                    return curr.cost;
                // scan all bikes - and create new nodes into the PQ for next worker.
                for(int j = 0; j < bikes.length; j++){
                    if ( (curr.mask & (1<<j)) == 0){
                        pq.offer( new Node(curr.worker+1, curr.mask | (1 << j),
                                curr.cost + getDist(bikes[j], workers[curr.worker]) ));
                    }
                }
            }
            return -1;
        }
        private int getDist(int[] bikepos,int[] wpos){
            return Math.abs(bikepos[0]-wpos[0]) + Math.abs(bikepos[1]-wpos[1]);
        }
        static class Node {
            int worker;
            int mask;
            int cost;
            public Node(int w,int m,int cost){
                this.worker = w;
                this.mask = m;
                this.cost = cost;
            }
        }
    }

    //DFS + Pruning And DP Solution
    /*
    The DFS solution is pretty straight forward, try assign each bike to each worker.
Time Complexy: O(n * m !), n is number of workers, m is number of bikes

Ususally, when input size <= 10, O(n!) can be accepeted. When input size <= 12, we probably need do some pruning. if the test case is not strong, or problem designer wants to allow this techonolgy (dfs + pruning) to pass. we can luckly get a AC.(For my experenice in LeetCode, when problem is tagged as Medium, this kind solution can be passed)

For this problem, we add a very simple but effective condition:

	if (distance > min) return ;
	int min = Integer.MAX_VALUE;
    public int assignBikes(int[][] workers, int[][] bikes) {
        dfs(new boolean[bikes.length], workers, 0, bikes, 0);
        return min;
    }
    public void dfs(boolean[] visit, int[][] workers, int i, int[][] bikes, int distance) {
        if (i >= workers.length) {
            min = Math.min(distance, min);
            return ;
        }
        if (distance > min) {
            return ;
        }
        for (int j = 0; j < bikes.length; j++) {
            if (visit[j]) {
                continue;
            }
            visit[j] = true;
            dfs(visit, workers, i + 1, bikes, distance + dis(bikes[j], workers[i]));
            visit[j] = false;
        }

    }
    public int dis(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }
Actually the Brute Force Solution is a Permutation Problem's solution.
One possible optimization is transfering Permutation Problem to Combination Problem(subset problem )，from n! to 2^n. In Leetcode, I think when n < 15 , it can be accepted.

Assuming we have 3 bikes, 3 workers, and [ (b1,w1), (b2,w2),(b3,w3)] is our solution. In the Above DFS solution, we have caculated [(b2,w2),(b1,w1),(b3,w3)], [(b1,w1),(b3,w3),(b2,w2)] and so on. The distance of them are exactly same, However we only need one. In another word, we only need to know the distance of set, not list :{(b1,w1),(b2,w2),(b3,w3)}, now the problem change from permutation problem to combination.

As long as we assign b3 to w3, b2 to w2, b1 to w1, we are good. The assigning order is userless.
For ith worker, the min distance = ith worker uses jth bike + min distance all i - 1 workers to use i -1 others bike from m. so this is dp problem .

Here we use bit to represent jth bike is used or not
state : dp[i][s] = the min distance for first i workers to build the state s ,
transit: dp[i][s] = min(dp[i][s], dp[i - 1][prev] + dis(w[i -1], bike[j)) | 0 < j <m, prev = s ^ (1 << j)
init:dp[0][0] = 0;
result: dp[n][s] s should have n bit

the code should be easy to understand.

  public int assignBikes(int[][] workers, int[][] bikes) {
        int n = workers.length;
        int m = bikes.length;
        int[][] dp = new int[n + 1][1 << m];
        for (int[] d : dp) {
            Arrays.fill(d, Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            for (int s = 1; s < (1 << m); s++) {
                for (int j = 0; j < m; j++) {
                    if ((s & (1 << j)) == 0) {
                        continue;
                    }
                    int prev = s ^ (1 << j);
                    dp[i][s] = Math.min(dp[i - 1][prev] + dis(workers[i - 1], bikes[j]), dp[i][s]) ;
                    if (i == n) {
                        min = Math.min(min, dp[i][s]);
                    }
                }
            }
        }
        return min;
    }

    public int dis(int[] p1, int[] p2) {
        return Math.abs(p1[0] - p2[0]) + Math.abs(p1[1] - p2[1]);
    }
Honestly, All the words are my thinking processing and code is my first accepted submission, so not perfect

I think this problem may have a existing algorithm, But I don't know.
     */

}
