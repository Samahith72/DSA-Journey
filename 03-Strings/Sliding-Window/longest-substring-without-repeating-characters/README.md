# WIN #54 — Longest Substring Without Repeating Characters

## Problem

[LeetCode 3 — Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

Given a string `s`, find the length of the **longest substring** that contains no duplicate characters.

A **substring** must be a contiguous part of the string.

For example:

```text id="b0q7hj"
"abc"
```

is a substring of:

```text id="1z9k3m"
"abcabcbb"
```

But:

```text id="g4v8q2"
"ac"
```

is not a substring because the characters are not contiguous.

---

## Example 1

```text id="x6m2p8"
Input:
s = "abcabcbb"

Output:
3
```

The longest substring without repeating characters can be:

```text id="n4v7c1"
"abc"
```

Its length is:

```text id="q8m3x5"
3
```

Other valid answers include:

```text id="r1c6v9"
"bca"
"cab"
```

---

## Example 2

```text id="p5x9m2"
Input:
s = "bbbbb"

Output:
1
```

The only possible substring without duplicate characters is:

```text id="v7c3n8"
"b"
```

So the answer is:

```text id="j2m6q4"
1
```

---

## Example 3

```text id="k8v2c5"
Input:
s = "pwwkew"

Output:
3
```

The longest substring without repeating characters is:

```text id="x3m7q1"
"wke"
```

Length:

```text id="n6c2v9"
3
```

Notice that:

```text id="g5p8m4"
"pwke"
```

is not valid because it is a **subsequence**, not a substring.

The characters must be contiguous.

---

## My Java Solution

```java id="r7x3m9"
class Solution {
    public int lengthOfLongestSubstring(String s) {
        
        HashSet< Character> set = new HashSet<>();

        int left= 0;
        int answer = 0;

        for(int right=0;right <s.length();right++){
            
            while(set.contains(s.charAt(right))){
                set.remove(s.charAt(left));
                left++;
            }


            set.add(s.charAt(right));
            answer = Math.max(answer, right - left+1);
        }
        return answer;
    }
}
```

---

## My Thought Process

The key phrase in this problem is:

> **longest substring without duplicate characters**

This immediately suggests a **Sliding Window**.

We maintain a window:

```text id="q6m2x8"
[left ........ right]
```

Inside this window, every character must be unique.

We use a `HashSet` to store the characters currently inside the window.

The idea is:

```text id="z4c7n1"
Expand right
     ↓
New character
     ↓
Is it already in the window?
     ↓
   YES
     ↓
Move left until duplicate is removed
     ↓
Add new character
     ↓
Update maximum length
```

---

# Step 1 — Create a HashSet

```java id="m8v3q6"
HashSet<Character> set = new HashSet<>();
```

The `HashSet` stores the characters currently present in our sliding window.

For example, if our current substring is:

```text id="x2n7c5"
"abc"
```

then:

```text id="p4m9v1"
set = {a, b, c}
```

The important property of a `HashSet` is that it does not allow duplicate values.

That makes it perfect for this problem.

---

# Step 2 — Initialize the Left Pointer

```java id="c6x1m8"
int left = 0;
```

`left` represents the beginning of our current window.

Initially:

```text id="q7v3n5"
left = 0
```

---

# Step 3 — Initialize the Answer

```java id="m2c8x4"
int answer = 0;
```

This stores the maximum length found so far.

Initially:

```text id="v5n1q7"
answer = 0
```

---

# Step 4 — Expand the Window Using `right`

```java id="x8m4c2"
for(int right = 0; right < s.length(); right++){
```

The `right` pointer moves from left to right through the string.

So our window is:

```text id="n6q2v9"
[left ........ right]
```

As `right` moves forward, the window expands.

---

# Step 5 — Check for a Duplicate

```java id="r3v7m1"
while(set.contains(s.charAt(right))){
```

This is the most important part.

Suppose the current window is:

```text id="c8m2x6"
"abc"
```

and `right` encounters another:

```text id="q5v9n3"
c
```

The set already contains `c`.

Therefore:

```java id="p7x1m4"
set.contains(s.charAt(right))
```

returns:

```text id="y6c3q8"
true
```

The current window is no longer valid.

We need to shrink it.

---

# Step 6 — Remove Characters From the Left

```java id="v2m8x5"
set.remove(s.charAt(left));
left++;
```

We remove the character at the left side of the window and move `left` forward.

For example:

```text id="j4q7c1"
window = "abc"
```

and a duplicate `c` arrives.

We first remove:

```text id="p8n3m6"
a
```

Window becomes conceptually:

```text id="x5c2v9"
"bc"
```

Then `left` moves forward.

If the duplicate is still present, the `while` loop continues removing characters.

Eventually the duplicate is removed from the window.

---

# Step 7 — Add the New Character

Once the duplicate has been removed:

```java id="m7c1x8"
set.add(s.charAt(right));
```

Now the window is valid again.

Every character inside the window is unique.

---

# Step 8 — Calculate the Current Window Length

```java id="q4v9m2"
right - left + 1
```

This gives the number of characters between the two pointers.

For example:

```text id="a6n3x7"
left = 2
right = 4
```

then:

```text id="p8m1c5"
4 - 2 + 1 = 3
```

So the current window has length `3`.

---

# Step 9 — Update the Maximum

```java id="v3q7n1"
answer = Math.max(answer, right - left + 1);
```

We compare the current valid window with the longest window we've seen so far.

For example:

```text id="k5m8x2"
current length = 4
answer = 3
```

Then:

```text id="c1v6q9"
answer = 4
```

---

# Step 10 — Return the Answer

After processing the entire string:

```java id="n7m3x5"
return answer;
```

The answer contains the length of the longest substring without duplicate characters.

---

## Complete Walkthrough

Let's trace your solution with:

```text id="q8m2v6"
s = "abcabcbb"
```

Initially:

```text id="x4c7n1"
left = 0
answer = 0
set = {}
```

---

### `right = 0`

Character:

```text id="m5v1q8"
'a'
```

`a` is not in the set.

Add:

```text id="r3c9x2"
set = {a}
```

Window:

```text id="p7m4v6"
"a"
```

Length:

```text id="n2q8c5"
1
```

Update:

```text id="x6m1v9"
answer = 1
```

---

### `right = 1`

Character:

```text id="c4v8n2"
'b'
```

Not present.

Add:

```text id="m7q3x1"
set = {a,b}
```

Window:

```text id="p5c9v4"
"ab"
```

Length:

```text id="n1m6x8"
2
```

Update:

```text id="q3v7c2"
answer = 2
```

---

### `right = 2`

Character:

```text id="x8m2c5"
'c'
```

Not present.

Add:

```text id="v4q1n7"
set = {a,b,c}
```

Window:

```text id="m6c3x9"
"abc"
```

Length:

```text id="p2v8q5"
3
```

Update:

```text id="n7m1c4"
answer = 3
```

---

### `right = 3`

Character:

```text id="c5x9m2"
'a'
```

But:

```text id="q7v3n8"
set.contains('a') = true
```

So we have a duplicate.

Current window:

```text id="m4c8x1"
"abc"
```

Remove from the left:

```text id="p6v2q9"
remove 'a'
left++
```

Now:

```text id="n3m7x5"
left = 1
set = {b,c}
```

The duplicate `a` is gone.

Add the new `a`:

```text id="v8c1m6"
set = {b,c,a}
```

Current window:

```text id="q2x7n4"
"bca"
```

Length:

```text id="m5v9c3"
3
```

Answer remains:

```text id="p1q6x8"
3
```

---

### `right = 4`

Character:

```text id="z3m7c1"
'b'
```

`b` already exists.

Remove from the left:

```text id="v5x2n8"
remove 'b'
left++
```

Now:

```text id="q6m1c4"
left = 2
set = {c,a}
```

Add `b`:

```text id="n8v3x7"
set = {c,a,b}
```

Window:

```text id="m2q9c5"
"cab"
```

Length:

```text id="x7v1n6"
3
```

Answer remains `3`.

---

The same process continues for the remaining characters.

The maximum length never becomes greater than:

```text id="k4c8m2"
3
```

Therefore:

```text id="v6q1n9"
Output = 3
```

---

## Why Do We Use `while` Instead of `if`?

This is an important sliding-window concept.

Your code uses:

```java id="f8m3q6"
while(set.contains(s.charAt(right))){
    set.remove(s.charAt(left));
    left++;
}
```

not:

```java
if(...)
```

Why?

Because we need to keep shrinking the window until the duplicate is completely removed.

For example:

```text id="r5c1v8"
window = "abc"
```

and the new character is:

```text id="m7q2x4"
c
```

We have:

```text id="p3n8c6"
[a,b,c] + c
```

We cannot simply move `left` once in every situation. We need to continue moving it until the existing duplicate is no longer inside the window.

The `while` loop guarantees:

```text id="x6v1m9"
window contains no duplicate characters
```

before we add the new character.

---

## Pattern Recognition

### Pattern: Variable-Size Sliding Window + HashSet

This is one of the most important sliding-window patterns.

The window has a **variable size**.

Unlike a fixed-size window where `k` is known:

```text id="q3m7c1"
window size = k
```

here the window expands and shrinks depending on whether it contains a duplicate.

The rule is:

```text id="v8n2x5"
Expand → right++

Duplicate?
    ↓
Shrink → left++

Valid window?
    ↓
Update answer
```

---

## The Core Sliding Window Invariant

A very important idea is:

> At every point in the algorithm, the window `[left...right]` contains only unique characters.

This is called the **window invariant**.

For example:

```text id="m4q8c2"
[a b c]
```

is valid.

But:

```text id="x7n3v9"
[a b c a]
```

is invalid.

So we move `left` until:

```text id="p1m6q4"
[b c a]
```

becomes valid again.

This invariant makes the entire algorithm easy to reason about.

---

## Why the HashSet Works So Well

We need to answer this question quickly:

> Does the current window already contain this character?

A `HashSet` gives us average:

```text id="c9v2m7"
O(1)
```

lookup.

So:

```java id="n5x1q8"
set.contains(character)
```

quickly tells us whether the new character would create a duplicate.

Similarly:

```java id="m7c3v9"
set.remove(character)
```

quickly removes the character when we shrink the window.

---

## Visual Representation

For:

```text id="x4m8c1"
s = "abcabcbb"
```

we start with:

```text id="q7n2v5"
[a b c]
```

Then the next `a` creates a duplicate:

```text id="m3x8c6"
[a b c a]
```

So we shrink:

```text id="v1q6n9"
  [b c a]
```

Then continue expanding.

The window is always maintained as:

```text id="k5m2x7"
[left -------- right]
```

with no duplicate characters.

---

## Edge Cases

### 1. Empty String

The constraints allow:

```text id="c8v1m5"
s = ""
```

The loop never executes.

Therefore:

```text id="q6n3x9"
answer = 0
```

Correct.

---

### 2. One Character

```text id="p4m8v2"
s = "a"
```

The longest valid substring is:

```text id="x7c1n5"
"a"
```

Answer:

```text id="m3q9v6"
1
```

---

### 3. All Characters Are the Same

```text id="n5c2x8"
s = "bbbbb"
```

The window can contain only one `b`.

Every new `b` causes the old `b` to be removed.

Therefore:

```text id="q1m7v4"
answer = 1
```

---

### 4. Entire String Has Unique Characters

```text id="c6x9n2"
s = "abc"
```

No duplicate is ever found.

The window keeps expanding:

```text id="m8v3q5"
a
ab
abc
```

Answer:

```text id="p2c7x1"
3
```

---

### 5. Duplicate Appears Far Inside the Window

The `while` loop keeps moving `left` until the duplicate is removed.

This is why the solution works even when the duplicate is not immediately next to the left pointer.

---

## Complexity

Let:

```text id="v5n2c8"
n = s.length()
```

### Time Complexity

At first, the nested `while` loop may look like it makes the algorithm `O(n²)`.

But it does not.

The key observation is:

* `right` moves forward at most `n` times.
* `left` also moves forward at most `n` times.
* A character can be added to the set and removed from the set only a limited number of times.

Therefore, the total work done by both pointers is linear.

So the time complexity is:

```text id="q7m3x1"
O(n)
```

---

### Space Complexity

The `HashSet` stores the characters currently inside the window.

In the worst case, it can contain `n` different characters.

Therefore:

```text id="c8v2m6"
O(n)
```

space in the general case.

For the given character set, the number of possible distinct characters is bounded, but `O(n)` is the standard general complexity.

---

## Key Learning

### 1. Sliding Window Maintains a Valid Range

The window:

```text id="x5m1q8"
[left ... right]
```

always represents the current candidate substring.

The goal is to keep it valid while expanding it as much as possible.

---

### 2. HashSet Is Perfect for Uniqueness

When the problem asks:

> Does this collection contain a duplicate?

a `HashSet` is often a strong choice.

Here:

```java id="n7c3v9"
set.contains(character)
```

immediately tells us whether the window already contains that character.

---

### 3. `left` Only Moves Forward

Notice:

```java id="p4m8x2"
left++;
```

`left` never moves backward.

Similarly:

```text id="q6v1n5"
right
```

only moves forward.

This is one of the reasons the total complexity is `O(n)`.

---

### 4. The Window Is Not Always the Answer

At every point, we have a valid window.

But it may not be the longest one.

That's why we continuously calculate:

```java id="m3c7x9"
right - left + 1
```

and update:

```java id="v8q2n6"
answer
```

---

## Sliding Window Template

Your solution represents a very useful general template:

```text id="j4n8c1"
left = 0

for right from 0 to n-1:

    add/process s[right]

    while window is invalid:
        remove/process s[left]
        left++

    update answer
```

For this problem:

```text id="p7m2x5"
Window invalid
      ↓
Duplicate character exists
```

So:

```text id="c3v9n1"
while(set.contains(s.charAt(right)))
```

shrinks the window.

---

## Final Takeaway

The entire solution can be remembered as:

```text id="q6m1v8"
Create HashSet
      ↓
left = 0
      ↓
Move right through the string
      ↓
New character already exists?
      ↓
    YES
      ↓
Remove characters from left
until duplicate disappears
      ↓
Add new character
      ↓
Calculate window length
      ↓
Update maximum
```

The main pattern to remember is:

> **For the longest substring satisfying a condition, use a variable-size sliding window. Expand with `right`, and whenever the window becomes invalid, shrink it with `left` until it becomes valid again.**

For uniqueness problems specifically:

> **Sliding Window + HashSet** is one of the most important patterns to recognize.
