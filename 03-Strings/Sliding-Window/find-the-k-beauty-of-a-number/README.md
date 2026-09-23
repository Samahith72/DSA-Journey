# WIN #49 — Find the K-Beauty of a Number

## Problem

[LeetCode 2269 — Find the K-Beauty of a Number](https://leetcode.com/problems/find-the-k-beauty-of-a-number/)

The **k-beauty** of an integer `num` is the number of substrings of `num` that satisfy both conditions:

1. The substring has exactly `k` digits.
2. The number represented by the substring is a divisor of `num`.

In other words, for every substring of length `k`:

```text id="x6m4q8"
substring != 0
AND
num % substring == 0
```

The substring must be contiguous.

### Important

Leading zeros are allowed.

For example:

```text id="3v8n1p"
"04"
```

represents the number:

```text id="k2m7c5"
4
```

Also:

```text id="p4x9q1"
"00"
```

represents `0`, but `0` cannot be used as a divisor.

---

## Example 1

```text id="y5q2m8"
Input: num = 240
       k = 2

Output: 2
```

The substrings of length `2` are:

```text id="c7v1n4"
"24"
"40"
```

Check:

```text id="f8m3x6"
240 % 24 = 0 ✓
240 % 40 = 0 ✓
```

Therefore:

```text id="r2q9k5"
k-beauty = 2
```

---

## Example 2

```text id="n6v3x8"
Input: num = 430043
       k = 2

Output: 2
```

Substrings:

```text id="q4m8c1"
"43"
"30"
"00"
"04"
"43"
```

Check:

```text id="z7p2x5"
43 → divisor ✓
30 → not a divisor ✗
00 → 0, cannot divide ✗
04 → 4, not a divisor ✗
43 → divisor ✓
```

Therefore:

```text id="j3n9v6"
Output = 2
```

Notice that `"43"` appears twice, and **both occurrences are counted**.

---

## My Java Solution

```java id="v6q2m8"
class Solution {
    public int divisorSubstrings(int num, int k) {
        int count = 0;
        String s = String.valueOf(num);

        for(int i=0;i <= s.length()-k;i++){
            int value = Integer.parseInt(s.substring(i,i+k));

            if(value != 0 && num % value == 0){
                count++;
            }
        }

        return count;

    }
}
```

---

## My Thought Process

The problem describes substrings of a **number**, but substrings are a string concept.

So the first step is to convert the number into a string:

```java id="1p7r3c"
String s = String.valueOf(num);
```

Then I need to inspect every substring of length `k`.

For example:

```text id="w4n8x2"
num = 240
k = 2
```

The string is:

```text id="z6m1q9"
"240"
```

The size-2 windows are:

```text id="p3v7c5"
"24"
"40"
```

For each window:

1. Convert the substring into an integer.
2. Make sure it is not zero.
3. Check whether `num % value == 0`.
4. If it is a divisor, increment the answer.

---

# Step 1 — Initialize the Count

```java id="k8x3m6"
int count = 0;
```

This stores the number of valid substrings found.

Initially:

```text id="r5v1q7"
count = 0
```

---

# Step 2 — Convert the Number Into a String

```java id="n2m7c4"
String s = String.valueOf(num);
```

For:

```text id="x6q1p8"
num = 240
```

we get:

```text id="j4v9m2"
s = "240"
```

This allows us to use substring operations.

---

# Step 3 — Iterate Over Every Size-K Substring

```java id="c7n3x5"
for(int i = 0; i <= s.length()-k; i++){
```

The window always has exactly `k` characters.

For:

```text id="q8m2v6"
s = "240"
k = 2
```

the valid starting positions are:

```text id="p1x7c9"
i = 0
i = 1
```

because:

```text id="w3n6m8"
s.length() - k
= 3 - 2
= 1
```

---

# Step 4 — Extract the Substring

```java id="m4q8v1"
s.substring(i, i+k)
```

For:

```text id="6c2x9p"
i = 0
k = 2
```

we get:

```text id="a7m3q5"
"24"
```

For:

```text id="n8v1c6"
i = 1
```

we get:

```text id="x5p2m9"
"40"
```

---

# Step 5 — Convert the Substring to an Integer

```java id="r7c3m5"
int value = Integer.parseInt(s.substring(i,i+k));
```

For:

```text id="q4n8x1"
"24"
```

we get:

```text id="y6m2v9"
value = 24
```

For a substring like:

```text id="f8c3p7"
"04"
```

`Integer.parseInt()` converts it to:

```text id="m1q6x4"
4
```

This correctly handles the problem's rule that leading zeros are allowed.

---

# Step 6 — Make Sure the Value Is Not Zero

```java id="x3v8n2"
if(value != 0 && ...)
```

This check is essential.

A substring such as:

```text id="p7m1c5"
"00"
```

becomes:

```text id="v4q9x6"
0
```

But division by zero is invalid.

So we must ensure:

```text id="n2m8c3"
value != 0
```

before performing the modulo operation.

---

# Step 7 — Check Whether It Divides `num`

The second condition is:

```java id="k6v3p8"
num % value == 0
```

If the remainder is zero, `value` is a divisor of `num`.

For:

```text id="a1x5m7"
num = 240
value = 24
```

we get:

```text id="q8n2c4"
240 % 24 = 0
```

So `24` is a valid divisor.

Therefore:

```java id="z5m7v1"
count++;
```

---

# Step 8 — Return the Count

After checking every substring:

```java id="c4x8n2"
return count;
```

This gives the k-beauty of the number.

---

## Complete Walkthrough

Consider:

```text id="m7q2v9"
num = 240
k = 2
```

Convert:

```text id="p3x8c5"
s = "240"
```

Initially:

```text id="n6m1q4"
count = 0
```

---

### Window 1

```text id="y8c3v6"
i = 0
```

Substring:

```text id="r5m2x9"
"24"
```

Convert:

```text id="q1v7c4"
value = 24
```

Check:

```text id="k8n3m6"
value != 0 ✓
240 % 24 == 0 ✓
```

So:

```text id="x4p9q2"
count = 1
```

---

### Window 2

```text id="v6m2c8"
i = 1
```

Substring:

```text id="n4x7q1"
"40"
```

Convert:

```text id="p8m3v5"
value = 40
```

Check:

```text id="c2q6n9"
value != 0 ✓
240 % 40 == 0 ✓
```

So:

```text id="m5x1v7"
count = 2
```

---

### Final Answer

```text id="z3q8c4"
count = 2
```

Therefore:

```text id="k6m2v9"
Output = 2
```

---

## Another Walkthrough

Consider:

```text id="q8m3x1"
num = 430043
k = 2
```

String:

```text id="v6c2n9"
"430043"
```

The windows are:

```text id="p5m8q3"
"43"
"30"
"00"
"04"
"43"
```

### `"43"`

```text id="x2n7c5"
430043 % 43 = 0
```

Count:

```text id="m8v1q4"
1
```

### `"30"`

```text id="c6x3n9"
430043 % 30 != 0
```

Count remains:

```text id="p2q7m5"
1
```

### `"00"`

```text id="n4v8c1"
value = 0
```

The condition:

```text id="j6x2m9"
value != 0
```

fails.

No modulo operation is attempted.

### `"04"`

```text id="q3m7v5"
value = 4
```

But:

```text id="c8x1n6"
430043 % 4 != 0
```

No increment.

### `"43"`

Again:

```text id="v2m6q8"
430043 % 43 = 0
```

Count:

```text id="r5x9c3"
2
```

Final answer:

```text id="p7n1m4"
2
```

---

## Pattern Recognition

### Pattern: Fixed-Size Sliding Window

The key phrase in the problem is:

```text id="x6c2m8"
substrings of length k
```

That means we need to inspect every contiguous window of exactly `k` characters.

This is a classic:

> **Fixed-Size Sliding Window**

pattern.

The windows look like:

```text id="q4m8v1"
[ a b ] c d
  ↓
a [ b c ] d
  ↓
a b [ c d ]
```

In this particular implementation, instead of maintaining the window value incrementally, your solution directly extracts each size-`k` substring.

---

## Why This Still Fits the Sliding Window Pattern

Your loop:

```java id="m8c2x6"
for(int i = 0; i <= s.length()-k; i++)
```

moves the starting position one character at a time.

At every position:

```text id="v3q7n1"
i → i + k
```

defines a fixed-size window.

So conceptually:

```text id="p5m9c2"
Fixed window of k characters
          ↓
Convert window to number
          ↓
Check divisor
```

---

## Important Observation: Leading Zeros

The problem explicitly allows leading zeros.

For example:

```text id="q1v8m4"
"04"
```

is a valid substring.

When converted:

```java id="j7c3x9"
Integer.parseInt("04")
```

we get:

```text id="n5m2q6"
4
```

This is exactly what we need.

Similarly:

```text id="c8v1x5"
"00" → 0
```

and we reject it because:

```text id="r4m7n2"
0 is not a divisor
```

---

## Why We Check `value != 0` First

The condition is:

```java id="x6q3m8"
if(value != 0 && num % value == 0)
```

Java evaluates `&&` from left to right and stops if the first condition is false.

So when:

```text id="n2c7v5"
value = 0
```

the second condition:

```text id="q8m1x4"
num % value
```

is never evaluated.

This prevents division/modulo by zero.

---

## Edge Cases

### 1. `k = 1`

```text id="v5m2q8"
num = 240
k = 1
```

Substrings:

```text id="n7x3c1"
"2"
"4"
"0"
```

Check:

```text id="m6q8v2"
240 % 2 = 0 ✓
240 % 4 = 0 ✓
0 → invalid divisor
```

Therefore:

```text id="c4n9x5"
Output = 2
```

---

### 2. Substring Is Zero

```text id="q7m3v8"
num = 100
k = 2
```

Windows:

```text id="x1c6n4"
"10"
"00"
```

`10` divides `100`.

`00` becomes `0`, which cannot be a divisor.

Therefore:

```text id="m8q2v5"
Output = 1
```

---

### 3. `k` Equals the Number of Digits

```text id="n5x8c2"
num = 240
k = 3
```

There is only one substring:

```text id="v3m7q1"
"240"
```

Since:

```text id="p9c4x6"
240 % 240 = 0
```

the answer is:

```text id="m2v8n5"
1
```

---

### 4. No Substring Is a Divisor

Suppose none of the size-`k` substrings divides `num`.

Then:

```text id="q6c1m8"
count
```

never changes.

Final:

```text id="x4v7n2"
0
```

---

### 5. Repeated Valid Substrings

Consider:

```text id="a8m3q6"
num = 430043
k = 2
```

The substring:

```text id="v5x1c9"
"43"
```

appears twice.

Both occurrences are valid divisors.

Therefore, both are counted.

This is important because the problem asks for the number of **substring occurrences**, not the number of unique substring values.

---

## Complexity

Let:

```text id="m7c2x8"
n = number of digits in num
```

There are:

```text id="q4v9n1"
n - k + 1
```

possible substrings.

For each substring, `substring()` and `parseInt()` process at most `k` characters.

Therefore, with the direct substring approach used in your solution, the worst-case time complexity is:

```text id="x8m3c5"
O(n × k)
```

Since `num <= 10^9`, the number of digits is very small in this problem, but the algorithmic complexity of the implementation is still best described as **O(nk)**.

---

### Space Complexity

The substring created for each iteration uses:

```text id="v2q6m8"
O(k)
```

temporary space.

Therefore, the auxiliary space complexity is:

```text id="c5n1x7"
O(k)
```

---

## Key Learning

### 1. Fixed-Length Substrings Are a Sliding Window Signal

Whenever you see:

```text id="p7m3x9"
substring of length k
```

think:

```text id="c2v8n5"
Fixed-Size Sliding Window
```

---

### 2. Convert String Windows Back Into Numbers When Needed

The number is initially converted to a string because the problem talks about **substrings**.

Then each substring is converted back:

```java id="x6q1m4"
Integer.parseInt(...)
```

so that we can perform:

```java id="n8v3c7"
num % value
```

---

### 3. Always Handle Zero Before Modulo

Remember:

```text id="q5m2x8"
0 cannot be a divisor
```

So:

```java id="v7c3n1"
value != 0
```

must be checked before:

```java id="m4x9q6"
num % value
```

---

### 4. Count Occurrences, Not Unique Values

If the same valid substring appears multiple times, every occurrence contributes to the k-beauty.

So we simply increment:

```java id="x2n8c5"
count++;
```

for every valid window.

---

## Final Takeaway

The core idea is:

```text id="q6v3m8"
Convert num to a string
        ↓
Look at every substring of length k
        ↓
Convert substring to an integer
        ↓
Ignore 0
        ↓
Check if num % value == 0
        ↓
If yes → count++
```

The pattern to remember is:

> **When a problem asks you to inspect every contiguous substring of a fixed length, think fixed-size sliding window. If each window must then satisfy a numerical condition, process that window after extracting it.**

Your solution runs in **O(nk) time** and **O(k) auxiliary space** because each substring is explicitly created and parsed.
