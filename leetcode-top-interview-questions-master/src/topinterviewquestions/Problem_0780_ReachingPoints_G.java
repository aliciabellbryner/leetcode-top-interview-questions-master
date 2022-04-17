package topinterviewquestions;

public class Problem_0780_ReachingPoints_G {
    //https://leetcode.com/problems/reaching-points/discuss/114856/JavaC%2B%2BPython-Modulo-from-the-End
    //Basic idea
    //If we start from sx,sy, it will be hard to find tx, ty.
    //If we start from tx,ty, we can find only one path to go back to sx, sy.
    //I cut down one by one at first and I got TLE. So I came up with remainder.
    //
    //First line
    //if 2 target points are still bigger than 2 starting point, we reduce target points.
    //Second line:
    //check if we reduce target points to (x, y+kx) or (x+ky, y)
    //
    //Complexity
    //Space O(1)
    //About time, O(log(min(tx,ty))).
    //More details Exercise 31.2-5
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (sx < tx && sy < ty) {//if 2 target points are still bigger than 2 starting point, we reduce target points.
            if (tx < ty) ty %= tx;
            else tx %= ty;
        }
        //check if we reduce target points to (x, y+kx) or (x+ky, y)
        return sx == tx && sy <= ty && (ty - sy) % sx == 0 || sy == ty && sx <= tx && (tx - sx) % sy == 0;
    }
}
