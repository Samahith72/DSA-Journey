# WIN #117 — 224. Basic Calculator

**LeetCode:** [224. Basic Calculator](https://leetcode.com/problems/basic-calculator/)  
**Difficulty:** Hard  
**Topics:** String, Stack, Math

---

## 1. Problem Statement

Given a string `s` representing a valid mathematical expression, implement a basic calculator to evaluate it and return the result.

The expression can contain:
- Non-negative integers
- `+` and `-` operators
- Parentheses `(` and `)`
- Spaces

Division and multiplication operators are not included.

### Examples

**Example 1**

Input:
```text
s = "1 + 1"
```

Output:
```text
2
```

**Example 2**

Input:
```text
s = " 2-1 + 2 "
```

Output:
```text
3
```

**Example 3**

Input:
```text
s = "(1+(4+5+2)-3)+(6+8)"
```

Output:
```text
23
```

---

## 2. Core Idea — Stack + Sign Tracking

Instead of directly evaluating the entire expression, we process it from left to right.

We maintain four important variables:

| Variable | Purpose |
|---|---|
| `result` | Stores the calculated result so far |
| `number` | Builds the current number |
| `sign` | Tracks whether the current number is positive or negative |
| `stack` | Stores the previous result and sign when entering parentheses |

### Understanding the sign

We use:

```java
int sign = 1;
```

- `1` means positive.
- `-1` means negative.

Whenever we encounter `+` or `-`, we first add the previous number to the result using its current sign.

Then we reset the number and update the sign.

### Why do we need a stack?

Parentheses create a separate expression that must be evaluated before being combined with the expression outside them.

For example:

```text
2 - (3 + 4)
```

When we encounter `(`, we save two things:

1. The result calculated before entering the parentheses.
2. The sign that applies to the entire parenthesized expression.

After saving them, we reset the current result and start evaluating the expression inside the parentheses.

When we encounter `)`, we combine the inner result with the saved sign and previous result.

---

## 3. Java Solution

```java
class Solution {
    public int calculate(String s) {

        Stack<Integer> stack = new Stack<>();

        int result = 0;
        int number = 0;
        int sign = 1;

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);

            if (Character.isDigit(ch)) {
                number = number * 10 + (ch - '0');
            }

            else if (ch == '+') {
                result += number * sign;
                number = 0;
                sign = 1;
            }

            else if (ch == '-') {
                result += number * sign;
                number = 0;
                sign = -1;
            }

            else if (ch == '(') {
                stack.push(result);
                stack.push(sign);

                result = 0;
                sign = 1;
            }

            else if (ch == ')') {
                result += number * sign;
                number = 0;

                result *= stack.pop();
                result += stack.pop();
            }
        }

        result += number * sign;

        return result;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Initialize variables

```java
Stack<Integer> stack = new Stack<>();

int result = 0;
int number = 0;
int sign = 1;
```

- `result` stores the evaluated expression so far.
- `number` builds multi-digit numbers.
- `sign` tracks the current number's sign.
- `stack` remembers the outer expression context.

### Step 2: Build multi-digit numbers

```java
if (Character.isDigit(ch)) {
    number = number * 10 + (ch - '0');
}
```

We use:

```text
number = number * 10 + digit
```

For example, consider the number `123`.

| Character | Calculation | Number |
|---|---|---:|
| `1` | 0 × 10 + 1 | 1 |
| `2` | 1 × 10 + 2 | 12 |
| `3` | 12 × 10 + 3 | 123 |

The expression `ch - '0'` converts a numeric character into its integer value.

### Step 3: Handle addition

```java
else if (ch == '+') {
    result += number * sign;
    number = 0;
    sign = 1;
}
```

When we encounter `+`:

1. Add the previous number to the result using its sign.
2. Reset `number`.
3. Set the next