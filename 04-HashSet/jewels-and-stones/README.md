# WIN #44 — Jewels and Stones

## Problem

[LeetCode 771 — Jewels and Stones](https://leetcode.com/problems/jewels-and-stones/)

You are given two strings:

* `jewels` — characters representing the types of stones that are jewels.
* `stones` — characters representing the stones you currently have.

Return the number of stones that are also jewels.

Characters are **case-sensitive**.

For example:

```text
"a" != "A"
```

### Example 1

```text
Input: jewels = "aA"
       stones = "aAAbbbb"

Output: 3
```

The jewels are:

```text
a
A
```

The stones are:

```text
a A A b b b b
```

The three stones that are jewels are:

```text
a, A, A
```

Therefore:

```text
Output = 3
```

### Example 2

```text
Input: jewels = "z"
       stones = "ZZ"

Output: 0
```

`z` and `Z` are different characters because the comparison is case-sensitive.

Therefore, none of the stones are jewels.

---

## My Java Solution

```java
class Solution {
    public int numJewelsInStones(String jewels, String stones) {
        HashSet<Character> set = new HashSet<>();
        for(char c: jewels.toCharArray()){
            set.add(c);
        }
        int result = 0;
        for(char c: stones.toCharArray()){
            if(set.contains(c)){
                result++;
            }
        }

        return result;
    }
}
```

---

## My Thought Process

The main thing I need to do is repeatedly answer:

> "Is this stone a jewel?"

For every character in `stones`, I need to check whether that character exists in `jewels`.

A `HashSet` is a natural choice because it is designed for **membership checking**.

The approach is:

```text
jewels
  ↓
Store every jewel character in a HashSet
  ↓
Traverse stones
  ↓
Check if each stone exists in the HashSet
  ↓
If yes → increment result
```

---

# Step 1 — Create a HashSet

```java
HashSet<Character> set = new HashSet<>();
```

The set will contain all the characters from `jewels`.

For example:

```text
jewels = "aA"
```

The set becomes:

```text
{ 'a', 'A' }
```

A `HashSet` stores unique values, which is perfect here because the problem guarantees that all characters in `jewels` are unique.

---

# Step 2 — Store All Jewel Characters

```java
for(char c: jewels.toCharArray()){
    set.add(c);
}
```

For:

```text
jewels = "aA"
```

we add:

```text
'a'
'A'
```

So:

```text
set = {'a', 'A'}
```

Now we have a quick way to determine whether a character is a jewel.

---

# Step 3 — Traverse the Stones

Next:

```java
for(char c: stones.toCharArray()){
```

We inspect every stone one by one.

For:

```text
stones = "aAAbbbb"
```

we process:

```text
a
A
A
b
b
b
b
```

---

# Step 4 — Check Whether the Stone Is a Jewel

For every stone:

```java
if(set.contains(c)){
```

we check whether the character exists in the `HashSet`.

For example:

```text
c = 'a'
```

Since:

```text
'a' ∈ {'a', 'A'}
```

the condition is true.

So:

```java
result++;
```

---

# Step 5 — Count Every Matching Stone

For:

```text
jewels = "aA"
stones = "aAAbbbb"
```

the checking looks like:

```text
Stone    Jewel?    Count
-------------------------
a        Yes        1
A        Yes        2
A        Yes        3
b        No         3
b        No         3
b        No         3
b        No         3
```

Final result:

```text
result = 3
```

---

# Step 6 — Return the Result

Finally:

```java
return result;
```

The answer is the total number of stones that are also jewels.

---

## Complete Walkthrough

Consider:

```text
jewels = "aA"
stones = "aAAbbbb"
```

### Build the Set

Start:

```text
set = {}
```

Add `a`:

```text
set = {'a'}
```

Add `A`:

```text
set = {'a', 'A'}
```

---

### Process Stones

First:

```text
'a'
```

Check:

```text
set.contains('a') → true
```

```text
result = 1
```

Second:

```text
'A'
```

Check:

```text
set.contains('A') → true
```

```text
result = 2
```

Third:

```text
'A'
```

Check:

```text
set.contains('A') → true
```

```text
result = 3
```

Now the remaining stones are `b`.

```text
set.contains('b') → false
```

No more increments happen.

Final:

```text
result = 3
```

---

## Pattern Recognition

### Pattern: HashSet + Membership Checking

The key clue is:

> We need to repeatedly check whether an element belongs to a collection.

This is a classic **HashSet** use case.

Instead of repeatedly searching through the `jewels` string:

```text
For every stone
    Search jewels
```

we preprocess the jewels:

```text
jewels
  ↓
HashSet
  ↓
O(1) average membership lookup
```

Then:

```text
for every stone:
    if stone exists in set:
        count++
```

### General Pattern

Whenever a problem asks:

```text
"Does this element exist?"
```

and there are many membership checks, think:

```text
HashSet
```

Examples of similar situations:

* Checking duplicates
* Checking whether an element belongs to a collection
* Finding common elements
* Filtering elements based on membership
* Detecting previously seen values

---

## Why HashSet Instead of HashMap?

We only care about:

```text
Does this character exist?
```

We do **not** need to store any associated value.

Therefore:

```text
HashSet
```

is more appropriate than:

```text
HashMap
```

A `HashMap` would be useful if we needed something like:

```text
character → frequency
```

But here we only need:

```text
character exists → yes/no
```

---

## Important Observation About Case Sensitivity

The problem explicitly says characters are case-sensitive.

Therefore:

```text
'a' != 'A'
```

Our `HashSet<Character>` naturally handles this.

For:

```text
jewels = "z"
stones = "ZZ"
```

the set contains:

```text
{'z'}
```

Checking:

```text
'Z'
```

gives:

```text
set.contains('Z') → false
```

Therefore:

```text
Output = 0
```

---

## Edge Cases

### 1. All Stones Are Jewels

```text
jewels = "abc"
stones = "abcabc"
```

Every stone is a jewel.

```text
Output = 6
```

---

### 2. No Stones Are Jewels

```text
jewels = "abc"
stones = "XYZ"
```

No character matches.

```text
Output = 0
```

---

### 3. Repeated Jewel Stones

```text
jewels = "a"
stones = "aaaa"
```

The jewel character can appear multiple times in `stones`.

Every occurrence should be counted:

```text
a → 1
a → 2
a → 3
a → 4
```

Therefore:

```text
Output = 4
```

The `HashSet` stores `a` only once, but every matching stone is counted separately.

---

### 4. Case-Sensitive Characters

```text
jewels = "aA"
stones = "Aa"
```

Both are jewels.

```text
Output = 2
```

---

## Complexity

Let:

```text
J = jewels.length
S = stones.length
```

### Time Complexity

Building the set takes:

```text
O(J)
```

Traversing `stones` takes:

```text
O(S)
```

Each `HashSet.contains()` operation is **O(1) average**.

Therefore:

```text
O(J + S)
```

---

### Space Complexity

The HashSet stores the unique jewel characters.

Therefore:

```text
O(J)
```

In this problem, the input contains only English letters, so the number of possible distinct characters is bounded by the character set.

---

## Key Learning

### 1. Use HashSet for Existence Checks

If the question is:

> "Does this value exist?"

a `HashSet` should immediately come to mind.

```java
set.contains(value)
```

---

### 2. Separate Membership From Counting

The HashSet answers:

```text
Is this character a jewel?
```

The variable `result` answers:

```text
How many jewel stones did we find?
```

So the responsibilities are clear:

```text
HashSet → membership
result  → count
```

---

### 3. Preprocess When You Have Repeated Queries

Instead of repeatedly searching `jewels`, we build the set once.

```text
Build Set
   ↓
Many O(1) average lookups
```

This is a common optimization pattern.

---

### 4. HashSet Does Not Count Frequency

The set only tells us whether a character exists.

For example:

```text
jewels = "a"
stones = "aaaa"
```

The set is still:

```text
{'a'}
```

But we separately count all four matching occurrences in `stones`.

---

## Final Takeaway

The core idea is:

```text
Store all jewel characters in a HashSet
              ↓
Traverse every stone
              ↓
Check set.contains(stone)
              ↓
If true → increment result
              ↓
Return result
```

The pattern to remember is:

> **When you only need to know whether an element exists in a collection, HashSet provides a simple and efficient membership check.**

This solution runs in **O(J + S) time** with **O(J) auxiliary space**.
