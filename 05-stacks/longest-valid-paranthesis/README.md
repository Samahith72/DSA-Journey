# WIN #115 — 32. Longest Valid Parentheses

**LeetCode:** [32. Longest Valid Parentheses](https://leetcode.com/problems/longest-valid-parentheses/)  
**Difficulty:** Hard  
**Topics:** String, Dynamic Programming, Stack

---

## 1. Problem Statement

Given a string containing only `'('` and `')'`, find the length of the longest valid (well-formed) parentheses substring.

A valid parentheses substring must have:
- Every opening parenthesis matched with a closing parenthesis.
- Opening and closing parentheses in the correct order.

### Examples

**Example 1**

Input:
```text
s = "(()"
```

Output:
```text
2
```

Explanation: The longest valid substring is `"()"`.

**Example 2**

Input:
```text
s = ")()())"
```

Output:
```text
4
```

Explanation: The longest valid substring is `"()()"`.

**Example 3**

Input:
```text
s = ""
```

Output:
```text
0
```

---

## 2. Core Idea — Stack of Indices

Instead of storing parentheses characters, we store their **indices**.

The stack helps us identify the boundaries of valid parentheses substrings.

The most important initialization is:

```java
stack.push(-1);
```

### Why do we push -1?

The value `-1` acts as a boundary before the beginning of the string.

When a valid substring starts at index `0`, we can calculate its length using:

```text
currentIndex - stack.peek()
```

For example, with `"()"`:

- The closing parenthesis is at index `1`.
- The stack's boundary is `-1`.
- Length = `1 - (-1) = 2`.

Without this initial boundary, calculating valid substrings starting at index `0` would be more complicated.

---

## 3. Java Solution

```java
class Solution {
    public int longestValidParentheses(String s) {

        Stack<Integer> stack = new Stack<>();
        int answer = 0;
        stack.push(-1);

        for(int i=0;i<s.length();i++){
            if(s.charAt(i) == '('){
                stack.push(i);
            }else{
                stack.pop();

                if(stack.isEmpty()){
                    stack.push(i);
                }else{
                    int length = i - stack.peek();
                    answer = Math.max(length, answer);
                }
            }
        }

        return answer;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize the stack

```java
Stack<Integer> stack = new Stack<>();
int answer = 0;
stack.push(-1);
```

- Create a stack to store indices.
- Initialize the maximum valid length to `0`.
- Push `-1` as the initial boundary.

### Step 2: Traverse the string

```java
for(int i=0;i<s.length();i++)
```

Visit every character using its index.

### Step 3: Handle opening parentheses

```java
if(s.charAt(i) == '('){
    stack.push(i);
}
```

Whenever we encounter `'('`, push its index onto the stack.

This index represents an unmatched opening parenthesis.

### Step 4: Handle closing parentheses

```java
else{
    stack.pop();
}
```

When we encounter `')'`, pop the top index.

This attempts to match the closing parenthesis with a previous opening parenthesis.

### Step 5: Handle an empty stack

```java
if(stack.isEmpty()){
    stack.push(i);
}
```

If the stack becomes empty, the current closing parenthesis cannot be matched.

We push its index as the new boundary.

Any valid substring found afterward must begin after this index.

### Step 6: Calculate the valid substring length

```java
else{
    int length = i - stack.peek();
    answer = Math.max(length, answer);
}
```

If the stack is not empty, the top index represents the boundary immediately before the current valid substring.

Calculate the length and update the maximum answer.

---

## 5. Dry Run

Let's trace the input:

```text
s = ")()())"
```

Initial state:

```text
Stack  = [-1]
Answer = 0
```

| Index | Character | Operation | Stack | Answer |
|---|---|---|---|---|
| 0 | `)` | Pop; stack empty, push 0 | `[0]` | 0 |
| 1 | `(` | Push index 1 | `[0, 1]` | 0 |
| 2 | `)` | Pop; length = 2 - 0 | `[0]` | 2 |
| 3 | `(` | Push index 3 | `[0, 3]` | 2 |
| 4 | `)` | Pop; length = 4 - 0 | `[0]` | 4 |
| 5 | `)` | Pop; stack empty, push 5 | `[5]` | 4 |

Final answer:

```text
4
```

The longest valid substring is:

```text
()()
```

---

## 6. Important Edge Cases

| Input | Output | Reason |
|---|---:|---|
| `""` | 0 | Empty string |
| `"()"` | 2 | One valid pair |
| `"(()"` | 2 | One unmatched opening parenthesis |
| `")()())"` | 4 | Longest valid substring is `"()()"` |
| `"((()))"` | 6 | Fully nested valid parentheses |
| `"((((("` | 0 | No closing parentheses |
| `")))))"` | 0 | No opening parentheses |

---

## 7. Complexity Analysis

Let `n` be the length of the input string.

**Time Complexity: O(n)**

- The string is traversed once.
- Each index is pushed onto the stack at most once.
- Each index is popped at most once.

Therefore, the overall time complexity is O(n).

**Space Complexity: O(n)**

In the worst case, the stack can contain indices for all opening parentheses.

Therefore, auxiliary space complexity is O(n).

---

## 8. Pattern Recognition

**Pattern: Stack of Indices + Boundary Tracking**

This approach is useful when:
- You need to find the longest valid substring.
- The validity of a substring depends on matching opening and closing symbols.
- You need to calculate substring lengths using positions.
- Invalid characters or unmatched parentheses create new boundaries.

The important distinction is that the stack stores **indices rather than characters**. This allows us to calculate the length of a valid substring directly.

---

## 9. Final Mental Model

Think of the stack as maintaining the boundary of the current valid parentheses region.

- `'('` → Store its index because it is waiting to be matched.
- `')'` → Remove the previous unmatched opening index.
- Empty stack → The current closing parenthesis becomes a new invalid boundary.
- Non-empty stack → Calculate the valid length using the current index and the top boundary.

The key formula is:

```text
Valid Length = Current Index - Stack Top
```

**The main takeaway:** The stack does not directly store valid substrings. It stores unmatched opening positions and boundary indices, allowing us to calculate the longest valid substring efficiently in one traversal.