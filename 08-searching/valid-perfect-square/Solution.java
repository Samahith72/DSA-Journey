class Solution {
    public boolean isPerfectSquare(int num) {
        if(num < 2){
            return true;
        }
        int low = 1;
        int high = num /2;
        while(low <= high){
            int mid = low + (high - low)/2;
            long square = (long) mid * mid;

            if(square == num){
                return true;
            }else if(square < num){
                low = mid+1;
            }else{
                high = mid -1;
            }
        }
        return false;
    }
}