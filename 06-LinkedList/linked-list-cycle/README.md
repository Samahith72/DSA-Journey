# WIN #67 — Linked List Cycle

## Problem

[LeetCode 141 — Linked List Cycle](https://leetcode.com/problems/linked-list-cycle/)

Given the `head` of a singly linked list, determine whether the linked list contains a **cycle**.

A cycle exists when we can reach the same node again by continuously following the `next` pointer.

For example:

```text
1 → 2 → 3 → 4
        ↑     ↓
        └─────┘
```

Here, node `4` points back to node `3`, so the linked list contains a cycle.

The problem asks us to return:

```text
true  → if a cycle exists
false → if no cycle exists
```

The follow-up asks us to solve the problem using:

```text
O(1) extra space
```

---

# Example 1

```text
Input:
head = [3,2,0,-4]
pos = 1

Output:
true
```

The tail points back to the node at index `1`:

```text
3 → 2 → 0 → -4
    ↑         ↓
    └─────────┘
```

Therefore, the list contains a cycle.

---

# Example 2

```text
Input:
head = [1,2]
pos = 0

Output:
true
```

The last node points back to the first node:

```text
1 → 2
↑   ↓
└───┘
```

Therefore:

```text
true
```

---

# Example 3

```text
Input:
head = [1]
pos = -1

Output:
false
```

There is no cycle:

```text
1 → null
```

Therefore:

```text
false
```

---

# My Java Solution

```java
public class Solution {
    public boolean hasCycle(ListNode head) {

        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null){

            slow = slow.next;
            fast = fast.next.next;

            if(fast == slow){
                return true;
            }
        }

        return false;
    }
}
```

---

# Core Idea

This problem uses the **Fast and Slow Pointer** technique, also known as **Floyd's Cycle Detection Algorithm**.

We use two pointers:

```text
slow
fast
```

Both start at the head.

But they move at different speeds:

```text
slow → moves 1 node at a time

fast → moves 2 nodes at a time
```

The important observation is:

> **If a cycle exists, the fast pointer will eventually catch the slow pointer.**

If there is no cycle, the fast pointer will eventually reach:

```text
null
```

---

# Why Two Pointers?

Imagine two runners on a circular track.

One runner moves:

```text
1 step
```

and the other moves:

```text
2 steps
```

The faster runner will eventually catch the slower runner.

A linked-list cycle behaves like a circular track.

For example:

```text
1 → 2 → 3 → 4
        ↑     ↓
        └─────┘
```

Once both pointers enter the cycle, `fast` keeps gaining on `slow`.

Eventually:

```text
fast == slow
```

Therefore, a cycle exists.

---

# Initial Setup

We create:

```java
ListNode fast = head;
ListNode slow = head;
```

For:

```text
1 → 2 → 3 → 4 → null
```

initially:

```text
slow
 ↓
1 → 2 → 3 → 4 → null

fast
 ↓
1 → 2 → 3 → 4 → null
```

Both start at the same node.

---

# Pointer Movement

Inside the loop:

```java
slow = slow.next;
fast = fast.next.next;
```

So:

```text
slow → 1 step
fast → 2 steps
```

This is the same Fast & Slow Pointer pattern used in **876. Middle of the Linked List**.

But here we use it for a different purpose:

```text
876 → Find the middle
141 → Detect a cycle
```

---

# Detecting a Cycle

Consider:

```text
1 → 2 → 3 → 4
        ↑     ↓
        └─────┘
```

The cycle is:

```text
3 → 4 → 3 → 4 → ...
```

After some movements, both pointers enter the cycle.

For example:

```text
slow → 3
fast → 4
```

Next:

```text
slow → 4
fast → 4
```

Now:

```java
if(fast == slow)
```

becomes:

```text
true
```

So we return:

```java
return true;
```

---

# Why Will They Always Meet?

Once both pointers are inside the cycle, imagine the cycle as a circular track.

Suppose:

```text
slow moves 1 step
fast moves 2 steps
```

The relative speed is:

```text
2 - 1 = 1 step
```

So every iteration, `fast` gets one position closer to `slow`.

Because the cycle has a finite number of nodes, eventually:

```text
fast == slow
```

Therefore, a cycle is guaranteed to be detected.

---

# What Happens Without a Cycle?

Consider:

```text
1 → 2 → 3 → 4 → null
```

There is no cycle.

The pointers move:

```text
Start:

slow = 1
fast = 1
```

After one iteration:

```text
slow = 2
fast = 3
```

After another:

```text
slow = 3
fast = null
```

Now the loop condition:

```java
while(fast != null && fast.next != null)
```

becomes false.

We return:

```java
return false;
```

Therefore:

```text
No cycle → false
```

---

# Why Do We Check `fast != null && fast.next != null`?

We move:

```java
fast = fast.next.next;
```

Therefore, before doing that, we need to ensure:

```text
fast exists
```

and:

```text
fast.next exists
```

So we use:

```java
while(fast != null && fast.next != null)
```

This prevents accessing a `null` reference.

If either condition fails, the fast pointer has reached the end of the list.

That means:

```text
No cycle exists
```

---

# Important Difference Between `fast == slow` and `fast.val == slow.val`

We check:

```java
if(fast == slow)
```

not:

```java
if(fast.val == slow.val)
```

This is extremely important.

Two different nodes can have the same value:

```text
1 → 2 → 3 → 2 → null
```

The two nodes containing `2` are different objects.

We need to determine whether the **same node** is reached again.

Therefore, we compare the references:

```text
fast == slow
```

not their values.

---

# Example Walkthrough

Consider:

```text
1 → 2 → 3 → 4 → 5
        ↑         ↓
        └─────────┘
```

The cycle starts at node `3`.

### Start

```text
slow = 1
fast = 1
```

### Iteration 1

```text
slow = 2
fast = 3
```

### Iteration 2

```text
slow = 3
fast = 5
```

### Iteration 3

```text
slow = 4
fast = 4
```

Now:

```java
fast == slow
```

is true.

Therefore:

```text
Cycle detected
```

and we return:

```text
true
```

---

# Visualizing the Chase

Think of the cycle as:

```text
        ┌─────────────┐
        ↓             │
3 → 4 → 5 → 3 → 4 → 5
```

Inside the cycle:

```text
slow → 1 step
fast → 2 steps
```

So the faster pointer keeps moving around the cycle until it catches the slower pointer.

```text
        fast
          ↓
3 → 4 → 5 → 3
    ↑
   slow
```

Eventually:

```text
fast
  ↓
3
↑
slow
```

Same node → cycle detected.

---

# Why This Uses O(1) Space

A common alternative is to use a `HashSet`:

```text
Store every visited node
        ↓
If node appears again
        ↓
Cycle detected
```

That approach requires:

```text
O(n)
```

extra memory.

Our solution only uses:

```text
slow
fast
```

Therefore:

```text
Space Complexity: O(1)
```

This satisfies the follow-up requirement.

---

# HashSet vs Fast & Slow Pointers

| Approach    |   Time |  Space | Idea                                  |
| ----------- | -----: | -----: | ------------------------------------- |
| HashSet     | `O(n)` | `O(n)` | Store visited nodes                   |
| Fast & Slow | `O(n)` | `O(1)` | Two pointers move at different speeds |

The Fast & Slow Pointer approach detects the cycle without storing visited nodes.

---

# Pattern Recognition

This is another important **Fast & Slow Pointer** problem.

When you see:

```text
Linked List
+
Cycle
```

immediately think:

```text
Fast & Slow Pointers
```

The basic pattern is:

```java
ListNode slow = head;
ListNode fast = head;

while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;

    if(slow == fast){
        return true;
    }
}

return false;
```

---

# Connection With 876. Middle of the Linked List

You just solved:

```text
876. Middle of the Linked List
```

using:

```text
slow → 1 step
fast → 2 steps
```

Now:

```text
141. Linked List Cycle
```

uses exactly the same pointer movement.

The difference is what we look for.

### Middle of Linked List

```text
fast reaches the end
        ↓
slow is the middle
```

### Linked List Cycle

```text
fast catches slow
        ↓
cycle exists
```

This is a very important DSA pattern because the **same technique can solve multiple linked-list problems**.

---

# Edge Cases

## Empty List

```text
head = null
```

Then:

```text
fast = null
slow = null
```

The loop does not execute.

Return:

```text
false
```

---

## One Node Without Cycle

```text
1 → null
```

There is no cycle.

Return:

```text
false
```

---

## One Node With Cycle

A node points to itself:

```text
┌────┐
↓    │
1 ───┘
```

Initially:

```text
slow = 1
fast = 1
```

The loop executes:

```text
slow → 1
fast → 1
```

Therefore:

```java
fast == slow
```

is true.

Return:

```text
true
```

---

## Two Nodes With Cycle

```text
1 → 2
↑   ↓
└───┘
```

The pointers eventually meet.

Return:

```text
true
```

---

# The Key Insight

The most important thing to remember is:

> **If two pointers move around a cycle at different speeds, the faster pointer must eventually catch the slower pointer.**

The algorithm is:

```text
Start
  ↓
slow = head
fast = head
  ↓
Move slow by 1
Move fast by 2
  ↓
Did they meet?
 ├── Yes → Cycle exists
 │
 └── No
      ↓
Did fast reach null?
 ├── Yes → No cycle
 └── No  → Continue
```

---

# Final Takeaway

This is one of the fundamental **Linked List + Two Pointer** problems.

Remember:

```java
ListNode slow = head;
ListNode fast = head;

while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;

    if(fast == slow){
        return true;
    }
}

return false;
```

The pattern is:

```text
Linked List
     ↓
Fast & Slow Pointers
     ↓
slow → 1 step
fast → 2 steps
     ↓
Same node?
 ├── Yes → Cycle
 └── No
      ↓
fast reaches null?
 ├── Yes → No cycle
 └── No  → Continue
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
Two Pointers
    ↓
Fast & Slow Pointer
    ↓
Floyd's Cycle Detection
    ↓
Detect Cycle
```
