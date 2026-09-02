# WIN #7 — Container With Most Water

## Problem

You are given an integer array `height`.

Each element represents the height of a vertical line.

You need to choose two lines that, together with the x-axis, form a container capable of holding the maximum amount of water.

The amount of water stored depends on two things:

```text
Width between the two lines

Height of the shorter line
```

The area of the container is calculated as:

```text
Area = Width × Minimum Height
```

For example:

```text
height = [1,8,6,2,5,4,8,3,7]
```

Choosing the lines with heights `8` and `7` gives:

```text
Width = 7

Height = min(8, 7) = 7

Area = 7 × 7 = 49
```

So the maximum amount of water that can be stored is:

```text
49
```

---

## My Java Solution

```java
class Solution {
    public int maxArea(int[] height) {

        int left = 0;
        int right = height.length - 1;
        int maximum = 0;

        while (left < right) {

            int width = right - left;
            int high = Math.min(height[left], height[right]);
            int area = width * high;

            maximum = Math.max(maximum, area);

            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maximum;
    }
}
```

---

## My Thought Process

The amount of water that a container can hold depends on the distance between the two lines and the height of the shorter line.

The formula is:

```text
Area = Width × Minimum Height
```

Initially, I place two pointers at opposite ends of the array.

```text
left  → first element

right → last element
```

This gives the maximum possible width.

For every pair of lines, I calculate:

```text
Width = right - left

Height = minimum of height[left] and height[right]

Area = Width × Height
```

Then I compare the current area with the maximum area found so far.

The important part is deciding which pointer to move.

If the left height is smaller:

```text
height[left] < height[right]
```

then the left line is limiting the amount of water the container can hold.

Moving the right pointer would reduce the width while keeping the same limiting height.

So I move the left pointer.

Similarly, if the right height is smaller or equal, I move the right pointer.

The overall approach is:

```text
Start with two pointers at opposite ends
        ↓
Calculate the current container area
        ↓
Store the maximum area
        ↓
Move the pointer with the smaller height
        ↓
Repeat until the pointers meet
```

---

## Pattern Recognition

### Pattern: Two Pointers

This pattern is useful when I need to compare elements from different positions in an array.

For this problem, the two pointers start from opposite ends:

```text
left  → beginning of the array

right → end of the array
```

The area depends on:

```text
Distance between the pointers

Smaller height between the two pointers
```

The general approach is:

```text
Place one pointer at the beginning.

Place another pointer at the end.

Calculate the current result.

Move the pointer that is limiting the result.

Continue until the pointers meet.
```

In this problem, the limiting factor is the shorter line.

That is why I always move the pointer with the smaller height.

---

## Complexity

### Time Complexity

```text
O(n)
```

Each pointer moves only in one direction.

The array is processed at most once.

### Space Complexity

```text
O(1)
```

Only a fixed number of variables are used.

No extra data structure is created.

---

## Key Learning

I learned that when two pointers are involved, it is important to understand which pointer should move and why.

In this problem, the shorter line determines the maximum height of water that can be stored.

Moving the taller line while keeping the shorter line would only reduce the width without increasing the limiting height.

Therefore, the only possible way to potentially find a better result is to move the pointer with the shorter height.

The key idea is:

```text
The smaller height is the limiting factor.

Move the pointer with the smaller height.

Hope to find a taller line while reducing the width.
```

This problem helped me understand how the Two Pointers pattern can reduce a brute-force `O(n²)` solution to an `O(n)` solution by eliminating combinations that cannot produce a better answer.