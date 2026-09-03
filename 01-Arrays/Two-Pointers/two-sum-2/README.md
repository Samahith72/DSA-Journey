# WIN #9 — Two Sum II - Input Array Is Sorted

## Problem

You are given a 1-indexed integer array `numbers` that is already sorted in non-decreasing order.

You need to find two numbers whose sum is equal to the given `target`.

The solution must return their indices, and because the array is 1-indexed, the returned indices start from `1`.

The problem guarantees that exactly one solution exists.

For example:

```text
numbers = [2,7,11,15]
target = 9
```

The two numbers are:

```text
2 + 7 = 9
```

Their 1-based indices are:

```text
[1,2]
```

---

## My Java Solution

```java
class Solution {
    public int[] twoSum(int[] numbers, int target) {

        int left = 0;
        int right = numbers.length - 1;

        while (left < right) {

            int sum = numbers[left] + numbers[right];

            if (sum == target) {
                return new int[]{left + 1, right + 1};
            }

            if (sum > target) {
                right--;
            } else {
                left++;
            }
        }

        return new int[]{};
    }
}
```

---

## My Thought Process

The important thing I noticed is that the array is already sorted.

Because of this, I do not need to check every possible pair.

I can start with two pointers:

```text
left → first element

right → last element
```

Then I calculate the sum of the two values.

There are three possible cases.

### Case 1: Sum equals target

If:

```text
numbers[left] + numbers[right] == target
```

I have found the required pair.

Because the problem uses a 1-indexed array, I return:

```text
left + 1
right + 1
```

---

### Case 2: Sum is greater than target

If:

```text
sum > target
```

the current sum is too large.

Since the array is sorted, I need to decrease the sum.

So I move the right pointer to the left:

```text
right--
```

This gives me a smaller value.

---

### Case 3: Sum is less than target

If:

```text
sum < target
```

the current sum is too small.

Since the array is sorted, I need to increase the sum.

So I move the left pointer to the right:

```text
left++
```

This gives me a larger value.

The overall approach is:

```text
Start left at the beginning
Start right at the end
        ↓
Calculate the sum
        ↓
If sum == target
    Return the indices
        ↓
If sum > target
    Move right left
        ↓
If sum < target
    Move left right
        ↓
Repeat until the answer is found
```

---

## Pattern Recognition

### Pattern: Two Pointers

This pattern is especially useful when working with a sorted array.

The sorted order gives information about how the current result will change when a pointer moves.

In this problem:

```text
left → moves right to increase the sum

right → moves left to decrease the sum
```

The general approach is:

```text
Place one pointer at the beginning.

Place another pointer at the end.

Calculate the result using both pointers.

If the result is too small:
    Move the left pointer forward.

If the result is too large:
    Move the right pointer backward.

If the result is correct:
    Return the answer.
```

The important reason this works is that the array is sorted.

If the sum is too large, moving `left` would only make the sum larger, so I move `right`.

If the sum is too small, moving `right` would only make the sum smaller, so I move `left`.

---

## Complexity

### Time Complexity

```text
O(n)
```

Both pointers only move towards each other.

Each element is considered at most a constant number of times.

### Space Complexity

```text
O(1)
```

Only the two pointer variables and a few other variables are used.

The solution satisfies the requirement of using constant extra space.

---

## Key Learning

I learned how a sorted array can provide useful information that allows me to eliminate unnecessary comparisons.

In the regular Two Sum problem, a brute-force solution checks many pairs.

Here, because the array is sorted, I can use two pointers and decide which direction to move based on the current sum.

The key idea is:

```text
Sum is too large → move right left

Sum is too small → move left right

Sum is correct → return the answer
```

I also learned to pay attention to whether an array is 0-indexed or 1-indexed.

The Java array itself uses 0-based indexing, but this problem requires 1-based indices, so I return:

```text
left + 1
right + 1
```

This problem reinforced the idea that a sorted array can often be paired with the Two Pointers pattern to achieve an `O(n)` solution with `O(1)` extra space.