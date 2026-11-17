# WIN #137 — 35. Search Insert Position

[LeetCode Problem](https://leetcode.com/problems/search-insert-position/)

**Difficulty:** Easy

**Topics:** Array, Binary Search

---

## Problem Statement

Given a sorted array of distinct integers and a target value, return the index if the target is found.

If the target is not found, return the index where it would be inserted in order.

You must write an algorithm with O(log n) runtime complexity.

### Example 1

**Input:**
```text
nums = [1,3,5,6]
target = 5
```

**Output:**
```text
2
```

**Explanation:** The target `5` exists at index `2`.

### Example 2

**Input:**
```text
nums = [1,3,5,6]
target = 2
```

**Output:**
```text
1
```

**Explanation:** The target `2` should be inserted at index `1`.

### Example 3

**Input:**
```text
nums = [1,3,5,6]
target = 7
```

**Output:**
```text
4
```

**Explanation:** The target `7` should be inserted at the end of the array.

---

## Core Idea

The key observation is that we can solve this problem using standard Binary Search.

We maintain two pointers:

- `low`: Starting index of the search range.
- `high`: Ending index of the search range.

During binary search:

- If `nums[mid] == target`, return `mid`.
- If `nums[mid] < target`, move `low` to `mid + 1`.
- If `nums[mid] > target`, move `high` to `mid - 1`.

The important part is what happens when the loop terminates.

When `low > high`, `low` points to the first position where the target can be inserted without disturbing the sorted order.

Therefore, we simply return `low`.

This approach is also known as finding the **Lower Bound** of the target.

---

## Java Solution — Binary Search

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
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

        return low;
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

We initially consider the entire array as the search space.

### 2. Calculate the middle index

```java
int mid = low + ((high - low) / 2);
```

We calculate the middle position of the current search range.

This avoids potential integer overflow compared with `(low + high) / 2`.

### 3. Compare the middle element with the target

**Case 1: Target found**

```java
if(nums[mid] == target){
    return mid;
}
```

If the middle element equals the target, return its index.

**Case 2: Target is greater**

```java
else if(nums[mid] < target){
    low = mid + 1;
}
```

Since the array is sorted, all elements before or at `mid` are smaller than the target.

Move the left boundary to `mid + 1`.

**Case 3: Target is smaller**

```java
else{
    high = mid - 1;
}
```

All elements after or at `mid` are greater than the target.

Move the right boundary to `mid - 1`.

### 4. Return the insertion position

```java
return low;
```

This is the most important part of the solution.

When the loop terminates:

```text
low > high
```

The two pointers have crossed.

At this point:

- Every index before `low` contains a value smaller than the target.
- Every index from `low` onward contains a value greater than the target.

Therefore, `low` is exactly the correct insertion position.

---

## Dry Run

Consider:

```text
nums = [1,3,5,6]
target = 2
```

Initially:

```text
low = 0
high = 3
```

### Iteration 1

```text
mid = 0 + (3 - 0) / 2
mid = 1

nums[1] = 3
```

Since:

```text
3 > 2
```

Update:

```text
high = mid - 1
high = 0
```

### Iteration 2

```text
low = 0
high = 0

mid = 0 + (0 - 0) / 2
mid = 0

nums[0] = 1
```

Since:

```text
1 < 2
```

Update:

```text
low = mid + 1
low = 1
```

Now:

```text
low = 1
high = 0
```

The loop terminates because `low > high`.

Return:

```text
1
```

**Output:**
```text
1
```

The target `2` should be inserted at index `1`:

```text
Before: [1,3,5,6]
After:  [1,2,3,5,6]
```

---

## Understanding Why Returning `low` Works

Consider the following cases:

| Input | Target | Final `low` | Result |
|---|---:|---:|---|
| `[1,3,5,6]` | 5 | 2 | 2 |
| `[1,3,5,6]` | 2 | 1 | 1 |
| `[1,3,5,6]` | 7 | 4 | 4 |
| `[1,3,5,6]` | 0 | 0 | 0 |
| `[1]` | 1 | 0 | 0 |
| `[]` | 5 | 0 | 0 |

The same binary search handles all these situations without requiring separate insertion-position logic.

---

## Complexity Analysis

Let `n` be the number of elements in the array.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(log n) | The search space is divided approximately in half during every iteration. |
| Space | O(1) | Only `low`, `high`, and `mid` are maintained. |

---

## Pattern Recognition

When a problem asks you to:

- Find an element in a sorted array.
- Return its index if it exists.
- Find where it should be inserted if it does not exist.
- Find the first position where an element can be placed.

Think about **Binary Search — Lower Bound**.

The key insight is that the final value of `low` identifies the first position where the target can be inserted while maintaining sorted order.

---

## Key Takeaway

Standard Binary Search can do more than find an existing element.

When the target is absent, the final `low` pointer naturally identifies its insertion position.

Remember:

- `nums[mid] == target`: Return `mid`.
- `nums[mid] < target`: Move `low` right.
- `nums[mid] > target`: Move `high` left.
- Search ends: Return `low`.

**Final complexity: O(log n) time and O(1) extra space.**