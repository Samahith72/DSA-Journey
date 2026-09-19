import java.util.Arrays;

class Solution {
    public int findContentChildren(int[] g, int[] s) {
        
        Arrays.sort(g);
        Arrays.sort(s);

        int greed = 0;
        int cookies = 0;
        int satisfied = 0;

        while(greed < g.length && cookies < s.length){
            if(s[cookies] >= g[greed]){
                cookies++;
                greed++;
                satisfied++;
            }else{
                cookies++;
            }
        }

        return satisfied;
        
    }
}