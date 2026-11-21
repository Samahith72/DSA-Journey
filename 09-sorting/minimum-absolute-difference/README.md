
# WIN #144 — 1200. Minimum Absolute Difference

[LeetCode Problem](https://leetcode.com/problems/minimum-absolute-difference/)

**Difficulty:** Easy  
**Topics:** Array, Sorting

---

## Problem Statement

Given an array of distinct integers `arr`, find all pairs of elements with the minimum absolute difference.

Return a list of pairs in ascending order. Each pair should contain two numbers, `[a, b]`, where `a < b`. The pairs should also be sorted in ascending order.

### Example

```text
Input: arr = [4,2,1,3]
Output: [[1,2],[2,3],[3,4]]
```

The minimum absolute difference is `1`, and all adjacent pairs with that difference are returned.

---

## Core Idea

Sort the array first. After sorting, the smallest absolute difference must occur between two adjacent elements.

The solution uses two passes:

1. Find the minimum difference between adjacent elements.
2. Scan the array again and collect every adjacent pair whose difference equals that minimum.

Sorting also ensures each pair is in ascending order and the result pairs are produced in ascending order.

---

## Java Solution

```java
class Solution {
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> result = new ArrayList<>();
        int minDiff = Integer.MAX_VALUE;

        for (int i = 1; i < arr.length; i++) {
            minDiff = Math.min(minDiff, arr[i] - arr[i - 1]);
        }

        for (int i = 1; i < arr.length; i++) {
            if ((arr[i] - arr[i - 1]) == minDiff) {
                result.add(Arrays.asList(arr[i - 1], arr[i]));
            }
        }

        return result;
    }
}
```

---

## Step-by-Step Explanation

1. Sort `arr` in ascending order.
2. Initialize `minDiff` to `Integer.MAX_VALUE`.
3. In the first loop, compare every adjacent pair and update `minDiff` with the smallest difference found.
4. In the second loop, compare each adjacent difference with `minDiff`.
5. If the difference matches, add the pair `[arr[i - 1], arr[i]]` to `result`.
6. Return all pairs with the minimum absolute difference.

---

## Dry Run

```text
arr = [4, 2, 1, 3]
```

After sorting:

```text
arr = [1, 2, 3, 4]
```

**First pass — find the minimum difference**

| Pair | Difference | Minimum so far |
|---|---:|---:|
| (1, 2) | 1 | 1 |
| (2, 3) | 1 | 1 |
| (3, 4) | 1 | 1 |

`minDiff = 1`

**Second pass — collect matching pairs**

| Pair | Difference | Add to result? |
|---|---:|---|
| (1, 2) | 1 | Yes |
| (2, 3) | 1 | Yes |
| (3, 4) | 1 | Yes |

Result:

```text
[[1, 2], [2, 3], [3, 4]]
```

---

## Edge Cases

- **Two elements:** The only pair is returned.
- **Minimum difference appears multiple times:** Every matching adjacent pair is included.
- **Negative values:** Sorting still places values in ascending order, so adjacent subtraction gives the absolute difference.
- **Already sorted array:** The same adjacent-pair scan works without any special handling.

---

## Complexity Analysis

Let `n` be the number of elements in `arr`.

- **Time Complexity:** `O(n log n)` — sorting takes `O(n log n)`, followed by two linear scans.
- **Auxiliary Space Complexity:** `O(1)` — excluding the sorting implementation's internal space.
- **Output Space:** `O(n)` in the worst case, when many adjacent pairs share the minimum difference.

---

## Pattern Recognition

When a problem asks for the minimum difference between pairs in an array, consider sorting first. In a sorted array, the closest pair must be adjacent, so checking every possible pair is unnecessary.

If the problem asks for all pairs that achieve the minimum, use one pass to find the minimum and another to collect every matching pair.

---

## Key Takeaway

Sorting turns the minimum absolute difference problem into an adjacent-element comparison. Find the smallest adjacent difference, then collect every pair that matches it.
