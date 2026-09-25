# WIN #56 — Longest Consecutive Sequence

## Problem

[LeetCode 128 — Longest Consecutive Sequence](https://leetcode.com/problems/longest-consecutive-sequence/)

Given an **unsorted** array of integers, find the length of the longest sequence of consecutive numbers.

A consecutive sequence looks like:

```text id="j7n3c5"
1, 2, 3, 4
```

or:

```text id="x8m2q6"
10, 11, 12, 13, 14
```

The numbers do not need to appear next to each other in the original array.

The important requirement is:

> The algorithm must run in **O(n)** time.

---

## Example 1

```text id="q4v8m1"
Input:
nums = [100,4,200,1,3,2]

Output:
4
```

The longest consecutive sequence is:

```text id="p6c2x9"
[1, 2, 3, 4]
```

Its length is:

```text id="m8v3q5"
4
```

---

## Example 2

```text id="n5x1c7"
Input:
nums = [0,3,7,2,5,8,4,6,0,1]

Output:
9
```

The longest sequence is:

```text id="q2m7v4"
[0,1,2,3,4,5,6,7,8]
```

Its length is:

```text id="c9x3n6"
9
```

---

## Example 3

```text id="v7m2q8"
Input:
nums = [1,0,1,2]

Output:
3
```

The unique values are:

```text id="p4c8x1"
0, 1, 2
```

The consecutive sequence is:

```text id="n6v3m9"
[0,1,2]
```

So the answer is:

```text id="q1x7c5"
3
```

Notice that the duplicate `1` does not affect the sequence.

---

## My Java Solution

```java id="r8m2v6"
class Solution {
    public int longestConsecutive(int[] nums) {

        HashSet< Integer > set = new HashSet<>();

        for(int num: nums){
            set.add(num);
        }
        int answer = 0;
        
        for(Integer num: set){
            if(!set.contains(num-1)){
                int current =num;
                int length =1;

                while(set.contains(current+1)){
                    length++;
                    current++;
                }
                answer = Math.max(answer, length);
            }
        }

        return answer;
        
    }
}
```

---

## My Thought Process

At first, the problem looks like a sorting problem.

We could sort the array:

```text id="x6m2q8"
[100,4,200,1,3,2]

        ↓

[1,2,3,4,100,200]
```

and then find the longest consecutive sequence.

But sorting would take:

```text id="p3v7c1"
O(n log n)
```

The problem specifically asks for:

```text id="m8x2q5"
O(n)
```

So sorting is not the right approach.

Instead, we use a `HashSet`.

The key idea is:

> Store every number in a HashSet so we can check whether a number exists in O(1) average time.

Then we need to figure out **where a consecutive sequence starts**.

A number `num` is the beginning of a sequence only if:

```text id="q7c3v9"
num - 1
```

does not exist.

For example:

```text id="n4m8x2"
1, 2, 3, 4
```

`1` is the beginning because:

```text id="v5q1c7"
0 does not exist
```

But `2` is not the beginning because:

```text id="m3x8n6"
1 exists
```

This observation prevents us from unnecessarily scanning the same sequence multiple times.

---

# Step 1 — Create a HashSet

```java id="c7m2x9"
HashSet<Integer> set = new HashSet<>();
```

The purpose of the set is to allow fast existence checks.

We want to quickly ask:

```text id="q4v8n1"
Does this number exist?
```

A `HashSet` gives us average:

```text id="x6m3c8"
O(1)
```

lookup.

---

# Step 2 — Put Every Number Into the Set

```java id="p9v2m5"
for(int num: nums){
    set.add(num);
}
```

Suppose:

```text id="h1c7x4"
nums = [100,4,200,1,3,2]
```

The set becomes:

```text id="n8m3q6"
{100,4,200,1,3,2}
```

Now we can quickly check whether:

```text id="v5x1c9"
num - 1
```

or:

```text id="m2q7n4"
num + 1
```

exists.

---

# Step 3 — Initialize the Answer

```java id="c8v3m1"
int answer = 0;
```

This stores the longest consecutive sequence found so far.

Initially:

```text id="q6x2n8"
answer = 0
```

---

# Step 4 — Traverse the Set

```java id="p4m9v2"
for(Integer num: set){
```

We iterate through the unique numbers.

This is also useful because duplicates have already been removed.

For example:

```text id="x7c3n5"
nums = [1,0,1,2]
```

becomes conceptually:

```text id="m6v1q8"
set = {0,1,2}
```

The duplicate `1` no longer matters.

---

# Step 5 — Detect the Start of a Sequence

This is the most important condition:

```java id="n8c2v6"
if(!set.contains(num-1)){
```

It means:

> Only start building a sequence if the previous number does not exist.

Consider:

```text id="q5m7x3"
[1,2,3,4]
```

For `1`:

```text id="v9c1n6"
set.contains(0)
```

is false.

Therefore:

```text id="m2x8q4"
1
```

is the beginning of the sequence.

But for `2`:

```text id="c7v3m9"
set.contains(1)
```

is true.

So `2` is not a sequence start.

Similarly:

```text id="p5n1x8"
3 → 2 exists
4 → 3 exists
```

Therefore we only start from `1`.

---

# Why Sequence Start Detection Is So Important

Suppose we have:

```text id="z8m3c5"
[1,2,3,4,5]
```

A naive approach might start from every number:

```text id="j6q2v9"
Start at 1 → scan 1,2,3,4,5
Start at 2 → scan 2,3,4,5
Start at 3 → scan 3,4,5
Start at 4 → scan 4,5
Start at 5
```

This repeats a lot of work.

Instead, your condition:

```java id="m4x7c1"
if(!set.contains(num-1))
```

allows us to start only at:

```text id="q9v2n6"
1
```

Then we scan:

```text id="c5m8x3"
1 → 2 → 3 → 4 → 5
```

only once.

This is the key reason the algorithm achieves O(n).

---

# Step 6 — Start the Sequence

Once we know `num` is a sequence start:

```java id="r3c8m1"
int current = num;
int length = 1;
```

For example:

```text id="v6q2n9"
num = 1
```

we start with:

```text id="x4m7c5"
current = 1
length = 1
```

---

# Step 7 — Expand the Sequence

```java id="n8v3q1"
while(set.contains(current+1)){
    length++;
    current++;
}
```

This checks whether the next consecutive number exists.

For:

```text id="k5m2x7"
current = 1
```

check:

```text id="p4c8n3"
set.contains(2)
```

Yes.

So:

```text id="v7q1m6"
length = 2
current = 2
```

Check:

```text id="x3n8c5"
set.contains(3)
```

Yes.

Then:

```text id="m1q6v9"
length = 3
current = 3
```

Continue:

```text id="n5c2x8"
4 exists
```

Then:

```text id="q7m3v1"
length = 4
current = 4
```

Finally:

```text id="c8x1n6"
5 does not exist
```

Stop.

The sequence length is:

```text id="p4m9v2"
4
```

---

# Step 8 — Update the Maximum

```java id="x6c3m8"
answer = Math.max(answer, length);
```

If the current sequence has length `4` and the previous best was `3`:

```text id="q2v7n5"
answer = max(3,4)
      = 4
```

We continue checking other possible sequence starts.

---

# Step 9 — Return the Answer

After all unique numbers have been processed:

```java id="m8c1x4"
return answer;
```

This gives the length of the longest consecutive sequence.

---

## Complete Walkthrough

Consider:

```text id="q7m2v9"
nums = [100,4,200,1,3,2]
```

### Step 1 — Build the Set

```text id="c5x8n1"
{100,4,200,1,3,2}
```

---

### Check `100`

Does:

```text id="m4v9q2"
99
```

exist?

No.

Therefore, `100` is a sequence start.

Start:

```text id="x7n3c6"
current = 100
length = 1
```

Check:

```text id="p2m8v5"
101
```

does not exist.

So:

```text id="n6q1x9"
length = 1
```

Update:

```text id="c3v7m2"
answer = 1
```

---

### Check `4`

Does:

```text id="x8m1c5"
3
```

exist?

Yes.

Therefore `4` is not a sequence start.

Skip it.

---

### Check `200`

Does:

```text id="q4v7n2"
199
```

exist?

No.

So `200` is a sequence start.

Check:

```text id="m6c2x8"
201
```

does not exist.

Length:

```text id="p3v9n5"
1
```

Answer remains:

```text id="k7m1c4"
1
```

---

### Check `1`

Does:

```text id="x5q8n2"
0
```

exist?

No.

Therefore `1` is a sequence start.

Start:

```text id="v3m7c9"
current = 1
length = 1
```

Check:

```text id="n2x6q4"
2 exists
```

So:

```text id="p8m3v1"
current = 2
length = 2
```

Check:

```text id="c5n9x7"
3 exists
```

So:

```text id="q1m6v8"
current = 3
length = 3
```

Check:

```text id="x4c2n5"
4 exists
```

So:

```text id="m7v1q9"
current = 4
length = 4
```

Check:

```text id="n8c3x6"
5 does not exist
```

Stop.

Update:

```text id="p2m7v4"
answer = max(1,4)
       = 4
```

---

### Check `3`

Does:

```text id="q6x1m8"
2
```

exist?

Yes.

Skip.

---

### Check `2`

Does:

```text id="v5n3c7"
1
```

exist?

Yes.

Skip.

---

### Final Answer

```text id="m8q2x6"
4
```

The longest consecutive sequence is:

```text id="p4v7n1"
1 → 2 → 3 → 4
```

---

## Pattern Recognition

### Pattern: HashSet + Sequence Start Detection

This problem is an important variation of the HashSet pattern.

The basic idea is:

```text id="x3m8q1"
Put everything into a HashSet
        ↓
Find numbers with no predecessor
        ↓
Those numbers are sequence starts
        ↓
Expand forward using num + 1
        ↓
Track the longest length
```

The critical condition is:

```java id="c7v2n5"
!set.contains(num - 1)
```

This tells us:

> "There is no number immediately before me, so I must be the beginning of a sequence."

---

## Why We Don't Sort

Sorting would make the problem easier to visualize:

```text id="p5m2x8"
[100,4,200,1,3,2]

        ↓

[1,2,3,4,100,200]
```

But:

```text id="n6c1v7"
Arrays.sort()
```

takes:

```text id="q8m3x5"
O(n log n)
```

The problem specifically requires:

```text id="v4c9n2"
O(n)
```

So we use a HashSet instead.

With a HashSet:

```text id="m7x2q6"
contains()
```

takes O(1) average time.

That allows us to find consecutive numbers without sorting.

---

## Why Duplicates Don't Cause a Problem

Consider:

```text id="c5n8x1"
nums = [1,0,1,2]
```

There are two `1`s.

After inserting everything into a `HashSet`:

```text id="m3v7q9"
{0,1,2}
```

The duplicate disappears automatically.

Now we can process:

```text id="x8c2n5"
0 → 1 → 2
```

and get:

```text id="p4m6v1"
3
```

This is another reason the HashSet is a good choice.

---

## Why the Algorithm Is O(n)

This is one of the most important things to understand.

At first glance, we have:

```java id="n7x3m8"
for(Integer num: set)
```

and inside it:

```java id="q5c1v6"
while(set.contains(current+1))
```

It may look like:

```text id="m8x2n4"
O(n²)
```

But it isn't.

The `while` loop only starts for **sequence beginnings**.

For example:

```text id="v3q7c9"
1,2,3,4,5
```

only `1` starts the scan.

We do:

```text id="p5m1x8"
1 → 2 → 3 → 4 → 5
```

Once.

We don't repeat the scan from `2`, `3`, `4`, or `5`.

Therefore, across the entire algorithm, each number participates in sequence expansion at most once.

This gives:

```text id="c6n2v9"
O(n)
```

average time.

---

## The Most Important Insight

Remember this:

```text id="x7m3q5"
If num - 1 exists:
    num is NOT the start.

If num - 1 does not exist:
    num IS the start.
```

Then:

```text id="n8c1v6"
Start from num
      ↓
Check num + 1
      ↓
Check num + 2
      ↓
Continue until missing
```

This is the entire algorithm.

---

## Edge Cases

### 1. Empty Array

```text id="q4m8x2"
nums = []
```

The set is empty.

The loop does not execute.

Answer:

```text id="c7v1n5"
0
```

---

### 2. One Element

```text id="m3x9q6"
nums = [10]
```

`10` has no predecessor.

So:

```text id="p8c2v4"
length = 1
```

Answer:

```text id="n5m7x1"
1
```

---

### 3. All Numbers Are Consecutive

```text id="x6c3m8"
nums = [1,2,3,4,5]
```

Only `1` is a sequence start.

We scan:

```text id="q2v7n4"
1 → 2 → 3 → 4 → 5
```

Answer:

```text id="m8c1x5"
5
```

---

### 4. No Consecutive Numbers

```text id="v4n9m2"
nums = [10,30,50]
```

Every number is a sequence start.

Each sequence has length `1`.

Answer:

```text id="x7q3c6"
1
```

---

### 5. Duplicate Values

```text id="n5m2v8"
nums = [1,2,2,3,4]
```

The HashSet becomes:

```text id="c6x1q9"
{1,2,3,4}
```

Sequence:

```text id="p8m4v7"
1 → 2 → 3 → 4
```

Answer:

```text id="q3n7x5"
4
```

---

### 6. Negative Numbers

The HashSet works exactly the same way with negative values.

For:

```text id="m2c8v1"
[-2,-1,0,1,5]
```

the sequence is:

```text id="x7n3q6"
-2 → -1 → 0 → 1
```

Length:

```text id="p5m8c2"
4
```

---

## Complexity

Let:

```text id="v1q6m9"
n = nums.length
```

### Time Complexity

Building the HashSet:

```text id="x4c8n2"
O(n)
```

Traversing the set:

```text id="m7v3q5"
O(n)
```

Sequence expansion is also:

```text id="c1x9n6"
O(n)
```

overall because each number is part of a forward sequence scan at most once.

Therefore, the total average time complexity is:

```text id="q8m2v4"
O(n)
```

---

### Space Complexity

The HashSet can contain up to `n` unique numbers.

Therefore:

```text id="p3v7c1"
O(n)
```

auxiliary space.

---

## Key Learning

### 1. HashSet Can Replace Sorting

When you only need fast existence checks, a HashSet can sometimes eliminate the need for sorting.

Instead of:

```text id="x8m4q2"
Sort → scan
```

we can use:

```text id="c5n1v7"
HashSet → existence checks
```

---

### 2. Look for a "Sequence Start"

This is the most important trick.

Don't blindly start a sequence from every number.

First ask:

```text id="m7c3x9"
Does num - 1 exist?
```

If yes:

```text id="q2v8n5"
Skip it.
```

If no:

```text id="p6m1c4"
Start the sequence.
```

---

### 3. Duplicates Can Be Removed Automatically

A `HashSet` stores only unique values.

This makes problems involving:

* duplicate numbers
* existence checks
* consecutive values

particularly suitable for HashSet solutions.

---

### 4. Nested Loops Don't Automatically Mean O(n²)

This problem is a great example.

We have:

```text id="x4q9m2"
for
    while
```

but the solution is still:

```text id="n6c3v8"
O(n)
```

because the inner loop does not restart for every element.

The sequence-start condition ensures that each consecutive sequence is scanned only from its beginning.

---

## Visual Summary

For:

```text id="z8m3c5"
nums = [100,4,200,1,3,2]
```

Create the set:

```text id="q4v7n1"
{100,4,200,1,3,2}
```

Find sequence starts:

```text id="m6c2x8"
100 → 99 doesn't exist → START
4   → 3 exists       → SKIP
200 → 199 doesn't exist → START
1   → 0 doesn't exist → START
3   → 2 exists       → SKIP
2   → 1 exists       → SKIP
```

Expand from `1`:

```text id="p5n8v3"
1 → 2 → 3 → 4
```

Length:

```text id="x7m1q6"
4
```

Final:

```text id="c3v9n2"
answer = 4
```

---

## Final Takeaway

The complete strategy is:

```text id="n8m3x5"
Put all numbers into a HashSet
          ↓
For every unique number:
          ↓
Does num - 1 exist?
      ↓           ↓
     YES          NO
      ↓            ↓
    Skip       Sequence Start
                   ↓
             Check num + 1
                   ↓
             Check num + 2
                   ↓
             Continue until
             number is missing
                   ↓
             Track max length
```

The main pattern to remember is:

> **For longest consecutive sequence problems, use a HashSet for O(1) average existence checks, and only start counting from numbers that have no predecessor (`num - 1`).**

This sequence-start trick is what turns what looks like a potentially quadratic solution into the required **O(n)** average-time solution.
