class Solution {
    public int minimumOneBitOperations(int n) {
        int result = 0;
        while( n > 0){
            result = result ^ n;
            n = n >> 1;
        }

        return result;
    }
}