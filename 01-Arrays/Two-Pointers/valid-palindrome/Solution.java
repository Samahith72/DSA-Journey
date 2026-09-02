class Solution {
    public boolean isPalindrome(String s) {

        if(s.length() == 0 || s.length() == 1){
            return true;
        }

        StringBuilder sb = new StringBuilder();

        for(char c: s.toCharArray()){
            if(Character.isLetterOrDigit(c)){
                sb.append(Character.toLowerCase(c));
            }
        }

        String s1 = sb.toString();

        int left = 0;
        int right = s1.length() -1;

        while(left < right){
            char c1 = s1.charAt(left);
            char c2 = s1.charAt(right);
            if(c1 != c2){
                return false;
            }

            left++;
            right--;
        }

        return true;
        
    }
}