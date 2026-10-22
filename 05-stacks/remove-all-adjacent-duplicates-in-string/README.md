# WIN #101 — 1047. Remove All Adjacent Duplicates In String

**LeetCode:** [1047. Remove All Adjacent Duplicates In String](https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string/)  
**Difficulty:** Easy  
**Topics:** String, Stack

---

## 1. Problem

You are given a string `s` consisting of lowercase English letters.

Whenever two adjacent characters are equal, remove both characters. Continue removing adjacent duplicates until no more removals are possible.

Return the final string.

### Example 1

**Input:**

```text
s = "abbaca"
```

**Output:**

```text
"ca"
```

**Explanation:**

First, remove `"bb"`:

```text
abbaca -> aaca
```

Then remove `"aa"`:

```text
aaca -> ca
```

No adjacent duplicates remain, so the answer is `"ca"`.

### Example 2

**Input:**

```text
s = "azxxzy"
```

**Output:**

```text
"ay"
```

---

## 2. Core Idea

The key observation is that removing two adjacent characters can create a new pair of adjacent duplicates.

For example:

```text
abbaca
```

Removing `"bb"` creates `"aa"`, which must also be removed.

A **stack** is useful because it keeps track of the characters that remain after processing the string so far.

For every character:

1. If the stack is empty, push the character.
2. If the top character equals the current character, pop the top character. Both adjacent duplicates are removed.
3. Otherwise, push the current character.

After processing all characters, the stack contains the final string in its original order from bottom to top.

My solution uses a `StringBuilder` to collect the stack's characters by popping them, then reverses the result to restore the correct order.

---

## 3. Java Solution

```java
class Solution {
    public String removeDuplicates(String s) {

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {
            if (!stack.isEmpty() && stack.peek() == c) {
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        StringBuilder sb = new StringBuilder();

        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.reverse().toString();
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Create the stack

```java
Stack<Character> stack = new Stack<>();
```

The stack stores characters that have not been removed.

The top of the stack represents the most recently processed character that remains in the result.

### Step 2: Process every character

```java
for (char c : s.toCharArray()) {
```

We iterate through the input string one character at a time.

For each character, we compare it with the top of the stack.

### Step 3: Check for adjacent duplicates

```java
if (!stack.isEmpty() && stack.peek() == c) {
    stack.pop();
}
```

There are two important operations here:

- `stack.isEmpty()` ensures we do not try to access the top of an empty stack.
- `stack.peek()` checks the top character without removing it.

If the top character equals the current character, the two characters are adjacent and equal in the remaining string. We pop the top character, and the current character is effectively discarded as well.

For example:

```text
Stack before: [a, b]
Current char: b

Top equals current character, so pop.

Stack after: [a]
```

Both `b` characters have been removed.

### Step 4: Push a character when no duplicate exists

```java
else {
    stack.push(c);
}
```

If the stack is empty or the top character differs from the current character, the current character cannot be removed at this moment.

We push it onto the stack.

### Step 5: Build the final string

```java
StringBuilder sb = new StringBuilder();

while (!stack.isEmpty()) {
    sb.append(stack.pop());
}
```

Popping elements returns them in reverse order.

For example, if the stack contains:

```text
Bottom -> c, a <- Top
```

Popping produces:

```text
a, c
```

Therefore, we reverse the `StringBuilder` before returning it.

### Step 6: Reverse and return

```java
return sb.reverse().toString();
```

Reversing restores the original order of the characters that remain.

---

## 5. Dry Run

Consider:

```text
s = "abbaca"
```

| Step | Current character | Action | Stack, bottom to top |
|---:|---|---|---|
| 1 | `a` | Push | `a` |
| 2 | `b` | Push | `a, b` |
| 3 | `b` | Top matches; pop | `a` |
| 4 | `a` | Top matches; pop | Empty |
| 5 | `c` | Push | `c` |
| 6 | `a` | Push | `c, a` |

After processing the entire string:

```text
Stack: [c, a]
```

Popping the stack produces `"ac"`.

Reverse it:

```text
"ac" -> "ca"
```

**Final output:**

```text
"ca"
```

The important detail is step 4: after removing `"bb"`, the remaining `a` becomes adjacent to the next `a`. The stack detects and removes this new duplicate automatically.

---

## 6. Edge Cases

| Case | Input | Output |
|---|---|---|
| Two identical characters | `"aa"` | `""` |
| No duplicates | `"abcd"` | `"abcd"` |
| All characters identical | `"aaaa"` | `""` |
| Duplicates create another pair | `"abbaca"` | `"ca"` |
| Duplicates separated by other characters | `"azxxzy"` | `"ay"` |
| Single character | `"a"` | `"a"` |

---

## 7. Complexity Analysis

Let \(n\) be the length of the input string.

**Time Complexity: O(n)**

- Converting the string to a character array takes O(n).
- Each character is pushed onto the stack at most once.
- Each character is popped at most once.
- Building and reversing the result takes O(n).

Therefore, the total time complexity is O(n).

**Auxiliary Space Complexity: O(n)**

In the worst case, no adjacent duplicates exist, so the stack stores all \(n\) characters. The `StringBuilder` also uses up to O(n) space for the result.

The overall additional space complexity is O(n).

---

## 8. Pattern Recognition

This problem demonstrates the **stack-based cancellation pattern**.

Use this pattern when:

- Processing elements from left to right.
- The current element may cancel the most recently retained element.
- Removing elements can create new adjacent pairs.
- You need to maintain the result after repeated local removals.

### Why does the stack work?

The stack preserves the reduced result of the prefix processed so far.

When a new character arrives, only the last retained character can form a new adjacent pair with it. The stack's top gives us that character in O(1) time.

If they match, removing the top handles the cancellation. Otherwise, pushing the new character extends the result.

This avoids repeatedly deleting characters from the middle of a string.

---

## 9. Stack Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Valid Parentheses | Matching pairs with a stack |
| Min Stack | Stack with auxiliary minimum tracking |
| Evaluate Reverse Polish Notation | Operand stack |
| Daily Temperatures | Monotonic decreasing stack |
| Next Greater Element | Monotonic stack |
| Next Greater Node In Linked List | Monotonic stack with indices |
| Remove All Adjacent Duplicates In String | Stack-based cancellation |

---

## 10. Final Mental Model

Imagine building the answer one character at a time.

```text
For each character:
    If the stack is empty:
        Push the character
    Else if the top equals the character:
        Pop the top
    Else:
        Push the character
```

For `"abbaca"`:

```text
a -> ab -> a -> empty -> c -> ca
```

The stack automatically handles duplicates that become adjacent after earlier removals.

**Key takeaway:** When a new character matches the top of the stack, remove the top instead of pushing the new character. This cancels the adjacent pair and lets the stack maintain the final reduced string in O(n) time.