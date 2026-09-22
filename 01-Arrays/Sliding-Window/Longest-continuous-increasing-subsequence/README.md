# WIN #45 — Longest Continuous Increasing Subsequence

## Problem

[LeetCode 674 — Longest Continuous Increasing Subsequence](https://leetcode.com/problems/longest-continuous-increasing-subsequence/)

Given an unsorted integer array `nums`, return the length of the longest **continuous increasing subsequence**.

A continuous increasing subsequence means:

* The elements must be next to each other in the array.
* Every element must be strictly greater than the previous element.

For example:

```text
[1, 3, 5]
```

is continuous and strictly increasing because:

```text
1 < 3 < 5
```

But:

```text
[1, 3, 5, 7]
```

is not a continuous subsequence of:

```text
[1,3,5,4,7]
```

because `7` is separated from `5` by `4`.

### Example 1

```text
Input: nums = [1,3,5,4,7]
Output: 3
```

The longest continuous increasing subsequence is:

```text
[1,3,5]
```

Its length is:

```text
3
```

---

### Example 2

```text
Input: nums = [2,2,2,2,2]
Output: 1
```

Equal values do not form an increasing sequence because the sequence must be **strictly increasing**.

Therefore, every element forms an individual sequence of length `1`.

---

## My Java Solution

```java
class Solution {
    public int findLengthOfLCIS(int[] nums) {

        int currentLength = 1;
        int maxLength = 1;

        for(int i=1;i < nums.length;i++){
            if(nums[i] > nums[i-1]){
                currentLength++;
            }else{
                currentLength =1;
            }

            maxLength = Math.max(maxLength, currentLength);
        }

        return maxLength;
        
    }
}
```

---

## My Thought Process

The important word in this problem is **continuous**.

We cannot skip elements.

For:

```text
[1,3,5,4,7]
```

we check adjacent elements:

```text
1 < 3 ✓
3 < 5 ✓
5 < 4 ✗
4 < 7 ✓
```

Whenever the current element is greater than the previous element, the increasing streak continues.

Otherwise, the streak is broken and we start again from length `1`.

So I maintain two variables:

```text
currentLength → length of the current increasing streak
maxLength     → longest streak found so far
```

---

# Step 1 — Initialize the Lengths

```java
int currentLength = 1;
int maxLength = 1;
```

Why start with `1`?

Every individual element is itself a continuous subsequence of length `1`.

For example:

```text
[5]
```

has an increasing subsequence of length `1`.

Since the constraints guarantee:

```text
nums.length >= 1
```

starting with `1` is safe.

---

# Step 2 — Start From the Second Element

```java
for(int i = 1; i < nums.length; i++){
```

We start from index `1` because we need to compare the current element with the previous element:

```java
nums[i] > nums[i-1]
```

For example:

```text
nums = [1,3,5,4,7]

index:
  0 1 2 3 4
  ↓ ↓ ↓ ↓ ↓
 [1,3,5,4,7]
    ↑
    i
```

---

# Step 3 — Check Whether the Increasing Streak Continues

```java
if(nums[i] > nums[i-1]){
    currentLength++;
}
```

If the current value is strictly greater than the previous value, the current increasing subsequence continues.

For:

```text
[1,3,5]
```

we have:

```text
1 < 3
3 < 5
```

So:

```text
currentLength:
1 → 2 → 3
```

---

# Step 4 — Reset When the Sequence Breaks

If:

```java
nums[i] <= nums[i-1]
```

the sequence is no longer strictly increasing.

So:

```java
else{
    currentLength = 1;
}
```

For:

```text
[1,3,5,4,7]
```

we reach:

```text
5 > 4
```

The increasing sequence:

```text
[1,3,5]
```

has ended.

So we reset:

```text
currentLength = 1
```

because `4` can now become the beginning of a new increasing sequence:

```text
[4,7]
```

---

# Step 5 — Update the Maximum

After processing each element:

```java
maxLength = Math.max(maxLength, currentLength);
```

This keeps track of the longest increasing streak found so far.

For example:

```text
currentLength = 3
maxLength = 3
```

Later:

```text
currentLength = 2
maxLength = 3
```

The maximum remains `3`.

---

## Complete Walkthrough

Consider:

```text
nums = [1,3,5,4,7]
```

Initially:

```text
currentLength = 1
maxLength = 1
```

---

### Index 1

Compare:

```text
1 < 3
```

Increasing.

```text
currentLength = 2
maxLength = 2
```

Current sequence:

```text
[1,3]
```

---

### Index 2

Compare:

```text
3 < 5
```

Increasing.

```text
currentLength = 3
maxLength = 3
```

Current sequence:

```text
[1,3,5]
```

---

### Index 3

Compare:

```text
5 < 4
```

False.

The increasing sequence breaks.

Reset:

```text
currentLength = 1
```

`maxLength` remains:

```text
3
```

---

### Index 4

Compare:

```text
4 < 7
```

Increasing.

```text
currentLength = 2
maxLength = 3
```

The new sequence is:

```text
[4,7]
```

But its length is only `2`, so the maximum remains `3`.

---

### Final Result

```text
maxLength = 3
```

Therefore:

```text
Output = 3
```

---

## Pattern Recognition

### Pattern: One-Pass Traversal + Running Count

The key clue is:

> Find the longest **continuous** increasing subsequence.

Because the elements must be adjacent, we only need to compare:

```text
nums[i]
```

with:

```text
nums[i-1]
```

We don't need:

* Sorting
* HashMap
* Two Pointers
* Dynamic Programming
* Extra arrays

The pattern is simply:

```text
Compare current with previous
          ↓
If increasing → extend streak
          ↓
Otherwise → reset streak
          ↓
Track maximum
```

---

## Important Difference: Subsequence vs Continuous Subsequence

This problem can be confusing because a normal **subsequence** can skip elements.

For:

```text
[1,3,5,4,7]
```

we could choose:

```text
[1,3,5,7]
```

and it would be increasing.

But it is **not continuous** because `4` is between `5` and `7`.

This problem requires:

```text
nums[i], nums[i+1], nums[i+2], ...
```

with no skipped elements.

Therefore, we only need adjacent comparisons.

---

## Why Strictly Increasing Matters

The condition is:

```text
nums[i] > nums[i-1]
```

not:

```text
nums[i] >= nums[i-1]
```

Consider:

```text
[2,2,2,2]
```

Since:

```text
2 > 2
```

is false, every increasing sequence is reset.

Therefore:

```text
currentLength = 1
```

throughout the traversal.

Final answer:

```text
1
```

---

## Another Walkthrough

Consider:

```text
nums = [2,2,2,2,2]
```

Initially:

```text
currentLength = 1
maxLength = 1
```

### Index 1

```text
2 > 2 → false
```

Reset:

```text
currentLength = 1
```

### Index 2

```text
2 > 2 → false
```

Again:

```text
currentLength = 1
```

The same happens for the remaining elements.

Therefore:

```text
maxLength = 1
```

Output:

```text
1
```

---

## Edge Cases

### 1. Single Element

```text
nums = [5]
```

There is only one element.

Therefore:

```text
Output = 1
```

---

### 2. Completely Increasing Array

```text
nums = [1,2,3,4,5]
```

Every comparison is true:

```text
1 < 2 < 3 < 4 < 5
```

Therefore:

```text
Output = 5
```

---

### 3. Completely Decreasing Array

```text
nums = [5,4,3,2,1]
```

Every comparison fails.

Each element starts a new sequence.

Therefore:

```text
Output = 1
```

---

### 4. Equal Values

```text
nums = [2,2,2,2]
```

Equal values are not strictly increasing.

Therefore:

```text
Output = 1
```

---

### 5. Multiple Increasing Sections

```text
nums = [1,2,3,2,3,4,5]
```

Increasing sections are:

```text
[1,2,3]       → length 3
[2,3,4,5]     → length 4
```

Therefore:

```text
Output = 4
```

---

## Complexity

### Time Complexity

We traverse the array exactly once.

Therefore:

```text
O(n)
```

Each element is processed once.

---

### Space Complexity

Only two variables are used to track the lengths:

```text
currentLength
maxLength
```

No additional data structure is required.

Therefore:

```text
O(1)
```

auxiliary space.

---

## Key Learning

### 1. Continuous Means Adjacent

When a problem says **continuous subarray/subsequence**, think:

```text
nums[i] and nums[i-1]
```

We cannot skip elements.

---

### 2. Maintain a Running Streak

A very useful pattern is:

```text
currentLength
```

to represent the length of the condition currently being satisfied.

Whenever the condition breaks:

```java
currentLength = 1;
```

---

### 3. Track Current and Global Maximum Separately

These two variables have different responsibilities:

```text
currentLength → current streak
maxLength     → best streak seen so far
```

This pattern appears in many array and string problems.

---

### 4. No Need for Extra Data Structures

Because the problem only depends on adjacent elements, we can solve it with:

```text
One loop
+
Two counters
```

This is an excellent example of simplifying a problem by identifying exactly what information needs to be remembered.

---

## Final Takeaway

The core idea is:

```text
Start with streak = 1
        ↓
Compare nums[i] with nums[i-1]
        ↓
If nums[i] > nums[i-1]
        ↓
Increase current streak
        ↓
Otherwise reset to 1
        ↓
Keep track of maximum streak
```

The pattern to remember is:

> **For a continuous/contiguous sequence, compare adjacent elements and maintain a running streak.**

This solution achieves **O(n) time** and **O(1) auxiliary space**.
