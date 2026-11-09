import java.util.Stack;

class Solution {
    public int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int answer = 0;
        int n = heights.length;

        for(int i=0;i<=n;i++){
            int currentHeight = (i == n)? 0: heights[i];

            while(!stack.isEmpty()&& heights[stack.peek()] > currentHeight){
                int height = heights[stack.pop()];

                int width;
                if(stack.isEmpty()){
                    width = i;
                }
                else{
                    width = i - stack.peek()-1;
                }

                int area = height * width;
                answer = Math.max(answer, area);
            }

            stack.push(i);
        }

        return answer;
    }
}