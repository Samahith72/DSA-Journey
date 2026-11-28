
# WIN #153 — 153. Find Minimum in Rotated Sorted Array

[LeetCode Problem](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)

**Difficulty:** Medium  
**Topics:** Array, Binary Search

---

## Problem Statement

Given an array of unique integers that was originally sorted in ascending order and then rotated at an unknown pivot, find and return the minimum element.

The solution must run in `O(log n)` time.

### Example

```text
Input: nums = [3,4,5,1,2]
Output: 1
```

The original sorted array was `[1,2,3,4,5]`. It was rotated, and the minimum element is `1`.

---

## Core Idea

Use binary search to locate the point where the sorted array was rotated.

Compare the middle element with the last element in the current search range:

- If `nums[mid] > nums[high]`, the minimum must be to the right of `mid`.
- Otherwise, the minimum is at `mid` or somewhere to its left.

When `low == high`, the search has narrowed to the index of the minimum element.

---

## Java Solution

```java
class Solution {
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] > nums[high]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return nums[low];
    }
}
```

---

## Step-by-Step Explanation

1. Initialize `low` to the first index and `high` to the last index.
2. Continue while `low < high`, ensuring the search range contains at least two elements.
3. Calculate the midpoint.
4. If `nums[mid] > nums[high]`, the minimum must be to the right of `mid`. Set `low = mid + 1`.
5. Otherwise, the minimum is at `mid` or to its left. Set `high = mid`, keeping `mid` in the search range.
6. When `low == high`, return `nums[low]`.

---

## Dry Run

```text
nums = [3,4,5,1,2]
```

| low | high | mid | nums[mid] | nums[high] | Action |
|---:|---:|---:|---:|---:|---|
| 0 | 4 | 2 | 5 | 2 | `5 > 2`, so `low = 3` |
| 3 | 4 | 3 | 1 | 2 | `1 <= 2`, so `high = 3` |

Now `low == high == 3`.

Return `nums[3]`, which is `1`.

---

## Edge Cases

- **Array is not rotated:** The minimum is the first element.
- **Minimum is near the end:** Binary search narrows to its index.
- **Two elements:** The comparison identifies the smaller one.
- **Single-element array:** `low` and `high` are equal, so the only element is returned.

---

## Complexity Analysis

Let `n` be the number of elements in `nums`.

- **Time Complexity:** `O(log n)` — each iteration eliminates approximately half of the search range.
- **Space Complexity:** `O(1)` — only a fixed number of variables are used.

---

## Pattern Recognition

This is a binary search on a rotated sorted array. Compare the midpoint with the rightmost element to determine which half contains the minimum.

The key distinction is that when `nums[mid] <= nums[high]`, `mid` may itself be the minimum, so the search keeps `mid` by setting `high = mid`.

---

## Key Takeaway

The minimum is the rotation point. Comparing `nums[mid]` with `nums[high]` tells binary search whether to discard the left portion or keep searching on the left, including `mid`.
