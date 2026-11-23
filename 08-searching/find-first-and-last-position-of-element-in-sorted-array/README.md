
# WIN #148 — 34. Find First and Last Position of Element in Sorted Array

[LeetCode Problem](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

**Difficulty:** Medium  
**Topics:** Array, Binary Search

---

## Problem Statement

Given an array of integers `nums` sorted in non-decreasing order, find the starting and ending position of a given `target`.

If the target is not found, return `[-1, -1]`.

The solution must run in `O(log n)` time.

### Example

```text
Input: nums = [5, 7, 7, 8, 8, 10], target = 8
Output: [3, 4]
```

The target `8` appears at indices `3` and `4`.

---

## Core Idea

Use two binary searches:

- `findFirst()` locates the first occurrence of `target`.
- `findLast()` locates the last occurrence of `target`.

When a match is found, do not return immediately. Save its index and continue searching in the direction where an earlier or later occurrence could exist.

If no match is found, the answer remains `-1`.

---

## Java Solution

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int first = findFirst(nums, target);
        int last = findLast(nums, target);

        return new int[]{first, last};
    }

    private int findFirst(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int answer = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                answer = mid;
                high = mid - 1;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }

    private int findLast(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        int answer = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                answer = mid;
                low = mid + 1;
            } else if (nums[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return answer;
    }
}
```

---

## Step-by-Step Explanation

### 1. Find the first occurrence

- Start with the full search range and initialize `answer = -1`.
- If `nums[mid] == target`, save `mid` and continue searching left by setting `high = mid - 1`.
- If `nums[mid] < target`, search right.
- If `nums[mid] > target`, search left.
- Return the saved index after the search ends.

### 2. Find the last occurrence

- Start with the full search range and initialize `answer = -1`.
- If `nums[mid] == target`, save `mid` and continue searching right by setting `low = mid + 1`.
- If `nums[mid] < target`, search right.
- If `nums[mid] > target`, search left.
- Return the saved index after the search ends.

### 3. Combine the results

Call both helper methods and return their results in an array: `{first, last}`.

---

## Dry Run

```text
nums = [5, 7, 7, 8, 8, 10]
target = 8
```

### First occurrence

| low | high | mid | nums[mid] | Action |
|---:|---:|---:|---:|---|
| 0 | 5 | 2 | 7 | Search right |
| 3 | 5 | 4 | 8 | Save index 4; search left |
| 3 | 3 | 3 | 8 | Save index 3; search left |

The search ends. `findFirst()` returns `3`.

### Last occurrence

| low | high | mid | nums[mid] | Action |
|---:|---:|---:|---:|---|
| 0 | 5 | 2 | 7 | Search right |
| 3 | 5 | 4 | 8 | Save index 4; search right |
| 5 | 5 | 5 | 10 | Search left |

The search ends. `findLast()` returns `4`.

Final result:

```text
[3, 4]
```

---

## Edge Cases

- **Target does not exist:** Both searches return `-1`, producing `[-1, -1]`.
- **Target appears once:** Both searches return the same index.
- **Target appears multiple times:** The first search finds the leftmost occurrence and the second finds the rightmost occurrence.
- **Empty array:** Both searches skip their loops and return `-1`.

---

## Complexity Analysis

Let `n` be the number of elements in `nums`.

- **Time Complexity:** `O(log n)` — each helper performs a binary search. Two logarithmic searches still take `O(log n)` time.
- **Space Complexity:** `O(1)` — the searches use only a fixed number of variables.

---

## Pattern Recognition

When a sorted array contains duplicates and the task asks for the first or last occurrence, use binary search but continue after finding a match.

- To find the first occurrence, save the match and move left.
- To find the last occurrence, save the match and move right.

---

## Key Takeaway

A standard binary search can find any occurrence of a target. To find its boundaries, keep searching after a match: left for the first index and right for the last.
