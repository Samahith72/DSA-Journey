# WIN #53 — Unique Number of Occurrences

## Problem

[LeetCode 1207 — Unique Number of Occurrences](https://leetcode.com/problems/unique-number-of-occurrences/)

Given an integer array `arr`, we need to determine whether the **number of occurrences of every distinct value is unique**.

In simple words:

1. Count how many times each number appears.
2. Look at all those occurrence counts.
3. Make sure no two different numbers have the same count.

If every frequency is different, return:

```text
true
```

Otherwise:

```text
false
```

---

## Example 1

```text
Input:
arr = [1,2,2,1,1,3]

Output:
true
```

Count each number:

```text
1 → 3 occurrences
2 → 2 occurrences
3 → 1 occurrence
```

The frequencies are:

```text
[3, 2, 1]
```

All frequencies are unique.

Therefore:

```text
true
```

---

## Example 2

```text
Input:
arr = [1,2]

Output:
false
```

Frequency:

```text
1 → 1
2 → 1
```

Both numbers occur exactly once.

So the frequencies are:

```text
[1, 1]
```

The frequency `1` is repeated.

Therefore:

```text
false
```

---

## Example 3

```text
Input:
arr = [-3,0,1,-3,1,1,1,-3,10,0]

Output:
true
```

Count each number:

```text
-3 → 3
0  → 2
1  → 4
10 → 1
```

The frequencies are:

```text
[3, 2, 4, 1]
```

All of them are different.

Therefore:

```text
true
```

---

## My Java Solution

```java
class Solution {
    public boolean uniqueOccurrences(int[] arr) {

        HashMap<Integer, Integer> map = new HashMap<>();
        for(int num : arr){
            map.put(num, map.getOrDefault(num,0)+1);
        }

        HashSet<Integer> set = new HashSet<>();
        for(Integer value: map.values()){
            if(set.contains(value)){
                return false;
            }

            set.add(value);
        }
        return true;
    }
}
```

---

## My Thought Process

The problem has two separate questions:

### Question 1

How many times does each number appear?

For example:

```text
arr = [1,2,2,1,1,3]
```

We need:

```text
1 → 3
2 → 2
3 → 1
```

A `HashMap` is perfect for this because we can store:

```text
number → frequency
```

### Question 2

Are all those frequencies different?

Once we have:

```text
1 → 3
2 → 2
3 → 1
```

we only care about:

```text
3, 2, 1
```

A `HashSet` is perfect for checking whether a value has already appeared.

So the complete idea is:

```text
HashMap
   ↓
Count frequency of every number
   ↓
HashMap values()
   ↓
HashSet
   ↓
Check whether every frequency is unique
```

---

# Step 1 — Create the Frequency HashMap

```java
HashMap<Integer, Integer> map = new HashMap<>();
```

The map stores:

```text
number → number of occurrences
```

For example:

```text
arr = [1,2,2,1,1,3]
```

will eventually produce:

```text
1 → 3
2 → 2
3 → 1
```

---

# Step 2 — Count Every Number

```java
for(int num : arr){
    map.put(num, map.getOrDefault(num,0)+1);
}
```

This is the frequency-counting pattern.

The important part is:

```java
map.getOrDefault(num, 0)
```

If `num` is not already in the map:

```text
getOrDefault → 0
```

Then we add `1`.

If it already exists, we get its current frequency and increase it.

---

## Example

Consider:

```text
arr = [1,2,2,1,1,3]
```

### First `1`

```text
map.getOrDefault(1,0) = 0
```

Store:

```text
1 → 1
```

---

### `2`

Store:

```text
1 → 1
2 → 1
```

---

### Second `2`

Increase its count:

```text
1 → 1
2 → 2
```

---

### Second `1`

```text
1 → 2
2 → 2
```

---

### Third `1`

```text
1 → 3
2 → 2
```

---

### `3`

Final map:

```text
1 → 3
2 → 2
3 → 1
```

---

# Step 3 — Create a HashSet

```java
HashSet<Integer> set = new HashSet<>();
```

Now we need to check the frequencies.

We don't care about the original numbers anymore.

We only care about:

```text
3
2
1
```

The `HashSet` stores frequencies that we have already encountered.

---

# Step 4 — Traverse the Frequencies

```java
for(Integer value: map.values()){
```

`map.values()` gives us all the occurrence counts.

For:

```text
1 → 3
2 → 2
3 → 1
```

we get:

```text
3
2
1
```

---

# Step 5 — Check for Duplicate Frequencies

```java
if(set.contains(value)){
    return false;
}
```

This is the key condition.

Suppose we already saw:

```text
3
```

and encounter another `3`.

Then:

```java
set.contains(3)
```

is:

```text
true
```

That means two different numbers have the same frequency.

Therefore, the answer must be:

```text
false
```

---

# Step 6 — Store the Frequency

If the frequency has not appeared before:

```java
set.add(value);
```

For:

```text
[3,2,1]
```

the process is:

```text
3 → add
2 → add
1 → add
```

The final set is:

```text
[3,2,1]
```

All frequencies were unique.

---

# Step 7 — Return True

If the entire map is processed without finding a duplicate frequency:

```java
return true;
```

That means every distinct number has a unique occurrence count.

---

## Complete Walkthrough

Consider:

```text
arr = [1,2,2,1,1,3]
```

### Phase 1 — Build Frequency Map

After processing the array:

```text
map:

1 → 3
2 → 2
3 → 1
```

---

### Phase 2 — Check Frequencies

Start:

```text
set = {}
```

First frequency:

```text
3
```

Not present.

```text
set = {3}
```

Second frequency:

```text
2
```

Not present.

```text
set = {3,2}
```

Third frequency:

```text
1
```

Not present.

```text
set = {3,2,1}
```

No duplicate was found.

Return:

```text
true
```

---

## Failure Walkthrough

Consider:

```text
arr = [1,2]
```

Frequency map:

```text
1 → 1
2 → 1
```

Start:

```text
set = {}
```

First frequency:

```text
1
```

Add:

```text
set = {1}
```

Second frequency:

```text
1
```

Check:

```java
set.contains(1)
```

returns:

```text
true
```

Therefore:

```java
return false;
```

---

## Pattern Recognition

### Pattern: Frequency Counting + Duplicate Frequency Detection

This problem combines two extremely useful HashMap/HashSet patterns:

```text
HashMap
number → frequency
```

and:

```text
HashSet
frequency → already seen?
```

The overall pattern is:

```text
Input Array
     ↓
HashMap
     ↓
Count occurrences
     ↓
map.values()
     ↓
HashSet
     ↓
Check duplicate frequencies
```

---

## Why Do We Need Both HashMap and HashSet?

Each data structure has a different job.

### HashMap

The `HashMap` answers:

> How many times did each number appear?

Example:

```text
1 → 3
2 → 2
3 → 1
```

### HashSet

The `HashSet` answers:

> Have I already seen this frequency?

Example:

```text
frequency = 3
```

If:

```java
set.contains(3)
```

is true, then another number already occurred `3` times.

Therefore the frequencies are not unique.

---

## Important Distinction

We are **not** checking whether the numbers themselves are unique.

For example:

```text
arr = [1,1,2,2,2,3]
```

The numbers obviously repeat.

That's completely fine.

We are checking whether their **frequencies** are unique:

```text
1 → 2
2 → 3
3 → 1
```

Frequencies:

```text
2, 3, 1
```

All unique.

Therefore:

```text
true
```

---

## Another Example

Consider:

```text
arr = [5,5,5,6,6,7,7,7,7]
```

Frequency map:

```text
5 → 3
6 → 2
7 → 4
```

Frequencies:

```text
3, 2, 4
```

All unique.

Therefore:

```text
true
```

Now consider:

```text
arr = [5,5,5,6,6,6,7]
```

Frequency map:

```text
5 → 3
6 → 3
7 → 1
```

Frequencies:

```text
3, 3, 1
```

`3` appears twice.

Therefore:

```text
false
```

---

## Edge Cases

### 1. Only One Element

```text
arr = [10]
```

Frequency:

```text
10 → 1
```

There is only one frequency, so it is automatically unique.

Answer:

```text
true
```

---

### 2. All Elements Are the Same

```text
arr = [5,5,5,5]
```

Frequency:

```text
5 → 4
```

There is only one distinct frequency.

Answer:

```text
true
```

---

### 3. Every Element Is Different

```text
arr = [1,2,3,4]
```

Frequency:

```text
1 → 1
2 → 1
3 → 1
4 → 1
```

The frequency `1` occurs multiple times.

Therefore:

```text
false
```

---

### 4. Negative Numbers

Negative numbers are completely valid keys in a `HashMap`.

For example:

```text
[-3,0,1,-3,1,1]
```

can be counted normally:

```text
-3 → 2
0  → 1
1  → 3
```

The sign of the number does not affect the frequency-counting logic.

---

## Complexity

Let:

```text
n = arr.length
```

### Time Complexity

First, we traverse the entire array:

```text
O(n)
```

to build the frequency map.

Then we traverse the distinct frequencies:

```text
O(n)
```

in the worst case.

Therefore:

```text
O(n) + O(n)
```

which simplifies to:

```text
O(n)
```

HashMap and HashSet operations are **O(1) average time**.

---

### Space Complexity

The HashMap can contain up to `n` distinct numbers:

```text
O(n)
```

The HashSet can contain up to `n` distinct frequencies:

```text
O(n)
```

Therefore, the overall auxiliary space is:

```text
O(n)
```

---

## Key Learning

### 1. Separate Counting From Validation

This problem becomes much easier when split into two phases:

```text
Phase 1:
Count frequencies

Phase 2:
Check whether frequencies are unique
```

Trying to do everything at once can make the logic unnecessarily complicated.

---

### 2. HashMap Is the Standard Frequency Counter

Whenever you see:

> Count how many times each value occurs.

Think:

```java
map.put(
    value,
    map.getOrDefault(value, 0) + 1
);
```

This is one of the most reusable HashMap patterns in DSA.

---

### 3. HashSet Is Perfect for Duplicate Detection

Whenever you need to answer:

> Have I already seen this value?

Think:

```java
if(set.contains(value))
```

Then:

```java
set.add(value);
```

This is another fundamental HashSet pattern.

---

### 4. The Values of a Map Can Become the Input to Another Structure

A useful idea from this problem is:

```text
HashMap
   ↓
map.values()
   ↓
HashSet
```

The keys are no longer important after counting.

We only care about the values — the frequencies.

---

## Visual Summary

```text
arr = [1,2,2,1,1,3]

        ↓

     HashMap

1 → 3
2 → 2
3 → 1

        ↓

   map.values()

[3, 2, 1]

        ↓

     HashSet

3 → add
2 → add
1 → add

        ↓

No duplicate frequency

        ↓

      true
```

For a failing case:

```text
arr = [1,2]

        ↓

1 → 1
2 → 1

        ↓

[1,1]

        ↓

HashSet

1 → add
1 → already exists

        ↓

      false
```

---

## Final Takeaway

The entire solution can be remembered as:

```text
Count every number
       ↓
Store number → frequency
       ↓
Extract all frequencies
       ↓
Use a HashSet to detect duplicates
       ↓
Duplicate frequency?
   ↓             ↓
 YES             NO
  ↓               ↓
false            true
```

The main pattern to remember is:

> **When a problem asks whether frequencies are unique, use a HashMap to count occurrences and a HashSet to detect duplicate frequencies.**

This is a fundamental **HashMap + HashSet** pattern and a very useful template for future frequency-based problems.
