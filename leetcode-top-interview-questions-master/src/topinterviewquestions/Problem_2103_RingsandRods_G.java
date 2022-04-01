package topinterviewquestions;

public class Problem_2103_RingsandRods_G {
    //https://leetcode.com/problems/rings-and-rods/discuss/1624277/Bitmask-Counter/1205322
    //bitmask counter
    //Code colors with bits (1, 2 and 4), and use the OR operation for each rod.
    //Return number of rods that have all 3 bits (colors) set - which value of 7.
    public int countPoints(String rings) {
        int[] rods = new int[10];
        for(int i = 0; i < rings.length() - 1; i += 2){
            if(rings.charAt(i) == 'R')
                rods[rings.charAt(i + 1) - '0'] |= (1 << 0);
            if(rings.charAt(i) == 'G')
                rods[rings.charAt(i + 1) - '0'] |= (1 << 1);
            if(rings.charAt(i) == 'B')
                rods[rings.charAt(i + 1) - '0'] |= (1 << 2);
        }
        int total = 0;
        for(int i = 0; i < rods.length; i++){
            if(rods[i] == 7)//7的二进制是111
                total++;
        }
        return total;
    }
}
