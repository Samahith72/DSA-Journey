# WIN #97 — 23. Merge k Sorted Lists

**LeetCode:** [23. Merge k Sorted Lists](https://leetcode.com/problems/merge-k-sorted-lists/)  
**Difficulty:** Hard  
**Topics:** Linked List, Divide and Conquer, Heap (Priority Queue), Merge Sort

---

## 1. Problem

You are given an array of `k` linked lists. Each linked list is sorted in ascending order.

Merge all the linked lists into one sorted linked list and return its head.

### Example 1

**Input:**

```text
lists = [[1,4,5],[1,3,4],[2,6]]
```

**Output:**

```text
[1,1,2,3,4,4,5,6]
```

The three sorted linked lists are:

```text
List 1: 1 -> 4 -> 5
List 2: 1 -> 3 -> 4
List 3: 2 -> 6
```

The merged list is:

```text
1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
```

### Example 2

**Input:**

```text
lists = []
```

**Output:**

```text
[]
```

### Example 3

**Input:**

```text
lists = [[]]
```

**Output:**

```text
[]
```

---

## 2. Core Idea

The main challenge is deciding which node should be added to the merged list next.

Because every input list is already sorted, the smallest remaining node must be at the head of one of the non-empty lists.

Instead of checking every list's head repeatedly, we use a **min-heap**, implemented in Java using `PriorityQueue`.

The heap always gives us the smallest available node.

The algorithm works as follows:

1. Insert the head of every non-empty linked list into the min-heap.
2. Remove the node with the smallest value.
3. Attach that node to the merged list.
4. If that node has a next node, insert the next node into the heap.
5. Repeat until the heap is empty.

The important observation is that we only need to keep track of the current smallest available node from each list.

---

## 3. Java Solution

```java
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap =
            new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        for (ListNode node : lists) {
            if (node != null) {
                minHeap.offer(node);
            }
        }

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            ListNode current = minHeap.poll();

            tail.next = current;
            tail = current;

            if (current.next != null) {
                minHeap.offer(current.next);
            }
        }

        return dummy.next;
    }
}
```

---

## 4. Step-by-Step Explanation

### Step 1: Create the min-heap

```java
PriorityQueue<ListNode> minHeap =
    new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));
```

Java's `PriorityQueue` is a min-heap by default when given a comparator that orders elements by ascending value.

The comparator compares the values of two nodes:

- If `a.val` is smaller, `a` has higher priority.
- If `b.val` is smaller, `b` has higher priority.
- If the values are equal, either node can be selected first.

We use `Integer.compare` to compare values safely without risking integer overflow from subtraction.

### Step 2: Insert the head of every non-empty list

```java
for (ListNode node : lists) {
    if (node != null) {
        minHeap.offer(node);
    }
}
```

Initially, the heap contains only the first node of each non-empty list.

For the example:

```text
List 1: 1 -> 4 -> 5
List 2: 1 -> 3 -> 4
List 3: 2 -> 6
```

The initial heap contains nodes with values:

```text
[1, 1, 2]
```

These represent the smallest unprocessed node from each list.

The heap does not need to contain every node in advance.

### Step 3: Initialize the merged list

```java
ListNode dummy = new ListNode(0);
ListNode tail = dummy;
```

The dummy node simplifies list construction because we can always attach the next selected node using `tail.next`.

`tail` tracks the last node in the merged list.

Initially:

```text
dummy -> null
tail
```

The dummy node is a temporary helper and is not included in the returned result.

### Step 4: Extract the smallest node

```java
ListNode current = minHeap.poll();
```

`poll()` removes and returns the node with the smallest value.

For the initial heap `[1, 1, 2]`, the first extracted value is `1`.

### Step 5: Attach the node to the merged list

```java
tail.next = current;
tail = current;
```

The first statement connects the selected node to the end of the merged list.

The second moves `tail` forward to the newly attached node.

After selecting the first node:

```text
dummy -> 1
         ^
        tail
```

### Step 6: Insert the selected node's successor

```java
if (current.next != null) {
    minHeap.offer(current.next);
}
```

Suppose the extracted node is the first `1` from:

```text
1 -> 4 -> 5
```

Its successor is `4`, so we insert that `4` into the heap.

The heap now represents the smallest unprocessed node from each list:

```text
List 1 candidate: 4
List 2 candidate: 1
List 3 candidate: 2
```

The next extracted node will be the remaining `1`.

This is the key to the algorithm: after taking a node from one list, only that list's next node becomes a new candidate.

### Step 7: Repeat until the heap is empty

The algorithm repeatedly extracts the smallest candidate, appends it, and inserts its successor.

For the example, the selected values are:

```text
1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6
```

When the heap becomes empty, every node has been processed.

### Step 8: Return the merged list

```java
return dummy.next;
```

`dummy.next` points to the first real node in the merged list.

We return it rather than the dummy node itself.

---

## 5. Dry Run

Input:

```text
List 1: 1 -> 4 -> 5
List 2: 1 -> 3 -> 4
List 3: 2 -> 6
```

The table tracks the values in the heap, the selected node, and the merged list. Heap values are shown in sorted order for readability; the internal heap array need not be sorted.

| Step | Selected node | Heap after adding successor | Merged list |
|---:|---:|---|---|
| Initial | — | `[1, 1, 2]` | Empty |
| 1 | `1` | `[1, 2, 4]` | `1` |
| 2 | `1` | `[2, 3, 4]` | `1 -> 1` |
| 3 | `2` | `[3, 4, 6]` | `1 -> 1 -> 2` |
| 4 | `3` | `[4, 4, 6]` | `1 -> 1 -> 2 -> 3` |
| 5 | `4` | `[4, 5, 6]` | `1 -> 1 -> 2 -> 3 -> 4` |
| 6 | `4` | `[5, 6]` | `1 -> 1 -> 2 -> 3 -> 4 -> 4` |
| 7 | `5` | `[6]` | `1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5` |
| 8 | `6` | `[]` | `1 -> 1 -> 2 -> 3 -> 4 -> 4 -> 5 -> 6` |

Once the heap is empty, the final sorted linked list is complete.

---

## 6. Edge Cases

| Case | Behavior |
|---|---|
| Empty array `lists = []` | The heap remains empty, and the method returns `null`. |
| All lists are empty | No nodes are inserted; the method returns `null`. |
| One non-empty list | Its nodes are extracted and appended in sorted order. |
| Lists contain duplicate values | All nodes are preserved, including duplicates. |
| Negative values | The comparator correctly orders negative and positive values. |
| Unequal list lengths | A list contributes nodes until it is exhausted. |

---

## 7. Complexity Analysis

Let:

- `k` be the number of linked lists.
- `N` be the total number of nodes across all lists.

### Time Complexity: O(N log k)

Building the initial heap inserts at most `k` nodes.

Every one of the `N` nodes is removed from the heap exactly once. Each removal takes O(log k), and inserting a successor also takes O(log k).

The heap contains at most one candidate per list, so its size never exceeds `k`.

The overall time complexity is **O(k + N log k)**, commonly expressed as **O(N log k)** when the non-empty-list setup cost is absorbed into the bound.

### Auxiliary Space Complexity: O(k)

The heap stores at most one candidate node per list, requiring O(k) space.

The dummy node and pointer variables use O(1) additional space. The algorithm reuses the existing linked-list nodes rather than creating a separate node for every value.

---

## 8. Pattern Recognition

This problem teaches the **k-way merge using a min-heap** pattern.

Recognize this pattern when:

- You have multiple sorted sequences.
- You need one globally sorted output.
- You need to repeatedly choose the smallest available element.
- You want to avoid scanning every sequence at each step.

### Why not scan all list heads every time?

A straightforward approach checks all `k` list heads to find the smallest node for every selection. This can take O(Nk) time.

The min-heap reduces the cost of selecting the smallest candidate from O(k) to O(log k), giving O(N log k) time.

### Alternative: Divide and conquer

Another approach merges the lists in pairs, then merges the resulting lists in subsequent rounds, similar to merge sort. This also achieves O(N log k) time and is a useful alternative when practicing divide-and-conquer techniques.

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
| Merge k Sorted Lists | Min-heap and k-way merge |

---

## 10. Final Mental Model

Imagine each sorted linked list as a line of people waiting in ascending order of their values. You only need to compare the person at the front of each line.

The min-heap keeps track of the smallest front node across all lists.

```text
Take the smallest available node
             |
             v
Append it to the merged list
             |
             v
Add its next node to the heap
             |
             v
Repeat until the heap is empty
```

**Key takeaway:** When merging multiple sorted linked lists, keep one candidate from each non-empty list in a min-heap. Extract the smallest candidate, append it to the result, and add its successor. This achieves O(N log k) time without creating a new node for every element.