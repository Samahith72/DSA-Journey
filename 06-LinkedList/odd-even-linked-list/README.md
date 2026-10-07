# WIN #84 — 328. Odd Even Linked List

[LeetCode — 328. Odd Even Linked List](https://leetcode.com/problems/odd-even-linked-list/)

**Difficulty:** Medium
**Topic:** Linked List, Two Pointers, Pointer Manipulation

---

## Problem

Given the `head` of a singly linked list, group all nodes with **odd indices** together, followed by all nodes with **even indices**.

The first node has index `1`, so:

```text
1 → Odd
2 → Even
3 → Odd
4 → Even
5 → Odd
...
```

The relative order inside both groups must remain unchanged.

You must solve the problem with:

```text
Time Complexity  → O(n)
Space Complexity → O(1)
```

### Example 1

```text
Input:
1 → 2 → 3 → 4 → 5

Output:
1 → 3 → 5 → 2 → 4
```

The odd-indexed nodes are:

```text
1 → 3 → 5
```

The even-indexed nodes are:

```text
2 → 4
```

We then connect:

```text
1 → 3 → 5 → 2 → 4
```

### Example 2

```text
Input:
2 → 1 → 3 → 5 → 6 → 4 → 7

Output:
2 → 3 → 6 → 7 → 1 → 5 → 4
```

Odd indices:

```text
2 → 3 → 6 → 7
```

Even indices:

```text
1 → 5 → 4
```

Final:

```text
2 → 3 → 6 → 7 → 1 → 5 → 4
```

---

# Core Idea

We maintain two separate linked-list sequences while traversing the original list:

```text
Odd-indexed nodes
Even-indexed nodes
```

We use three pointers:

```text
odd
even
evenHead
```

### `odd`

Points to the current last node in the odd-indexed group.

### `even`

Points to the current last node in the even-indexed group.

### `evenHead`

Stores the first node of the even group.

This is important because after building the odd group, we need to attach the entire even group to its end.

The structure starts as:

```text
1 → 2 → 3 → 4 → 5
↑   ↑
odd even
```

We remember:

```text
evenHead = 2
```

Then we rearrange the pointers to create:

```text
1 → 3 → 5

2 → 4
```

Finally:

```text
1 → 3 → 5 → 2 → 4
```

---

# Java Solution

```java
class Solution {
    public ListNode oddEvenList(ListNode head) {

        if(head == null || head.next == null){
            return head;
        }

        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;

        while(even != null && even.next != null){
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;
        return head;
    }
}
```

---

# Step-by-Step Explanation

## 1. Handle small lists

```java
if(head == null || head.next == null){
    return head;
}
```

If the list is empty:

```text
[]
```

there is nothing to rearrange.

If there is only one node:

```text
1
```

it is already an odd-indexed group.

So we simply return `head`.

---

# 2. Initialize the pointers

```java
ListNode odd = head;
ListNode even = head.next;
ListNode evenHead = even;
```

For:

```text
1 → 2 → 3 → 4 → 5
```

we have:

```text
odd
 ↓
1 → 2 → 3 → 4 → 5
    ↑
   even
```

And:

```text
evenHead → 2
```

The important distinction is:

```text
odd
```

will move as we build the odd group.

```text
even
```

will move as we build the even group.

```text
evenHead
```

never moves.

It permanently remembers where the even group starts.

---

# Why Do We Need `evenHead`?

Suppose the list is:

```text
1 → 2 → 3 → 4 → 5
```

As we move `even`:

```text
even → 2
```

then:

```text
even → 4
```

Eventually `even` becomes:

```text
null
```

If we didn't save the original even head, we would lose access to:

```text
2 → 4
```

That's why we store:

```java
ListNode evenHead = even;
```

At the end:

```java
odd.next = evenHead;
```

connects the two groups.

---

# 3. Process the list

The loop is:

```java
while(even != null && even.next != null)
```

We need both:

```text
even
```

and:

```text
even.next
```

because we are going to use `even.next` as the next odd node.

The structure is:

```text
odd → even → nextOdd → nextEven
```

We need both `even` and `even.next` to exist before changing the pointers.

---

# 4. Connect the current odd node to the next odd node

```java
odd.next = even.next;
```

Initially:

```text
1 → 2 → 3 → 4 → 5
↑   ↑   ↑
odd even nextOdd
```

We change:

```text
odd.next
```

from:

```text
2
```

to:

```text
3
```

So:

```text
1 → 3 → 4 → 5
```

while the even group still starts at:

```text
2 → 3 → 4 → 5
```

The next step will fix the even group.

---

# 5. Move the odd pointer

```java
odd = odd.next;
```

Now:

```text
1 → 3 → 4 → 5
    ↑
   odd
```

`odd` is now pointing to the newly added odd-indexed node.

---

# 6. Connect the current even node to the next even node

```java
even.next = odd.next;
```

Remember:

```text
odd.next
```

is currently the next even node.

For our example:

```text
odd → 3 → 4
```

So:

```java
even.next = odd.next;
```

makes:

```text
2 → 4
```

Now the two groups are becoming:

```text
Odd:
1 → 3

Even:
2 → 4
```

---

# 7. Move the even pointer

```java
even = even.next;
```

Now:

```text
2 → 4
    ↑
   even
```

The pointers are ready to process the next pair.

---

# Complete First Iteration

Starting list:

```text
1 → 2 → 3 → 4 → 5
↑   ↑
odd even
```

### Step 1

```java
odd.next = even.next;
```

```text
1 → 3
    ↓
```

### Step 2

```java
odd = odd.next;
```

```text
1 → 3
    ↑
   odd
```

### Step 3

```java
even.next = odd.next;
```

```text
2 → 4
```

### Step 4

```java
even = even.next;
```

```text
2 → 4
    ↑
   even
```

Now:

```text
Odd group:
1 → 3

Even group:
2 → 4
```

Node `5` is still waiting to be connected.

---

# Second Iteration

Current structure:

```text
1 → 3 → 4 → 5
    ↑
   odd

2 → 4
    ↑
   even
```

The next odd node is:

```text
even.next = 5
```

So:

```java
odd.next = even.next;
```

produces:

```text
1 → 3 → 5
```

Then:

```java
odd = odd.next;
```

Now:

```text
1 → 3 → 5
        ↑
       odd
```

Next:

```java
even.next = odd.next;
```

But `5.next` is currently `null`.

Therefore:

```text
2 → 4 → null
```

Then:

```java
even = even.next;
```

gives:

```text
even = null
```

The loop ends.

---

# 8. Connect the Odd Group to the Even Group

At this point:

```text
Odd group:

1 → 3 → 5


Even group:

2 → 4
```

We saved the beginning of the even group in:

```text
evenHead
 ↓
2 → 4
```

So:

```java
odd.next = evenHead;
```

connects them:

```text
1 → 3 → 5 → 2 → 4
```

Finally:

```java
return head;
```

returns the reordered list.

---

# Pointer Visualization

The easiest way to understand this problem is to think in terms of two chains.

Initially:

```text
1 → 2 → 3 → 4 → 5
```

We split it logically into:

```text
Odd chain:

1 → 3 → 5


Even chain:

2 → 4
```

Then connect:

```text
Odd chain
    ↓
1 → 3 → 5 → 2 → 4
              ↑
          Even chain
```

The important part is that we never create new nodes.

We only modify existing `next` pointers.

---

# Why Does the Relative Order Stay the Same?

The problem requires:

```text
Odd nodes:
1 → 3 → 5

Even nodes:
2 → 4
```

The order inside each group must remain unchanged.

Our algorithm always adds the **next odd node** to the end of the odd group and the **next even node** to the end of the even group.

Therefore:

```text
1 → 3 → 5
```

remains in the original order.

And:

```text
2 → 4
```

also remains in the original order.

We are only moving the two groups relative to each other.

---

# Why Is This O(1) Extra Space?

We only use:

```text
odd
even
evenHead
```

No:

```text
Array
Stack
Queue
List
```

is created.

Therefore:

```text
Space = O(1)
```

This is especially important because the problem explicitly requires constant extra space.

---

# Why Is This O(n)?

Every node is visited a constant number of times.

The pointers move forward through the list:

```text
odd → forward
even → forward
```

They never move backward.

Therefore:

```text
Time = O(n)
```

---

# Edge Cases

## 1. Empty list

```text
[]
```

The condition:

```java
if(head == null || head.next == null)
```

returns immediately.

---

## 2. One node

```text
1
```

There is no even-indexed node.

Result:

```text
1
```

---

## 3. Two nodes

```text
1 → 2
```

There is one odd node and one even node.

The result remains:

```text
1 → 2
```

---

## 4. Three nodes

```text
1 → 2 → 3
```

Odd group:

```text
1 → 3
```

Even group:

```text
2
```

Final:

```text
1 → 3 → 2
```

---

## 5. Even number of nodes

```text
1 → 2 → 3 → 4
```

Odd group:

```text
1 → 3
```

Even group:

```text
2 → 4
```

Final:

```text
1 → 3 → 2 → 4
```

---

# Pattern Recognition

This problem teaches the **Linked List Grouping / Reordering** pattern.

Instead of creating separate arrays, we can build multiple linked-list chains using pointers.

The general idea is:

```text
Original list
     ↓
Separate into groups
     ↓
Build each group by rewiring next pointers
     ↓
Connect the groups
```

Here:

```text
Group 1 → Odd-indexed nodes
Group 2 → Even-indexed nodes
```

---

# Connection With Previous Linked List Problems

This problem builds on several patterns you've already learned.

### 21. Merge Two Sorted Lists

You learned how to maintain a `tail` pointer while building a linked-list structure.

### 92. Reverse Linked List II

You learned how important correct pointer reconnection is when modifying a list.

### 24. Swap Nodes in Pairs

You learned how to rearrange nodes by changing their `next` pointers rather than their values.

### 328. Odd Even Linked List

Now we extend that idea by maintaining **two linked-list chains simultaneously** and connecting them at the end.

---

# Key Takeaway

The most important mental model is:

```text
Original:

1 → 2 → 3 → 4 → 5 → 6


Separate into:

Odd:
1 → 3 → 5

Even:
2 → 4 → 6


Connect:

1 → 3 → 5 → 2 → 4 → 6
```

The three important pointers are:

```text
odd
even
evenHead
```

Their jobs are:

```text
odd
↓
Build the odd-indexed chain

even
↓
Build the even-indexed chain

evenHead
↓
Remember where the even chain started
```

The core pointer operations are:

```java
odd.next = even.next;
odd = odd.next;

even.next = odd.next;
even = even.next;
```

Finally:

```java
odd.next = evenHead;
```

This gives:

```text
Time  → O(n)
Space → O(1)
```

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
```

---

# Final Mental Model

When you see a linked-list problem asking you to **reorder nodes while preserving the order inside groups**, think:

```text
1. Identify the groups
2. Maintain a pointer for each group
3. Rewire next pointers
4. Preserve the head of every group
5. Connect the groups at the end
```

For this problem:

```text
head
 ↓
odd → odd → odd

evenHead
    ↓
even → even → even
```

Then:

```text
odd tail.next = evenHead
```

The entire list is reorganized without creating a single new list node.

That is the core pointer-manipulation pattern behind this problem.
