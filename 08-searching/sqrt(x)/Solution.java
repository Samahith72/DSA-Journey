class Solution {
    public int mySqrt(int x) {
        int low=0;
        int high = x;

        while(low <= high){
            int mid = low + (high-low)/2;
            long square = (long) mid * mid;

            if(square == x){
                return mid;
            }else if(square < x){
                low = mid +1;
            }else{
                high = mid- 1;
            }
        }

        return high;
    }
}