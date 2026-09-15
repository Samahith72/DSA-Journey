# WIN #35 — Find First Palindromic String in the Array

## Problem

Given an array of strings `words`, return the **first palindromic string** in the array.

A string is palindromic if it reads the same from left to right and right to left.

For example:

```text
"racecar"
```

is a palindrome because:

```text
racecar
←     →
```

reads the same in both directions.

If there is no palindromic string, return:

```text
""
```

For example:

```text
Input:
words = ["abc","car","ada","racecar","cool"]

Output:
"ada"
```

Even though `"racecar"` is also a palindrome, `"ada"` appears first.

---

## My Java Solution

```java
class Solution {
    public String firstPalindrome(String[] words) {

        for(String word: words){
            if(isPalindrome(word)){
                return word;
            }
        }

        return "";
    }

    private static boolean isPalindrome(String word){
        int left = 0;
        int right = word.length() - 1;

        while(left < right){
            if(word.charAt(left) != word.charAt(right)){
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
```

---

## My Thought Process

There are really two problems here:

1. Find the words one by one in their original order.
2. Check whether each word is a palindrome.

The problem specifically asks for the **first** palindrome.

So there is no need to check every word after finding one.

I can simply:

```text
Scan from left to right
        ↓
Check each word
        ↓
Palindrome?
   ↓          ↓
 YES          NO
  ↓            ↓
Return it    Continue
```

If I reach the end without finding one, return:

```text
""
```

---

# Step 1 — Check Words in Order

I use:

```java
for(String word : words)
```

This guarantees that the strings are checked in the same order they appear in the array.

For:

```text
["abc", "car", "ada", "racecar", "cool"]
```

the order is:

```text
1. "abc"
2. "car"
3. "ada"
4. "racecar"
5. "cool"
```

As soon as `"ada"` is found to be a palindrome, I return it.

There is no reason to continue checking `"racecar"` or `"cool"`.

---

# Step 2 — Check Whether a Word Is a Palindrome

To check a palindrome, I use two pointers:

```java
int left = 0;
int right = word.length() - 1;
```

They start at opposite ends:

```text
left →               ← right
r a c e c a r
```

The idea is simple:

> Compare characters at the same distance from the two ends.

So:

```text
word[left] == word[right]
```

must be true.

If they are different, the word cannot be a palindrome.

---

# Step 3 — Move the Pointers Inward

After comparing the two characters:

```java
left++;
right--;
```

For:

```text
"racecar"
```

we compare:

```text
r == r
```

Then:

```text
a == a
```

Then:

```text
c == c
```

The pointers eventually meet in the middle.

Since no mismatch was found, the method returns:

```java
return true;
```

---

# Example Walkthrough

Consider:

```text
words = ["abc", "car", "ada", "racecar", "cool"]
```

### Word 1 — `"abc"`

Pointers:

```text
a b c
↑   ↑
```

Compare:

```text
a != c
```

So:

```text
"abc" → Not a palindrome
```

Continue.

---

### Word 2 — `"car"`

```text
c a r
↑   ↑
```

Compare:

```text
c != r
```

So:

```text
"car" → Not a palindrome
```

Continue.

---

### Word 3 — `"ada"`

```text
a d a
↑   ↑
```

Compare:

```text
a == a
```

Move inward:

```text
  d
  ↑
```

The pointers meet.

Therefore:

```text
"ada" → Palindrome
```

The main method immediately executes:

```java
return word;
```

So the final answer is:

```text
"ada"
```

---

# Why Do We Return Immediately?

The problem asks for:

> The **first** palindromic string.

Suppose:

```text
words = ["abc", "racecar", "madam"]
```

Both `"racecar"` and `"madam"` are palindromes.

But the answer must be:

```text
"racecar"
```

Therefore, once a palindrome is found, we should immediately return it.

This is a useful general problem-solving pattern:

> **When the problem asks for the first element satisfying a condition, stop searching as soon as the condition is satisfied.**

---

# Why Does `isPalindrome()` Work?

A palindrome has matching characters from both ends.

For example:

```text
"level"
```

can be viewed as:

```text
l e v e l
↑       ↑
```

Then:

```text
l == l
```

Move inward:

```text
l e v e l
  ↑   ↑
```

Then:

```text
e == e
```

Finally:

```text
  v
  ↑
```

The middle character does not need to be compared with anything.

Therefore, the loop condition:

```java
while(left < right)
```

is enough.

---

# Pattern Recognition

## Pattern: Two Pointers — Palindrome Check

This is one of the most common applications of the **Two Pointers** pattern.

The structure is:

```text
left →              ← right

Compare both characters

If different:
    Not a palindrome

If same:
    Move both pointers inward
```

The general template is:

```text
left = 0
right = length - 1

while(left < right):

    compare left and right

    if different:
        return false

    left++
    right--

return true
```

This pattern can be reused for many palindrome-related problems.

---

# Why Not Reverse the String?

One possible approach would be:

```text
original string
      ↓
reverse it
      ↓
compare both strings
```

But that requires creating another string.

My solution doesn't need to create a reversed copy.

Instead, it directly compares the characters from both ends.

This makes the palindrome check:

```text
O(n) time
O(1) extra space
```

where `n` is the length of the word.

---

# Handling the No-Palindrome Case

Suppose:

```text
words = ["def", "ghi"]
```

We check:

```text
"def" → not palindrome
"ghi" → not palindrome
```

The loop finishes without returning.

So we execute:

```java
return "";
```

Therefore:

```text
Output:
""
```

---

# Edge Cases

## Single-Character Word

```text
["a"]
```

A single character is always a palindrome.

The pointers are:

```text
left = 0
right = 0
```

Since:

```text
left < right
```

is false, the loop doesn't execute.

The method returns:

```text
true
```

---

## Two-Character Palindrome

```text
["aa"]
```

Compare:

```text
a == a
```

So it is a palindrome.

---

## Two Different Characters

```text
["ab"]
```

Compare:

```text
a != b
```

So it is not a palindrome.

---

## No Palindrome

```text
["abc", "def", "ghi"]
```

Every word fails the palindrome check.

The final result is:

```text
""
```

---

# Complexity

Let:

```text
W = number of words
L = length of a word
```

## Time Complexity

The outer loop scans the words:

```text
O(W)
```

For each word, the palindrome check can take:

```text
O(L)
```

because the two pointers may examine roughly half the characters.

In the worst case, every word may need to be checked completely.

Therefore, the overall worst-case complexity is:

```text
O(total number of characters across all words)
```

or, if every word has roughly length `L`:

```text
O(W × L)
```

The algorithm can be faster in practice because it stops immediately when the first palindrome is found.

---

## Space Complexity

The palindrome check uses only:

```text
left
right
```

and a few primitive variables.

Therefore, the auxiliary space is:

```text
O(1)
```

No reversed string or additional data structure is created.

---

# Key Learning

### 1. First Means Stop Early

When a problem asks for the **first** element satisfying a condition:

```text
Check
 ↓
Valid?
 ↓
YES → Return immediately
```

There is no reason to continue searching.

---

### 2. Palindromes Naturally Suggest Two Pointers

Whenever a string needs to be compared from both ends, think:

```text
Two Pointers
```

Start with:

```text
left = beginning
right = end
```

and move inward.

---

### 3. Don't Create Extra Data When You Don't Need It

To check whether a string is a palindrome, I don't need:

```text
original string
+
reversed string
```

I can simply compare:

```text
first ↔ last
second ↔ second-last
...
```

using constant extra space.

---

### 4. Separate the Responsibilities

My solution separates:

```text
firstPalindrome()
```

from:

```text
isPalindrome()
```

The first method answers:

> Which word should I return?

The helper method answers:

> Is this particular word a palindrome?

This makes the code easier to understand and reuse.

---

## Final Takeaway

The solution can be summarized as:

```text
Scan words from left to right
          ↓
Check each word using Two Pointers
          ↓
left ↔ right
          ↓
Mismatch → not palindrome
Match → move inward
          ↓
First palindrome → return immediately
          ↓
No palindrome → return ""
```

The core pattern is:

```text
Two Pointers
     +
Palindrome Check
     +
Early Return
```

The palindrome check itself uses:

```text
Time:  O(length of word)
Space: O(1)
```

and the overall solution efficiently finds the **first** palindromic word without unnecessarily processing the remaining words.
