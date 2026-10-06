# WIN #83 — 921. Minimum Add to Make Parentheses Valid

[LeetCode — 921. Minimum Add to Make Parentheses Valid](https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/)

**Difficulty:** Medium
**Topic:** Stack, Greedy, Parentheses

---

## Problem

You are given a string `s` containing only:

```text id="w2c4qk"
'('
')'
```

A parentheses string is valid when every opening parenthesis has a matching closing parenthesis and the parentheses are properly ordered.

In one move, you can insert either:

```text id="6k5j3m"
(
```

or:

```text id="h4q8zn"
)
```

at any position.

Return the **minimum number of insertions** required to make the string valid.

### Example 1

```text id="x5r1mw"
Input:
s = "())"

Output:
1
```

The string has one unmatched closing parenthesis:

```text id="p2z7qx"
( ) )
    ↑
 unmatched
```

We need to insert one `(`:

```text id="g8c3ty"
( ( ) )
```

So the answer is:

```text id="2v5h9n"
1
```

### Example 2

```text id="k3n8yd"
Input:
s = "((("

Output:
3
```

There are three unmatched opening parentheses.

We need three closing parentheses:

```text id="6r4x1p"
( ( ( ) ) )
```

Therefore:

```text id="j9w2hf"
3
```

---

# Core Idea

We don't actually need a stack.

We only need to keep track of:

```text id="d7q5mn"
open
answer
```

### `open`

`open` represents the number of currently unmatched opening parentheses.

When we see:

```text id="8h3fqa"
(
```

we increase `open`.

When we see:

```text id="s2v9kc"
)
```

there are two possibilities.

### Case 1: An opening parenthesis is available

If:

```text id="t6r4yp"
open > 0
```

then the closing parenthesis can match one of the previous opening parentheses.

So:

```text id="w8k2cs"
open--
```

### Case 2: No opening parenthesis is available

If:

```text id="a4p7zn"
open == 0
```

then the current `)` has nothing to match.

We must insert an opening parenthesis before it.

Therefore:

```text id="q9m3vd"
answer++
```

At the end, if `open > 0`, those opening parentheses still need closing parentheses.

So:

```text id="r5x8kp"
answer += open;
```

---

# Java Solution

```java id="g2t6bn"
class Solution {
    public int minAddToMakeValid(String s) {
        int open = 0;
        int answer = 0;

        for(char c : s.toCharArray()){
            if(c == '('){
                open++;
            }else{
                if(open > 0){
                    open--;
                }else{
                    answer++;
                }
            }
        }

        answer += open;

        return answer;
    }
}
```

---

# Step-by-Step Explanation

## 1. Initialize the counters

```java id="p8w4yc"
int open = 0;
int answer = 0;
```

`open` tells us how many unmatched `(` currently exist.

`answer` counts how many insertions we need.

Initially:

```text id="4y7k2m"
open = 0
answer = 0
```

---

# 2. Process every character

```java id="q5n9vx"
for(char c : s.toCharArray())
```

We examine the parentheses from left to right.

This is important because parentheses are order-sensitive.

---

# 3. When we see `(`

```java id="a6c2jp"
if(c == '('){
    open++;
}
```

An opening parenthesis creates one unmatched opening bracket.

For example:

```text id="v7m4qx"
(
```

becomes:

```text id="5c8n2z"
open = 1
```

Another `(`:

```text id="f2j8wk"
( (
```

gives:

```text id="9r3y6p"
open = 2
```

---

# 4. When we see `)`

```java id="w3h7kd"
else{
    if(open > 0){
        open--;
    }else{
        answer++;
    }
}
```

There are two situations.

---

## Case 1: `open > 0`

Suppose:

```text id="8c2m5v"
( )
```

When we encounter `)`:

```text id="b4q7yx"
open = 1
```

There is an available `(` to match it.

So:

```java id="v6n3qa"
open--;
```

Now:

```text id="j2x8km"
open = 0
```

The pair is balanced.

---

## Case 2: `open == 0`

Suppose:

```text id="t7m2qp"
)
```

There is no previous `(`.

So this closing parenthesis cannot be matched.

We need to insert an opening parenthesis:

```text id="z5k9wr"
( )
```

Therefore:

```java id="n4c8vb"
answer++;
```

This counts the required insertion.

---

# Example Walkthrough

Consider:

```text id="e6s2hk"
s = "())"
```

Start:

```text id="0b3w7p"
open = 0
answer = 0
```

### Character 1: `(`

```text id="q9v5mt"
open = 1
answer = 0
```

### Character 2: `)`

There is an opening parenthesis available:

```text id="k7p2fd"
open = 0
answer = 0
```

### Character 3: `)`

There is no opening parenthesis available.

So we need one insertion:

```text id="c3m8yx"
open = 0
answer = 1
```

At the end:

```text id="d5q7vn"
answer += open
```

Therefore:

```text id="r8k2zc"
answer = 1
```

Final answer:

```text id="j4p6ws"
1
```

---

# Example: `"((("`

Start:

```text id="v7x3qa"
open = 0
answer = 0
```

Process the first `(`:

```text id="w5m8kp"
open = 1
```

Second `(`:

```text id="q2r6yc"
open = 2
```

Third `(`:

```text id="n9t4vb"
open = 3
```

There are no closing parentheses.

So:

```text id="a6k3mz"
open = 3
```

These three opening parentheses still need three closing parentheses.

Therefore:

```java id="f8q2xd"
answer += open;
```

gives:

```text id="m3w7yp"
answer = 3
```

---

# Why Do We Add `open` at the End?

This is one of the most important parts of the solution.

Suppose:

```text id="h6q2vk"
s = "((()"
```

Process:

```text id="p4x8rn"
( → open = 1
( → open = 2
( → open = 3
) → open = 2
```

At the end:

```text id="s7m3yc"
open = 2
```

That means there are two unmatched opening parentheses:

```text id="w8q5kd"
( ( ( )
↑ ↑
unmatched
```

We need two `)`:

```text id="k4n9vx"
( ( ( ) ) )
```

So:

```java id="r6y2qp"
answer += open;
```

adds the required two insertions.

---

# Why Don't We Need a Stack?

A traditional parentheses validation problem often uses a stack.

But here, we don't need to know **which specific opening parenthesis** matches a closing parenthesis.

All opening parentheses are identical:

```text
(
```

We only care about the number of unmatched opening parentheses.

Therefore:

```text id="m5q8cz"
Stack<Character>
```

can be replaced with:

```text id="j3x7vp"
int open
```

This reduces the space complexity from `O(n)` to `O(1)`.

---

# Greedy Interpretation

This solution is also a **greedy approach**.

When we encounter:

```text id="2r7m5x"
)
```

and there is no unmatched `(`, we immediately know that an insertion is unavoidable.

There is no future character that can come before this `)` and match it.

Therefore:

```text id="q6v9kn"
if(open == 0)
    answer++;
```

is the optimal choice.

Similarly, after processing the entire string, every remaining unmatched `(` requires exactly one `)`.

So:

```text id="a8c4yt"
answer += open;
```

is also unavoidable.

---

# Visual Mental Model

Think of `open` as a balance counter.

```text
'('  → balance + 1
')'  → balance - 1
```

But the balance can never logically become negative.

If a `)` would make it negative:

```text id="p3x7qm"
balance = 0
next = ')'
```

we need to insert:

```text id="n8k4vz"
(
```

instead.

So:

```text id="y5m2rc"
open = unmatched '('
answer = unmatched ')'
```

At the end:

```text id="q7v3xp"
total insertions
=
unmatched ')'
+
unmatched '('
```

or:

```text id="c9m5zk"
answer + open
```

---

# Edge Cases

## 1. Already valid

```text id="v6q2mx"
s = "()"
```

Processing:

```text id="r8k4yp"
( → open = 1
) → open = 0
```

Result:

```text id="j5n7vc"
answer = 0
```

No insertion is needed.

---

## 2. Only opening parentheses

```text id="a3x8qm"
s = "((("
```

All three remain unmatched:

```text id="w7k2pn"
open = 3
```

Answer:

```text id="d4m9yc"
3
```

---

## 3. Only closing parentheses

```text id="z8q3vk"
s = ")))"
```

Every `)` has no matching `(`.

Therefore:

```text id="m6x2rp"
answer = 3
```

We need:

```text id="h5n9yw"
( ) ( ) ( )
```

or an equivalent valid arrangement.

---

## 4. Mixed invalid parentheses

```text id="p7c4xm"
s = "())("
```

Process:

```text id="q2m8vk"
( → open = 1
) → open = 0
) → answer = 1
( → open = 1
```

At the end:

```text id="r5x9np"
answer = 1
open = 1
```

Therefore:

```text id="k3m7yc"
answer + open = 2
```

Two insertions are required.

---

# Complexity

Let `n` be the length of the string.

### Time Complexity

```text id="x7m3qp"
O(n)
```

Every character is processed exactly once.

### Space Complexity

```text id="c5k8yn"
O(1)
```

We only use two integer variables:

```text id="n2v6mr"
open
answer
```

No stack or additional data structure is required.

---

# Pattern Recognition

This problem teaches an important **Parentheses Balance** pattern.

When the problem asks about making parentheses valid, think about:

```text id="w4q8zp"
unmatched opening parentheses
+
unmatched closing parentheses
```

For this problem:

```text id="j6m2yc"
open   = unmatched '('
answer = unmatched ')'
```

The final answer is:

```text id="q8v4kn"
unmatched '(' + unmatched ')'
```

This is often much simpler than using a stack.

---

# Connection With Previous Stack Problems

Previously, in:

```text
20. Valid Parentheses
```

the stack was used to match different types of brackets.

Here we only have:

```text
(
)
```

and we don't need to remember the exact opening bracket.

So instead of:

```text id="f2n7qm"
Stack<Character>
```

we can use:

```text id="b8x3vp"
int open
```

The underlying idea is still the same:

```text id="r6m2yc"
Track unmatched opening parentheses
        ↓
Match them when ')' appears
        ↓
Count impossible ')' as insertions
        ↓
Fix remaining '(' at the end
```

---

# Key Takeaway

The most important mental model is:

```text id="h7q3mv"
'('
 ↓
open++

')'
 ↓
if open > 0
    open--
else
    answer++
```

After processing the entire string:

```text id="k5x8rp"
answer += open;
```

So the final answer represents:

```text id="m3q7yc"
insertions needed for unmatched ')'
+
insertions needed for unmatched '('
```

The complete algorithm is:

```text id="v8n2mk"
1. Start open = 0
2. Start answer = 0
3. For every character:
      '(' → open++
      ')' → match an open if possible
             otherwise answer++
4. Add remaining open parentheses to answer
5. Return answer
```

Complexity:

```text id="p6y4xn"
Time  → O(n)
Space → O(1)
```

---

# Stack and Parentheses Patterns Learned So Far

```text id="j8m4yc"
20  → Valid Parentheses
678 → Valid Parenthesis String
921 → Minimum Add to Make Parentheses Valid
```

### Key patterns

```text id="n5q7vp"
20
→ Stack-based matching

678
→ Greedy range of possible open parentheses

921
→ Greedy unmatched-parentheses counting
```

---

# Final Mental Model

For this problem, don't think:

```text
"How do I insert parentheses?"
```

Think:

```text
"How many parentheses are currently unmatched?"
```

Maintain:

```text id="w3k8qm"
open   → unmatched '('
answer → unmatched ')'
```

Then:

```text id="x6m2vp"
Every unmatched '('
        ↓
needs one ')'

Every unmatched ')'
        ↓
needs one '('
```

Therefore:

```text id="q9r4yc"
minimum insertions
=
unmatched '(' + unmatched ')'
```

That is exactly what your solution computes.
