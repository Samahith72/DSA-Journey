# WIN #71 — Convert Binary Number in a Linked List to Integer

## Problem

[LeetCode 1290 — Convert Binary Number in a Linked List to Integer](https://leetcode.com/problems/convert-binary-number-in-a-linked-list-to-integer/)

Given a singly linked list where every node contains either `0` or `1`, the linked list represents a **binary number**.

The first node contains the **most significant bit**.

Return the decimal value of the binary number.

For example:

```text
1 → 0 → 1
```

represents:

```text
101₂
```

which is:

```text
5₁₀
```

---

# Example 1

```text
Input:
head = [1,0,1]

Output:
5
```

The binary number is:

```text
101
```

Converting to decimal:

```text
1 × 2² + 0 × 2¹ + 1 × 2⁰
```

```text
= 4 + 0 + 1
= 5
```

Therefore:

```text
101₂ = 5₁₀
```

---

# Example 2

```text
Input:
head = [0]

Output:
0
```

The binary number is simply:

```text
0₂ = 0₁₀
```

---

# My Java Solution

```java
class Solution {
    public int getDecimalValue(ListNode head) {

        int answer = 0;

        while(head != null){

            answer = answer * 2 + head.val;

            head = head.next;
        }

        return answer;
    }
}
```

---

# Core Idea

The key idea is to process the binary number **from left to right**.

For example:

```text
1 → 0 → 1
```

Start with:

```text
answer = 0
```

Read `1`:

```text
answer = 0 × 2 + 1
       = 1
```

Read `0`:

```text
answer = 1 × 2 + 0
       = 2
```

Read `1`:

```text
answer = 2 × 2 + 1
       = 5
```

Final answer:

```text
5
```

The entire conversion can therefore be done with:

```java
answer = answer * 2 + head.val;
```

---

# Why Multiply by 2?

This is the most important idea in the problem.

In binary, moving one digit to the left means multiplying the current number by `2`.

For example:

```text
1
```

Add another binary digit:

```text
10
```

The value changes from:

```text
1
```

to:

```text
1 × 2 + 0 = 2
```

Add another digit:

```text
101
```

Now:

```text
2 × 2 + 1 = 5
```

Therefore:

```text
101₂ = 5₁₀
```

---

# Step-by-Step Example

Consider:

```text
1 → 0 → 1
```

We maintain:

```text
answer
```

and process each node.

---

## Step 1 — Read `1`

Initially:

```text
answer = 0
```

Current node:

```text
1
```

Apply:

```java
answer = answer * 2 + head.val;
```

Therefore:

```text
answer = 0 × 2 + 1
       = 1
```

Now:

```text
answer = 1
```

Move to the next node:

```java
head = head.next;
```

---

## Step 2 — Read `0`

Current node:

```text
0
```

Calculate:

```text
answer = 1 × 2 + 0
       = 2
```

Now:

```text
answer = 2
```

Move forward.

---

## Step 3 — Read `1`

Current node:

```text
1
```

Calculate:

```text
answer = 2 × 2 + 1
       = 5
```

Now:

```text
answer = 5
```

The list is finished.

Return:

```text
5
```

---

# Visual Understanding

For:

```text
1 → 0 → 1
```

think of the calculation as:

```text
Start
  ↓
answer = 0
  ↓
Read 1
  ↓
0 × 2 + 1 = 1
  ↓
Read 0
  ↓
1 × 2 + 0 = 2
  ↓
Read 1
  ↓
2 × 2 + 1 = 5
  ↓
Return 5
```

---

# General Binary Conversion Pattern

Suppose the binary number is:

```text
b₁ b₂ b₃ ... bₙ
```

We can process it from left to right using:

```text
answer = answer × 2 + currentBit
```

For example:

```text
1101
```

Start:

```text
answer = 0
```

Read `1`:

```text
0 × 2 + 1 = 1
```

Read `1`:

```text
1 × 2 + 1 = 3
```

Read `0`:

```text
3 × 2 + 0 = 6
```

Read `1`:

```text
6 × 2 + 1 = 13
```

Therefore:

```text
1101₂ = 13₁₀
```

---

# Why We Don't Need Powers of 2

A common approach might be to explicitly calculate:

```text
1 × 2³
1 × 2²
0 × 2¹
1 × 2⁰
```

But that would require tracking the position of every bit.

Instead, we can build the answer incrementally.

The formula:

```text
answer = answer × 2 + bit
```

automatically accounts for the correct powers of `2`.

This makes the solution very simple.

---

# Understanding the Formula

Suppose we already processed:

```text
101
```

Its decimal value is:

```text
5
```

Now we encounter another bit:

```text
0
```

The new binary number is:

```text
1010
```

Appending a binary digit to the right means:

```text
101 × 2 + 0
```

Therefore:

```text
5 × 2 + 0 = 10
```

So:

```text
1010₂ = 10₁₀
```

If the next bit is `1`:

```text
1011
```

then:

```text
5 × 2 + 1 = 11
```

Therefore:

```text
1011₂ = 11₁₀
```

This is exactly what the algorithm does.

---

# Why We Traverse the Linked List Once

The binary digits are already stored in the correct order:

```text
Most Significant Bit
        ↓
1 → 0 → 1
            ↑
       Least Significant Bit
```

We don't need to reverse the list or store the values.

We simply traverse:

```java
while(head != null)
```

and process each bit.

---

# Pointer Movement

The linked list pointer is moved using:

```java
head = head.next;
```

So the traversal is:

```text
head
 ↓
1 → 0 → 1
```

Then:

```text
    head
     ↓
1 → 0 → 1
```

Then:

```text
        head
         ↓
1 → 0 → 1
```

Finally:

```text
1 → 0 → 1 → null
            ↑
           head
```

At this point:

```text
head == null
```

and the loop stops.

---

# Why the Most Significant Bit Being at the Head Matters

The problem states that the **most significant bit is at the head**.

For:

```text
1 → 0 → 1
```

we interpret it as:

```text
101
```

not:

```text
101
```

from the opposite direction.

The traversal therefore naturally processes the bits from:

```text
Most Significant
        ↓
Least Significant
```

which is exactly what the incremental formula needs.

---

# Edge Cases

## Single Zero

```text
0
```

Calculation:

```text
answer = 0 × 2 + 0
       = 0
```

Result:

```text
0
```

---

## Single One

```text
1
```

Calculation:

```text
answer = 0 × 2 + 1
       = 1
```

Result:

```text
1
```

---

## All Zeros

```text
0 → 0 → 0 → 0
```

Every iteration gives:

```text
0 × 2 + 0 = 0
```

Result:

```text
0
```

---

## All Ones

Consider:

```text
1 → 1 → 1 → 1
```

Calculations:

```text
0 × 2 + 1 = 1
1 × 2 + 1 = 3
3 × 2 + 1 = 7
7 × 2 + 1 = 15
```

Therefore:

```text
1111₂ = 15₁₀
```

---

# Why This Is a Linked List Problem

The actual mathematical conversion is simple.

The Linked List part is about efficiently traversing the bits:

```text
Node
 ↓
bit
 ↓
update answer
 ↓
move to next node
```

We don't need random access like an array.

We simply process every node sequentially.

---

# Pattern Recognition

When a linked list contains digits or values representing a number, think:

```text
Traverse the list
       ↓
Build answer incrementally
       ↓
Move to next node
```

For binary numbers specifically:

```text
answer = answer * 2 + currentBit
```

For a decimal representation, the equivalent idea would be:

```text
answer = answer * 10 + currentDigit
```

The base determines the multiplier.

---

# Connection With Previous Linked List Problems

This problem is different from the pointer-manipulation problems you've solved so far.

Previous problems focused on changing or comparing nodes:

```text
206 → Reverse pointers
876 → Find middle
141 → Detect cycle
234 → Reverse + compare
83  → Remove duplicates
21  → Merge lists
```

Here, we don't modify the linked list.

Instead:

```text
Linked List
    ↓
Traverse nodes
    ↓
Read each bit
    ↓
Build numerical answer
```

This is an important pattern:

> **Use the linked list as a sequential data source while maintaining a running calculation.**

---

# Complexity

Let:

```text
n = number of nodes
```

We visit every node exactly once.

Therefore:

```text
Time Complexity: O(n)
```

We only maintain one integer:

```text
answer
```

and use the linked-list pointer itself.

Therefore:

```text
Space Complexity: O(1)
```

---

# Key Insight

The most important idea is:

> **When processing a binary number from left to right, multiply the current answer by 2 and add the new bit.**

The formula is:

```text
answer = answer × 2 + bit
```

For:

```text
1 → 0 → 1
```

we get:

```text
0 × 2 + 1 = 1
1 × 2 + 0 = 2
2 × 2 + 1 = 5
```

Therefore:

```text
101₂ = 5₁₀
```

---

# Final Takeaway

The complete solution is:

```java
class Solution {
    public int getDecimalValue(ListNode head) {

        int answer = 0;

        while(head != null){

            answer = answer * 2 + head.val;

            head = head.next;
        }

        return answer;
    }
}
```

The core pattern is:

```text
Read current bit
      ↓
answer = answer × 2 + bit
      ↓
Move to next node
      ↓
Repeat
```

### Complexity

```text
Time  : O(n)
Space : O(1)
```

### Pattern

```text
Linked List
    ↓
Sequential Traversal
    ↓
Running Calculation
    ↓
Binary Number
    ↓
answer = answer × 2 + bit
```

**Important Linked List patterns learned so far:**

```text
206  → Reverse Linked List
876  → Fast & Slow Pointers
141  → Floyd's Cycle Detection
234  → Find Middle + Reverse + Compare
83   → Sorted List + Remove Duplicates
21   → Merge Two Sorted Lists
1290 → Linked List Traversal + Running Calculation
```
