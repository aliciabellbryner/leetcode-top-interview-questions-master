package topinterviewquestions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Problem_0631_DesignExcelSumFormula_G {

    //https://leetcode.com/problems/design-excel-sum-formula/discuss/104857/Java-implement-the-logic-in-Cell-class-easy-to-understand/147966
    //O(1) set() operation, only do the traverse in get()/sum() operation, 103ms.
    //Good for set() heavy system.
    class Excel {
        class Cell {
            int val;
            final Map<Cell, Integer> vars = new HashMap<>();//代表相同的val的cell出现的次数

            Cell(int v) {val = v;}

            void add(Cell cell) { vars.put(cell, vars.getOrDefault(cell, 0) + 1);}

            int val() {
                if (vars.isEmpty())
                    return val;
                else {
                    int sum = 0;
                    for (Cell cell : vars.keySet())
                        sum += vars.get(cell) * cell.val();
                    return sum;
                }
            }

            void set(int v) {
                val = v;
                vars.clear();
            }
        }

        final Cell[][] cells;

        public Excel(int H, char W) {
            cells = new Cell[H + 1][W - 'A' + 1];
            for (int i = 0; i < cells.length; i++)
                for (int j = 0; j < cells[0].length; j++)
                    cells[i][j] = new Cell(0);
        }

        public void set(int r, char c, int v) { cells[r][c - 'A'].set(v);}

        public int get(int r, char c) { return cells[r][c - 'A'].val();}

        public int sum(int r, char c, String[] strs) {
            Cell cell = cells[r][c - 'A'];
            cell.set(0);
            for (String s : strs) {
                int k = s.indexOf(':');
                if (k > 0) {
                    int[] start = position(s.substring(0, k)), end = position(s.substring(k + 1));
                    for (int i = start[0]; i <= end[0]; i++)
                        for (int j = start[1]; j <= end[1]; j++)
                            cell.add(cells[i][j]);
                } else {
                    int[] p = position(s);
                    cell.add(cells[p[0]][p[1]]);
                }
            }
            return cell.val();
        }

        int[] position(String s) {
            return new int[]{Integer.parseInt(s.substring(1)), s.charAt(0) - 'A'};
            //substring(begIndex):  the begin index, inclusive.
            //Integer.parseInt(s.substring(1)): 从index=1开始，因为第一个char是letter，这个值是行数
            //s.charAt(0) - 'A'： 这个是列数
            //比如如果s="A1", 则返回[1, 0]; 如果s="F12", 则返回[12, 5]
        }

    }

    public static void main(String[] args) {
        String s = "F12";
        int[] test = new int[]{Integer.parseInt(s.substring(1)), s.charAt(0) - 'A'};
//        System.out.println(Arrays.toString(test));
        System.out.println(Arrays.toString(test));
    }


    //https://leetcode.com/problems/design-excel-sum-formula/solution/
    public class Excel1 {
        Formula[][] Formulas;
        class Formula {
            Formula(HashMap < String, Integer > c, int v) {
                val = v;
                cells = c;
            }
            HashMap < String, Integer > cells;
            int val;
        }
        Stack< int[] > stack = new Stack < > ();
        public Excel1(int H, char W) {
            Formulas = new Formula[H][(W - 'A') + 1];
        }

        public int get(int r, char c) {
            if (Formulas[r - 1][c - 'A'] == null)
                return 0;
            return Formulas[r - 1][c - 'A'].val;
        }
        public void set(int r, char c, int v) {
            Formulas[r - 1][c - 'A'] = new Formula(new HashMap < String, Integer > (), v);
            topologicalSort(r - 1, c - 'A');
            execute_stack();
        }

        public int sum(int r, char c, String[] strs) {
            HashMap < String, Integer > cells = convert(strs);
            int summ = calculate_sum(r - 1, c - 'A', cells);
            set(r, c, summ);
            Formulas[r - 1][c - 'A'] = new Formula(cells, summ);
            return summ;
        }

        public void topologicalSort(int r, int c) {
            for (int i = 0; i < Formulas.length; i++)
                for (int j = 0; j < Formulas[0].length; j++)
                    if (Formulas[i][j] != null && Formulas[i][j].cells.containsKey("" + (char)('A' + c) + (r + 1))) {
                        topologicalSort(i, j);
                    }
            stack.push(new int[] {r,c});
        }

        public void execute_stack() {
            while (!stack.isEmpty()) {
                int[] top = stack.pop();
                if (Formulas[top[0]][top[1]].cells.size() > 0)
                    calculate_sum(top[0], top[1], Formulas[top[0]][top[1]].cells);
            }
        }

        public HashMap< String, Integer > convert(String[] strs) {
            HashMap < String, Integer > res = new HashMap < > ();
            for (String st: strs) {
                if (st.indexOf(":") < 0)
                    res.put(st, res.getOrDefault(st, 0) + 1);
                else {
                    String[] cells = st.split(":");
                    int si = Integer.parseInt(cells[0].substring(1)), ei = Integer.parseInt(cells[1].substring(1));
                    char sj = cells[0].charAt(0), ej = cells[1].charAt(0);
                    for (int i = si; i <= ei; i++) {
                        for (char j = sj; j <= ej; j++) {
                            res.put("" + j + i, res.getOrDefault("" + j + i, 0) + 1);
                        }
                    }
                }
            }
            return res;
        }

        public int calculate_sum(int r, int c, HashMap < String, Integer > cells) {
            int sum = 0;
            for (String s: cells.keySet()) {
                int x = Integer.parseInt(s.substring(1)) - 1, y = s.charAt(0) - 'A';
                sum += (Formulas[x][y] != null ? Formulas[x][y].val : 0) * cells.get(s);
            }
            Formulas[r][c] = new Formula(cells, sum);
            return sum;
        }
    }


}
