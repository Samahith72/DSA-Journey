# WIN #8 — Move Zeroes

## Problem

Given an integer array `nums`, move all `0`'s to the end of the array while maintaining the relative order of the non-zero elements.

The operation must be performed in-place, which means I cannot create a copy of the array.

For example:

```text
Input: [0,1,0,3,12]

Output: [1,3,12,0,0]
```

The relative order of the non-zero elements remains the same:

```text
1 → 3 → 12
```

Only the zeroes are moved to the end.

---

## My Java Solution

```java
class Solution {
    public void moveZeroes(int[] nums) {

        if (nums.length == 1 && nums[0] == 0) {
            return;
        }

        int zero = 0;
        int i = 0;
        int j = 0;

        while (j < nums.length) {

            if (nums[j] != 0) {
                nums[i] = nums[j];
                j++;
                i++;
            } else {
                j++;
            }
        }

        while (i < nums.length) {
            nums[i] = 0;
            i++;
        }
    }
}
```

---

## My Thought Process

The main requirement is to move all zeroes to the end while keeping the non-zero elements in their original relative order.

I also need to modify the array in-place.

Instead of repeatedly swapping zeroes with non-zero elements, I decided to first move all the non-zero elements towards the beginning of the array.

I used two pointers:

```text
i → position where the next non-zero element should be placed

j → scans through the entire array
```

The pointer `j` checks every element.

If `nums[j]` is not zero, I place it at `nums[i]` and move both pointers forward.

If `nums[j]` is zero, I do not place it anywhere immediately. I simply move `j` forward and continue searching for the next non-zero element.

For example:

```text
nums = [0,1,0,3,12]
```

The first non-zero element is `1`.

I place it at the position tracked by `i`.

Then I continue scanning until I find the next non-zero element.

The non-zero elements are eventually moved to the beginning:

```text
[1,3,12,_,_]
```

At this point, `i` points to the position where the remaining zeroes should start.

So I use a second loop to fill all remaining positions with zero:

```text
[1,3,12,0,0]
```

The overall approach is:

```text
Scan the array
        ↓
Find non-zero elements
        ↓
Move them towards the beginning
        ↓
Keep their relative order
        ↓
Fill the remaining positions with zeroes
```

---

## Pattern Recognition

### Pattern: Two Pointers / In-Place Array Manipulation

This pattern is useful when I need to rearrange elements in an array without creating another array.

The two pointers have different responsibilities:

```text
i → write pointer

j → scanning pointer
```

The general approach is:

```text
Initialize a write pointer.

Use another pointer to scan the array.

When a valid element is found:
    Place it at the write position.
    Move the write pointer forward.

After processing the array:
    Fill the remaining positions with the required value.
```

In this problem:

```text
Valid element = non-zero element

Write pointer = i

Scanning pointer = j
```

This allows me to move all non-zero values to the front while preserving their relative order.

---

## Complexity

### Time Complexity

```text
O(n)
```

The first loop scans the array once.

The second loop fills the remaining positions with zeroes.

Therefore, the total work is still linear:

```text
O(n)
```

### Space Complexity

```text
O(1)
```

The array is modified in-place and only a constant number of variables are used.

---

## Key Learning

I learned that two pointers do not always have to move towards each other.

In this problem, both pointers move from left to right, but they have different responsibilities.

```text
j → reads and scans the array

i → writes the non-zero elements
```

This separation between reading and writing makes it possible to rearrange the array in-place while maintaining the relative order of the non-zero elements.

The important idea is:

```text
First place all non-zero elements at the beginning.

Then fill the remaining positions with zeroes.
```

This avoids creating an extra array and gives an `O(n)` time and `O(1)` space solution.