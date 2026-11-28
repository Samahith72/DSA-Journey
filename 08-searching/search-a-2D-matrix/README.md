
# WIN #154 — 74. Search a 2D Matrix

[LeetCode Problem](https://leetcode.com/problems/search-a-2d-matrix/)

**Difficulty:** Medium  
**Topics:** Array, Binary Search, Matrix

---

## Problem Statement

You are given an `m x n` matrix with the following properties:

- Each row is sorted in ascending order.
- The first integer of each row is greater than the last integer of the previous row.

Given an integer `target`, return `true` if it exists in the matrix; otherwise, return `false`.

The solution must run in `O(log(m * n))` time.

### Example

```text
Input: matrix = [
  [1, 3, 5, 7],
  [10, 11, 16, 20],
  [23, 30, 34, 60]
]
target = 3

Output: true
```

---

## Core Idea

Treat the matrix as a single sorted one-dimensional array and perform binary search on it.

For a matrix with `rows` rows and `cols` columns, the virtual array contains `rows * cols` elements.

Convert a one-dimensional index `mid` into matrix coordinates:

- `row = mid / cols`
- `col = mid % cols`

This lets the algorithm search the matrix without creating a separate flattened array.

---

## Java Solution

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int low = 0;
        int high = rows * cols - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            int row = mid / cols;
            int col = mid % cols;

            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return false;
    }
}
```

---

## Step-by-Step Explanation

1. Store the number of rows and columns in `rows` and `cols`.
2. Treat the matrix as a sorted array with indices from `0` to `rows * cols - 1`.
3. Calculate `mid` using the standard binary search formula.
4. Convert `mid` to matrix coordinates:
   - Integer division by `cols` gives the row.
   - Remainder after division by `cols` gives the column.
5. If the element equals `target`, return `true`.
6. If the element is smaller than `target`, search the right half.
7. If the element is larger than `target`, search the left half.
8. If the search range becomes empty, return `false`.

---

## Dry Run

```text
matrix = [
  [1, 3, 5, 7],
  [10, 11, 16, 20],
  [23, 30, 34, 60]
]
target = 3
```

The matrix has `3` rows and `4` columns, so the virtual array has `12` elements.

| low | high | mid | Matrix position | Value | Action |
|---:|---:|---:|---|---:|---|
| 0 | 11 | 5 | `[1][1]` | 11 | Search left |
| 0 | 4 | 2 | `[0][2]` | 5 | Search left |
| 0 | 1 | 0 | `[0][0]` | 1 | Search right |
| 1 | 1 | 1 | `[0][1]` | 3 | Target found |

Return `true`.

---

## Edge Cases

- **Target is the first element:** Found at virtual index `0`.
- **Target is the last element:** Found at virtual index `rows * cols - 1`.
- **Target is smaller or larger than every element:** Returns `false`.
- **Single-row matrix:** Binary search works like a normal array search.
- **Single-column matrix:** The index conversion maps each virtual index to the corresponding row.

---

## Complexity Analysis

Let `m` be the number of rows and `n` be the number of columns.

- **Time Complexity:** `O(log(m * n))` — binary search halves the virtual array's search range each iteration.
- **Space Complexity:** `O(1)` — the matrix is searched directly without creating a flattened copy.

---

## Pattern Recognition

When a matrix is globally sorted across rows, it can be treated as a one-dimensional sorted array.

Use binary search on virtual indices and convert each index into a row and column using division and modulo.

---

## Key Takeaway

A globally sorted matrix can be searched with standard binary search. Map each virtual array index to `matrix[mid / cols][mid % cols]` to search without flattening the matrix.
