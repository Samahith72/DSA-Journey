# WIN #25 — Backspace String Compare

## Problem

Given two strings `s` and `t`, return `true` if they are equal after applying all backspaces.

The `#` character represents a **backspace**.

For example:

```text
s = "ab#c"
```

The `#` removes the character before it:

```text
"ab#c"
   ↓
"ac"
```

So if:

```text
s = "ab#c"
t = "ad#c"
```

both strings become:

```text
"ac"
```

Therefore, the answer is:

```text
true
```

---

## My Java Solution

```java
class Solution {
    public boolean backspaceCompare(String s, String t) {
        int i = s.length()-1;
        int j = t.length() -1;

        int skipS =0, skipT = 0;

        while(i >= 0 || j >=0){
            while(i >=0){
                if(s.charAt(i) == '#'){
                    skipS++;
                    i--;
                }else if(skipS > 0){
                    skipS--;
                    i--;
                }else{
                    break;
                }
            }

            while( j >= 0){
                if(t.charAt(j) == '#'){
                    skipT++;
                    j--;
                }else if(skipT >0){
                    skipT--;
                    j--;
                }else{
                    break;
                }
            }

            if(i < 0 && j < 0){
                return true;
            }else if(i < 0 || j < 0){
                return false;
            }

            if(s.charAt(i) != t.charAt(j)){
                return false;
            }

            i--;
            j--;
        }

        return true;
    }
}
```

---

## My Thought Process

The first idea that might come to mind is to actually process both strings and create their final versions.

For example:

```text
"ab#c" → "ac"
"ad#c" → "ac"
```

Then compare:

```text
"ac" == "ac"
```

But that would require extra space to store the processed strings.

The follow-up asks for:

```text
O(n) time
O(1) space
```

So I wanted to compare the strings **without actually constructing the final strings**.

The key observation is:

> If we scan from right to left, we can determine which characters will survive the backspaces.

That's why I use two pointers.

```text
i → scans string s from right to left
j → scans string t from right to left
```

I also maintain:

```text
skipS → number of characters in s that should be skipped
skipT → number of characters in t that should be skipped
```

---

## Why Scan From Right to Left?

Consider:

```text
s = "ab#c"
```

If we scan from left to right:

```text
a → keep
b → keep
# → delete b
c → keep
```

We would need to somehow remove `b` that we already processed.

But scanning from right to left makes the situation easier:

```text
"ab#c"
     ↑
```

We first encounter:

```text
c
```

There is no backspace affecting it, so `c` survives.

Then we encounter:

```text
#
```

This tells us:

> The next valid character to the left must be skipped.

So we can simply maintain a counter.

---

## Understanding `skipS` and `skipT`

These variables represent pending backspaces.

For example:

```text
s = "ab#c"
```

Starting from the right:

```text
c
#
b
a
```

When we encounter `#`:

```java
skipS++;
```

So:

```text
skipS = 1
```

Then when we encounter `b`:

```java
else if(skipS > 0){
    skipS--;
    i--;
}
```

We skip `b`.

Now:

```text
skipS = 0
```

The next character is `a`, which survives.

Therefore:

```text
"ab#c"
```

effectively becomes:

```text
"ac"
```

without ever creating `"ac"`.

---

## Step-by-Step Example

Consider:

```text
s = "ab#c"
t = "ad#c"
```

### Initial State

```text
s = "ab#c"
         ↑ i

t = "ad#c"
         ↑ j

skipS = 0
skipT = 0
```

---

### Step 1 — Compare `c`

For `s`:

```text
s[i] = 'c'
```

There are no pending backspaces.

So `c` survives.

For `t`:

```text
t[j] = 'c'
```

Again, `c` survives.

Compare:

```text
c == c
```

So we move both pointers left.

```text
i--
j--
```

---

### Step 2 — Encounter `#`

Now both pointers are at:

```text
#
```

For `s`:

```java
if(s.charAt(i) == '#'){
    skipS++;
    i--;
}
```

So:

```text
skipS = 1
```

Similarly:

```text
skipT = 1
```

---

### Step 3 — Skip the Previous Character

For `s`, the previous character is:

```text
b
```

Since:

```text
skipS > 0
```

we skip it.

```text
skipS--
i--
```

For `t`, we skip:

```text
d
```

So both strings effectively have:

```text
"ac"
```

---

### Step 4 — Compare `a`

The next valid character in both strings is:

```text
a
```

So:

```text
a == a
```

The pointers move again.

Eventually both strings are exhausted.

Therefore:

```text
true
```

---

## Important Edge Case: Multiple Backspaces

Consider:

```text
s = "ab##"
```

Starting from the right:

```text
#
```

First backspace:

```text
skipS = 1
```

Then another:

```text
skipS = 2
```

Now we encounter:

```text
b
```

Since:

```text
skipS > 0
```

we skip `b`:

```text
skipS = 1
```

Then we encounter:

```text
a
```

Again:

```text
skipS > 0
```

so we skip `a`:

```text
skipS = 0
```

Nothing remains.

Therefore:

```text
"ab##" → ""
```

This is exactly what we want.

---

## Why Do We Need Two Inner Loops?

The main loop is:

```java
while(i >= 0 || j >= 0)
```

Inside it, we have one loop for `s` and another for `t`.

Their job is to find the **next valid character** in each string.

For `s`:

```java
while(i >=0){
    if(s.charAt(i) == '#'){
        skipS++;
        i--;
    }else if(skipS > 0){
        skipS--;
        i--;
    }else{
        break;
    }
}
```

The loop continues while:

* We find a `#`, so we increase the number of pending skips.
* We have pending skips, so we skip the current character.

It stops when we finally find a character that should actually be compared.

The exact same logic is applied to `t`.

---

## Comparing the Valid Characters

After both inner loops finish, `i` and `j` point to characters that will actually appear in the final strings.

So we check:

```java
if(s.charAt(i) != t.charAt(j)){
    return false;
}
```

If the characters are different, the final strings cannot be equal.

Therefore, we can immediately return:

```text
false
```

If they are equal:

```java
i--;
j--;
```

and continue looking for the next valid characters.

---

## Handling Different Lengths

After finding the next valid characters, there are three possibilities.

### Both Strings Are Exhausted

```java
if(i < 0 && j < 0){
    return true;
}
```

This means both strings have become empty at the same time.

Therefore, they are equal.

---

### Only `s` Is Exhausted

```java
else if(i < 0 || j < 0){
    return false;
}
```

If one string still has a valid character while the other doesn't, they cannot be equal.

For example:

```text
s = "a#"
t = "b"
```

`a#` becomes:

```text
""
```

while `t` becomes:

```text
"b"
```

So the answer is:

```text
false
```

---

## Pattern Recognition

### Pattern: Two Pointers + Skip Counting

This problem is a great example of combining **Two Pointers** with a small amount of state.

The pointers start at the end:

```text
s → i
t → j
```

and move toward the beginning.

The important state is:

```text
skipS
skipT
```

which tells us how many characters need to be ignored because of backspaces.

The general pattern is:

```text
Start from the end
       ↓
Find next valid character
       ↓
Compare characters
       ↓
Move both pointers
       ↓
Repeat
```

---

## Why Does This Work?

Every `#` represents one deletion of the closest valid character to its left.

By scanning from right to left, we encounter the `#` **before** the character it deletes.

Therefore, we can simply record:

```text
skip++
```

and skip the next valid character we encounter.

This allows us to simulate backspaces without modifying the strings.

---

## Complexity

### Time Complexity

Each pointer only moves from the end of its string toward the beginning.

Even though there are nested loops, characters are not repeatedly processed indefinitely.

Each character is visited a constant number of times.

Therefore:

```text
O(n + m)
```

where:

* `n` = length of `s`
* `m` = length of `t`

If both strings are considered to have approximately the same length, this is commonly written as:

```text
O(n)
```

### Space Complexity

I don't create a new string, array, stack, or other data structure.

Only four integer variables are used:

```text
i
j
skipS
skipT
```

Therefore:

```text
O(1)
```

extra space.

---

## Follow-Up

The problem asks:

> Can you solve it in `O(n)` time and `O(1)` space?

Yes.

**This solution already satisfies the follow-up.**

```text
Time:  O(n + m)
Space: O(1)
```

The important optimization is avoiding the creation of processed strings.

Instead of:

```text
String → Process → New String
```

we do:

```text
String
   ↓
Two Pointer
   ↓
Skip Backspaces
   ↓
Compare Directly
```

---

## Key Learning

* Backspaces make left-to-right processing inconvenient because a character may need to be deleted after we have already seen it.
* Scanning from **right to left** makes backspace processing much easier.
* A `skip` counter can represent pending backspaces.
* Two pointers allow both strings to be processed simultaneously.
* We don't need to actually modify or rebuild either string.
* Nested loops do not automatically mean `O(n²)` — here, each pointer moves only backward through its string.
* This solution achieves the optimal follow-up requirements of **O(n) time and O(1) space**.

---

## Final Takeaway

The core idea is:

```text
Start from the end of both strings
              ↓
       Encounter '#'
              ↓
        Increase skip
              ↓
     Skip characters to left
              ↓
   Find next valid characters
              ↓
         Compare them
              ↓
       Move both pointers
```

The most important insight to remember is:

> **When a character affects something to its left, consider processing the string from right to left.**

Here, that simple change allows us to handle backspaces efficiently without using extra space.
