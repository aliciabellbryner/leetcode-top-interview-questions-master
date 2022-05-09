package topinterviewquestions;

import java.util.*;

public class Problem_1263_MinimumMovestoMoveaBoxtoTheirTargetLocation_G {

    //https://leetcode.com/problems/minimum-moves-to-move-a-box-to-their-target-location/discuss/431431/Java-straightforward-BFS-solution
    //For me, this is a typical bfs question to find the minimum step, so the key point is to find out the state. For this problem, I choose the coordinates of box and storekeeper as the state. Because the grid length is from 1 to 20, we can use one 32 bit integer to present all the coordinates.
    //Every step, move storekeeper 1 step, if it meet the box, then the box move 1 step in the same direction. Because it want to know the minimum step the box move, so when the first time box on the target pocision, it may not be the minimum anwser. So we compare every one and find the minimum step.
    class Solution {
        int[][] moves = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};
        public int minPushBox(char[][] grid) {
            int[] box = null, target = null, storekeeper = null;
            int n = grid.length, m = grid[0].length;
            for (int i = 0; i < n; i++) for (int j = 0; j < m; j++) {
                if (grid[i][j] == 'B') box = new int[]{i, j};
                else if (grid[i][j] == 'T') target = new int[]{i, j};
                else if (grid[i][j] == 'S') storekeeper = new int[]{i, j};
            }
            Queue<Integer> q = new LinkedList<>();
            Map<Integer, Integer> dis = new HashMap<>();
            int start = encode(box[0], box[1], storekeeper[0], storekeeper[1]);
            dis.put(start, 0);
            q.offer(start);
            int ret = Integer.MAX_VALUE;
            while (!q.isEmpty()) {
                int u = q.poll();
                int[] du = decode(u);
                if (dis.get(u) >= ret) continue;
                if (du[0] == target[0] && du[1] == target[1]) {
                    ret = Math.min(ret, dis.get(u));
                    continue;
                }
                int[] b = new int[]{du[0], du[1]};
                int[] s = new int[]{du[2], du[3]};
                // move the storekeeper for 1 step
                for (int[] move : moves) {
                    int nsx = s[0] + move[0];
                    int nsy = s[1] + move[1];
                    if (nsx < 0 || nsx >= n || nsy < 0 || nsy >= m || grid[nsx][nsy] == '#') continue;
                    // if it meet the box, then the box move in the same direction
                    if (nsx == b[0] && nsy == b[1]) {
                        int nbx = b[0] + move[0];
                        int nby = b[1] + move[1];
                        if (nbx < 0 || nbx >= n || nby < 0 || nby >= m || grid[nbx][nby] == '#') continue;
                        int v = encode(nbx, nby, nsx, nsy);
                        if (dis.containsKey(v) && dis.get(v) <= dis.get(u) + 1) continue;
                        dis.put(v, dis.get(u) + 1);
                        q.offer(v);
                    } else { // if the storekeeper doesn't meet the box, the position of the box do not change
                        int v = encode(b[0], b[1], nsx, nsy);
                        if (dis.containsKey(v) && dis.get(v) <= dis.get(u)) continue;
                        dis.put(v, dis.get(u));
                        q.offer(v);
                    }
                }
            }
            return ret == Integer.MAX_VALUE ? -1 : ret;
        }
        int encode(int bx, int by, int sx, int sy) {
            return (bx << 24) | (by << 16) | (sx << 8) | sy;
        }
        int[] decode(int num) {
            int[] ret = new int[4];
            ret[0] = (num >>> 24) & 0xff;
            ret[1] = (num >>> 16) & 0xff;
            ret[2] = (num >>> 8) & 0xff;
            ret[3] = num & 0xff;
            return ret;
        }
    }


    //BFS：
    //BFS 每走一步，会把下一步能走的格子加入队列
    //按顺序走完这一步所有的可能，队列里就剩下下一步所有的可能
    //当走到终点时，就是最少步骤
    //每走一步，还会使用 set 来记录已经走到过的格子的坐标
    //把下一步加入队列前，检查，重复的就不用进队列了
    //分析：
    //如果这道题没有人，箱子自己走，那么只需要了解BFS，就可以解决了
    //如果有人存在，把人的状态同时记录下来
    //当人走到箱子的格子上，把箱子沿同一方向移动，如果箱子位置合法，那就是推动箱子了
    //
    //作者：ikaruga
    //链接：https://leetcode-cn.com/problems/minimum-moves-to-move-a-box-to-their-target-location/solution/1263-by-ikaruga/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution2 {
        public int minPushBox(char[][] grid) {

            int rlen = grid.length;
            int llen = grid[0].length;
            //O(MN) 查找 箱子和人物的位置
            int px = 0, py = 0, bx = 0, by = 0;
            for(int i = 0; i < rlen; i++){
                for(int j = 0; j < llen; j++){
                    if(grid[i][j] == 'S'){
                        px = i;
                        py = j;
                    }
                    if(grid[i][j] == 'B'){
                        bx = i;
                        by = j;
                    }
                }
            }

            //visited[i][j][m][n] = true 表示 人物在 (i, j) 坐标和 箱子在 (m, n) 坐标 这个状态已经访问过了
            boolean[][][][] visited = new boolean[rlen][llen][rlen][llen];
        /*
        当人物在箱子的左边时，人物可以选择向右边走
        当人物在箱子的右边时，人物可以选择向左边走
        当人物在箱子的上边时，人物可以选择向下边走
        当人物在箱子的下边时，人物可以选择向上边走
        这样才能保证步数最少，否则，如果箱子在左边，人物还向着右边走，那么就距离箱子越来越远，这是毫无意义的步数

        无法满足条件的情况：
        如果箱子会自己走的话，那么简单的 bfs 就能够完成了，但是这里需要人物来推动箱子
        这意味着箱子可能虽然旁边就是终点，但是可能不存在能够容纳人物的地方来推动箱子
        比如 下图，虽然 箱子 B 旁边就是终点 T，如果它能够自己走的话直接一步到终点
        但是由于需要推动，而却不存在容纳人物 S 的位置来 箱子 B 到达终点 T
        # # # #
        # T B #
        # . S #

        什么时候箱子的位置会发生改变？
        当人物向上下两个方向走的时候，如果人物的下一个位置就是箱子的位置，
        那么相当于顶着箱子前进，那么箱子同时也要往前进

        因为人物的移动不算在步数里的，因此可能移动的时候当前箱子推的步数很大，比如示例 1 ，人物绕了一大圈
        如果最优的情况，即最少的推箱子步数就是这绕一大圈的，但是别的状态还在推箱子，它能够更快的到达终点，但是推箱子步数很大
        所以最先碰到终点的不一定是步数最少的，所以需要使用一个优先队列
        */
            int[][] pos = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            PriorityQueue<Node> queue = new PriorityQueue<>((a, b) -> a.step - b.step);
            queue.add(new Node(px, py, bx, by, 0));
            while(!queue.isEmpty()){
                int size = queue.size();
                while(size-- > 0){
                    Node node = queue.poll();
                    if(grid[node.bx][node.by] == 'T'){
                        return node.step;
                    }
                    //往四个方向走
                    for(int[] p : pos){
                        int newPx = node.px + p[0];
                        int newPy = node.py + p[1];
                        int newBx = node.bx;
                        int newBy = node.by;
                        int newStep = node.step;
                        //人物的前进位置刚好是箱子的位置，那么箱子的位置也要发生改变
                        if(newPx == node.bx && newPy == node.by){
                            newBx += p[0];
                            newBy += p[1];
                            //箱子动了，步数 +1
                            newStep++;
                        }
                        //越界或者在障碍物上，那么跳过
                        if(newPx < 0 || newPx == rlen || newPy < 0 || newPy == llen
                                || newBx < 0 || newBx == rlen || newBy < 0 || newBy == llen
                                || grid[newPx][newPy] == '#' || grid[newBx][newBy] == '#'){
                            continue;
                        }
                        if(!visited[newPx][newPy][newBx][newBy]){
                            visited[newPx][newPy][newBx][newBy] = true;
                            queue.add(new Node(newPx, newPy, newBx, newBy, newStep));
                        }
                    }
                }
            }
            return -1;
        }
        class Node{
            //人物坐标
            int px;
            int py;
            //箱子坐标
            int bx;
            int by;
            /*
            当前状态的步数，这里需要该变量是因为人物走动无需计算步数，而无意义的推箱子却可能计算步数，
            为了防止无意义的推箱子导致作为结果的步数增加，因此每个状态都需要存储各自的步数
            */
            int step;
            public Node(int px, int py, int bx, int by, int step){
                this.px = px;
                this.py = py;
                this.bx = bx;
                this.by = by;
                this.step = step;
            }
        }
    }
}
