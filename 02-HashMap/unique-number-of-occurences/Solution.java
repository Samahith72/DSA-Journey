import java.util.HashMap;
import java.util.HashSet;

class Solution {
    public boolean uniqueOccurrences(int[] arr) {

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int num : arr){
            map.put(num, map.getOrDefault(num,0)+1);
        }

        HashSet<Integer> set = new HashSet<>();
        for(Integer value: map.values()){
            if(set.contains(value)){
                return false;
            }

            set.add(value);
        }
        return true;
    }
}