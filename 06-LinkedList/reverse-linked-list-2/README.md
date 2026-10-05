# WIN #77 — 92. Reverse Linked List II

[LeetCode 92 — Reverse Linked List II](https://leetcode.com/problems/reverse-linked-list-ii/)

Difficulty: Medium
Topic: Linked List / Reversal

---

# Problem

Given the `head` of a singly linked list and two integers `left` and `right`, reverse the nodes from position `left` to position `right`.

The rest of the linked list must remain unchanged.

The positions are **1-indexed**.

---

# Example 1

```text
Input:
head = [1,2,3,4,5]
left = 2
right = 4

Output:
[1,4,3,2,5]
```

Original:

```text
1 → 2 → 3 → 4 → 5
    └─────────┘
       reverse
```

After reversing positions `2` through `4`:

```text
1 → 4 → 3 → 2 → 5
```

Only this section changed:

```text
2 → 3 → 4
```

became:

```text
4 → 3 → 2
```

---

# Example 2

```text
Input:
head = [5]
left = 1
right = 1

Output:
[5]
```

Since:

```text
left == right
```

there is nothing to reverse.

---

# Core Idea

This problem is a variation of the standard **Reverse Linked List** problem.

In the normal reverse problem, we reverse the entire list:

```text
1 → 2 → 3 → 4 → 5

becomes:

5 → 4 → 3 → 2 → 1
```

Here, we only reverse a specific section:

```text
1 → 2 → 3 → 4 → 5
    └─────────┘
       reverse
```

becomes:

```text
1 → 4 → 3 → 2 → 5
```

The key challenge is reconnecting the reversed section to the nodes before and after it.

The solution uses:

```text
Dummy Node
     ↓
Position before left
     ↓
Head of section
     ↓
Repeated front insertion
```

The clever part is that we reverse the section **in place** by repeatedly moving the next node to the front of the sublist.

---

# Java Solution

```java
class Solution {
    public ListNode reverseBetween(ListNode head, int left, int right) {

        if(head == null || left == right){
            return head;
        }

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy;

        for(int i=1; i< left;i++){
            prev= prev.next;
        }

        ListNode current = prev.next;

        for(int i=0;i < right-left;i++){
            ListNode next = current.next;
            current.next = next.next;
            next.next = prev.next;
            prev.next = next;
        }

        return dummy.next;
    }
}
```

---

# Step 1: Handle Simple Cases

```java
if(head == null || left == right){
    return head;
}
```

There are two cases where we don't need to perform any reversal.

### Empty list

```text
head = null
```

Return immediately.

### Only one position

```text
left == right
```

For example:

```text
1 → 2 → 3 → 4 → 5
        ↑
      left = right = 3
```

There is nothing to reverse.

So we return the original list.

---

# Step 2: Create a Dummy Node

```java
ListNode dummy = new ListNode(0);
dummy.next = head;
```

This gives us:

```text
dummy → 1 → 2 → 3 → 4 → 5
```

Why do we need a dummy node?

Because the reversal may start at the first node.

For example:

```text
left = 1
right = 3
```

We need to reverse:

```text
1 → 2 → 3
```

Without a node before `1`, reconnecting the reversed section becomes more complicated.

The dummy node gives us a node before every possible starting position.

---

# Step 3: Find the Node Before `left`

```java
ListNode prev = dummy;

for(int i = 1; i < left; i++){
    prev = prev.next;
}
```

Suppose:

```text
1 → 2 → 3 → 4 → 5
```

and:

```text
left = 2
right = 4
```

Initially:

```text
dummy → 1 → 2 → 3 → 4 → 5
   ↑
  prev
```

The loop moves `prev` until it reaches the node immediately before position `left`.

So:

```text
dummy → 1 → 2 → 3 → 4 → 5
         ↑
        prev
```

Therefore:

```text
prev = 1
```

and:

```text
prev.next = 2
```

which is the first node of the section we want to reverse.

---

# Step 4: Set `current`

```java
ListNode current = prev.next;
```

For our example:

```text
1 → 2 → 3 → 4 → 5
    ↑
  current
```

`current` represents the first node of the section being reversed.

An important observation:

> `current` stays at the same node during the entire inner loop.

It eventually becomes the **last node of the reversed section**.

---

# The Main Reversal Technique

The most important part of the solution is:

```java
for(int i = 0; i < right - left; i++){
    ListNode next = current.next;
    current.next = next.next;
    next.next = prev.next;
    prev.next = next;
}
```

This is different from the standard three-pointer reversal.

Instead, we repeatedly take the node immediately after `current` and move it to the front of the reversing section.

This is called **front insertion**.

---

# Understand One Iteration

Initial list:

```text
1 → 2 → 3 → 4 → 5
    ↑   ↑
   prev current
```

We want to reverse:

```text
2 → 3 → 4
```

At this point:

```text
prev = 1
current = 2
```

The next node is:

```java
ListNode next = current.next;
```

So:

```text
next = 3
```

We have:

```text
1 → 2 → 3 → 4 → 5
    ↑   ↑
 current next
```

---

# Step 5: Remove `next` From Its Current Position

```java
current.next = next.next;
```

This changes:

```text
2 → 3 → 4
```

into:

```text
2 → 4
```

So the temporary structure is:

```text
1 → 2 → 4 → 5

3
```

The node `3` is temporarily detached from the chain.

---

# Step 6: Insert `next` After `prev`

```java
next.next = prev.next;
```

Currently:

```text
prev → 2 → 4
```

So:

```text
3 → 2
```

Now we have:

```text
3 → 2 → 4
```

---

# Step 7: Connect `prev` to `next`

```java
prev.next = next;
```

Now:

```text
1 → 3 → 2 → 4 → 5
```

We have effectively reversed the first two nodes of the section.

Original section:

```text
2 → 3 → 4
```

Current section:

```text
3 → 2 → 4
```

---

# Visualizing One Iteration

Before:

```text
1 → 2 → 3 → 4 → 5
    ↑   ↑
  current next
```

Remove `3`:

```text
1 → 2 → 4 → 5

3
```

Insert `3` before `2`:

```text
1 → 3 → 2 → 4 → 5
```

This is the fundamental operation.

---

# Second Iteration

We still have:

```text
1 → 3 → 2 → 4 → 5
        ↑   ↑
      current next
```

Notice something important:

```text
current
   ↓
   2
```

`current` has **not moved**.

Now:

```java
next = current.next;
```

gives:

```text
next = 4
```

Remove `4`:

```text
1 → 3 → 2 → 5
```

Then insert `4` after `prev`:

```text
1 → 4 → 3 → 2 → 5
```

Now the entire section is reversed.

---

# Complete Reversal Visualization

We start with:

```text
1 → 2 → 3 → 4 → 5
    ↑
  prev
    ↑
  current
```

### Iteration 1

Move `3` to the front:

```text
1 → 3 → 2 → 4 → 5
         ↑
       current
```

### Iteration 2

Move `4` to the front:

```text
1 → 4 → 3 → 2 → 5
             ↑
           current
```

Done.

The reversed section is:

```text
4 → 3 → 2
```

and the final list is:

```text
1 → 4 → 3 → 2 → 5
```

---

# Why Does the Loop Run `right - left` Times?

Suppose:

```text
left = 2
right = 4
```

The section contains:

```text
2, 3, 4
```

which is 3 nodes.

To reverse 3 nodes using front insertion, we need:

```text
3 - 1 = 2
```

operations.

Therefore:

```java
i < right - left
```

runs exactly 2 times.

In general:

```text
Number of reversal operations = right - left
```

---

# Understanding the Four Pointer Operations

The core of the algorithm is:

```java
ListNode next = current.next;
current.next = next.next;
next.next = prev.next;
prev.next = next;
```

Each line has a specific purpose.

### 1. Save the next node

```java
next = current.next;
```

We need to keep a reference to the node we are about to move.

---

### 2. Remove it from its current position

```java
current.next = next.next;
```

This skips `next`.

---

### 3. Put it at the front of the reversed section

```java
next.next = prev.next;
```

Now `next` points to the current first node of the reversed section.

---

### 4. Connect `prev` to the new first node

```java
prev.next = next;
```

Now the new node becomes the front of the reversed section.

---

# The Important Invariant

During the inner loop:

```text
prev
 ↓
[reversed portion] → current → [unprocessed portion]
```

For example:

```text
1 → 4 → 3 → 2 → 5
↑           ↑   ↑
prev      current remaining
```

More conceptually:

```text
prev → reversed section → current → unprocessed nodes
```

The algorithm repeatedly takes:

```text
current.next
```

and moves it to the front of the reversed section.

This invariant makes the algorithm easier to understand.

---

# What Happens When `left = 1`?

Consider:

```text
head = [1,2,3,4,5]
left = 1
right = 3
```

The dummy node makes this easy:

```text
dummy → 1 → 2 → 3 → 4 → 5
  ↑
 prev
```

Since:

```text
left = 1
```

the loop that finds `prev` doesn't move it.

So:

```text
prev = dummy
```

and:

```text
current = 1
```

The same reversal algorithm works:

```text
dummy → 1 → 2 → 3 → 4 → 5
```

After reversing:

```text
dummy → 3 → 2 → 1 → 4 → 5
```

Finally:

```java
return dummy.next;
```

returns:

```text
3 → 2 → 1 → 4 → 5
```

This is one of the main reasons the dummy node is useful.

---

# What Happens When `right = n`?

Suppose:

```text
1 → 2 → 3 → 4 → 5
left = 2
right = 5
```

We reverse:

```text
2 → 3 → 4 → 5
```

giving:

```text
1 → 5 → 4 → 3 → 2
```

There is no node after the reversed section.

The algorithm still works because:

```java
current.next = next.next;
```

eventually sets:

```text
current.next = null;
```

when `next` is the final node.

---

# Edge Cases

## 1. Empty List

```text
head = null
```

The condition:

```java
if(head == null)
```

returns immediately.

---

## 2. `left == right`

Example:

```text
1 → 2 → 3 → 4
      ↑
left = right = 3
```

No reversal is necessary.

Return:

```text
1 → 2 → 3 → 4
```

---

## 3. Reverse the Entire List

```text
head = [1,2,3,4,5]
left = 1
right = 5
```

Result:

```text
5 → 4 → 3 → 2 → 1
```

The dummy node allows the same algorithm to handle this case.

---

## 4. Reverse Two Nodes

```text
1 → 2 → 3 → 4
left = 2
right = 3
```

Only one iteration is required:

```text
1 → 3 → 2 → 4
```

Because:

```text
right - left = 1
```

---

# Common Mistake

A common mistake is trying to use the normal full-list reversal directly.

For example:

```text
1 → 2 → 3 → 4 → 5
```

If we reverse everything:

```text
5 → 4 → 3 → 2 → 1
```

we have changed nodes that were supposed to remain unchanged.

This problem requires:

```text
reverse only [left, right]
```

So we need to carefully maintain the connection between:

```text
before left
```

and:

```text
after right
```

The `prev` pointer handles the first connection, while `current.next` handles the second.

---

# Why This Is O(1) Space

We don't create any new nodes except:

```java
ListNode dummy = new ListNode(0);
```

All reversal operations happen by modifying existing `next` pointers.

Therefore the auxiliary space is:

```text
O(1)
```

---

# Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

We first move `prev` to position `left - 1`.

Then we perform:

```text
right - left
```

reversal operations.

Overall:

```text
O(left) + O(right - left)
= O(n)
```

### Space Complexity

```text
O(1)
```

Only a constant number of pointers are used.

---

# Pattern Recognition

Whenever you see:

> Reverse only a portion of a singly linked list.

Think:

```text
Dummy Node
    ↓
Find node before left
    ↓
Keep current at start of section
    ↓
Repeatedly move current.next to front
    ↓
Return dummy.next
```

The important variables are:

```text
prev
current
next
```

where:

```text
prev    → node before the reversing section
current → first node of the reversing section
next    → node being moved to the front
```

---

# Important Difference From Reverse Linked List

For LeetCode 206:

```text
Reverse Linked List
```

we used:

```java
ListNode prev = null;
ListNode current = head;

while(current != null){
    ListNode next = current.next;
    current.next = prev;
    prev = current;
    current = next;
}
```

That reverses the **entire list**.

Here we only reverse:

```text
[left, right]
```

So we need additional pointers to preserve the rest of the list.

The key technique is:

```text
Move current.next to the front
```

instead of moving every node using the standard three-pointer approach.

---

# Key Takeaway

The most important concept in this problem is **in-place sublist reversal using front insertion**.

For:

```text
1 → 2 → 3 → 4 → 5
```

and:

```text
left = 2
right = 4
```

we repeatedly take the node after `current` and move it immediately after `prev`.

```text
1 → 2 → 3 → 4 → 5
    ↓
1 → 3 → 2 → 4 → 5
    ↓
1 → 4 → 3 → 2 → 5
```

The core operation is:

```java
ListNode next = current.next;
current.next = next.next;
next.next = prev.next;
prev.next = next;
```

The mental model is:

```text
prev
 ↓
[reversed section] → current → [remaining section]

Take current.next
        ↓
Move it to the front
        ↓
Repeat
```

This allows us to reverse a sublist in one traversal with:

```text
Time:  O(n)
Space: O(1)
```

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
```

---

# Final Mental Model

```text
Find node before left
        ↓
Keep current at left
        ↓
Take current.next
        ↓
Remove it from current's position
        ↓
Insert it after prev
        ↓
Repeat right - left times
        ↓
Return dummy.next
```

For the example:

```text
1 → 2 → 3 → 4 → 5
    └─────────┘
       reverse
```

Think:

```text
1 → [2 3 4] → 5
       ↓
1 → [3 2 4] → 5
       ↓
1 → [4 3 2] → 5
```

Final:

```text
1 → 4 → 3 → 2 → 5
```
