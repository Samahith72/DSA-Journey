# WIN #75 — 237. Delete Node in a Linked List

[LeetCode 237 — Delete Node in a Linked List](https://leetcode.com/problems/delete-node-in-a-linked-list/)

Difficulty: Medium
Topic: Linked List

---

# Problem

You are given a singly linked list and a reference to the node that needs to be deleted.

The important constraint is:

> You are NOT given access to the `head` of the linked list.

You are only given the node that needs to be deleted.

Also, the given node is guaranteed to **not be the last node**.

The goal is to make the linked list appear as if that node was deleted.

---

# Example 1

```text id="v8c5kz"
Input:
head = [4,5,1,9]
node = 5

Output:
[4,1,9]
```

Original:

```text id="5jjq2s"
4 → 5 → 1 → 9
    ↑
   node
```

Expected:

```text id="0v3h7q"
4 → 1 → 9
```

---

# Example 2

```text id="f4h9s2"
Input:
head = [4,5,1,9]
node = 1

Output:
[4,5,9]
```

Original:

```text id="8zq1mt"
4 → 5 → 1 → 9
        ↑
       node
```

Expected:

```text id="7l0q4r"
4 → 5 → 9
```

---

# Core Idea

Normally, to delete a node from a singly linked list, we need access to the **previous node**.

For example:

```text id="m1x8qp"
4 → 5 → 1 → 9
    ↑   ↑
 previous node
```

To delete `5`, we would normally do:

```java
previous.next = node.next;
```

But this problem does not give us the previous node.

We only have:

```text id="g5k3xw"
4 → 5 → 1 → 9
    ↑
   node
```

So how can we delete `5`?

The trick is:

> Instead of deleting the current node directly, copy the value of the next node into the current node, then remove the next node.

---

# The Trick

Suppose:

```text id="6zv0u8"
4 → 5 → 1 → 9
    ↑
   node
```

We cannot remove `5` directly because we don't know the previous node.

Instead, copy `1` into the node containing `5`.

Before:

```text id="w8n3ka"
4 → 5 → 1 → 9
    ↑
   node
```

After:

```text id="x6q0fd"
4 → 1 → 1 → 9
    ↑
   node
```

Now the original `5` value is gone.

Then skip the next node:

```text id="h7p2md"
4 → 1 ─────→ 9
    ↑
   node
```

The final list is:

```text id="s2q5ne"
4 → 1 → 9
```

From the perspective of the linked list, it is exactly as if the original node was deleted.

---

# Java Solution

```java id="p8y4nm"
class Solution {
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }
}
```

---

# Step-by-Step Explanation

## Step 1: Copy the Next Node's Value

```java id="d4f7qa"
node.val = node.next.val;
```

Suppose:

```text id="r1v8kp"
4 → 5 → 1 → 9
    ↑
   node
```

Here:

```text id="z7m2sc"
node.val = 5
node.next.val = 1
```

After:

```java id="n3x6yt"
node.val = node.next.val;
```

the list conceptually becomes:

```text id="b6k9rw"
4 → 1 → 1 → 9
    ↑
   node
```

The node itself has not changed.

Only its value has changed.

---

# Step 2: Skip the Next Node

Now:

```text id="q4s8vb"
4 → 1 → 1 → 9
        ↑
      duplicate
```

We don't need the next node anymore.

So:

```java id="w5p1kc"
node.next = node.next.next;
```

changes the links from:

```text id="m0q7dz"
node → next → next.next
```

to:

```text id="j3f8ns"
node ─────────→ next.next
```

Therefore:

```text id="c8v2lp"
4 → 1 → 9
```

---

# Why Does This Work?

The problem does not actually require the physical object `node` to disappear from memory.

It requires that:

* The value of the given node should no longer exist in the list.
* The number of nodes should decrease by one.
* Nodes before it remain in the same order.
* Nodes after it remain in the same order.

By copying the next node's value into the current node and removing the next node, all of these conditions are satisfied.

Consider:

```text id="a4k7px"
4 → 5 → 1 → 9
    ↑
   node
```

After:

```text id="q9m2lc"
4 → 1 → 9
```

The list has effectively removed `5`.

---

# Visual Understanding

Suppose:

```text id="f7q3mz"
node
 ↓
[5] → [1] → [9]
```

### Copy

```text id="n2x8cv"
node
 ↓
[1] → [1] → [9]
```

### Skip the next node

```text id="u6p4ka"
node
 ↓
[1] ─────→ [9]
```

Final:

```text id="k1r5vd"
[1] → [9]
```

The original `5` is no longer part of the list.

---

# Why Can't We Delete the Given Node Directly?

Because we don't have its previous node.

Normally:

```text id="s7c3qb"
previous → node → next
```

To delete `node`, we need:

```java
previous.next = node.next;
```

But the problem gives us only:

```text id="z4m9yt"
node → next
```

There is no way to move backward in a singly linked list.

So we use the next node to simulate deletion.

---

# Important Constraint: Node Cannot Be the Last Node

The problem guarantees:

```text id="e6w2qp"
node != tail
```

This is absolutely necessary.

Why?

Our solution needs:

```java
node.next
```

If `node` were the last node:

```text id="q8k1vr"
4 → 5 → 1
        ↑
       node
```

then:

```text id="r2m7xs"
node.next == null
```

We cannot copy a value from a nonexistent next node.

Therefore, the guarantee that `node` is not the tail makes this technique possible.

---

# Example Walkthrough

Consider:

```text id="a5j8nc"
4 → 5 → 1 → 9
    ↑
   node
```

### Initial state

```text id="b7r3mp"
node.val = 5
node.next.val = 1
```

### Copy

```java id="q2v6kt"
node.val = node.next.val;
```

Now:

```text id="c9x4wf"
4 → 1 → 1 → 9
    ↑
   node
```

### Skip

```java id="n8p5yd"
node.next = node.next.next;
```

Now:

```text id="v3m7qa"
4 → 1 → 9
```

Final answer:

```text id="j6r2kp"
[4,1,9]
```

---

# Another Example

Input:

```text id="s8f3nc"
4 → 5 → 1 → 9
        ↑
       node
```

We want to remove `1`.

### Copy `9`

```text id="x5q1mv"
4 → 5 → 9 → 9
        ↑
       node
```

### Skip next

```text id="d7k4pb"
4 → 5 → 9
```

Final result:

```text id="y2n8cr"
[4,5,9]
```

---

# Important Observation

We are not actually deleting the object represented by `node`.

Instead:

```text id="q6m1vw"
Change current node
        +
Remove next node
```

So:

```java
node.val = node.next.val;
node.next = node.next.next;
```

is effectively:

```text id="n5x9ka"
Delete current node
```

from the perspective of the linked-list structure.

This is a clever example of modifying the representation instead of directly performing the requested operation.

---

# Edge Cases

## 1. Node Is the Second-Last Node

Example:

```text id="z3c8pv"
1 → 2 → 3 → 4
        ↑
       node
```

Copy `4`:

```text id="r7m2lx"
1 → 2 → 4 → 4
```

Then skip the last node:

```text id="k9q4wf"
1 → 2 → 4
```

Works correctly.

---

## 2. Only Two Nodes Exist

Example:

```text id="p6v1ma"
1 → 2
↑
node
```

The given node can only be the first node because it cannot be the tail.

Copy `2`:

```text id="t8x3qn"
2 → 2
```

Then:

```java
node.next = node.next.next;
```

gives:

```text id="h4m7yc"
2
```

Correct.

---

## 3. Given Node Is the Tail

This case is explicitly forbidden.

```text
1 → 2 → 3
        ↑
       node
```

There is no `node.next`, so the technique cannot work.

The problem guarantees this never happens.

---

# Common Mistake

A common mistake is trying to write:

```java
node = node.next;
```

This does not delete anything from the linked list.

It only changes the local reference variable.

For example:

```java
node = node.next;
```

does not modify:

```text
previous.next
```

and therefore does not change the actual linked list.

The correct approach modifies the actual node:

```java
node.val = node.next.val;
node.next = node.next.next;
```

---

# Pattern Recognition

Whenever you see:

> Delete a node from a singly linked list, but you are only given the node itself.

Think:

```text
No previous pointer
        ↓
Use the next node
        ↓
Copy next value
        ↓
Skip next node
```

The template is:

```java
node.val = node.next.val;
node.next = node.next.next;
```

---

# Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(1)
```

Only two operations are performed:

```java
node.val = node.next.val;
node.next = node.next.next;
```

We don't traverse the list.

### Space Complexity

```text
O(1)
```

No additional data structures are used.

Only the given node is modified.

---

# Comparison With Normal Linked List Deletion

### Normal deletion

Usually we need:

```text
previous → node → next
```

and perform:

```java
previous.next = node.next;
```

### This problem

We only have:

```text
node → next
```

So we perform:

```java
node.val = node.next.val;
node.next = node.next.next;
```

This gives us:

```text
Normal:
previous.next = node.next

This problem:
node.val = node.next.val
node.next = node.next.next
```

---

# Key Takeaway

This problem teaches a very unusual but important linked-list technique.

When you are given the node to delete but **not its previous node**, you cannot perform the normal deletion operation.

Instead:

```text
1. Copy the next node's value into the current node.
2. Skip the next node.
```

The entire solution is:

```java
node.val = node.next.val;
node.next = node.next.next;
```

The mental model to remember is:

```text
Given:
A → B → C

Want to delete B.

Normally:
A.next = B.next

But we don't have A.

Instead:
B.val = C.val

A → C → C

Then:
B.next = C.next

A → C
```

So the general pattern is:

```text
Cannot access previous node
        ↓
Copy next node
        ↓
Delete next node
        ↓
O(1) time
O(1) space
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
```
