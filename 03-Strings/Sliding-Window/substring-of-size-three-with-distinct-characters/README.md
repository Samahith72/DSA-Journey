# WIN #46 — Substrings of Size Three with Distinct Characters

## Problem

[LeetCode 1876 — Substrings of Size Three with Distinct Characters](https://leetcode.com/problems/substrings-of-size-three-with-distinct-characters/)

A string is called **good** if it contains no repeated characters.

Given a string `s`, return the number of **good substrings of length exactly 3**.

A substring must be **contiguous**, meaning its characters must appear next to each other.

If the same substring appears multiple times, every occurrence must be counted separately.

### Example 1

```text id="r1m0e3"
Input: s = "xyzzaz"
Output: 1
```

The substrings of size `3` are:

```text id="q6g1v8"
"xyz"
"yzz"
"zza"
"zaz"
```

Checking them:

```text id="5f6zj4"
"xyz" → all characters different ✓
"yzz" → z repeats ✗
"zza" → z repeats ✗
"zaz" → z repeats ✗
```

Therefore:

```text id="1z5qkj"
Output = 1
```

### Example 2

```text id="v0yl7z"
Input: s = "aababcabc"
Output: 4
```

The good substrings are:

```text id="c1e7f5"
"abc"
"bca"
"cab"
"abc"
```

Every occurrence is counted, so the answer is:

```text id="m8qj9v"
4
```

---

## My Java Solution

```java id="w8i0gz"
class Solution {
    public int countGoodSubstrings(String s) {
        int count  = 0;

        for(int i = 0 ;i <= s.length()-3;i++){
            char a = s.charAt(i);
            char b = s.charAt(i+1);
            char c = s.charAt(i+2);

            if(a != b && b != c && a != c){
                count++;
            }
        }

        return count;
    }
}
```

---

## My Thought Process

The problem specifically asks about:

```text
substrings of length 3
```

This immediately suggests looking at every **fixed-size window of 3 characters**.

For example:

```text
s = "xyzzaz"
```

We can visualize the windows as:

```text
[ x y z ] z a z
  x [ y z z ] a z
  x y [ z z a ] z
  x y z [ z a z ]
```

Each window contains exactly 3 characters.

For each window, I simply check whether:

```text
a != b
b != c
a != c
```

If all three conditions are true, the substring is good.

---

# Step 1 — Initialize the Count

```java id="i3s4rh"
int count = 0;
```

This variable stores the number of good substrings found so far.

Initially:

```text
count = 0
```

---

# Step 2 — Iterate Through Every Size-3 Window

```java id="7z2qmy"
for(int i = 0; i <= s.length()-3; i++){
```

Why `s.length() - 3`?

Because each window needs exactly 3 characters:

```text
i
i+1
i+2
```

So `i + 2` must remain a valid index.

For:

```text
s = "xyzzaz"
```

the length is `6`.

The possible starting positions are:

```text
0
1
2
3
```

which gives exactly:

```text
6 - 3 + 1 = 4
```

substrings of length 3.

---

# Step 3 — Get the Three Characters

Inside every window:

```java id="up2t3j"
char a = s.charAt(i);
char b = s.charAt(i+1);
char c = s.charAt(i+2);
```

So for:

```text
s = "xyzzaz"
i = 0
```

we get:

```text
a = 'x'
b = 'y'
c = 'z'
```

The current substring is:

```text
"xyz"
```

---

# Step 4 — Check Whether All Three Characters Are Different

The condition is:

```java id="j28t2e"
if(a != b && b != c && a != c){
    count++;
}
```

We need all three comparisons to be true.

For:

```text
a = 'x'
b = 'y'
c = 'z'
```

we have:

```text
x != y ✓
y != z ✓
x != z ✓
```

Therefore, the substring is good.

So:

```text
count++
```

---

# Step 5 — Move the Window

After checking the current substring, the loop increments `i`.

The window moves one position to the right.

For:

```text
"xyzzaz"
```

the windows are:

```text
"xyz"
"yzz"
"zza"
"zaz"
```

This is the fixed-size sliding window pattern.

---

## Complete Walkthrough

Consider:

```text
s = "xyzzaz"
```

### Window 1

```text
i = 0
```

Characters:

```text
x y z
```

Check:

```text
x != y ✓
y != z ✓
x != z ✓
```

Good substring.

```text
count = 1
```

---

### Window 2

```text
i = 1
```

Characters:

```text
y z z
```

Check:

```text
y != z ✓
z != z ✗
```

Not good.

```text
count = 1
```

---

### Window 3

```text
i = 2
```

Characters:

```text
z z a
```

Check:

```text
z != z ✗
```

Not good.

```text
count = 1
```

---

### Window 4

```text
i = 3
```

Characters:

```text
z a z
```

Check:

```text
z != a ✓
a != z ✓
z != z ✗
```

Not good.

Final:

```text
count = 1
```

Therefore:

```text
Output = 1
```

---

## Pattern Recognition

### Pattern: Fixed-Size Sliding Window

The biggest clue in this problem is:

```text
Substring length = exactly 3
```

Whenever a problem asks you to inspect:

```text
contiguous subarrays/substrings
+
fixed size k
```

think:

> **Fixed-Size Sliding Window**

Here, the window size is simply:

```text
k = 3
```

The general structure is:

```text
[ a b c ] d e
  ↓
a [ b c d ] e
  ↓
a b [ c d e ]
```

In this particular problem, because the window size is only `3`, we can directly access the three characters.

---

## Why We Don't Need a HashSet

A `HashSet` could be used to check whether the three characters are distinct.

For example:

```text
Set = {a,b,c}
```

But it is unnecessary here.

There are only three characters.

We can simply check:

```java
a != b
b != c
a != c
```

This is simpler and uses constant space.

This is an important lesson:

> **Don't automatically use a data structure when a few direct comparisons are enough.**

---

## Why All Three Comparisons Are Needed

Consider:

```text
a = 'a'
b = 'b'
c = 'a'
```

Checking only:

```text
a != b
```

would be true.

Checking:

```text
b != c
```

would also be true.

But:

```text
a != c
```

is false.

So:

```text
"aba"
```

is not a good substring because `a` appears twice.

That's why the solution checks all three pairs:

```text
a != b
b != c
a != c
```

---

## Another Walkthrough

Consider:

```text
s = "aababcabc"
```

The size-3 windows are:

```text
"aab"
"aba"
"bab"
"abc"
"bca"
"cab"
"abc"
```

Now check each one:

| Substring | Good? |
| --------- | ----- |
| `aab`     | ❌     |
| `aba`     | ❌     |
| `bab`     | ❌     |
| `abc`     | ✅     |
| `bca`     | ✅     |
| `cab`     | ✅     |
| `abc`     | ✅     |

Therefore:

```text
count = 4
```

Notice that `"abc"` appears twice.

Both occurrences are counted.

---

## Edge Cases

### 1. String Length Exactly 3

```text
s = "abc"
```

There is exactly one substring:

```text
"abc"
```

All characters are different.

Therefore:

```text
Output = 1
```

---

### 2. Three Equal Characters

```text
s = "aaa"
```

The only substring is:

```text
"aaa"
```

Characters repeat.

Therefore:

```text
Output = 0
```

---

### 3. Two Characters Are Equal

```text
s = "aab"
```

```text
a == a
```

So it is not good.

```text
Output = 0
```

---

### 4. Every Window Is Good

```text
s = "abcdef"
```

Windows:

```text
"abc"
"bcd"
"cde"
"def"
```

Every window contains three distinct characters.

Therefore:

```text
Output = 4
```

---

### 5. Repeated Good Substrings

```text
s = "abcabc"
```

Windows:

```text
"abc" ✓
"bca" ✓
"cab" ✓
"abc" ✓
```

All four are counted.

Therefore:

```text
Output = 4
```

---

## Complexity

### Time Complexity

There are:

```text
n - 3 + 1
```

possible substrings of length `3`.

For each substring, we perform a constant number of comparisons.

Therefore:

```text
O(n)
```

time complexity.

---

### Space Complexity

Only three character variables and one counter are used:

```text
a
b
c
count
```

No additional data structure is created.

Therefore:

```text
O(1)
```

auxiliary space.

---

## Key Learning

### 1. Fixed-Length Substrings Suggest Sliding Window

Whenever you see:

```text
substring of size k
```

look for a fixed-size sliding window.

---

### 2. Continuous Means We Cannot Skip Characters

For:

```text
"xyzzaz"
```

we inspect:

```text
xyz
yzz
zza
zaz
```

We cannot choose arbitrary characters.

---

### 3. Small Windows Can Be Checked Directly

Because the window size is exactly `3`, we don't need complicated data structures.

We can simply check:

```text
a != b
b != c
a != c
```

---

### 4. Count Occurrences, Not Unique Substrings

If:

```text
"abc"
```

appears multiple times, every occurrence counts.

For:

```text
"abcabc"
```

both occurrences of `"abc"` contribute to the answer.

---

## Final Takeaway

The core idea is:

```text
Create a window of 3 characters
          ↓
Check whether all 3 are distinct
          ↓
If yes → count++
          ↓
Move window one position
          ↓
Repeat
```

The pattern to remember is:

> **When a problem asks about every contiguous substring/subarray of a fixed size, think Fixed-Size Sliding Window.**

For this particular problem, the window size is `3`, so direct character comparisons are enough.

This solution achieves **O(n) time** and **O(1) auxiliary space**.
