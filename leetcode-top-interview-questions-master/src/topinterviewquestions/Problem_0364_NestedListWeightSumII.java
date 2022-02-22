package topinterviewquestions;

import java.util.ArrayList;
import java.util.LinkedList;

/*
You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists.

The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1] has each integer's value set to its depth. Let maxDepth be the maximum depth of any integer.

The weight of an integer is maxDepth - (the depth of the integer) + 1.

Return the sum of each integer in nestedList multiplied by its weight.



Example 1:


Input: nestedList = [[1,1],2,[1,1]]
Output: 8
Explanation: Four 1's with a weight of 1, one 2 with a weight of 2.
1*1 + 1*1 + 2*2 + 1*1 + 1*1 = 8
Example 2:


Input: nestedList = [1,[4,[6]]]
Output: 17
Explanation: One 1 at depth 3, one 4 at depth 2, and one 6 at depth 1.
1*3 + 4*2 + 6*1 = 17


Constraints:

1 <= nestedList.length <= 50
The values of the integers in the nested list is in the range [-100, 100].
The maximum depth of any integer is less than or equal to 50.
 */
public class Problem_0364_NestedListWeightSumII {
    /*
    Overview
This problem is an extension of Nested List Weight Sum, where we need to find the sum of each integer multiplied by its depth. The slight change in this problem is that instead of multiplying each integer by its depth, we will multiply each integer by its weight which is equal to maxDepth - depth + 1. Here maxDepth is the maximum depth of any integer in the list.

The input here is a list of user-defined type nestedList. Each of these nestedList elements in the list can either be an integer or a list of nestedList elements. To clarify further, the following nested list is an example of a valid list of nestedList elements.

In the nested list [1, 2, 3, 4, [6, 7, [8]]], the first four elements are integers and the last one is a list of nestedList whose first two elements are integers and the last element is a nestedList with a single integer as its element.

Similarly, we can keep on increasing the nesting level by adding nestedList inside a nestedList.

Two things worth noting before we move on:

Input like [1,[2,[3,[[]]]]] is not valid because here one of the nestedList is empty (it does not contain an integer or a nestedList).
We will be working with the NestedInteger class for this problem. Accordingly, we must use the predefined functions getList and getInteger to access the data inside the given nestedList.

Approach 1: Double Pass Depth-First Search (DFS)
Intuition

To calculate the weight (maxDepth - depth + 1) of any integer, we must first find the maximum depth of the given nested list. How can we do that? Whenever we need to work with a nested object, we should always consider recursively exploring the nested objects. This recursive exploration can be done in a depth-first or breadth-first manner. In this approach, we will choose to use depth-first search.

If you are unfamiliar with depth-first search, you can learn about it in this Explore Card.

So to find the maximum depth, we can iterate over the elements in the given nested list, and the maximum depth of the list will be the maximum depth of any element inside the list. If the list only contains integers, then its depth is 1. However, if the list contains other nested lists, then its depth is 1 plus the maximum depth of these nested lists. Thus, we can recursively call our findMaxDepth function on any nested list to find the maximum depth.

Now that we know how to find the value of maxDepth we can use the insights from Nested List Weight Sum by changing depth to weight. We perform DFS over the list of nestedList one by one, keeping track of the current depth depth. If the element in the list is an integer, x, we add its product with the weight as x * (maxDepth - depth + 1) to answer. If the nested integer is a list, we recursively follow the same process on the nestedList but with depth equal to depth + 1.

Algorithm

Find the value of maxDepth. The recursive function findMaxDepth traverses over the NestedInteger and recursively explores each nested list. The depth of the current nested list will be one (for the current level) plus the maximum depth among all of the nested lists that it contains. If a nested list only contains integers, then return 1.
Perform another depth-first search on the list. This time, keep track of the current depth, and for every integer, add the product of the integer and its weight (maxDepth - depth + 1) to the answer.
Implementation


Complexity Analysis

Let NN be the total number of nested elements in the input list.

For example, the list [[[[[1]]]], 2] contains 44 nested lists and 22 nested integers (11 and 22), so NN is 66, for list [[[[1, [2]]]], [3, [4]]] there are 66 nested list and 44 integers, hence NN is 1010.

Time complexity: O(N)O(N)

We perform two depth-first searches: one to find the maximum depth and one to get the weighted sum of the nested list. In each DFS, we will visit every element exactly once. Hence the time complexity is O(N)O(N).

Space complexity: O(N)O(N)

Space complexity is equal to the maximum number of active stack calls during the depth-first search. In the worst case, such as [[[[[[1]]]]]], the call stack will use O(N)O(N) space. Hence the space complexity is O(N)O(N).


Approach 2: Single Pass Depth-First Search (DFS)
Intuition

In the previous approach, we perform DFS twice. Can we do this in a single traversal? The reason for doing DFS two times is that we need maxdepth to find the integer's weight, hence we have to find the maxdepth in advance to calculate the weight. If we can somehow pull out the maxDepth from weight definition to an independent term, we can solve the problem in a single traversal.

We need to find the value of \sum_{i=1}^{N} a_{i} * weight∑
i=1
N
​
 a
i
​
 ∗weight. Where a_ia
i
​
 's are all the integers present in the list, maxDepth is the maximum depth of an integer in list and depth_idepth
i
​
  is the depth of a_ia
i
​
 .

\sum_{i=1}^{N} a_{i} * weight_{i}∑
i=1
N
​
 a
i
​
 ∗weight
i
​


= \sum_{i=1}^{N} a_{i} * (maxDepth - depth_{i} + 1)∑
i=1
N
​
 a
i
​
 ∗(maxDepth−depth
i
​
 +1)

= \sum_{i=1}^{N} (a_{i} * maxDepth - a_i * depth_{i}+ a_i)∑
i=1
N
​
 (a
i
​
 ∗maxDepth−a
i
​
 ∗depth
i
​
 +a
i
​
 )

= \sum_{i=1}^{N} a_{i} * maxDepth∑
i=1
N
​
 a
i
​
 ∗maxDepth - \sum_{i=1}^{N} a_i * depth_{i}∑
i=1
N
​
 a
i
​
 ∗depth
i
​
  + \sum_{i=1}^{N} a_{i} ∑
i=1
N
​
 a
i
​


= maxDepth * \sum_{i=1}^{N} a_{i} maxDepth∗∑
i=1
N
​
 a
i
​
  - \sum_{i=1}^{N} a_i * depth_{i}∑
i=1
N
​
 a
i
​
 ∗depth
i
​
  + 1 * \sum_{i=1}^{N} a_{i} 1∗∑
i=1
N
​
 a
i
​


= (maxDepth + 1) * \sum_{i=1}^{N} a_{i} (maxDepth+1)∗∑
i=1
N
​
 a
i
​
  - \sum_{i=1}^{N} a_i * depth_{i}∑
i=1
N
​
 a
i
​
 ∗depth
i
​


= (maxDepth + 1) * sumOfElements(maxDepth+1)∗sumOfElements - sumOfProductssumOfProducts
Notice that maxDepth is now outside of the summation. Thus, we do not need to use maxDepth until the last step in our calculation. Therefore we can find the maxDepth at the same time that we perform a depth-first traversal to find the sum of all a_{i}a
i
​
  values (sumOfElements) and the sum of all a_{i} * depth_{i}a
i
​
 ∗depth
i
​
  values (sumOfProducts).

Algorithm

Perform DFS over nestedInteger.
Add the product of integer and its depth into the sumOfProducts, this sum will be equal to \sum_{i=1}^{N} a_i * depth_{i}∑
i=1
N
​
 a
i
​
 ∗depth
i
​
 .
For every integer, compare the depth with maxDepth and update it accordingly.
Add the integer to the sumOfElements. This sum will be equal to \sum_{i=1}^{N} a_{i}∑
i=1
N
​
 a
i
​
 .
Return the value of (maxDepth + 1) * sumOfElements - sumOfProducts.
Implementation


Complexity Analysis

Let NN be the total number of nested elements in the input list.

For example, the list [[[[[1]]]], 2] contains 44 nested lists and 22 nested integers (11 and 22), so NN is 66, for list [[[[1, [2]]]], [3, [4]]] there are 66 nested list and 44 integers, hence NN is 1010.

Time complexity: O(N)O(N)

We perform only one depth-first search. In the DFS, we traverse every element (i.e., nested lists and integers) in the nested list once. Hence the time complexity is O(N)O(N).

Space complexity: O(N)O(N)

Space complexity is equal to the maximum number of active stack calls during the depth-first search. In the worst case, such as [[[[[[1]]]]]], the call stack will use O(N)O(N) space. Hence the space complexity is O(N)O(N).


Approach 3: Single Pass Breadth-First Search (BFS)
Intuition

In the previous approach, we traversed over all the elements of the nestedList in depth-first search manner. We can traverse over the elements in any manner we want as long as we can determine the current depth while traversing elements. Hence, in this approach, we will traverse over nestedList in a breadth-first search manner.

If you're not familiar with BFS, check out our Explore Card

We will use the previously defined equation (maxDepth + 1) * sumOfElements - sumOfProducts to find the answer iteratively. We will traverse over the lists, level by level as shown in the figure below.

fig

Similar to the previous approach we will find the values of sumOfElements, maxDepth, and sumOfProducts while performing a BFS over the integers.

Algorithm

Initialize the first level of the BFS tree by adding all the elements in the input nestedList to the queue.
For each level, pop out the front element from the queue.
If it is a list then add its elements into the queue. Otherwise, update the value of sumOfElements, maxDepth and sumOfProducts.
When the queue becomes empty, return the value of (maxDepth + 1) * sumOfElements - sumOfProducts.
Implementation


Complexity Analysis

Let NN be the total number of nested elements in the input list.

For example, the list [[[[[1]]]], 2] contains 44 nested lists and 22 nested integers (11 and 22), so NN is 66, for list [[[[1, [2]]]], [3, [4]]] there are 66 nested list and 44 integers, hence NN is 1010.

Time complexity: O(N)O(N)

Each nested element is put in the queue and removed from the queue exactly once.

Space complexity: O(N)O(N)

The worst-case for space complexity in BFS occurs where the majority of elements are at the same depth, as all of the elements at that depth will be in the queue at the same time. Therefore worst-case space complexity is O(N)O(N).
     */
    /*
    class Solution1 {
        public int depthSumInverse(List<NestedInteger> nestedList) {
            int maxDepth = findMaxDepth(nestedList);
            return weightedSum(nestedList, 1, maxDepth);
        }

        private int findMaxDepth(List<NestedInteger> list) {
            int maxDepth = 1;

            for (NestedInteger nested : list) {
                if (!nested.isInteger()) {
                    maxDepth = Math.max(maxDepth, 1 + findMaxDepth(nested.getList()));
                }
            }

            return maxDepth;
        }

        private int weightedSum(List<NestedInteger> list, int depth, int maxDepth) {
            int answer = 0;
            for (NestedInteger nested : list) {
                if (nested.isInteger()) {
                    answer += nested.getInteger() * (maxDepth - depth + 1);
                } else {
                    answer += weightedSum(nested.getList(), depth + 1, maxDepth);
                }
            }
            return answer;
        }
    }


    class WeightedSumTriplet {
    int maxDepth;
    int sumOfElements;
    int sumOfProducts;

    WeightedSumTriplet(int maxDepth, int sumOfElements, int sumOfProducts) {
        this.maxDepth = maxDepth;
        this.sumOfElements = sumOfElements;
        this.sumOfProducts = sumOfProducts;
    }
}

class Solution2 {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        WeightedSumTriplet weightedSumTriplet = getWeightedSumTriplet(nestedList, 1);
        int maxDepth = weightedSumTriplet.maxDepth;
        int sumOfElements = weightedSumTriplet.sumOfElements;
        int sumOfProducts = weightedSumTriplet.sumOfProducts;

        return (maxDepth + 1) * sumOfElements - sumOfProducts;
    }

    private WeightedSumTriplet getWeightedSumTriplet(List<NestedInteger> list, int depth) {
        int sumOfProducts = 0;
        int sumOfElements = 0;
        int maxDepth = 0;

        for (NestedInteger nested : list) {
            if (nested.isInteger()) {
                sumOfProducts += nested.getInteger() * depth;
                sumOfElements += nested.getInteger();
                maxDepth = Math.max(maxDepth, depth);
            } else {
                WeightedSumTriplet result = getWeightedSumTriplet(nested.getList(), depth + 1);
                sumOfProducts += result.sumOfProducts;
                sumOfElements += result.sumOfElements;
                maxDepth = Math.max(maxDepth, result.maxDepth);
            }
        }

        return new WeightedSumTriplet(maxDepth, sumOfElements, sumOfProducts);
    }
}


class Solution3 {
    public int depthSumInverse(List<NestedInteger> nestedList) {
        Queue<NestedInteger> Q = new LinkedList<>();
        Q.addAll(nestedList);

        int depth = 1;
        int maxDepth = 0;
        int sumOfElements = 0;
        int sumOfProducts = 0;

        while (!Q.isEmpty()) {
            int size = Q.size();
            maxDepth = Math.max(maxDepth, depth);

            for (int i = 0; i < size; i++) {
                NestedInteger nested = Q.poll();

                if (nested.isInteger()) {
                    sumOfElements += nested.getInteger();
                    sumOfProducts += nested.getInteger() * depth;
                } else {
                    Q.addAll(nested.getList());
                }
            }
            depth++;
        }
        return (maxDepth + 1) * sumOfElements - sumOfProducts;
    }
}



     */

    /*
    //discussion
    //Inspired by lzb700m's solution and one of mine. Instead of multiplying by depth, add integers multiple times (by going level by level and adding the unweighted sum to the weighted sum after each level).
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int unweighted = 0, weighted = 0;
        while (!nestedList.isEmpty()) {
            List<NestedInteger> nextLevel = new ArrayList<>();
            for (NestedInteger ni : nestedList) {
                if (ni.isInteger())
                    unweighted += ni.getInteger();
                else
                    nextLevel.addAll(ni.getList());
            }
            weighted += unweighted;
            nestedList = nextLevel;
        }
        return weighted;
    }

     */

/*
    //https://leetcode.com/problems/nested-list-weight-sum-ii/discuss/83655/JAVA-AC-BFS-solution/275829
    public int depthSumInverse(List<NestedInteger> nestedList) {
        Queue<NestedInteger> q = new LinkedList(nestedList);
        int prevSum = 0, total = 0;
        while(!q.isEmpty()){
            int size = q.size();
            for(int i = 0; i < size; ++i){
                NestedInteger ni = q.poll();
                if(ni.isInteger()){
                    prevSum += ni.getInteger();
                }else{
                    q.addAll(ni.getList());
                }
            }
            total += prevSum;
        }
        return total;
    }

 */

}
