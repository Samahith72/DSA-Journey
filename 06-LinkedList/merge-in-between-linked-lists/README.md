# WIN #96 — 1669. Merge In Between Linked Lists

**LeetCode:** [1669. Merge In Between Linked Lists](https://leetcode.com/problems/merge-in-between-linked-lists/)  
**Difficulty:** Medium  
**Topics:** Linked List

---

## 1. Problem

You are given two singly linked lists, `list1` and `list2`, along with two indices, `a` and `b`.

Remove the nodes in `list1` from index `a` through index `b`, inclusive, and insert the entire `list2` in their place.

Return the head of the modified `list1`.

### Example

**Input:**

```text
list1 = [10, 1, 13, 6, 9, 5]
a = 3
b = 4
list2 = [1000000, 1000001, 1000002]
```

**Output:**

```text
[10, 1, 13, 1000000, 1000001, 1000002, 5]
```

The nodes at indices `3` and `4`, containing `6` and `9`, are removed. The entire second list is inserted between `13` and `5`.

---

## 2. Core Idea

The key is to connect the two lists by changing only a few `next` pointers.

We need three important nodes:

1. **Node before the deletion range:** The node at index `a - 1` in `list1`.
2. **Tail of `list2`:** The last node in the second list.
3. **Node after the deletion range:** The node at index `b + 1` in `list1`.

Once these nodes are identified, perform two connections:

```java
currentl1.next = list2;
taill2.next = l1.next;
```

The first connection inserts `list2` after the node before the deletion range. The second connects the tail of `list2` to the node immediately after the deleted range.

The original nodes between indices `a` and `b` become disconnected from the resulting list.

---

## 3. Java Solution

```java
class Solution {
    public ListNode mergeInBetween(ListNode list1, int a, int b, ListNode list2) {

        ListNode currentl1 = list1;
        ListNode taill2 = list2;

        for (int i = 0; i < a - 1; i++) {
            currentl1 = currentl1.next;
        }

        while (taill2.next != null) {
            taill2 = taill2.next;
        }

        ListNode l1 = getNode(b, list1);

        currentl1.next = list2;
        taill2.next = l1.next;

        return list1;
    }

    private ListNode getNode(int k, ListNode head) {
        for (int i = 0; i < k; i++) {
            head = head.next;
        }
        return head;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Find the node before index `a`

```java
ListNode currentl1 = list1;

for (int i = 0; i < a - 1; i++) {
    currentl1 = currentl1.next;
}
```

We start at the head of `list1` and move forward until we reach index `a - 1`.

For the example, `a = 3`, so we stop at index `2`, containing `13`.

```text
Index:   0     1     2     3     4     5
List1:  10 ->  1 ->  13 ->  6 ->  9 ->  5
                       ^
                  currentl1
```

This node will become the connection point for `list2`.

### Step 2: Find the tail of `list2`

```java
ListNode taill2 = list2;

while (taill2.next != null) {
    taill2 = taill2.next;
}
```

We traverse `list2` until we reach its last node, whose `next` pointer is `null`.

```text
1000000 -> 1000001 -> 1000002 -> null
                                  ^
                                taill2
```

We need this node because it will connect to the remaining part of `list1`.

### Step 3: Find the node at index `b`

```java
ListNode l1 = getNode(b, list1);
```

The helper method starts from the head and advances `k` times to reach the node at index `k`.

Since `b = 4`, `l1` points to the node containing `9`.

```text
10 -> 1 -> 13 -> 6 -> 9 -> 5
                         ^
                         l1
```

We need `l1.next`, which points to `5`, the first node that must remain after the deletion.

### Step 4: Connect the node before the range to `list2`

```java
currentl1.next = list2;
```

This replaces the original connection from `13` to `6` with a connection from `13` to `1000000`.

```text
10 -> 1 -> 13 -> 1000000 -> 1000001 -> 1000002
```

At this point, the original connection from the inserted list to the remaining part of `list1` has not yet been made.

### Step 5: Connect the tail of `list2` to the remaining list

```java
taill2.next = l1.next;
```

Because `l1` points to the node at index `b`, `l1.next` points to the node immediately after the deletion range.

The tail of `list2` is now connected to `5`.

```text
10 -> 1 -> 13 -> 1000000 -> 1000001 -> 1000002 -> 5
```

The resulting list contains the required nodes in the correct order.

### Step 6: Return the original head

```java
return list1;
```

The head of `list1` has not changed, so we return it.

---

## 5. Pointer Visualization

### Before modification

```text
list1:
10 -> 1 -> 13 -> 6 -> 9 -> 5 -> null
                ^         ^
              index a   index b

list2:
1000000 -> 1000001 -> 1000002 -> null
```

### After the first connection

```text
10 -> 1 -> 13 -> 1000000 -> 1000001 -> 1000002 -> null

6 -> 9 -> 5 -> null
```

The original list's nodes after index `b` are still connected to the deleted range, but they are no longer reachable from the head of `list1`.

### After the second connection

```text
10 -> 1 -> 13 -> 1000000 -> 1000001 -> 1000002 -> 5 -> null
```

The resulting list is correct because both connections have been updated.

---

## 6. Edge Cases

| Case | Expected behavior |
|---|---|
| `a = 1` | The first node of `list1` is removed and `list2` is inserted after the original head's predecessor position. |
| `b` is the second-to-last index | The final node of `list1` remains connected after `list2`. |
| `list2` contains one node | Its only node acts as both the head and tail of the inserted list. |
| `list2` contains multiple nodes | Traverse to its tail before connecting it to the remaining portion of `list1`. |

The constraints guarantee that `a >= 1` and `b` is before the last node of `list1`. Therefore, the node before index `a` and the node after index `b` both exist.

---

## 7. Complexity Analysis

Let:

- `n` be the length of `list1`.
- `m` be the length of `list2`.

**Time Complexity: O(n + m)**

- Finding the node before index `a` takes O(n) in the worst case.
- Finding the tail of `list2` takes O(m).
- Finding the node at index `b` takes O(n).
- Updating the two pointers takes O(1).

The combined time complexity is O(n + m).

**Auxiliary Space Complexity: O(1)**

Only a constant number of node references are used. No additional list or collection is created.

---

## 8. Pattern Recognition

This problem demonstrates the **linked-list segment replacement pattern**.

Whenever a problem asks you to remove a range of nodes and insert another list, identify these three positions:

1. The node immediately before the range.
2. The tail of the list being inserted.
3. The node immediately after the range.

Then update the necessary pointers to connect the remaining pieces.

### A useful improvement

The submitted solution traverses `list1` twice: once to find the node before index `a`, and once to find the node at index `b`.

You can optimize the traversal by finding both boundary nodes in a single pass, while separately finding the tail of `list2`. The overall complexity remains O(n + m), but the implementation avoids traversing the first list twice.

---

## 9. Linked List Patterns Learned So Far

| Problem | Pattern |
|---|---|
| Reverse Linked List | Three-pointer reversal |
| Middle of Linked List | Fast and slow pointers |
| Linked List Cycle | Floyd's cycle detection |
| Linked List Cycle II | Visited nodes and cycle entry |
| Palindrome Linked List | Find middle, reverse, and compare |
| Remove Nth Node From End | Fixed-gap two pointers |
| Reorder List | Find middle, reverse, and merge |
| Odd Even Linked List | Multiple pointer chains |
| Rotate List | Circular connection and split |
| Partition List | Separate chains |
| Copy List with Random Pointer | HashMap object mapping |
| Sort List | Merge sort and pointer splitting |
| Flatten a Multilevel Doubly Linked List | DFS and pointer rewiring |
| Split Linked List in Parts | Length calculation and balanced partitioning |
| Next Greater Node In Linked List | Monotonic stack |
| Design Front Middle Back Queue | Doubly linked list and middle indexing |
| Delete the Middle Node | Length calculation and predecessor rewiring |
| Merge In Between Linked Lists | Segment replacement and boundary-pointer rewiring |

---

## 10. Final Mental Model

Think of the linked list as a chain that must be cut at two boundaries and reconnected around an inserted chain.

```text
Before:
A -> [nodes to remove] -> B

Inserted list:
C -> D -> E

After:
A -> C -> D -> E -> B
```

Remember the two essential operations:

```java
nodeBeforeRange.next = list2;
tailOfList2.next = nodeAfterRange;
```

**Key takeaway:** You do not need to create a new list or copy values. By reconnecting the existing nodes through their `next` pointers, you can replace an entire segment efficiently.