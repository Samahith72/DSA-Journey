# WIN #38 — Long Pressed Name

## Problem

Given two strings `name` and `typed`, determine whether `typed` could have been produced by typing `name` when some keys were **long pressed**.

When a key is long pressed, the same character may appear multiple times.

For example:

```text
name = "alex"
typed = "aaleex"
```

This is valid because:

```text
a → aa
l → l
e → ee
x → x
```

Therefore:

```text
Output:
true
```

But:

```text
name = "saeed"
typed = "ssaaedd"
```

is invalid because the second `e` from `name` is missing.

---

## My Java Solution

```java
class Solution {
    public boolean isLongPressedName(String name, String typed) {

        if(typed.length() < name.length()){
            return false;
        }

        int i = 0;
        int j = 0;

        while(j < typed.length()){

            if(i < name.length() && name.charAt(i) == typed.charAt(j)){
                i++;
                j++;
            }
            else if(j > 0 && typed.charAt(j) == typed.charAt(j-1)){
                j++;
            }
            else{
                return false;
            }
        }

        return i == name.length();
    }
}
```

---

## My Thought Process

The first thing I noticed is that `typed` can contain **extra characters**, but those extra characters cannot be arbitrary.

They must be repetitions caused by a long press.

For example:

```text
name  = "alex"
typed = "aaleex"
```

We can match:

```text
name:   a   l   e   x
        ↓   ↓   ↓   ↓
typed:  a a l e e x
          ↑     ↑
        extra  extra
```

The extra `a` and `e` are valid because they repeat the previous character.

So I need two pointers:

```text
i → current character in name
j → current character in typed
```

---

# Step 1 — Check Length

I first check:

```java
if(typed.length() < name.length()){
    return false;
}
```

Why?

A long press can add extra characters, but it can never remove characters from `name`.

Therefore:

```text
typed.length() < name.length()
```

means it is immediately impossible.

For example:

```text
name  = "abc"
typed = "ab"
```

There is no way to create `"abc"` from only two typed characters.

So we return:

```text
false
```

---

# Step 2 — Use Two Pointers

I initialize:

```java
int i = 0;
int j = 0;
```

where:

```text
i → name
j → typed
```

For:

```text
name  = "alex"
typed = "aaleex"
```

we start with:

```text
name:
a l e x
↑
i

typed:
a a l e e x
↑
j
```

---

# Step 3 — Match Normal Characters

The first condition is:

```java
if(i < name.length() && name.charAt(i) == typed.charAt(j)){
    i++;
    j++;
}
```

If the current characters match, this is a normal character from the original name.

So we consume both:

```text
name  → move i
typed → move j
```

For example:

```text
name  = "alex"
typed = "aaleex"
```

First:

```text
name[i]   = 'a'
typed[j]  = 'a'
```

They match.

So:

```text
i++
j++
```

Now:

```text
name:
a l e x
  ↑
  i

typed:
a a l e e x
  ↑
  j
```

---

# Step 4 — Handle a Long Press

Now `typed[j]` may not match `name[i]`.

That does **not** immediately mean the answer is false.

It could be an extra character caused by a long press.

The second condition checks:

```java
else if(j > 0 && typed.charAt(j) == typed.charAt(j-1)){
    j++;
}
```

This asks:

> Is the current typed character the same as the previous typed character?

If yes, it can be considered an extra long-pressed character.

For:

```text
typed = "aaleex"
```

after matching the first `a`:

```text
typed:
a a l e e x
  ↑
  j
```

We compare:

```text
typed[j]     = 'a'
typed[j - 1] = 'a'
```

They are equal.

So this extra `a` is allowed.

We simply move:

```text
j++
```

without moving `i`.

---

# Why Don't We Move `i` for a Long Press?

This is one of the most important ideas in the solution.

Suppose:

```text
name = "alex"
typed = "aaleex"
```

The first two typed characters are:

```text
aa
```

But they represent only one character from `name`:

```text
name → a
typed → aa
```

So after matching the first `a`:

```text
i → next character 'l'
j → extra 'a'
```

The extra `a` should only advance `j`.

Therefore:

```text
Normal match:
i++ and j++

Long press:
only j++
```

---

# Step 5 — Reject Invalid Characters

If neither condition is true:

```java
else{
    return false;
}
```

That means:

1. The current typed character does not match the current name character.
2. It is also not a repetition of the previous typed character.

Therefore, the typed string cannot have been produced by long pressing the name.

For example:

```text
name  = "alex"
typed = "ablex"
```

When we reach:

```text
name[i]   = 'l'
typed[j]  = 'b'
```

`b` is not:

```text
'l'
```

and it is not a repetition of the previous typed character.

Therefore:

```text
false
```

---

# Complete Example

Consider:

```text
name  = "alex"
typed = "aaleex"
```

Let's process it step by step.

### Step 1

```text
name[i]   = a
typed[j]  = a
```

Match.

```text
i++
j++
```

---

### Step 2

```text
name[i]   = l
typed[j]  = a
```

They don't match.

Check previous typed character:

```text
typed[j-1] = a
typed[j]   = a
```

They match.

So this is a long press.

```text
j++
```

`i` stays where it is.

---

### Step 3

Now:

```text
name[i]   = l
typed[j]  = l
```

Match.

```text
i++
j++
```

---

### Step 4

Now:

```text
name[i]   = e
typed[j]  = e
```

Match.

---

### Step 5

Next:

```text
name[i]   = x
typed[j]  = e
```

They don't match.

But:

```text
typed[j] == typed[j-1]
```

because:

```text
e == e
```

So this is another long press.

```text
j++
```

---

### Step 6

Now:

```text
name[i]   = x
typed[j]  = x
```

Match.

Both strings are processed.

Therefore:

```text
i == name.length()
```

and the answer is:

```text
true
```

---

# The Key Rule

At every character in `typed`, there are only three possibilities:

```text
                         typed[j]
                            |
             +--------------+--------------+
             |              |              |
          Matches       Repeated        Neither
          name[i]       previous       condition
             |              |              |
          i++, j++         j++          false
```

This makes the algorithm easy to reason about.

---

# Pattern Recognition

## Pattern: Two Pointers + Greedy Matching

This is a variation of the **Two Pointers** pattern.

We have:

```text
i → name
j → typed
```

But the pointers don't always move together.

### Normal character

```text
name[i] == typed[j]

i++
j++
```

### Long-pressed character

```text
typed[j] == typed[j-1]

j++
```

The important observation is:

> A character in `typed` that doesn't match the next character in `name` can only be accepted if it is a repetition of the previous typed character.

---

# Why the Greedy Approach Works

When:

```text
name[i] == typed[j]
```

we immediately match them.

There is no reason to delay this match because `typed[j]` already represents the next required character from `name`.

When they don't match, we only accept the character if it is a repetition of the previous typed character.

This gives us a simple greedy rule:

```text
Match the required character whenever possible.
Otherwise, consume only valid repeated characters.
Reject everything else.
```

---

# Example of an Invalid Case

Consider:

```text
name  = "saeed"
typed = "ssaaedd"
```

Start matching:

```text
s → s
```

The next `s` is a repetition, so it is allowed.

Then:

```text
a → a
```

The next `a` is also a repetition.

Then we reach:

```text
name:
s a e e d
    ↑
    i

typed:
s s a a e d d
        ↑
        j
```

The next required character is:

```text
e
```

but typed gives:

```text
d
```

And:

```text
d != previous typed character e
```

So this cannot be a long press.

Therefore:

```text
false
```

---

# Why `return i == name.length()`?

At the end, I use:

```java
return i == name.length();
```

This is important.

It is not enough for us to successfully process all of `typed`.

We also need to make sure that **every character from `name` was matched**.

For example:

```text
name  = "alex"
typed = "ale"
```

We can process all of `typed`, but:

```text
x
```

from `name` is still missing.

Therefore:

```text
i != name.length()
```

and we return:

```text
false
```

This final check guarantees that the entire original name has been matched.

---

# Edge Cases

## Exact Match

```text
name  = "alex"
typed = "alex"
```

No long press occurs.

Output:

```text
true
```

---

## Entire Character Is Long Pressed

```text
name  = "a"
typed = "aaaa"
```

The first `a` matches.

All remaining `a`s are repetitions.

Output:

```text
true
```

---

## No Extra Characters

```text
name  = "abc"
typed = "abc"
```

Output:

```text
true
```

---

## Typed String Too Short

```text
name  = "alex"
typed = "ale"
```

Since:

```text
typed.length() < name.length()
```

we immediately return:

```text
false
```

---

## Wrong Extra Character

```text
name  = "alex"
typed = "ablex"
```

The `b` cannot be explained by a long press.

Output:

```text
false
```

---

# Complexity

Let:

```text
n = name.length()
m = typed.length()
```

## Time Complexity

Both pointers only move forward.

`i` moves at most:

```text
n
```

times.

`j` moves at most:

```text
m
```

times.

Therefore:

```text
O(n + m)
```

---

## Space Complexity

Only two pointers and a few primitive variables are used.

No additional data structure is created.

Therefore:

```text
O(1)
```

extra space.

---

# Key Learning

### 1. Extra Characters Need a Valid Explanation

Just because `typed` contains more characters doesn't mean they are automatically valid.

Every extra character must be explainable as:

```text
same character as the previous typed character
```

---

### 2. Two Pointers Don't Always Move Together

This problem is a good example of independent pointer movement.

Normal match:

```text
i++   j++
```

Long press:

```text
      j++
```

This is an important variation of the Two Pointers pattern.

---

### 3. Final Validation Matters

Processing all of `typed` does not guarantee that all of `name` was matched.

That's why:

```java
return i == name.length();
```

is necessary.

---

### 4. Greedy Matching Can Simplify String Problems

Whenever the current character directly matches what is required, consume it immediately.

When it doesn't, only accept it if there is a valid reason for the extra character.

This avoids complicated backtracking.

---

## Final Takeaway

The solution can be summarized as:

```text
i → pointer for name
j → pointer for typed

while j hasn't reached the end:

    if name[i] == typed[j]:
        match both
        i++
        j++

    else if typed[j] == typed[j-1]:
        treat it as long press
        j++

    else:
        invalid
        return false

return i == name.length()
```

The core pattern is:

```text
Two Pointers
     +
Greedy Matching
     +
Run-Length / Repetition Handling
```

The final complexity is:

```text
Time:  O(n + m)
Space: O(1)
```

The biggest takeaway is:

> **When comparing an original sequence with a potentially expanded version, distinguish between characters that advance the original sequence and extra repeated characters that only advance the expanded sequence.**
