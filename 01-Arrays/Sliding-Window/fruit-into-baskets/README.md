# WIN #59 — Fruit Into Baskets

## Problem

[LeetCode 904 — Fruit Into Baskets](https://leetcode.com/problems/fruit-into-baskets/)

You are given an integer array `fruits` where:

```text
fruits[i]
```

represents the type of fruit produced by the `ith` tree.

You have exactly **two baskets**.

Each basket can contain only **one type of fruit**, but there is no limit on how many fruits of that type the basket can hold.

You can start at any tree and must move continuously to the right, picking exactly one fruit from every tree.

Once you encounter a third fruit type that cannot fit into either basket, you must stop.

The goal is to find the **maximum number of fruits** that can be collected.

The important observation is:

> We need to find the longest contiguous subarray containing **at most two distinct fruit types**.

---

## Example 1

```text
Input:
fruits = [1,2,1]

Output:
3
```

We can start from the first tree:

```text
[1,2,1]
```

There are only two fruit types:

```text
1
2
```

Therefore, we can collect all three fruits.

```text
answer = 3
```

---

## Example 2

```text
Input:
fruits = [0,1,2,2]

Output:
3
```

If we start at index `0`:

```text
[0,1]
```

we have two fruit types.

But the next fruit is:

```text
2
```

which would create a third fruit type.

So we must stop.

Instead, start from index `1`:

```text
[1,2,2]
```

This contains only:

```text
1
2
```

Therefore:

```text
answer = 3
```

---

## Example 3

```text
Input:
fruits = [1,2,3,2,2]

Output:
4
```

Starting from index `0`:

```text
[1,2]
```

we cannot include `3` because that would give us three fruit types.

Instead, start from index `1`:

```text
[2,3,2,2]
```

There are only two fruit types:

```text
2
3
```

So we can collect:

```text
4
```

fruits.

---

## My Java Solution

```java
class Solution {
    public int totalFruit(int[] fruits) {

        HashMap<Integer, Integer> map = new HashMap<>();

        int left = 0;
        int answer = 0;

        for (int right = 0; right < fruits.length; right++) {

            int fruit = fruits[right];

            map.put(fruit, map.getOrDefault(fruit, 0) + 1);

            while (map.size() > 2) {

                int leftFruit = fruits[left];

                map.put(leftFruit, map.get(leftFruit) - 1);

                if (map.get(leftFruit) == 0) {
                    map.remove(leftFruit);
                }

                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        return answer;
    }
}
```

---

## My Thought Process

At first, the problem may look like we need to try every possible starting position.

For example:

```text
[1,2,3,2,2]
```

We could start from:

```text
index 0
index 1
index 2
index 3
...
```

and find how many fruits can be collected from each position.

But checking every possible subarray would be inefficient.

The important observation is that we are looking for:

```text
Longest contiguous subarray
```

with:

```text
At most 2 distinct values
```

That is a classic **sliding window** problem.

We maintain a window:

```text
[left ........ right]
```

and use a `HashMap` to keep track of the frequency of every fruit type inside the window.

The strategy is:

```text
Expand right
     ↓
Add fruit to HashMap
     ↓
More than 2 fruit types?
     ↓
YES
     ↓
Move left until only 2 types remain
     ↓
Calculate current window length
     ↓
Update maximum
```

---

# Step 1 — Create a HashMap

```java
HashMap<Integer, Integer> map = new HashMap<>();
```

The HashMap stores:

```text
fruit type → frequency inside current window
```

For example, if the current window is:

```text
[1,2,2,1]
```

the HashMap contains:

```text
1 → 2
2 → 2
```

This tells us:

```text
There are 2 fruits of type 1
There are 2 fruits of type 2
```

The frequency is important because when we move the left pointer, we need to know when a fruit type completely leaves the window.

---

# Step 2 — Initialize the Left Pointer

```java
int left = 0;
```

The sliding window starts at:

```text
left = 0
```

Initially:

```text
[0 ... right]
```

As the window becomes invalid, we move `left` forward.

---

# Step 3 — Initialize the Answer

```java
int answer = 0;
```

This stores the maximum valid window length found so far.

Initially:

```text
answer = 0
```

---

# Step 4 — Expand the Right Pointer

```java
for (int right = 0; right < fruits.length; right++) {
```

The `right` pointer moves from left to right through the array.

At every position, we add the current fruit into the window.

For example:

```text
fruits = [1,2,1]
```

The window grows:

```text
[1]
[1,2]
[1,2,1]
```

---

# Step 5 — Get the Current Fruit

```java
int fruit = fruits[right];
```

This stores the fruit type at the current `right` position.

For example:

```text
right = 2
fruits[right] = 1
```

so:

```text
fruit = 1
```

---

# Step 6 — Add the Fruit to the HashMap

```java
map.put(fruit, map.getOrDefault(fruit, 0) + 1);
```

This increases the frequency of the current fruit.

Suppose:

```text
map = {
    1 → 2,
    2 → 1
}
```

and the current fruit is:

```text
1
```

After adding it:

```text
map = {
    1 → 3,
    2 → 1
}
```

---

# Step 7 — Check Whether the Window Has More Than Two Types

This is the most important condition:

```java
while (map.size() > 2) {
```

We are allowed to have only:

```text
2
```

different fruit types.

So if:

```text
map.size() > 2
```

the current window is invalid.

For example:

```text
fruits = [1,2,3]
```

The HashMap becomes:

```text
1 → 1
2 → 1
3 → 1
```

Therefore:

```text
map.size() = 3
```

which violates the two-basket rule.

We need to shrink the window.

---

# Step 8 — Remove the Leftmost Fruit

```java
int leftFruit = fruits[left];
```

We identify the fruit at the left edge of the current window.

Then:

```java
map.put(leftFruit, map.get(leftFruit) - 1);
```

decreases its frequency.

For example:

```text
map = {
    1 → 1,
    2 → 1,
    3 → 1
}
```

and:

```text
leftFruit = 1
```

After removing it:

```text
map = {
    1 → 0,
    2 → 1,
    3 → 1
}
```

---

# Step 9 — Remove Fruit Type When Its Frequency Becomes Zero

```java
if (map.get(leftFruit) == 0) {
    map.remove(leftFruit);
}
```

This is very important.

Suppose:

```text
map = {
    1 → 0,
    2 → 1,
    3 → 1
}
```

The fruit type `1` no longer exists inside the current window.

Therefore, we remove it completely:

```text
map = {
    2 → 1,
    3 → 1
}
```

Now:

```text
map.size() = 2
```

so the window becomes valid again.

---

# Step 10 — Move the Left Pointer

```java
left++;
```

We shrink the window from the left.

For example:

```text
[1,2,3]
 ↑
left
```

After:

```java
left++;
```

the window becomes:

```text
[2,3]
 ↑
left
```

We continue moving `left` until the window contains at most two fruit types.

---

# Step 11 — Calculate the Window Length

Once the window is valid:

```java
answer = Math.max(answer, right - left + 1);
```

The current window is:

```text
[left ... right]
```

Its length is:

```text
right - left + 1
```

For example:

```text
left = 1
right = 4
```

then:

```text
length = 4 - 1 + 1
       = 4
```

We compare it with the best answer found so far.

---

# Complete Walkthrough

Consider:

```text
fruits = [1,2,3,2,2]
```

We want the longest window containing at most two distinct fruit types.

---

## Step 1 — right = 0

Current fruit:

```text
1
```

HashMap:

```text
{
    1 → 1
}
```

Window:

```text
[1]
```

Size:

```text
1
```

Valid.

Current length:

```text
1
```

Answer:

```text
1
```

---

## Step 2 — right = 1

Current fruit:

```text
2
```

HashMap:

```text
{
    1 → 1,
    2 → 1
}
```

Window:

```text
[1,2]
```

Distinct types:

```text
2
```

Valid.

Length:

```text
2
```

Answer:

```text
2
```

---

## Step 3 — right = 2

Current fruit:

```text
3
```

HashMap:

```text
{
    1 → 1,
    2 → 1,
    3 → 1
}
```

Distinct types:

```text
3
```

Invalid.

We need to shrink the window.

---

### Remove fruits from the left

Current:

```text
left = 0
```

Left fruit:

```text
1
```

Decrease frequency:

```text
1 → 0
```

Remove `1` from the map.

HashMap:

```text
{
    2 → 1,
    3 → 1
}
```

Move:

```text
left = 1
```

Now the window is:

```text
[2,3]
```

Distinct types:

```text
2
```

Valid.

Length:

```text
2
```

Answer remains:

```text
2
```

---

## Step 4 — right = 3

Current fruit:

```text
2
```

Add it:

```text
{
    2 → 2,
    3 → 1
}
```

Window:

```text
[2,3,2]
```

Distinct types:

```text
2
```

Valid.

Length:

```text
3
```

Answer:

```text
3
```

---

## Step 5 — right = 4

Current fruit:

```text
2
```

Add it:

```text
{
    2 → 3,
    3 → 1
}
```

Window:

```text
[2,3,2,2]
```

Distinct types:

```text
2
```

Valid.

Length:

```text
4
```

Update:

```text
answer = 4
```

---

## Final Answer

```text
4
```

The longest valid window is:

```text
[2,3,2,2]
```

---

# Why We Use a HashMap

We need to know:

```text
How many different fruit types are currently inside the window?
```

A HashMap gives us:

```text
fruit type → frequency
```

For example:

```text
{
    2 → 3,
    3 → 1
}
```

The number of keys tells us the number of distinct fruit types:

```text
map.size() = 2
```

This directly corresponds to the number of baskets available.

---

# Why We Store Frequencies

We cannot simply use a HashSet because when `left` moves, we need to know whether a fruit type still exists somewhere else in the window.

Consider:

```text
[2,3,2]
```

The HashSet contains:

```text
{2,3}
```

If we remove the first `2`, another `2` still exists.

So we cannot simply remove `2` from the set.

The HashMap tells us:

```text
2 → 2
```

After removing one:

```text
2 → 1
```

Therefore, `2` remains in the window.

Only when:

```text
frequency == 0
```

do we remove that fruit type from the HashMap.

---

# Why This Is a Sliding Window Problem

The problem asks for:

```text
Maximum length
```

of a:

```text
Contiguous subarray
```

with a condition:

```text
At most 2 distinct fruit types
```

This is a classic variable-size sliding window.

The window is:

```text
[left ........ right]
```

`right` expands the window:

```text
left ........ right →
```

When the window becomes invalid:

```text
more than 2 fruit types
```

we move:

```text
left →
```

until the window becomes valid again.

The pattern is:

```text
Expand → Check → Shrink if necessary → Record answer
```

---

# Pattern Recognition

## Pattern: Variable-Size Sliding Window + HashMap

This problem is an important sliding-window pattern.

The general strategy is:

```text
Initialize left = 0
        ↓
Expand right
        ↓
Add current element to HashMap
        ↓
Check whether window violates condition
        ↓
If invalid:
    Move left
    Update frequencies
    Remove zero-frequency elements
        ↓
Window becomes valid
        ↓
Update maximum length
```

For this problem, the condition is:

```text
map.size() <= 2
```

The important pattern to remember is:

> **When finding the longest contiguous subarray with at most K distinct elements, use a variable-size sliding window and a HashMap to maintain frequencies.**

Here:

```text
K = 2
```

---

# Why We Don't Try Every Starting Position

A brute-force approach might look like:

```text
Start at index 0
    Keep expanding

Start at index 1
    Keep expanding

Start at index 2
    Keep expanding
```

For every starting point, we could count distinct fruit types.

But this can take:

```text
O(n²)
```

time.

The sliding window avoids repeating this work.

Instead:

```text
right
  →
```

continuously expands the window.

When the window becomes invalid:

```text
left
  →
```

moves forward.

Each element is added to the window once and removed from the window at most once.

Therefore, the total work is linear.

---

# Why the Algorithm Is O(n)

At first glance, we have:

```java
for (...)
{
    while (...)
    {
        left++;
    }
}
```

It may look like:

```text
O(n²)
```

But it is actually:

```text
O(n)
```

because both pointers only move forward.

The `right` pointer moves:

```text
0 → 1 → 2 → ... → n-1
```

at most `n` times.

The `left` pointer also moves forward at most `n` times.

Therefore:

```text
right movement = O(n)
left movement  = O(n)
```

Total:

```text
O(n)
```

average time.

---

# Edge Cases

## 1. Only One Fruit Type

```text
fruits = [1,1,1,1]
```

There is only one fruit type.

We can collect everything:

```text
answer = 4
```

---

## 2. Exactly Two Fruit Types

```text
fruits = [1,2,1,2,1]
```

There are only two types:

```text
1
2
```

So the entire array is valid.

Answer:

```text
5
```

---

## 3. Every Fruit Is Different

```text
fruits = [1,2,3,4,5]
```

We can only keep two types at once.

The longest valid window has length:

```text
2
```

For example:

```text
[1,2]
```

---

## 4. Third Type Appears After a Long Sequence

```text
fruits = [1,1,1,2,2,3]
```

Before `3`, the window is:

```text
[1,1,1,2,2]
```

which contains two types.

When `3` arrives, the window becomes invalid.

We move `left` until all `1`s are removed.

The valid window becomes:

```text
[2,2,3]
```

Length:

```text
3
```

---

## 5. Repeated Third Type

```text
fruits = [1,2,2,2,3,3]
```

When `3` arrives, we don't necessarily remove only one element.

We keep moving `left` until one of the fruit types disappears completely.

This is why the `while` loop is required.

---

# The Most Important Insight

The key condition is:

```java
while (map.size() > 2)
```

This means:

```text
If there are more than two distinct fruit types,
the current window is invalid.
```

Then:

```text
Remove fruits from the left
```

until:

```text
map.size() <= 2
```

The entire solution can be remembered as:

```text
Right pointer expands
        ↓
Add fruit frequency
        ↓
More than 2 types?
        ↓
YES
        ↓
Move left
        ↓
Decrease frequency
        ↓
Remove type if frequency = 0
        ↓
Window valid
        ↓
Update maximum
```

---

# Visual Summary

For:

```text
fruits = [1,2,3,2,2]
```

The window evolves like this:

```text
[1]
```

```text
[1,2]
```

```text
[1,2,3]  ← INVALID
```

Shrink:

```text
[2,3]
```

Expand:

```text
[2,3,2]
```

Expand:

```text
[2,3,2,2]
```

Final maximum:

```text
4
```

The longest valid window is:

```text
[2,3,2,2]
```

---

# Final Takeaway

The complete strategy is:

```text
Create HashMap
      ↓
left = 0
      ↓
Move right through the array
      ↓
Add fruit to HashMap
      ↓
If distinct fruit types > 2
      ↓
Move left
      ↓
Decrease fruit frequency
      ↓
Remove fruit when frequency becomes 0
      ↓
Window has at most 2 types
      ↓
Calculate right - left + 1
      ↓
Update maximum
```

The main pattern to remember is:

> **For longest-subarray problems with a limit on the number of distinct elements, use a variable-size sliding window with a HashMap of frequencies. Expand with the right pointer, shrink with the left pointer whenever the window becomes invalid, and track the maximum valid window length.**

This gives an average **O(n)** time solution with **O(k)** HashMap space, where `k` is the number of distinct fruit types currently tracked.
