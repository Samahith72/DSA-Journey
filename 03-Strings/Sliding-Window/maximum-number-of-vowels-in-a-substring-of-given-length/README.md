# WIN #60 — Maximum Number of Vowels in a Substring of Given Length

## Problem

[LeetCode 1456 — Maximum Number of Vowels in a Substring of Given Length](https://leetcode.com/problems/maximum-number-of-vowels-in-a-substring-of-given-length/)

Given a string `s` and an integer `k`, return the **maximum number of vowels** present in any substring of `s` with length exactly `k`.

The vowels are:

```text
a, e, i, o, u
```

The important requirement is:

> Find a substring of exactly length `k` that contains the maximum possible number of vowels.

---

## Example 1

```text
Input:
s = "abciiidef"
k = 3

Output:
3
```

The substring:

```text
"iii"
```

has:

```text
3
```

vowels.

Therefore:

```text
answer = 3
```

---

## Example 2

```text
Input:
s = "aeiou"
k = 2

Output:
2
```

Every substring of length `2` contains two vowels.

For example:

```text
"ae"
"ei"
"io"
"ou"
```

Each contains:

```text
2
```

vowels.

Therefore:

```text
answer = 2
```

---

## Example 3

```text
Input:
s = "leetcode"
k = 3

Output:
2
```

Some valid substrings are:

```text
"lee"
"eet"
"ode"
```

Each of these contains:

```text
2
```

vowels.

Therefore:

```text
answer = 2
```

---

## My Java Solution

```java
class Solution {
    public int maxVowels(String s, int k) {

        int right = 0;
        int vowelCount = 0;

        while(right < k){
            if(isVowel(s.charAt(right))){
                vowelCount++;
            }
            right++;
        }

        int answer = vowelCount;
        
        while(right < s.length()){
            if(isVowel(s.charAt(right))){
                vowelCount++;
            }

            if(isVowel(s.charAt(right - k))){
                vowelCount--;
            }

            answer = Math.max(answer, vowelCount);
            right++;
        }

        return answer;
        
    }

    private boolean isVowel(char c){
        if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
            return true;
        }
        return false;
    }
}
```

---

## My Thought Process

The first thing to notice is that the substring must always have exactly:

```text
k
```

characters.

For example:

```text
s = "abciiidef"
k = 3
```

The possible windows are:

```text
"abc"
"bci"
"cii"
"iii"
"iid"
"ide"
"def"
```

We need to count the vowels in every window and find the maximum.

A brute-force solution could count the vowels in every substring separately.

But that would repeat a lot of work.

For example, when moving from:

```text
"abc"
```

to:

```text
"bci"
```

most of the characters are the same.

Instead of counting all three characters again, we can:

```text
Remove the character leaving the window
        ↓
Add the character entering the window
        ↓
Update the vowel count
```

This is exactly what a **fixed-size sliding window** does.

The strategy is:

```text
Create the first window of size k
          ↓
Count its vowels
          ↓
Store the count as the answer
          ↓
Move the window one position at a time
          ↓
Add the new character
          ↓
Remove the old character
          ↓
Update maximum
```

---

# Step 1 — Initialize the Right Pointer

```java
int right = 0;
```

The `right` pointer represents the position we are currently processing.

Initially:

```text
right = 0
```

We use it to construct the first window of size `k`.

---

# Step 2 — Initialize the Vowel Count

```java
int vowelCount = 0;
```

This variable stores:

```text
number of vowels inside the current window
```

Initially:

```text
vowelCount = 0
```

---

# Step 3 — Build the First Window

```java
while(right < k){
    if(isVowel(s.charAt(right))){
        vowelCount++;
    }
    right++;
}
```

We process the first `k` characters.

For:

```text
s = "abciiidef"
k = 3
```

the first window is:

```text
"abc"
```

We check:

```text
a → vowel
b → not vowel
c → not vowel
```

Therefore:

```text
vowelCount = 1
```

After processing the first three characters:

```text
right = 3
```

The first window is now complete.

---

# Step 4 — Store the First Answer

```java
int answer = vowelCount;
```

The first window gives us our initial maximum.

For:

```text
"abc"
```

we have:

```text
vowelCount = 1
```

Therefore:

```text
answer = 1
```

---

# Step 5 — Slide the Window

Now we process the rest of the string:

```java
while(right < s.length()){
```

The current window has exactly:

```text
k
```

characters.

Instead of creating a completely new substring, we move it one position to the right.

For example:

```text
"abc"
```

becomes:

```text
"bci"
```

Then:

```text
"bci"
```

becomes:

```text
"cii"
```

and so on.

---

# Step 6 — Add the New Character

```java
if(isVowel(s.charAt(right))){
    vowelCount++;
}
```

The character at `right` is entering the window.

For example:

```text
Current window:
"abc"

Next window:
"bci"
```

The new character is:

```text
i
```

Since `i` is a vowel:

```text
vowelCount++
```

So:

```text
vowelCount = 2
```

---

# Step 7 — Remove the Character Leaving the Window

This is the other half of the sliding window:

```java
if(isVowel(s.charAt(right - k))){
    vowelCount--;
}
```

The character at:

```text
right - k
```

is the character that is leaving the window.

Why?

Suppose:

```text
k = 3
right = 3
```

The new character is:

```text
s[3]
```

The character leaving the window is:

```text
s[3 - 3]
=
s[0]
```

So:

```text
right - k
```

always identifies the character that must be removed.

---

# Example of Adding and Removing

Consider:

```text
s = "abciiidef"
k = 3
```

First window:

```text
"abc"
```

Vowels:

```text
a
```

Count:

```text
1
```

Now slide to:

```text
"bci"
```

The new character is:

```text
i
```

Add:

```text
1 + 1 = 2
```

The character leaving is:

```text
a
```

Remove:

```text
2 - 1 = 1
```

So the new vowel count is:

```text
1
```

This gives the correct count for:

```text
"bci"
```

---

# Step 8 — Update the Maximum

```java
answer = Math.max(answer, vowelCount);
```

After updating the current window's vowel count, we compare it with the best answer so far.

For example:

```text
answer = 2
vowelCount = 3
```

Then:

```text
answer = max(2,3)
       = 3
```

The maximum is updated.

---

# Step 9 — Move the Right Pointer

```java
right++;
```

After processing the current window, we move `right` forward.

This creates the next window.

For example:

```text
"abc"
```

becomes:

```text
"bci"
```

then:

```text
"cii"
```

and so on.

---

# Step 10 — Check Whether a Character Is a Vowel

The helper method is:

```java
private boolean isVowel(char c){
    if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'){
        return true;
    }
    return false;
}
```

It returns:

```text
true
```

for:

```text
a
e
i
o
u
```

and:

```text
false
```

for every other lowercase English letter.

This keeps the main sliding-window logic simple.

---

# Complete Walkthrough

Consider:

```text
s = "abciiidef"
k = 3
```

We need the maximum number of vowels in any substring of length `3`.

---

## Step 1 — First Window

First three characters:

```text
"abc"
```

Check each character:

```text
a → vowel
b → consonant
c → consonant
```

Therefore:

```text
vowelCount = 1
answer = 1
```

Window:

```text
[abc]
```

---

## Step 2 — Move to "bci"

Current:

```text
"abc"
```

New:

```text
"bci"
```

Character entering:

```text
i
```

`i` is a vowel:

```text
vowelCount = 2
```

Character leaving:

```text
a
```

`a` is a vowel:

```text
vowelCount = 1
```

Therefore:

```text
answer = max(1,1)
       = 1
```

Current window:

```text
"bci"
```

Vowels:

```text
i
```

Count:

```text
1
```

---

## Step 3 — Move to "cii"

Current:

```text
"bci"
```

New:

```text
"cii"
```

Character entering:

```text
i
```

Vowel:

```text
vowelCount = 2
```

Character leaving:

```text
b
```

`b` is not a vowel.

So:

```text
vowelCount = 2
```

Update:

```text
answer = max(1,2)
       = 2
```

---

## Step 4 — Move to "iii"

Current:

```text
"cii"
```

New:

```text
"iii"
```

Character entering:

```text
i
```

Vowel:

```text
vowelCount = 3
```

Character leaving:

```text
c
```

`c` is not a vowel.

So:

```text
vowelCount = 3
```

Update:

```text
answer = max(2,3)
       = 3
```

Current window:

```text
"iii"
```

Vowels:

```text
i i i
```

Count:

```text
3
```

---

## Step 5 — Move to "iid"

Current:

```text
"iii"
```

New:

```text
"iid"
```

Character entering:

```text
d
```

Not a vowel.

Character leaving:

```text
i
```

Vowel.

So:

```text
vowelCount = 3 - 1
           = 2
```

Answer remains:

```text
3
```

---

## Step 6 — Move to "ide"

Current:

```text
"iid"
```

New:

```text
"ide"
```

Character entering:

```text
e
```

Vowel:

```text
vowelCount = 3
```

Character leaving:

```text
i
```

Vowel:

```text
vowelCount = 2
```

Answer:

```text
3
```

---

## Step 7 — Move to "def"

Current:

```text
"ide"
```

New:

```text
"def"
```

Character entering:

```text
f
```

Not a vowel.

Character leaving:

```text
i
```

Vowel.

Therefore:

```text
vowelCount = 1
```

Answer remains:

```text
3
```

---

## Final Answer

```text
3
```

The substring:

```text
"iii"
```

contains the maximum number of vowels.

---

# Why We Don't Recount Every Window

A brute-force solution could examine every substring of length `k`.

For every window, we could count all `k` characters.

For example:

```text
"abc" → count vowels
"bci" → count vowels
"cii" → count vowels
"iii" → count vowels
```

Each window requires:

```text
O(k)
```

work.

There are approximately:

```text
O(n)
```

windows.

So the total becomes:

```text
O(n × k)
```

In the worst case, this can approach:

```text
O(n²)
```

when `k` is large.

The sliding window avoids this repeated work.

Instead of recounting all `k` characters, we only process:

```text
1 character entering
1 character leaving
```

for every movement.

Therefore:

```text
O(1)
```

work is performed per window.

---

# Why This Is a Fixed-Size Sliding Window

This problem is different from `Fruit Into Baskets`.

In `Fruit Into Baskets`, the window size changes depending on whether there are more than two fruit types.

Here, the window size is always:

```text
k
```

For example:

```text
k = 3
```

Every window must contain exactly three characters:

```text
"abc"
"bci"
"cii"
"iii"
...
```

Therefore, this is a:

```text
Fixed-Size Sliding Window
```

The pattern is:

```text
Build first window of size k
        ↓
Calculate its information
        ↓
Slide one position
        ↓
Add incoming element
        ↓
Remove outgoing element
        ↓
Update answer
```

---

# Pattern Recognition

## Pattern: Fixed-Size Sliding Window

This is a classic fixed-size sliding-window problem.

Whenever a problem asks for something like:

```text
Maximum / minimum / sum / count
```

over every substring or subarray of exactly:

```text
k
```

elements, think:

```text
Fixed-Size Sliding Window
```

The general strategy is:

```text
Create window of size k
        ↓
Calculate initial result
        ↓
Move right
        ↓
Add new element
        ↓
Remove element at right-k
        ↓
Update result
```

For this problem, the information we maintain is:

```text
number of vowels
```

The important pattern to remember is:

> **For a fixed-size window, don't recalculate the entire window after every shift. Add the new element, remove the outgoing element, and update the maintained value.**

---

# Why `right - k` Is the Outgoing Character

This is one of the most important details in the solution.

Suppose:

```text
k = 3
```

and we have:

```text
index:
0 1 2 3 4
```

First window:

```text
[0,1,2]
```

When we move to:

```text
[1,2,3]
```

the character at index:

```text
0
```

leaves.

At this point:

```text
right = 3
```

Therefore:

```text
right - k
= 3 - 3
= 0
```

So:

```java
s.charAt(right - k)
```

is exactly the character that leaves the window.

This allows us to update the vowel count in constant time.

---

# Why We Initialize the First Window Separately

The code first does:

```java
while(right < k){
    ...
    right++;
}
```

This creates the initial window.

For:

```text
k = 3
```

we process:

```text
index 0
index 1
index 2
```

After that:

```text
right = 3
```

The first window is:

```text
[0,1,2]
```

Then the second loop handles all subsequent windows.

This makes the sliding logic straightforward:

```text
First window:
Count everything

Every next window:
Add one
Remove one
```

---

# Edge Cases

## 1. k Equals the String Length

```text
s = "aeiou"
k = 5
```

There is only one possible window:

```text
"aeiou"
```

All five characters are vowels.

Therefore:

```text
answer = 5
```

---

## 2. No Vowels

```text
s = "bcdfgh"
k = 3
```

Every window contains:

```text
0
```

vowels.

Therefore:

```text
answer = 0
```

---

## 3. All Vowels

```text
s = "aeiou"
k = 3
```

Every window of length `3` contains:

```text
3
```

vowels.

Therefore:

```text
answer = 3
```

---

## 4. k = 1

```text
s = "abcde"
k = 1
```

Each window contains exactly one character.

The maximum number of vowels in a one-character window is:

```text
1
```

if at least one vowel exists.

---

## 5. First Window Has the Maximum

```text
s = "aaabcde"
k = 3
```

First window:

```text
"aaa"
```

contains:

```text
3
```

vowels.

No later window can exceed `3`.

Therefore:

```text
answer = 3
```

---

# The Most Important Insight

The key idea is:

```text
Don't recount the entire window.
```

Instead:

```text
Current window
       ↓
Remove outgoing character
       ↓
Add incoming character
       ↓
Get new window count
```

For example:

```text
"abc"
```

becomes:

```text
"bcd"
```

Instead of checking:

```text
b
c
d
```

again, we simply:

```text
Remove a
Add d
```

The same idea works for sums, maximums, minimums, frequencies, and many other fixed-size window problems.

---

# Visual Summary

For:

```text
s = "abciiidef"
k = 3
```

The windows are:

```text
abc → 1 vowel
bci → 1 vowel
cii → 2 vowels
iii → 3 vowels
iid → 2 vowels
ide → 2 vowels
def → 1 vowel
```

Therefore:

```text
answer = 3
```

The maximum occurs at:

```text
"iii"
```

---

# Complexity

Let:

```text
n = s.length()
```

## Time Complexity

The first window processes `k` characters:

```text
O(k)
```

Every remaining character is processed once:

```text
O(n-k)
```

Each character involves constant-time operations.

Therefore:

```text
O(k + n-k)
```

which simplifies to:

```text
O(n)
```

---

## Space Complexity

The algorithm uses only a few variables:

```text
right
vowelCount
answer
```

and does not create another array or substring.

Therefore:

```text
O(1)
```

space complexity.

---

# Comparison With Brute Force

### Brute Force

For every substring of length `k`:

```text
Count all k characters
```

Complexity:

```text
O(n × k)
```

---

### Sliding Window

First window:

```text
Count k characters
```

Every next window:

```text
Add one
Remove one
```

Complexity:

```text
O(n)
```

The improvement comes from **reusing the information from the previous window**.

---

# Final Takeaway

The complete strategy is:

```text
Create the first window of size k
          ↓
Count the vowels
          ↓
Store the count as answer
          ↓
Move the window one position right
          ↓
Check the new character
          ↓
If vowel → increment count
          ↓
Check the outgoing character at right - k
          ↓
If vowel → decrement count
          ↓
Update maximum
          ↓
Continue until the string ends
```

The main pattern to remember is:

> **For fixed-size substring problems, use a sliding window. Build the first window once, then for every shift add the incoming element and remove the outgoing element instead of recalculating the entire window.**

This gives an **O(n) time** and **O(1) space** solution.
