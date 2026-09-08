class Solution {
    public int removeElement(int[] nums, int val) {
        int tracePtr =0;
        int countPtr =0;
        int count = 0;

        while(tracePtr < nums.length){
            if(nums[tracePtr] != val){
                nums[countPtr] = nums[tracePtr];
                count++;
                countPtr++;
                tracePtr++;
            }else{
                tracePtr++;
            }
        }


        return count;
        
    }
}