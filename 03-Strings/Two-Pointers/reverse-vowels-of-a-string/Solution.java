class Solution {
    public String reverseVowels(String s) {

        char[] answer = s.toCharArray();
        int left = 0;
        int right = s.length()-1;

        while(left < right){
            while(left < right && !isVowel(s.charAt(left))){
                left++;
            }
            while(left < right && !isVowel(s.charAt(right))){
                right--;
            }
            if(left < right){
                swap(answer, left, right);
                left++;
                right--;
            }
        }

        return new String(answer);
        
    }

    private static void swap(char[] c, int left, int right){
        char temp = c[left];
        c[left] = c[right];
        c[right] = temp;
    }


    private static boolean isVowel(char c){
        return c == 'a' ||c == 'e' ||c == 'i' ||c == 'o' ||c == 'u' ||c == 'A' ||c == 'E' ||c == 'I' ||c == 'O' ||c == 'U';
    }
}