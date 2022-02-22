package topinterviewquestions;
/*
You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements may also be integers or other lists.

The depth of an integer is the number of lists that it is inside of. For example, the nested list [1,[2,2],[[3],2],1] has each integer's value set to its depth.

Return the sum of each integer in nestedList multiplied by its depth.



Example 1:


Input: nestedList = [[1,1],2,[1,1]]
Output: 10
Explanation: Four 1's at depth 2, one 2 at depth 1. 1*2 + 1*2 + 2*1 + 1*2 + 1*2 = 10.
Example 2:


Input: nestedList = [1,[4,[6]]]
Output: 27
Explanation: One 1 at depth 1, one 4 at depth 2, and one 6 at depth 3. 1*1 + 4*2 + 6*3 = 27.
Example 3:

Input: nestedList = [0]
Output: 0


Constraints:

1 <= nestedList.length <= 50
The values of the integers in the nested list is in the range [-100, 100].
The maximum depth of any integer is less than or equal to 50.
 */
public class Problem_0339_NestedListWeightSum {
    /*
    Approach 1: Depth-first Search
Because the input is nested, it is natural to think about the problem in a recursive way. We go through the list of nested integers one by one, keeping track of the current depth dd. If a nested integer is an integer, nn, we calculate its sum as n\times dn×d. If the nested integer is a list, we calculate the sum of this list recursively using the same process but with depth equals d + 1d+1.

Implementation


Complexity Analysis

Let NN be the total number of nested elements in the input list. For example, the list [ [[[[1]]]], 2 ] contains 44 nested lists and 22 nested integers (11 and 22), so N = 6N=6 for that particular case.

Time complexity : \mathcal{O}(N)O(N).

Recursive functions can be a bit tricky to analyze, particularly when their implementation includes a loop. A good strategy is to start by determining how many times the recursive function is called, and then how many times the loop will iterate across all calls to the recursive function.

The recursive function, dfs(...) is called exactly once for each nested list. As NN also includes nested integers, we know that the number of recursive calls has to be less than NN.

On each nested list, it iterates over all of the nested elements directly inside that list (in other words, not nested further). As each nested element can only be directly inside one list, we know that there must only be one loop iteration for each nested element. This is a total of NN loop iterations.

So combined, we are performing at most 2 \cdot N2⋅N recursive calls and loop iterations. We drop the 22 as it is a constant, leaving us with time complexity \mathcal{O}(N)O(N).

Space complexity : \mathcal{O}(N)O(N).

In terms of space, at most O(D)O(D) recursive calls are placed on the stack, where DD is the maximum level of nesting in the input. For example, D=2D=2 for the input [[1,1],2,[1,1]], and D=3D=3 for the input [1,[4,[6]]].

In the worst case, D = ND=N, (e.g. the list [[[[[[]]]]]]) so the worst-case space complexity is O(N)O(N).


Approach 2: Breadth-first Search
We can also solve the problem using a breadth-first search. The algorithm for this is closely based on the standard breadth-first search template. The algorithm fully processes each depth before moving to the next one.

Implementation


Complexity Analysis

Time complexity : \mathcal{O}(N)O(N).

Similar to the DFS approach. Each nested element is put on the queue and removed from the queue exactly once.

Space complexity : \mathcal{O}(N)O(N).

The worst-case for space complexity in BFS occurs where most of the elements are in a single layer, for example, a flat list such as [1, 2, 3, 4, 5] as all of the elements must be put on the queue at the same time. Therefore, this approach also has a worst-case space complexity of \mathcal{O}(N)O(N).
     */

    /*
    class Solution1 {

        public int depthSum(List<NestedInteger> nestedList) {
            return dfs(nestedList, 1);
        }

        private int dfs(List<NestedInteger> list, int depth) {
            int total = 0;
            for (NestedInteger nested : list) {
                if (nested.isInteger()) {
                    total += nested.getInteger() * depth;
                } else {
                    total += dfs(nested.getList(), depth + 1);
                }
            }
            return total;
        }
    }

    class Solution2 {
    public int depthSum(List<NestedInteger> nestedList) {
        Queue<NestedInteger> queue = new LinkedList<>();
        queue.addAll(nestedList);

        int depth = 1;
        int total = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                NestedInteger nested = queue.poll();
                if (nested.isInteger()) {
                    total += nested.getInteger() * depth;
                } else {
                    queue.addAll(nested.getList());
                }
            }
            depth++;
        }
        return total;
    }
}


     */
}
