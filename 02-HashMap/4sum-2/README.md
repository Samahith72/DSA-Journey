# WIN #57 — 4Sum II

## Problem

[LeetCode 454 — 4Sum II](https://leetcode.com/problems/4sum-ii/)

Given four integer arrays `nums1`, `nums2`, `nums3`, and `nums4` of equal length `n`, return the number of tuples `(i, j, k, l)` such that:

```text
nums1[i] + nums2[j] + nums3[k] + nums4[l] == 0
```

The indices can be chosen independently from each array.

The important requirement is to count **all possible tuples** whose sum is zero.

---

## Example 1

```text
Input:
nums1 = [1,2]
nums2 = [-2,-1]
nums3 = [-1,2]
nums4 = [0,2]

Output:
2
```

The two valid tuples are:

```text
(0, 0, 0, 1)
```

because:

```text
1 + (-2) + (-1) + 2 = 0
```

and:

```text
(1, 1, 0, 0)
```

because:

```text
2 + (-1) + (-1) + 0 = 0
```

Therefore:

```text
answer = 2
```

---

## Example 2

```text
Input:
nums1 = [0]
nums2 = [0]
nums3 = [0]
nums4 = [0]

Output:
1
```

There is only one possible tuple:

```text
(0,0,0,0)
```

and:

```text
0 + 0 + 0 + 0 = 0
```

Therefore:

```text
answer = 1
```

---

## My Java Solution

```java
class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        
        HashMap<Integer, Integer> map = new HashMap<>();

        for(int a : nums1){
            for(int b : nums2){
                int sum = a+b;

                map.put(sum, map.getOrDefault(sum,0)+1);
            }
        }

        int answer =0;

        for(int c: nums3){
            for(int d: nums4){
                int sum = c+d;
                int required = -sum;
                answer += map.getOrDefault(required, 0);
            }
        }

        return answer;
    }
}
```

---

## My Thought Process

At first, the problem looks like we need to check every possible combination:

```text
nums1
   ↓
nums2
   ↓
nums3
   ↓
nums4
```

That would mean four nested loops:

```java
for(a : nums1)
    for(b : nums2)
        for(c : nums3)
            for(d : nums4)
```

For every combination, we would check:

```text
a + b + c + d == 0
```

But if each array has `n` elements, this would take:

```text
O(n⁴)
```

which is too expensive.

So instead of considering all four arrays at once, we split them into two groups:

```text
(nums1 + nums2)
```

and:

```text
(nums3 + nums4)
```

The equation:

```text
a + b + c + d = 0
```

can be rearranged as:

```text
a + b = -(c + d)
```

This is the key observation.

So we can:

```text
Calculate all nums1 + nums2 sums
            ↓
Store their frequencies in HashMap
            ↓
Calculate all nums3 + nums4 sums
            ↓
Look for -(c+d) in the HashMap
```

This reduces the problem from:

```text
O(n⁴)
```

to:

```text
O(n²)
```

---

# Step 1 — Create a HashMap

```java
HashMap<Integer, Integer> map = new HashMap<>();
```

The HashMap stores:

```text
sum → frequency
```

For example, if the pair sums are:

```text
3
3
5
-1
3
```

the map becomes:

```text
3  → 3
5  → 1
-1 → 1
```

The value represents how many different index pairs produce that sum.

This frequency is extremely important because we are counting **tuples**, not just checking whether a sum exists.

---

# Step 2 — Calculate All Pair Sums of nums1 and nums2

```java
for(int a : nums1){
    for(int b : nums2){
        int sum = a+b;

        map.put(sum, map.getOrDefault(sum,0)+1);
    }
}
```

We consider every pair:

```text
a ∈ nums1
b ∈ nums2
```

and calculate:

```text
a + b
```

There are:

```text
n × n = n²
```

possible pairs.

---

## Example

Consider:

```text
nums1 = [1,2]
nums2 = [-2,-1]
```

The possible pair sums are:

```text
1 + (-2) = -1
1 + (-1) = 0

2 + (-2) = 0
2 + (-1) = 1
```

Therefore:

```text
sum = -1 → frequency 1
sum =  0 → frequency 2
sum =  1 → frequency 1
```

The HashMap becomes:

```text
{
    -1 : 1,
     0 : 2,
     1 : 1
}
```

Notice that `0` occurs twice.

That means there are **two different `(i,j)` pairs** whose sum is `0`.

---

# Step 3 — Why Store Frequency?

Suppose:

```text
nums1 = [1,2]
nums2 = [-1,-2]
```

Pair sums include:

```text
1 + (-1) = 0
2 + (-2) = 0
```

So:

```text
0 → 2
```

We cannot simply store:

```text
0 → true
```

because we need to know how many pairs produce that sum.

If another pair from `nums3` and `nums4` requires:

```text
0
```

then there are actually:

```text
2
```

valid tuples.

Therefore:

```java
map.put(sum, map.getOrDefault(sum,0)+1);
```

keeps track of the number of ways to produce every pair sum.

---

# Step 4 — Initialize the Answer

```java
int answer = 0;
```

This stores the total number of valid tuples.

Initially:

```text
answer = 0
```

---

# Step 5 — Calculate Pair Sums of nums3 and nums4

```java
for(int c: nums3){
    for(int d: nums4){
        int sum = c+d;
```

We again generate all possible pair sums.

For every:

```text
c ∈ nums3
d ∈ nums4
```

we calculate:

```text
c + d
```

There are:

```text
n²
```

such pairs.

---

# Step 6 — Find the Required Sum

The original equation is:

```text
a + b + c + d = 0
```

Move `(c+d)` to the other side:

```text
a + b = -(c+d)
```

So if:

```text
c + d = 3
```

then we need:

```text
a + b = -3
```

This is why we calculate:

```java
int required = -sum;
```

For example:

```text
c + d = 2
```

then:

```text
required = -2
```

We now ask the HashMap:

```text
How many nums1 + nums2 pairs have a sum of -2?
```

---

# Step 7 — Add the Frequency

```java
answer += map.getOrDefault(required, 0);
```

This is the most important line in the second half of the solution.

Suppose:

```text
required = -2
```

and the HashMap contains:

```text
-2 → 3
```

That means there are:

```text
3
```

different `(i,j)` pairs satisfying:

```text
nums1[i] + nums2[j] = -2
```

For the current `(k,l)` pair from `nums3` and `nums4`, all three combinations create a valid tuple.

Therefore:

```text
answer += 3
```

---

# Step 8 — Return the Answer

After processing every pair from `nums3` and `nums4`:

```java
return answer;
```

The answer contains the total number of valid tuples.

---

# Complete Walkthrough

Consider:

```text
nums1 = [1,2]
nums2 = [-2,-1]
nums3 = [-1,2]
nums4 = [0,2]
```

We need:

```text
a + b + c + d = 0
```

---

## Step 1 — Calculate nums1 + nums2

Pairs:

```text
1 + (-2) = -1
1 + (-1) = 0

2 + (-2) = 0
2 + (-1) = 1
```

HashMap:

```text
{
    -1 : 1,
     0 : 2,
     1 : 1
}
```

---

## Step 2 — Process nums3 + nums4

Take:

```text
c = -1
d = 0
```

Then:

```text
c + d = -1
```

Required:

```text
-(-1) = 1
```

The HashMap contains:

```text
1 → 1
```

Therefore:

```text
answer = 1
```

This corresponds to:

```text
2 + (-1) + (-1) + 0 = 0
```

---

## Next Pair

Take:

```text
c = -1
d = 2
```

Then:

```text
c + d = 1
```

Required:

```text
-1
```

The HashMap contains:

```text
-1 → 1
```

So:

```text
answer = 2
```

This corresponds to:

```text
1 + (-2) + (-1) + 2 = 0
```

---

## Next Pair

Take:

```text
c = 2
d = 0
```

Then:

```text
c + d = 2
```

Required:

```text
-2
```

The HashMap does not contain `-2`.

Therefore:

```text
answer += 0
```

Answer remains:

```text
2
```

---

## Final Pair

Take:

```text
c = 2
d = 2
```

Then:

```text
c + d = 4
```

Required:

```text
-4
```

Again, `-4` does not exist.

So:

```text
answer = 2
```

Final result:

```text
2
```

---

# The Mathematical Trick

The entire problem can be transformed from:

```text
a + b + c + d = 0
```

into:

```text
a + b = -(c + d)
```

This allows us to split four arrays into two groups:

```text
┌───────────────┐
│ nums1 + nums2 │
└───────┬───────┘
        │
        │ Store frequencies
        ↓
     HashMap
        ↑
        │ Lookup required sum
        │
┌───────┴───────┐
│ nums3 + nums4 │
└───────────────┘
```

---

# Why We Use HashMap Instead of HashSet

A `HashSet` would only tell us:

```text
Does this sum exist?
```

But this problem asks:

```text
How many tuples exist?
```

So we need the **frequency** of every pair sum.

Therefore:

```text
HashSet
```

is not enough.

We need:

```text
HashMap<sum, frequency>
```

For example:

```text
0 → 4
```

means:

```text
There are 4 different pairs whose sum is 0.
```

---

# Why We Split the Arrays Into Two Groups

The original problem has four arrays.

Trying every combination gives:

```text
n × n × n × n
```

which is:

```text
O(n⁴)
```

Instead, we divide them:

```text
Group 1:
nums1 + nums2

Group 2:
nums3 + nums4
```

Each group has:

```text
n × n = n²
```

pairs.

Therefore:

```text
First group  → O(n²)
Second group → O(n²)
```

Total:

```text
O(n²) + O(n²)
```

which is:

```text
O(n²)
```

This is the core optimization.

---

# Pattern Recognition

## Pattern: HashMap + Pair Sum + Frequency

This problem is an important **pair-sum decomposition** pattern.

The general strategy is:

```text
Four values need to satisfy a target
            ↓
Split them into two pairs
            ↓
Calculate all pair sums of first two arrays
            ↓
Store frequencies in HashMap
            ↓
Calculate pair sums of remaining arrays
            ↓
Find the complementary sum
            ↓
Add its frequency
```

The key equation is:

```text
a + b + c + d = 0
```

which becomes:

```text
a + b = -(c + d)
```

So the important pattern to remember is:

> **When a problem asks for the number of combinations across four arrays that satisfy a target sum, split the four values into two pairs and use a HashMap to store the frequencies of one side's pair sums.**

---

# Why Frequency Matters

Consider:

```text
nums1 = [1,1]
nums2 = [-1,-1]
```

Every combination produces:

```text
1 + (-1) = 0
```

There are:

```text
2 × 2 = 4
```

different pairs.

Therefore:

```text
map:
0 → 4
```

Now suppose:

```text
nums3 = [0]
nums4 = [0]
```

The required sum is:

```text
-(0 + 0) = 0
```

The HashMap gives:

```text
4
```

Therefore:

```text
answer = 4
```

This demonstrates why storing only unique sums would produce the wrong result.

---

# Edge Cases

## 1. All Arrays Contain Zero

```text
nums1 = [0]
nums2 = [0]
nums3 = [0]
nums4 = [0]
```

There is exactly:

```text
1
```

valid tuple.

Answer:

```text
1
```

---

## 2. Multiple Duplicate Values

```text
nums1 = [1,1]
nums2 = [-1,-1]
nums3 = [0,0]
nums4 = [0,0]
```

The first two arrays create:

```text
0 → 4
```

The second two arrays create:

```text
0
0
0
0
```

Each of those four pairs can combine with all four pairs from the first two arrays.

Therefore:

```text
4 × 4 = 16
```

valid tuples.

---

## 3. No Valid Combination

Suppose no pair sum from:

```text
nums1 + nums2
```

matches the required negative pair sum from:

```text
nums3 + nums4
```

Then:

```java
map.getOrDefault(required, 0)
```

returns:

```text
0
```

and the answer remains unchanged.

---

## 4. Negative Values

Negative numbers work naturally.

For example:

```text
a + b = -5
```

and:

```text
c + d = 5
```

Then:

```text
-5 + 5 = 0
```

The HashMap lookup simply searches for:

```text
-5
```

---

# Complexity

Let:

```text
n = nums1.length
```

All four arrays have length `n`.

## Time Complexity

Creating all pair sums from `nums1` and `nums2`:

```text
O(n²)
```

Creating all pair sums from `nums3` and `nums4`:

```text
O(n²)
```

HashMap lookups take average:

```text
O(1)
```

Therefore:

```text
Total Time = O(n²)
```

---

## Space Complexity

The HashMap can contain up to:

```text
n²
```

different pair sums.

Therefore:

```text
Space Complexity = O(n²)
```

---

# Brute Force vs Optimized Approach

## Brute Force

```text
for a in nums1
    for b in nums2
        for c in nums3
            for d in nums4
                check a+b+c+d
```

Complexity:

```text
O(n⁴)
```

---

## Optimized

```text
nums1 + nums2
      ↓
  HashMap
      ↓
nums3 + nums4
      ↓
lookup -(c+d)
```

Complexity:

```text
O(n²)
```

The optimization comes from **precomputing pair sums** instead of repeatedly calculating the same combinations.

---

# The Most Important Insight

Remember this transformation:

```text
a + b + c + d = 0
```

Split it:

```text
a + b = -(c + d)
```

Then:

```text
Store:
a + b → frequency

Lookup:
-(c + d)
```

In code:

```java
int sum = a + b;
map.put(sum, map.getOrDefault(sum, 0) + 1);
```

and:

```java
int sum = c + d;
int required = -sum;

answer += map.getOrDefault(required, 0);
```

These two parts are the heart of the solution.

---

# Visual Summary

For:

```text
nums1 = [1,2]
nums2 = [-2,-1]
nums3 = [-1,2]
nums4 = [0,2]
```

First calculate:

```text
nums1 + nums2

1 + (-2) = -1
1 + (-1) =  0
2 + (-2) =  0
2 + (-1) =  1
```

Store:

```text
-1 → 1
 0 → 2
 1 → 1
```

Then process:

```text
nums3 + nums4

(-1) + 0 = -1
```

Required:

```text
1
```

Found:

```text
1 → 1
```

So:

```text
answer = 1
```

Next:

```text
(-1) + 2 = 1
```

Required:

```text
-1
```

Found:

```text
-1 → 1
```

So:

```text
answer = 2
```

The remaining pair sums have no matching complements.

Final:

```text
answer = 2
```

---

# Final Takeaway

The complete strategy is:

```text
Four arrays
     ↓
Split into two pairs
     ↓
nums1 + nums2
     ↓
Calculate every pair sum
     ↓
Store sum frequencies in HashMap
     ↓
nums3 + nums4
     ↓
Calculate every pair sum
     ↓
Required = -(c + d)
     ↓
Look up required in HashMap
     ↓
Add its frequency
     ↓
Return answer
```

The main pattern to remember is:

> **For four-array sum problems, split the arrays into two pairs. Store the frequency of pair sums from the first pair of arrays, then for every pair from the remaining arrays, look for its complementary negative sum. This reduces the solution from O(n⁴) to O(n²).**
