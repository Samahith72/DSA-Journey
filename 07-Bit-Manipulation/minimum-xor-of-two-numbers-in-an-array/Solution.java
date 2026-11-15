import java.util.HashSet;

class Solution {
    public int findMaximumXOR(int[] nums) {

        int maxXor = 0;
        int mask = 0;

        for(int bit = 30; bit >= 0;bit--){
            HashSet<Integer> prefixes = new HashSet<>();
            mask = mask | (1 << bit);

            for(int num: nums){
                int prefix = num & mask;
                prefixes.add(prefix);
            }

            int candidate = maxXor | (1 << bit);

            for(int prefix: prefixes){
                int required = prefix ^ candidate;

                if(prefixes.contains(required)){
                    maxXor = candidate;
                    break;
                }
            }
        }

        return maxXor;

       /* int max = 0;
        for(int i=0;i < nums.length-1;i++){
            for(int j=i+1;j < nums.length;j++){
                int xor = nums[i] ^ nums[j];
                max = Math.max(max, xor);
            }
        }
        return max;
        */
    }
}