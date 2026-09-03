# WIN #10 — Rotate Array

## Problem

You are given an integer array `nums`.

The goal is to rotate the array to the right by `k` steps.

For example:

```text
Input: nums = [1,2,3,4,5,6,7], k = 3

Output: [5,6,7,1,2,3,4]
```

Rotating the array to the right by one position gives:

```text
[7,1,2,3,4,5,6]
```

After three rotations:

```text
[5,6,7,1,2,3,4]
```

The solution must modify the array in-place.

---

## My Java Solution

```java
class Solution {
    public void rotate(int[] nums, int k) {

        int n = nums.length;

        k %= n;

        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    public static void reverse(int[] nums, int start, int end) {

        while (start < end) {
            int temp = nums[start];

            nums[start] = nums[end];
            nums[end] = temp;

            start++;
            end--;
        }
    }
}
```

---

## My Thought Process

The first thing I noticed is that rotating the array one position at a time would require repeatedly moving elements, which could become inefficient when `k` is large.

Instead, I used the reversal technique.

The key idea is that the last `k` elements need to move to the beginning of the array.

For example:

```text
nums = [1,2,3,4,5,6,7]
k = 3
```

The part that needs to move to the front is:

```text
[5,6,7]
```

The remaining part is:

```text
[1,2,3,4]
```

So the desired result is:

```text
[5,6,7,1,2,3,4]
```

I can achieve this using three reversals.

### Step 1: Reverse the entire array

```text
[1,2,3,4,5,6,7]

↓ reverse

[7,6,5,4,3,2,1]
```

### Step 2: Reverse the first `k` elements

Since `k = 3`:

```text
[7,6,5,4,3,2,1]

↓ reverse first 3 elements

[5,6,7,4,3,2,1]
```

### Step 3: Reverse the remaining elements

```text
[5,6,7,4,3,2,1]

↓ reverse from index 3 to the end

[5,6,7,1,2,3,4]
```

This gives the required rotated array.

I also used:

```java
k %= n;
```

This is important because if `k` is greater than the array length, rotating by `n` positions brings the array back to its original state.

For example:

```text
n = 7
k = 10
```

Instead of performing 10 rotations:

```text
k = 10 % 7
k = 3
```

So I only need to rotate by 3 positions.

---

## Pattern Recognition

### Pattern: Array Manipulation / Reversal

This pattern is useful when an array needs to be rearranged in-place without using an additional array.

The important technique here is the three-reversal method.

The general approach for rotating an array to the right by `k` positions is:

```text
Reverse the entire array

Reverse the first k elements

Reverse the remaining elements
```

For example:

```text
Original:

[1,2,3,4,5,6,7]

Reverse everything:

[7,6,5,4,3,2,1]

Reverse first k elements:

[5,6,7,4,3,2,1]

Reverse remaining elements:

[5,6,7,1,2,3,4]
```

The `reverse()` method itself uses two pointers:

```text
start → beginning of the section

end → end of the section
```

The two pointers move towards each other while swapping elements.

---

## Complexity

### Time Complexity

```text
O(n)
```

The array is reversed three times.

Each reversal takes linear time relative to the portion being reversed.

Therefore, the total time complexity is still:

```text
O(n)
```

### Space Complexity

```text
O(1)
```

The array is modified in-place.

Only a temporary variable is used during swapping, so no additional data structure is required.

---

## Key Learning

I learned that an array rotation does not have to be performed one step at a time.

The important insight was to recognize that the last `k` elements need to move to the beginning while maintaining their original order.

The three-reversal technique allows this to be done in-place:

```text
Reverse the entire array

Reverse the first k elements

Reverse the remaining elements
```

I also learned why `k %= n` is important when the number of rotations is larger than the array length.

This problem helped me understand how reversing different sections of an array can be combined to achieve a larger rearrangement efficiently.

The final solution uses:

```text
O(n) time
O(1) extra space
```

while modifying the original array in-place.