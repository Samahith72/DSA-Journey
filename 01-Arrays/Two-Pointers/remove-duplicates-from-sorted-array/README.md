# WIN #6 — Remove Duplicates from Sorted Array

## Problem

You are given an integer array `nums` sorted in non-decreasing order.

The goal is to remove duplicate elements in-place so that every unique element appears only once.

The relative order of the elements must remain the same.

After removing the duplicates, return the number of unique elements.

The first `k` positions of the array should contain all the unique elements, where `k` is the number returned.

For example:

```text
Input: nums = [1,1,2]

After removing duplicates:

nums = [1,2,_]

Output: 2
```

The underscore represents positions that can be ignored.

---

## My Java Solution

```java
class Solution {
    public int removeDuplicates(int[] nums) {

        if (nums.length == 0 || nums.length == 1) {
            return nums.length;
        }

        int i = 1;

        for (int j = 1; j < nums.length; j++) {

            if (nums[j] != nums[i - 1]) {
                nums[i] = nums[j];
                i++;
            }
        }

        return i;
    }
}
```

---

## My Thought Process

The important part of this problem is that the array is already sorted.

Because the array is sorted, duplicate elements will always appear next to each other.

Instead of creating a new array, I used two pointers to modify the original array in-place.

I used:

```text
i → keeps track of the position where the next unique element should be placed

j → moves through the array and checks every element
```

Initially, `i` starts at index `1` because the first element is always unique.

Then `j` starts checking the remaining elements.

For every element:

1. Compare `nums[j]` with the last unique element, which is at `nums[i - 1]`.
2. If they are the same, it means the current element is a duplicate, so I ignore it.
3. If they are different, it means a new unique element is found.
4. Place that element at index `i`.
5. Move `i` forward.

For example:

```text
nums = [0,0,1,1,1,2,2,3,3,4]
```

The process looks like:

```text
0 → first unique element

0 → duplicate, ignore

1 → new unique element, place it

1 → duplicate, ignore

1 → duplicate, ignore

2 → new unique element, place it

2 → duplicate, ignore

3 → new unique element, place it

3 → duplicate, ignore

4 → new unique element, place it
```

After processing the array:

```text
nums = [0,1,2,3,4,_,_,_,_,_]
```

The value of `i` represents the number of unique elements.

---

## Pattern Recognition

### Pattern: Two Pointers

This pattern is useful when I need to traverse an array while keeping track of another position.

In this problem, the two pointers have different responsibilities:

```text
i → points to where the next unique element should be stored

j → scans every element in the array
```

The general approach is:

```text
Initialize a pointer for the result position.

Use another pointer to scan the array.

If a new valid element is found:
    Place it at the result position.
    Move the result pointer forward.

Continue until the array is processed.
```

This pattern is useful for problems involving:

```text
Removing duplicates

Moving elements

Filtering elements

Modifying an array in-place
```

---

## Complexity

### Time Complexity

```text
O(n)
```

The array is traversed only once.

### Space Complexity

```text
O(1)
```

The array is modified in-place and only a constant number of variables are used.

---

## Key Learning

I learned that the sorted property of the array is the main reason this problem can be solved efficiently.

Because duplicate elements are always next to each other, I do not need an extra data structure to keep track of previously seen values.

The two-pointer approach allows me to:

```text
Scan every element

Identify unique values

Move unique values to the beginning of the array

Modify the array in-place
```

The key insight was understanding that `j` is responsible for reading the array, while `i` is responsible for building the part of the array that contains only unique elements.

This problem helped me understand how two pointers can be used not only for comparing elements from opposite directions, but also for reading and writing elements at different positions in the same array.