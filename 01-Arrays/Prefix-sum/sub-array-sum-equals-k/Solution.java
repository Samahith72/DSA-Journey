class Solution {
    public int subarraySum(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,1);

        int sum = 0;
        int count = 0;

        for(int num: nums){
            sum += num;
            int need = sum - k;

            if(map.containsKey(need)){
                count += map.get(need);
            }
            map.put(sum, map.getOrDefault(sum, 0) + 1);
            
        }
        return count;
    }
}