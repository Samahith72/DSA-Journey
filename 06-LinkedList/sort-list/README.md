# WIN #90 — 148. Sort List

[LeetCode — 148. Sort List](https://leetcode.com/problems/sort-list/)

**Difficulty:** Medium
**Topic:** Linked List, Merge Sort, Recursion, Fast and Slow Pointers

---

## 1. Problem

Given the `head` of a linked list, return the linked list after sorting it in **ascending order**.

### Example 1

```text
Input:  head = [4,2,1,3]
Output: [1,2,3,4]
```

### Example 2

```text
Input:  head = [-1,5,3,4,0]
Output: [-1,0,3,4,5]
```

### Example 3

```text
Input:  head = []
Output: []
```

---

# 2. Core Idea

The natural sorting algorithms we might think of first, such as Bubble Sort or Selection Sort, would take:

```text
O(n²)
```

time.

But the problem asks whether we can achieve:

```text
O(n log n)
```

time.

For a linked list, **Merge Sort** is a very natural choice.

The idea is:

```text
1. Find the middle of the linked list
2. Split the list into two halves
3. Recursively sort both halves
4. Merge the two sorted halves
```

For example:

```text
4 → 2 → 1 → 3
```

Split:

```text
4 → 2        1 → 3
```

Sort both:

```text
2 → 4        1 → 3
```

Merge:

```text
1 → 2 → 3 → 4
```

---

# 3. Why Merge Sort?

Merge Sort follows the divide-and-conquer strategy.

```text
                    4 → 2 → 1 → 3
                           |
                     Split into halves
                       /          \
                    4 → 2        1 → 3
                     /  \         /  \
                    4    2       1    3
                     \  /         \  /
                    2 → 4        1 → 3
                         \        /
                          \      /
                         1 → 2 → 3 → 4
```

The list keeps getting divided until each part contains only one node.

A single-node list is already sorted.

Then we merge the sorted pieces back together.

---

# 4. Java Solution

```java
class Solution {
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = null;

        ListNode left = sortList(head);
        ListNode right = sortList(slow);

        return merge(left, right);
    }

    private ListNode merge(ListNode left, ListNode right) {
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (left != null && right != null) {
            if (left.val <= right.val) {
                tail.next = left;
                left = left.next;
            } else {
                tail.next = right;
                right = right.next;
            }

            tail = tail.next;
        }

        if (left != null) {
            tail.next = left;
        } else {
            tail.next = right;
        }

        return dummy.next;
    }
}
```

---

# 5. Step 1 — Base Case

```java
if (head == null || head.next == null) {
    return head;
}
```

If the list is:

```text
null
```

there is nothing to sort.

If the list contains one node:

```text
5 → null
```

it is already sorted.

So we return `head`.

This is also what eventually stops the recursion.

---

# 6. Step 2 — Find the Middle

We use the familiar fast and slow pointer technique.

```java
ListNode slow = head;
ListNode fast = head;
ListNode prev = null;
```

The pointers move as:

```text
slow → 1 step
fast → 2 steps
```

```java
while (fast != null && fast.next != null) {
    prev = slow;
    slow = slow.next;
    fast = fast.next.next;
}
```

When the loop finishes:

```text
slow
 ↓
middle of the list
```

and:

```text
prev
 ↓
node immediately before middle
```

---

# 7. Why Do We Need `prev`?

Finding `slow` gives us the beginning of the second half.

But we also need to **break the original list into two separate lists**.

Suppose:

```text
4 → 2 → 1 → 3
        ↑
       slow
```

`prev` points to `2`.

So:

```text
prev.next = null;
```

breaks the list:

```text
4 → 2 → null

1 → 3 → null
```

This is important because otherwise the two recursive calls would still be connected.

---

# 8. Step 3 — Recursively Sort Both Halves

```java
ListNode left = sortList(head);
ListNode right = sortList(slow);
```

The left half is:

```text
4 → 2
```

The right half is:

```text
1 → 3
```

Each half is recursively divided again.

Eventually:

```text
4 → 2
```

becomes:

```text
4
2
```

and:

```text
1 → 3
```

becomes:

```text
1
3
```

Each individual node is already sorted.

---

# 9. Step 4 — Merge the Sorted Lists

After recursion, we have two sorted lists.

For example:

```text
left:
2 → 4

right:
1 → 3
```

Now we call:

```java
return merge(left, right);
```

The `merge()` function combines these two sorted lists into one sorted list.

---

# 10. The `merge()` Function

We create a dummy node:

```java
ListNode dummy = new ListNode(0);
ListNode tail = dummy;
```

The dummy node makes insertion easier because we don't need special handling for the first node.

Initially:

```text
dummy
  ↓
  0
  ↑
 tail
```

---

# 11. Compare the Two Lists

```java
while (left != null && right != null) {
```

As long as both lists have nodes, compare their values.

```java
if (left.val <= right.val) {
    tail.next = left;
    left = left.next;
}
```

If the left node is smaller:

```text
left → 2
right → 3
```

we attach `2`.

```text
dummy → 2
```

Then move:

```java
left = left.next;
```

---

# 12. Move the Tail

After attaching a node:

```java
tail = tail.next;
```

So:

```text
dummy → 2
         ↑
        tail
```

The `tail` pointer always represents the last node in the merged list.

---

# 13. Example of the Merge Process

Suppose:

```text
left:
2 → 4

right:
1 → 3
```

### Compare 2 and 1

```text
2 > 1
```

Take `1`.

```text
merged:
1
```

### Compare 2 and 3

```text
2 < 3
```

Take `2`.

```text
merged:
1 → 2
```

### Compare 4 and 3

```text
4 > 3
```

Take `3`.

```text
merged:
1 → 2 → 3
```

Now:

```text
right = null
```

The remaining left list is:

```text
4
```

Attach it directly.

Final:

```text
1 → 2 → 3 → 4
```

---

# 14. Attach the Remaining Nodes

Once one list becomes empty:

```java
if (left != null) {
    tail.next = left;
} else {
    tail.next = right;
}
```

We don't need to compare anymore.

The remaining list is already sorted.

For example:

```text
merged:
1 → 2 → 3

left:
4 → 5
```

We can simply connect:

```text
1 → 2 → 3 → 4 → 5
```

---

# 15. Why Does `merge()` Return `dummy.next`?

The dummy node is only a helper.

For example:

```text
dummy → 1 → 2 → 3
```

We don't want to return the dummy.

So:

```java
return dummy.next;
```

returns:

```text
1 → 2 → 3
```

---

# 16. Complete Recursion Example

Consider:

```text
4 → 2 → 1 → 3
```

### First split

```text
4 → 2        1 → 3
```

### Split again

```text
4    2       1    3
```

Each node is now a base case.

Then merge:

```text
4 + 2
↓
2 → 4
```

and:

```text
1 + 3
↓
1 → 3
```

Finally:

```text
2 → 4
```

and:

```text
1 → 3
```

are merged:

```text
1 → 2 → 3 → 4
```

---

# 17. Why Is the Time Complexity O(n log n)?

Each level of Merge Sort processes all `n` nodes.

For example:

```text
Level 1 → n nodes
Level 2 → n nodes
Level 3 → n nodes
...
```

The list is divided in half at every level.

The number of levels is:

```text
log n
```

Therefore:

```text
O(n) × O(log n)
```

gives:

```text
O(n log n)
```

---

# 18. Complexity

### Time Complexity

```text
O(n log n)
```

The list is repeatedly divided into halves and the sorted halves are merged.

### Space Complexity

The merging itself uses:

```text
O(1)
```

extra space because the existing nodes are reused.

However, the recursive calls use the call stack.

The recursion depth is:

```text
O(log n)
```

Therefore, for this recursive implementation:

```text
Auxiliary Space = O(log n)
```

This is important because the problem's follow-up asks for **O(1) memory**.

Your current solution achieves:

```text
Time  = O(n log n)
Space = O(log n)
```

The fully iterative bottom-up Merge Sort version can achieve:

```text
Time  = O(n log n)
Space = O(1)
```

---

# 19. Important Observation

Your solution does **not create new list nodes during sorting**.

The merge operation:

```java
tail.next = left;
```

or:

```java
tail.next = right;
```

simply rearranges the existing nodes.

For example:

```text
left:
2 → 4

right:
1 → 3
```

The nodes themselves are not copied.

Their `next` pointers are rearranged to produce:

```text
1 → 2 → 3 → 4
```

This is exactly what makes Merge Sort particularly suitable for linked lists.

---

# 20. Pattern Recognition

When you see:

```text
Linked List
+
Sorting
+
O(n log n)
```

Think:

```text
Merge Sort
```

The standard linked-list Merge Sort pattern is:

```text
Find middle
     ↓
Split
     ↓
Recursively sort left
     ↓
Recursively sort right
     ↓
Merge
```

A very useful template to remember is:

```text
sortList(head)
    ↓
find middle
    ↓
break list
    ↓
sort left
    ↓
sort right
    ↓
merge(left, right)
```

---

# 21. Connection With Previous Problems

This problem combines several linked-list techniques that have already appeared.

### Fast and Slow Pointers

Used in:

* Middle of the Linked List
* Linked List Cycle
* Palindrome Linked List
* Reorder List

Here they are used to:

```text
Find the middle of the list
```

### Pointer Rewiring

We use:

```java
prev.next = null;
```

to split the list.

The merge operation also repeatedly changes:

```java
tail.next
```

to build the sorted list.

### Dummy Node

The merge function uses:

```java
ListNode dummy = new ListNode(0);
```

which is the same useful pattern used in:

* Merge Two Sorted Lists
* Remove Linked List Elements
* Other linked-list construction problems

---

# 22. Linked List Patterns Learned So Far

| Problem                       | Pattern                     |
| ----------------------------- | --------------------------- |
| Reverse Linked List           | Three-pointer reversal      |
| Middle of Linked List         | Fast and slow pointers      |
| Linked List Cycle             | Floyd's cycle detection     |
| Linked List Cycle II          | Visited nodes / cycle entry |
| Palindrome Linked List        | Middle + reverse + compare  |
| Remove Nth Node               | Fixed-gap two pointers      |
| Swap Nodes                    | Fixed-gap two pointers      |
| Reorder List                  | Middle + reverse + merge    |
| Odd Even Linked List          | Multiple pointer chains     |
| Rotate List                   | Circular connection + split |
| Partition List                | Separate chains             |
| Copy List with Random Pointer | HashMap object mapping      |
| Sort List                     | Merge Sort + split + merge  |

The new major pattern is:

```text
Linked List + Divide and Conquer
```

---

# 23. Final Mental Model

For a linked-list sorting problem, think:

```text
Unsorted List
      ↓
Find Middle
      ↓
Split into Two Lists
      ↓
Sort Left        Sort Right
    ↓                ↓
  Sorted           Sorted
    \                /
     \              /
       Merge
         ↓
   Sorted Linked List
```

For:

```text
4 → 2 → 1 → 3
```

the mental process is:

```text
4 → 2 → 1 → 3
       ↓
   split here
    /       \
4 → 2       1 → 3
 ↓            ↓
2 → 4        1 → 3
    \        /
     \      /
    1 → 2 → 3 → 4
```

The key idea is:

> Merge Sort is ideal for linked lists because we can split the list using pointers and merge sorted lists by rewiring existing nodes, achieving O(n log n) time without creating a second copy of the list.
