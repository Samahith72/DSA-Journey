# WIN #81 — 19. Remove Nth Node From End of List

[LeetCode — 19. Remove Nth Node From End of List](https://leetcode.com/problems/remove-nth-node-from-end-of-list/)

**Difficulty:** Medium
**Topic:** Linked List, Two Pointers

---

## Problem

Given the `head` of a linked list, remove the `nth` node from the **end** of the list and return the head of the modified list.

### Example 1

```text
Input:
head = [1,2,3,4,5]
n = 2

Output:
[1,2,3,5]
```

The 2nd node from the end is `4`.

```text
1 → 2 → 3 → 4 → 5
            ↑
           remove

1 → 2 → 3 → 5
```

### Example 2

```text
Input:
head = [1]
n = 1

Output:
[]
```

The only node is the 1st node from the end, so it is removed.

### Example 3

```text
Input:
head = [1,2]
n = 1

Output:
[1]
```

The 1st node from the end is the last node, `2`.

---

# Core Idea

The key challenge is removing a node when we are given its position **from the end**.

We can solve this in **one pass** using two pointers:

```text
fast
slow
```

The important idea is to maintain a gap of exactly `n` nodes between `fast` and `slow`.

When `fast` reaches the end:

```text
slow → node before the node we want to remove
```

This is especially useful because to remove a node from a singly linked list, we need access to the **previous node**.

Then we can simply do:

```java
slow.next = slow.next.next;
```

---

# Why Do We Use a Dummy Node?

We create:

```java
ListNode dummy = new ListNode(-1);
dummy.next = head;
```

So the structure becomes:

```text
dummy → 1 → 2 → 3 → 4 → 5
```

The dummy node makes removing the first node much easier.

For example:

```text
head = [1,2,3]
n = 3
```

The node we need to remove is the first node.

Without a dummy node, we would need special handling for:

```text
head = head.next;
```

With the dummy node:

```text
dummy → 1 → 2 → 3
   ↑
 slow
```

We can use the exact same deletion operation:

```java
slow.next = slow.next.next;
```

---

# Java Solution

```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {

        ListNode dummy = new ListNode(-1);
        dummy.next = head;

        ListNode fast = dummy;
        ListNode slow = dummy;

        for(int i = 0; i < n; i++){
            fast = fast.next;
        }

        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }
}
```

---

# Step-by-Step Explanation

## 1. Create the dummy node

```java
ListNode dummy = new ListNode(-1);
dummy.next = head;
```

For:

```text
1 → 2 → 3 → 4 → 5
```

we get:

```text
dummy → 1 → 2 → 3 → 4 → 5
```

Then:

```java
ListNode fast = dummy;
ListNode slow = dummy;
```

Both pointers start at the dummy node.

```text
dummy → 1 → 2 → 3 → 4 → 5
  ↑
fast
  ↑
slow
```

---

# 2. Move fast n steps

```java
for(int i = 0; i < n; i++){
    fast = fast.next;
}
```

Suppose:

```text
n = 2
```

Initially:

```text
dummy → 1 → 2 → 3 → 4 → 5
  ↑
fast
  ↑
slow
```

Move `fast` once:

```text
dummy → 1 → 2 → 3 → 4 → 5
  ↑       ↑
 slow    fast
```

Move `fast` again:

```text
dummy → 1 → 2 → 3 → 4 → 5
  ↑           ↑
 slow        fast
```

Now there is a gap of exactly `n` nodes between `slow` and `fast`.

---

# 3. Move both pointers together

Now we execute:

```java
while(fast.next != null){
    fast = fast.next;
    slow = slow.next;
}
```

The two pointers move at the same speed.

Current state:

```text
dummy → 1 → 2 → 3 → 4 → 5
  ↑           ↑
 slow        fast
```

Move once:

```text
dummy → 1 → 2 → 3 → 4 → 5
      ↑           ↑
     slow        fast
```

Move again:

```text
dummy → 1 → 2 → 3 → 4 → 5
          ↑           ↑
         slow        fast
```

Move again:

```text
dummy → 1 → 2 → 3 → 4 → 5
              ↑           ↑
             slow        fast
```

Move again:

```text
dummy → 1 → 2 → 3 → 4 → 5
                  ↑       ↑
                 slow    fast
```

Now:

```text
fast.next == null
```

So the loop stops.

The important result is:

```text
slow → node before the node to remove
```

Here:

```text
dummy → 1 → 2 → 3 → 4 → 5
                  ↑
                 slow
```

The node after `slow` is `4`, which is the 2nd node from the end.

---

# 4. Remove the node

Now we perform:

```java
slow.next = slow.next.next;
```

Before:

```text
3 → 4 → 5
    ↑
  remove
```

After:

```text
3 → 5
```

The final list becomes:

```text
1 → 2 → 3 → 5
```

---

# Why `slow.next = slow.next.next` Works

Suppose:

```text
slow → A → B
```

We want to remove `A`.

Currently:

```text
slow.next = A
```

and:

```text
A.next = B
```

So:

```java
slow.next = slow.next.next;
```

means:

```text
slow.next = B;
```

Therefore:

```text
slow → B
```

and `A` is disconnected from the list.

This is the standard linked-list deletion pattern.

---

# Why the Gap Is `n`

This is the most important part of the problem.

We move:

```text
fast = n steps
```

ahead of `slow`.

So:

```text
fast
  ↓
[---------------- n ----------------]
                                     ↓
                                    slow
```

When `fast` reaches the end, `slow` must be positioned `n` nodes behind it.

That means:

```text
slow = node before nth node from the end
```

For example:

```text
1 → 2 → 3 → 4 → 5
```

with:

```text
n = 2
```

we want:

```text
slow → 3
              fast → 5
```

Then:

```text
slow.next = 4
```

and `4` is the node to remove.

---

# Why `while(fast.next != null)`?

We use:

```java
while(fast.next != null)
```

instead of:

```java
while(fast != null)
```

because we want `slow` to stop at the **node before the target**.

If we allowed `fast` to move until it became `null`, `slow` would move one position too far.

We need:

```text
fast → last node
```

not:

```text
fast → null
```

Therefore:

```java
while(fast.next != null)
```

is exactly what we need.

---

# Edge Cases

## 1. Removing the only node

```text
head = [1]
n = 1
```

Structure:

```text
dummy → 1
  ↑
 slow
```

After moving `fast` one step:

```text
dummy → 1
  ↑
 slow

fast → 1
```

Since:

```text
fast.next == null
```

the loop doesn't execute.

Then:

```java
slow.next = slow.next.next;
```

becomes:

```text
dummy.next = 1.next
```

which is:

```text
dummy.next = null
```

Result:

```text
[]
```

---

## 2. Removing the first node

```text
head = [1,2,3]
n = 3
```

The target is the first node.

The dummy node allows us to handle it normally:

```text
dummy → 1 → 2 → 3
  ↑
 slow
```

Then:

```java
slow.next = slow.next.next;
```

becomes:

```text
dummy.next = 2
```

Result:

```text
2 → 3
```

No special case is required.

---

## 3. Removing the last node

```text
head = [1,2,3]
n = 1
```

The target is `3`.

Eventually:

```text
dummy → 1 → 2 → 3
              ↑
             slow
```

Then:

```java
slow.next = slow.next.next;
```

removes `3`.

Result:

```text
1 → 2
```

---

# One-Pass Requirement

The problem asks:

> Could you do this in one pass?

Yes.

The two-pointer approach satisfies this requirement.

We don't need to:

```text
1. Calculate the length
2. Find the target
3. Delete it
```

Instead:

```text
Dummy
  ↓
slow ────────────────→ target's previous node
         n gap
                     fast → end
```

Everything is done in one traversal after establishing the pointer gap.

---

# Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

`fast` moves forward and then both pointers traverse the list.

Overall, the work is linear.

### Space Complexity

```text
O(1)
```

Only:

```text
dummy
fast
slow
```

are used.

No additional data structure is required.

---

# Pattern Recognition

This problem is a classic example of the:

## Two-Pointer Fixed-Gap Pattern

When you see:

```text
nth node from the end
```

immediately think:

```text
Fast + Slow
```

The general pattern is:

```text
1. Create a dummy node if deletion may involve head.
2. Put fast and slow at dummy.
3. Move fast n steps.
4. Move fast and slow together.
5. Stop when fast reaches the last node.
6. slow is the node before the target.
7. Delete using slow.next = slow.next.next.
```

---

# Important Difference From 1721

This problem is closely related to the previous problem:

```text
1721. Swapping Nodes in a Linked List
```

Both use a fixed-gap two-pointer technique.

But the goal is different.

### 1721

We need:

```text
first  → kth node from beginning
second → kth node from end
```

Then:

```text
swap(first.val, second.val)
```

### 19

We need:

```text
slow → node before nth node from end
```

Then:

```text
slow.next = slow.next.next
```

So the same two-pointer idea can solve different linked-list problems depending on **where we want the slow pointer to stop**.

---

# Key Takeaway

The most important mental model is:

```text
Need nth node from the end?
        ↓
Create a gap of n.
        ↓
Move both pointers together.
        ↓
Fast reaches the end.
        ↓
Slow reaches the node before the target.
        ↓
Delete slow.next.
```

The dummy node makes the solution handle:

```text
Deleting head
Deleting middle
Deleting tail
Deleting the only node
```

using the same deletion operation:

```java
slow.next = slow.next.next;
```

This is one of the most reusable patterns in linked-list problems.

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
```

---

# Final Mental Model

For linked-list problems involving positions from the end:

```text
              Fixed Gap
                 ↓
       ┌──────────────────┐
       │                  │
fast ──┘                  │
                          │
slow ─────────────────────┘
```

For this problem:

```text
fast starts n nodes ahead
        ↓
fast and slow move together
        ↓
fast reaches the last node
        ↓
slow = node before target
        ↓
slow.next = slow.next.next
```

Remember the two key lines:

```java
for(int i = 0; i < n; i++){
    fast = fast.next;
}
```

and:

```java
slow.next = slow.next.next;
```

Together, they turn the problem into a clean `O(n)` time and `O(1)` space solution.
