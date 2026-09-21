import java.util.HashMap;

class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(nums[0], 0);

        for(int i =1; i< nums.length;i++){
            if(map.containsKey(nums[i])){
               int num = map.get(nums[i]);
               if(nums[i] == nums[num] && Math.abs(num - i) <= k){
                return true;
               }
            }
                map.put(nums[i], i);
            
        }
        return false;
    }
}