# WIN #140 — 278. First Bad Version

[LeetCode Problem](https://leetcode.com/problems/first-bad-version/)

**Difficulty:** Easy

**Topics:** Binary Search, Interactive

---

## Problem Statement

You are a product manager and currently leading a team to develop a new product.

Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all versions after a bad version are also considered bad.

Suppose you have `n` versions:

```text
[1, 2, 3, ..., n]
```

You want to find the first bad version.

You are given an API:

```java
boolean isBadVersion(int version);
```

The API returns:

- `true` if the version is bad.
- `false` if the version is good.

Implement a function to find the first bad version while minimizing the number of API calls.

### Example 1

**Input:**
```text
n = 5
bad = 4
```

**Output:**
```text
4
```

**Explanation:**

```text
Versions:  [1, 2, 3, 4, 5]
Status:    [G, G, G, B, B]
```

Version `4` is the first bad version.

### Example 2

**Input:**
```text
n = 1
bad = 1
```

**Output:**
```text
1
```

---

## Core Idea

The key observation is that versions follow a monotonic pattern:

```text
Good Good Good Bad Bad Bad
```

Once a version becomes bad, every version after it is also bad.

This property makes Binary Search an ideal solution.

Instead of checking every version sequentially, we repeatedly divide the search space into two halves.

We maintain:

- `low`: The earliest possible first bad version.
- `high`: The latest possible first bad version.

For every middle version:

1. If `isBadVersion(mid)` returns `true`, the first bad version could be `mid` or an earlier version.
2. If it returns `false`, the first bad version must be after `mid`.

We continue until `low` and `high` point to the same version.

---

## Java Solution — Binary Search

```java
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int low = 1;
        int high = n;

        while(low < high){
            int mid = low + (high - low)/2;

            if(isBadVersion(mid)){
                high = mid;
            }else{
                low = mid+1;
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
int low = 1;
int high = n;
```

Version numbering starts from `1`, so our initial search range is:

\[
[1,n]
\]

Initially, any version could potentially be the first bad version.

### 2. Continue while the boundaries are different

```java
while(low < high)
```

We continue searching while more than one candidate version remains.

Unlike standard binary search, we use `low < high` instead of `low <= high` because we are searching for the boundary between good and bad versions.

When both pointers become equal, we have found the answer.

### 3. Calculate the middle version

```java
int mid = low + (high - low)/2;
```

We calculate the middle of the current search range.

This formula also avoids potential integer overflow from adding `low` and `high` directly.

### 4. Check whether the middle version is bad

**Case 1: Middle version is bad**

```java
if(isBadVersion(mid)){
    high = mid;
}
```

If the middle version is bad, the first bad version could be:

- The current middle version.
- Any earlier version.

Therefore, we move the right boundary to `mid`.

Notice that we do not use `mid - 1`, because `mid` itself might be the first bad version.

**Case 2: Middle version is good**

```java
else{
    low = mid+1;
}
```

If the middle version is good, the first bad version must occur after it.

Therefore, we eliminate the current version and everything before it.

### 5. Return the first bad version

```java
return low;
```

When the loop terminates:

```text
low == high
```

Both pointers identify the first bad version.

We return `low`.

---

## Dry Run

Consider:

```text
n = 5
First bad version = 4
```

Initial search range:

```text
low = 1
high = 5
```

| Iteration | Low | High | Mid | API Result | Decision |
|---|---:|---:|---:|---|---|
| 1 | 1 | 5 | 3 | Good (`false`) | `low = mid + 1` |
| 2 | 4 | 5 | 4 | Bad (`true`) | `high = mid` |

### Iteration 1

```text
low = 1
high = 5

mid = 1 + (5 - 1) / 2
mid = 3
```

Check:

```java
isBadVersion(3)
```

Result:

```text
false
```

Version `3` is good, so the first bad version must be after it.

Update:

```java
low = mid + 1;
```

New range:

```text
low = 4
high = 5
```

### Iteration 2

```text
mid = 4 + (5 - 4) / 2
mid = 4
```

Check:

```java
isBadVersion(4)
```

Result:

```text
true
```

Version `4` is bad. It could be the first bad version, so we retain it.

Update:

```java
high = mid;
```

New range:

```text
low = 4
high = 4
```

The loop terminates.

**Output:**
```text
4
```

---

## Understanding Why We Use high = mid

This is one of the most important details in this implementation.

Consider:

```text
Versions: [1, 2, 3, 4, 5]
Status:   [G, G, B, B, B]
```

Suppose:

```text
low = 1
high = 5
mid = 3
```

If version `3` is bad, it might be the first bad version.

Therefore, we must retain it:

```java
high = mid;
```

If we instead used:

```java
high = mid - 1;
```

we could accidentally eliminate the correct answer.

The general rule is:

- Bad version: Keep `mid` as a possible answer.
- Good version: Eliminate `mid` and search to its right.

---

## Edge Cases

| Input | First Bad Version | Output |
|---|---:|---:|
| `n = 1` | 1 | 1 |
| `n = 5` | 1 | 1 |
| `n = 5` | 5 | 5 |
| `n = 5` | 3 | 3 |
| `n = 10` | 7 | 7 |

---

## Complexity Analysis

Let `n` be the total number of versions.

| Complexity | Value | Explanation |
|---|---|---|
| Time | O(log n) | The search space is approximately halved after each API call. |
| Space | O(1) | Only `low`, `high`, and `mid` are maintained. |

**API Calls:** O(log n), which minimizes the number of version checks compared with a linear search.

---

## Pattern Recognition

When a problem involves:

- A sequence of values.
- A condition that changes from false to true.
- Finding the first position where the condition becomes true.
- A monotonic search space.

Think about **Binary Search on the Answer — First True Position**.

The important approach is:

1. If the middle value satisfies the condition, search left while retaining it.
2. If it does not satisfy the condition, search right.
3. Continue until both boundaries converge.

This pattern is commonly used for finding the first occurrence, minimum valid answer, and transition points.

---

## Key Takeaway

First Bad Version is a classic example of Binary Search on a monotonic condition.

The important distinction from ordinary binary search is that we are not searching for an exact value. We are searching for the first position where a condition becomes true.

Remember:

- Bad (`true`): `high = mid`
- Good (`false`): `low = mid + 1`
- Stop when: `low == high`
- Return: `low`

**Final complexity: O(log n) time and O(1) extra space.**