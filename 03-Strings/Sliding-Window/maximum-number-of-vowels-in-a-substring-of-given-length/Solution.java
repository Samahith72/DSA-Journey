class Solution {
    public int maxVowels(String s, int k) {

        int right =0;
        int vowelCount = 0;

        while(right < k){
            if(isVowel(s.charAt(right))){
                vowelCount++;
            }
            right++;
        }

        int answer = vowelCount;
        
        while(right < s.length()){
            if(isVowel(s.charAt(right))){
                vowelCount++;
            }

            if(isVowel(s.charAt(right -k))){
                vowelCount--;
            }

            answer = Math.max(answer, vowelCount);
            right++;
        }

        return answer;
        
    }

    private boolean isVowel(char c){
        if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ){
            return true;
        }
        return false;
    }
}