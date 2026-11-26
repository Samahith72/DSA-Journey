class Solution {
    public int shipWithinDays(int[] weights, int days) {
        int high = 0;
        int low = 0;

        for(int i: weights){
            low = Math.max(low, i);
            high += i;
        }

        while(low < high){
            int capacity = low + (high - low)/2;
            int daysNeeded = 1;
            int currentLoad = 0;

            for(int i: weights){
                if(currentLoad + i > capacity){
                    daysNeeded++;
                    currentLoad = 0;
                }

                currentLoad += i;
            }

            if(daysNeeded <= days){
                high = capacity;
            }else{
                low = capacity+1;
            }
        }

        return low;
    }

}