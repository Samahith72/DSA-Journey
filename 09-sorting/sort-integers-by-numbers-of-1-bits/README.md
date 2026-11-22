
# WIN #147 — 1356. Sort Integers by The Number of 1 Bits

[LeetCode Problem](https://leetcode.com/problems/sort-integers-by-the-number-of-1-bits/)

**Difficulty:** Easy  
**Topics:** Array, Bit Manipulation, Sorting

---

## Problem Statement

Given an integer array `arr`, sort it in ascending order by the number of `1` bits in each integer's binary representation.

If two integers have the same number of `1` bits, sort them by their numeric value in ascending order.

Return the sorted array.

### Example

```text
Input: arr = [0, 1, 2, 3, 4, 5, 6, 7, 8]
Output: [0, 1, 2, 4, 8, 3, 5, 6, 7]
```

The numbers are first ordered by their set-bit count. Numbers with the same count are then ordered numerically.

---

## Core Idea

Use a custom comparator to sort the numbers using two conditions:

1. Compare the number of set bits (`1` bits) in each number.
2. If both numbers have the same set-bit count, compare their numeric values.

Java's `Integer.bitCount()` returns the number of `1` bits in an integer's binary representation.

Because Java's `Arrays.sort()` comparator for objects requires an `Integer[]`, copy the input `int[]` into an `Integer[]`, sort it, then copy the sorted values back into the original array.

---

## Java Solution

```java
class Solution {
    public int[] sortByBits(int[] arr) {
        Integer[] nums = new Integer[arr.length];

        for (int i = 0; i < arr.length; i++) {
            nums[i] = arr[i];
        }

        Arrays.sort(nums, (a, b) -> {
            int bitA = Integer.bitCount(a);
            int bitB = Integer.bitCount(b);

            if (bitA != bitB) {
                return bitA - bitB;
            }

            return a - b;
        });

        for (int i = 0; i < arr.length; i++) {
            arr[i] = nums[i];
        }

        return arr;
    }
}
```

---

## Step-by-Step Explanation

1. Create an `Integer[]` named `nums` because the comparator-based `Arrays.sort()` works with object arrays.
2. Copy each value from `arr` into `nums`.
3. Sort `nums` using a custom comparator:
   - Count the set bits in both numbers using `Integer.bitCount()`.
   - If the counts differ, place the number with fewer set bits first.
   - If the counts are equal, place the smaller number first.
4. Copy the sorted values from `nums` back into `arr`.
5. Return the sorted array.

---

## Dry Run

```text
arr = [3, 7, 8, 9, 10]
```

| Number | Binary | Set bits |
|---:|---|---:|
| 3 | `11` | 2 |
| 7 | `111` | 3 |
| 8 | `1000` | 1 |
| 9 | `1001` | 2 |
| 10 | `1010` | 2 |

Sort by set-bit count first, then by numeric value:

```text
[8, 3, 9, 10, 7]
```

---

## Edge Cases

- **All numbers have the same set-bit count:** They are sorted by numeric value.
- **Numbers have different set-bit counts:** The number with fewer set bits comes first.
- **Zero:** It has zero set bits, so it appears before positive numbers.
- **Single-element array:** The array is returned unchanged.

---

## Complexity Analysis

Let `n` be the number of elements in `arr`.

- **Time Complexity:** `O(n log n)` — sorting performs `O(n log n)` comparisons, with each comparison counting bits in constant time for fixed-width integers.
- **Space Complexity:** `O(n)` — the `Integer[]` copy stores all elements.

---

## Pattern Recognition

When sorting by a computed property, use a custom comparator.

Here, the primary key is the number of set bits, and the secondary key is the integer value. The comparator applies the secondary key only when the primary keys are equal.

---

## Key Takeaway

A custom comparator can combine multiple sorting rules. Count set bits with `Integer.bitCount()`, sort by that count, and use the numeric value to break ties.
