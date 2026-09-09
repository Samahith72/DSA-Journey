import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Solution {
    public int[] intersect(int[] nums1, int[] nums2) {

        HashMap<Integer, Integer> frequency = new HashMap<>();

        for(int num : nums1){
            frequency.put(num, frequency.getOrDefault(num,0)+1);
        }


        List<Integer> list = new ArrayList<>();

        for(int num: nums2){
            if(frequency.containsKey(num) && frequency.get(num) > 0){
                list.add(num);
                frequency.put(num, frequency.get(num) -1);
            }
        }

        int[] answer = new int[list.size()];

        for(int i=0;i< list.size();i++){
            answer[i] = list.get(i);
        }


        return answer;
    }
}