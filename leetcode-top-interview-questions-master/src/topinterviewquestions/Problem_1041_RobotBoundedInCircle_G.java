package topinterviewquestions;

public class Problem_1041_RobotBoundedInCircle_G {
    //https://leetcode.com/problems/robot-bounded-in-circle/discuss/290856/JavaC%2B%2BPython-Let-Chopper-Help-Explain
    //Intuition
    //Let chopper help explain.
    //
    //Starting at the origin and face north (0,1),
    //after one sequence of instructions,
    //
    //if chopper return to the origin, he is obvious in an circle.
    //if chopper finishes with face not towards north,
    //it will get back to the initial status in another one or three sequences.
    //
    //Explanation
    //(x,y) is the location of chopper.
    //d[i] is the direction he is facing.
    //i = (i + 1) % 4 will turn right
    //i = (i + 3) % 4 will turn left
    //Check the final status after instructions.
    //Complexity
    //Time O(N)
    //Space O(1)
    public boolean isRobotBounded(String ins) {
        int x = 0, y = 0, i = 0, d[][] = {{0, 1}, {1, 0}, {0, -1}, { -1, 0}};
        for (int j = 0; j < ins.length(); ++j)
            if (ins.charAt(j) == 'R')
                i = (i + 1) % 4;
            else if (ins.charAt(j) == 'L')
                i = (i + 3) % 4;
            else {
                x += d[i][0]; y += d[i][1];
            }
        return x == 0 && y == 0 || i > 0;
    }
}
