package topinterviewquestions;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class Problem_0729_MyCalendarI_G {

    //https://leetcode.com/problems/my-calendar-i/discuss/109475/JavaC%2B%2B-Clean-Code-with-Explanation
    class MyCalendar {
        TreeSet<int[]> books = new TreeSet<int[]>((int[] a, int[] b) -> a[0] - b[0]);

        public boolean book(int s, int e) {
            int[] book = new int[] { s, e }, floor = books.floor(book), ceiling = books.ceiling(book);
            if (floor != null && s < floor[1]) return false; // (s, e) start within floor
            if (ceiling != null && ceiling[0] < e) return false; // ceiling start within (s, e)
            books.add(book);
            return true;
        }
    }

    //
    class MyCalendar2 {
        private List<int[]> books = new ArrayList<>();
        public boolean book(int start, int end) {
            for (int[] b : books)
                if (Math.max(b[0], start) < Math.min(b[1], end)) return false;
            books.add(new int[]{ start, end });
            return true;
        }
    }
}
