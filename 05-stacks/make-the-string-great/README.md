# WIN #103 — 1544. Make The String Great

**LeetCode:** [1544. Make The String Great](https://leetcode.com/problems/make-the-string-great/)  
**Difficulty:** Easy  
**Topics:** String, Stack

---

## 1. Problem

You are given a string `s` containing uppercase and lowercase English letters.

A string is considered good if it does not contain two adjacent characters that are the same letter but have different cases.

For example:

- `"aA"` is a bad pair.
- `"Bb"` is a bad pair.
- `"ab"` is a good pair.
- `"AA"` is a good pair because both characters have the same case.

You can repeatedly remove adjacent bad pairs until the string becomes good.

Return the final string. An empty string is also considered good.

### Example 1

**Input:**

```text
s = "leEeetcode"
```

**Output:**

```text
"leetcode"
```

The adjacent characters `E` and `e` form a bad pair, so removing them produces `"leetcode"`.

### Example 2

**Input:**

```text
s = "abBAcC"
```

**Output:**

```text
""
```

One possible sequence of removals is:

```text
abBAcC
  |
  v
aAcC
  |
  v
cC
  |
  v
""
```

### Example 3

**Input:**

```text
s = "s"
```

**Output:**

```text
"s"
```

A single character cannot form a bad pair.

---

## 2. Core Idea

The key observation is that removing a bad pair can make two previously separated characters adjacent. Those characters may form another bad pair.

A **stack** helps us handle these newly adjacent characters automatically.

For every character in the input:

1. If the stack is empty, push the character.
2. Otherwise, inspect the top character.
3. If the top and current characters represent the same letter but have different cases, pop the top character and discard the current character.
4. Otherwise, push the current character.

After processing the entire string, the stack contains the characters of the final good string in their original order from bottom to top.

My solution pops all characters into a `StringBuilder` and reverses the result to restore the original order.

---

## 3. Java Solution

```java
class Solution {
    public String makeGood(String s) {

        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {

            if (stack.isEmpty()) {
                stack.push(c);
                continue;
            }

            char ch = stack.peek();

            if (Character.toLowerCase(ch) == Character.toLowerCase(c)
                    && ch != c) {
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

### Step 1: Initialize the stack

```java
Stack<Character> stack = new Stack<>();
```

The stack stores characters that have not been removed.

The top of the stack represents the last character retained from the processed portion of the string.

### Step 2: Process each character

```java
for (char c : s.toCharArray()) {
```

We process the input string from left to right, one character at a time.

### Step 3: Handle an empty stack

```java
if (stack.isEmpty()) {
    stack.push(c);
    continue;
}
```

If the stack is empty, there is no previous retained character to compare with.

We push the current character and continue to the next iteration.

### Step 4: Inspect the previous retained character

```java
char ch = stack.peek();
```

`peek()` retrieves the top character without removing it.

We compare this character with the current character to determine whether they form a bad pair.

### Step 5: Detect a bad pair

```java
if (Character.toLowerCase(ch) == Character.toLowerCase(c)
        && ch != c) {
    stack.pop();
}
```

The condition has two parts.

**First:**

```java
Character.toLowerCase(ch) == Character.toLowerCase(c)
```

This checks whether both characters represent the same letter, ignoring case.

For example, `'a'` and `'A'` both become `'a'` after conversion.

**Second:**

```java
ch != c
```

This ensures that the original characters differ.

Together, the conditions identify the same letter in opposite cases.

When a bad pair is found, `stack.pop()` removes the previous character. The current character is not pushed, so both characters disappear from the result.

### Step 6: Keep characters that do not form a bad pair

```java
else {
    stack.push(c);
}
```

If the characters are different letters or have the same case, they do not form a bad pair.

We push the current character onto the stack.

### Step 7: Build the final string

```java
StringBuilder sb = new StringBuilder();

while (!stack.isEmpty()) {
    sb.append(stack.pop());
}
```

Popping the stack returns the characters in reverse order.

For example:

```text
Stack, bottom to top: [l, e, t, c, o, d, e]
Popped order:          e, d, o, c, t, e, l
```

### Step 8: Restore the original order

```java
return sb.reverse().toString();
```

Reversing the collected characters restores their original order and produces the final good string.

---

## 5. Dry Run

Consider:

```text
s = "leEeetcode"
```

| Step | Current character | Action | Stack, bottom to top |
|---:|---|---|---|
| 1 | `l` | Push | `l` |
| 2 | `e` | Push | `l, e` |
| 3 | `E` | Same letter, different case; pop `e` | `l` |
| 4 | `e` | Push | `l, e` |
| 5 | `e` | Same case; push | `l, e, e` |
| 6 | `t` | Push | `l, e, e, t` |
| 7 | `c` | Push | `l, e, e, t, c` |
| 8 | `o` | Push | `l, e, e, t, c, o` |
| 9 | `d` | Push | `l, e, e, t, c, o, d` |
| 10 | `e` | Push | `l, e, e, t, c, o, d, e` |

The stack contains:

```text
l e e t c o d e
```

After popping and reversing, the result is:

```text
"leetcode"
```

The uppercase `E` and the lowercase `e` that followed it were removed. The two lowercase `e` characters later in the string remain because they have the same case.

---

## 6. Edge Cases

| Case | Input | Output |
|---|---|---|
| One character | `"s"` | `"s"` |
| One bad pair | `"aA"` | `""` |
| Same letter and same case | `"aa"` | `"aa"` |
| No bad pairs | `"abc"` | `"abc"` |
| Multiple cascading removals | `"abBAcC"` | `""` |
| Bad pair near the beginning | `"aAbc"` | `"bc"` |
| Bad pair near the end | `"abCc"` | `"ab"` |

---

## 7. Complexity Analysis

Let \(n\) be the length of the input string.

**Time Complexity: O(n)**

- Converting the string to a character array takes O(n).
- Each character is pushed at most once and popped at most once.
- Building and reversing the result takes O(n).

Therefore, the total time complexity is O(n).

**Auxiliary Space Complexity: O(n)**

In the worst case, no characters are removed, and the stack stores all \(n\) characters. The `StringBuilder` also uses up to O(n) space.

Therefore, the overall additional space complexity is O(n).

---

## 8. Pattern Recognition

This problem demonstrates the **stack-based cancellation pattern**, similar to Remove All Adjacent Duplicates in String.

Use this pattern when:

- Processing characters from left to right.
- A new character can cancel the most recently retained character.
- Removing a pair can create another removable pair.
- You need to maintain the reduced string without repeatedly deleting characters from the middle.

### Difference from Remove All Adjacent Duplicates in String

| Feature | Remove All Adjacent Duplicates | Make The String Great |
|---|---|---|
| Removal condition | Adjacent characters are identical | Same letter, opposite cases |
| Example bad pair | `"aa"` | `"aA"` |
| Stack operation | Pop when characters are equal | Pop when lowercase forms match and original characters differ |
| Main pattern | Stack-based cancellation | Stack-based cancellation |

The overall algorithm remains the same. Only the condition that determines whether two characters cancel changes.

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
| Remove All Adjacent Duplicates In String | Stack-based cancellation of identical characters |
| Baseball Game | Stack-based simulation and score history |
| Make The String Great | Stack-based cancellation using case-insensitive comparison |

---

## 10. Final Mental Model

Think of the stack as the good portion of the string built so far.

For every incoming character:

```text
If the stack is empty:
    Push the character

Otherwise:
    Compare it with the top character

    If they are the same letter in opposite cases:
        Pop the top and discard the current character
    Else:
        Push the current character
```

For example:

```text
abBAcC
  |
  v
aA cC
  |
  v
cC
  |
  v
""
```

Removing a bad pair exposes the next pair automatically because the stack's new top is the last character retained before that pair.

**Key takeaway:** Use a stack to maintain the reduced string. When the current character and the stack's top represent the same letter in opposite cases, remove the pair; otherwise, retain the current character. This solves the problem in O(n) time.