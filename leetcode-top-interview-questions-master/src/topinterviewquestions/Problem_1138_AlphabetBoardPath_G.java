package topinterviewquestions;

public class Problem_1138_AlphabetBoardPath_G {
    //https://leetcode.com/problems/alphabet-board-path/discuss/345235/Python-Easy-Solution/313997
    public String alphabetBoardPath(String target) {
        StringBuilder sb=new StringBuilder();
        int x=0, y=0;
        for(int i=0;i<target.length();i++) {
            int c=target.charAt(i)-'a';
            int nx=c/5, ny=c%5;
            if(nx==x&&ny==y) sb.append('!');
            else {
                StringBuilder horizontal=new StringBuilder(), vertical=new StringBuilder();
                char h='L', v='U';
                if(nx>x) v='D';
                if(ny>y) h='R';
                for(int j=0;j<Math.abs(nx-x);j++) vertical.append(v);
                for(int j=0;j<Math.abs(ny-y);j++) horizontal.append(h);
                if(c==25) sb.append(horizontal).append(vertical).append('!');//因为如果c==25说明是'z'那别的地方走到z这个位置就必须先horizontal再vertical走
                else sb.append(vertical).append(horizontal).append('!');
            }
            x=nx;
            y=ny;
        }
        return sb.toString();
    }
}
