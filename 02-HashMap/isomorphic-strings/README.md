# WIN #50 — Isomorphic Strings

## Problem

[LeetCode 205 — Isomorphic Strings](https://leetcode.com/problems/isomorphic-strings/)

Given two strings `s` and `t`, determine whether they are **isomorphic**.

Two strings are isomorphic when the characters in `s` can be replaced to form `t` while maintaining the same character pattern.

The important rules are:

1. Every character in `s` must always map to the same character in `t`.
2. Two different characters in `s` cannot map to the same character in `t`.
3. A character is allowed to map to itself.
4. The relative order of characters must remain unchanged.

---

## Example 1

```text
Input:
s = "egg"
t = "add"

Output:
true
```

The mapping is:

```text
e → a
g → d
```

So:

```text
egg
↓↓↓
add
```

The mapping remains consistent, therefore the strings are isomorphic.

---

## Example 2

```text
Input:
s = "f11"
t = "b23"

Output:
false
```

The mappings would need to be:

```text
f → b
1 → 2
1 → 3
```

The character `1` is being mapped to both `2` and `3`.

That violates the one-to-one mapping rule.

Therefore:

```text
false
```

---

## Example 3

```text
Input:
s = "paper"
t = "title"

Output:
true
```

The mapping is:

```text
p → t
a → i
p → t
e → l
r → e
```

The repeated character `p` consistently maps to `t`.

Therefore:

```text
true
```

---

## My Java Solution

```java
class Solution {
    public boolean isIsomorphic(String s, String t) {

        HashMap<Character, Character > map1 = new HashMap<>();
        HashMap<Character, Character> map2 = new HashMap<>();

        for(int i=0; i< s.length();i++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);


            if(map1.containsKey(c1) && map1.get(c1) != c2){
                return false;
            }

            if(map2.containsKey(c2) && map2.get(c2) != c1){
                return false;
            }

            map1.put(c1,c2);
            map2.put(c2,c1);
        }

        return true;
        
    }
}
```

---

## My Thought Process

The main challenge is not simply checking whether characters have a mapping.

We need to guarantee a **one-to-one relationship**.

For example:

```text
s = "egg"
t = "add"
```

We can create:

```text
e → a
g → d
```

This works.

But consider:

```text
s = "ab"
t = "cc"
```

We would get:

```text
a → c
b → c
```

This is invalid because two different characters from `s` map to the same character in `t`.

So one HashMap is not enough.

I need to verify the mapping in **both directions**:

```text
s → t
AND
t → s
```

That's why I use two HashMaps.

---

# Step 1 — Create Two HashMaps

```java
HashMap<Character, Character> map1 = new HashMap<>();
HashMap<Character, Character> map2 = new HashMap<>();
```

The first map stores:

```text
s character → t character
```

For example:

```text
e → a
g → d
```

The second map stores the reverse:

```text
t character → s character
```

So:

```text
a → e
d → g
```

This lets us verify that the mapping is one-to-one.

---

# Step 2 — Traverse Both Strings Together

```java
for(int i=0; i< s.length();i++){
```

Because the problem guarantees:

```text
s.length() == t.length()
```

we can use the same index for both strings.

At every position:

```java
char c1 = s.charAt(i);
char c2 = t.charAt(i);
```

Here:

```text
c1 = character from s
c2 = character from t
```

For:

```text
s = "egg"
t = "add"
```

the iterations are:

```text
i = 0 → e, a
i = 1 → g, d
i = 2 → g, d
```

---

# Step 3 — Check the Forward Mapping

```java
if(map1.containsKey(c1) && map1.get(c1) != c2){
    return false;
}
```

This checks:

> Has this character from `s` already been mapped to a different character?

Suppose we have:

```text
s = "foo"
t = "bar"
```

At the beginning:

```text
f → b
o → a
```

When we encounter `o` again, it must still map to `a`.

If instead we encountered:

```text
o → r
```

the mapping would be inconsistent.

Therefore we return:

```text
false
```

---

# Step 4 — Check the Reverse Mapping

This is the most important part of the solution.

```java
if(map2.containsKey(c2) && map2.get(c2) != c1){
    return false;
}
```

This checks whether a character from `t` has already been assigned to another character from `s`.

Consider:

```text
s = "ab"
t = "cc"
```

First iteration:

```text
a → c
```

We store:

```text
map1:
a → c

map2:
c → a
```

Second iteration:

```text
b → c
```

Now `map2` already contains:

```text
c → a
```

but the current character is:

```text
b
```

So:

```java
map2.get(c2) != c1
```

becomes:

```text
a != b
```

Therefore:

```text
false
```

This prevents multiple characters from mapping to the same character.

---

# Step 5 — Store Both Mappings

After the consistency checks:

```java
map1.put(c1,c2);
map2.put(c2,c1);
```

We store both directions.

For:

```text
s = "egg"
t = "add"
```

the maps eventually become:

```text
map1:

e → a
g → d
```

and:

```text
map2:

a → e
d → g
```

---

# Step 6 — Return True

If we successfully process every character without finding an invalid mapping:

```java
return true;
```

This means the two strings follow the same character pattern.

---

## Complete Walkthrough

Let's trace:

```text
s = "paper"
t = "title"
```

Initially:

```text
map1 = {}
map2 = {}
```

---

### Index 0

```text
c1 = p
c2 = t
```

No mappings exist.

Store:

```text
map1:
p → t

map2:
t → p
```

---

### Index 1

```text
c1 = a
c2 = i
```

Store:

```text
map1:
p → t
a → i

map2:
t → p
i → a
```

---

### Index 2

```text
c1 = p
c2 = t
```

`map1` already contains:

```text
p → t
```

Current mapping is also:

```text
p → t
```

So it is consistent.

`map2` contains:

```text
t → p
```

which is also consistent.

Continue.

---

### Index 3

```text
c1 = e
c2 = l
```

Store:

```text
e → l
l → e
```

---

### Index 4

```text
c1 = r
c2 = e
```

Store:

```text
r → e
e → r
```

No conflict occurs.

Therefore:

```text
true
```

---

## Why Do We Need Two HashMaps?

This is the key idea of this problem.

### One HashMap is not enough

Suppose we only stored:

```text
s → t
```

For:

```text
s = "ab"
t = "cc"
```

we would get:

```text
a → c
b → c
```

Nothing in a single forward map prevents this.

But the problem says:

> No two characters may map to the same character.

Therefore, we also need:

```text
t → s
```

which detects:

```text
c → a
```

followed by:

```text
c → b
```

as a conflict.

So the complete rule is:

```text
s → t
AND
t → s
```

---

## Pattern Recognition

### Pattern: HashMap + Bidirectional Mapping

The key pattern here is:

> **When two sequences must have a one-to-one relationship, maintain mappings in both directions.**

Think of it as:

```text
        s
        ↓
     map1
        ↓
        t

        t
        ↓
     map2
        ↓
        s
```

The two maps guarantee that:

```text
One s character → only one t character
```

and:

```text
One t character → only one s character
```

This is essentially a **bijection**.

---

## A Useful Way to Recognize This Pattern

When you see a problem saying things like:

* "one character maps to another"
* "the mapping must remain consistent"
* "no two values can map to the same value"
* "one-to-one mapping"
* "preserve the pattern"

you should immediately think:

```text
HashMap
+
Reverse Mapping
```

---

## Pattern Example

Consider:

```text
s = "egg"
t = "add"
```

Pattern of `s`:

```text
e g g
1 2 2
```

Pattern of `t`:

```text
a d d
1 2 2
```

The patterns match.

Now:

```text
s = "foo"
t = "bar"
```

Pattern of `s`:

```text
f o o
1 2 2
```

Pattern of `t`:

```text
b a r
1 2 3
```

The patterns do not match.

The two HashMaps allow us to detect this mapping conflict while traversing the strings.

---

## Edge Cases

### 1. Same Character Mapping to Itself

```text
s = "a"
t = "a"
```

Mapping:

```text
a → a
```

This is valid.

---

### 2. Repeated Characters

```text
s = "egg"
t = "add"
```

The repeated `g` must always map to `d`.

Your first HashMap verifies this.

---

### 3. Two Characters Mapping to One Character

```text
s = "ab"
t = "cc"
```

Invalid.

The reverse HashMap catches this.

---

### 4. Different Mapping for the Same Character

```text
s = "foo"
t = "bar"
```

The character `o` would need to map to both:

```text
a
r
```

which is invalid.

The forward HashMap catches this.

---

### 5. Different Characters With Different Lengths

The problem guarantees:

```text
t.length() == s.length()
```

so we do not need a separate length check.

---

## Complexity

Let:

```text
n = s.length()
```

Since both strings have the same length, we process `n` character pairs.

### Time Complexity

Each character is processed once.

HashMap operations such as:

```java
containsKey()
get()
put()
```

take **O(1) average time**.

Therefore:

```text
O(n)
```

---

### Space Complexity

We maintain two HashMaps.

In the worst case, every character is different.

Therefore:

```text
O(n)
```

auxiliary space.

Because the input consists of ASCII characters, the number of distinct possible characters is bounded by the ASCII character set, so in this specific constraint setting the practical map size is bounded. The standard general complexity description is still **O(n)** auxiliary space.

---

## Key Learning

### 1. One-to-One Relationships Need Two Directions

If you only store:

```text
A → B
```

you can verify that `A` always maps to `B`.

But you cannot guarantee that another character doesn't also map to `B`.

That's why we maintain:

```text
A → B
B → A
```

---

### 2. Check Before Updating

The order is important:

```java
if(map1.containsKey(c1) && map1.get(c1) != c2){
    return false;
}
```

and:

```java
if(map2.containsKey(c2) && map2.get(c2) != c1){
    return false;
}
```

Only after validating the mapping do we update the maps.

---

### 3. Early Return Makes the Solution Efficient

As soon as an invalid mapping is found:

```java
return false;
```

There is no reason to process the remaining characters.

---

### 4. This Is a Bijection Problem

The relationship must be:

```text
s character ↔ t character
```

not merely:

```text
s character → t character
```

This distinction is the main concept behind the problem.

---

## Final Takeaway

The entire solution can be remembered as:

```text
Traverse both strings together
          ↓
Take one character from s
and one from t
          ↓
Check s → t mapping
          ↓
Check t → s mapping
          ↓
Conflict?
   ↓              ↓
  YES             NO
   ↓               ↓
false         Store both mappings
                    ↓
             Continue traversal
                    ↓
                  true
```

The main pattern to remember is:

> **For character/string transformation problems requiring a one-to-one mapping, use two HashMaps to maintain the mapping in both directions.**

**WIN #50 complete — HashMap + Bidirectional Mapping.**
