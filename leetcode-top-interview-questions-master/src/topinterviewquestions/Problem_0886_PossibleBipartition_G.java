package topinterviewquestions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Problem_0886_PossibleBipartition_G {
    //https://leetcode-cn.com/problems/possible-bipartition/solution/ke-neng-de-er-fen-fa-by-leetcode/
    //方法：深度优先搜索
    //思路
    //
    //尝试将每个人分配到一个组是很自然的想法。假设第一组中的人是红色，第二组中的人是蓝色。
    //
    //如果第一个人是红色的，那么这个人不喜欢的人必须是蓝色的。然后，任何不喜欢蓝色人的人都是红色的，那么任何不喜欢红色人的人都是蓝色的，依此类推。
    //
    //如果在任何时候存在冲突，那么这个任务是不可能的完成的，因为从第一步开始每一步都符合逻辑。如果没有冲突，那么着色是有效的，所以答案是 true。
    //
    //算法
    //
    //考虑由给定的 “不喜欢” 边缘形成的 N 人的图表。我们要检查这个图的每个连通分支是否为二分的。
    //
    //对于每个连通的部分，我们只需试着用两种颜色对它进行着色，就可以检查它是否是二分的。如何做到这一点：将任一结点涂成红色，然后将它的所有邻居都涂成蓝色，然后将所有的邻居的邻居都涂成红色，以此类推。如果我们将一个红色结点涂成蓝色（或蓝色结点涂成红色），那么就会产生冲突。
    //
    //作者：LeetCode
    //链接：https://leetcode-cn.com/problems/possible-bipartition/solution/ke-neng-de-er-fen-fa-by-leetcode/
    //来源：力扣（LeetCode）
    //著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    class Solution {
        ArrayList<Integer>[] graph;// 使用邻接表存储图
        Map<Integer,Integer> color;//记录上色结果
        public boolean possibleBipartition(int N, int[][] dislikes) {
            graph=new ArrayList[N+1];// 0位其实不用,使用的使1~N位
            //ArrayList实例化
            for (int i = 0; i !=N+1; i++) {
                graph[i]=new ArrayList<Integer>();
            }
            //图初始化
            for(int[] cp:dislikes) {
                graph[cp[0]].add(cp[1]);
                graph[cp[1]].add(cp[0]);
            }
            color=new HashMap();
            for(int node=1;node!=N+1;node++) {// 对该组N人遍历
                if(!color.containsKey(node)) {// 还未上色
                    boolean OK=dfs(node,0);//从node开始深度遍历
                    if(!OK) return false;
                }else continue;//已经上色
            }
            return true;
        }
        private boolean dfs(int node, int c) {
            //从possibleBipartition调用时node是未上色的
            if(color.containsKey(node)) {// 若已经上色则看是否上色正确
                boolean OK=color.get(node)==c;
                return OK;
            }
            color.put(node,c);// 上色
            // 深度遍历
            for(int noFriend:graph[node]) {
                boolean OK=dfs(noFriend,c^1);
                if(!OK) return false;
            }
            return true;
        }
    }
}
