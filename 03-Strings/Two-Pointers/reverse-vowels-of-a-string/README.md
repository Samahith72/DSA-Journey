# WIN #34 — Reverse Vowels of a String

## Problem

Given a string `s`, reverse **only the vowels** in the string and return the resulting string.

The vowels are:

```text
a, e, i, o, u
A, E, I, O, U
```

All other characters must remain in their original positions.

For example:

```text
Input:
s = "IceCreAm"

Output:
"AceCreIm"
```

The vowels in the original string are:

```text
[I, e, e, A]
```

After reversing them:

```text
[A, e, e, I]
```

the resulting string becomes:

```text
"AceCreIm"
```

---

## My Java Solution

```java
class Solution {
    public String reverseVowels(String s) {

        char[] answer = s.toCharArray();

        int left = 0;
        int right = s.length()-1;

        while(left < right){

            while(left < right && !isVowel(s.charAt(left))){
                left++;
            }

            while(left < right && !isVowel(s.charAt(right))){
                right--;
            }

            if(left < right){
                swap(answer, left, right);
                left++;
                right--;
            }
        }

        return new String(answer);
        
    }

    private static void swap(char[] c, int left, int right){
        char temp = c[left];
        c[left] = c[right];
        c[right] = temp;
    }

    private static boolean isVowel(char c){
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' ||
               c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
    }
}
```

---

## My Thought Process

The important observation is that I don't need to reverse the entire string.

I only need to reverse the **vowels**.

For example:

```text
"hello"
```

The characters are:

```text
h e l l o
  ↑     ↑
 vowel  vowel
```

The vowels are:

```text
[e, o]
```

After reversing them:

```text
[o, e]
```

the result becomes:

```text
"holle"
```

The consonants stay exactly where they were.

So I need a way to find:

```text
leftmost vowel
        +
rightmost vowel
```

and swap them.

This immediately suggests **Two Pointers**.

---

# Two Pointer Setup

I use:

```java
int left = 0;
int right = s.length() - 1;
```

So the pointers start at opposite ends:

```text
left →                    ← right
 I c e C r e A m
```

The goal is to move them toward the center.

But there is one important rule:

> The pointers should only stop when they find vowels.

---

# Step 1 — Move `left` to a Vowel

The first inner loop is:

```java
while(left < right && !isVowel(s.charAt(left))){
    left++;
}
```

This means:

> Keep moving `left` forward until it reaches a vowel.

For:

```text
"hello"
```

we start at:

```text
h e l l o
↑
left
```

`h` is not a vowel, so:

```text
left++
```

Now:

```text
h e l l o
  ↑
 left
```

`e` is a vowel, so we stop.

---

# Step 2 — Move `right` to a Vowel

Similarly:

```java
while(left < right && !isVowel(s.charAt(right))){
    right--;
}
```

This moves `right` backward until it reaches a vowel.

For:

```text
"hello"
```

we start at:

```text
h e l l o
        ↑
       right
```

`o` is already a vowel, so we stop immediately.

Now both pointers are pointing to vowels:

```text
h e l l o
  ↑     ↑
 left  right
```

---

# Step 3 — Swap the Vowels

Now:

```java
swap(answer, left, right);
```

So:

```text
h e l l o
  ↑     ↑
  e     o
```

becomes:

```text
h o l l e
```

Then both pointers move inward:

```java
left++;
right--;
```

---

# Complete Example

Consider:

```text
s = "IceCreAm"
```

The characters are:

```text
I c e C r e A m
```

Start:

```text
left = 0
right = 7
```

### First vowel pair

`left` finds:

```text
I
↑
```

`right` moves backward and finds:

```text
A
↑
```

Swap:

```text
I c e C r e A m
↑             ↑
```

becomes:

```text
A c e C r e I m
```

Now:

```text
left++
right--
```

---

### Second vowel pair

`left` moves to:

```text
e
```

`right` moves to:

```text
e
```

So we swap:

```text
A c e C r e I m
    ↑       ↑
    e       e
```

Since both characters are the same, the string remains:

```text
A c e C r e I m
```

The pointers continue moving inward.

The final result is:

```text
"AceCreIm"
```

---

# Why Do We Use `char[]`?

Java `String` objects are immutable.

That means we cannot directly change:

```java
s.charAt(left)
```

So I convert the string into a character array:

```java
char[] answer = s.toCharArray();
```

Now I can modify individual characters:

```java
answer[left]
answer[right]
```

After all swaps are complete, I convert the array back into a string:

```java
return new String(answer);
```

---

# Why Does `isVowel()` Help?

Instead of repeatedly writing a long condition inside the main algorithm, I created:

```java
private static boolean isVowel(char c)
```

It returns `true` when the character is one of:

```text
a e i o u
A E I O U
```

This keeps the main two-pointer logic easier to read:

```java
while(left < right && !isVowel(...))
```

rather than mixing all the vowel comparisons into the pointer logic.

---

# Important Detail — Checking `s` vs `answer`

My pointer checks use:

```java
s.charAt(left)
s.charAt(right)
```

while the swaps happen in:

```java
answer
```

This works because I only swap characters that are vowels with other vowels.

Therefore, the characters at the pointer positions remain vowels even after previous swaps.

The original string is also never modified.

---

# Pattern Recognition

## Pattern: Two Pointers

This is a classic **opposite-direction two-pointer** problem.

The pointers start at:

```text
left →              ← right
```

and move toward each other.

The general pattern is:

```text
Move left until condition is satisfied
Move right until condition is satisfied
Process the two elements
Move both pointers inward
```

For this problem:

```text
Move left  → until vowel
Move right → until vowel
Swap vowels
Move inward
```

---

# The Core Algorithm

The entire idea can be summarized as:

```text
left = beginning
right = end

while left < right:

    move left until it finds a vowel

    move right until it finds a vowel

    if left < right:
        swap the vowels
        move both pointers inward
```

Visualized:

```text
vowel                              vowel
  ↓                                  ↓
[ ... LEFT ... characters ... RIGHT ... ]
   →                                  ←
```

After swapping:

```text
[ ... swapped ... ]
       →        ←
```

Eventually the pointers meet.

---

# Why This Works

Every vowel that belongs at the beginning of the vowel sequence must come from the corresponding vowel at the end.

Similarly, every vowel at the end must come from the corresponding vowel at the beginning.

By repeatedly swapping:

```text
first vowel ↔ last vowel
second vowel ↔ second-last vowel
...
```

we effectively reverse the sequence of vowels without moving any consonants.

Therefore:

* Every vowel is reversed.
* Every consonant remains in its original position.
* The final string satisfies the problem requirements.

---

# Example

Consider:

```text
s = "leetcode"
```

The vowels are:

```text
[e, e, o, e]
```

Reverse them:

```text
[e, o, e, e]
```

The resulting string is:

```text
"leotcede"
```

The consonants:

```text
l
t
c
d
```

remain in their original positions.

---

# Edge Cases

## No Vowels

```text
s = "bcdfg"
```

There is nothing to reverse.

Output:

```text
"bcdfg"
```

---

## One Vowel

```text
s = "cat"
```

The only vowel is:

```text
a
```

Reversing one element changes nothing.

Output:

```text
"cat"
```

---

## Same Vowels

```text
s = "a"
```

Output:

```text
"a"
```

---

## Uppercase Vowels

The problem allows uppercase vowels, so `isVowel()` checks both:

```text
Lowercase:
a e i o u

Uppercase:
A E I O U
```

---

# Complexity

## Time Complexity

```text
O(n)
```

The `left` pointer only moves forward.

The `right` pointer only moves backward.

Even though there are nested `while` loops, each pointer travels across the string at most once.

Therefore the total work is:

```text
O(n)
```

---

## Space Complexity

```text
O(n)
```

The input string is converted into:

```java
char[] answer = s.toCharArray();
```

which requires a character array proportional to the size of the string.

Therefore, the auxiliary space used by my implementation is:

```text
O(n)
```

The two-pointer logic itself uses only `O(1)` extra variables, but the mutable character array makes the overall space complexity `O(n)`.

---

# Key Learning

### 1. Identify What Actually Needs to Move

The problem doesn't ask us to reverse the whole string.

Only the vowels need to move.

So instead of performing unnecessary operations on consonants, we skip them.

---

### 2. Two Pointers Can Skip Irrelevant Elements

This is an important variation of the two-pointer pattern.

We aren't simply comparing:

```text
left
right
```

at every step.

Instead:

```text
left  → skip non-vowels
right → skip non-vowels
```

until both pointers find something meaningful to process.

---

### 3. In-Place Modification Often Starts With a Mutable Representation

Since Java `String` is immutable, I use:

```java
char[] answer = s.toCharArray();
```

This gives me direct access to individual characters and allows constant-time swaps.

---

### 4. Don't Overcomplicate the Problem

The problem looks like a string manipulation problem, but the actual algorithm is very simple:

```text
Find vowel
Find vowel
Swap
Move inward
```

Recognizing the **Two Pointers** pattern turns it into a linear-time solution.

---

## Final Takeaway

The core idea is:

```text
left → find first vowel
right → find last vowel
        ↓
      swap
        ↓
   move inward
```

The pattern is:

```text
Two Pointers
     +
Skip Invalid / Irrelevant Elements
     +
In-Place Character Swapping
```

My solution achieves:

```text
Time:  O(n)
Space: O(n)
```

The most important takeaway is:

> **When only certain elements of a sequence need to be rearranged, use two pointers to skip everything irrelevant and process only the elements that matter.**
