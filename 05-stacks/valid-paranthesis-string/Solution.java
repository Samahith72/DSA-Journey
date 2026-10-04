class Solution {
    public boolean checkValidString(String s) {

        int minOpen = 0;
        int maxOpen = 0;

        for(char c: s.toCharArray()){
            if(c == '('){
                minOpen++;
                maxOpen++;
            }else if(c == ')'){
                minOpen--;
                maxOpen--;
            }else{
                minOpen--;
                maxOpen++;
            }
            minOpen = Math.max(0, minOpen);
            
            if(maxOpen < 0){
                return false;
            }
        }
        
        
        return minOpen == 0;
    }
}