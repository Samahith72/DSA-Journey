import java.util.Stack;

class Solution {

    public int[] finalPrices(int[] prices) {
        int n = prices.length;
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[n];

        ans[n - 1] = prices[n - 1];
        stack.push(prices[n - 1]);

        for (int i = n - 2; i >= 0; i--) {
            while (!stack.isEmpty() && stack.peek() > prices[i]) {
                stack.pop();
            }

            if (!stack.isEmpty()) {
                ans[i] = prices[i] - stack.peek();
            }
            else {
                ans[i] = prices[i];
            }

            stack.push(prices[i]);
        }

        return ans;
    }
}