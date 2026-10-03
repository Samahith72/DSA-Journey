# 🏆 WIN #73 — 160. Intersection of Two Linked Lists

[**LeetCode 160 — Intersection of Two Linked Lists**](https://leetcode.com/problems/intersection-of-two-linked-lists/)

**Difficulty:** 🟢 Easy
**Topic:** Linked List

---

# 📌 Problem

Given the heads of two singly linked lists `headA` and `headB`, return the **node at which the two lists intersect**.

If the two linked lists do not intersect, return:

```text
null
```

The important point is that **intersection means the same node reference**, not simply two nodes having the same value.

---

# 🧠 What Does "Intersection" Actually Mean?

Consider:

```text
List A:

4 → 1 → 8 → 4 → 5
          ↑
          │
List B:   5 → 6 → 1
```

The lists intersect at node `8`.

After that point, they share the exact same nodes:

```text
             ┌→ 8 → 4 → 5
             │
4 → 1 ───────┘

5 → 6 → 1 ──┘
```

The important thing is:

```text
A's 1 ≠ B's 1
```

even though their values are both `1`.

They are different objects in memory.

But:

```text
A's 8 == B's 8
```

because both pointers refer to the **same node**.

So we must compare:

```java
pointerA == pointerB
```

not:

```java
pointerA.val == pointerB.val
```

---

# 💡 Core Idea

The two linked lists can have different lengths.

For example:

```text
A:  a1 → a2 → a3 → c1 → c2 → c3
B:  b1 → b2 → c1 → c2 → c3
```

List A has:

```text
3 nodes before intersection
```

List B has:

```text
2 nodes before intersection
```

Therefore, if we simply move both pointers together from their heads, they will not reach the intersection at the same time.

The trick is:

> When one pointer reaches the end of its list, move it to the **head of the other list**.

So:

```text
pointerA:
A → B

pointerB:
B → A
```

This makes both pointers travel exactly:

```text
m + n
```

nodes.

Eventually they either meet at the intersection node or both become `null`.

---

# 💻 Java Solution

```java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {

        ListNode pointerA = headA;
        ListNode pointerB = headB;

        while(pointerA != pointerB){
            if(pointerA == null){
                pointerA = headB;
            }else{
                pointerA = pointerA.next;
            }

            if(pointerB == null){
                pointerB = headA;
            }else{
                pointerB = pointerB.next;
            }
        }

        return pointerA;
    }
}
```

---

# 🔍 Step-by-Step Explanation

## 1. Start One Pointer at Each Head

```java
ListNode pointerA = headA;
ListNode pointerB = headB;
```

Initially:

```text
A: 4 → 1 → 8 → 4 → 5
   ↑
 pointerA

B: 5 → 6 → 1 → 8 → 4 → 5
   ↑
 pointerB
```

---

# 2. Move Both Pointers

The loop is:

```java
while(pointerA != pointerB)
```

We continue until both pointers point to the **same node**.

Again, this is reference comparison:

```java
pointerA != pointerB
```

not value comparison.

---

# 3. Normal Movement

If `pointerA` is not `null`:

```java
pointerA = pointerA.next;
```

Similarly:

```java
pointerB = pointerB.next;
```

So both pointers normally move one node at a time.

---

# 🔄 4. The Important Trick

When `pointerA` reaches the end:

```java
if(pointerA == null){
    pointerA = headB;
}
```

Instead of stopping, pointer A starts traversing **List B**.

Likewise:

```java
if(pointerB == null){
    pointerB = headA;
}
```

Pointer B starts traversing **List A**.

So the paths become:

```text
pointerA:

A → A → A → null
              ↓
              B → B → B → null
```

And:

```text
pointerB:

B → B → B → null
              ↓
              A → A → A → null
```

---

# 🧠 Why Does Switching Heads Work?

Suppose:

```text
Length of A = m
Length of B = n
```

Let:

```text
a = nodes before intersection in A
b = nodes before intersection in B
c = nodes in the common part
```

Then:

```text
A = a + c
B = b + c
```

Pointer A travels:

```text
A + B
```

Pointer B travels:

```text
B + A
```

Therefore both pointers travel exactly the same total distance:

```text
m + n
```

The unequal prefixes effectively cancel each other out.

---

# 📊 Visual Example

Consider:

```text
A: 4 → 1 → 8 → 4 → 5
B: 5 → 6 → 1 → 8 → 4 → 5
```

Lengths:

```text
A = 5
B = 6
```

The intersection is:

```text
8 → 4 → 5
```

### Pointer A's journey

```text
A → A → A → A → A → null
                      ↓
                      B → B → B → ...
```

### Pointer B's journey

```text
B → B → B → B → B → B → null
                          ↓
                          A → A → ...
```

After switching lists, the extra distance each pointer had to travel on its original list gets balanced.

Eventually:

```text
pointerA
    ↓
    8
    ↑
pointerB
```

Both point to the same node.

The loop stops:

```java
while(pointerA != pointerB)
```

and:

```java
return pointerA;
```

returns the intersection node.

---

# 🎯 Example 1 Walkthrough

```text
A = 4 → 1 → 8 → 4 → 5
B = 5 → 6 → 1 → 8 → 4 → 5
```

Intersection:

```text
8
```

The two pointers initially have different distances to `8`.

After traversing their own lists:

```text
pointerA → null
pointerB → null
```

But instead of stopping:

```text
pointerA → headB
pointerB → headA
```

Now each pointer traverses the other list.

Eventually:

```text
pointerA → 8
pointerB → 8
```

Therefore:

```text
pointerA == pointerB
```

and we return that node.

---

# ❌ What If There Is No Intersection?

Example:

```text
A: 2 → 6 → 4
B: 1 → 5
```

There is no common node.

Pointer A traverses:

```text
A → null → B
```

Pointer B traverses:

```text
B → null → A
```

Eventually both reach:

```text
null
```

So:

```java
pointerA == pointerB
```

becomes true because both are `null`.

The loop ends and:

```java
return pointerA;
```

returns:

```text
null
```

---

# ⚠️ Very Important: Compare References, Not Values

This is one of the most important lessons from this problem.

Suppose:

```text
List A:

4 → 1 → 8 → 4 → 5

List B:

5 → 6 → 1 → 8 → 4 → 5
```

There are two nodes with value `1`.

But they are different nodes.

So:

```java
pointerA.val == pointerB.val
```

does **not** mean the lists intersect there.

We need:

```java
pointerA == pointerB
```

because Java object references tell us whether both pointers point to the **same node object**.

---

# 🔥 Why We Don't Modify the Lists

The problem specifically says that the original linked-list structure must remain unchanged.

Our solution only moves:

```text
pointerA
pointerB
```

We never modify:

```java
node.next
```

Therefore the original lists remain exactly as they were.

This is one reason the two-pointer solution is especially clean.

---

# 🧩 Edge Cases

## 1. Lists Intersect at the Head

```text
A
↓
1 → 2 → 3

B ────┘
```

Both pointers start at the same node:

```java
pointerA == pointerB
```

The loop doesn't execute.

Return:

```text
1
```

---

## 2. Lists Intersect at the Last Node

```text
A: 1 → 2 ─┐
          ↓
          5
          ↑
B: 3 → 4 ┘
```

Eventually both pointers reach:

```text
5
```

Return that node.

---

## 3. No Intersection

```text
A: 1 → 2 → 3

B: 4 → 5 → 6
```

Both eventually become:

```text
null
```

Return:

```text
null
```

---

## 4. One List Is Longer

```text
A: 1 → 2 → 3 → 4 → 5
B:       6 → 7 → 4 → 5
```

Switching heads automatically compensates for the difference in length.

No need to calculate:

```text
lengthA
lengthB
```

and no need to manually advance the longer list.

---

# ❌ Common Wrong Approach

A common mistake is:

```java
if(pointerA.val == pointerB.val)
```

This is incorrect.

Why?

Because intersection is based on **node identity**, not node value.

For example:

```text
A: 1 → 2 → 3
B: 4 → 1 → 3
```

The two `1` nodes may be completely different objects.

Therefore:

```text
same value ≠ same node
```

The correct condition is:

```java
pointerA == pointerB
```

---

# 🧠 Pattern Recognition

Whenever you see:

> "Find where two linked lists intersect"

Think:

```text
Two Pointers
      ↓
Different lengths?
      ↓
Switch heads
      ↓
A → B
B → A
      ↓
Compare references
```

The core template is:

```java
ListNode a = headA;
ListNode b = headB;

while(a != b){

    if(a == null)
        a = headB;
    else
        a = a.next;

    if(b == null)
        b = headA;
    else
        b = b.next;
}
```

---

# ⏱️ Complexity

Let:

```text
m = length of List A
n = length of List B
```

### Time Complexity

```text
O(m + n)
```

Each pointer traverses at most both lists.

### Space Complexity

```text
O(1)
```

Only two pointers are used.

This satisfies the follow-up requirement.

---

# 🚀 Key Takeaway

The most important idea is:

> **Make both pointers travel the same total distance.**

Instead of calculating the lengths of the two lists, we let each pointer traverse both lists:

```text
Pointer A: A → B
Pointer B: B → A
```

Then their different starting distances automatically cancel out.

The final pattern is:

```text
A → B
B → A
```

and compare:

```java
pointerA == pointerB
```

If they meet:

```text
→ Intersection Node
```

If they don't:

```text
→ null
```

---

# 🔗 Linked List Patterns Learned So Far

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
```

## ⭐ The Pattern to Remember

```text
Different list lengths
        ↓
Don't calculate lengths
        ↓
Pointer A → List A → List B
Pointer B → List B → List A
        ↓
Both travel m + n
        ↓
Compare references
        ↓
Intersection OR null
```
