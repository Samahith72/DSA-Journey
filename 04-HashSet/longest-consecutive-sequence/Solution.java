import java.util.HashSet;

class Solution {
    public int longestConsecutive(int[] nums) {

        HashSet< Integer > set = new HashSet<>();

        for(int num: nums){
            set.add(num);
        }
        int answer = 0;
        
        for(Integer num: set){
            if(!set.contains(num-1)){
                int current =num;
                int length =1;

                while(set.contains(current+1)){
                    length++;
                    current++;
                }
                answer = Math.max(answer, length);
            }
        }

        return answer;
        
    }
}