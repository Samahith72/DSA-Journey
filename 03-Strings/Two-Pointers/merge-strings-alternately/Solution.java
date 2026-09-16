class Solution {
    public String mergeAlternately(String word1, String word2) {
        char[] c1 = word1.toCharArray();
        char[] c2 = word2.toCharArray();

        StringBuilder sb = new StringBuilder();
        int ptr1 =0;
        int ptr2 = 0;

        while(ptr1 <= word1.length()-1 && ptr2 <= word2.length()-1){
            if(ptr1 <= word1.length()-1){
                sb.append(c1[ptr1]);
                ptr1++;
            }
            if(ptr2 <= word2.length()-1){
                sb.append(c2[ptr2]);
                ptr2++;
            }

            
        }
        if(ptr1 != word1.length()){
            while(ptr1 <= word1.length() -1){
                sb.append(c1[ptr1]);
                ptr1++;
            }
        }
        if(ptr2 != word2.length()){
            while(ptr2 <= word2.length() -1){
                sb.append(c2[ptr2]);
                ptr2++;
            }
        }

        return sb.toString();
    }
}