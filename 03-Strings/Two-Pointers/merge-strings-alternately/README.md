# WIN #36 — Merge Strings Alternately

## Problem

Given two strings `word1` and `word2`, merge them by taking characters alternately, starting with `word1`.

If one string is longer than the other, append the remaining characters of that string to the end.

For example:

```text
Input:
word1 = "abc"
word2 = "pqr"

Output:
"apbqcr"
```

The merging happens like this:

```text
word1:  a   b   c
word2:    p   q   r
        ↓ ↓ ↓ ↓ ↓ ↓
merged: a p b q c r
```

If one string is longer:

```text
Input:
word1 = "ab"
word2 = "pqrs"

Output:
"apbqrs"
```

The remaining `"rs"` is appended after the alternating portion.

---

## My Java Solution

```java
class Solution {
    public String mergeAlternately(String word1, String word2) {

        char[] c1 = word1.toCharArray();
        char[] c2 = word2.toCharArray();

        StringBuilder sb = new StringBuilder();

        int ptr1 = 0;
        int ptr2 = 0;

        while(ptr1 <= word1.length()-1 && ptr2 <= word2.length()-1){

            if(ptr1 <= word1.length()-1){
                sb.append(c1[ptr1]);
                ptr1++;
            }

            if(ptr2 <= word2.length()-1){
                sb.append(c2[ptr2]);
                ptr2++;
            }
        }

        if(ptr1 != word1.length()){
            while(ptr1 <= word1.length() -1){
                sb.append(c1[ptr1]);
                ptr1++;
            }
        }

        if(ptr2 != word2.length()){
            while(ptr2 <= word2.length() -1){
                sb.append(c2[ptr2]);
                ptr2++;
            }
        }

        return sb.toString();
    }
}
```

---

## My Thought Process

The main challenge is that I have **two different strings** that need to be processed simultaneously.

For example:

```text
word1 = "abc"
word2 = "pqr"
```

I need:

```text
a → from word1
p → from word2
b → from word1
q → from word2
c → from word1
r → from word2
```

So I need to know:

```text
Where am I in word1?
Where am I in word2?
```

This naturally leads to using **two pointers**.

```text
ptr1 → current position in word1
ptr2 → current position in word2
```

Both start at `0`.

---

# Step 1 — Convert Strings to Character Arrays

I first convert both strings:

```java
char[] c1 = word1.toCharArray();
char[] c2 = word2.toCharArray();
```

This gives direct access to individual characters:

```text
c1[0]
c1[1]
c1[2]
```

and:

```text
c2[0]
c2[1]
c2[2]
```

---

# Step 2 — Use Two Pointers

I initialize:

```java
int ptr1 = 0;
int ptr2 = 0;
```

So initially:

```text
word1 = abc
         ↑
       ptr1

word2 = pqr
         ↑
       ptr2
```

`ptr1` tracks `word1`.

`ptr2` tracks `word2`.

---

# Step 3 — Add Characters Alternately

The main loop is:

```java
while(ptr1 <= word1.length()-1 && 
      ptr2 <= word2.length()-1)
```

This continues while **both strings still have characters remaining**.

Inside the loop:

```java
sb.append(c1[ptr1]);
ptr1++;
```

First, take one character from `word1`.

Then:

```java
sb.append(c2[ptr2]);
ptr2++;
```

take one character from `word2`.

Therefore, every iteration adds:

```text
word1 character
        ↓
word2 character
        ↓
word1 character
        ↓
word2 character
```

---

# Example Walkthrough

Consider:

```text
word1 = "abc"
word2 = "pqr"
```

Initially:

```text
ptr1 = 0
ptr2 = 0
```

### Iteration 1

Take:

```text
word1[0] = a
word2[0] = p
```

Result:

```text
"ap"
```

Pointers:

```text
ptr1 = 1
ptr2 = 1
```

---

### Iteration 2

Take:

```text
word1[1] = b
word2[1] = q
```

Result:

```text
"apbq"
```

Pointers:

```text
ptr1 = 2
ptr2 = 2
```

---

### Iteration 3

Take:

```text
word1[2] = c
word2[2] = r
```

Result:

```text
"apbqcr"
```

Pointers:

```text
ptr1 = 3
ptr2 = 3
```

Both strings are now exhausted.

Final result:

```text
"apbqcr"
```

---

# What Happens When One String Is Longer?

This is the important edge case.

Consider:

```text
word1 = "ab"
word2 = "pqrs"
```

Initially:

```text
word1: a b
        ↑
       ptr1

word2: p q r s
        ↑
       ptr2
```

First iteration:

```text
a p
```

Second iteration:

```text
a p b q
```

Now:

```text
ptr1 = 2
ptr2 = 2
```

`word1` is exhausted.

The main loop stops because:

```text
ptr1 <= word1.length() - 1
```

is now false.

But `word2` still contains:

```text
r s
```

So I append the remaining characters:

```java
if(ptr2 != word2.length()){
    while(ptr2 <= word2.length() -1){
        sb.append(c2[ptr2]);
        ptr2++;
    }
}
```

The final result becomes:

```text
"apbqrs"
```

---

# What If `word1` Is Longer?

Consider:

```text
word1 = "abcd"
word2 = "pq"
```

The alternating part becomes:

```text
a p b q
```

At this point:

```text
ptr1 = 2
ptr2 = 2
```

`word2` is exhausted.

The remaining part of `word1` is:

```text
c d
```

So I append:

```text
"cd"
```

Final result:

```text
"apbqcd"
```

---

# Why Use `StringBuilder`?

The result is built character by character.

Instead of repeatedly creating new strings, I use:

```java
StringBuilder sb = new StringBuilder();
```

Then:

```java
sb.append(...)
```

adds characters to the result.

Finally:

```java
return sb.toString();
```

converts the `StringBuilder` into the required `String`.

This is a standard Java technique when constructing a string incrementally.

---

# Pattern Recognition

## Pattern: Two Pointers

This problem is another example of the **Two Pointers** pattern.

But unlike problems where both pointers operate on the same array or string, here each pointer belongs to a **different string**.

```text
word1: a b c d
        ↑
      ptr1

word2: p q r
        ↑
      ptr2
```

The pointers move independently:

```text
ptr1++ → after taking a character from word1
ptr2++ → after taking a character from word2
```

The general pattern is:

```text
Two sequences
     ↓
One pointer for each sequence
     ↓
Process elements according to the required relationship
```

---

# Core Algorithm

The entire solution can be summarized as:

```text
ptr1 = 0
ptr2 = 0

while both strings have characters:

    add word1[ptr1]
    ptr1++

    add word2[ptr2]
    ptr2++

append remaining characters of word1
append remaining characters of word2

return result
```

Visualized:

```text
word1 → a → b → c → d
         ↓   ↓   ↓
word2 → p → q → r
         ↓   ↓   ↓

result → a p b q c r d
```

---

# Why This Works

At every iteration, the algorithm follows exactly the order required by the problem:

```text
1. Character from word1
2. Character from word2
```

The pointers ensure that each character is used exactly once.

When one string finishes, the remaining characters from the other string are appended.

Therefore:

* Characters from both strings are preserved.
* The alternating order is maintained.
* No character is skipped.
* Remaining characters are added at the end.

---

# Edge Cases

## Both Strings Have the Same Length

```text
word1 = "abc"
word2 = "pqr"
```

Result:

```text
"apbqcr"
```

---

## `word1` Is Shorter

```text
word1 = "ab"
word2 = "pqrs"
```

Result:

```text
"apbqrs"
```

The remaining:

```text
"rs"
```

is appended.

---

## `word2` Is Shorter

```text
word1 = "abcd"
word2 = "pq"
```

Result:

```text
"apbqcd"
```

The remaining:

```text
"cd"
```

is appended.

---

## Both Strings Have One Character

```text
word1 = "a"
word2 = "b"
```

Result:

```text
"ab"
```

---

# Complexity

Let:

```text
m = word1.length()
n = word2.length()
```

## Time Complexity

Every character from both strings is processed exactly once.

Therefore:

```text
O(m + n)
```

The alternating loop processes the common portion, and the remaining loops process whichever string is longer.

So the total work is:

```text
Time = O(m + n)
```

---

## Space Complexity

The solution creates:

```java
char[] c1
char[] c2
```

which require:

```text
O(m + n)
```

space.

The `StringBuilder` also stores the final merged string:

```text
O(m + n)
```

Therefore, considering the data structures created by my implementation:

```text
Space = O(m + n)
```

The two-pointer variables themselves require only `O(1)` extra space.

---

# Key Learning

### 1. Two Pointers Don't Have to Be on the Same Structure

I previously used two pointers on:

```text
one array
```

or:

```text
one string
```

Here, the pointers operate on **two different strings**:

```text
ptr1 → word1
ptr2 → word2
```

This is an important variation of the Two Pointers pattern.

---

### 2. Think About What Happens When One Pointer Finishes

Whenever two sequences are processed together, an important question is:

> What happens when one sequence runs out first?

Here:

```text
word1 exhausted
    ↓
append remaining word2
```

or:

```text
word2 exhausted
    ↓
append remaining word1
```

Handling this explicitly makes the algorithm complete.

---

### 3. Every Pointer Has a Responsibility

Instead of having two pointers that do the same thing:

```text
ptr1 → word1
ptr2 → word2
```

Each pointer has a clear responsibility.

This makes the algorithm easy to reason about.

---

### 4. Build Strings With `StringBuilder`

When repeatedly adding characters in Java:

```java
StringBuilder sb
```

is a natural choice.

The pattern is:

```text
Create StringBuilder
        ↓
append characters
        ↓
toString()
```

---

## Final Takeaway

The solution can be summarized as:

```text
        word1
          ↓
        ptr1
          \
           → alternate → result
          /
        ptr2
          ↑
        word2
```

The core pattern is:

```text
Two Pointers
     +
Two-Sequence Traversal
     +
StringBuilder
```

The solution achieves:

```text
Time:  O(m + n)
Space: O(m + n)
```

The biggest takeaway is:

> **When two sequences need to be processed together, give each sequence its own pointer and move them independently according to the required pattern.**
