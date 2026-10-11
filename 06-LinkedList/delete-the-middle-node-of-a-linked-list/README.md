# WIN #95 — 2095. Delete the Middle Node of a Linked List

[LeetCode — 2095. Delete the Middle Node of a Linked List](https://leetcode.com/problems/delete-the-middle-node-of-a-linked-list/)

**Difficulty:** Medium  
**Topics:** Linked List, Traversal, Pointer Manipulation

---

## 1. Problem

Given the head of a singly linked list, delete its middle node and return the head of the modified list.

The middle node is defined using **0-based indexing** as the node at index:

```text
floor(n / 2)
```

where `n` is the total number of nodes.

For an even-sized list, the middle node is the second of the two middle nodes.

### Example 1

```text
Input:  [1,3,4,7,1,2,6]
Output: [1,3,4,1,2,6]
```

The list contains 7 nodes.

```text
Index:  0  1  2  3  4  5  6
Value:  1  3  4  7  1  2  6
                  ↑
             Delete this node
```

### Example 2

```text
Input:  [1,2,3,4]
Output: [1,2,4]
```

The list contains 4 nodes.

The middle index is `4 / 2 = 2`, so the node containing `3` is deleted.

### Example 3

```text
Input:  [2,1]
Output: [2]
```

The middle index is `2 / 2 = 1`, so the node containing `1` is deleted.

---

## 2. Core Idea

Your solution uses a **two-pass approach**:

1. Traverse the linked list to calculate its length.
2. Calculate the middle index.
3. Find the node immediately before the middle node.
4. Skip the middle node by updating the previous node's `next` pointer.

The key formula is:

```java
int mid = size / 2;
```

Since the list uses zero-based indexing, this gives the correct middle index for both odd and even lengths.

For example:

```text
List:  1 → 3 → 4 → 7 → 1 → 2 → 6

size = 7
mid = 7 / 2 = 3
```

The middle node is at index `3`, containing `7`.

To delete it, we need the node at index `2`, containing `4`.

---

## 3. Java Solution

```java
class Solution {
    public ListNode deleteMiddle(ListNode head) {

        if(head == null || head.next == null){
            return null;
        }

        int size = 0;
        ListNode current = head;

        while(current != null){
            size++;
            current = current.next;
        }

        int mid = size / 2;
        ListNode previous = getNode(mid - 1, head);

        previous.next = previous.next.next;
        return head;
    }

    private ListNode getNode(int k, ListNode head){
        ListNode current = head;

        for(int i = 0; i < k; i++){
            current = current.next;
        }

        return current;
    }
}
```

---

## 4. Step 1 — Handle the Base Cases

```java
if(head == null || head.next == null){
    return null;
}
```

There are two cases.

**Empty list:**

```text
head = null
```

There is no node to retain, so return `null`.

**Single-node list:**

```text
5 → null
```

The only node is also the middle node.

Deleting it produces an empty list, so return `null`.

This check also ensures that the later calculation of `mid - 1` is safe for the remaining cases.

---

## 5. Step 2 — Calculate the List Length

```java
int size = 0;
ListNode current = head;

while(current != null){
    size++;
    current = current.next;
}
```

We traverse the entire list and count the nodes.

For example:

```text
1 → 3 → 4 → 7 → 1 → 2 → 6
```

The counter progresses as follows:

```text
Visit 1  → size = 1
Visit 3  → size = 2
Visit 4  → size = 3
Visit 7  → size = 4
Visit 1  → size = 5
Visit 2  → size = 6
Visit 6  → size = 7
```

After the traversal:

```text
size = 7
```

Notice that `current` becomes `null` after this loop. That is why we use `head` again when calling `getNode()`.

---

## 6. Step 3 — Calculate the Middle Index

```java
int mid = size / 2;
```

Integer division in Java discards the fractional part.

| List size | Middle index | Explanation |
|---:|---:|---|
| 1 | 0 | Only node |
| 2 | 1 | Second node |
| 3 | 1 | Second node |
| 4 | 2 | Third node |
| 5 | 2 | Third node |
| 6 | 3 | Fourth node |
| 7 | 3 | Fourth node |

This matches the problem's definition:

```text
middle index = floor(size / 2)
```

For a list of 7 nodes:

```text
mid = 7 / 2 = 3
```

For a list of 4 nodes:

```text
mid = 4 / 2 = 2
```

Both cases select the correct node.

---

## 7. Step 4 — Find the Previous Node

```java
ListNode previous = getNode(mid - 1, head);
```

We don't need to find the middle node itself.

We need the node immediately before it so that we can change its `next` pointer.

For example:

```text
Index:  0   1   2   3   4   5   6
List:   1 → 3 → 4 → 7 → 1 → 2 → 6
                  ↑
                middle
```

Here:

```text
mid = 3
mid - 1 = 2
```

So `getNode(2, head)` returns the node containing `4`.

```text
previous → 4
```

### Understanding `getNode()`

```java
private ListNode getNode(int k, ListNode head){
    ListNode current = head;

    for(int i = 0; i < k; i++){
        current = current.next;
    }

    return current;
}
```

The helper starts at index `0` and moves forward `k` times.

For `k = 2`:

```text
Start:   1 → 3 → 4 → 7 → 1 → 2 → 6
         ↑

Move 1:  1 → 3 → 4 → 7 → 1 → 2 → 6
             ↑

Move 2:  1 → 3 → 4 → 7 → 1 → 2 → 6
                 ↑
```

It returns the node containing `4`.

---

## 8. Step 5 — Delete the Middle Node

This is the most important line in your solution:

```java
previous.next = previous.next.next;
```

Before deletion:

```text
previous
   ↓
   4 → 7 → 1
```

Here:

```text
previous.next       = node containing 7
previous.next.next  = node containing 1
```

After executing:

```java
previous.next = previous.next.next;
```

the connection becomes:

```text
previous
   ↓
   4 ─────→ 1
```

The resulting list is:

```text
1 → 3 → 4 → 1 → 2 → 6
```

The node containing `7` is skipped.

We do not need to shift values or create a new list. We only change one pointer.

Finally:

```java
return head;
```

returns the head of the modified list.

---

## 9. Complete Dry Run

Consider:

```text
head = [1,3,4,7,1,2,6]
```

### Step 1: Count nodes

```text
size = 7
```

### Step 2: Find the middle index

```text
mid = 7 / 2 = 3
```

### Step 3: Find the previous node

```text
mid - 1 = 2
```

Node at index `2` contains `4`.

### Step 4: Reconnect the list

Before:

```text
1 → 3 → 4 → 7 → 1 → 2 → 6
```

Execute:

```java
previous.next = previous.next.next;
```

After:

```text
1 → 3 → 4 → 1 → 2 → 6
```

### Final output

```text
[1,3,4,1,2,6]
```

---

## 10. Edge Cases

### Single node

```text
Input: [5]
Output: []
```

The base case returns `null`.

### Two nodes

```text
Input: [2,1]
Output: [2]
```

The middle index is `1`. The previous node is at index `0`, so its `next` pointer is updated to `null`.

### Odd number of nodes

```text
Input: [1,2,3,4,5]
Output: [1,2,4,5]
```

The middle index is `2`, so node `3` is deleted.

### Even number of nodes

```text
Input: [1,2,3,4,5,6]
Output: [1,2,3,5,6]
```

The middle index is `3`, so node `4` is deleted.

---

## 11. Complexity Analysis

Let `n` be the number of nodes.

### Time Complexity

**O(n)**

The solution performs two traversals:

- Counting the nodes takes `O(n)`.
- Finding the previous node takes `O(n)` in the worst case.

The pointer deletion itself takes `O(1)` time.

Therefore:

```text
O(n) + O(n) + O(1) = O(n)
```

### Auxiliary Space Complexity

**O(1)**

The solution uses only a constant number of pointers and integer variables. It does not create another list or use an additional data structure proportional to the input size.

---

## 12. Alternative Pattern — Fast and Slow Pointers

This problem can also be solved in one traversal using fast and slow pointers.

The idea is to maintain `previous` just before `slow`:

```text
slow moves 1 step
fast moves 2 steps
previous follows slow
```

When `fast` reaches the end, `slow` points to the middle node that must be deleted, and `previous` points to the node before it.

Then the deletion is:

```java
previous.next = slow.next;
```

This approach achieves:

```text
Time:  O(n)
Space: O(1)
```

Your current solution is also `O(n)` time and `O(1)` auxiliary space. The fast-and-slow approach is simply an alternative that avoids counting the length first.

---

## 13. Pattern Recognition

When a linked-list problem asks you to delete a node at a particular position, remember:

1. Find the target position.
2. Locate the node before the target.
3. Skip the target by updating the previous node's `next` pointer.

The essential deletion pattern is:

```java
previous.next = previous.next.next;
```

This works when `previous` is guaranteed to exist, as it is in your solution after handling lists with fewer than two nodes.

---

## 14. Linked List Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Reverse Linked List | Three-pointer reversal |
| Middle of Linked List | Fast and slow pointers |
| Linked List Cycle | Floyd's cycle detection |
| Linked List Cycle II | Visited nodes / cycle entry |
| Remove Nth Node | Fixed-gap two pointers |
| Reorder List | Middle + reverse + merge |
| Sort List | Merge Sort + split + merge |
| Split Linked List in Parts | Length calculation + balanced partitioning |
| Delete the Middle Node | Length calculation + previous-node rewiring |

---

## 15. Final Mental Model

Think of the problem in four steps:

```text
Count the nodes
      ↓
Calculate middle index
      ↓
Find the previous node
      ↓
Skip the middle node
```

The central idea is:

> To delete a middle node in a singly linked list, find its predecessor and redirect the predecessor's `next` pointer to the node after the middle.
