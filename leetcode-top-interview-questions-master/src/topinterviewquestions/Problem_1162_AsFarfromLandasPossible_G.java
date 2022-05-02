package topinterviewquestions;

import java.util.LinkedList;
import java.util.Queue;

public class Problem_1162_AsFarfromLandasPossible_G {
    //bfs
    //https://leetcode.com/problems/as-far-from-land-as-possible/discuss/360963/C++-with-picture-DFS-and-BFS/328355
    public int maxDistance(int[][] grid) {
        int res=0, N=grid.length;
        int[][] dir={{0,1}, {0,-1}, {1,0}, {-1,0}};
        Queue<Integer> q=new LinkedList<>();
        for(int i=0;i<N;i++) {
            for(int j=0;j<N;j++) {
                if(grid[i][j]==1) q.offer(i*N+j);
            }
        }
        if(q.size()==N*N||q.size()==0) return -1;
        while(!q.isEmpty()) {
            int size=q.size();
            for(int i=0;i<size;i++) {
                int cur=q.poll(), x=cur/N, y=cur%N;
                for(int j=0;j<4;j++) {
                    int newx=x+dir[j][0], newy=y+dir[j][1];
                    if(newx<0||newx>=N||newy<0||newy>=N||grid[newx][newy]==1) continue;
                    q.offer(newx*N+newy);
                    grid[newx][newy]=1;
                }
            }
            if(!q.isEmpty()) res++;
        }
        return res;
    }
}
