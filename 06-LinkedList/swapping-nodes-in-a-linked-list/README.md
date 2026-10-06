# WIN #80 — 1721. Swapping Nodes in a Linked List

[LeetCode — 1721. Swapping Nodes in a Linked List](https://leetcode.com/problems/swapping-nodes-in-a-linked-list/)

**Difficulty:** Medium
**Topic:** Linked List, Two Pointers

---

## Problem

You are given the `head` of a singly linked list and an integer `k`.

You need to swap the **values** of:

* The `k`th node from the beginning
* The `k`th node from the end

The list is **1-indexed**.

The important point is that we only swap the **values**, not the actual nodes.

### Example

```text
Input:
head = [1,2,3,4,5]
k = 2

Output:
[1,4,3,2,5]
```

The 2nd node from the beginning is `2`.

The 2nd node from the end is `4`.

So:

```text
1 → 2 → 3 → 4 → 5
    ↑           ↑
  first       second

After swapping:

1 → 4 → 3 → 2 → 5
```

---

# Core Idea

The main challenge is finding the `k`th node from the end without first calculating the length of the linked list.

We can solve this using **three pointers**:

```text
fast
first
second
```

### What they represent

* `first` finds the `k`th node from the beginning.
* `fast` is used to create the required gap.
* `second` eventually reaches the `k`th node from the end.

The key idea is:

> Keep `fast` exactly `k - 1` positions ahead of `second`.

Then, when `fast` reaches the last node, `second` will be at the `k`th node from the end.

---

# Java Solution

```java
class Solution {
    public ListNode swapNodes(ListNode head, int k) {

        ListNode fast = head;
        ListNode second = head;
        ListNode first = head;

        for(int i = 0; i < k - 1; i++){
            fast = fast.next;
            first = first.next;
        }

        while(fast.next != null){
            fast = fast.next;
            second = second.next;
        }

        int temp = second.val;
        second.val = first.val;
        first.val = temp;

        return head;
    }
}
```

---

# Step-by-Step Explanation

## 1. Initialize the pointers

```java
ListNode fast = head;
ListNode second = head;
ListNode first = head;
```

Initially, all three pointers point to the first node.

```text
head
 ↓
1 → 2 → 3 → 4 → 5
↑
fast
↑
first
↑
second
```

---

## 2. Find the kth node from the beginning

```java
for(int i = 0; i < k - 1; i++){
    fast = fast.next;
    first = first.next;
}
```

We move both `fast` and `first` `k - 1` times.

Why `k - 1`?

Because the list is 1-indexed.

For:

```text
k = 2
```

We need to move once:

```text
1 → 2 → 3 → 4 → 5
    ↑
  first
    ↑
  fast
```

Now:

```text
first = 2nd node
fast  = 2nd node
```

So `first` has found the `k`th node from the beginning.

---

# 3. Find the kth node from the end

Now we move `fast` and `second` together:

```java
while(fast.next != null){
    fast = fast.next;
    second = second.next;
}
```

At this point, `fast` is already `k - 1` nodes ahead of `second`.

For:

```text
1 → 2 → 3 → 4 → 5
```

After the first loop:

```text
1 → 2 → 3 → 4 → 5
    ↑           ↑
  second      fast
```

Now move both together:

### First movement

```text
1 → 2 → 3 → 4 → 5
        ↑       ↑
      second   fast
```

### Second movement

```text
1 → 2 → 3 → 4 → 5
            ↑   ↑
          second fast
```

### Third movement

```text
1 → 2 → 3 → 4 → 5
                ↑
              second
              fast
```

When `fast` reaches the last node:

```text
1 → 2 → 3 → 4 → 5
            ↑       ↑
          second   fast
```

Therefore:

```text
first  → kth node from beginning
second → kth node from end
```

For `k = 2`:

```text
first  → 2
second → 4
```

---

# 4. Swap the values

Now we simply exchange their values:

```java
int temp = second.val;
second.val = first.val;
first.val = temp;
```

Before:

```text
1 → 2 → 3 → 4 → 5
    ↑       ↑
  first   second
```

After:

```text
1 → 4 → 3 → 2 → 5
```

We don't change any `next` pointers.

We only change:

```text
first.val
second.val
```

---

# Why Does the Two-Pointer Technique Work?

Suppose the list has `n` nodes.

The `k`th node from the beginning is at position:

```text
k
```

The `k`th node from the end is at position:

```text
n - k + 1
```

Instead of calculating `n`, we create a gap of `k - 1` nodes between `fast` and `second`.

Then:

```text
When fast reaches the last node
        ↓
second is k positions from the end
```

This allows us to find the target node in a single traversal.

---

# Visual Pattern

The algorithm can be remembered as:

```text
Step 1:

first and fast move k - 1 positions

1 → 2 → 3 → 4 → 5
    ↑           ↑
  first       fast


Step 2:

second starts from head

1 → 2 → 3 → 4 → 5
↑               ↑
second         fast


Step 3:

Move fast and second together

1 → 2 → 3 → 4 → 5
        ↑       ↑
      second   fast


Step 4:

fast reaches the end

1 → 2 → 3 → 4 → 5
            ↑   ↑
          second fast
```

Therefore:

```text
first  = kth node from beginning
second = kth node from end
```

---

# Edge Cases

### 1. Single node

```text
[1], k = 1
```

Both pointers refer to the same node.

Swapping the value with itself changes nothing.

---

### 2. k = 1

We need to swap:

```text
first node
last node
```

Example:

```text
1 → 2 → 3 → 4 → 5
↑               ↑
first          second
```

---

### 3. k = n

The `k`th node from the beginning is the last node.

The `k`th node from the end is the first node.

The algorithm handles this naturally.

---

### 4. Middle node

If `k` points to the middle node, both `first` and `second` can refer to the same node.

Swapping its value with itself is valid.

---

# Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

We make at most two passes over the list.

Since:

```text
O(n) + O(n) = O(n)
```

the overall complexity is:

```text
O(n)
```

### Space Complexity

```text
O(1)
```

Only a constant number of pointers are used.

No extra array, stack, or linked list is created.

---

# Pattern Recognition

This problem teaches an important linked-list pattern:

## Fixed-Gap Two Pointers

Whenever you need to find something relative to the **end of a linked list**, think:

```text
Fast pointer + Slow pointer
```

or more generally:

```text
Create a fixed gap
        ↓
Move both pointers together
        ↓
Fast reaches the end
        ↓
Slow is at the required position
```

Here:

```text
gap = k - 1
```

So:

```text
fast ───────────────→ end
       k - 1 gap
second ─────────────→ target
```

---

# Key Takeaway

The most important idea is:

> To find the kth node from the end without calculating the length, keep another pointer exactly `k - 1` positions ahead.

The complete mental process is:

```text
1. Move first and fast k - 1 steps
        ↓
2. first is kth from the beginning
        ↓
3. Move fast and second together
        ↓
4. Stop when fast reaches the last node
        ↓
5. second is kth from the end
        ↓
6. Swap their values
```

The solution is efficient because it requires:

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
```

---

# Final Mental Model

For linked-list problems involving a position from the end:

```text
Need kth from beginning?
        ↓
Move directly k - 1 times.

Need kth from end?
        ↓
Create a k - 1 gap.
        ↓
Move both pointers together.
        ↓
When fast reaches the end,
the other pointer is kth from the end.
```

For this problem:

```text
first  → kth from beginning
second → kth from end

swap(first.val, second.val)
```

This is another important variation of the **two-pointer technique** for linked lists.
