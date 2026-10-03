# 🏆 WIN #72 — 203. Remove Linked List Elements

[**LeetCode 203 — Remove Linked List Elements**](https://leetcode.com/problems/remove-linked-list-elements/)

**Difficulty:** 🟢 Easy
**Topic:** Linked List

---

## 📌 Problem

Given the `head` of a linked list and an integer `val`, remove **all nodes** whose value is equal to `val`.

Return the **new head** of the linked list.

### Example 1

```text
Input:
head = [1,2,6,3,4,5,6]
val = 6

Output:
[1,2,3,4,5]
```

### Example 2

```text
Input:
head = []
val = 1

Output:
[]
```

### Example 3

```text
Input:
head = [7,7,7,7]
val = 7

Output:
[]
```

---

# 💡 Core Idea

The main challenge is that the node we want to delete could be:

* Somewhere in the middle
* At the end
* The first node
* Several consecutive nodes
* Every node in the list

The easiest way to handle **all of these cases uniformly** is to use a **dummy node** before the actual head.

```text
dummy → head → ...
```

Then we maintain a pointer called `current`.

Instead of checking `current` itself, we check:

```java
current.next
```

If `current.next` contains the value we want to remove:

```java
current.next = current.next.next;
```

This simply skips the unwanted node.

---

# 🧠 Why Do We Need a Dummy Node?

Consider:

```text
head → 6 → 2 → 3
       ↑
     remove
```

If the first node itself needs to be removed, we would have to change `head`.

Without a dummy node, we need special handling:

```java
while(head != null && head.val == val)
    head = head.next;
```

The dummy node eliminates this special case.

We create:

```text
dummy → 6 → 2 → 3
```

Now removing `6` is exactly the same operation as removing any other node:

```text
dummy → 2 → 3
```

We simply do:

```java
dummy.next = dummy.next.next;
```

---

# 💻 Java Solution

```java
class Solution {
    public ListNode removeElements(ListNode head, int val) {

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode current = dummy;

        while(current.next != null){
            if(current.next.val == val){
                current.next = current.next.next;
            }else{
                current = current.next;
            }
        }

        return dummy.next;
    }
}
```

---

# 🔍 Step-by-Step Explanation

## 1. Create a Dummy Node

```java
ListNode dummy = new ListNode(0);
dummy.next = head;
```

Suppose:

```text
head
 ↓
1 → 2 → 6 → 3 → 4
```

After creating the dummy:

```text
dummy → 1 → 2 → 6 → 3 → 4
```

The dummy node does not belong to the answer.

It is only there to make deletion easier.

---

## 2. Start `current` at Dummy

```java
ListNode current = dummy;
```

So:

```text
dummy → 1 → 2 → 6 → 3 → 4
  ↑
current
```

We always examine the node **after `current`**.

---

# 🔄 3. Traverse the List

```java
while(current.next != null)
```

We continue while there is a node to examine.

Notice that we use:

```java
current.next
```

instead of:

```java
current
```

because we need `current` to be the node **before the node we might delete**.

---

# ❌ 4. If the Next Node Has the Target Value

```java
if(current.next.val == val)
```

Suppose:

```text
1 → 2 → 6 → 3
        ↑
      remove
```

Here:

```text
current = 2
current.next = 6
```

Since:

```java
current.next.val == val
```

we remove it.

---

# ✂️ 5. Remove the Node

```java
current.next = current.next.next;
```

Before:

```text
current
   ↓
   2 → 6 → 3
       ↑
      remove
```

After:

```text
current
   ↓
   2 ─────→ 3
```

The `6` is skipped.

This is the fundamental linked-list deletion operation:

```text
A → B → C
```

To remove `B`:

```text
A.next = C
```

In code:

```java
A.next = A.next.next;
```

---

# ⚠️ Important: Don't Move `current` After Deletion

This is a very important part of the solution.

When we delete:

```java
current.next = current.next.next;
```

we **do not** do:

```java
current = current.next;
```

Why?

Because there may be another target value immediately after the deleted node.

### Example

```text
1 → 6 → 6 → 3
```

Suppose:

```text
val = 6
```

First deletion:

```text
1 → 6 → 3
```

`current` is still pointing to `1`.

Now:

```text
current.next = 6
```

is still a node that needs to be checked.

We delete it again:

```text
1 → 3
```

Therefore, when deleting:

```java
if(current.next.val == val){
    current.next = current.next.next;
}
```

we keep `current` where it is.

---

# ✅ 6. If the Node Should Stay

If:

```java
current.next.val != val
```

then we move forward:

```java
current = current.next;
```

Example:

```text
1 → 2 → 6 → 3
↑
current
```

If `1` isn't the value to remove:

```text
1 → 2 → 6 → 3
     ↑
   current
```

We move to the next node.

---

# 🔁 Complete Example

Suppose:

```text
head = [1,2,6,3,4,5,6]
val = 6
```

Initially:

```text
dummy → 1 → 2 → 6 → 3 → 4 → 5 → 6
  ↑
current
```

### Check `1`

`1 != 6`

Move:

```text
dummy → 1 → 2 → 6 → 3 → 4 → 5 → 6
         ↑
       current
```

### Check `2`

`2 != 6`

Move:

```text
dummy → 1 → 2 → 6 → 3 → 4 → 5 → 6
             ↑
           current
```

### Check `6`

`6 == 6`

Remove it:

```text
dummy → 1 → 2 ─────→ 3 → 4 → 5 → 6
             ↑
           current
```

Notice `current` stays at `2`.

### Continue

Check `3` → keep it.

```text
dummy → 1 → 2 → 3 → 4 → 5 → 6
                  ↑
                current
```

Eventually the final `6` is removed.

Final list:

```text
1 → 2 → 3 → 4 → 5
```

---

# 🎯 Why Return `dummy.next`?

At the end:

```java
return dummy.next;
```

The dummy node itself should not be returned.

Remember:

```text
dummy → 1 → 2 → 3
```

The actual linked list starts at:

```text
dummy.next
   ↓
   1 → 2 → 3
```

So:

```java
return dummy.next;
```

returns the correct new head.

This also works when the original head gets deleted.

---

# 🧩 Edge Cases

### 1. Empty List

```text
head = []
```

Then:

```text
dummy → null
```

The loop doesn't execute.

Return:

```text
[]
```

---

### 2. First Node Must Be Removed

```text
6 → 2 → 3
```

The dummy node handles it naturally:

```text
dummy → 6 → 2 → 3
```

After deletion:

```text
dummy → 2 → 3
```

Return:

```text
2 → 3
```

---

### 3. All Nodes Must Be Removed

```text
7 → 7 → 7 → 7
```

Each node is removed while `current` stays at the dummy node.

Final:

```text
dummy → null
```

Return:

```text
null
```

---

### 4. Consecutive Duplicates

```text
1 → 6 → 6 → 6 → 3
```

Because `current` doesn't move after deletion:

```text
1 → 6 → 6 → 6 → 3
    ↓
1 → 6 → 6 → 3
    ↓
1 → 6 → 3
    ↓
1 → 3
```

All consecutive target nodes are correctly removed.

---

# ⏱️ Complexity

Let `n` be the number of nodes.

### Time Complexity

```text
O(n)
```

We traverse the linked list once.

### Space Complexity

```text
O(1)
```

Only a few pointers are used:

```text
dummy
current
```

No additional data structure is required.

---

# 🧠 Pattern Recognition

Whenever you see:

> "Remove nodes from a linked list based on a condition"

Think:

```text
Dummy Node
    ↓
Previous / Current Pointer
    ↓
Check current.next
    ↓
Skip unwanted node
```

The key deletion pattern is:

```java
current.next = current.next.next;
```

And the important rule is:

```text
DELETE → DON'T MOVE current
KEEP   → MOVE current
```

---

# 🔥 Important Linked List Pattern Learned

So far:

```text
206  → Reverse Linked List
       └── 3-pointer reversal

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
```

---

# 🚀 Key Takeaway

The most important idea in this problem is the **dummy node + previous/current pointer pattern**.

Instead of directly modifying the `head`, create:

```text
dummy → head
```

Then always inspect:

```java
current.next
```

If it needs to be removed:

```java
current.next = current.next.next;
```

Otherwise:

```java
current = current.next;
```

### The golden rule:

```text
If deleting → stay
If keeping  → move
```

This pattern appears repeatedly in linked-list problems involving **deletion, filtering, removing duplicates, and modifying nodes**.
