#  WIN #74 — 678. Valid Parenthesis String

[**LeetCode 678 — Valid Parenthesis String**](https://leetcode.com/problems/valid-parenthesis-string/)

**Difficulty:**  Medium
**Topic:** Stack / Greedy

---

# Problem

Given a string `s` containing only:

```text
'('
')'
'*'
```

return `true` if the string is valid.

The special character `'*'` can represent:

```text
'('
')'
""
```

So every `'*'` gives us **three possible choices**.

---

#  Core Idea

The challenge is that we don't know what each `'*'` should represent.

For example:

```text
(*))
```

The `*` could become:

```text
(
```

giving:

```text
(())
```

which is valid.

Instead of trying every possible interpretation of `'*'`, we maintain a **range of possible open-parenthesis counts**.

We use two variables:

```java
int minOpen = 0;
int maxOpen = 0;
```

They represent:

```text
minOpen → minimum possible number of unmatched '('
maxOpen → maximum possible number of unmatched '('
```

So instead of tracking one exact state, we track a **range**:

```text
[minOpen, maxOpen]
```

---

#  Why Do We Need a Range?

Consider:

```text
s = "*"
```

The `*` can become:

```text
"("
")"
""
```

Therefore the number of open parentheses could be:

```text
1
0
```

We don't need to choose immediately.

We simply represent the possibilities as:

```text
[0, 1]
```

This is the key idea behind the solution.

---

#  Java Solution

```java
class Solution {
    public boolean checkValidString(String s) {

        int minOpen = 0;
        int maxOpen = 0;

        for(char c: s.toCharArray()){
            if(c == '('){
                minOpen++;
                maxOpen++;
            }else if(c == ')'){
                minOpen--;
                maxOpen--;
            }else{
                minOpen--;
                maxOpen++;
            }

            minOpen = Math.max(0, minOpen);

            if(maxOpen < 0){
                return false;
            }
        }

        return minOpen == 0;
    }
}
```

---

#  Step-by-Step Explanation

## 1. Start With No Open Parentheses

```java
int minOpen = 0;
int maxOpen = 0;
```

Initially:

```text
Possible open parentheses:

minOpen = 0
maxOpen = 0

Range = [0, 0]
```

---

# 2. When We See `'('`

```java
if(c == '('){
    minOpen++;
    maxOpen++;
}
```

A normal opening parenthesis **must** increase the number of unmatched opens.

For example:

```text
s = "("
```

Before:

```text
[0, 0]
```

After:

```text
[1, 1]
```

There is exactly one unmatched opening parenthesis.

---

# 3. When We See `')'`

```java
else if(c == ')'){
    minOpen--;
    maxOpen--;
}
```

A closing parenthesis tries to match an opening parenthesis.

So both possible counts decrease:

```text
[2, 5]
```

becomes:

```text
[1, 4]
```

---

# 4. When We See `'*'`

This is the most important part.

```java
else{
    minOpen--;
    maxOpen++;
}
```

Remember:

```text
'*' can be:
    '('
    ')'
    ""
```

Therefore:

### If `*` becomes `'('`

Open count increases:

```text
maxOpen++
```

### If `*` becomes `')'`

Open count decreases:

```text
minOpen--
```

### If `*` becomes empty

Open count stays the same.

Therefore we don't need to explicitly track all three choices.

We simply expand the range:

```text
minOpen--
maxOpen++
```

---

#  Why Do We Clamp `minOpen` to Zero?

After processing a character:

```java
minOpen = Math.max(0, minOpen);
```

Why?

Because the number of unmatched opening parentheses can never actually be negative.

Suppose:

```text
s = "*"
```

The minimum interpretation is:

```text
* → ')'
```

which gives:

```text
-1
```

But that doesn't mean we have `-1` opening parentheses.

The minimum realistic number is:

```text
0
```

So:

```java
minOpen = Math.max(0, minOpen);
```

converts:

```text
-1 → 0
```

---

#  Why Do We Check `maxOpen < 0`?

```java
if(maxOpen < 0){
    return false;
}
```

This is extremely important.

`maxOpen` represents the **maximum possible number of unmatched opening parentheses**.

If even the maximum possibility becomes negative, there is no way to make the string valid.

### Example

```text
s = ")"
```

Initially:

```text
minOpen = 0
maxOpen = 0
```

Process `')'`:

```text
minOpen = -1
maxOpen = -1
```

Then:

```java
minOpen = Math.max(0, minOpen);
```

gives:

```text
minOpen = 0
maxOpen = -1
```

Now:

```java
if(maxOpen < 0)
```

is true.

Therefore:

```text
false
```

There is no opening parenthesis available to match `')'`.

---

#  Complete Example

Consider:

```text
s = "(*))"
```

We process each character.

---

## Character 1: `'('`

```text
minOpen = 1
maxOpen = 1
```

Range:

```text
[1, 1]
```

Meaning:

```text
There must be exactly 1 unmatched '('.
```

---

## Character 2: `'*'`

`*` can be:

```text
')' → decrease
'(' → increase
''  → unchanged
```

So:

```text
minOpen = 0
maxOpen = 2
```

Range:

```text
[0, 2]
```

This means there could be anywhere between:

```text
0 and 2
```

unmatched opening parentheses.

---

## Character 3: `')'`

Both values decrease:

```text
minOpen = -1
maxOpen = 1
```

Clamp minimum:

```text
minOpen = 0
```

So:

```text
[0, 1]
```

There is still at least one possible valid interpretation.

---

## Character 4: `')'`

Decrease again:

```text
minOpen = -1
maxOpen = 0
```

Clamp:

```text
minOpen = 0
```

Final range:

```text
[0, 0]
```

Therefore:

```text
minOpen == 0
```

and the answer is:

```text
true
```

One valid interpretation is:

```text
(*))
```

becomes:

```text
(())
```

---

# Why Does `minOpen == 0` Mean Valid?

At the end, we need to know whether there is **at least one possible interpretation** where every opening parenthesis is matched.

`minOpen` represents the minimum possible unmatched opening parentheses.

If:

```text
minOpen == 0
```

then there exists an interpretation where we have no unmatched `'('`.

Therefore the string can be valid.

---

#  Example: `"("`

Process:

```text
(
```

gives:

```text
minOpen = 1
maxOpen = 1
```

At the end:

```java
return minOpen == 0;
```

becomes:

```text
1 == 0
```

which is:

```text
false
```

There is no `')'` or `'*'` left to close the opening parenthesis.

---

#  Example: `")("`

First:

```text
)
```

gives:

```text
maxOpen = -1
```

Immediately:

```java
if(maxOpen < 0)
    return false;
```

So we return:

```text
false
```

The first character is already impossible to match.

---

#  The Real Meaning of `minOpen` and `maxOpen`

Think of them as a **range of possibilities**.

```text
minOpen
   ↓
minimum number of unmatched '(' possible

maxOpen
   ↓
maximum number of unmatched '(' possible
```

For example:

```text
[1, 4]
```

means:

```text
Possible unmatched '(' counts:

1
2
3
4
```

We don't care exactly which one occurs yet.

We only need to know whether the range contains a valid possibility.

---

#  The Two Critical Conditions

There are two different failure conditions.

### Condition 1: `maxOpen < 0`

```java
if(maxOpen < 0){
    return false;
}
```

Means:

> Even under the most favorable interpretation, we have more `')'` than we can match.

So the string is immediately invalid.

---

### Condition 2: `minOpen != 0` at the end

```java
return minOpen == 0;
```

Means:

> Even after choosing the best possible interpretation of `'*'`, some `'('` remain unmatched.

Therefore the string is invalid.

---

#  Important Visualization

For every character:

```text
             Possible unmatched '('
                      │
                      ▼

                 maxOpen
                    │
        ┌───────────┴───────────┐
        │                       │
        │     possibilities     │
        │                       │
        └───────────┬───────────┘
                    │
                 minOpen
```

We maintain the range:

```text
[minOpen, maxOpen]
```

For `'*'`:

```text
         '*'
       /  |  \
      /   |   \
    '('  ''   ')'
     ↑    ↑    ↑
    +1    0   -1
```

So:

```text
minOpen -= 1
maxOpen += 1
```

---

#  Why This Is Greedy

We don't explicitly decide what every `'*'` should become.

Instead, we maintain **all useful possibilities simultaneously** using a range.

The greedy strategy is:

```text
minOpen → assume '*' helps close parentheses
maxOpen → assume '*' helps open parentheses
```

This gives us the widest possible range of valid states.

We only need to know whether that range eventually includes `0`.

---

# 🧪 More Examples

## Example 1

```text
s = "()"
```

Processing:

```text
( → [1,1]
) → [0,0]
```

Final:

```text
minOpen = 0
```

Result:

```text
true
```

---

## Example 2

```text
s = "(*)"
```

Processing:

```text
( → [1,1]
* → [0,2]
) → [0,1]
```

Final:

```text
minOpen = 0
```

Result:

```text
true
```

The `*` can represent `')'`:

```text
()
```

or empty:

```text
()
```

---

## Example 3

```text
s = "(*))"
```

Ranges:

```text
( → [1,1]
* → [0,2]
) → [0,1]
) → [0,0]
```

Result:

```text
true
```

---

#  Common Mistake

A common approach is to treat every `'*'` immediately as either:

```text
'('
```

or:

```text
')'
```

But we don't know which choice is correct until we see the rest of the string.

For example:

```text
(*)
```

The `*` could simply be empty.

The greedy range approach avoids making an irreversible decision too early.

---

# ⏱ Complexity

Let `n` be the length of the string.

### Time Complexity

```text
O(n)
```

We scan the string exactly once.

### Space Complexity

```text
O(1)
```

We only maintain:

```java
minOpen
maxOpen
```

No stack or extra data structure is required.

---

#  Pattern Recognition

Whenever you see:

> Parentheses + wildcard that can represent multiple possibilities

Think:

```text
Greedy Range
    ↓
Track minimum possible state
    +
Track maximum possible state
    ↓
[min, max]
```

For this problem:

```text
minOpen → minimum possible unmatched '('
maxOpen → maximum possible unmatched '('
```

---

#  Key Takeaway

The biggest idea in this problem is **not trying every possible meaning of `'*'`**.

Instead, maintain a range:

```text
[minOpen, maxOpen]
```

For each character:

```text
'('  → min++, max++

')'  → min--, max--

'*'  → min--, max++
```

Then:

```java
minOpen = Math.max(0, minOpen);
```

keeps the minimum realistic.

And:

```java
if(maxOpen < 0)
    return false;
```

detects an impossible prefix.

Finally:

```java
return minOpen == 0;
```

checks whether there is at least one valid interpretation.

### ⭐ Remember this pattern:

```text
Wildcard Parentheses
        ↓
Don't choose '*' immediately
        ↓
Track possible range
        ↓
[minOpen, maxOpen]
        ↓
maxOpen < 0 → impossible
        ↓
minOpen == 0 → valid
```

---

#  Stack / Greedy Patterns Learned

```text
20   → Valid Parentheses
       └── Stack + Matching Brackets

678  → Valid Parenthesis String
       └── Greedy + Range of Possible Open Parentheses
```

And from the Linked List section:

```text
206  → Reverse Linked List
876  → Middle of the Linked List
141  → Linked List Cycle
234  → Palindrome Linked List
83   → Remove Duplicates from Sorted List
21   → Merge Two Sorted Lists
1290 → Convert Binary Number in a Linked List to Integer
203  → Remove Linked List Elements
160  → Intersection of Two Linked Lists
```

##  Final Mental Model

```text
'(' → definitely opens
')' → definitely closes
'*' → could open / close / disappear

Therefore:

minOpen = most closed scenario
maxOpen = most open scenario

Maintain the range
        ↓
Reject if maxOpen < 0
        ↓
Accept if minOpen == 0
```
