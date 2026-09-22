# WIN #47 — Find the Difference

## Problem

[LeetCode 389 — Find the Difference](https://leetcode.com/problems/find-the-difference/)

You are given two strings `s` and `t`.

String `t` is created by:

1. Taking all characters from `s`.
2. Randomly shuffling them.
3. Adding exactly one extra character.

Return the character that was added to `t`.

The order of the characters does not matter.

### Example 1

```text id="4z4v4v"
Input: s = "abcd"
       t = "abcde"

Output: "e"
```

The characters of `s` are:

```text id="l7e2as"
a b c d
```

The characters of `t` are:

```text id="0kq6om"
a b c d e
```

The extra character is:

```text id="s2ck4q"
e
```

---

### Example 2

```text id="j7y5qv"
Input: s = ""
       t = "y"

Output: "y"
```

Since `s` is empty, the only character in `t` must be the added character.

---

## My Java Solution

```java id="x3p3w8"
class Solution {
    public char findTheDifference(String s, String t) {
        char result =0;

        for(char c: s.toCharArray()){
            result ^= c;
        }

        for(char c : t.toCharArray()){
            result ^= c;
        }

        return result;
    }
}
```

---

## My Thought Process

At first glance, this problem might look like a frequency-counting problem.

One possible approach would be:

```text
Count every character in s
Count every character in t
Compare the frequencies
```

But we can solve it more efficiently using a special property of **XOR**.

The important XOR properties are:

```text id="v4w7lh"
x ^ x = 0
x ^ 0 = x
```

and XOR is **commutative**:

```text id="4omxyd"
a ^ b = b ^ a
```

and **associative**:

```text id="2j5b7r"
(a ^ b) ^ c = a ^ (b ^ c)
```

This means the order of the characters does not matter.

Since every character from `s` appears exactly once in `t`, all matching characters will cancel each other out.

The only character left is the extra character.

---

# Step 1 — Create a Result Variable

```java id="2rj3kq"
char result = 0;
```

We use `result` to accumulate the XOR of all characters.

Initially:

```text id="wv7m9y"
result = 0
```

---

# Step 2 — XOR All Characters From `s`

```java id="d4ahqp"
for(char c: s.toCharArray()){
    result ^= c;
}
```

Suppose:

```text id="a1ps0d"
s = "abcd"
```

Then:

```text id="1z5g1e"
result = 0 ^ 'a' ^ 'b' ^ 'c' ^ 'd'
```

So the result contains the XOR of all characters from `s`.

---

# Step 3 — XOR All Characters From `t`

Now:

```java id="z1w4jk"
for(char c : t.toCharArray()){
    result ^= c;
}
```

Suppose:

```text id="7kz5gz"
t = "abcde"
```

Now:

```text id="u2p1vx"
result =
0
^ 'a'
^ 'b'
^ 'c'
^ 'd'
^ 'a'
^ 'b'
^ 'c'
^ 'd'
^ 'e'
```

Because XOR can be reordered, we can group matching characters:

```text id="8r1c9a"
('a' ^ 'a')
^ ('b' ^ 'b')
^ ('c' ^ 'c')
^ ('d' ^ 'd')
^ 'e'
```

Each matching pair becomes zero:

```text id="m9q4xk"
0 ^ 0 ^ 0 ^ 0 ^ 'e'
```

So:

```text id="6v2f5r"
result = 'e'
```

---

# Step 4 — Return the Remaining Character

Finally:

```java id="7q9q0c"
return result;
```

The only character that was not canceled is the extra character.

Therefore:

```text id="fd4x6g"
return 'e'
```

---

## Complete Walkthrough

Consider:

```text id="1h9s6p"
s = "abcd"
t = "abcde"
```

### Start

```text id="c6l9jg"
result = 0
```

---

### Process `s`

```text id="n2r6sa"
result = 0 ^ a ^ b ^ c ^ d
```

---

### Process `t`

```text id="s4y2cw"
result = 0 ^ a ^ b ^ c ^ d
              ^ a ^ b ^ c ^ d ^ e
```

Rearrange the XOR operations:

```text id="g6eqo3"
(a ^ a)
^ (b ^ b)
^ (c ^ c)
^ (d ^ d)
^ e
```

Each pair cancels:

```text id="3h6p1k"
0 ^ 0 ^ 0 ^ 0 ^ e
```

Therefore:

```text id="l3b6x4"
result = e
```

Return:

```text id="z9q1w7"
'e'
```

---

## Why XOR Works

This problem is a perfect example of using the XOR cancellation property.

### Property 1 — A Value XORed With Itself Becomes Zero

```text id="u4c8tg"
x ^ x = 0
```

For example:

```text id="9xq1lz"
'a' ^ 'a' = 0
```

---

### Property 2 — XOR With Zero Keeps the Value

```text id="0m9m9p"
x ^ 0 = x
```

Therefore:

```text id="1f9x2q"
'e' ^ 0 = 'e'
```

---

### Property 3 — Order Doesn't Matter

XOR is commutative:

```text id="y4j1vb"
a ^ b = b ^ a
```

So even though `t` is randomly shuffled, we can still pair matching characters.

For example:

```text id="z4z4i8"
s = "abc"
t = "cbad"
```

The order is different, but:

```text id="b8x2z1"
a ^ b ^ c ^ c ^ b ^ a ^ d
```

still becomes:

```text id="q8d0wb"
d
```

---

## Pattern Recognition

### Pattern: XOR Cancellation

The important clue is:

```text id="x3p0j7"
Two collections contain the same elements
+
One collection contains exactly one extra element
```

This is a strong signal for **XOR cancellation**.

The general idea is:

```text id="f6q2y8"
All elements from first collection
        +
All elements from second collection
        ↓
XOR everything
        ↓
Matching elements cancel
        ↓
Only extra element remains
```

### General Pattern

Whenever you see:

* One extra number
* One missing number
* Duplicates that should cancel
* Two arrays/strings containing mostly identical elements
* Order doesn't matter

consider whether XOR can solve the problem.

---

## Why We Don't Need Sorting

A sorting approach could be used:

```text id="k7q6m1"
Sort s
Sort t
Compare characters
```

But sorting would introduce:

```text id="n4j8v2"
O(n log n)
```

time complexity.

The XOR approach does not care about the order.

For example:

```text id="9o6y5t"
s = "abcd"
t = "dcbae"
```

The characters still cancel correctly.

So sorting is unnecessary.

---

## Why We Don't Need a HashMap

Another possible approach would be frequency counting:

```text id="m6v1b3"
a → frequency
b → frequency
c → frequency
...
```

Then compare the frequencies of `s` and `t`.

That works, but it requires additional storage.

The XOR approach only needs:

```java id="r0q8z7"
char result
```

So it uses constant auxiliary space.

---

## Edge Cases

### 1. Empty `s`

```text id="x4q1zz"
s = ""
t = "y"
```

There is no character to process in `s`.

Then:

```text id="n6j8p4"
result = 0 ^ 'y'
```

Therefore:

```text id="v1g4s2"
result = 'y'
```

---

### 2. Extra Character at the Beginning

```text id="y6k5v8"
s = "abcd"
t = "eabcd"
```

The extra character is `e`.

The order does not matter because XOR can reorder the operations.

Result:

```text id="k8q2x0"
'e'
```

---

### 3. Extra Character in the Middle

```text id="n7h1z6"
s = "abcd"
t = "abecd"
```

The extra character is:

```text id="f8x5y4"
e
```

The XOR approach still finds it.

---

### 4. Extra Character at the End

```text id="3x8q6k"
s = "abcd"
t = "abcde"
```

Result:

```text id="q5w9y2"
e
```

---

### 5. Repeated Characters

The method also works when characters themselves repeat.

For example:

```text id="h2w8p5"
s = "aabb"
t = "aabbc"
```

The XOR expression becomes:

```text id="1v6x4m"
a ^ a ^ b ^ b
^ a ^ a ^ b ^ b ^ c
```

The matching values cancel, leaving:

```text id="j5q7z3"
c
```

---

## Complexity

### Time Complexity

We traverse every character in both strings exactly once.

If:

```text id="q2k7p9"
n = s.length()
```

then `t.length() = n + 1`.

Therefore:

```text id="w6j3x8"
O(n)
```

time complexity.

---

### Space Complexity

Only one variable is used:

```java id="z0r4q2"
char result
```

No HashMap, HashSet, array, or other data structure is created.

Therefore:

```text id="v8k1c6"
O(1)
```

auxiliary space.

---

## Key Learning

### 1. XOR Can Cancel Matching Values

The most important property to remember is:

```text id="e3x8q1"
x ^ x = 0
```

This lets us eliminate matching elements.

---

### 2. XOR Is Order Independent

Because XOR is commutative and associative:

```text id="p7q5z2"
a ^ b ^ c
```

is equivalent to:

```text id="h8m2w4"
c ^ a ^ b
```

This makes XOR useful when the input is shuffled.

---

### 3. Look for Cancellation Problems

When every element should appear twice except one, think:

```text id="t6y4k8"
XOR
```

The unmatched element survives.

---

### 4. Character Values Can Be XORed

In Java, `char` values are integer-like numeric values internally, so the XOR operator can be applied directly:

```java id="q9r2v5"
result ^= c;
```

The final XOR value can be returned as a `char`.

---

## Final Takeaway

The core idea is:

```text id="c6p3v8"
XOR all characters in s
          ↓
XOR all characters in t
          ↓
Matching characters cancel
          ↓
Only the added character remains
```

The key pattern to remember is:

> **When two collections contain the same elements except for one extra element, XOR can cancel all matching elements and leave the unique difference.**

This solution achieves **O(n) time** and **O(1) auxiliary space**.
