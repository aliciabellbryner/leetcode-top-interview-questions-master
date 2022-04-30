package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_0909_SnakesandLadders_G {
    //方法一：广度优先搜索
    //我们可以将棋盘抽象成一个包含 N^2N
    //2
    //  个节点的有向图，对于每个节点 xx，若 x+i\ (1\le i \le 6)x+i (1≤i≤6) 上没有蛇或梯子，则连一条从 xx 到 x+ix+i 的有向边；否则记蛇梯的目的地为 yy，连一条从 xx 到 yy 的有向边。
    //
    //如此转换后，原问题等价于在这张有向图上求出从 11 到 N^2N
    //2
    //  的最短路长度。
    //
    //对于该问题，我们可以使用广度优先搜索。将节点编号和到达该节点的移动次数作为搜索状态，顺着该节点的出边扩展新状态，直至到达终点 N^2N
    //2
    // ，返回此时的移动次数。若无法到达终点则返回 -1−1。
    //
    //代码实现时，我们可以用一个队列来存储搜索状态，初始时将起点状态 (1,0)(1,0) 加入队列，表示当前位于起点 11，移动次数为 00。然后不断取出队首，每次取出队首元素时扩展新状态，即遍历该节点的出边，若出边对应节点未被访问，则将该节点和移动次数加一的结果作为新状态，加入队列。如此循环直至到达终点或队列为空。
    //
    //此外，我们需要计算出编号在棋盘中的对应行列，以便从 \textit{board}board 中得到目的地。设编号为 \textit{id}id，由于每行有 nn 个数字，其位于棋盘从下往上数的第 \left \lfloor \dfrac{\textit{id}-1}{n} \right \rfloor⌊
    //n
    //id−1
    //​
    // ⌋ 行，记作 rr。由于棋盘的每一行会交替方向，若 rr 为偶数，则编号方向从左向右，列号为 (\textit{id}-1) \bmod n(id−1)modn；若 rr 为奇数，则编号方向从右向左，列号为 n-1-((\textit{id}-1) \bmod n)n−1−((id−1)modn)。
    //
    //作者：LeetCode-Solution
    //链接：https://leetcode-cn.com/problems/snakes-and-ladders/solution/she-ti-qi-by-leetcode-solution-w0vl/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        public int snakesAndLadders(int[][] board) {
            int n = board.length;
            boolean[] visited = new boolean[n * n + 1];
            Queue<int[]> queue = new LinkedList<int[]>();
            queue.offer(new int[]{1, 0});
            while (!queue.isEmpty()) {
                int[] p = queue.poll();
                for (int i = 1; i <= 6; ++i) {
                    int nxt = p[0] + i;
                    if (nxt > n * n) { // 超出边界
                        break;
                    }
                    int[] rc = id2rc(nxt, n); // 得到下一步的行列
                    if (board[rc[0]][rc[1]] > 0) { // 存在蛇或梯子
                        nxt = board[rc[0]][rc[1]];
                    }
                    if (nxt == n * n) { // 到达终点
                        return p[1] + 1;
                    }
                    if (!visited[nxt]) {
                        visited[nxt] = true;
                        queue.offer(new int[]{nxt, p[1] + 1}); // 扩展新状态
                    }
                }
            }
            return -1;
        }

        public int[] id2rc(int id, int n) {
            int r = (id - 1) / n, c = (id - 1) % n;
            if (r % 2 == 1) {
                c = n - 1 - c;
            }
            return new int[]{n - 1 - r, c};
        }
    }

}
