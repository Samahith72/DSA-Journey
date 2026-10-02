# WIN #68 — Palindrome Linked List

## Problem

[LeetCode 234 — Palindrome Linked List](https://leetcode.com/problems/palindrome-linked-list/)

Given the `head` of a singly linked list, return `true` if the linked list is a **palindrome**, otherwise return `false`.

A palindrome reads the same from left to right and right to left.

For example:

```text
1 → 2 → 2 → 1
```

is a palindrome because:

```text
1 → 2 → 2 → 1
↑           ↑
same        same
```

But:

```text
1 → 2
```

is not a palindrome.

---

# Example 1

```text
Input:
head = [1,2,2,1]

Output:
true
```

The linked list is:

```text
1 → 2 → 2 → 1
```

Reading from both directions gives:

```text
1 → 2 → 2 → 1
```

Therefore, it is a palindrome.

---

# Example 2

```text
Input:
head = [1,2]

Output:
false
```

The linked list is:

```text
1 → 2
```

Forward:

```text
1 → 2
```

Backward:

```text
2 → 1
```

They are different, so the answer is:

```text
false
```

---

# My Java Solution

```java
class Solution {
    public boolean isPalindrome(ListNode head) {

        ListNode slow = head;
        ListNode fast = head;

        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode prev = null;
        ListNode current = slow;

        while(current != null){
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }

        ListNode ptr = head;

        while(prev != null){
            if(ptr.val != prev.val){
                return false;
            }

            ptr = ptr.next;
            prev = prev.next;
        }

        return true;
    }
}
```

---

# Core Idea

A palindrome linked list can be checked by comparing:

```text
First half
   ↓
Forward

Second half
   ↓
Backward
```

The problem is that a singly linked list can only naturally move **forward**.

For example:

```text
1 → 2 → 2 → 1 → null
```

We can move:

```text
1 → 2 → 2 → 1
```

but we cannot move backward:

```text
1 ← 2 ← 2 ← 1
```

So we need to find a way to compare the two halves.

The solution uses **three important techniques**:

```text
1. Fast & Slow Pointers
2. Reverse the second half
3. Compare both halves
```

---

# Step 1 — Find the Middle

We start with:

```java
ListNode slow = head;
ListNode fast = head;
```

Then:

```java
while(fast != null && fast.next != null){

    slow = slow.next;
    fast = fast.next.next;
}
```

The pointers move at different speeds:

```text
slow → 1 step
fast → 2 steps
```

Therefore, when `fast` reaches the end, `slow` reaches the middle.

---

# Example

Consider:

```text
1 → 2 → 2 → 1
```

Initially:

```text
slow = 1
fast = 1
```

After one iteration:

```text
slow = 2
fast = 2
```

After the second iteration:

```text
slow = 2
fast = null
```

So `slow` points to the beginning of the second half.

Conceptually:

```text
1 → 2 | 2 → 1
        ↑
       slow
```

---

# Step 2 — Reverse the Second Half

Now we start from:

```java
ListNode current = slow;
```

and reverse the list from `slow` onwards.

The reversal code is:

```java
ListNode prev = null;
ListNode current = slow;

while(current != null){

    ListNode next = current.next;
    current.next = prev;
    prev = current;
    current = next;
}
```

This is the same **Linked List Reversal** technique used in:

```text
206. Reverse Linked List
```

---

# Why Reverse the Second Half?

Suppose the list is:

```text
1 → 2 → 2 → 1
```

After finding the middle:

```text
1 → 2 | 2 → 1
        ↑
       slow
```

We reverse the second half:

```text
1 → 2 | 1 → 2
```

Now both halves can be traversed forward:

```text
First half:   1 → 2

Second half:  1 → 2
```

This makes comparison easy.

---

# Step 3 — Compare Both Halves

After reversing, `prev` points to the beginning of the reversed second half.

We create:

```java
ListNode ptr = head;
```

Then:

```java
while(prev != null){

    if(ptr.val != prev.val){
        return false;
    }

    ptr = ptr.next;
    prev = prev.next;
}
```

We compare:

```text
ptr
 ↓
First half

prev
 ↓
Reversed second half
```

---

# Example Walkthrough

Consider:

```text
1 → 2 → 2 → 1
```

### Find Middle

After Fast & Slow:

```text
1 → 2 | 2 → 1
        ↑
       slow
```

### Reverse Second Half

```text
1 → 2 | 1 → 2
```

Now:

```text
head
 ↓
1 → 2

prev
 ↓
1 → 2
```

### Compare

First comparison:

```text
ptr.val  = 1
prev.val = 1
```

Same.

Move both:

```text
ptr  → 2
prev → 2
```

Second comparison:

```text
ptr.val  = 2
prev.val = 2
```

Same.

The second half has been completely checked.

Therefore:

```text
true
```

---

# Non-Palindrome Example

Consider:

```text
1 → 2 → 3 → 4
```

After finding the middle:

```text
1 → 2 | 3 → 4
        ↑
       slow
```

Reverse the second half:

```text
1 → 2 | 4 → 3
```

Now compare:

```text
First half:   1 → 2

Second half:  4 → 3
```

First comparison:

```text
1 != 4
```

Therefore:

```java
return false;
```

---

# Why Do We Only Compare While `prev != null`?

The code uses:

```java
while(prev != null)
```

rather than traversing the entire first half.

This works because `prev` represents the reversed second half.

For a palindrome:

```text
1 → 2 → 2 → 1
```

we only need to compare:

```text
1 ↔ 1
2 ↔ 2
```

Once the second half ends, all necessary comparisons have been completed.

---

# Odd-Length Lists

Consider:

```text
1 → 2 → 3 → 2 → 1
```

There is a single middle element:

```text
1 → 2 → 3 → 2 → 1
        ↑
       middle
```

After the Fast & Slow Pointer traversal, `slow` points to:

```text
3
```

The solution reverses:

```text
3 → 2 → 1
```

into:

```text
1 → 2 → 3
```

Then it compares:

```text
First traversal:
1 → 2 → 3

Reversed part:
1 → 2 → 3
```

The middle node does not cause a problem because we only compare while the reversed second portion still has nodes.

---

# Even-Length Lists

For:

```text
1 → 2 → 2 → 1
```

we get:

```text
1 → 2 | 2 → 1
```

The second half is:

```text
2 → 1
```

After reversing:

```text
1 → 2
```

Now:

```text
First half:   1 → 2
Second half:  1 → 2
```

The values match.

---

# The Three Techniques Together

This problem combines three important Linked List patterns:

```text
                    Palindrome
                        ↓
              ┌─────────┴─────────┐
              ↓                   ↓
       Fast & Slow             Reverse
        Pointers              Linked List
              ↓                   ↓
         Find Middle       Reverse 2nd Half
              └─────────┬─────────┘
                        ↓
                    Compare
```

This is why this problem is important for building Linked List fundamentals.

---

# Connection With Previous Problems

You have already solved the two main building blocks.

### 876. Middle of the Linked List

Used:

```text
Fast & Slow Pointers
```

```java
slow = slow.next;
fast = fast.next.next;
```

### 206. Reverse Linked List

Used:

```text
prev
current
next
```

```java
ListNode next = current.next;
current.next = prev;
prev = current;
current = next;
```

### 234. Palindrome Linked List

Combines both:

```text
876
 ↓
Find middle
 ↓
206
 ↓
Reverse second half
 ↓
Compare
```

This is a good example of **combining known patterns to solve a harder problem**.

---

# Why Not Use an ArrayList?

A simpler solution would be:

```text
Linked List
     ↓
Copy all values into ArrayList
     ↓
Compare from both ends
```

For example:

```text
1 → 2 → 2 → 1

ArrayList:
[1, 2, 2, 1]
```

Then check:

```text
left → 1
right → 1

left → 2
right → 2
```

This works, but it requires:

```text
O(n)
```

extra space.

Our solution reverses the second half **in place**, so we don't need an additional data structure.

---

# Complexity

Let:

```text
n = number of nodes
```

### Finding the middle

```text
O(n)
```

### Reversing the second half

```text
O(n)
```

### Comparing both halves

```text
O(n)
```

Overall:

```text
Time Complexity: O(n)
```

Each part is linear, and:

```text
O(n) + O(n) + O(n) = O(n)
```

---

# Space Complexity

We only use a few pointers:

```text
slow
fast
prev
current
next
ptr
```

The number of pointers is constant.

Therefore:

```text
Space Complexity: O(1)
```

This is an **in-place** solution.

---

# Important Pointer Pattern

The reversal part is:

```java
ListNode prev = null;
ListNode current = slow;

while(current != null){

    ListNode next = current.next;
    current.next = prev;
    prev = current;
    current = next;
}
```

Remember this pattern:

```text
Before:

prev       current
 ↓            ↓
null         1 → 2 → 3 → null

After one iteration:

       prev    current
        ↓        ↓
null ← 1        2 → 3
```

Continue until:

```text
3 → 2 → 1
```

and `prev` points to the new head.

---

# Edge Cases

## Empty List

```text
head = null
```

The problem's constraints normally provide a list, but conceptually an empty list is a palindrome.

---

## One Node

```text
1 → null
```

A single value is always a palindrome.

```text
true
```

---

## Two Equal Nodes

```text
1 → 1
```

Both values match:

```text
true
```

---

## Two Different Nodes

```text
1 → 2
```

Values don't match:

```text
false
```

---

# Key Insight

The most important idea is:

> **To check a palindrome in O(1) extra space, find the middle, reverse the second half, and compare it with the first half.**

The complete thought process is:

```text
Linked List
     ↓
Find Middle
     ↓
Reverse Second Half
     ↓
Compare First Half
with Reversed Second Half
     ↓
Mismatch?
 ├── Yes → false
 └── No  → true
```

---

# Final Takeaway

This problem is a combination of two patterns you already know:

```text
Fast & Slow Pointers
+
Reverse Linked List
```

The complete pattern is:

```text
1 → 2 → 2 → 1
```

Find middle:

```text
1 → 2 | 2 → 1
        ↑
       slow
```

Reverse second half:

```text
1 → 2 | 1 → 2
```

Compare:

```text
1 ↔ 1
2 ↔ 2
```

Therefore:

```text
true
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
Fast & Slow Pointers
    ↓
Find Middle
    ↓
Reverse Second Half
    ↓
Compare Both Halves
    ↓
Palindrome?
```

**Important patterns learned so far:**

```text
206 → Reverse Linked List
876 → Fast & Slow Pointers
141 → Floyd's Cycle Detection
234 → Combine Fast & Slow + Reversal
```
