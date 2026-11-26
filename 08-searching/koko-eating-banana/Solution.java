class Solution {
    public int minEatingSpeed(int[] piles, int h) {
        int low = 1 ;
        int high = 0;

        for(int pile: piles){
            high = Math.max(high, pile);
        }

        while(low < high){
            int k = low + (high-low)/2;
            int hours = 0;

            for(int pile: piles){
                hours += (pile + k-1)/k;
            }

            if(hours <= h){
                high =k;
            }else{
                low = k+1;
            }
        }

        return low;
    }
}