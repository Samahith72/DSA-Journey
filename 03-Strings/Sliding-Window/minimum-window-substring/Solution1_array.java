class Solution {
    public String minWindow(String s, String t) {
        
        int[] frequency = new int[128];
        for(char c: t.toCharArray()){
            frequency[c]++;
        }
        int left= 0;
        int right =0;
        int required =  0;
        for(int i=0;i < 128; i++){
            if(frequency[i] > 0)
                required++;
        }
        int formed = 0;
        int bestLength = Integer.MAX_VALUE;
        int bestStart = 0;
        int[] window = new int[128];
        while(right < s.length()){
            char c = s.charAt(right);
            window[c]++;
            if(frequency[c] > 0 && window[c] == frequency[c]){
                    formed++;
             }

            while(formed == required){
                int currentWindowLength = right - left +1;
                if(currentWindowLength < bestLength){
                    bestLength = currentWindowLength;
                    bestStart = left;
                }

                char leftChar = s.charAt(left);
                
                window[leftChar]--;

                if(frequency[leftChar] > 0 && window[leftChar] < frequency[leftChar]){
                    formed--;
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