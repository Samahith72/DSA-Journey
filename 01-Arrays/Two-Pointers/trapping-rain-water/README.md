# WIN #13 — Trapping Rain Water

## Problem

You are given an array of non-negative integers where each element represents the height of a bar in an elevation map.

Each bar has a width of `1`.

The goal is to calculate how much water can be trapped between the bars after it rains.

For example:

```text
Input:
[0,1,0,2,1,0,1,3,2,1,2,1]

Output:
6
```

The water is trapped in the spaces where a bar has taller boundaries on both sides.

The amount of water that can be stored at a position depends on the smaller of the maximum heights on its left and right.

---

## My Java Solution

```java
class Solution {
    public int trap(int[] height) {

        int leftMax = 0;
        int rightMax = 0;

        int left = 0;
        int right = height.length - 1;

        int water = 0;

        while (left <= right) {

            if (leftMax <= rightMax) {

                if (height[left] > leftMax) {
                    leftMax = height[left];
                } else {
                    water += leftMax - height[left];
                }

                left++;

            } else {

                if (height[right] > rightMax) {
                    rightMax = height[right];
                } else {
                    water += rightMax - height[right];
                }

                right--;
            }
        }

        return water;
    }
}
```

---

## My Thought Process

The amount of water trapped at a particular position depends on the tallest bar on its left and the tallest bar on its right.

The basic idea is:

```text
Water at position = min(leftMax, rightMax) - currentHeight
```

A simple approach would be to calculate the left maximum and right maximum for every position, but that would require additional work and possibly extra arrays.

Instead, I used two pointers.

I maintain:

```text
left → starts from the beginning

right → starts from the end

leftMax → maximum height found from the left side

rightMax → maximum height found from the right side
```

The important part is deciding which side to process.

I compare:

```text
leftMax <= rightMax
```

If this is true, I process the left side.

If the current left height is greater than `leftMax`, I update:

```text
leftMax = height[left]
```

Otherwise, I know that water can be trapped at this position:

```text
water += leftMax - height[left]
```

Then I move:

```text
left++
```

If:

```text
leftMax > rightMax
```

I process the right side instead.

If the current right height is greater than `rightMax`, I update:

```text
rightMax = height[right]
```

Otherwise, I calculate:

```text
water += rightMax - height[right]
```

Then I move:

```text
right--
```

The important idea is that when `leftMax` is smaller than or equal to `rightMax`, the left side has enough information to determine the amount of water that can be trapped there.

Similarly, when `rightMax` is smaller, I can safely process the right side.

The overall approach is:

```text
Start two pointers at both ends
        ↓
Track maximum height from both sides
        ↓
Compare leftMax and rightMax
        ↓
Process the side with the smaller maximum
        ↓
Calculate trapped water if possible
        ↓
Move that pointer inward
        ↓
Continue until the pointers meet
```

---

## Pattern Recognition

### Pattern: Two Pointers + Maximum Tracking

This pattern is useful when I need information from both the left and right sides of an array while avoiding extra arrays.

The general idea is:

```text
left = beginning
right = end

Track the maximum value from both sides.

While left and right have not crossed:

    Compare the left maximum and right maximum.

    Process the side with the smaller maximum.

    Update the maximum or calculate the required result.

    Move that pointer inward.
```

For this problem:

```text
leftMax  → tallest bar encountered from the left

rightMax → tallest bar encountered from the right
```

The key observation is:

```text
If leftMax <= rightMax:
    Process the left side

Otherwise:
    Process the right side
```

This works because the smaller boundary determines how much water can be trapped.

If the left maximum is smaller, the right side already has a boundary at least as tall as `leftMax`.

Therefore, the water on the left can be calculated using `leftMax`.

The same reasoning applies to the right side.

---

## Complexity

### Time Complexity

```text
O(n)
```

The two pointers move across the array only once.

Each position is processed at most once.

### Space Complexity

```text
O(1)
```

Only a fixed number of variables are used.

No additional arrays or data structures are required.

---

## Key Learning

I learned that the amount of trapped water at a position depends on the boundaries around it.

The important formula is:

```text
Water = minimum(leftMax, rightMax) - currentHeight
```

Instead of storing the left and right maximum for every position, I can maintain only:

```text
leftMax
rightMax
```

while using two pointers to process the array.

The most important insight for me was understanding why I can choose which side to process.

```text
leftMax <= rightMax → process left

leftMax > rightMax → process right
```

The smaller maximum is the limiting boundary, so I can safely calculate the water on that side.

This problem helped me understand a more advanced use of the Two Pointers pattern, where the pointers are not simply comparing two elements but are used together with additional state to solve the problem in `O(n)` time and `O(1)` extra space.