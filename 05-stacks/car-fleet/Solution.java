import java.util.Arrays;
import java.util.Stack;

class Solution {
    public int carFleet(int target, int[] position, int[] speed) {

        Stack<Double> fleet = new Stack<>();
        int n = position.length;

        Integer[] indices = new Integer[n];
        for(int i=0;i<n;i++){
            indices[i] = i;
        }

        Arrays.sort(indices,(a,b) -> Integer.compare(position[b], position[a]));

        for(int i=0;i < n;i++){
            int idx = indices[i];
            double arrivalTime =(double) (target - position[idx])/speed[idx];
            if(fleet.isEmpty()){
                fleet.push(arrivalTime);
                continue;
            } 

            if(arrivalTime > fleet.peek()){
                fleet.push(arrivalTime);
            }
        }
        return fleet.size();
        
    }
}