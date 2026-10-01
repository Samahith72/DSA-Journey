# WIN #65 — Reverse Linked List

## Problem

[LeetCode 206 — Reverse Linked List](https://leetcode.com/problems/reverse-linked-list/)

Given the `head` of a singly linked list, reverse the linked list and return the new head.

For example:

```text
1 → 2 → 3 → 4 → 5
```

should become:

```text
5 → 4 → 3 → 2 → 1
```

The problem can be solved using both:

* **Iterative approach**
* **Recursive approach**

This solution uses the **iterative approach**.

---

# Example 1

```text
Input:
head = [1,2,3,4,5]

Output:
[5,4,3,2,1]
```

Before:

```text
1 → 2 → 3 → 4 → 5 → null
```

After:

```text
5 → 4 → 3 → 2 → 1 → null
```

---

# Example 2

```text
Input:
head = [1,2]

Output:
[2,1]
```

Before:

```text
1 → 2 → null
```

After:

```text
2 → 1 → null
```

---

# Example 3

```text
Input:
head = []

Output:
[]
```

An empty linked list is already reversed.

---

# My Java Solution — Iterative

```java
class Solution {
    public ListNode reverseList(ListNode head) {

        ListNode prev = null;
        ListNode current = head;

        while(current != null){

            ListNode next = current.next;

            current.next = prev;

            prev = current;
            current = next;
        }

        return prev;
    }
}
```

---

# Core Idea

The key idea is to reverse every `next` pointer one node at a time.

Initially:

```text
1 → 2 → 3 → 4 → 5 → null
```

We want:

```text
1 ← 2 ← 3 ← 4 ← 5
```

The problem is that if we change:

```text
1.next
```

we could lose access to the rest of the list.

Therefore, before changing the pointer, we first save the next node.

This is why we use three pointers:

```text
prev
current
next
```

Their roles are:

```text
prev    → Previous node in the reversed list
current → Node currently being processed
next    → Saves the remaining original list
```

---

# The Three-Pointer Technique

Initially:

```java
ListNode prev = null;
ListNode current = head;
```

For:

```text
1 → 2 → 3 → 4 → 5 → null
```

we start with:

```text
prev
 ↓
null

current
 ↓
1 → 2 → 3 → 4 → 5 → null
```

---

# Step 1 — Save the Next Node

```java
ListNode next = current.next;
```

Suppose:

```text
current = 1
```

Then:

```text
next = 2
```

We now have:

```text
prev     current    next
 ↓          ↓         ↓
null       1  →      2 → 3 → 4 → 5
```

The `next` pointer is important because we are about to change `current.next`.

---

# Step 2 — Reverse the Pointer

```java
current.next = prev;
```

Before:

```text
1 → 2
```

After:

```text
1 → null
```

Now:

```text
prev
 ↓
null

current
 ↓
1 → null

next
 ↓
2 → 3 → 4 → 5
```

The first node has now been reversed.

---

# Step 3 — Move `prev`

```java
prev = current;
```

Now:

```text
prev
 ↓
1 → null
```

`prev` represents the beginning of the reversed portion.

---

# Step 4 — Move `current`

```java
current = next;
```

Now:

```text
prev
 ↓
1 → null

current
 ↓
2 → 3 → 4 → 5 → null
```

We have successfully processed node `1`.

---

# First Iteration

The complete first iteration is:

```java
ListNode next = current.next;
current.next = prev;
prev = current;
current = next;
```

Starting:

```text
prev = null
current = 1
```

Ending:

```text
prev = 1 → null
current = 2 → 3 → 4 → 5
```

---

# Second Iteration

Now:

```text
prev
 ↓
1 → null

current
 ↓
2 → 3 → 4 → 5 → null
```

Save next:

```text
next = 3
```

Reverse:

```text
2 → 1 → null
```

Move pointers:

```text
prev
 ↓
2 → 1 → null

current
 ↓
3 → 4 → 5 → null
```

---

# Third Iteration

Starting:

```text
prev
 ↓
2 → 1 → null

current
 ↓
3 → 4 → 5 → null
```

Save:

```text
next = 4
```

Reverse:

```text
3 → 2 → 1 → null
```

Move:

```text
prev
 ↓
3 → 2 → 1 → null

current
 ↓
4 → 5 → null
```

---

# Fourth Iteration

Starting:

```text
prev
 ↓
3 → 2 → 1 → null

current
 ↓
4 → 5 → null
```

After processing:

```text
prev
 ↓
4 → 3 → 2 → 1 → null

current
 ↓
5 → null
```

---

# Fifth Iteration

Starting:

```text
prev
 ↓
4 → 3 → 2 → 1 → null

current
 ↓
5 → null
```

Save:

```text
next = null
```

Reverse:

```text
5 → 4 → 3 → 2 → 1 → null
```

Move:

```text
prev
 ↓
5 → 4 → 3 → 2 → 1 → null

current = null
```

The loop stops because:

```java
current != null
```

is now false.

---

# Why Do We Return `prev`?

At the end:

```text
prev
 ↓
5 → 4 → 3 → 2 → 1 → null
```

while:

```text
current = null
```

Therefore, the new head of the reversed linked list is:

```text
5
```

which is stored in:

```text
prev
```

So we return:

```java
return prev;
```

---

# Complete Pointer Movement

For:

```text
1 → 2 → 3 → 4 → 5 → null
```

the algorithm gradually builds the reversed list:

```text
null

1 → null

2 → 1 → null

3 → 2 → 1 → null

4 → 3 → 2 → 1 → null

5 → 4 → 3 → 2 → 1 → null
```

At every step:

```text
current node
     ↓
reverse its pointer
     ↓
move it to prev
     ↓
move current forward
```

---

# Why `next` Is Necessary

Consider:

```text
1 → 2 → 3 → 4
```

If we immediately do:

```java
current.next = prev;
```

for node `1`, we get:

```text
1 → null
```

Without saving:

```java
ListNode next = current.next;
```

we would lose access to:

```text
2 → 3 → 4
```

Therefore, always remember:

> **Save the next node before changing the current node's `next` pointer.**

This is one of the most important linked-list pointer techniques.

---

# Pointer Invariant

During the algorithm, we maintain two separate portions:

```text
Reversed portion       Remaining portion
      ↓                       ↓
prev                   current
      ↓                       ↓
3 → 2 → 1 → null       4 → 5 → null
```

The invariant is:

```text
prev
```

contains the already reversed part, while:

```text
current
```

points to the first node that has not yet been reversed.

Each iteration moves exactly one node from the remaining portion to the reversed portion.

---

# Why This Is O(1) Space

We only create three references:

```java
prev
current
next
```

We do not create another linked list.

We reverse the existing links in place.

Therefore:

```text
Space Complexity: O(1)
```

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

We only use a constant number of pointers:

```text
Space Complexity: O(1)
```

---

# Edge Cases

## Empty List

```text
head = null
```

Then:

```text
current = null
```

The loop does not execute.

We return:

```text
prev = null
```

Result:

```text
[]
```

---

## One Node

```text
1 → null
```

Processing gives:

```text
1 → null
```

The same node becomes the head.

---

## Two Nodes

```text
1 → 2 → null
```

After reversal:

```text
2 → 1 → null
```

---

# Recursive Approach

The follow-up also asks whether the linked list can be reversed recursively.

Yes.

The recursive idea is to first reverse the rest of the list and then attach the current node to the end.

```java
class Solution {
    public ListNode reverseList(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }

        ListNode newHead = reverseList(head.next);

        head.next.next = head;
        head.next = null;

        return newHead;
    }
}
```

---

# Recursive Base Case

```java
if(head == null || head.next == null){
    return head;
}
```

There are two cases where we stop:

### Empty list

```text
null
```

### Last node

```text
5 → null
```

The last node becomes the new head.

---

# Recursive Example

Consider:

```text
1 → 2 → 3 → null
```

The recursive calls go:

```text
reverse(1)
    ↓
reverse(2)
    ↓
reverse(3)
```

At node `3`:

```text
3 → null
```

we return `3`.

Then while returning from recursion:

```text
2 → 3
```

becomes:

```text
3 → 2
```

Then:

```text
1 → 2
```

becomes:

```text
3 → 2 → 1
```

The critical operation is:

```java
head.next.next = head;
```

For:

```text
1 → 2
```

this means:

```text
2.next = 1
```

Then:

```java
head.next = null;
```

breaks the old forward link:

```text
1 → 2
```

so we don't create a cycle.

---

# Iterative vs Recursive

| Approach  |   Time |  Space | Main Idea      |
| --------- | -----: | -----: | -------------- |
| Iterative | `O(n)` | `O(1)` | Three pointers |
| Recursive | `O(n)` | `O(n)` | Call stack     |

The iterative approach reverses the list **in place** using:

```text
prev
current
next
```

The recursive approach uses the function call stack.

---

# Pattern Recognition

Whenever you need to modify links in a singly linked list, think carefully about:

```text
current
next
previous
```

For reversing a linked list, the core pattern is:

```text
next = current.next
current.next = prev
prev = current
current = next
```

This four-line pattern is one of the most important linked-list patterns for DSA.

---

# The Key Insight

The most important thing to remember is:

> **Save the next node, reverse the current node's pointer, then move both pointers forward.**

The process is:

```text
             ┌──────────────┐
             ↓              │
current → save next         │
             ↓              │
        reverse pointer     │
             ↓              │
        prev = current      │
             ↓              │
        current = next ─────┘
```

Or simply:

```text
next = current.next
current.next = prev
prev = current
current = next
```

---

# Final Takeaway

This problem is one of the fundamental **Linked List pointer manipulation** problems.

Remember these three pointers:

```text
prev
current
next
```

and their roles:

```text
prev
 ↓
Reversed portion

current
 ↓
Node being processed

next
 ↓
Remaining original list
```

The final pattern is:

```text
1 → 2 → 3 → 4 → 5

        ↓

5 → 4 → 3 → 2 → 1
```

### Complexity

```text
Time  : O(n)
Space : O(1)   // Iterative
```

### Pattern

```text
Linked List
    ↓
Pointer Manipulation
    ↓
Three-Pointer Technique
    ↓
Reverse Links In-Place
```
