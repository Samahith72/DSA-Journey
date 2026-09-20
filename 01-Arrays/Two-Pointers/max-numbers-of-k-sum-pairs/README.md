# WIN #41 — Max Number of K-Sum Pairs

## Problem

[LeetCode 1679 — Max Number of K-Sum Pairs](https://leetcode.com/problems/max-number-of-k-sum-pairs/)

Given an integer array `nums` and an integer `k`, we can perform the following operation:

* Pick two numbers from the array whose sum is exactly `k`.
* Remove those two numbers.
* Count the operation.

Return the **maximum number of operations** that can be performed.

### Example 1

```text
Input: nums = [1,2,3,4], k = 5
Output: 2
```

We can form:

```text
1 + 4 = 5
2 + 3 = 5
```

So we can perform `2` operations.

### Example 2

```text
Input: nums = [3,1,3,4,3], k = 6
Output: 1
```

The only possible pair is:

```text
3 + 3 = 6
```

There are three `3`s, so only one pair can be formed.

Therefore:

```text
Output = 1
```

---

## My Java Solution

```java
class Solution {
    public int maxOperations(int[] nums, int k) {
        Arrays.sort(nums);

        int left = 0;
        int right = nums.length-1;
        int ops =0;

        while(left < right)
        {
            int sum = nums[left] + nums[right];
            if(sum == k){
                ops++;
                left++;
                right--;
            }

            else if(sum < k){
                left++;
            }else{
                right--;
            }
        }

        return ops;
    }
}
```

---

## My Thought Process

The main challenge is finding as many pairs as possible where:

```text
nums[i] + nums[j] = k
```

A brute-force approach would try every possible pair.

That would take:

```text
O(n²)
```

which is too slow for:

```text
n <= 100000
```

So I first sort the array.

Once the array is sorted, I can use **two pointers**:

```text
left  → smallest element
right → largest element
```

Then I compare their sum with `k`.

```text
sum == k  → found a pair
sum < k   → need a larger value
sum > k   → need a smaller value
```

This lets us eliminate impossible choices efficiently.

---

# Step 1 — Sort the Array

First:

```java
Arrays.sort(nums);
```

For example:

```text
nums = [1,2,3,4]
k = 5
```

The array is already sorted:

```text
[1, 2, 3, 4]
 ↑        ↑
left     right
```

If the input was:

```text
[3,1,4,2]
```

after sorting:

```text
[1,2,3,4]
```

Sorting gives us the ordering needed for the two-pointer strategy.

---

# Step 2 — Initialize Two Pointers

```java
int left = 0;
int right = nums.length-1;
```

So:

```text
left  → smallest number
right → largest number
```

For:

```text
[1,2,3,4]
```

we start with:

```text
[1, 2, 3, 4]
 ↑        ↑
 L        R
```

---

# Step 3 — Calculate the Pair Sum

Inside the loop:

```java
int sum = nums[left] + nums[right];
```

Now we have three possible situations.

---

# Step 4 — If Sum Equals K

```java
if(sum == k){
    ops++;
    left++;
    right--;
}
```

This means we found a valid pair.

For:

```text
[1,2,3,4]
k = 5
```

we start with:

```text
1 + 4 = 5
```

So:

```text
ops++;
```

and both numbers are considered used:

```text
left++;
right--;
```

Now:

```text
[1, 2, 3, 4]
 ↑        ↑
used    used
```

The pointers move inward:

```text
    [2, 3]
     ↑  ↑
     L  R
```

---

# Step 5 — If Sum Is Smaller Than K

```java
else if(sum < k){
    left++;
}
```

Suppose:

```text
nums = [1,2,3,7]
k = 10
```

Initially:

```text
1 + 7 = 8
```

Since:

```text
8 < 10
```

we need a **larger sum**.

Because the array is sorted, the only useful direction is to increase `left`.

```text
left++;
```

Now we try:

```text
2 + 7 = 9
```

Then:

```text
3 + 7 = 10
```

We found a valid pair.

### Why not move `right`?

Moving `right` left would make the sum even smaller.

So when:

```text
sum < k
```

we must increase `left`.

---

# Step 6 — If Sum Is Greater Than K

```java
else{
    right--;
}
```

Suppose:

```text
nums = [1,3,8,10]
k = 10
```

Initially:

```text
1 + 10 = 11
```

This is too large.

We need a smaller sum.

Because the array is sorted, decreasing `right` gives us a smaller value:

```text
1 + 8 = 9
```

So:

```java
right--;
```

### Why not move `left`?

Moving `left` forward would increase the sum even more.

So when:

```text
sum > k
```

we decrease `right`.

---

# Step 7 — Stop When Pointers Meet

The loop condition is:

```java
while(left < right)
```

Once:

```text
left >= right
```

there aren't two unused elements available to form another pair.

Therefore, we stop.

---

## Complete Walkthrough

Consider:

```text
nums = [1,2,3,4]
k = 5
```

### Initial State

```text
[1, 2, 3, 4]
 ↑        ↑
 L        R
```

Calculate:

```text
1 + 4 = 5
```

Valid pair.

```text
ops = 1
```

Move both pointers:

```text
[1, 2, 3, 4]
    ↑  ↑
    L  R
```

---

### Second Pair

Now:

```text
2 + 3 = 5
```

Another valid pair.

```text
ops = 2
```

Move both pointers:

```text
left++
right--
```

Now:

```text
left >= right
```

Stop.

Final answer:

```text
2
```

---

## Another Walkthrough

Consider:

```text
nums = [3,1,3,4,3]
k = 6
```

### Step 1 — Sort

```text
[1,3,3,3,4]
```

Pointers:

```text
[1, 3, 3, 3, 4]
 ↑           ↑
 L           R
```

Calculate:

```text
1 + 4 = 5
```

Since:

```text
5 < 6
```

move `left`.

```text
[1, 3, 3, 3, 4]
    ↑        ↑
    L        R
```

Now:

```text
3 + 4 = 7
```

Since:

```text
7 > 6
```

move `right`.

```text
[1, 3, 3, 3, 4]
    ↑     ↑
    L     R
```

Now:

```text
3 + 3 = 6
```

Valid pair.

```text
ops = 1
```

Move both pointers.

Only one `3` remains, so no second pair can be formed.

Final answer:

```text
1
```

---

## Pattern Recognition

### Pattern: Sorting + Two Pointers + Greedy

This problem has a very recognizable pattern:

```text
Find maximum number of pairs
+
Pair must satisfy a condition
+
Numbers can be ordered
```

That is a strong signal for:

> **Sort + Two Pointers**

After sorting:

```text
smallest ← left
largest  ← right
```

We use their sum to decide which pointer to move.

### Decision Rule

```text
sum == k
    ↓
Found a pair
Move both pointers

sum < k
    ↓
Need a larger value
Move left

sum > k
    ↓
Need a smaller value
Move right
```

---

## Why This Greedy Approach Works

The goal is not just to find **a** pair.

We want to find the **maximum number of pairs**.

When:

```text
nums[left] + nums[right] == k
```

we immediately use both elements.

Because `left` is the smallest available number and `right` is the largest available number, this pairing consumes two elements that can form a valid pair without leaving a better alternative that would increase the total number of pairs.

The sorting allows us to make this decision locally while still maximizing the number of operations.

---

## Edge Cases

### 1. No Pair Exists

```text
nums = [1,2,3]
k = 10
```

No two numbers sum to `10`.

Result:

```text
0
```

---

### 2. Duplicate Values

```text
nums = [3,3,3,3]
k = 6
```

Pairs:

```text
3 + 3 = 6
3 + 3 = 6
```

Result:

```text
2
```

---

### 3. Only One Element

```text
nums = [5]
k = 10
```

A pair cannot be formed.

Result:

```text
0
```

---

### 4. Many Possible Values but Limited Frequency

```text
nums = [1,1,1,4]
k = 5
```

Only one `4` exists.

Therefore:

```text
1 + 4 = 5
```

can happen only once.

Result:

```text
1
```

---

## Complexity

### Time Complexity

Sorting takes:

```text
O(n log n)
```

The two-pointer traversal takes:

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

The two-pointer algorithm itself uses:

```text
O(1)
```

extra space.

So the **algorithmic auxiliary space** is:

```text
O(1)
```

apart from the sorting implementation's internal stack/workspace.

---

## Key Learning

### 1. Sorting Can Turn Pair Searching Into Two Pointers

Without sorting, finding complementary pairs can require a HashMap.

With sorting, we can use:

```text
left + right
```

to efficiently determine which direction to move.

---

### 2. The Sum Tells Us Which Pointer to Move

This is the most important observation:

```text
sum < k → left++
sum > k → right--
sum = k → both++
```

Remembering this rule makes many sorted pair problems easier.

---

### 3. Greedy Means Making the Best Local Choice

Whenever we find:

```text
nums[left] + nums[right] == k
```

we consume that pair immediately.

There is no need to keep searching for another combination involving those same elements.

---

### 4. Duplicates Are Naturally Handled

We do not need a separate duplicate-handling mechanism.

For example:

```text
[2,2,2,2]
k = 4
```

The pointers naturally create:

```text
2 + 2
2 + 2
```

and stop when no two elements remain.

---

## Final Takeaway

The core idea is simple:

```text
Sort the array
      ↓
Put one pointer at each end
      ↓
Check their sum
      ↓
sum == k → count pair + move both
sum < k  → move left
sum > k  → move right
      ↓
Repeat until pointers meet
```

The pattern to remember is:

> **When you need the maximum number of pairs with a target sum, sorting + two pointers is a powerful greedy approach.**

This solution runs in **O(n log n) time** and uses **O(1) auxiliary space**.
