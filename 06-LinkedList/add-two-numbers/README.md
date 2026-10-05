# WIN #79 — 2. Add Two Numbers

[LeetCode 2 — Add Two Numbers](https://leetcode.com/problems/add-two-numbers/)

Difficulty: Medium
Topic: Linked List / Math

---

# Problem

You are given two non-empty linked lists representing two non-negative integers.

The digits are stored in **reverse order**, and each node contains a single digit.

Add the two numbers and return the result as a linked list.

For example:

```text
l1 = [2,4,3]
l2 = [5,6,4]
```

The actual numbers are:

```text
342
465
```

because the digits are stored in reverse order.

Adding them:

```text
342 + 465 = 807
```

Therefore the result is:

```text
[7,0,8]
```

---

# Example 1

```text
Input:
l1 = [2,4,3]
l2 = [5,6,4]

Output:
[7,0,8]
```

Visual representation:

```text
l1: 2 → 4 → 3
    ↓   ↓   ↓
   ones tens hundreds

l2: 5 → 6 → 4
    ↓   ↓   ↓
   ones tens hundreds
```

Calculate:

```text
342 + 465 = 807
```

Result:

```text
7 → 0 → 8
```

---

# Example 2

```text
Input:
l1 = [0]
l2 = [0]

Output:
[0]
```

---

# Example 3

```text
Input:
l1 = [9,9,9,9,9,9,9]
l2 = [9,9,9,9]

Output:
[8,9,9,9,0,0,0,1]
```

This example demonstrates why we need to maintain a `carry`.

---

# Core Idea

The key observation is that the linked lists store digits in **reverse order**.

For:

```text
[2,4,3]
```

the actual number is:

```text
342
```

The first node represents the **ones digit**.

That is exactly the same order in which we normally perform addition:

```text
  342
+ 465
-----
  807
```

We start from the rightmost digit:

```text
2 + 5 = 7
```

then:

```text
4 + 6 = 10
```

write `0` and carry `1`.

Then:

```text
3 + 4 + 1 = 8
```

So the linked-list representation naturally lets us perform addition from least significant digit to most significant digit.

---

# Main Pattern

At every position, we calculate:

```text
sum = digit1 + digit2 + carry
```

Then:

```text
digit = sum % 10
carry = sum / 10
```

For example:

```text
sum = 10
```

gives:

```text
digit = 10 % 10 = 0
carry = 10 / 10 = 1
```

So:

```text
10
↓
digit = 0
carry = 1
```

---

# Java Solution

```java
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode node = new ListNode(0);
        ListNode tail = node;

        int carry = 0;

        while(l1 != null || l2 != null || carry != 0){
            int sum = carry;

            if(l1 != null){
                sum += l1.val;
                l1 = l1.next;
            }

            if(l2 != null){
                sum += l2.val;
                l2 = l2.next;
            }

            int digit = sum % 10;
            carry = sum / 10;

            tail.next = new ListNode(digit);
            tail = tail.next;
        }

        return node.next;
    }
}
```

---

# Step-by-Step Explanation

## 1. Create a Dummy Node

```java
ListNode node = new ListNode(0);
ListNode tail = node;
```

The dummy node helps us build the result list easily.

Initially:

```text
node
 ↓
0 → null
↑
tail
```

The `0` is not part of the answer.

It is only a starting point.

---

# Why Use a Dummy Node?

Without a dummy node, we would have to separately handle the first result node.

With the dummy node:

```java
tail.next = new ListNode(digit);
tail = tail.next;
```

works for every digit.

For example:

```text
dummy → 7 → 0 → 8
         ↑
        tail
```

At the end:

```java
return node.next;
```

because the dummy node itself should not be returned.

---

# 2. Maintain `carry`

```java
int carry = 0;
```

Carry is required whenever the sum of two digits is `10` or greater.

For example:

```text
9 + 8 = 17
```

We store:

```text
digit = 7
carry = 1
```

The next calculation becomes:

```text
next digit + next digit + 1
```

---

# 3. Continue While Something Remains

The loop condition is:

```java
while(l1 != null || l2 != null || carry != 0)
```

This is very important.

We continue if:

```text
l1 still has digits
OR
l2 still has digits
OR
a carry still exists
```

The third condition is especially important.

---

# Why Check `carry != 0`?

Consider:

```text
l1 = [9]
l2 = [1]
```

We calculate:

```text
9 + 1 = 10
```

So:

```text
digit = 0
carry = 1
```

At this point both lists are finished:

```text
l1 = null
l2 = null
```

But we still have:

```text
carry = 1
```

Therefore the loop must execute one more time.

The result becomes:

```text
0 → 1
```

which represents:

```text
10
```

Without:

```java
carry != 0
```

we would incorrectly return:

```text
[0]
```

instead of:

```text
[0,1]
```

---

# 4. Start the Sum With Carry

Inside the loop:

```java
int sum = carry;
```

Why?

Because the previous digit may have produced a carry.

For example:

```text
  4
+ 6
---
 10
```

We store:

```text
digit = 0
carry = 1
```

The next calculation must include that `1`.

So:

```text
sum = carry + current digit + current digit
```

---

# 5. Add the Digit From `l1`

```java
if(l1 != null){
    sum += l1.val;
    l1 = l1.next;
}
```

If `l1` still has a node, add its digit.

Then move to the next node:

```java
l1 = l1.next;
```

This lets us process the linked list from left to right while still performing arithmetic from least significant digit to most significant digit.

---

# 6. Add the Digit From `l2`

Similarly:

```java
if(l2 != null){
    sum += l2.val;
    l2 = l2.next;
}
```

The important thing is that the two lists do not necessarily have the same length.

So we check them independently.

---

# Different Lengths

Consider:

```text
l1 = [9,9,9]
l2 = [1]
```

We can think of this as:

```text
999 + 1 = 1000
```

The linked lists are:

```text
l1: 9 → 9 → 9

l2: 1
```

When `l2` becomes `null`, we simply stop adding digits from `l2`.

But we continue processing `l1` and `carry`.

This is why we use:

```java
if(l1 != null)
```

and:

```java
if(l2 != null)
```

instead of assuming both lists have equal lengths.

---

# 7. Extract the Result Digit

After adding:

```text
digit1 + digit2 + carry
```

we calculate:

```java
int digit = sum % 10;
```

The `% 10` operation gives us the digit that belongs at the current position.

Examples:

```text
7  → digit = 7
12 → digit = 2
15 → digit = 5
20 → digit = 0
```

---

# 8. Calculate the New Carry

```java
carry = sum / 10;
```

Integer division gives us the carry.

Examples:

```text
7  / 10 = 0
12 / 10 = 1
15 / 10 = 1
20 / 10 = 2
```

Since each input digit is between `0` and `9`, the maximum possible sum is:

```text
9 + 9 + 1 = 19
```

Therefore the carry is always either:

```text
0 or 1
```

---

# 9. Add the Digit to the Result List

```java
tail.next = new ListNode(digit);
tail = tail.next;
```

Suppose:

```text
digit = 7
```

Then:

```text
dummy → 7
         ↑
        tail
```

Next digit:

```text
0
```

becomes:

```text
dummy → 7 → 0
             ↑
            tail
```

And so on.

---

# Complete Example Walkthrough

Consider:

```text
l1 = [2,4,3]
l2 = [5,6,4]
```

This represents:

```text
342 + 465
```

---

## Step 1

Digits:

```text
2 + 5 = 7
```

So:

```text
digit = 7
carry = 0
```

Result:

```text
7
```

---

## Step 2

Digits:

```text
4 + 6 + 0 = 10
```

Therefore:

```text
digit = 0
carry = 1
```

Result:

```text
7 → 0
```

---

## Step 3

Digits:

```text
3 + 4 + 1 = 8
```

Therefore:

```text
digit = 8
carry = 0
```

Result:

```text
7 → 0 → 8
```

Both lists are now finished and:

```text
carry = 0
```

So the loop stops.

Return:

```text
[7,0,8]
```

---

# Visualizing the Addition

The linked lists:

```text
l1: 2 → 4 → 3
l2: 5 → 6 → 4
```

represent:

```text
  342
+ 465
-----
  807
```

But because the linked lists are reversed, we process:

```text
2 + 5
4 + 6
3 + 4
```

which is exactly the correct order for arithmetic.

The result:

```text
7 → 0 → 8
```

represents:

```text
807
```

---

# Example With Carry

Consider:

```text
l1 = [9,9,9]
l2 = [1]
```

This represents:

```text
999 + 1 = 1000
```

### First digit

```text
9 + 1 = 10
```

```text
digit = 0
carry = 1
```

Result:

```text
0
```

### Second digit

`l2` is already finished.

So:

```text
9 + 0 + 1 = 10
```

```text
digit = 0
carry = 1
```

Result:

```text
0 → 0
```

### Third digit

Again:

```text
9 + 0 + 1 = 10
```

Result:

```text
0 → 0 → 0
```

### Final carry

Both lists are now finished, but:

```text
carry = 1
```

Therefore the loop executes once more.

```text
0 + 0 + 1 = 1
```

Result:

```text
0 → 0 → 0 → 1
```

So:

```text
[0,0,0,1]
```

represents:

```text
1000
```

---

# Why The Lists Are Stored in Reverse

This problem becomes much easier because the least significant digit is at the head.

For:

```text
342
```

the linked list is:

```text
2 → 4 → 3
```

The first node is:

```text
2 = ones
```

The second:

```text
4 = tens
```

The third:

```text
3 = hundreds
```

Normal addition starts from the ones place.

Therefore:

```text
Linked List Order
       =
Addition Order
```

No reversal of the input lists is required.

---

# Handling Different Lengths

Consider:

```text
l1 = [2,4,3]
l2 = [5,6]
```

These represent:

```text
342 + 65
```

The algorithm processes:

```text
2 + 5
4 + 6
3 + 0
```

When `l2` becomes `null`:

```java
if(l2 != null)
```

simply doesn't execute.

But `l1` continues.

This allows the same loop to handle different-length lists without special cases.

---

# Why We Don't Convert to Integers

A tempting approach would be:

```text
Linked List
   ↓
Convert to number
   ↓
Add numbers
   ↓
Convert back to Linked List
```

This is not a good solution.

The numbers can contain up to 100 digits, which is much larger than Java's primitive integer types.

The linked-list solution performs the addition digit by digit and works regardless of the total number of digits.

---

# Important Pattern: Dummy Node + Tail

This problem reinforces the result-list construction pattern from previous problems.

We start with:

```java
ListNode node = new ListNode(0);
ListNode tail = node;
```

Then every new result digit is appended using:

```java
tail.next = new ListNode(digit);
tail = tail.next;
```

So:

```text
node
 ↓
dummy → 7 → 0 → 8
              ↑
             tail
```

At the end:

```java
return node.next;
```

returns the actual result.

This is a very useful pattern whenever we need to build a linked list from left to right.

---

# Edge Cases

## 1. Both Numbers Are Zero

```text
l1 = [0]
l2 = [0]
```

Calculation:

```text
0 + 0 = 0
```

Result:

```text
[0]
```

---

## 2. One List Is Longer

```text
l1 = [9,9,9]
l2 = [1]
```

The algorithm continues processing `l1` after `l2` becomes `null`.

---

## 3. Final Carry

```text
l1 = [9]
l2 = [9]
```

Calculation:

```text
9 + 9 = 18
```

Result:

```text
digit = 8
carry = 1
```

Then the final carry creates:

```text
[8,1]
```

which represents:

```text
18
```

---

## 4. No Carry

```text
l1 = [2]
l2 = [3]
```

Calculation:

```text
2 + 3 = 5
```

Result:

```text
[5]
```

---

# Common Mistakes

## Mistake 1: Forgetting the Carry

Incorrect:

```java
int sum = l1.val + l2.val;
```

This fails when:

```text
sum >= 10
```

We must carry the extra value to the next digit.

Correct:

```java
int sum = carry;
```

then add the available digits.

---

## Mistake 2: Forgetting Different Lengths

You cannot assume:

```text
l1 != null
l2 != null
```

at the same time.

One list can finish before the other.

Therefore:

```java
if(l1 != null)
```

and:

```java
if(l2 != null)
```

must be handled independently.

---

## Mistake 3: Forgetting the Final Carry

The loop must include:

```java
carry != 0
```

Otherwise:

```text
9 + 1
```

would incorrectly produce:

```text
[0]
```

instead of:

```text
[0,1]
```

---

# Pattern Recognition

Whenever you see:

> Two linked lists represent numbers digit by digit and the digits are stored in reverse order.

Think:

```text
Two Pointers
    +
Carry
    +
Dummy Result Node
    +
Tail Pointer
```

The core formula is:

```text
sum = digit1 + digit2 + carry

digit = sum % 10

carry = sum / 10
```

---

# Complexity

Let:

```text
m = length of l1
n = length of l2
```

### Time Complexity

```text
O(max(m, n))
```

Each node from both lists is processed once.

There can be one additional iteration for the final carry.

### Space Complexity

```text
O(max(m, n))
```

The result list contains at most:

```text
max(m, n) + 1
```

nodes.

The algorithm itself uses only:

```text
O(1)
```

extra working space apart from the output list.

---

# Key Takeaway

The most important idea is to treat the linked lists exactly like normal column addition.

For every position:

```text
digit1
  +
digit2
  +
carry
  ↓
sum
```

Then split the sum:

```text
sum % 10 → current digit
sum / 10 → next carry
```

The complete pattern is:

```text
l1 → digit
l2 → digit
carry
 ↓
sum
 ↓
digit = sum % 10
carry = sum / 10
 ↓
append digit to result
```

Because the input digits are already stored in reverse order, we can perform the addition in a single forward traversal.

---

# Linked List Patterns Learned So Far

```text
206  → Reverse Linked List
       └── 3-Pointer Reversal

876  → Middle of the Linked List
       └── Fast & Slow Pointers

141  → Linked List Cycle
       └── Floyd's Cycle Detection

234  → Palindrome Linked List
       └── Find Middle + Reverse + Compare

83   → Remove Duplicates from Sorted List
       └── Compare + Skip Duplicate Nodes

21   → Merge Two Sorted Lists
       └── Dummy Node + Two Pointers

1290 → Convert Binary Number in a Linked List to Integer
       └── Linked List Traversal + Running Calculation

203  → Remove Linked List Elements
       └── Dummy Node + Node Deletion

160  → Intersection of Two Linked Lists
       └── Two Pointers + Head Switching

237  → Delete Node in a Linked List
       └── Copy Next Node + Skip Next Node

2181 → Merge Nodes in Between Zeros
       └── Running Sum + Result List Construction

92   → Reverse Linked List II
       └── Sublist Reversal + Front Insertion

707  → Design Linked List
       └── Linked List Implementation + Pointer Manipulation

2    → Add Two Numbers
       └── Digit-by-Digit Addition + Carry + Result List
```

---

# Final Mental Model

```text
l1: 2 → 4 → 3
l2: 5 → 6 → 4

        ↓

2 + 5 + 0 = 7
        ↓
digit = 7
carry = 0

4 + 6 + 0 = 10
        ↓
digit = 0
carry = 1

3 + 4 + 1 = 8
        ↓
digit = 8
carry = 0

        ↓

result: 7 → 0 → 8
```

Remember the three formulas:

```text
sum   = digit1 + digit2 + carry
digit = sum % 10
carry = sum / 10
```

And the result construction pattern:

```java
tail.next = new ListNode(digit);
tail = tail.next;
```

This combination of **two-pointer traversal, carry handling, and tail-based linked-list construction** is the core pattern for this problem.
