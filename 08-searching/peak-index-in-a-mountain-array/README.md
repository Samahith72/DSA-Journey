
# WIN #146 — 852. Peak Index in a Mountain Array

[LeetCode Problem](https://leetcode.com/problems/peak-index-in-a-mountain-array/)

**Difficulty:** Medium  
**Topics:** Array, Binary Search

---

## Problem Statement

A mountain array is an array where:

- The values strictly increase to a peak.
- The values strictly decrease after the peak.

Given a mountain array `arr`, return the index of its peak element.

The peak is guaranteed to be neither the first nor the last element.

### Example

```text
Input: arr = [0, 2, 1, 0]
Output: 1
Explanation: The peak element is 2, located at index 1.
```

---

## Core Idea

Use binary search to determine whether the midpoint is on the increasing or decreasing side of the mountain.

- If `arr[mid] < arr[mid + 1]`, the array is still increasing. The peak must be to the right.
- Otherwise, the midpoint is at the peak or on the decreasing side. The peak is at `mid` or to its left.

When `low == high`, the search has narrowed to the peak index.

---

## Java Solution

```java
class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        int low = 0;
        int high = arr.length - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (arr[mid] < arr[mid + 1]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }

        return low;
    }
}
```

---

## Step-by-Step Explanation

1. Initialize `low` and `high` to the first and last indices.
2. Continue while `low < high`. This ensures `mid + 1` remains within the array bounds.
3. Calculate the midpoint.
4. If `arr[mid] < arr[mid + 1]`, the slope is rising, so move `low` to `mid + 1`.
5. Otherwise, the slope is falling or `mid` is the peak. Keep `mid` in the search range by setting `high = mid`.
6. When `low` equals `high`, return that index.

---

## Dry Run

```text
arr = [0, 2, 1, 0]
```

| low | high | mid | Comparison | Action |
|---:|---:|---:|---|---|
| 0 | 3 | 1 | `2 < 1` is false | `high = 1` |
| 0 | 1 | 0 | `0 < 2` is true | `low = 1` |

Now `low == high == 1`, so return `1`.

---

## Edge Cases

- **Peak near the beginning:** The search can return index `1`.
- **Peak near the end:** The search can return index `arr.length - 2`.
- **Two possible search halves:** The comparison with `arr[mid + 1]` identifies whether the peak is to the left or right.
- **Single-element or non-mountain arrays:** These are outside the problem’s stated constraints.

---

## Complexity Analysis

Let `n` be the number of elements in `arr`.

- **Time Complexity:** `O(log n)` — each iteration eliminates roughly half of the search range.
- **Space Complexity:** `O(1)` — only a fixed number of variables are used.

---

## Pattern Recognition

For a mountain array, compare adjacent values around the midpoint to identify the slope.

- Rising slope: move right.
- Falling slope: keep the midpoint and search left.

This finds the peak without scanning the entire array.

---

## Key Takeaway

The peak is the point where the array stops increasing and starts decreasing. Binary search finds it by repeatedly checking the slope between `mid` and `mid + 1`.
