# WIN #31 — Minimum Window Substring

## Problem

Given two strings `s` and `t`, find the **minimum window substring** of `s` that contains every character from `t`, including duplicate characters.

If no such substring exists, return an empty string `""`.

For example:

```text
Input:
s = "ADOBECODEBANC"
t = "ABC"

Output:
"BANC"
```

The window `"BANC"` contains:

```text
A → 1
B → 1
C → 1
```

which satisfies everything required by `t`.

Another important example:

```text
Input:
s = "a"
t = "aa"

Output:
""
```

Even though `s` contains an `a`, it does not contain **two `a`s**, which are required by `t`.

---

## My Java Solutions

I solved this problem in **two ways**.

Both solutions use the same **Sliding Window + Two Pointers** idea.

The difference is how character frequencies are stored:

* **Approach 1:** `HashMap<Character, Integer>`
* **Approach 2:** `int[128]`

---

# Approach 1 — HashMap Frequency Counting

```java
class Solution {
    public String minWindow(String s, String t) {
        
        Map<Character, Integer> frequency = new HashMap<>();
        for(char c: t.toCharArray()){
            frequency.put(c, frequency.getOrDefault(c,0)+1);
        }

        Map<Character, Integer> window = new HashMap<>();
        int left= 0;
        int right =0;
        int required = frequency.size();
        int formed = 0;

        int bestLength = Integer.MAX_VALUE;
        int bestStart = 0;


        while(right != s.length()){
            char c = s.charAt(right);
            window.put(c, window.getOrDefault(c, 0)+1 );

            if(frequency.containsKey(c)){
                if(window.get(c).equals(frequency.get(c))){
                    formed++;
                }
            }

            while(formed == required){
                int currentWindowLength = right - left +1;
                if(currentWindowLength < bestLength){
                    bestLength = currentWindowLength;
                    bestStart = left;
                }

                char leftChar = s.charAt(left);
                
                window.put(leftChar, window.get(leftChar)-1);

                if(frequency.containsKey(leftChar)){
                    if(window.get(leftChar) < frequency.get(leftChar)){
                        formed--;
                    }
                }
                left++;
            }
            right++;
        }

        if(bestLength == Integer.MAX_VALUE){
            return "";
        }

        return s.substring(bestStart, bestStart + bestLength);
    }
}
```

---

# Approach 2 — Fixed-Size Frequency Array

```java
class Solution {
    public String minWindow(String s, String t) {
        
        int[] frequency = new int[128];
        for(char c: t.toCharArray()){
            frequency[c]++;
        }

        int left= 0;
        int right =0;

        int required =  0;
        for(int i=0;i < 128; i++){
            if(frequency[i] > 0)
                required++;
        }

        int formed = 0;
        int bestLength = Integer.MAX_VALUE;
        int bestStart = 0;

        int[] window = new int[128];

        while(right < s.length()){
            char c = s.charAt(right);
            window[c]++;

            if(frequency[c] > 0 && window[c] == frequency[c]){
                formed++;
            }

            while(formed == required){
                int currentWindowLength = right - left +1;

                if(currentWindowLength < bestLength){
                    bestLength = currentWindowLength;
                    bestStart = left;
                }

                char leftChar = s.charAt(left);
                
                window[leftChar]--;

                if(frequency[leftChar] > 0 && 
                   window[leftChar] < frequency[leftChar]){
                    formed--;
                }

                left++;
            }

            right++;
        }

        if(bestLength == Integer.MAX_VALUE){
            return "";
        }

        return s.substring(bestStart, bestStart + bestLength);
    }
}
```

---

# My Thought Process

The first important observation is that this is **not simply a substring problem**.

We need to find a window that satisfies a frequency requirement.

For example:

```text
s = "ADOBECODEBANC"
t = "AABC"
```

The window must contain:

```text
A → 2
B → 1
C → 1
```

So we need to keep track of:

1. What characters are required?
2. How many times is each character required?
3. How many of those requirements are currently satisfied by our window?

This naturally leads to a **frequency map/array + sliding window**.

---

# Pattern Recognition

## Pattern: Sliding Window + Two Pointers

The window is represented by:

```text
[left ........ right]
```

The two pointers define the current substring.

### `right`

Moves forward to **expand the window**.

### `left`

Moves forward to **shrink the window** once the current window becomes valid.

The main idea is:

```text
Expand → Make window valid → Shrink → Record answer → Expand again
```

---

# Step 1 — Count Required Characters

For:

```text
t = "ABC"
```

we store:

```text
A → 1
B → 1
C → 1
```

For:

```text
t = "AABC"
```

we store:

```text
A → 2
B → 1
C → 1
```

This is why we need frequency counting rather than simply checking whether a character exists.

---

# Step 2 — Expand the Window

Start with:

```text
left = 0
right = 0
```

Move `right` through `s`.

For:

```text
s = "ADOBECODEBANC"
t = "ABC"
```

we gradually build:

```text
A
AD
ADO
ADOB
ADOBE
ADOBEC
```

When the window contains all required characters, it becomes **valid**.

For example:

```text
ADOBEC
```

contains:

```text
A
B
C
```

So we now have a valid window.

---

# Step 3 — Track `formed`

A very important part of the solution is:

```java
int required = frequency.size();
int formed = 0;
```

`required` tells us how many **different characters** we need.

For:

```text
t = "AABC"
```

we have:

```text
required = 3
```

because the different characters are:

```text
A, B, C
```

`formed` tells us how many of those character requirements are currently completely satisfied.

For example:

```text
Required:

A → 2
B → 1
C → 1
```

Current window:

```text
A → 2
B → 1
C → 1
```

Then:

```text
formed = 3
required = 3
```

Therefore:

```text
formed == required
```

means the current window is valid.

---

# Step 4 — Shrink the Window

Once:

```text
formed == required
```

we know the window is valid.

Now the question becomes:

> Can we make this valid window smaller?

So we move `left` forward.

Before shrinking:

```text
A D O B E C
↑         ↑
left     right
```

We record its length.

Then remove the character at `left`:

```text
A
↑
left
```

and move:

```text
left++
```

If the window is still valid, we continue shrinking.

Eventually, removing a character makes the window invalid.

At that point:

```text
formed < required
```

So we stop shrinking and move `right` again.

---

# Why Do We Track `bestStart` and `bestLength`?

Whenever we have a valid window:

```java
int currentWindowLength = right - left + 1;
```

we compare it with our best answer so far.

```java
if(currentWindowLength < bestLength){
    bestLength = currentWindowLength;
    bestStart = left;
}
```

We don't need to store the substring every time.

We only remember:

```text
bestStart
bestLength
```

Then at the end:

```java
return s.substring(bestStart, bestStart + bestLength);
```

---

# Example Walkthrough

Consider:

```text
s = "ADOBECODEBANC"
t = "ABC"
```

Required:

```text
A → 1
B → 1
C → 1
```

Initially:

```text
left = 0
right = 0
formed = 0
```

As `right` moves:

```text
A D O B E C
```

we eventually have:

```text
A → satisfied
B → satisfied
C → satisfied
```

Therefore:

```text
formed = required
```

Current valid window:

```text
"ADOBEC"
```

Now shrink from the left:

```text
"DOBEC"
"OBEC"
```

Eventually removing `C` would make the window invalid, so we stop.

Then continue expanding.

Later we reach:

```text
"BANC"
```

which is also valid.

Its length is:

```text
4
```

Compared with:

```text
"ADOBEC" → 6
```

we update the answer:

```text
best = "BANC"
```

No shorter valid window exists.

Therefore:

```text
Output:
"BANC"
```

---

# Approach 1 vs Approach 2

The algorithm is essentially the same in both solutions.

The major difference is the data structure used for frequency counting.

## Approach 1 — HashMap

```java
Map<Character, Integer> frequency
Map<Character, Integer> window
```

This is more general and readable.

We explicitly store:

```text
'A' → 1
'B' → 1
'C' → 1
```

### Advantages

* Easy to understand.
* Works naturally with characters.
* Doesn't depend on a fixed character range.

### Disadvantage

HashMap operations have more overhead than direct array indexing.

---

## Approach 2 — Frequency Array

```java
int[] frequency = new int[128];
int[] window = new int[128];
```

Because the problem only contains uppercase and lowercase English letters, a fixed-size array is enough.

A character can directly act as an array index:

```java
frequency[c]++;
```

For example:

```text
c = 'A'
```

means:

```java
frequency['A']
```

stores the frequency of `A`.

This avoids HashMap operations and makes the implementation more lightweight.

---

# Why `formed` Is Better Than Comparing Entire Maps

A common mistake would be repeatedly checking whether the entire window satisfies `t`.

For example, after every movement of `left` or `right`, we could compare all frequencies.

That would introduce unnecessary work.

Instead, we maintain:

```text
formed
```

and update it only when a character reaches or falls below its required frequency.

For example:

```java
if(frequency[c] > 0 && window[c] == frequency[c]){
    formed++;
}
```

This means:

> This character has just reached the exact number of occurrences we need.

Similarly:

```java
if(frequency[leftChar] > 0 &&
   window[leftChar] < frequency[leftChar]){
    formed--;
}
```

means:

> We removed one occurrence too many, so this character is no longer satisfied.

This allows us to determine validity in `O(1)` time.

---

# Why the Sliding Window Is Efficient

At first, the nested `while` loop may look like it could make the algorithm `O(n²)`.

But it doesn't.

The important observation is that:

```text
right only moves forward
left only moves forward
```

Neither pointer ever moves backward.

So across the entire algorithm:

```text
right → at most m movements
left  → at most m movements
```

Therefore the total work is linear.

---

# Complexity

Let:

```text
m = s.length()
n = t.length()
```

## Approach 1 — HashMap

### Time Complexity

```text
O(m + n)
```

We process `t` once to build the frequency map.

We then process `s` using two pointers.

Both `left` and `right` move only forward.

Therefore:

```text
O(m + n)
```

### Space Complexity

```text
O(n)
```

The HashMaps store the characters and their frequencies.

---

## Approach 2 — Frequency Array

### Time Complexity

```text
O(m + n)
```

We process `t`, scan the fixed-size frequency array, and process `s`.

The frequency array has a constant size of `128`, so that part is effectively:

```text
O(1)
```

The overall complexity is:

```text
O(m + n)
```

### Space Complexity

```text
O(1)
```

because the frequency arrays have a fixed size of `128`.

---

# Key Learning

### 1. Minimum Window Problems → Think Sliding Window

When a problem asks for:

> The smallest/longest substring satisfying some condition

a sliding window should immediately come to mind.

---

### 2. Frequency Matters

`t = "AABC"` is different from:

```text
"ABC"
```

because `A` must appear twice.

So simply checking whether a character exists is not enough.

We need:

```text
required frequency
vs
current window frequency
```

---

### 3. `formed` Makes the Window Check Efficient

Instead of repeatedly checking every character, we maintain a counter:

```text
formed == required
```

This tells us in `O(1)` time whether the window is valid.

---

### 4. Expand and Shrink

The general sliding-window structure is:

```text
Expand with right
        ↓
Window becomes valid
        ↓
Shrink with left
        ↓
Record the smallest valid window
        ↓
Window becomes invalid
        ↓
Expand again
```

This is one of the most important sliding-window patterns to recognize.

---

### 5. Same Algorithm, Better Data Structure

My two solutions taught me an important optimization:

```text
HashMap
   ↓
works and is flexible

Fixed-size array
   ↓
same algorithm
less overhead
```

When the input character set is known and small, an array can replace a HashMap and make frequency operations simpler and faster.

---

## Final Takeaway

The biggest idea I learned from this problem is:

> **Don't repeatedly search for the answer. Maintain a valid window, shrink it as much as possible, and keep track of the best window found so far.**

The core pattern is:

```text
Sliding Window
+ Two Pointers
+ Frequency Counting
+ Expand / Shrink
```

And the optimized version achieves the required:

```text
Time:  O(m + n)
Space: O(1)
```
