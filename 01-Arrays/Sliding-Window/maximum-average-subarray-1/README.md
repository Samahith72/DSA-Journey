# WIN #43 — Maximum Average Subarray I

## Problem

[LeetCode 643 — Maximum Average Subarray I](https://leetcode.com/problems/maximum-average-subarray-i/)

Given an integer array `nums` and an integer `k`, find a **contiguous subarray of length exactly `k`** that has the maximum average value.

Return the maximum average.

The answer is accepted if the calculation error is less than `10^-5`.

### Example 1

```text
Input: nums = [1,12,-5,-6,50,3], k = 4
Output: 12.75000
```

The subarray with the maximum average is:

```text
[12, -5, -6, 50]
```

Its sum is:

```text
12 - 5 - 6 + 50 = 51
```

Therefore:

```text
51 / 4 = 12.75
```

### Example 2

```text
Input: nums = [5], k = 1
Output: 5.00000
```

The only possible subarray is:

```text
[5]
```

So the maximum average is `5`.

---

## My Java Solution

```java
class Solution {
    public double findMaxAverage(int[] nums, int k) {

        int sum =0;

        for(int i=0;i < k;i++){
            sum+= nums[i];
        }

        int maxSum = sum;

        for(int i = k; i< nums.length;i++){
            sum = sum - nums[i-k] + nums[i];
            maxSum = Math.max(sum, maxSum);
        }

        return (double)maxSum / k;
        
    }
}
```

---

## My Thought Process

The important observation is that the subarray must always have a **fixed length `k`**.

For example:

```text
nums = [1,12,-5,-6,50,3]
k = 4
```

The possible windows are:

```text
[1, 12, -5, -6]
[12, -5, -6, 50]
[-5, -6, 50, 3]
```

Each window has exactly `4` elements.

A brute-force approach would calculate the sum of every window from scratch.

That would repeatedly add the same elements.

Instead, I use a **Sliding Window**.

The idea is:

```text
Current Window
      ↓
Remove the element leaving the window
      ↓
Add the new element entering the window
      ↓
Compare the new sum with maxSum
```

---

# Step 1 — Calculate the First Window

First, calculate the sum of the first `k` elements:

```java
int sum = 0;

for(int i = 0; i < k; i++){
    sum += nums[i];
}
```

For:

```text
nums = [1,12,-5,-6,50,3]
k = 4
```

the first window is:

```text
[1, 12, -5, -6]
```

Its sum is:

```text
1 + 12 - 5 - 6 = 2
```

So:

```text
sum = 2
```

---

# Step 2 — Store the Maximum Sum

The first window is currently the best window we have seen.

So:

```java
int maxSum = sum;
```

Initially:

```text
maxSum = 2
```

---

# Step 3 — Slide the Window

Now start from index `k`:

```java
for(int i = k; i < nums.length; i++){
```

For every new element, we:

1. Remove the element that leaves the window.
2. Add the new element entering the window.

This is done using:

```java
sum = sum - nums[i-k] + nums[i];
```

This single line is the heart of the solution.

---

# Step 4 — Understand the Sliding Formula

Suppose:

```text
nums = [1, 12, -5, -6, 50, 3]
k = 4
```

Current window:

```text
[1, 12, -5, -6]
```

Sum:

```text
2
```

When the window moves one position:

```text
[12, -5, -6, 50]
```

The element `1` leaves:

```text
sum - 1
```

The element `50` enters:

```text
+ 50
```

So:

```text
2 - 1 + 50 = 51
```

The formula:

```java
sum = sum - nums[i-k] + nums[i];
```

does exactly this.

---

# Step 5 — Track the Maximum Sum

After calculating the new window sum:

```java
maxSum = Math.max(sum, maxSum);
```

For our example:

```text
First window:
[1, 12, -5, -6]
sum = 2
maxSum = 2
```

Second window:

```text
[12, -5, -6, 50]
sum = 51
maxSum = 51
```

Third window:

```text
[-5, -6, 50, 3]
sum = 42
maxSum = 51
```

Therefore:

```text
maxSum = 51
```

---

# Step 6 — Convert Maximum Sum to Average

The problem asks for the **average**, not the sum.

The average of a window of length `k` is:

```text
sum / k
```

So:

```java
return (double)maxSum / k;
```

For:

```text
maxSum = 51
k = 4
```

we get:

```text
51 / 4 = 12.75
```

The explicit cast to `double` is important because integer division would otherwise lose the decimal part.

---

## Complete Walkthrough

Consider:

```text
nums = [1,12,-5,-6,50,3]
k = 4
```

### Window 1

```text
[1, 12, -5, -6]
```

Sum:

```text
1 + 12 - 5 - 6 = 2
```

```text
sum = 2
maxSum = 2
```

---

### Window 2

Remove:

```text
1
```

Add:

```text
50
```

New window:

```text
[12, -5, -6, 50]
```

New sum:

```text
2 - 1 + 50 = 51
```

```text
sum = 51
maxSum = 51
```

---

### Window 3

Remove:

```text
12
```

Add:

```text
3
```

New window:

```text
[-5, -6, 50, 3]
```

New sum:

```text
51 - 12 + 3 = 42
```

```text
sum = 42
maxSum = 51
```

---

### Final Result

Maximum sum:

```text
51
```

Average:

```text
51 / 4 = 12.75
```

Therefore:

```text
Output = 12.75
```

---

## Pattern Recognition

### Pattern: Fixed-Size Sliding Window

This is one of the most important **Sliding Window** patterns.

The key clue is:

> Find the best/maximum/minimum value among all **contiguous subarrays of exactly `k` elements**.

Whenever you see:

```text
Contiguous
+
Fixed size k
```

think:

```text
Sliding Window
```

### Basic Pattern

```text
[ a b c d ] e f
  ← k = 4 →

Slide:

a [ b c d e ] f

Slide:

a b [ c d e f ]
```

Instead of recalculating every window:

```text
newSum = oldSum - outgoingElement + incomingElement
```

This is what makes the solution efficient.

---

## Why Sliding Window Works

Suppose the current window is:

```text
[a, b, c, d]
```

Its sum is:

```text
a + b + c + d
```

The next window is:

```text
[b, c, d, e]
```

There is no need to calculate:

```text
b + c + d + e
```

from scratch.

We already know:

```text
a + b + c + d
```

So:

```text
newSum = oldSum - a + e
```

Only two operations are required.

This reduces the repeated work significantly.

---

## Edge Cases

### 1. `k = 1`

```text
nums = [5]
k = 1
```

Each window contains one element.

The answer is simply the largest element.

```text
Output = 5.0
```

---

### 2. `k = n`

If:

```text
k == nums.length
```

there is only one possible window.

For example:

```text
nums = [1,2,3,4]
k = 4
```

Sum:

```text
10
```

Average:

```text
10 / 4 = 2.5
```

---

### 3. Negative Numbers

The algorithm works with negative values as well.

For example:

```text
nums = [-5,-2,-8,-1]
k = 2
```

Possible windows:

```text
[-5,-2] → -7
[-2,-8] → -10
[-8,-1] → -9
```

Maximum sum is:

```text
-7
```

So maximum average is:

```text
-7 / 2 = -3.5
```

---

### 4. Maximum Window Sum Can Be Negative

`maxSum` is initialized using the first window:

```java
int maxSum = sum;
```

This is important.

We should **not** initialize:

```java
int maxSum = 0;
```

because all possible window sums could be negative.

For example:

```text
nums = [-5,-6,-7]
k = 2
```

The maximum sum is `-11`, not `0`.

Initializing with the first actual window handles this correctly.

---

## Complexity

### Time Complexity

The first window takes:

```text
O(k)
```

Then we traverse the remaining elements once:

```text
O(n-k)
```

Therefore:

```text
O(k) + O(n-k) = O(n)
```

So the overall time complexity is:

```text
O(n)
```

---

### Space Complexity

Only a few variables are used:

```text
sum
maxSum
i
k
```

No additional array, HashMap, or list is created.

Therefore:

```text
O(1)
```

auxiliary space.

---

## Key Learning

### 1. Fixed-Size Windows Are a Strong Sliding Window Signal

Whenever you see:

```text
subarray of length k
```

immediately consider a fixed-size sliding window.

---

### 2. Don't Recalculate What You Already Know

Instead of calculating every window independently:

```text
Window 1 → calculate everything
Window 2 → calculate everything again
Window 3 → calculate everything again
```

reuse the previous result:

```text
Previous Sum
    ↓
Remove outgoing element
    ↓
Add incoming element
    ↓
New Sum
```

---

### 3. Track the Sum, Not the Average

Since every window has exactly `k` elements:

```text
average = sum / k
```

Because `k` is the same for every window, the window with the maximum sum will also have the maximum average.

So we only need to track:

```java
maxSum
```

and perform the division once at the end.

---

### 4. Initialize Maximum From the First Window

This is an important implementation detail:

```java
int maxSum = sum;
```

rather than:

```java
int maxSum = 0;
```

This makes the solution work correctly when all numbers are negative.

---

## Final Takeaway

The core idea is:

```text
Build first window
      ↓
Store its sum
      ↓
Slide the window
      ↓
Remove outgoing element
      ↓
Add incoming element
      ↓
Update maximum sum
      ↓
Divide by k
```

The most important formula to remember is:

```text
newSum = oldSum - outgoing + incoming
```

Whenever a problem asks for the **maximum/minimum value over contiguous subarrays of a fixed size**, think:

> **Fixed-Size Sliding Window**

This solution achieves **O(n) time** and **O(1) auxiliary space**.
