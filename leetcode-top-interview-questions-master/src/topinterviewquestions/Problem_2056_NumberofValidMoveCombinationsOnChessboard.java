package topinterviewquestions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
n == pieces.length
n == positions.length
1 <= n <= 4
pieces only contains the strings "rook", "queen", and "bishop".
There will be at most one queen on the chessboard.
1 <= xi, yi <= 8
Each positions[i] is distinct.
 */
public class Problem_2056_NumberofValidMoveCombinationsOnChessboard {
    public int countCombinations(String[] pieces, int[][] positions) {
        List<List<int[]>> allTargets = IntStream.range(0, pieces.length).mapToObj(i -> new ArrayList<int[]>()).collect(Collectors.toList());
        for (int i = 0; i < pieces.length; i++) {
            int p[] = positions[i], x = p[0], y = p[1];
            allTargets.get(i).add(p);//add init position as a target
            for (int r = 1; r <= 8; r++)
                for (int c = 1; c <= 8; c++)
                    if (r != x || c != y) {//all but not init position, it is already added
                        if (!pieces[i].equals("rook") && (r + c == x + y || r - c == x - y))//valid target for bishop and queen
                            allTargets.get(i).add(new int[]{r, c});
                        if (!pieces[i].equals("bishop") && (r == x || c == y))//valid target for rook and queen
                            allTargets.get(i).add(new int[]{r, c});
                    }
        }
        return count(0, positions, allTargets, new LinkedList<>());
    }

    int count(int idx, int[][] initPositions, List<List<int[]>> allTargets, LinkedList<int[]> targets) {
        if (idx == initPositions.length)
            return valid(initPositions, targets) ? 1 : 0;
        int count = 0;
        for (int[] position : allTargets.get(idx)) {
            targets.add(position);
            count += count(idx + 1, initPositions, allTargets, targets);
            targets.removeLast();
        }
        return count;
    }

    boolean valid(int[][] initPositions, List<int[]> targets) {
        List<int[]> positions = Arrays.stream(initPositions).map(int[]::clone).collect(Collectors.toList());//deep copy init positions as we are going to move towards targets
        for (boolean keepMoving = true; keepMoving; ) {
            keepMoving = false;
            Set<Integer> used = new HashSet<>();
            for (int i = 0; i < positions.size(); i++) {
                int verticalDirection = targets.get(i)[0] - positions.get(i)[0], horizontalDirection = targets.get(i)[1] - positions.get(i)[1];
                positions.get(i)[0] += Integer.compare(verticalDirection, 0);
                positions.get(i)[1] += Integer.compare(horizontalDirection, 0);
                if (verticalDirection != 0 || horizontalDirection != 0)//target is not reached
                    keepMoving = true;
                if (!used.add(13 * positions.get(i)[0] + positions.get(i)[1]))//collision with another piece
                    return false;
            }
        }
        return true;
    }
}
