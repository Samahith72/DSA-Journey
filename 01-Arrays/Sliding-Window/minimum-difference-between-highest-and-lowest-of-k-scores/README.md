# WIN #52 — Minimum Difference Between Highest and Lowest of K Scores

## Problem

[LeetCode 1984 — Minimum Difference Between Highest and Lowest of K Scores](https://leetcode.com/problems/minimum-difference-between-highest-and-lowest-of-k-scores/)

We are given an array `nums`, where each value represents the score of a student.

We need to select exactly `k` students such that the difference between the **highest** and **lowest** selected scores is as small as possible.

The difference is:

```text
highest score - lowest score
```

Our goal is to minimize this difference.

---

## Example 1

```text
Input:
nums = [90]
k = 1

Output:
0
```

There is only one student:

```text
[90]
```

Highest:

```text
90
```

Lowest:

```text
90
```

Difference:

```text
90 - 90 = 0
```

Therefore:

```text
Output = 0
```

---

## Example 2

```text
Input:
nums = [9,4,1,7]
k = 2

Output:
2
```

We need to choose two scores.

Possible pairs include:

```text
[9,4] → 9 - 4 = 5
[9,1] → 9 - 1 = 8
[9,7] → 9 - 7 = 2
[4,1] → 4 - 1 = 3
[4,7] → 7 - 4 = 3
[1,7] → 7 - 1 = 6
```

The minimum difference is:

```text
2
```

---

## My Java Solution

```java
class Solution {
    public int minimumDifference(int[] nums, int k) {
        if(nums.length <= 1){
            return 0;
        }

        Arrays.sort(nums);
        int answer = Integer.MAX_VALUE;

        for(int i=0; i<= nums.length-k;i++){
            int difference = nums[i+k-1] - nums[i];

            answer = Math.min(difference, answer);
        }
        return answer;
    }
}
```

---

## My Thought Process

The first thing to notice is that the original order of the scores does not matter.

We only care about:

```text
highest score
-
lowest score
```

among the selected `k` students.

So the natural first step is to sort the array.

For example:

```text
Before sorting:

[9, 4, 1, 7]

After sorting:

[1, 4, 7, 9]
```

Now the problem becomes much easier.

If we select `k` elements, the best group will be a group of `k` **consecutive elements in the sorted array**.

For:

```text
[1, 4, 7, 9]
```

and:

```text
k = 2
```

the possible windows are:

```text
[1, 4]
[4, 7]
[7, 9]
```

Their differences are:

```text
4 - 1 = 3
7 - 4 = 3
9 - 7 = 2
```

Therefore:

```text
answer = 2
```

---

# Step 1 — Handle the Smallest Case

```java
if(nums.length <= 1){
    return 0;
}
```

If the array contains only one score, then the only possible selection has:

```text
highest = lowest
```

Therefore:

```text
difference = 0
```

For example:

```text
[90]
```

gives:

```text
90 - 90 = 0
```

---

# Step 2 — Sort the Scores

```java
Arrays.sort(nums);
```

This is the key transformation.

Suppose:

```text
nums = [9,4,1,7]
```

After sorting:

```text
nums = [1,4,7,9]
```

Now values that are close together are next to each other.

This allows us to use a sliding window.

---

# Step 3 — Initialize the Answer

```java
int answer = Integer.MAX_VALUE;
```

We need to find the minimum difference.

So we initially use the largest possible integer as the answer:

```text
answer = ∞
```

Conceptually:

```text
answer = very large number
```

Then every calculated difference can replace it if it is smaller.

---

# Step 4 — Create a Window of K Elements

```java
for(int i=0; i<= nums.length-k;i++){
```

The variable `i` represents the beginning of our window.

The window contains exactly `k` elements:

```text
nums[i ... i+k-1]
```

For:

```text
nums = [1,4,7,9]
k = 2
```

the windows are:

```text
i = 0 → [1,4]
i = 1 → [4,7]
i = 2 → [7,9]
```

The condition:

```text
i <= nums.length - k
```

ensures that the window never goes outside the array.

---

# Step 5 — Calculate the Difference

```java
int difference = nums[i+k-1] - nums[i];
```

Because the array is sorted:

```text
nums[i]
```

is the smallest value in the current window.

And:

```text
nums[i+k-1]
```

is the largest value in the current window.

Therefore:

```text
difference =
highest value - lowest value
```

For:

```text
[4,7]
```

we calculate:

```text
7 - 4 = 3
```

---

# Step 6 — Keep the Minimum Difference

```java
answer = Math.min(difference, answer);
```

Every window gives us one possible difference.

We keep the smallest one.

For example:

```text
Window       Difference

[1,4]          3
[4,7]          3
[7,9]          2
```

The answer becomes:

```text
min(3,3,2)
= 2
```

---

# Step 7 — Return the Answer

After checking every possible window:

```java
return answer;
```

The smallest difference is returned.

---

## Complete Walkthrough

Consider:

```text
nums = [9,4,1,7]
k = 2
```

### After Sorting

```text
[1,4,7,9]
```

We now need windows of size `2`.

---

### Window 1

```text
i = 0
```

Window:

```text
[1,4]
```

Highest:

```text
4
```

Lowest:

```text
1
```

Difference:

```text
4 - 1 = 3
```

So:

```text
answer = 3
```

---

### Window 2

```text
i = 1
```

Window:

```text
[4,7]
```

Difference:

```text
7 - 4 = 3
```

Update:

```text
answer = min(3,3)
       = 3
```

---

### Window 3

```text
i = 2
```

Window:

```text
[7,9]
```

Difference:

```text
9 - 7 = 2
```

Update:

```text
answer = min(3,2)
       = 2
```

---

### Final Answer

```text
2
```

---

# Why Does Sorting Work?

This is the most important idea in the problem.

Suppose the sorted scores are:

```text
[1, 4, 7, 9]
```

and we need to choose `2` students.

Suppose we choose:

```text
1 and 9
```

The difference is:

```text
9 - 1 = 8
```

There are two elements between them:

```text
1, 4, 7, 9
   ↑  ↑
```

If we instead choose closer values:

```text
7 and 9
```

the difference becomes:

```text
9 - 7 = 2
```

In general, if we choose `k` values and they are not consecutive in the sorted array, there are unnecessary values between the smallest and largest selected values.

Replacing selected values with some of those values between them cannot increase the range and can potentially reduce it.

Therefore, an optimal selection can always be represented as a **contiguous window of `k` elements after sorting**.

That is why we only need to check:

```text
nums[i+k-1] - nums[i]
```

for every possible `i`.

---

## Pattern Recognition

### Pattern: Sorting + Fixed-Size Sliding Window

This problem combines two important patterns.

### 1. Sorting

First:

```java
Arrays.sort(nums);
```

Sorting puts similar values next to each other.

---

### 2. Fixed-Size Sliding Window

After sorting, we check every group of exactly `k` consecutive elements:

```text
[1,4]
   [4,7]
      [7,9]
```

The window size never changes:

```text
window size = k
```

So this is a:

> **Fixed-Size Sliding Window**

---

## How to Recognize This Pattern

When you see a problem asking:

> Choose exactly `k` elements such that the difference between the maximum and minimum is minimized.

Think:

```text
Sort
  ↓
Create windows of size k
  ↓
highest - lowest
  ↓
Keep minimum
```

This is a very common competitive-programming pattern.

---

## Why We Don't Need to Check Every Combination

Without the sorting insight, we might think:

> There are many ways to choose `k` students, so maybe we need to check every combination.

That would be extremely expensive.

Instead:

```text
Original array
      ↓
    Sort
      ↓
[small → large]
      ↓
Check only consecutive windows of k elements
```

This reduces the problem dramatically.

---

## Edge Cases

### 1. `k = 1`

If we only choose one score:

```text
[90]
```

then:

```text
highest = lowest = 90
```

So the difference is always:

```text
0
```

---

### 2. `k = nums.length`

If we must select every student, there is only one possible selection.

For:

```text
[1,4,7,9]
```

the answer is:

```text
9 - 1 = 8
```

The loop checks only one window.

---

### 3. Duplicate Scores

Consider:

```text
[5,5,5,8]
```

with:

```text
k = 3
```

After sorting:

```text
[5,5,5,8]
```

First window:

```text
[5,5,5]
```

Difference:

```text
5 - 5 = 0
```

So the answer is:

```text
0
```

---

### 4. Already Sorted Array

If the input is already sorted, `Arrays.sort()` simply maintains the ordering and the same sliding-window logic applies.

---

### 5. Unsorted Array

Sorting handles this automatically.

For example:

```text
[20, 5, 12, 8]
```

becomes:

```text
[5,8,12,20]
```

Then we can examine consecutive groups.

---

## Complexity

Let:

```text
n = nums.length
```

### Time Complexity

First we sort the array:

```text
O(n log n)
```

Then we scan the array once:

```text
O(n)
```

Therefore:

```text
O(n log n) + O(n)
```

which simplifies to:

```text
O(n log n)
```

---

### Space Complexity

Your algorithm does not create another array.

The loop itself uses only a few variables:

```text
i
difference
answer
```

Therefore, the **auxiliary space of the algorithm is O(1)**, excluding any internal memory used by Java's `Arrays.sort()` implementation.

---

## Key Learning

### 1. Sorting Can Turn a Combination Problem Into a Window Problem

Initially, the problem appears to ask us to examine many possible combinations of `k` students.

Sorting reveals that we only need to examine consecutive groups.

---

### 2. After Sorting, Range = Last - First

For a sorted window:

```text
[a, b, c, d]
```

the minimum value is:

```text
a
```

and the maximum value is:

```text
d
```

So:

```text
difference = d - a
```

We don't need to inspect every element inside the window.

---

### 3. Fixed-Size Window

The window always contains exactly `k` elements:

```text
nums[i]
...
nums[i+k-1]
```

Therefore:

```text
window size = k
```

---

### 4. Minimize the Range

Every window represents one possible valid group.

We simply calculate:

```java
nums[i+k-1] - nums[i]
```

and keep the smallest result.

---

## Visual Summary

```text
Original:

[9, 4, 1, 7]

        ↓ Sort

[1, 4, 7, 9]

        ↓ k = 2

[1, 4] → 4 - 1 = 3

   [4, 7] → 7 - 4 = 3

      [7, 9] → 9 - 7 = 2

        ↓

Minimum = 2
```

---

## Final Takeaway

The complete strategy is:

```text
Sort the array
      ↓
Create a fixed-size window of k elements
      ↓
For each window:
    highest = nums[i+k-1]
    lowest  = nums[i]
      ↓
Calculate:
    highest - lowest
      ↓
Keep the minimum
```

The main pattern to remember is:

> **When you need to choose `k` values so that their range (maximum − minimum) is minimized, sort the values first and then check every contiguous window of size `k`.**

This gives an efficient **O(n log n)** solution using **Sorting + Fixed-Size Sliding Window**.
