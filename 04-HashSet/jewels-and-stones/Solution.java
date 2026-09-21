import java.util.HashSet;

class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        HashSet<Character> set = new HashSet<>();
        for(char c: jewels.toCharArray()){
            set.add(c);
        }
        int result = 0;
        for(char c: stones.toCharArray()){
            if(set.contains(c)){
                result++;
            }
        }

        return result;
    }
}