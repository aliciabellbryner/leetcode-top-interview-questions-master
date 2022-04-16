package topinterviewquestions;

public class Problem_0388_LongestAbsoluteFilePath_G {
    public int lengthLongestPath(String input) {
        int maxlen = 0;
        int[] pathlen = new int[input.length()+1];
        String[] st = input.split("\n");
        for(String line: st){
            String name = line.replaceAll("(\t)+","");
            int depth = line.length() - name.length();
            if(name.contains("."))
                maxlen = Math.max(maxlen, pathlen[depth] + name.length());
            else
                pathlen[depth+1] = pathlen[depth] + name.length() + 1;
        }

        return maxlen;
    }
}
