# WIN #3 — Maximum Subarray

## Problem

You are given an integer array `nums`.

You need to find the contiguous subarray with the largest sum and return that sum.

A subarray must contain consecutive elements from the original array.

For example:

```text
nums = [-2,1,-3,4,-1,2,1,-5,4]
```

The maximum subarray is:

```text
[4,-1,2,1]
```

The sum of this subarray is:

```text
6
```

---

## My Java Solution

```java
class Solution {
    public int maxSubArray(int[] nums) {

        int low = 0;
        int high = nums.length - 1;
        int result = maxSubArraySum(nums, low, high);

        return result;
    }

    public int maxSubArraySum(int[] arr, int low, int high) {

        // Best case: only one element is present
        if (low == high) {
            return arr[low];
        }

        // Find the middle of the array
        int mid = low + (high - low) / 2;

        // Find the maximum subarray sum in the left half
        int leftMax = maxSubArraySum(arr, low, mid);

        // Find the maximum subarray sum in the right half
        int rightMax = maxSubArraySum(arr, mid + 1, high);

        // Find the maximum subarray sum crossing the middle
        int crossingMax = crossingSum(arr, low, mid, high);

        return Math.max(Math.max(leftMax, rightMax), crossingMax);
    }

    public int crossingSum(int[] arr, int low, int mid, int high) {

        // Find the maximum sum starting from mid and moving left
        int sum = 0;
        int leftSum = Integer.MIN_VALUE;

        for (int i = mid; i >= low; i--) {
            sum += arr[i];

            if (sum > leftSum) {
                leftSum = sum;
            }
        }

        // Find the maximum sum from mid + 1 and moving right
        int sum2 = 0;
        int rightSum = Integer.MIN_VALUE;

        for (int i = mid + 1; i <= high; i++) {
            sum2 += arr[i];

            if (sum2 > rightSum) {
                rightSum = sum2;
            }
        }

        // Add the best sum from both sides
        return leftSum + rightSum;
    }
}
```

---

## My Thought Process

At first, this problem looks like we need to check every possible subarray and find the one with the largest sum.

However, checking every possible subarray would take a lot of comparisons.

Instead, I used the Divide and Conquer approach.

The idea is to divide the array into two halves.

For every part of the array, the maximum subarray can exist in only three places:

1. Completely in the left half.
2. Completely in the right half.
3. Crossing the middle.

So I recursively find the maximum subarray sum in the left half and the right half.

Then I calculate the maximum subarray sum that crosses the middle.

Finally, I compare all three values and return the largest one.

The logic is:

```text
Maximum Subarray
        ↓
Divide the array into two halves
        ↓
Find the maximum subarray in the left half
        ↓
Find the maximum subarray in the right half
        ↓
Find the maximum subarray crossing the middle
        ↓
Return the maximum of all three
```

The recursion stops when there is only one element left.

```java
if (low == high) {
    return arr[low];
}
```

In that case, the single element itself is the maximum subarray sum for that part of the array.

---

## Pattern Recognition

### Pattern: Divide and Conquer

This pattern is useful when:

* A large problem can be divided into smaller similar problems.
* The smaller problems can be solved recursively.
* The results from the smaller problems can be combined.

The general approach is:

```text
Divide the problem into smaller parts.

Solve the left part recursively.

Solve the right part recursively.

Find the result that combines both parts.

Return the best answer.
```

For this problem:

```text
leftMax = maximum subarray completely in the left half

rightMax = maximum subarray completely in the right half

crossingMax = maximum subarray that crosses the middle
```

Then:

```text
result = maximum of leftMax, rightMax, and crossingMax
```

---

## Complexity

### Time Complexity

```text
O(n log n)
```

The array is repeatedly divided into two halves.

At every level of recursion, the crossing sum calculations process the elements in the current section.

Since there are approximately `log n` levels of recursion, the overall time complexity is:

```text
O(n log n)
```

### Space Complexity

```text
O(log n)
```

The recursive function calls use stack space.

The maximum depth of the recursion is approximately `log n`.

---

## Key Learning

I learned that a problem does not always need to be solved by checking every possible combination.

In this problem, after dividing the array, the maximum subarray can only be in three places:

```text
Left half

Right half

Crossing the middle
```

I also learned how Divide and Conquer works with recursion.

The main idea is to break a large problem into smaller versions of the same problem, solve them recursively, and combine their results.

This problem can also be solved using Kadane's Algorithm in `O(n)` time.

However, this solution helped me understand how Divide and Conquer can be applied to an array problem.