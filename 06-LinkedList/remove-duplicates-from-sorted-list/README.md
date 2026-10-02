# WIN #69 — Remove Duplicates from Sorted List

## Problem

[LeetCode 83 — Remove Duplicates from Sorted List](https://leetcode.com/problems/remove-duplicates-from-sorted-list/)

Given the `head` of a **sorted** linked list, remove all duplicate values so that every element appears only once.

The resulting linked list must remain sorted.

For example:

```text
1 → 1 → 2
```

should become:

```text
1 → 2
```

Because the list is already sorted, all duplicate values will appear **next to each other**.

This property makes it possible to solve the problem using a simple traversal with two pointers.

---

# Example 1

```text
Input:
head = [1,1,2]

Output:
[1,2]
```

Initial list:

```text
1 → 1 → 2 → null
```

The first two nodes contain the same value:

```text
1 → 1 → 2
    ↑
 duplicate
```

We remove the second `1`:

```text
1 → 2 → null
```

---

# Example 2

```text
Input:
head = [1,1,2,3,3]

Output:
[1,2,3]
```

Initial:

```text
1 → 1 → 2 → 3 → 3
```

Remove the duplicate `1`:

```text
1 → 2 → 3 → 3
```

Then remove the duplicate `3`:

```text
1 → 2 → 3
```

Final answer:

```text
[1,2,3]
```

---

# My Java Solution

```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {

        if(head == null){
            return head;
        }

        ListNode current = head.next;
        ListNode prev = head;

        while(current != null){

            if(current.val == prev.val){

                prev.next = prev.next.next;
                current = current.next;

            }else{

                prev = prev.next;
                current = current.next;
            }
        }

        return head;
    }
}
```

---

# Core Idea

The most important thing to notice is that the linked list is **sorted**.

For example:

```text
1 → 1 → 2 → 3 → 3 → 4
```

Because the list is sorted, duplicate values are always adjacent:

```text
1 → 1
      ↑
    duplicate

3 → 3
      ↑
    duplicate
```

Therefore, we don't need a `HashSet` to remember which values we have already seen.

We only need to compare the current node with the previous unique node.

---

# Two Pointers

We use:

```java
ListNode prev = head;
ListNode current = head.next;
```

Think of them as:

```text
prev
 ↓
1 → 1 → 2 → 3
    ↑
  current
```

The job of the pointers is:

```text
prev    → last unique node
current → node currently being checked
```

---

# Case 1 — Duplicate Found

Suppose we have:

```text
1 → 1 → 2
↑   ↑
prev current
```

We check:

```java
if(current.val == prev.val)
```

Since:

```text
current.val = 1
prev.val    = 1
```

they are equal.

Therefore, `current` is a duplicate.

We remove it using:

```java
prev.next = prev.next.next;
```

Before:

```text
prev
 ↓
1 → 1 → 2
    ↑
 current
```

After:

```text
prev
 ↓
1 ─────→ 2
```

The duplicate node is skipped.

---

# Why `prev.next = prev.next.next` Works

Suppose:

```text
prev → duplicate → next
```

For example:

```text
1 → 1 → 2
```

Here:

```text
prev.next
   ↓
duplicate
```

and:

```text
prev.next.next
        ↓
       2
```

So:

```java
prev.next = prev.next.next;
```

changes:

```text
1 → 1 → 2
```

into:

```text
1 ─────→ 2
```

The duplicate node is no longer connected to the list.

---

# Case 2 — Different Value

Suppose:

```text
1 → 2 → 3
↑   ↑
prev current
```

We check:

```java
if(current.val == prev.val)
```

Here:

```text
1 != 2
```

so there is no duplicate.

We move both pointers:

```java
prev = prev.next;
current = current.next;
```

Now:

```text
1 → 2 → 3
    ↑   ↑
   prev current
```

We continue checking.

---

# Complete Walkthrough

Consider:

```text
1 → 1 → 2 → 3 → 3
```

Initially:

```text
prev
 ↓
1 → 1 → 2 → 3 → 3
    ↑
 current
```

### Step 1

Compare:

```text
1 == 1
```

Duplicate found.

Remove the second `1`:

```text
1 → 2 → 3 → 3
```

Pointers remain logically at:

```text
prev
 ↓
1 → 2 → 3 → 3
    ↑
 current
```

---

### Step 2

Compare:

```text
1 != 2
```

Move both:

```text
1 → 2 → 3 → 3
    ↑   ↑
   prev current
```

---

### Step 3

Compare:

```text
2 != 3
```

Move both:

```text
1 → 2 → 3 → 3
        ↑   ↑
       prev current
```

---

### Step 4

Compare:

```text
3 == 3
```

Duplicate found.

Remove the second `3`:

```text
1 → 2 → 3
```

Now:

```text
current = null
```

The loop ends.

Return:

```text
1 → 2 → 3
```

---

# Why Does `prev` Stay in Place After Removing a Duplicate?

This is an important detail.

Suppose:

```text
1 → 1 → 1 → 2
```

Initially:

```text
prev
 ↓
1 → 1 → 1 → 2
    ↑
 current
```

The first duplicate is found.

We remove it:

```text
1 → 1 → 2
```

We **do not move `prev`** because there may be another duplicate immediately after it.

Now:

```text
prev
 ↓
1 → 1 → 2
    ↑
 current
```

Again:

```text
1 == 1
```

Remove it:

```text
1 → 2
```

Only after encountering a different value should `prev` move forward.

This is why the code has:

```java
if(current.val == prev.val){

    prev.next = prev.next.next;
    current = current.next;

}else{

    prev = prev.next;
    current = current.next;
}
```

---

# Why Does This Work Only Because the List Is Sorted?

Consider a sorted list:

```text
1 → 1 → 2 → 3 → 3
```

Duplicates are adjacent.

So when we see:

```text
current.val == prev.val
```

we know that `current` is a duplicate.

But imagine an unsorted list:

```text
1 → 2 → 1 → 3
```

The duplicate `1` is not next to the first `1`.

A simple adjacent comparison would not detect it.

For an unsorted linked list, we would need a different strategy, such as using a `HashSet`.

Therefore, the key property here is:

```text
Sorted List
    ↓
Duplicates are adjacent
    ↓
Compare neighboring values
```

---

# Why No HashSet?

For an unsorted list:

```text
1 → 2 → 1 → 3
```

we might use:

```java
HashSet<Integer> set;
```

to remember values we have already encountered.

But here:

```text
1 → 1 → 2 → 3 → 3
```

the sorted property already tells us where duplicates are.

Therefore, a `HashSet` is unnecessary.

This allows us to solve the problem with:

```text
O(1)
```

extra space.

---

# Important Pointer Operation

The most important line in this problem is:

```java
prev.next = prev.next.next;
```

Think of it as:

```text
Before:

prev
 ↓
A → B → C

        ↓
      remove B


After:

prev
 ↓
A ─────→ C
```

This is one of the most important operations to understand when manipulating linked lists.

We aren't physically deleting the Java object ourselves.

We are simply changing the links so that the duplicate node is no longer part of the reachable list.

---

# Why Return `head`?

We never change the first node.

For example:

```text
1 → 1 → 2
```

After removing duplicates:

```text
1 → 2
```

The original `head` still points to the first `1`.

Therefore:

```java
return head;
```

is enough.

---

# Edge Cases

## Empty List

```text
head = null
```

The code handles this immediately:

```java
if(head == null){
    return head;
}
```

Therefore:

```text
null
```

is returned.

---

## One Node

```text
1 → null
```

There is nothing to remove.

Return:

```text
1 → null
```

---

## No Duplicates

```text
1 → 2 → 3 → 4
```

Every adjacent value is different.

The pointers simply move forward until the end.

Result:

```text
1 → 2 → 3 → 4
```

---

## All Values Are Duplicates

Consider:

```text
1 → 1 → 1 → 1
```

The algorithm repeatedly removes duplicates:

```text
1 → 1 → 1 → 1
```

↓

```text
1 → 1 → 1
```

↓

```text
1 → 1
```

↓

```text
1
```

Final result:

```text
1
```

---

# Complexity

Let:

```text
n = number of nodes
```

We traverse the linked list once.

Therefore:

```text
Time Complexity: O(n)
```

We only use two pointers:

```text
prev
current
```

No additional data structure is required.

Therefore:

```text
Space Complexity: O(1)
```

---

# Connection With Previous Linked List Problems

You have now learned another useful linked-list manipulation pattern.

### 206. Reverse Linked List

```text
Reverse pointers
```

```text
current.next = prev;
```

### 876. Middle of the Linked List

```text
Fast & Slow Pointers
```

```text
slow = slow.next;
fast = fast.next.next;
```

### 141. Linked List Cycle

```text
Fast & Slow Pointers
+
Detect when they meet
```

### 234. Palindrome Linked List

```text
Find middle
     ↓
Reverse second half
     ↓
Compare
```

### 83. Remove Duplicates

```text
Sorted List
     ↓
Compare adjacent nodes
     ↓
Skip duplicate node
```

---

# Pattern Recognition

When a linked-list problem says:

```text
The linked list is sorted
```

and asks you to:

```text
Remove duplicates
```

think:

```text
Duplicates are adjacent
        ↓
Compare current with previous
        ↓
Same?
 ├── Yes → Skip current
 └── No  → Move forward
```

The basic pattern is:

```java
ListNode prev = head;
ListNode current = head.next;

while(current != null){

    if(current.val == prev.val){

        prev.next = prev.next.next;
        current = current.next;

    }else{

        prev = prev.next;
        current = current.next;
    }
}
```

---

# Key Insight

The most important observation is:

> **Because the linked list is sorted, all duplicates appear next to each other. Therefore, we only need to compare each node with the previous unique node.**

The entire solution can be visualized as:

```text
Sorted Linked List
        ↓
Duplicates are adjacent
        ↓
Compare current.val
with prev.val
        ↓
   Same?
  /     \
Yes      No
 ↓        ↓
Skip     Move
node     both
 ↓        ↓
Continue Continue
```

---

# Final Takeaway

The key operation is:

```java
prev.next = prev.next.next;
```

which skips the duplicate node.

The complete approach is:

```text
1. Start with prev at head.
2. Start current at head.next.
3. Compare current with prev.
4. If values are equal, skip current.
5. Otherwise move both pointers.
6. Continue until current becomes null.
7. Return head.
```

### Complexity

```text
Time  : O(n)
Space : O(1)
```

### Pattern

```text
Sorted Linked List
        ↓
Adjacent Comparison
        ↓
Duplicate?
   ├── Yes → Skip Node
   └── No  → Move Forward
```

**Important Linked List patterns learned so far:**

```text
206 → Reverse Linked List
876 → Fast & Slow Pointers
141 → Floyd's Cycle Detection
234 → Find Middle + Reverse + Compare
83  → Sorted List + Pointer Manipulation
```
