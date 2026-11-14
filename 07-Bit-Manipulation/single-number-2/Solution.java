class Solution {
    public int singleNumber(int[] nums) {
        /*int result = 0;

        for(int bit=0;bit < 32; bit++){
            int count = 0;
            for(int num: nums){
                if((num & (1 << bit)) != 0){
                    count++;
                }
            }
            if(count %3 != 0){
                result = result ^ (1 << bit);
            }
        }
        return result;*/

        int ones = 0;
        int twos = 0;

        for(int num: nums){
            ones = (ones ^ num) & ~twos;
            twos = (twos ^ num) & ~ones;
        }

        return ones;
    }
}