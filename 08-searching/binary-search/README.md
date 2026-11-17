# WIN #136 — 704. Binary Search

[LeetCode Problem](https://leetcode.com/problems/binary-search/)

**Difficulty:** Easy

**Topics:** Array, Binary Search

---

## Problem Statement

Given an array of integers `nums`, which is sorted in ascending order, and an integer `target`, return the index of `target` if it exists in the array.

If the target does not exist, return `-1`.

You must write an algorithm with O(log n) runtime complexity.

### Example 1

**Input:**
```text
nums = [-1,0,3,5,9,12]
target = 9
```

**Output:**
```text
4
```

**Explanation:** `9` exists in `nums` at index `4`.

### Example 2

**Input:**
```text
nums = [-1,0,3,5,9,12]
target = 2
```

**Output:**
```text
-1
```

**Explanation:** `2` does not exist in the array.

---

## Core Idea

Binary Search works on a sorted array by repeatedly dividing the search space into two halves.

Instead of checking every element sequentially, we calculate the middle index and compare its value with the target.

There are three possible outcomes:

1. If `nums[mid] == target`, we have found the target.
2. If `nums[mid] < target`, search the right half.
3. If `nums[mid] > target`, search the left half.

We continue until the target is found or the search range becomes empty.

The key advantage is that every iteration eliminates approximately half of the remaining elements.

---

## Java Solution — Iterative Binary Search

```java
class Solution {
    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while(low <= high){
            int mid = low + ((high - low) / 2);

            if(nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }

        return -1;
    }
}
```

---

## Step-by-Step Explanation

### 1. Initialize the search boundaries

```java
int low = 0;
int high = nums.length - 1;
```

- `low` represents the starting index of the search range.
- `high` represents the ending index of the search range.

Initially, the entire array is the search space.

### 2. Continue while the search range is valid

```java
while(low <= high)
```

As long as `low` is less than or equal to `high`, there are elements left to examine.

When `low > high`, the search range becomes empty, meaning the target does not exist.

### 3. Calculate the middle index

```java
int mid = low + ((high - low) / 2);
```

We calculate the middle index of the current search range.

This form is preferred over:

```java
int mid = (low + high) / 2;
```

because `low + high` can potentially overflow for large integer indices.

### 4. Compare the middle element with the target

**Case 1: Target found**

```java
if(nums[mid] == target){
    return mid;
}
```

If the middle element matches the target, immediately return its index.

**Case 2: Target is greater**

```java
else if(nums[mid] < target){
    low = mid + 1;
}
```

Because the array is sorted, every element to the left of `mid` is smaller than or equal to `nums[mid]`.

Therefore, if the target is greater, we eliminate the left half and continue searching from `mid + 1`.

**Case 3: Target is smaller**

```java
else{
    high = mid - 1;
}
```

If the target is smaller than the middle element, we eliminate the right half and continue searching up to `mid - 1`.

### 5. Return -1 if the target is not found

```java
return -1;
```

If the loop terminates without finding the target, return `-1`.

---

## Dry Run

Consider:

```text
nums = [-1,0,3,5,9,12]
target = 9
```

Initially:

```text
low = 0
high = 5
```

| Iteration | Low | High | Mid | nums[mid] | Decision |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 5 | 2 | 3 | Target is greater, move right |
| 2 | 3 | 5 | 4 | 9 | Target found |

### Iteration 1

```text
low = 0
high = 5

mid = 0 + (5 - 0) / 2
mid = 2
```

Middle element:

```text
nums[2] = 3
```

Since:

```text
3 < 9
```

Update:

```java
low = mid + 1;
```

New search range:

```text
low = 3
high = 5
```

### Iteration 2

```text
mid = 3 + (5 - 3) / 2
mid = 4
```

Middle element:

```text
nums[4] = 9
```

Since:

```text
nums[mid] == target
```

Return:

```text
4
```

**Output:**
```text
4
```

---

## Complexity Analysis

Let `n` be the number of elements in the array.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(log n) | The search space is reduced by approximately half in every iteration. |
| Space | O(1) | Only three variables are maintained: `low`, `high`, and `mid`. |

---

## Edge Cases

| Input | Target | Output |
|---|---:|---:|
| `[]` | 5 | -1 |
| `[5]` | 5 | 0 |
| `[5]` | 2 | -1 |
| `[1,2,3,4,5]` | 1 | 0 |
| `[1,2,3,4,5]` | 5 | 4 |
| `[-10,-5,0,5,10]` | -5 | 1 |

---

## Pattern Recognition

When a problem involves:

- A sorted array.
- Searching for a specific element.
- Finding an index or determining whether an element exists.
- A requirement of O(log n) time complexity.

Think about **Binary Search**.

The fundamental approach is:

1. Maintain two boundaries: `low` and `high`.
2. Calculate the middle index.
3. Compare the middle element with the target.
4. Eliminate the half that cannot contain the target.
5. Repeat until the target is found or the search space becomes empty.

The most important requirement is that the search space must have an ordering that allows one half to be safely eliminated.

---

## Key Takeaway

Binary Search improves searching from O(n) linear traversal to O(log n) by eliminating half of the remaining search space in every iteration.

The central idea is simple:

- Target equals middle element: return the index.
- Target is greater: search right.
- Target is smaller: search left.

**Final complexity: O(log n) time and O(1) extra space.**