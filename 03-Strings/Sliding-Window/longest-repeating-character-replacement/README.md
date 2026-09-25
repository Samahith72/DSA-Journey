# WIN #55 — Longest Repeating Character Replacement

## Problem

[LeetCode 424 — Longest Repeating Character Replacement](https://leetcode.com/problems/longest-repeating-character-replacement/)

We are given:

* A string `s` containing uppercase English letters.
* An integer `k`.

We can change any character into any other uppercase character.

We can perform this operation at most `k` times.

The goal is to find the length of the **longest substring that can be converted into a substring containing only one repeated character**.

---

## Example 1

```text
Input:
s = "ABAB"
k = 2

Output:
4
```

We can change:

```text
A B A B
↓ ↓ ↓ ↓
B B B B
```

We changed the two `A`s into `B`s.

So the entire substring can contain the same character.

Therefore:

```text
Answer = 4
```

---

## Example 2

```text
Input:
s = "AABABBA"
k = 1

Output:
4
```

Consider the substring:

```text
"AABA"
```

The frequency is:

```text
A → 3
B → 1
```

If we replace the `B` with `A`:

```text
AAAA
```

Only one replacement is needed.

Therefore:

```text
Answer = 4
```

There can be multiple ways to obtain a valid substring of length `4`.

---

## My Java Solution

```java
class Solution {
    public int characterReplacement(String s, int k) {

        int[] frequency = new int[26];

        int left = 0;
        int maxFrequency = 0;
        int answer = 0;

        for (int right = 0; right < s.length(); right++) {

            int index = s.charAt(right) - 'A';
            frequency[index]++;

            maxFrequency = Math.max(maxFrequency, frequency[index]);

            while ((right - left + 1) - maxFrequency > k) {

                frequency[s.charAt(left) - 'A']--;
                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        return answer;
        
    }
}
```

---

## My Thought Process

The important thing to realize is that we don't actually need to perform the replacements.

Instead, for every sliding window, we ask:

> How many characters would I need to replace to make the entire window consist of one character?

Suppose our current window is:

```text
"AABAB"
```

The frequencies are:

```text
A → 3
B → 2
```

The best character to keep is `A`, because it occurs the most.

So we keep:

```text
A A A
```

and replace the other characters:

```text
B B
```

Number of replacements needed:

```text
window length - most frequent character count
```

So:

```text
5 - 3 = 2
```

If:

```text
k = 2
```

this window is valid.

Therefore the key formula is:

```text
replacements needed =
window length - maximum frequency
```

---

# Step 1 — Create a Frequency Array

```java
int[] frequency = new int[26];
```

Since the problem contains only uppercase English letters:

```text
A B C D ... Z
```

there are exactly `26` possible characters.

Instead of using a `HashMap`, we can use an array.

The array stores:

```text
character → frequency
```

For example:

```text
frequency[0] → count of A
frequency[1] → count of B
frequency[2] → count of C
...
frequency[25] → count of Z
```

---

# Step 2 — Initialize the Sliding Window

```java
int left = 0;
```

`left` represents the beginning of the current window.

The `right` pointer will move through the string.

So our window is:

```text
[left ........ right]
```

---

# Step 3 — Track the Maximum Frequency

```java
int maxFrequency = 0;
```

This stores the highest frequency of any character inside the current window.

For example, if the window is:

```text
"AABAB"
```

then:

```text
A → 3
B → 2
```

so:

```text
maxFrequency = 3
```

This tells us which character we would keep and repeat.

---

# Step 4 — Store the Answer

```java
int answer = 0;
```

This keeps track of the longest valid window found so far.

---

# Step 5 — Expand the Window

```java
for (int right = 0; right < s.length(); right++) {
```

The `right` pointer moves from left to right.

At every step, we add the new character to the current window.

---

# Step 6 — Convert the Character Into an Array Index

```java
int index = s.charAt(right) - 'A';
```

Because the string contains uppercase English letters, we can map:

```text
A → 0
B → 1
C → 2
...
Z → 25
```

For example:

```text
'B' - 'A'
= 1
```

and:

```text
'D' - 'A'
= 3
```

This lets us use the frequency array.

---

# Step 7 — Increase the Character Frequency

```java
frequency[index]++;
```

Suppose the current character is `A`.

Then:

```text
frequency[0]++
```

If we have seen three `A`s in the window:

```text
frequency[0] = 3
```

---

# Step 8 — Update the Maximum Frequency

```java
maxFrequency = Math.max(maxFrequency, frequency[index]);
```

Suppose the current frequencies are:

```text
A → 3
B → 2
```

Then:

```text
maxFrequency = 3
```

We only care about the most frequent character because that is the character we would keep.

---

# Step 9 — Calculate How Many Replacements Are Needed

This is the most important formula:

```java
(right - left + 1) - maxFrequency
```

First:

```text
right - left + 1
```

is the current window length.

Then:

```text
maxFrequency
```

is the number of characters we can keep unchanged.

Therefore:

```text
window length - maxFrequency
```

is the number of characters that must be replaced.

---

## Example

Suppose:

```text
window = "AABAB"
```

Length:

```text
5
```

Frequency:

```text
A → 3
B → 2
```

Maximum frequency:

```text
3
```

So:

```text
replacements needed = 5 - 3
                     = 2
```

If:

```text
k = 2
```

the window is valid.

---

# Step 10 — Shrink the Window If Necessary

Your code checks:

```java
while ((right - left + 1) - maxFrequency > k) {
```

This means:

> If the number of replacements needed is greater than `k`, the current window is invalid.

We cannot transform the entire window into one repeated character using only `k` operations.

So we need to shrink the window.

---

# Step 11 — Remove the Left Character

```java
frequency[s.charAt(left) - 'A']--;
left++;
```

We remove the character at the left side of the window.

For example:

```text
A A B A B
↑
left
```

If we remove the first `A`:

```text
A B A B
  ↑
 left
```

The window becomes smaller.

We continue shrinking until:

```text
replacements needed <= k
```

---

# Step 12 — Update the Answer

Once the window becomes valid:

```java
answer = Math.max(answer, right - left + 1);
```

We calculate the current valid window length and compare it with the best answer found so far.

---

# Step 13 — Return the Answer

After processing the entire string:

```java
return answer;
```

This gives the maximum length of a substring that can be converted into a string containing one repeated character using at most `k` replacements.

---

## Complete Walkthrough

Let's use:

```text
s = "AABABBA"
k = 1
```

Initially:

```text
left = 0
maxFrequency = 0
answer = 0
```

---

### `right = 0`

Character:

```text
A
```

Window:

```text
A
```

Frequency:

```text
A → 1
```

Maximum frequency:

```text
1
```

Window length:

```text
1
```

Replacements needed:

```text
1 - 1 = 0
```

Valid.

Answer:

```text
1
```

---

### `right = 1`

Character:

```text
A
```

Window:

```text
AA
```

Frequency:

```text
A → 2
```

Maximum frequency:

```text
2
```

Replacements:

```text
2 - 2 = 0
```

Valid.

Answer:

```text
2
```

---

### `right = 2`

Character:

```text
B
```

Window:

```text
AAB
```

Frequency:

```text
A → 2
B → 1
```

Maximum frequency:

```text
2
```

Window length:

```text
3
```

Replacements:

```text
3 - 2 = 1
```

Since:

```text
1 <= k
```

the window is valid.

Answer:

```text
3
```

---

### `right = 3`

Character:

```text
A
```

Window:

```text
AABA
```

Frequency:

```text
A → 3
B → 1
```

Maximum frequency:

```text
3
```

Window length:

```text
4
```

Replacements:

```text
4 - 3 = 1
```

Valid because:

```text
1 <= k
```

Answer:

```text
4
```

---

### `right = 4`

Character:

```text
B
```

Window:

```text
AABAB
```

Frequency:

```text
A → 3
B → 2
```

Maximum frequency:

```text
3
```

Window length:

```text
5
```

Replacements:

```text
5 - 3 = 2
```

But:

```text
2 > k
```

and:

```text
k = 1
```

So the window is invalid.

We shrink it.

Remove the character at `left`:

```text
A A B A B
↑
```

Remove `A`.

Window becomes:

```text
A B A B
```

and:

```text
left++
```

Now the window has length:

```text
4
```

The number of required replacements is now within `k`.

So:

```text
answer = 4
```

---

### Remaining Characters

The same process continues for:

```text
B
A
```

The longest valid window remains:

```text
4
```

Therefore:

```text
Output = 4
```

---

## The Most Important Formula

This problem becomes much easier if you remember:

```text
Replacements Needed
=
Window Length - Most Frequent Character Frequency
```

Or mathematically:

```text
(window size) - maxFrequency
```

If:

```text
(window size) - maxFrequency <= k
```

then the window is valid.

If:

```text
(window size) - maxFrequency > k
```

then the window is invalid and must be shrunk.

---

## Why Does This Formula Work?

Suppose our window is:

```text
"AABAB"
```

Frequencies:

```text
A → 3
B → 2
```

We want the entire window to become the same character.

The best choice is `A`, because it already appears three times.

So:

```text
A A B A B
↓ ↓ ↓ ↓ ↓
A A A A A
```

We only need to change:

```text
B
B
```

That's:

```text
2 replacements
```

And:

```text
window length - maxFrequency
=
5 - 3
=
2
```

Exactly.

---

## Pattern Recognition

### Pattern: Variable-Size Sliding Window + Frequency Array

This is another **variable-size sliding window** problem.

The window expands using:

```text
right++
```

and shrinks whenever it becomes invalid:

```text
left++
```

The difference from the previous problem is the definition of a valid window.

### Previous Problem — Longest Substring Without Repeating Characters

A window is valid when:

```text
No duplicate characters
```

### This Problem

A window is valid when:

```text
Characters that need replacement <= k
```

That is:

```text
window length - maxFrequency <= k
```

---

## Window Invariant

The important condition we maintain is:

```text
window size - maxFrequency <= k
```

Whenever this is true:

```text
window is valid
```

Whenever it becomes false:

```text
window is invalid
```

and we shrink it.

---

## Why We Track `maxFrequency`

You might wonder why we only track the maximum frequency instead of checking every character.

Suppose:

```text
window = "AABBC"
```

Frequencies:

```text
A → 2
B → 2
C → 1
```

The best character to convert everything into is either `A` or `B`.

Both occur twice.

So:

```text
maxFrequency = 2
```

Window size:

```text
5
```

Replacements:

```text
5 - 2 = 3
```

We don't need to know which character has the maximum frequency for the calculation.

We only need the maximum count.

---

## Why a Frequency Array Instead of a HashMap?

The problem explicitly says:

```text
s consists of only uppercase English letters
```

There are only 26 possibilities.

So:

```java
int[] frequency = new int[26];
```

is simpler and more efficient than using a `HashMap<Character, Integer>`.

Mapping:

```text
'A' → 0
'B' → 1
...
'Z' → 25
```

makes frequency lookup constant time.

---

## Important Observation About `maxFrequency`

Notice that when we shrink the window, your code does **not** decrease `maxFrequency`.

For example:

```java
frequency[s.charAt(left) - 'A']--;
left++;
```

but there is no:

```text
maxFrequency--
```

This is intentional and is a standard optimization for this problem.

`maxFrequency` represents the largest frequency observed while expanding the window.

It may occasionally be larger than the true maximum frequency of the current smaller window.

This does **not** cause the algorithm to return an incorrect maximum.

The important part is that it allows us to avoid repeatedly scanning all 26 characters after every shrink.

---

## Why a Stale `maxFrequency` Is Still Safe

Suppose the actual current maximum frequency becomes smaller after removing a character.

The stored:

```text
maxFrequency
```

might temporarily be slightly larger.

That can make the condition:

```text
window size - maxFrequency <= k
```

look more permissive than the exact current frequency would.

But this does not cause us to produce an invalid final maximum length.

Why?

Because the larger `maxFrequency` came from a window that previously existed, and the algorithm only uses it to avoid unnecessary shrinking while scanning forward.

This is a common optimization for LeetCode 424.

---

## Edge Cases

### 1. `k = 0`

If:

```text
k = 0
```

we cannot replace any characters.

Therefore, the longest valid window must already contain only one repeated character.

For:

```text
"ABBB"
```

the answer is:

```text
3
```

because:

```text
"BBB"
```

already consists of one character.

---

### 2. All Characters Are the Same

```text
s = "AAAA"
k = 0
```

No replacements are needed.

The entire string is valid:

```text
answer = 4
```

---

### 3. `k` Is Large Enough for the Entire String

Suppose:

```text
s = "ABAB"
k = 2
```

The entire string has:

```text
A → 2
B → 2
```

Window length:

```text
4
```

Replacements:

```text
4 - 2 = 2
```

Since:

```text
2 <= k
```

the entire string is valid.

Answer:

```text
4
```

---

### 4. One Character

```text
s = "A"
```

There is nothing to replace.

Answer:

```text
1
```

---

## Complexity

Let:

```text
n = s.length()
```

### Time Complexity

The `right` pointer moves from:

```text
0 → n-1
```

so it moves at most `n` times.

The `left` pointer also only moves forward and can move at most `n` times.

Therefore, even though there is a `while` loop inside the `for` loop, the total number of pointer movements is linear.

Time complexity:

```text
O(n)
```

---

### Space Complexity

The frequency array always contains exactly 26 entries:

```java
int[] frequency = new int[26];
```

Therefore:

```text
O(26)
```

which is:

```text
O(1)
```

auxiliary space.

---

## Key Learning

### 1. Find the Majority Character

For every window, identify the character with the highest frequency.

That character is the one we should keep.

Everything else can potentially be replaced.

---

### 2. Replacements = Window Size - Majority Count

This is the most important formula:

```text
replacements = window length - maxFrequency
```

Then compare it with:

```text
k
```

---

### 3. Sliding Window Maintains the Largest Valid Range

We want the longest window satisfying:

```text
window length - maxFrequency <= k
```

So:

```text
Expand while possible
Shrink when invalid
```

---

### 4. Fixed Alphabet → Frequency Array

When the input domain is small and known:

```text
A-Z
```

an array is often simpler than a HashMap.

---

### 5. Nested Loops Can Still Be O(n)

This is an important DSA lesson.

The code contains:

```java
for(...)
```

and:

```java
while(...)
```

but the complexity is still:

```text
O(n)
```

because `left` and `right` each move only forward.

This is the same amortized sliding-window idea seen in previous problems.

---

## Comparison With WIN #54

The previous problem was:

```text
Longest Substring Without Repeating Characters
```

There we used:

```text
HashSet
```

because the rule was:

```text
No duplicate characters
```

Here we use:

```text
Frequency Array
```

because we need to know:

```text
How many times does each character appear?
```

The common pattern is still:

```text
Variable-Size Sliding Window
```

The difference is the condition that determines whether the window is valid.

---

## Visual Summary

For:

```text
s = "AABABBA"
k = 1
```

Consider:

```text
A A B A
```

Frequency:

```text
A → 3
B → 1
```

Therefore:

```text
window size = 4
maxFrequency = 3

replacements = 4 - 3
             = 1
```

Since:

```text
1 <= k
```

the window is valid.

Now:

```text
A A B A B
```

Frequency:

```text
A → 3
B → 2
```

Therefore:

```text
window size = 5
maxFrequency = 3

replacements = 5 - 3
             = 2
```

But:

```text
2 > k
```

so the window must shrink.

---

## Final Takeaway

The entire solution can be remembered as:

```text
Create frequency array
        ↓
Expand right
        ↓
Update frequency
        ↓
Track maximum frequency
        ↓
Calculate:

window size - maxFrequency

        ↓
Is it > k?
   ↓          ↓
 YES          NO
  ↓            ↓
Shrink       Valid window
left         ↓
  ↓       Update answer
Repeat
```

The main pattern to remember is:

> **For a longest-substring problem where you are allowed to modify at most `k` characters, use a variable-size sliding window and track the most frequent character. The number of replacements needed is `window length - maxFrequency`.**

This gives an **O(n) time, O(1) space** solution.
