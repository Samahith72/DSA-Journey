class Solution {
    public int maxProfit(int[] prices) {

        int result = 0;
        int minimum = Integer.MAX_VALUE;

        for(int i =0 ;i< prices.length; i++){
            if(minimum > prices[i]){
                minimum = prices[i];
            }

            if(result < prices[i] - minimum){
                result = prices[i] -minimum;
            }
        }

        return result;
        
    }
}