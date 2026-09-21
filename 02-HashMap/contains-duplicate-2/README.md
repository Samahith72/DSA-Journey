# WIN #42 — Contains Duplicate II

## Problem

[LeetCode 219 — Contains Duplicate II](https://leetcode.com/problems/contains-duplicate-ii/)

Given an integer array `nums` and an integer `k`, return `true` if there are two **distinct indices** `i` and `j` such that:

```text
nums[i] == nums[j]
```

and:

```text
|i - j| <= k
```

In other words, we need to find a duplicate number whose two occurrences are at most `k` positions apart.

### Example 1

```text
Input: nums = [1,2,3,1], k = 3
Output: true
```

The value `1` appears at indices `0` and `3`.

```text
|0 - 3| = 3
```

Since:

```text
3 <= k
```

the answer is `true`.

### Example 2

```text
Input: nums = [1,0,1,1], k = 1
Output: true
```

The value `1` appears at indices `2` and `3`.

```text
|2 - 3| = 1
```

So the condition is satisfied.

### Example 3

```text
Input: nums = [1,2,3,1,2,3], k = 2
Output: false
```

The repeated values are more than `2` positions apart.

Therefore, no valid nearby duplicate exists.

---

## My Java Solution

```java
class Solution {
    public boolean containsNearbyDuplicate(int[] nums, int k) {

        HashMap<Integer, Integer> map = new HashMap<>();

        map.put(nums[0], 0);

        for(int i =1; i< nums.length;i++){
            if(map.containsKey(nums[i])){
               int num = map.get(nums[i]);
               if(nums[i] == nums[num] && Math.abs(num - i) <= k){
                return true;
               }
            }
                map.put(nums[i], i);
            
        }
        return false;
    }
}
```

---

## My Thought Process

The important part of this problem is that we don't just need to know:

> "Does this number appear again?"

We need to know:

> "Where did this number appear most recently?"

Because the condition depends on the **index difference**:

```text
|i - j| <= k
```

A `HashMap` is perfect for this because we can store:

```text
number → latest index
```

For example:

```text
1 → 0
2 → 1
3 → 2
```

When we encounter a number again, we can immediately find its previous index and check the distance.

---

# Step 1 — Create a HashMap

```java
HashMap<Integer, Integer> map = new HashMap<>();
```

The map stores:

```text
value → most recent index
```

For example:

```text
nums = [1,2,3,1]
```

After processing the first three elements:

```text
1 → 0
2 → 1
3 → 2
```

---

# Step 2 — Store the First Element

```java
map.put(nums[0], 0);
```

The first element is at index `0`.

For:

```text
nums = [1,2,3,1]
```

the map initially becomes:

```text
1 → 0
```

Then we start checking from index `1`.

---

# Step 3 — Traverse the Array

```java
for(int i =1; i< nums.length;i++){
```

For every new element, we check whether it already exists in the map.

```java
if(map.containsKey(nums[i])){
```

If it exists, we have found a duplicate.

---

# Step 4 — Get the Previous Index

```java
int num = map.get(nums[i]);
```

The variable `num` stores the previous index of the same value.

For example:

```text
nums = [1,2,3,1]
```

When:

```text
i = 3
nums[i] = 1
```

the map contains:

```text
1 → 0
```

Therefore:

```text
num = 0
```

So we know that the previous `1` was at index `0`.

---

# Step 5 — Check the Index Distance

Now:

```java
if(nums[i] == nums[num] && Math.abs(num - i) <= k){
    return true;
}
```

The important condition is:

```text
Math.abs(num - i) <= k
```

For example:

```text
i = 3
num = 0
k = 3
```

Then:

```text
|0 - 3| = 3
```

Since:

```text
3 <= 3
```

we found a valid nearby duplicate.

So:

```java
return true;
```

---

# Step 6 — Update the Latest Index

After checking the current element:

```java
map.put(nums[i], i);
```

This updates the value's index.

For example, suppose:

```text
nums = [1,2,1,1]
```

When we reach index `2`:

```text
1 → 0
```

The distance is:

```text
|2 - 0| = 2
```

If `k` is smaller than `2`, this occurrence doesn't satisfy the condition.

So we update:

```text
1 → 2
```

Now if another `1` appears at index `3`, we compare it with the **most recent occurrence** at index `2`:

```text
|3 - 2| = 1
```

This is important because the closest previous occurrence gives us the best chance of satisfying:

```text
|i - j| <= k
```

---

# Step 7 — Return False

If we finish scanning the entire array without finding a valid pair:

```java
return false;
```

That means no duplicate occurred within the required distance.

---

## Complete Walkthrough

Consider:

```text
nums = [1,2,3,1]
k = 3
```

### Initial State

```text
map = {}
```

Store the first element:

```text
1 → 0
```

---

### Index 1

```text
nums[1] = 2
```

`2` is not in the map.

Store:

```text
1 → 0
2 → 1
```

---

### Index 2

```text
nums[2] = 3
```

`3` is not in the map.

Store:

```text
1 → 0
2 → 1
3 → 2
```

---

### Index 3

```text
nums[3] = 1
```

`1` already exists.

Previous index:

```text
0
```

Current index:

```text
3
```

Distance:

```text
|3 - 0| = 3
```

Since:

```text
3 <= k
```

return:

```text
true
```

---

## Another Walkthrough

Consider:

```text
nums = [1,2,3,1,2,3]
k = 2
```

### Processing

```text
index 0 → 1
index 1 → 2
index 2 → 3
```

Map:

```text
1 → 0
2 → 1
3 → 2
```

At index `3`:

```text
nums[3] = 1
```

Previous index:

```text
0
```

Distance:

```text
|3 - 0| = 3
```

But:

```text
3 > 2
```

So this duplicate is too far away.

Update:

```text
1 → 3
```

At index `4`:

```text
nums[4] = 2
```

Previous index:

```text
1
```

Distance:

```text
|4 - 1| = 3
```

Again:

```text
3 > 2
```

Update:

```text
2 → 4
```

At index `5`:

```text
nums[5] = 3
```

Previous index:

```text
2
```

Distance:

```text
|5 - 2| = 3
```

Again too far.

No valid pair exists.

Therefore:

```text
false
```

---

## Pattern Recognition

### Pattern: HashMap + Latest Index Tracking

The key clue in this problem is:

```text
Duplicate + Index Distance
```

We need two pieces of information:

1. Has this value appeared before?
2. Where did it appear most recently?

A `HashMap` can store exactly that:

```text
value → latest index
```

Whenever we see the value again:

```text
current index - previous index
```

can be checked in `O(1)` average time.

### General Pattern

Whenever a problem asks something like:

> Find repeated values within a certain distance/window.

Think:

```text
HashMap
   ↓
value → latest index
```

This is a very useful pattern for array problems involving duplicates and positions.

---

## Why Store the Latest Index?

Consider:

```text
nums = [1,2,1,1]
```

Suppose we are currently at:

```text
index = 3
```

The value `1` previously appeared at:

```text
index 0
index 2
```

The latest occurrence is index `2`.

We want to compare:

```text
|3 - 2| = 1
```

rather than:

```text
|3 - 0| = 3
```

because the latest occurrence is always the closest previous occurrence.

Therefore, updating:

```java
map.put(nums[i], i);
```

is important.

---

## Edge Cases

### 1. No Duplicate

```text
nums = [1,2,3,4]
k = 2
```

No value occurs twice.

```text
false
```

---

### 2. Duplicate Exactly K Positions Apart

```text
nums = [1,2,3,1]
k = 3
```

Distance:

```text
|0 - 3| = 3
```

Since the condition is:

```text
<= k
```

this is valid.

```text
true
```

---

### 3. Duplicate Just Outside the Range

```text
nums = [1,2,3,1]
k = 2
```

Distance:

```text
|0 - 3| = 3
```

But:

```text
3 > 2
```

Therefore:

```text
false
```

---

### 4. Same Value Repeated Multiple Times

```text
nums = [1,1,1,1]
k = 1
```

At indices `0` and `1`:

```text
|0 - 1| = 1
```

So the answer is immediately:

```text
true
```

---

### 5. k = 0

If:

```text
k = 0
```

we would need:

```text
|i - j| <= 0
```

But two distinct indices can never have a distance of `0`.

Therefore, the answer is always:

```text
false
```

for valid input when `k = 0`.

---

## Complexity

### Time Complexity

We traverse the array once.

Each `HashMap` lookup and insertion takes **O(1) average time**.

Therefore:

```text
O(n)
```

---

### Space Complexity

The HashMap can store up to `n` distinct values.

Therefore:

```text
O(n)
```

auxiliary space.

---

## Key Learning

### 1. Store Information You Will Need Later

Instead of only storing:

```text
value
```

we store:

```text
value → index
```

because the problem asks about the distance between indices.

---

### 2. Latest Occurrence Is the Most Useful

For a duplicate at index `i`, the latest previous occurrence gives the smallest possible distance.

Therefore:

```java
map.put(nums[i], i);
```

should happen after checking the current occurrence.

---

### 3. HashMap Converts Repeated Searching Into Constant-Time Lookup

Without a HashMap, we might repeatedly search backward through the array.

That could become:

```text
O(n²)
```

With a HashMap:

```text
Check → Lookup → Distance → Update
```

can be done in one pass.

---

### 4. Think About What the Problem Is Actually Asking

This is not simply:

> "Does the array contain duplicates?"

It is:

> "Does the array contain duplicates whose indices are close enough?"

That additional condition changes the data we need to store.

---

## Final Takeaway

The core idea is:

```text
Traverse the array
      ↓
Check if value already exists
      ↓
Get its latest index
      ↓
Check index distance
      ↓
If distance <= k → true
      ↓
Otherwise update latest index
```

The pattern to remember is:

> **When a duplicate problem also gives an index-distance constraint, use a HashMap to store each value's most recent index.**

This gives an **O(n) time** and **O(n) space** solution.
