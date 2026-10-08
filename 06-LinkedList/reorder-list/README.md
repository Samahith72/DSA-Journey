# WIN #87 — 143. Reorder List

[LeetCode — 143. Reorder List](https://leetcode.com/problems/reorder-list/)

**Difficulty:** Medium
**Topic:** Linked List, Fast & Slow Pointers, Reversal, Pointer Manipulation

---

## Problem

Given the head of a singly linked list:

```text
L0 → L1 → L2 → ... → Ln-1 → Ln
```

reorder the list into:

```text
L0 → Ln → L1 → Ln-1 → L2 → Ln-2 → ...
```

The values inside the nodes **cannot be modified**.

Only the `next` pointers and node positions can be changed.

### Example 1

```text
Input:
1 → 2 → 3 → 4

Output:
1 → 4 → 2 → 3
```

The original list is:

```text
1 → 2 → 3 → 4
```

We take:

```text
first  → 1, 2
last   → 4, 3
```

and interleave them:

```text
1 → 4 → 2 → 3
```

### Example 2

```text
Input:
1 → 2 → 3 → 4 → 5

Output:
1 → 5 → 2 → 4 → 3
```

The two sides are:

```text
First half:
1 → 2 → 3

Second half:
4 → 5
```

Reverse the second half:

```text
5 → 4
```

Then merge:

```text
1 → 5 → 2 → 4 → 3
```

---

# Core Idea

This problem looks complicated because the required order jumps back and forth:

```text
L0 → Ln → L1 → Ln-1 → L2 → Ln-2
```

The key is to break the problem into **three simpler linked-list operations**:

```text
1. Find the middle of the list
2. Reverse the second half
3. Merge the two halves alternately
```

For:

```text
1 → 2 → 3 → 4 → 5
```

### Step 1: Split

```text
First half:
1 → 2 → 3

Second half:
4 → 5
```

### Step 2: Reverse second half

```text
5 → 4
```

### Step 3: Merge alternately

```text
1 → 5 → 2 → 4 → 3
```

This gives the required order.

---

# Java Solution

```java
class Solution {
    public void reorderList(ListNode head) {

        if (head == null || head.next == null) {
            return;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode second = slow.next;
        slow.next = null;

        ListNode prev = null;

        while (second != null) {
            ListNode next = second.next;
            second.next = prev;
            prev = second;
            second = next;
        }

        second = prev;

        ListNode first = head;

        while (second != null) {
            ListNode firstNext = first.next;
            ListNode secondNext = second.next;

            first.next = second;
            second.next = firstNext;

            first = firstNext;
            second = secondNext;
        }
    }
}
```

---

# Step-by-Step Explanation

# Part 1: Find the Middle

We start with:

```java
ListNode slow = head;
ListNode fast = head;
```

Then:

```java
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
}
```

This is the **fast and slow pointer technique** you've already used in:

```text
876. Middle of the Linked List
```

The `slow` pointer moves one node at a time.

The `fast` pointer moves two nodes at a time.

Therefore, when `fast` reaches the end:

```text
slow
 ↓
middle of list
```

---

# Example: Odd-Length List

Consider:

```text
1 → 2 → 3 → 4 → 5
```

Initially:

```text
slow
 ↓
1 → 2 → 3 → 4 → 5
 ↑
fast
```

After one iteration:

```text
1 → 2 → 3 → 4 → 5
    ↑       ↑
   slow    fast
```

After two:

```text
1 → 2 → 3 → 4 → 5
        ↑           ↑
       slow        fast
```

After the next movement, `fast` reaches the end.

So:

```text
slow → 3
```

The middle is node `3`.

---

# Part 2: Split the List

Once we have `slow`, we do:

```java
ListNode second = slow.next;
slow.next = null;
```

This is extremely important.

Before:

```text
1 → 2 → 3 → 4 → 5
        ↑
       slow
```

We save:

```java
second = slow.next;
```

So:

```text
second → 4
```

Then:

```java
slow.next = null;
```

breaks the connection.

Now we have two separate lists:

```text
First:
1 → 2 → 3


Second:
4 → 5
```

This is the first major transformation.

---

# Why Do We Split the List?

The required order is:

```text
L0 → Ln → L1 → Ln-1 → L2 → ...
```

This means we need to alternate:

```text
first half
second half reversed
```

So separating the two halves makes the problem much easier.

---

# Part 3: Reverse the Second Half

Now:

```text
second → 4 → 5
```

We reverse it using the same technique from:

```text
206. Reverse Linked List
```

Your code:

```java
ListNode prev = null;

while (second != null) {
    ListNode next = second.next;
    second.next = prev;
    prev = second;
    second = next;
}
```

The three-pointer pattern is:

```text
next
current
prev
```

In this code:

```text
second = current
```

---

# Reversing `4 → 5`

Initially:

```text
second → 4 → 5
prev → null
```

### Step 1

Save:

```java
ListNode next = second.next;
```

So:

```text
next → 5
```

Then:

```java
second.next = prev;
```

Now:

```text
4 → null
```

Move:

```java
prev = second;
second = next;
```

Now:

```text
prev → 4
second → 5
```

---

### Step 2

Save:

```text
next → null
```

Then:

```text
5 → 4
```

Move the pointers:

```text
prev → 5 → 4
second → null
```

The reversal is complete.

---

# Important Detail

After the reversal:

```text
prev
 ↓
5 → 4
```

But:

```text
second == null
```

Therefore we do:

```java
second = prev;
```

Now:

```text
second
   ↓
5 → 4
```

So `second` becomes the head of the reversed second half.

---

# Part 4: Merge the Two Halves

At this point:

```text
First half:

1 → 2 → 3


Second half:

5 → 4
```

We need:

```text
1 → 5 → 2 → 4 → 3
```

We initialize:

```java
ListNode first = head;
```

So:

```text
first
 ↓
1 → 2 → 3

second
 ↓
5 → 4
```

Now we alternate between the two lists.

---

# The Critical Part

Your merge loop is:

```java
while (second != null) {
    ListNode firstNext = first.next;
    ListNode secondNext = second.next;

    first.next = second;
    second.next = firstNext;

    first = firstNext;
    second = secondNext;
}
```

There are four important operations here.

---

## 1. Save the next first-half node

```java
ListNode firstNext = first.next;
```

Initially:

```text
first → 1 → 2 → 3
```

So:

```text
firstNext → 2
```

We must save this before changing any links.

---

## 2. Save the next second-half node

```java
ListNode secondNext = second.next;
```

Initially:

```text
second → 5 → 4
```

So:

```text
secondNext → 4
```

Again, we save it before modifying the links.

---

# Why Save Both?

Because we are about to modify:

```text
first.next
second.next
```

If we don't save the original next nodes first, we could lose access to the remaining lists.

This is a very important linked-list rule:

> Before changing a `next` pointer, save the node that was originally after it if you still need that node.

---

# 3. Connect First to Second

```java
first.next = second;
```

Before:

```text
1 → 2
```

and:

```text
5 → 4
```

After:

```text
1 → 5
```

---

# 4. Connect Second to the Remaining First

```java
second.next = firstNext;
```

Since:

```text
firstNext = 2
```

we get:

```text
1 → 5 → 2
```

Now the first pair has been merged.

---

# Move Both Pointers

Finally:

```java
first = firstNext;
second = secondNext;
```

So:

```text
first → 2
second → 4
```

The remaining lists are effectively:

```text
2 → 3

4
```

We repeat the same process.

---

# Complete Merge Walkthrough

Starting:

```text
First:
1 → 2 → 3

Second:
5 → 4
```

---

### First iteration

Save:

```text
firstNext = 2
secondNext = 4
```

Connect:

```text
1 → 5 → 2
```

Move:

```text
first → 2
second → 4
```

---

### Second iteration

Save:

```text
firstNext = 3
secondNext = null
```

Connect:

```text
2 → 4 → 3
```

Move:

```text
first → 3
second → null
```

The loop ends.

Final:

```text
1 → 5 → 2 → 4 → 3
```

Exactly what we need.

---

# Complete Visual Transformation

For:

```text
1 → 2 → 3 → 4 → 5
```

### Step 1: Find middle

```text
1 → 2 → 3 | 4 → 5
        ↑
      middle
```

### Step 2: Split

```text
1 → 2 → 3

4 → 5
```

### Step 3: Reverse second half

```text
1 → 2 → 3

5 → 4
```

### Step 4: Interleave

```text
1 → 5 → 2 → 4 → 3
```

This is the entire algorithm.

---

# Why Does This Work?

The original list is:

```text
L0 → L1 → L2 → ... → Ln
```

After splitting:

```text
First half:
L0 → L1 → L2


Second half:
...
```

After reversing the second half:

```text
Ln → Ln-1 → Ln-2
```

Then alternating the two lists gives:

```text
L0 → Ln → L1 → Ln-1 → L2 → Ln-2
```

which is exactly the required ordering.

---

# Why Do We Not Modify Node Values?

The problem specifically says:

> You may not modify the values in the list's nodes.

Our solution never does:

```java
node.val = ...
```

Instead, we only change:

```java
node.next
```

For example:

```java
first.next = second;
second.next = firstNext;
```

So the actual nodes are rearranged while their values remain unchanged.

---

# Edge Cases

## 1. Empty list

```text
head = null
```

The condition:

```java
if (head == null || head.next == null)
```

returns immediately.

---

## 2. One node

```text
1
```

There is nothing to reorder.

Result:

```text
1
```

---

## 3. Two nodes

```text
1 → 2
```

The desired order is:

```text
1 → 2
```

The algorithm handles this naturally.

---

## 4. Three nodes

```text
1 → 2 → 3
```

The result should be:

```text
1 → 3 → 2
```

Split:

```text
1 → 2

3
```

Reverse second half:

```text
3
```

Merge:

```text
1 → 3 → 2
```

---

## 5. Even number of nodes

```text
1 → 2 → 3 → 4
```

Split:

```text
1 → 2

3 → 4
```

Reverse:

```text
4 → 3
```

Merge:

```text
1 → 4 → 2 → 3
```

---

# Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

We perform three linear operations:

```text
Find middle       → O(n)
Reverse half      → O(n)
Merge halves      → O(n)
```

Therefore:

```text
O(n) + O(n) + O(n) = O(n)
```

### Space Complexity

```text
O(1)
```

We only use a constant number of pointers:

```text
slow
fast
second
prev
first
firstNext
secondNext
```

No array, stack, or additional linked list is created.

---

# Pattern Recognition

This problem is extremely important because it combines **three fundamental linked-list patterns**.

### Pattern 1: Fast and Slow Pointers

Used to find the middle:

```text
slow → one step
fast → two steps
```

### Pattern 2: In-Place Reversal

Used to reverse the second half:

```text
current.next = prev
```

### Pattern 3: Alternating Merge

Used to combine:

```text
first → second → first → second
```

So the complete pattern is:

```text
Find Middle
     ↓
Split
     ↓
Reverse Second Half
     ↓
Merge Alternately
```

---

# Connection With Previous Linked List Problems

This problem brings together several techniques you've already learned.

### 876. Middle of the Linked List

You already learned:

```text
slow = slow.next
fast = fast.next.next
```

Here we use the exact same technique.

### 206. Reverse Linked List

You already learned:

```text
next = current.next
current.next = prev
prev = current
current = next
```

Here we apply it only to the second half.

### 92. Reverse Linked List II

You learned how to reverse part of a linked list while preserving the rest.

Here we first split the list and then reverse the entire second half.

### 24. Swap Nodes in Pairs

You learned how to carefully save pointers before rewiring them.

The merge phase here uses the same principle.

### 328. Odd Even Linked List

You learned how to maintain separate chains and reconnect them.

Here we maintain two halves and merge them alternately.

---

# Key Takeaway

The most important thing to remember is that this is **not one complicated operation**.

Break it into three simple problems:

```text
1. Find the middle
2. Reverse the second half
3. Merge the two halves
```

For:

```text
1 → 2 → 3 → 4 → 5
```

think:

```text
        Find middle
             ↓
1 → 2 → 3 | 4 → 5
             ↓
         Reverse
             ↓
1 → 2 → 3 | 5 → 4
             ↓
       Alternate merge
             ↓
1 → 5 → 2 → 4 → 3
```

The merge pattern is:

```text
first → second → first → second
```

and before changing any pointer, save the remaining nodes:

```java
ListNode firstNext = first.next;
ListNode secondNext = second.next;
```

This prevents losing access to the rest of the list.

---

# Linked List Patterns Learned So Far

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
237  → Delete Node in a Linked List
2181 → Merge Nodes in Between Zeros
92   → Reverse Linked List II
707  → Design Linked List
2    → Add Two Numbers
1721 → Swapping Nodes in a Linked List
19   → Remove Nth Node From End of List
24   → Swap Nodes in Pairs
328  → Odd Even Linked List
61   → Rotate List
86   → Partition List
143  → Reorder List
```

---

# Final Mental Model

Whenever you see a linked-list problem requiring a pattern like:

```text
First → Last → Second → Second Last → ...
```

think:

```text
Find middle
    ↓
Split list
    ↓
Reverse second half
    ↓
Merge alternately
```

The three techniques are:

```text
Fast/Slow Pointers
        +
Linked List Reversal
        +
Pointer Rewiring
```

The final structure:

```text
First Half:   L0 → L1 → L2
                    ↓
Second Half:  Ln → Ln-1 → Ln-2
                    ↓
Merge:        L0 → Ln → L1 → Ln-1 → L2
```

Complexity:

```text
Time  → O(n)
Space → O(1)
```
