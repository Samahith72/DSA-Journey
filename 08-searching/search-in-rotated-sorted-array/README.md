
# WIN #149 — 33. Search in Rotated Sorted Array

[LeetCode Problem](https://leetcode.com/problems/search-in-rotated-sorted-array/)

**Difficulty:** Medium  
**Topics:** Array, Binary Search

---

## Problem Statement

Given an integer array `nums` sorted in ascending order and then rotated at an unknown pivot, search for `target`.

All elements are distinct. Return the index of `target` if it exists in `nums`; otherwise, return `-1`.

The solution must run in `O(log n)` time.

### Example

```text
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
```

The target `0` is located at index `4`.

---

## Core Idea

Even though the array is rotated, at least one half of the current search range is always sorted.

For each midpoint:

1. If `nums[mid]` equals `target`, return `mid`.
2. If the left half is sorted, check whether the target lies within that half.
3. Otherwise, the right half is sorted. Check whether the target lies within it.
4. Continue searching only the half that can contain the target.

This allows binary search to work in a rotated sorted array.

---

## Java Solution

```java
class Solution {
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[low] <= nums[mid]) {
                if (nums[low] <= target && target < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            else {
                if (nums[mid] < target && target <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }
}
```

---

## Step-by-Step Explanation

1. Initialize `low` and `high` to the first and last indices.
2. Calculate `mid`. If `nums[mid]` equals `target`, return the index.
3. Check whether the left half is sorted using `nums[low] <= nums[mid]`.
4. If the left half is sorted:
   - If `target` is between `nums[low]` and `nums[mid]`, search left.
   - Otherwise, search right.
5. If the left half is not sorted, the right half must be sorted:
   - If `target` is greater than `nums[mid]` and no greater than `nums[high]`, search right.
   - Otherwise, search left.
6. If the search range becomes empty, return `-1`.

The range checks use inclusive and exclusive bounds carefully because `nums[mid]` has already been checked for equality.

---

## Dry Run

```text
nums = [4, 5, 6, 7, 0, 1, 2]
target = 0
```

| low | high | mid | nums[mid] | Decision |
|---:|---:|---:|---:|---|
| 0 | 6 | 3 | 7 | Left half is sorted, but target is not in `[4, 7)`. Search right. |
| 4 | 6 | 5 | 1 | Left half is sorted and target is in `[0, 1)`. Search left. |
| 4 | 4 | 4 | 0 | Target found. Return `4`. |

---

## Edge Cases

- **Target is present:** Return its index.
- **Target is absent:** Return `-1` when the search range is empty.
- **Array is not rotated:** The same logic works because the full range is sorted.
- **Single-element array:** Return `0` if it matches the target; otherwise return `-1`.
- **Empty array:** The loop is skipped and `-1` is returned.

---

## Complexity Analysis

Let `n` be the number of elements in `nums`.

- **Time Complexity:** `O(log n)` — each iteration eliminates approximately half of the search range.
- **Space Complexity:** `O(1)` — only a fixed number of variables are used.

---

## Pattern Recognition

In a rotated sorted array with distinct elements, one half of the search range is always sorted.

Identify the sorted half, check whether the target lies within its bounds, and discard the half that cannot contain the target.

---

## Key Takeaway

Rotation disrupts the overall sorted order, but not the sorted order within each half. Use the sorted half to determine which direction to continue searching.
