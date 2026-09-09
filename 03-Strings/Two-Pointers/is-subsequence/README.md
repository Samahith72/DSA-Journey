# WIN #26 — Is Subsequence

## Problem

Given two strings `s` and `t`, return `true` if `s` is a **subsequence** of `t`.

A subsequence is formed by deleting some characters from a string **without changing the relative order** of the remaining characters.

For example:

```text
t = "abcde"
```

`"ace"` is a subsequence because we can remove `b` and `d`:

```text
abcde
↓
ace
```

But `"aec"` is **not** a subsequence because the order of the characters is different.

---

## Example 1

```text
Input:
s = "abc"
t = "ahbgdc"

Output:
true
```

We can find:

```text
a → h → b → g → d → c
↑       ↑             ↑
a       b             c
```

The characters `a`, `b`, and `c` appear in the correct order.

---

## Example 2

```text
Input:
s = "axc"
t = "ahbgdc"

Output:
false
```

We can find `a`, but there is no `x` after it.

Therefore, `s` is not a subsequence of `t`.

---

## My Java Solution

```java
class Solution {
    public boolean isSubsequence(String s, String t) {

        int sPointer =0;
        int tPointer = 0;

        while(sPointer < s.length() && tPointer < t.length() ){
            if(s.charAt(sPointer) == t.charAt(tPointer)){
                sPointer++;
            }

            tPointer++;
        }

        return sPointer == s.length()? true: false;
    }
}
```

---

## My Thought Process

The important thing about a subsequence is:

> The characters don't need to be next to each other, but their **relative order must remain the same**.

So I don't need to create a new string.

Instead, I can scan through `t` and try to find the characters of `s` one by one.

I use two pointers:

```text
sPointer → points to the character I currently need from s

tPointer → scans through t
```

The idea is:

```text
Find s[sPointer] inside t
          ↓
If found → move sPointer
          ↓
Always move tPointer
          ↓
Continue
```

---

## Understanding the Two Pointers

Consider:

```text
s = "abc"
t = "ahbgdc"
```

Initially:

```text
sPointer = 0
tPointer = 0
```

So:

```text
s[sPointer] = 'a'
t[tPointer] = 'a'
```

We are currently looking for:

```text
'a'
```

inside `t`.

---

## Step 1: Find `a`

We compare:

```text
s[0] = 'a'
t[0] = 'a'
```

They match.

Therefore:

```java
sPointer++;
```

Now:

```text
sPointer = 1
```

We are now looking for:

```text
'b'
```

Then:

```java
tPointer++;
```

so:

```text
tPointer = 1
```

---

## Step 2: Skip Characters That Don't Match

Now:

```text
s[sPointer] = 'b'
t[tPointer] = 'h'
```

They don't match.

So we **do not move `sPointer`**.

We still need to find `b`.

But we move `tPointer`:

```java
tPointer++;
```

Now we check:

```text
t = "ahbgdc"
      ↑
```

Eventually we reach:

```text
'b'
```

and the characters match.

So:

```text
sPointer++
```

Now we are looking for:

```text
'c'
```

---

## Complete Walkthrough

For:

```text
s = "abc"
t = "ahbgdc"
```

the process looks like:

```text
s: a b c
   ↑

t: a h b g d c
   ↑
```

### Compare `a`

```text
a == a
```

Match → move both.

```text
s: a b c
     ↑

t: a h b g d c
     ↑
```

### Compare `b` with `h`

```text
b != h
```

Only `tPointer` moves.

```text
s: a b c
     ↑

t: a h b g d c
       ↑
```

### Compare `b` with `b`

```text
b == b
```

Match → move both.

```text
s: a b c
       ↑

t: a h b g d c
         ↑
```

### Compare `c` with `g`

```text
c != g
```

Move `tPointer`.

Then:

```text
c != d
```

Move again.

Finally:

```text
c == c
```

Match.

Now:

```text
sPointer == s.length()
```

Therefore:

```text
true
```

---

## Why Does `tPointer` Always Move?

This is a very important part of the solution.

Suppose:

```text
s = "abc"
t = "ahbgdc"
```

When we compare:

```text
b vs h
```

they don't match.

Should we go back and reconsider `a`?

No.

We already found `a`.

Now we are only looking for `b`.

So `h` can simply be skipped.

That's why:

```java
tPointer++;
```

happens on **every iteration**.

But `sPointer` only moves when we find the character we're looking for.

---

## Why Does `sPointer` Only Move on a Match?

Because `sPointer` represents:

> The next character from `s` that we need to find.

For:

```text
s = "abc"
```

we must find:

```text
a → b → c
```

in exactly that order.

If we haven't found `a`, we cannot start looking for `b`.

Therefore:

```java
if(s.charAt(sPointer) == t.charAt(tPointer)){
    sPointer++;
}
```

Only a successful match advances `sPointer`.

---

## Example Where the Answer Is `false`

Consider:

```text
s = "axc"
t = "ahbgdc"
```

Initially, we need:

```text
a
```

We find it.

Now:

```text
sPointer → x
```

We scan through `t`:

```text
a h b g d c
  ↑
```

None of the remaining characters is `x`.

Eventually:

```text
tPointer == t.length()
```

but:

```text
sPointer != s.length()
```

So the final condition:

```java
return sPointer == s.length();
```

returns:

```text
false
```

---

## Why Does the Final Check Work?

At the end, there are two possibilities.

### Case 1 — We Matched Everything

If:

```text
sPointer == s.length()
```

then every character in `s` has been found in the correct order.

Therefore:

```text
true
```

### Case 2 — Some Characters Are Still Missing

If:

```text
sPointer < s.length()
```

then at least one character from `s` was never found.

Therefore:

```text
false
```

So this:

```java
return sPointer == s.length();
```

is enough to determine the answer.

---

## Pattern Recognition

### Pattern: Two Pointers

This is a classic **Two Pointers** problem.

The two pointers move differently:

```text
sPointer
    ↓
Moves only when a required character is found

tPointer
    ↓
Moves through every character of t
```

The general pattern is:

```text
Two sequences
     ↓
Scan the larger sequence
     ↓
Look for the next required element
     ↓
Match → advance both
No match → advance only larger sequence
```

This pattern is especially useful for:

* Subsequence problems
* Comparing sequences
* Merging sorted arrays
* Searching for a pattern while preserving order

---

## Why Does This Work?

The definition of a subsequence only requires that the characters appear in the correct **relative order**.

They don't have to be adjacent.

For:

```text
s = "ace"
t = "abcde"
```

we can find:

```text
a
 ↓
b
 ↓
c
 ↓
d
 ↓
e
```

and select:

```text
a → c → e
```

The two-pointer approach naturally preserves this order because `tPointer` only moves forward.

Once we match a character, we never go backward.

Therefore, the relative ordering is automatically maintained.

---

## Complexity

### Time Complexity

`tPointer` moves through `t` at most once.

Therefore:

```text
O(n)
```

where `n` is the length of `t`.

More precisely:

```text
O(|s| + |t|)
```

because both pointers can move through their respective strings.

### Space Complexity

No additional data structure or string is created.

Only two integer pointers are used:

```text
sPointer
tPointer
```

Therefore:

```text
O(1)
```

extra space.

---

## Follow-Up

The problem asks:

> What if there are lots of incoming strings `s1, s2, ..., sk` and we repeatedly need to check whether each one is a subsequence of the same `t`?

The simple two-pointer solution would scan `t` again for every new `s`.

If there are a huge number of queries, that repeated scanning becomes expensive.

A better approach is to **preprocess `t` once**.

Since `t` contains only lowercase English letters, we can store the positions where each character occurs.

For example:

```text
t = "ahbgdc"
```

We could store:

```text
a → [0]
b → [2]
c → [5]
d → [4]
g → [3]
h → [1]
```

Then, for each incoming `s`, we can quickly find the next occurrence of each required character using binary search.

The idea becomes:

```text
Preprocess t
     ↓
Store positions of each character
     ↓
Receive s1
     ↓
Find character positions
     ↓
Receive s2
     ↓
Reuse the same preprocessed data
```

This is useful when:

```text
t = fixed
s = many different queries
```

The main idea is to **pay a preprocessing cost once instead of repeatedly scanning `t`**.

---

## Key Learning

* A subsequence preserves **relative order**, not necessarily adjacency.
* Two pointers are ideal for scanning a sequence while maintaining order.
* `tPointer` scans through the larger string.
* `sPointer` only moves when the required character is found.
* Characters in `t` that don't match can simply be skipped.
* We never need to modify or create either string.
* The solution achieves **O(n) time and O(1) extra space**.
* When the same `t` is searched against many different `s` strings, preprocessing `t` can make repeated queries much faster.

---

## Final Takeaway

The entire approach can be remembered as:

```text
s = "abc"
t = "ahbgdc"

        Scan t →
        
a → match → move sPointer
h → skip
b → match → move sPointer
g → skip
d → skip
c → match → move sPointer

sPointer reached the end
        ↓
      true
```

The key idea is:

> **Scan the larger string and advance the subsequence pointer only when the next required character is found.**
