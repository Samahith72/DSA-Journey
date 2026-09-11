import java.util.HashMap;

class Solution {
    public String minWindow(String s, String t) {
        
        HashMap<Character, Integer> frequency = new HashMap<>();
        for(char c: t.toCharArray()){
            frequency.put(c, frequency.getOrDefault(c,0)+1);
        }

        HashMap<Character, Integer> window = new HashMap<>();
        int left= 0;
        int right =0;
        int required = frequency.size();
        int formed = 0;

        int bestLength = Integer.MAX_VALUE;
        int bestStart = 0;


        while(right != s.length()){
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0)+1 );

            if(frequency.containsKey(c)){
                if(window.get(c).equals(frequency.get(c))){
                    formed++;
                }
            }

            while(formed == required){
                int currentWindowLength = right - left +1;
                if(currentWindowLength < bestLength){
                    bestLength = currentWindowLength;
                    bestStart = left;
                }

                char leftChar = s.charAt(left);
                
                window.put(leftChar, window.get(leftChar)-1);

                if(frequency.containsKey(leftChar)){
                    if(window.get(leftChar) < frequency.get(leftChar)){
                        formed--;
                    }
                }
                left++;
            }
            right++;
        }

        if(bestLength == Integer.MAX_VALUE){
            return "";
        }

        return s.substring(bestStart, bestStart + bestLength);
    }
}