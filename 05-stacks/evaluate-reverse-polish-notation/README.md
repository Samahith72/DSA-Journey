# WIN #109 — 150. Evaluate Reverse Polish Notation

**LeetCode:** [150. Evaluate Reverse Polish Notation](https://leetcode.com/problems/evaluate-reverse-polish-notation/)  
**Difficulty:** Medium  
**Topic:** Stack, String, Math

---

## Problem Statement

You are given an array of strings called `tokens`, representing an arithmetic expression written in Reverse Polish Notation (RPN).

Your task is to evaluate the expression and return its integer result.

The valid operators are:

- `+` — Addition
- `-` — Subtraction
- `*` — Multiplication
- `/` — Division

Each token is either an integer or an operator. The expression is guaranteed to be valid, and division by zero will not occur.

**Important:** Integer division must truncate toward zero.

### Example 1

**Input:**
```text
tokens = ["2", "1", "+", "3", "*"]
```

**Output:**
```text
9
```

**Explanation:**

The expression is equivalent to:

```text
(2 + 1) * 3 = 9
```

### Example 2

**Input:**
```text
tokens = ["4", "13", "5", "/", "+"]
```

**Output:**
```text
6
```

**Explanation:**

```text
4 + (13 / 5)
= 4 + 2
= 6
```

---

## Core Idea

Reverse Polish Notation places operators **after their operands**.

Unlike a normal mathematical expression, RPN does not require parentheses or operator precedence rules. We can evaluate it from left to right using a stack.

### How the Stack Works

1. If the current token is a number, convert it to an integer and push it onto the stack.
2. If the current token is an operator, pop the top two numbers.
3. Perform the operation using the correct operand order.
4. Push the result back onto the stack.
5. After processing all tokens, the stack contains the final result.

### Why Use a Stack?

A stack stores numbers that are waiting to participate in an operation.

When an operator appears, the two most recently encountered available operands are the ones that must be used.

This follows the **Last In, First Out (LIFO)** principle.

---

## Java Solution

```java
class Solution {
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();

        for (String token : tokens) {
            if (token.equals("+")) {
                int n2 = stack.pop();
                int n1 = stack.pop();
                stack.push(n1 + n2);

            } else if (token.equals("-")) {
                int n2 = stack.pop();
                int n1 = stack.pop();
                stack.push(n1 - n2);

            } else if (token.equals("*")) {
                int n2 = stack.pop();
                int n1 = stack.pop();
                stack.push(n1 * n2);

            } else if (token.equals("/")) {
                int n2 = stack.pop();
                int n1 = stack.pop();
                stack.push(n1 / n2);

            } else {
                int value = Integer.parseInt(token);
                stack.push(value);
            }
        }

        return stack.peek();
    }
}
```

---

## Step-by-Step Explanation

### 1. Initialize the Stack

```java
Stack<Integer> stack = new Stack<>();
```

Create a stack to store operands and intermediate results.

For example, if the expression begins with `"2"` and `"1"`, the stack becomes:

```text
Top -> 1
       2
```

### 2. Process Each Token

```java
for (String token : tokens)
```

Traverse the input array from left to right.

Each token is either a number or one of the four supported operators.

### 3. Handle Numbers

```java
int value = Integer.parseInt(token);
stack.push(value);
```

If the token is not an operator, convert it from a string to an integer and push it onto the stack.

For example:

```text
Token: "2"

Stack:
Top -> 2
```

Negative numbers such as `"-11"` are also handled correctly by `Integer.parseInt()`.

### 4. Handle Operators

For every operator, pop the top two operands:

```java
int n2 = stack.pop();
int n1 = stack.pop();
```

**Operand order is important.**

The first popped value is the right operand, and the second popped value is the left operand.

For subtraction:

```java
stack.push(n1 - n2);
```

For division:

```java
stack.push(n1 / n2);
```

Do not reverse the operands for subtraction or division.

For example, if the stack contains `10` followed by `3`, with `3` on top:

```text
n2 = 3
n1 = 10
```

The subtraction is:

```text
10 - 3 = 7
```

It is not `3 - 10`.

Java's integer division also truncates toward zero, matching the problem's requirement.

### 5. Return the Final Result

```java
return stack.peek();
```

Once all tokens have been processed, the valid expression leaves exactly one value on the stack.

`peek()` returns that value without removing it.

---

## Dry Run

Consider:

```text
tokens = ["2", "1", "+", "3", "*"]
```

| Step | Token | Operation | Stack after operation |
|---|---|---|---|
| 1 | `"2"` | Push `2` | `[2]` |
| 2 | `"1"` | Push `1` | `[2, 1]` |
| 3 | `"+"` | Pop `1` and `2`; push `2 + 1 = 3` | `[3]` |
| 4 | `"3"` | Push `3` | `[3, 3]` |
| 5 | `"*"` | Pop `3` and `3`; push `3 * 3 = 9` | `[9]` |

The stack contains one value:

```text
[9]
```

**Output:** `9`

### Visualizing the Evaluation

The expression:

```text
2 1 + 3 *
```

is evaluated as:

```text
2 + 1 = 3
3 * 3 = 9
```

The result of each operation is pushed back onto the stack and can be used by a later operator.

---

## Edge Cases

| Case | Input | Expected output | Reason |
|---|---|---|---|
| Single number | `["42"]` | `42` | The number remains on the stack. |
| Addition | `["2", "3", "+"]` | `5` | Adds the two operands. |
| Subtraction | `["10", "3", "-"]` | `7` | Preserves operand order. |
| Multiplication | `["4", "5", "*"]` | `20` | Multiplies the operands. |
| Integer division | `["13", "5", "/"]` | `2` | Integer division truncates toward zero. |
| Negative operand | `["-2", "3", "*"]` | `-6` | Negative numbers are parsed correctly. |
| Negative division result | `["7", "-3", "/"]` | `-2` | Java truncates `-2.333...` toward zero. |

The input is guaranteed to be a valid RPN expression, so the solution does not need to validate the expression structure or handle division by zero.

---

## Complexity Analysis

Let `n` be the number of tokens.

### Time Complexity: O(n)

Every token is processed exactly once.

Each stack operation—`push()`, `pop()`, and `peek()`—takes O(1) time.

Therefore, the total time complexity is **O(n)**.

### Space Complexity: O(n)

In the worst case, the stack may hold most of the input operands before any operators combine them.

Therefore, the auxiliary space complexity is **O(n)**.

---

## Pattern Recognition

**Pattern: Stack-Based Expression Evaluation**

Use this pattern when an expression can be processed by maintaining operands and combining them whenever an operator appears.

Recognize this approach when:

- The input is written in Reverse Polish Notation.
- Operators appear after their operands.
- Each operator combines the two most recent available operands.
- Intermediate results must be reused by later operations.

### General Rule

```text
Number   -> Push onto the stack

Operator -> Pop right operand
            Pop left operand
            Calculate left operator right
            Push the result
```

This rule is the foundation of evaluating Reverse Polish Notation with a stack.

---

## Key Takeaways

1. A stack is well suited to evaluating Reverse Polish Notation because it provides the most recent operands first.
2. Process the tokens from left to right.
3. Push numbers and calculate results whenever an operator appears.
4. **For subtraction and division, preserve the operand order: the second popped number is the left operand.**
5. Java integer division truncates toward zero, as required by the problem.
6. When a valid expression has been fully processed, its result is the only value left on the stack.

**Final Mental Model:** Read the expression from left to right. Store numbers on the stack. Whenever an operator appears, take the top two numbers, calculate the result, and put it back. Continue until one final answer remains.