# WIN #64 — Valid Parentheses

## Problem

[LeetCode 20 — Valid Parentheses](https://leetcode.com/problems/valid-parentheses/)

Given a string `s` containing only:

```text
( ) { } [ ]
```

determine whether the string is valid.

A string is valid when:

1. Every opening bracket is closed by the same type of bracket.
2. Brackets are closed in the correct order.
3. Every closing bracket has a corresponding opening bracket.

---

# Examples

### Example 1

```text
Input:
s = "()"

Output:
true
```

The opening `(` is correctly closed by `)`.

---

### Example 2

```text
Input:
s = "()[]{}"

Output:
true
```

Every opening bracket has the correct closing bracket.

---

### Example 3

```text
Input:
s = "(]"

Output:
false
```

`(` must be closed by `)`, not `]`.

---

### Example 4

```text
Input:
s = "([])"

Output:
true
```

The brackets are correctly nested:

```text
(
    [
    ]
)
```

---

### Example 5

```text
Input:
s = "([)]"

Output:
false
```

Although all brackets have matching types, they are closed in the wrong order.

---

# My Java Solution

```java
class Solution {
    public boolean isValid(String s) {

        Stack<Character> stack = new Stack<>();

        for(char c : s.toCharArray()){

            if(c == '('){
                stack.push(')');
            }
            else if(c == '{'){
                stack.push('}');
            }
            else if(c == '['){
                stack.push(']');
            }
            else{
                if(stack.isEmpty() || stack.pop() != c){
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
```

---

# Core Idea

The key idea is to use a **Stack**.

Brackets follow a **Last In, First Out (LIFO)** pattern.

For example:

```text
([ ])
```

When we encounter:

```text
(
```

we need to remember that the next required closing bracket is:

```text
)
```

Then we encounter:

```text
[
```

Now `[` is the most recently opened bracket, so it must be closed first.

Therefore:

```text
(
[
]
)
```

This is exactly what a Stack is designed for.

---

# The Important Trick

Instead of pushing the opening bracket itself:

```java
stack.push(c);
```

the solution pushes the **expected closing bracket**.

For example:

```java
if(c == '('){
    stack.push(')');
}
```

So:

```text
Input: (
Stack: )
```

For:

```java
else if(c == '{'){
    stack.push('}');
}
```

we get:

```text
Input: {
Stack: }
```

And:

```java
else if(c == '['){
    stack.push(']');
}
```

gives:

```text
Input: [
Stack: ]
```

This makes the closing-bracket check extremely simple.

---

# Step 1 — Create the Stack

```java
Stack<Character> stack = new Stack<>();
```

The Stack stores the closing brackets that we expect to encounter.

For example, after processing:

```text
([{
```

the Stack contains:

```text
)
]
}
```

The top of the Stack is:

```text
}
```

because `{` was opened most recently.

---

# Step 2 — Traverse the String

```java
for(char c : s.toCharArray()){
```

We process every character from left to right.

For example:

```text
s = "([])"
```

Processing order:

```text
(
[
]
)
```

---

# Step 3 — Opening Bracket

When we see:

```text
(
```

we push:

```text
)
```

```java
if(c == '('){
    stack.push(')');
}
```

Similarly:

```java
else if(c == '{'){
    stack.push('}');
}
```

and:

```java
else if(c == '['){
    stack.push(']');
}
```

The Stack now represents what closing bracket we expect next.

---

# Step 4 — Closing Bracket

If the character is not an opening bracket, it must be a closing bracket.

```java
else{
    if(stack.isEmpty() || stack.pop() != c){
        return false;
    }
}
```

There are two conditions that can make the string invalid.

### Case 1 — Stack is Empty

```java
stack.isEmpty()
```

Suppose:

```text
s = ")"
```

There is no opening bracket before `)`.

Therefore:

```text
Stack = empty
Current = )
```

The string is invalid.

---

### Case 2 — Wrong Closing Bracket

Suppose:

```text
s = "(]"
```

After processing `(`:

```text
Stack:
)
```

Now we encounter:

```text
]
```

The Stack expects:

```text
)
```

but we received:

```text
]
```

Therefore:

```java
stack.pop() != c
```

is true, and we return:

```text
false
```

---

# Why `pop()` Is Important

Consider:

```text
s = "([{}])"
```

Let's process it step by step.

### Read `(`

Push:

```text
)
```

Stack:

```text
)
```

---

### Read `[`

Push:

```text
]
```

Stack:

```text
)
]
```

Top:

```text
]
```

---

### Read `{`

Push:

```text
}
```

Stack:

```text
)
]
}
```

Top:

```text
}
```

---

### Read `}`

Expected:

```text
}
```

We pop:

```text
}
```

Match.

Stack:

```text
)
]
```

---

### Read `]`

Expected:

```text
]
```

We pop:

```text
]
```

Match.

Stack:

```text
)
```

---

### Read `)`

Expected:

```text
)
```

We pop:

```text
)
```

Match.

Stack:

```text
empty
```

Therefore:

```java
return stack.isEmpty();
```

returns:

```text
true
```

---

# Why Do We Check `stack.isEmpty()` at the End?

Consider:

```text
s = "((("
```

Every character is an opening bracket.

Processing gives:

```text
(
(
(
```

Stack:

```text
)
)
)
```

There are still unmatched opening brackets.

Therefore the string is invalid.

This is why we return:

```java
return stack.isEmpty();
```

If the Stack is empty:

```text
all brackets were matched
```

If the Stack is not empty:

```text
some opening brackets were never closed
```

---

# Why This Handles Ordering

Consider:

```text
s = "([)]"
```

Let's process it.

### `(`

Expected:

```text
)
```

Stack:

```text
)
```

### `[`

Expected:

```text
]
```

Stack:

```text
)
]
```

### `)`

The Stack expects:

```text
]
```

but the current character is:

```text
)
```

Mismatch.

Therefore:

```text
false
```

This is important because simply checking whether every opening bracket has a matching closing bracket would not be enough.

The **Stack maintains the correct nesting order**.

---

# Stack Visualization

For:

```text
s = "{[()]}"
```

The Stack changes like this:

```text
Read {    → push }
Read [    → push ]
Read (    → push )
Read )    → pop )
Read ]    → pop ]
Read }    → pop }
```

Final Stack:

```text
empty
```

Therefore:

```text
true
```

---

# Why Stack Is the Right Data Structure

The problem requires the **most recently opened bracket** to be closed first.

For example:

```text
(( ))
```

The second `(` must be closed before the first `(`.

This is:

```text
Last In → First Out
```

which is exactly:

```text
LIFO
```

And the data structure that provides LIFO behavior is a:

```text
Stack
```

---

# Pattern Recognition

Whenever a problem involves:

```text
Nested brackets
Nested expressions
Undo operations
Matching pairs
Most recent unmatched element
```

think:

```text
Stack
```

For this problem:

```text
Opening bracket
       ↓
Push expected closing bracket
       ↓
Closing bracket
       ↓
Compare with Stack top
       ↓
Match → pop
Mismatch → false
       ↓
End of string
       ↓
Stack empty?
       ↓
true / false
```

---

# Why Push the Closing Bracket Instead of Opening Bracket?

A common implementation is:

```java
stack.push(c);
```

and later checking:

```text
)
matches (
]
matches [
}
matches {
```

But this solution uses a simpler approach.

When we see:

```text
(
```

we immediately know:

```text
)
```

is required.

So we store:

```text
)
```

in the Stack.

Then when we encounter a closing bracket, we can simply do:

```java
stack.pop() != c
```

Instead of writing multiple matching conditions.

This makes the code shorter and easier to reason about.

---

# Complexity

Let:

```text
n = s.length()
```

We process every character exactly once.

For every character we perform Stack operations:

```text
push → O(1)
pop  → O(1)
isEmpty → O(1)
```

Therefore:

```text
Time Complexity: O(n)
```

---

# Space Complexity

In the worst case, every character can be an opening bracket.

For example:

```text
(((((((((
```

The Stack could contain `n` elements.

Therefore:

```text
Space Complexity: O(n)
```

---

# Edge Cases

### Empty Stack + Closing Bracket

```text
")"
```

Result:

```text
false
```

---

### Unclosed Opening Bracket

```text
"("
```

Result:

```text
false
```

because the Stack is not empty at the end.

---

### Correct Nesting

```text
"([])"
```

Result:

```text
true
```

---

### Incorrect Nesting

```text
"([)]"
```

Result:

```text
false
```

---

### Multiple Correct Pairs

```text
"()[]{}"
```

Result:

```text
true
```

---

# The Key Insight

The most important thing to remember is:

> **For every opening bracket, push the closing bracket that must appear next. For every closing bracket, compare it with the top of the Stack.**

The complete thought process is:

```text
Opening bracket
      ↓
Push expected closing bracket

Closing bracket
      ↓
Is Stack empty?
      ↓
Yes → false
No
      ↓
Does top match current bracket?
      ↓
No → false
Yes
      ↓
Pop

End
 ↓
Is Stack empty?
 ↓
Yes → true
No  → false
```

---

# Final Takeaway

This is one of the fundamental **Stack pattern** problems.

The three things to remember are:

```text
1. Opening brackets → push their expected closing bracket.

2. Closing brackets → compare with Stack top and pop.

3. At the end → Stack must be empty.
```

The final pattern is:

```text
Input
 ↓
Traverse characters
 ↓
Opening bracket?
 ├── Yes → Push expected closing bracket
 │
 └── No  → Check Stack + compare + pop
                    ↓
                 mismatch?
                    ↓
                   false

End
 ↓
Stack empty?
 ├── Yes → true
 └── No  → false
```

**Time:** `O(n)`
**Space:** `O(n)`
**Pattern:** `Stack / LIFO / Matching Pairs`
