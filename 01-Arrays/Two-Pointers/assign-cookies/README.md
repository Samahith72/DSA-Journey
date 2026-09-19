# WIN #39 — Assign Cookies

## Problem

We have:

* An array `g` where `g[i]` represents the **greed factor** of a child.
* An array `s` where `s[j]` represents the **size** of a cookie.

A child is satisfied if:

```text
cookie size >= child's greed factor
```

Each child can receive at most one cookie, and each cookie can be given to at most one child.

The goal is to **maximize the number of satisfied children**.

For example:

```text
Input:
g = [1,2,3]
s = [1,1]

Output:
1
```

Only the child with greed factor `1` can be satisfied.

Another example:

```text
Input:
g = [1,2]
s = [1,2,3]

Output:
2
```

Both children can be satisfied.

---

## My Java Solution

```java
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        
        Arrays.sort(g);
        Arrays.sort(s);

        int greed = 0;
        int cookies = 0;
        int satisfied = 0;

        while(greed < g.length && cookies < s.length){
            if(s[cookies] >= g[greed]){
                cookies++;
                greed++;
                satisfied++;
            }else{
                cookies++;
            }
        }

        return satisfied;
    }
}
```

---

## My Thought Process

The first thing I noticed is that I don't need to assign cookies randomly.

I want to maximize the number of satisfied children.

The important question is:

> Which child should receive which cookie?

A natural greedy strategy is:

```text
Smallest greed → Smallest cookie that can satisfy them
```

So I first sort both arrays.

For example:

```text
g = [1,2,3]
s = [1,1]
```

After sorting:

```text
Children:
[1,2,3]

Cookies:
[1,1]
```

Now I can compare them from smallest to largest.

---

# Step 1 — Sort Both Arrays

I use:

```java
Arrays.sort(g);
Arrays.sort(s);
```

After sorting:

```text
g → smallest greed → largest greed

s → smallest cookie → largest cookie
```

This allows me to make the greedy decision locally.

---

# Step 2 — Use Two Pointers

I use:

```java
int greed = 0;
int cookies = 0;
```

where:

```text
greed   → current child
cookies → current cookie
```

For:

```text
g = [1,2,3]
s = [1,1]
```

we start with:

```text
Children:
[1,2,3]
 ↑
greed

Cookies:
[1,1]
 ↑
cookies
```

---

# Step 3 — Check Whether the Cookie Can Satisfy the Child

The main condition is:

```java
if(s[cookies] >= g[greed])
```

This asks:

> Is the current cookie large enough for the current child?

If yes, we assign it.

```java
cookies++;
greed++;
satisfied++;
```

All three values move forward.

---

# Example Walkthrough

Consider:

```text
g = [1,2,3]
s = [1,1]
```

### Step 1

Current:

```text
child greed = 1
cookie size = 1
```

Check:

```text
1 >= 1
```

True.

So we assign the cookie.

```text
satisfied = 1
```

Pointers become:

```text
greed = 1
cookies = 1
```

---

### Step 2

Now:

```text
child greed = 2
cookie size = 1
```

Check:

```text
1 >= 2
```

False.

So this cookie cannot satisfy the current child.

We discard it:

```java
cookies++;
```

Now:

```text
cookies = 2
```

There are no cookies left.

Therefore:

```text
satisfied = 1
```

Final answer:

```text
1
```

---

# Why Can We Discard a Cookie That Is Too Small?

This is the most important greedy observation.

Suppose:

```text
current child greed = 3
current cookie = 2
```

We know:

```text
2 < 3
```

So this cookie cannot satisfy the current child.

Because the children are sorted:

```text
3,4,5,6...
```

every remaining child is at least as greedy as the current child.

Therefore, this cookie cannot satisfy **any** remaining child.

So there is no reason to keep it.

We safely do:

```java
cookies++;
```

and move to the next cookie.

---

# Why Start With the Least Greedy Child?

Suppose:

```text
g = [1,2,3]
s = [2,3]
```

The smallest cookie `2` can satisfy:

```text
greed = 1
```

or:

```text
greed = 2
```

If we give the cookie to the child with greed `2`, the child with greed `1` could potentially still be satisfied by something else.

But if we give the smallest suitable cookie to the least greedy child:

```text
1 ← cookie 2
```

we preserve larger cookies for children that need them.

This is the greedy principle:

> **Use the smallest cookie that can satisfy the least greedy remaining child.**

This avoids wasting large cookies on children who could have accepted smaller ones.

---

# Example of the Greedy Strategy

Consider:

```text
g = [1,2,3]
s = [1,2,3]
```

Start:

```text
child = 1
cookie = 1
```

`1 >= 1`, so assign.

```text
[1] → [1]
```

Next:

```text
child = 2
cookie = 2
```

`2 >= 2`, so assign.

Next:

```text
child = 3
cookie = 3
```

`3 >= 3`, so assign.

Final:

```text
satisfied = 3
```

Every child is satisfied.

---

# What If the Cookie Is Too Small?

Consider:

```text
g = [2,3]
s = [1,3]
```

Start:

```text
child = 2
cookie = 1
```

Since:

```text
1 < 2
```

the cookie cannot satisfy the child.

Because the next child requires even more:

```text
3
```

the cookie `1` cannot satisfy them either.

So we discard it.

Next:

```text
child = 2
cookie = 3
```

Now:

```text
3 >= 2
```

So we assign it.

Final:

```text
satisfied = 1
```

---

# Pattern Recognition

## Pattern: Sorting + Two Pointers + Greedy

This problem combines three important ideas.

### 1. Sorting

Sort both arrays:

```text
g → smallest greed first
s → smallest cookie first
```

### 2. Two Pointers

Use:

```text
greed   → current child
cookies → current cookie
```

### 3. Greedy Decision

At every step:

```text
Can this cookie satisfy this child?
```

If yes:

```text
Assign cookie
Move both pointers
```

If no:

```text
Discard cookie
Move only cookie pointer
```

---

# The Core Algorithm

The entire solution can be summarized as:

```text
Sort greed factors
Sort cookie sizes

greed = 0
cookies = 0

while children and cookies remain:

    if current cookie >= current child's greed:

        assign cookie
        satisfied++
        greed++
        cookies++

    else:

        cookie is too small
        discard it
        cookies++
```

---

# Why This Greedy Approach Works

There are two important cases.

## Case 1 — Cookie Can Satisfy the Child

Suppose:

```text
cookie >= greed
```

Then assigning the cookie is safe because:

* The child is satisfied.
* We use the smallest available cookie that can satisfy this child.
* Larger cookies remain available for children with higher greed.

Therefore:

```text
greed++
cookies++
```

---

## Case 2 — Cookie Is Too Small

Suppose:

```text
cookie < greed
```

Because the arrays are sorted, every remaining child has greed:

```text
>= current greed
```

Therefore this cookie cannot satisfy any remaining child.

So we discard it:

```text
cookies++
```

This is what makes the greedy approach correct.

---

# Example

Consider:

```text
g = [1,2,3]
s = [1,1,2,3]
```

After sorting:

```text
Children:
[1,2,3]

Cookies:
[1,1,2,3]
```

### Match 1

```text
1 >= 1 ✓
```

```text
satisfied = 1
```

### Match 2

```text
1 >= 2 ✗
```

Discard cookie `1`.

### Match 3

```text
2 >= 2 ✓
```

```text
satisfied = 2
```

### Match 4

```text
3 >= 3 ✓
```

```text
satisfied = 3
```

Final answer:

```text
3
```

---

# Important Insight

The problem is not really about cookies.

It is about **matching two sorted sets under a minimum requirement**.

The same pattern can appear in problems involving:

```text
Resources → Requirements
Machines  → Tasks
Servers   → Workloads
Skills    → Jobs
Capacity  → Demand
```

Whenever one side has a minimum requirement and the other side provides a resource, sorting + greedy matching can be a useful pattern to consider.

---

# Edge Cases

## More Children Than Cookies

```text
g = [1,2,3]
s = [1]
```

Only one child can be satisfied.

```text
Output:
1
```

---

## More Cookies Than Children

```text
g = [1,2]
s = [1,2,3,4]
```

Only two children exist, so:

```text
Output:
2
```

Extra cookies don't matter.

---

## No Cookies

```text
g = [1,2,3]
s = []
```

The loop never runs.

```text
Output:
0
```

---

## Every Cookie Is Too Small

```text
g = [5,6]
s = [1,2,3]
```

Every cookie is discarded.

```text
Output:
0
```

---

# Complexity

Let:

```text
n = g.length
m = s.length
```

## Time Complexity

Sorting the children:

```text
O(n log n)
```

Sorting the cookies:

```text
O(m log m)
```

The two-pointer traversal:

```text
O(n + m)
```

Therefore the total complexity is:

```text
O(n log n + m log m)
```

The sorting dominates the linear traversal.

---

## Space Complexity

The two-pointer algorithm itself uses:

```text
O(1)
```

additional variables.

However, Java's `Arrays.sort()` implementation may use some internal stack/workspace depending on the array type and runtime.

For the algorithmic approach, we generally describe the extra space as:

```text
O(1)
```

apart from the sorting implementation.

---

# Key Learning

### 1. Give the Smallest Suitable Resource

The central greedy idea is:

> **Don't waste a large cookie on a child who can be satisfied by a smaller cookie.**

Instead:

```text
smallest suitable cookie
          ↓
least greedy child
```

---

### 2. A Failed Match Can Sometimes Be Permanently Discarded

When:

```text
cookie < current greed
```

we don't need to reconsider that cookie later.

Because the children are sorted, every future child needs at least as much.

Therefore:

```text
cookie → discard
```

This is a powerful greedy observation.

---

### 3. Sorting Creates a Useful Order

Without sorting, it would be difficult to know which cookie can safely be discarded.

After sorting:

```text
small → large
```

we can make decisions locally and never need to backtrack.

---

### 4. Two Pointers Can Represent a Matching Process

Here:

```text
greed   → current requirement
cookies → current resource
```

The pointers move based on whether the current resource satisfies the current requirement.

This is a useful two-pointer variation beyond simple array manipulation.

---

## Final Takeaway

The solution can be summarized as:

```text
Sort children by greed
Sort cookies by size
          ↓
      Two pointers
          ↓
Cookie >= greed?
   ↓              ↓
 YES              NO
  ↓                ↓
Assign           Discard
  ↓                ↓
Move both       Move cookie
```

The core pattern is:

```text
Sorting
   +
Two Pointers
   +
Greedy Matching
```

The final complexity is:

```text
Time:  O(n log n + m log m)
Space: O(1) auxiliary
```

The biggest takeaway is:

> **When matching resources to requirements, sort both sides and try to satisfy the smallest requirement with the smallest resource that can satisfy it. If the smallest resource is already too small, it can be safely discarded.**
