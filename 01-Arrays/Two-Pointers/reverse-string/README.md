# WIN #20 — Reverse String

## Problem

Given a string represented as an array of characters `s`, reverse the string **in-place**.

The input is provided as a character array, so the original array must be modified directly.

The solution must use:

```text
O(1) extra memory
```

For example:

```text
Input:
s = ["h","e","l","l","o"]

Output:
["o","l","l","e","h"]
```

Another example:

```text
Input:
s = ["H","a","n","n","a","h"]

Output:
["h","a","n","n","a","H"]
```

The important requirement is that I cannot create another array to store the reversed string. I need to reverse the existing array itself.

---

## My Java Solution

```java
class Solution {
    public void reverseString(char[] s) {

        int left = 0;
        int right = s.length - 1;

        while(left < right){
            char c = s[left];
            s[left] = s[right];
            s[right] = c;

            left++;
            right--;
        }
        
    }
}
```

---

## My Thought Process

The first thing I noticed is that I need to reverse the entire array.

For example:

```text
["h","e","l","l","o"]
```

needs to become:

```text
["o","l","l","e","h"]
```

The first character needs to move to the last position, the second character needs to move to the second-last position, and so on.

So instead of shifting every character one by one, I can directly swap characters from opposite ends.

I use two pointers:

```text
left
right
```

The `left` pointer starts at the beginning:

```text
left = 0
```

and the `right` pointer starts at the end:

```text
right = s.length - 1
```

The overall idea is:

```text
Start from both ends
        ↓
Swap left and right characters
        ↓
Move left forward
        ↓
Move right backward
        ↓
Repeat until they meet
```

---

## Step 1: Initialize Two Pointers

I start with:

```java
int left = 0;
int right = s.length - 1;
```

For:

```text
s = ["h","e","l","l","o"]
```

the pointers are:

```text
left
 ↓
[h,e,l,l,o]
         ↑
       right
```

So:

```text
left = 0
right = 4
```

---

## Step 2: Swap the Characters

The main operation is:

```java
char c = s[left];
s[left] = s[right];
s[right] = c;
```

This swaps the characters at the two pointers.

For example:

```text
[h,e,l,l,o]
 ↑       ↑
left   right
```

The characters are:

```text
s[left] = 'h'
s[right] = 'o'
```

After swapping:

```text
[o,e,l,l,h]
```

Now the first and last positions are correct.

---

## Step 3: Move Both Pointers

After the swap, I move:

```java
left++;
right--;
```

So the pointers move toward the center.

The array is now:

```text
[o,e,l,l,h]
   ↑   ↑
 left right
```

Now:

```text
left = 1
right = 3
```

I repeat the same process.

Swap:

```text
'e' ↔ 'l'
```

The array becomes:

```text
[o,l,l,e,h]
```

Then move the pointers again:

```text
left = 2
right = 2
```

At this point, the pointers have met.

There is no need to swap the middle character with itself.

---

## Step 4: Stop When the Pointers Meet

The loop condition is:

```java
while(left < right)
```

This means I continue swapping only while the left pointer is strictly before the right pointer.

For:

```text
["h","e","l","l","o"]
```

the process is:

```text
Initial:

[h,e,l,l,o]
 ↑       ↑
 L       R

Swap:

[o,e,l,l,h]

Move:

[o,e,l,l,h]
   ↑   ↑
   L   R

Swap:

[o,l,l,e,h]

Move:

[o,l,l,e,h]
     ↑
     L/R

Stop
```

Final result:

```text
["o","l","l","e","h"]
```

---

## Complete Example Walkthrough

Consider:

```text
s = ["h","e","l","l","o"]
```

### Step 1

```text
left = 0
right = 4
```

Array:

```text
[h,e,l,l,o]
 ↑       ↑
 L       R
```

Swap:

```text
[o,e,l,l,h]
```

Move pointers:

```text
left = 1
right = 3
```

---

### Step 2

```text
[o,e,l,l,h]
   ↑   ↑
   L   R
```

Swap:

```text
'e' ↔ 'l'
```

Array becomes:

```text
[o,l,l,e,h]
```

Move pointers:

```text
left = 2
right = 2
```

---

### Step 3

Now:

```text
left == right
```

So:

```java
left < right
```

is false.

The loop stops.

Final result:

```text
["o","l","l","e","h"]
```

---

## Why I Don't Need Another Array

A straightforward approach might be to create a new array:

```text
Original:
[h,e,l,l,o]

New array:
[o,l,l,e,h]
```

But the problem requires:

```text
O(1) extra memory
```

So creating another array would require:

```text
O(n)
```

space.

Instead, I modify the original array directly using swaps:

```text
[h,e,l,l,o]
     ↓
Swap characters
     ↓
[o,l,l,e,h]
```

The only extra variable I need is:

```java
char c;
```

which stores one character temporarily during a swap.

Therefore, the solution uses constant extra memory.

---

## Why Two Pointers Work

The key observation is that reversing an array is naturally based on **pairs of opposite positions**.

For an array:

```text
[h,e,l,l,o]
```

the pairs are:

```text
h ↔ o
e ↔ l
l ↔ l
```

So I don't need to process every character independently.

I can process two characters at a time:

```text
First ↔ Last
Second ↔ Second Last
Third ↔ Third Last
```

This is exactly what the two-pointer technique provides.

The pattern is:

```text
left → → →
          ← ← ← right
```

Both pointers move toward the center.

---

## Pattern Recognition

### Pattern: Two Pointers / In-Place

This problem is a simple example of the **Two Pointers** pattern.

The two pointers start at opposite ends:

```text
left = beginning
right = end
```

Then:

```text
while(left < right)
```

I swap the elements and move both pointers toward the center.

The general pattern is:

```text
Left                     Right
 ↓                         ↓
[A,B,C,D,E]
 ↑                         ↑
Swap
 ↓                         ↓
[E,B,C,D,A]

Move both pointers inward
```

The overall structure is:

```text
Initialize left and right
        ↓
Swap elements
        ↓
left++
right--
        ↓
Repeat
        ↓
Stop when left >= right
```

This pattern is useful when I need to work with elements from both ends of an array or string.

Common examples include:

```text
Reverse an array
Reverse a string
Check for palindrome
Two Sum in a sorted array
Move elements based on conditions
Partition arrays
```

---

## Why `left < right`?

I use:

```java
while(left < right)
```

instead of:

```java
while(left <= right)
```

because when:

```text
left == right
```

both pointers point to the same element.

There is nothing to swap.

For example:

```text
[a,b,c]
```

After swapping `a` and `c`:

```text
[c,b,a]
```

the pointers meet at:

```text
     ↓
[c,b,a]
   left/right
```

The middle element `b` is already in the correct position.

So the algorithm can stop.

---

## Odd and Even Length Arrays

The same approach works for both odd and even-sized arrays.

### Odd Length

For:

```text
[a,b,c,d,e]
```

the pointers eventually meet at:

```text
[a,b,c,d,e]
     ↑
   left/right
```

The middle element does not need to be changed.

### Even Length

For:

```text
[a,b,c,d]
```

the pointers eventually cross:

```text
[c,d,b,a]
     ↑ ↑
   right left
```

At that point:

```text
left > right
```

and the loop stops.

So the condition:

```java
left < right
```

works for both cases.

---

## Complexity

### Time Complexity

```text
O(n)
```

Each swap handles two characters.

For an array of length `n`, I need approximately:

```text
n / 2
```

swaps.

Therefore:

```text
O(n / 2)
```

which simplifies to:

```text
O(n)
```

So the time complexity is:

```text
O(n)
```

---

### Space Complexity

```text
O(1)
```

The array is modified directly.

I only use:

```text
left
right
c
```

as additional variables.

No additional array or data structure is created.

Therefore:

```text
Auxiliary Space = O(1)
```

---

## Key Learning

I learned how the **Two Pointer** technique can be used to reverse an array efficiently.

Instead of creating a new array or shifting elements, I can simply swap corresponding elements from opposite ends.

The strategy is:

```text
Start at both ends
        ↓
Swap the characters
        ↓
Move both pointers toward the center
        ↓
Repeat until the pointers meet
```

The key idea is:

```text
First ↔ Last
Second ↔ Second Last
Third ↔ Third Last
```

This allows the entire array to be reversed in:

```text
O(n) Time
O(1) Auxiliary Space
```

The biggest takeaway for me is recognizing that when a problem involves the **beginning and end of an array or string**, I should consider whether a two-pointer approach can process both sides simultaneously.

This problem is one of the simplest and clearest examples of the pattern:

```text
Two Pointers
     ↓
Opposite ends
     ↓
Swap
     ↓
Move inward
     ↓
O(n) Time + O(1) Space
```
